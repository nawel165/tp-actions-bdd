//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class StudentsTest {

    private static final String URL = "jdbc:postgresql://localhost:5432/tp";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    @BeforeEach
    void setUp() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE students RESTART IDENTITY");
        }
    }

    @Test
    void testAddAndGetStudent() throws Exception {
        Students.addStudent("Sara", 16);

        String student = Students.getStudent("Sara");

        assertEquals("Sara:16.0", student);
    }

    @Test
    void testGetUnknownStudent() throws Exception {
        assertNull(Students.getStudent("Inconnu"));
    }

    @Test
    void testAverageGrade() throws Exception {
        Students.addStudent("Amel", 10);
        Students.addStudent("Karim", 14);

        assertEquals(12.0, Students.averageGrade(), 0.001);
    }
}