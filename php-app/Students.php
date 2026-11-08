<?php

class Students
{
    private static function connect(): PDO
    {
        $host = "localhost";
        $port = "5432";
        $dbname = "tp";
        $user = "postgres";
        $password = "postgres";

        $dsn = "pgsql:host=$host;port=$port;dbname=$dbname";

        return new PDO($dsn, $user, $password, [
            PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
        ]);
    }

    public static function addStudent(string $name, float $grade): int
    {
        $sql = "INSERT INTO students (name, grade) VALUES (:name, :grade) RETURNING id";

        $conn = self::connect();
        $stmt = $conn->prepare($sql);

        $stmt->execute([
            ":name" => $name,
            ":grade" => $grade
        ]);

        return (int) $stmt->fetchColumn();
    }

    public static function getStudent(string $name): ?string
    {
        $sql = "SELECT name, grade FROM students
                WHERE name = :name
                ORDER BY id DESC
                LIMIT 1";

        $conn = self::connect();
        $stmt = $conn->prepare($sql);
        $stmt->execute([":name" => $name]);

        $student = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($student === false) {
            return null;
        }

        return $student["name"] . ":" . (float) $student["grade"];
    }

    public static function averageGrade(): float
    {
        $conn = self::connect();
        $stmt = $conn->query("SELECT AVG(grade) FROM students");

        $average = $stmt->fetchColumn();

        return $average === null ? 0.0 : (float) $average;
    }
}
