package com.datastax.sample.entity;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Entity for table Orders.
 *
 * @author Cedrick LUNVEN (@clunven)
 */
@Table(value = "orders")
public class Order implements Serializable {

    /** Serial. */
    private static final long serialVersionUID = 363796610992902431L;
    
    @Column("id")
    @CassandraType(type = Name.UUID)
    private UUID id;
    
    @Column("address")
    @CassandraType(type = Name.TEXT)
    private String address;
    
    @Column("prod_id")
    @CassandraType(type = Name.UUID)
    private UUID productId;
    
    @Column("prod_name")
    @CassandraType(type = Name.TEXT)
    private UUID productName;
    
    @Column("description")
    @CassandraType(type = Name.TEXT)
    private String description;
    
    @Column("price")
    @CassandraType(type = Name.DECIMAL)
    private Double price;
    
    @Column("sell_price")
    @CassandraType(type = Name.DECIMAL)
    private Double sellingPrice;
    
    @PrimaryKey("customer_name")
    @CassandraType(type = Name.TEXT)
    private String customerName;

    /**
     * Getter accessor for attribute 'id'.
     *
     * @return
     *       current value of 'id'
     */
    public UUID getId() {
        return id;
    }

    /**
     * Setter accessor for attribute 'id'.
     * @param id
     * 		new value for 'id '
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Getter accessor for attribute 'address'.
     *
     * @return
     *       current value of 'address'
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter accessor for attribute 'address'.
     * @param address
     * 		new value for 'address '
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter accessor for attribute 'productId'.
     *
     * @return
     *       current value of 'productId'
     */
    public UUID getProductId() {
        return productId;
    }

    /**
     * Setter accessor for attribute 'productId'.
     * @param productId
     * 		new value for 'productId '
     */
    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    /**
     * Getter accessor for attribute 'productName'.
     *
     * @return
     *       current value of 'productName'
     */
    public UUID getProductName() {
        return productName;
    }

    /**
     * Setter accessor for attribute 'productName'.
     * @param productName
     * 		new value for 'productName '
     */
    public void setProductName(UUID productName) {
        this.productName = productName;
    }

    /**
     * Getter accessor for attribute 'description'.
     *
     * @return
     *       current value of 'description'
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter accessor for attribute 'description'.
     * @param description
     * 		new value for 'description '
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter accessor for attribute 'price'.
     *
     * @return
     *       current value of 'price'
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Setter accessor for attribute 'price'.
     * @param price
     * 		new value for 'price '
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Getter accessor for attribute 'sellingPrice'.
     *
     * @return
     *       current value of 'sellingPrice'
     */
    public Double getSellingPrice() {
        return sellingPrice;
    }

    /**
     * Setter accessor for attribute 'sellingPrice'.
     * @param sellingPrice
     * 		new value for 'sellingPrice '
     */
    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * Getter accessor for attribute 'customerName'.
     *
     * @return
     *       current value of 'customerName'
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter accessor for attribute 'customerName'.
     * @param customerName
     * 		new value for 'customerName '
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
