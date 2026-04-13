import java.sql.*;
public class InspectSchema {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://crossover.proxy.rlwy.net:43763/railway?sslmode=require";
        String user = "postgres";
        String password = "QNleCWVdSsxTybKKzwcWBOgFZuyhMNbf";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            printColumns(conn, "contratos");
            printColumns(conn, "clientes");
            printColumns(conn, "planes");
            printColumns(conn, "vendedores");
            printColumns(conn, "facturas");
            printColumns(conn, "usuarios");
        }
    }
    private static void printColumns(Connection conn, String table) throws SQLException {
        System.out.print(table + ":");
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT column_name FROM information_schema.columns WHERE table_name = ? ORDER BY ordinal_position")) {
            stmt.setString(1, table);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean first = true;
                while (rs.next()) {
                    if (!first) System.out.print(",");
                    first = false;
                    System.out.print(rs.getString(1));
                }
            }
        }
        System.out.println();
    }
}
