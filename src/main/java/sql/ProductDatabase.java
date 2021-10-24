package sql;

import product.Product;
import util.CheckedSqlFunction;

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
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        executeUpdate(sql);
    }

    public void dropTable() throws SQLException {
        String sql = "DROP TABLE PRODUCT";
        executeUpdate(sql);
    }

    public void addProduct(String name, long price) throws SQLException {
        String sql = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
        executeUpdate(sql);
    }

    public Product getMaxByPrice() throws SQLException {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";

        return executeQueryWithResultSet(sql, rs -> {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int price  = rs.getInt("price");
            return new Product(id, name, price);
        });
    }

    public Product getMinByPrice() throws SQLException {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";

        return executeQueryWithResultSet(sql, rs -> {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int price  = rs.getInt("price");
            return new Product(id, name, price);
        });
    }

    public int getCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM PRODUCT";

        return executeQueryWithResultSet(sql, rs -> rs.getInt(1));
    }

    public int getSum() throws SQLException {
        String sql = "SELECT SUM(price) FROM PRODUCT";

        return executeQueryWithResultSet(sql, rs -> rs.getInt(1));
    }

    public List<Product> getProducts() throws SQLException {
        String sql = "SELECT * FROM PRODUCT";

        return executeQueryWithResultSet(sql, rs -> {
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                products.add(new Product(id, name, price));
            }
            return products;
        });
    }

    public void executeUpdate(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + name)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public <T> T executeQueryWithResultSet(String sql, CheckedSqlFunction<ResultSet, T> function) throws SQLException {
        T result;
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + name)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            result = function.apply(rs);

            rs.close();
            stmt.close();
        }
        return result;
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
