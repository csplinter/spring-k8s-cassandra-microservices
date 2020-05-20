package com.datastax.sample.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;

/**
 * Configuration Cassandra Driver <-> Spring Data.
 * 
 * With Spring Data you can either use convention only an define proper keys
 * in application.yaml or use JavaConfig and extend {@link AbstractCassandraConfiguration}
 * 
 * Sample application.yaml for.
 * spring:
 *   data:
 *     cassandra:
 *       contact-points: localhost:9042
 *       local-datacenter: dc1
 *       keyspace-name: springdemo
 *
 * @author Cedrick LUNVEN (@clunven)
 */
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;
    
    @Value("${spring.data.cassandra.local-datacenter}")
    private String localDataCenter;
    
    /** {@inheritDoc} */
    @Override
    public String[] getEntityBasePackages() {
        return new String[]{ "com.datastax.sample.entity" };
    }
    
    /** {@inheritDoc} */
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        List<CreateKeyspaceSpecification> createKeyspaceSpecifications = new ArrayList<>();
        CreateKeyspaceSpecification spec = CreateKeyspaceSpecification.createKeyspace(getKeyspaceName());
        spec.ifNotExists(true).withNetworkReplication(DataCenterReplication.of(getLocalDataCenter(), 3));
        createKeyspaceSpecifications.add(spec);
        return createKeyspaceSpecifications;
    }

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

}
