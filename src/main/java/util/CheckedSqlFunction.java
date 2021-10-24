package util;

import java.sql.SQLException;

@FunctionalInterface
public interface CheckedSqlFunction<T, R> {
    R apply(T t) throws SQLException;
}
