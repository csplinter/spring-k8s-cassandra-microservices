package com.datastax.sample.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.datastax.sample.entity.TimeserieDaily;
import com.datastax.sample.entity.TimeserieDailyKey;

/**
 * Find by Symbol.
 *
 * @author Cedrick LUNVEN (@clunven)
 */
public interface TimeseriesRepository extends CrudRepository<TimeserieDaily, TimeserieDailyKey> { 
    
    DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyymmdd");
    
    /**
     * I want to die
     */
    List<TimeserieDaily> findByTimeserieDailyKeySourceAndTimeserieDailyKeyYyyymmdd(String symbol, String valueDate);
    
    /**
     * Get the serie for today, no paging.
     */
    default List<TimeserieDaily> findTimeSeriesToday(String source) {
        return findByTimeserieDailyKeySourceAndTimeserieDailyKeyYyyymmdd(source, YYYYMMDD.format(LocalDate.now()));
    }
}
