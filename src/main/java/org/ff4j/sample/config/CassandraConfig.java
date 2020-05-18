package org.ff4j.sample.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;

import com.datastax.oss.driver.api.core.CqlSession;

/**
 * Configuration Cassandra Driver <-> Spring Data.
 *
 * @author Cedrick LUNVEN (@clunven)
 */
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
   
    @Value("${cassandra.keyspace}")
    public String keyspaceName;
    
    /**
     * Delegate Configuration into `application.conf`
     */
    @Bean
    @Primary
    public CqlSession cqlSession() {
        return CqlSession.builder().build(); 
    }
    
    /** {@inheritDoc} */
    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
    
    /** {@inheritDoc} */
    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }
    
    /** {@inheritDoc} */
    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"org.ff4j.sample.entity"};
    }
    
    /** {@inheritDoc} */
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        List<CreateKeyspaceSpecification> createKeyspaceSpecifications = new ArrayList<>();
        CreateKeyspaceSpecification spec = CreateKeyspaceSpecification.createKeyspace(keyspaceName);
        spec.ifNotExists(true).withNetworkReplication(DataCenterReplication.of("dc1", 3));
        createKeyspaceSpecifications.add(spec);
        return createKeyspaceSpecifications;
    }
    

}
