
const { Pool } = require("pg");

const pool = new Pool({
    host: "localhost",
    database: "tp",
    user: "postgres",
    password: "postgres",
    port: 5432
});

async function addStudent(name, grade) {
    const result = await pool.query(
        "INSERT INTO students (name, grade) VALUES ($1, $2) RETURNING id",
        [name, grade]
    );

    return result.rows[0].id;
}

async function getStudent(name) {
    const result = await pool.query(
        "SELECT id, name, grade FROM students WHERE name = $1 ORDER BY id DESC LIMIT 1",
        [name]
    );

    if (result.rows.length === 0) {
        return null;
    }

    return {
        id: result.rows[0].id,
        name: result.rows[0].name,
        grade: Number(result.rows[0].grade)
    };
}

async function averageGrade() {
    const result = await pool.query(
        "SELECT AVG(grade) AS average FROM students"
    );

    if (result.rows[0].average === null) {
        return 0;
    }

    return Number(result.rows[0].average);
}

module.exports = {
    pool,
    addStudent,
    getStudent,
    averageGrade
};
