package util;

import java.sql.SQLException;

@FunctionalInterface
public interface CheckedSqlRunnable {
    void run() throws SQLException;
}
