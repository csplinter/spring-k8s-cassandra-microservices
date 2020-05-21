package com.datastax.sample.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.datastax.sample.entity.TimeSerieDaily;
import com.datastax.sample.entity.TimeSerieDailyKey;

/**
 * Find by Symbol.
 *
 * @author Cedrick LUNVEN (@clunven)
 */
public interface TickDataRepository extends CrudRepository<TimeSerieDaily, TimeSerieDailyKey> { 
    
    List<TimeSerieDaily> findByTickDataKeySourceAndTickDataKeyYyyymmdd(String symbol, String valueDate);
}
