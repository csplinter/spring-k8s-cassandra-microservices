package com.datastax.sample.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.datastax.sample.entity.TickData;
import com.datastax.sample.entity.TickDataKey;

/**
 * Find by Symbol.
 *
 * @author Cedrick LUNVEN (@clunven)
 */
public interface TickDataRepository extends CrudRepository<TickData, TickDataKey> { 
    
    List<TickData> findByTickDataKeySymbol(String symbol);
}
