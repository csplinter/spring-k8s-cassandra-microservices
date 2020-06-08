package com.datastax.examples.dao;

import com.datastax.examples.model.Product;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ProductDao {
    private PreparedStatement insertProduct;
    private PreparedStatement selectProductByName;
    private PreparedStatement selectProductByNameAndId;
    private PreparedStatement deleteProductByName;
    private PreparedStatement deleteProductByNameAndId;
    private CqlSession session;

    private static final String productsTableName = "products";

    private static final String createProductsTable = "" +
            "CREATE TABLE IF NOT EXISTS " + productsTableName + " (" +
            "name text, " +
            "id uuid, " +
            "description text, " +
            "price decimal, " +
            "last_updated timestamp, " +
            "PRIMARY KEY ((name), id));";


    private static final String insertProductStmt = "" +
            "INSERT INTO " + productsTableName + " (name, id, description, price, last_updated) VALUES (?,?,?,?,toTimestamp(now()));";

    private static final String selectProductByNameStmt = "" +
            "SELECT id, description, price, last_updated FROM " + productsTableName + " WHERE name=?;";

    private static final String selectProductByNameAndIdStmt = "" +
            "SELECT description, price, last_updated FROM " + productsTableName + " WHERE name=? AND id=?;";

    private static final String deleteProductByNameStmt = "" +
            "DELETE FROM " + productsTableName + " WHERE name=?;";

    private static final String deleteProductByNameAndIdStmt = "" +
            "DELETE FROM " + productsTableName + " WHERE name=? AND id=?;";

    public ProductDao(CqlSession session){
        this.session = session;
        maybeCreateProductSchema();
        this.insertProduct = session.prepare(insertProductStmt);
        this.selectProductByName = session.prepare(selectProductByNameStmt);
        this.selectProductByNameAndId = session.prepare(selectProductByNameAndIdStmt);
        this.deleteProductByName = session.prepare(deleteProductByNameStmt);
        this.deleteProductByNameAndId = session.prepare(deleteProductByNameAndIdStmt);
    }

    private void maybeCreateProductSchema(){
        session.execute(createProductsTable);
    }

    public Iterable<Product> findByName(String name){
        ResultSet rs = session.execute(selectProductByName.bind(name));
        List<Product> products = new ArrayList<>(rs.getAvailableWithoutFetching());
        for (Row row : rs){
            products.add(new Product(
                    name,
                    row.getUuid("id"),
                    row.getString("description"),
                    row.getBigDecimal("price"),
                    row.getInstant("last_updated")));
        }
        return products;
    }

    public Product findByNameAndId(String name, UUID id){
        ResultSet rs = session.execute(selectProductByNameAndId.bind(name, id));
        Row row = rs.one();
        return new Product(
                name, id,
                row.getString("description"),
                row.getBigDecimal("price"),
                row.getInstant("last_updated"));
    }

    public void addProduct(Product product){
        session.execute(insertProduct
                .bind(product.getName(), product.getId(), product.getDescription(), product.getPrice()));
    }

    public void deleteByName(String name){
        session.execute(deleteProductByName.bind(name));
    }

    public void deleteByNameAndId(String name, UUID id){
        session.execute(deleteProductByNameAndId.bind(name, id));
    }
}
