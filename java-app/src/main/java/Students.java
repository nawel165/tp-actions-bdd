import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Students {

    private static final String URL = "jdbc:postgresql://localhost:5432/tp";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static int addStudent(String name, double grade) throws Exception {
        String sql = "INSERT INTO students (name, grade) VALUES (?, ?) RETURNING id";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setDouble(2, grade);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt("id");
            }
        }
    }

    public static String getStudent(String name) throws Exception {
        String sql = "SELECT name, grade FROM students WHERE name = ? ORDER BY id DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name") + ":" + rs.getDouble("grade");
                }
                return null;
            }
        }
    }

    public static double averageGrade() throws Exception {
        String sql = "SELECT AVG(grade) AS average FROM students";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            rs.next();
            return rs.getDouble("average");
        }
    }
}