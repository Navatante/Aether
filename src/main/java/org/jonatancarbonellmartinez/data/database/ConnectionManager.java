package org.jonatancarbonellmartinez.data.database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Usa ThreadLocal<Connection> para almacenar una conexión por hilo.
 * Administra conexiones activas, evitando abrir demasiadas conexiones.
 * Cierra conexiones cuando ya no son necesarias.
 */

@Singleton
public class ConnectionManager {
    private final Database database;
    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private final ConcurrentHashMap<Connection, Integer> connectionUsageCount = new ConcurrentHashMap<>();

    @Inject
    public ConnectionManager(Database database) {
        this.database = database;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn == null || conn.isClosed()) {
            conn = database.getConnection();
            connectionHolder.set(conn);
            connectionUsageCount.put(conn, 1);
        } else {
            connectionUsageCount.compute(conn, (k, v) -> v == null ? 1 : v + 1);
        }
        return conn;
    }

    public void releaseConnection() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            int count = connectionUsageCount.compute(conn, (k, v) -> v - 1);
            if (count == 0) {
                connectionUsageCount.remove(conn);
                conn.close();
                connectionHolder.remove();
            }
        }
    }
}
