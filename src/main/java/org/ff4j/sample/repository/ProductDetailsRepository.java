package org.ff4j.sample.repository;

import java.util.List;

import org.ff4j.sample.entity.TickData;
import org.ff4j.sample.entity.TickDataKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * Find by Symbol.
 *
 * @author Cedrick LUNVEN (@clunven)
 */
public interface ProductDetailsRepository extends CassandraRepository<TickData, TickDataKey> { 
    
    List<TickData> findByKeySymbol(String symbol);
}
