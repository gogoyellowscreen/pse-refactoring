package sql;

import product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    public static final String TEST_DB_NAME = "test.db";

    private final String name;

    private ProductDatabase(String name) {
        this.name = name;
    }

    public void createIfNotExist() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + name)) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public void dropTable() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + name)) {
            String sql = "DROP TABLE PRODUCT";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public List<Product> getProducts() throws SQLException {
        List<Product> result = new ArrayList<>();
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + name)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price  = rs.getInt("price");
                result.add(new Product(id, name, price));
            }

            rs.close();
            stmt.close();
        }
        return result;
    }

    public void executeUpdate(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + name)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public static ProductDatabase fromName(String name) {
        if (name.equals(TEST_DB_NAME)) {
            throw new IllegalArgumentException("DB name should differs from db name used for testing.");
        }

        return new ProductDatabase(name);
    }

    public static ProductDatabase forTesting() {
        return new ProductDatabase(TEST_DB_NAME);
    }
}
