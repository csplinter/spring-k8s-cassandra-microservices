package com.datastax.examples.conf;

import com.datastax.examples.dao.ProductDao;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class SpringBootCassandraConfiguration {
    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${astra.secure-connect-bundle:none}")
    private String astraSecureConnectBundle;

    @Value("${cassandra.username:cassandra}")
    private String username;

    @Value("${cassandra.password:cassandra}")
    private String password;

    @Value("${cassandra.contact-points}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private Integer port;

    @Value("${cassandra.local-datacenter:dc1}")
    private String localDataCenter;

    public String getKeyspace() {
        return this.keyspace;
    }

    public String getAstraSecureConnectBundle() {
        return this.astraSecureConnectBundle;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Bean
    public CqlSession cqlSession() {
        CqlSessionBuilder cqlSessionBuilder = CqlSession.builder();

        if (!astraSecureConnectBundle.equals("none")) {
            cqlSessionBuilder
                    .withCloudSecureConnectBundle(this.getClass().getResourceAsStream(this.astraSecureConnectBundle))
                    .withAuthCredentials(this.username, this.password);
        }
        else {
            cqlSessionBuilder.addContactPoint(new InetSocketAddress(this.contactPoints, this.port));
            cqlSessionBuilder.withLocalDatacenter(this.localDataCenter);
        }

        return cqlSessionBuilder.build();
    }

    @Bean
    public ProductDao productDao(CqlSession session) {
        return new ProductDao(session);
    }
}