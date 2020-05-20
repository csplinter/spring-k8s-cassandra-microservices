package org.ff4j.sample.repository;

import java.util.List;

import org.ff4j.sample.entity.TickData;
import org.ff4j.sample.entity.TickDataKey;
import org.springframework.data.repository.CrudRepository;

/**
 * Find by Symbol.
 *
 * @author Cedrick LUNVEN (@clunven)
 */
public interface TickDataRepository { 
    
    List<TickData> findByTickDataKeySymbol(String symbol);
}
