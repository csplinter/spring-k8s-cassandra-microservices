package com.datastax.examples.conf;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.AbstractSessionConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.cql.session.init.KeyspacePopulator;
import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator;

import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.examples.SpringDataApplication;


@Configuration
public class SpringDataCassandraJavaConfig 
                extends AbstractCassandraConfiguration 
                implements CqlSessionBuilderCustomizer {

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    protected String getLocalDataCenter() {
        return localDataCenter;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public void customize(CqlSessionBuilder cqlSessionBuilder) {
        //cqlSessionBuilder.
        // Here you define the SecureCloudConnectBundle for ASTRA
    }
    
    @Value("${spring.data.cassandra.keyspace-name:springdemo}")
    private String keyspaceName;
    
    @Value("${spring.data.cassandra.local-datacenter}")
    private String localDataCenter;
    
    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;
    
    @Value("${spring.data.cassandra.port:9042}")
    private int port;
    
    /** {@inheritDoc} */
    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Arrays.asList(CreateKeyspaceSpecification
                .createKeyspace(getKeyspaceName())
                .ifNotExists(true)
                .withNetworkReplication(DataCenterReplication.of(getLocalDataCenter(), 1))
                .with(KeyspaceOption.DURABLE_WRITES));
    }
    
    /** {@inheritDoc} */
    @Override
    public String[] getEntityBasePackages() {
        return new String[]{ SpringDataApplication.class.getPackageName() + ".model" };
    }
    
    /** {@inheritDoc} */
    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
    
    /** {@inheritDoc} */
    @Override
    protected KeyspacePopulator keyspacePopulator() {
        ResourceKeyspacePopulator keyspacePopulate = new ResourceKeyspacePopulator();
        keyspacePopulate.setSeparator(";");
        keyspacePopulate.setScripts(new ClassPathResource("sample-data.cql"));
        return keyspacePopulate;
    }
    

}
