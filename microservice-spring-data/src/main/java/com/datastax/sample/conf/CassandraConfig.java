package com.datastax.sample.conf;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.AbstractSessionConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.cql.session.init.KeyspacePopulator;
import org.springframework.data.cassandra.core.cql.session.init.ScriptException;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.metadata.Node;
import com.datastax.sample.SpringDataApplication;

/**
 * Configuration Cassandra Driver <-> Spring Data.
 * 
 * With Spring Data you can either use convention only an define proper keys
 * in application.yaml or use JavaConfig and extend {@link AbstractCassandraConfiguration}
 * 
 * Here we choose the Java Config.
 *
 * @see AbstractSessionConfiguration
 * @see AbstractCassandraConfiguration
 *
 * @author Cedrick LUNVEN (@clunven)
 */
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
   
    /** Logger for the class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConfig.class);
    
    @Value("${spring.data.cassandra.keyspace-name:springdemo}")
    private String keyspaceName;
    
    @Value("${spring.data.cassandra.local-datacenter:dc1}")
    private String localDataCenter;
    
    @Value("${spring.data.cassandra.contact-point:localhost}")
    private String contactPoints;
    
    @Value("${spring.data.cassandra.port:9042}")
    private int port;
    
    /** {@inheritDoc} */
    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }
    /** {@inheritDoc} */
    @Override
    protected String getLocalDataCenter() {
        return localDataCenter;
    }
    /** {@inheritDoc} */
    @Override
    protected String getContactPoints() {
        return contactPoints;
    }
    /** {@inheritDoc} */
    @Override
    protected int getPort() {
        return port;
    }
    
    /** {@inheritDoc} */
    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Arrays.asList(CreateKeyspaceSpecification
                .createKeyspace(getKeyspaceName())
                .ifNotExists(true)
                .withNetworkReplication(DataCenterReplication.of(getLocalDataCenter(), 3))
                .with(KeyspaceOption.DURABLE_WRITES));
    }
    
    /** {@inheritDoc} */
    @Override
    public String[] getEntityBasePackages() {
        return new String[]{ SpringDataApplication.class.getPackageName() };
    }
    
    /** {@inheritDoc} */
    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
    
    /** {@inheritDoc} */
    @Override
    protected KeyspacePopulator keyspacePopulator() {
        return new KeyspacePopulator() {
            /** {@inheritDoc} */
            @Override
            public void populate(CqlSession cqlSession) throws ScriptException {
                for (Node host : cqlSession.getMetadata().getNodes().values()) {
                    LOGGER.info("+ '{}/{}': ip='{}',listen='{}'", 
                            host.getDatacenter(), host.getRack(),
                            host.getBroadcastAddress().orElse(null),
                            host.getListenAddress().orElse(null));
                }
            }
        };
    }

}
