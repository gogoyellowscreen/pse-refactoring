package command;

import sql.ProductDatabase;
import util.CheckedSqlRunnable;

import java.io.PrintWriter;
import java.sql.SQLException;

abstract public class Command {

    abstract public void printResult(PrintWriter writer);

    protected void wrapChecked(CheckedSqlRunnable runnable) {
        try {
            runnable.run();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Command fromNameAndDatabase(String name, ProductDatabase database) {
        switch (name) {
            case "max":
                return new MaxByPrice(database);
            case "min":
                return new MinByPrice(database);
            case "count":
                return new ProductsCount(database);
            case "sum":
                return new ProductsPriceSum(database);
            case "get":
                return new ProductsGet(database);
        }
        throw new IllegalArgumentException("No such command.");
    }
}
