package com.datastax.sample.entity;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

/**
 * This is a timeSeries table
 *
 * @author Cedrick LUNVEN (@clunven)
 */
@PrimaryKeyClass
public class TimeSerieDailyKey implements Serializable {

    /** Serial. */
    private static final long serialVersionUID = 1142109498800363080L;
    
    /**
     * Tick Data Partition Key
     */
    @PrimaryKeyColumn(name = "source", 
            ordinal = 0, 
            type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = Name.TEXT)
    private String source;
    
    /**
     * Tick Data Clustering Column
     */
    @PrimaryKeyColumn(name = "yyyymmdd", 
            ordinal = 1, 
            type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = Name.TEXT)
    private String yyyymmdd;
    
    /**
     * Tick Data Clustering Column
     */
    @PrimaryKeyColumn(name = "tick", ordinal = 0, 
            type = PrimaryKeyType.CLUSTERED, 
            ordering = Ordering.DESCENDING)
    @CassandraType(type = Name.TIMESTAMP)
    private String tick;

    /**
     * Getter accessor for attribute 'source'.
     *
     * @return
     *       current value of 'source'
     */
    public String getSource() {
        return source;
    }

    /**
     * Setter accessor for attribute 'source'.
     * @param source
     * 		new value for 'source '
     */
    public void setSource(String source) {
        this.source = source;
    }
    

    /**
     * Getter accessor for attribute 'yyymmdd'.
     *
     * @return
     *       current value of 'yyymmdd'
     */
    public String getYyyymmdd() {
        return yyyymmdd;
    }

    /**
     * Setter accessor for attribute 'yyymmdd'.
     * @param yyymmdd
     * 		new value for 'yyymmdd '
     */
    public void setYyyymmdd(String yyymmdd) {
        this.yyyymmdd = yyymmdd;
    }

    /**
     * Getter accessor for attribute 'serialversionuid'.
     *
     * @return
     *       current value of 'serialversionuid'
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
}
