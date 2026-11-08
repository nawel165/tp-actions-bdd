
const {
    pool,
    addStudent,
    getStudent,
    averageGrade
} = require("./students");

beforeEach(async () => {
    await pool.query("TRUNCATE TABLE students RESTART IDENTITY");
});

afterAll(async () => {
    await pool.end();
});

test("Ajouter un étudiant puis le relire", async () => {
    await addStudent("Sara", 16);

    const student = await getStudent("Sara");

    expect(student).not.toBeNull();
    expect(student.name).toBe("Sara");
    expect(student.grade).toBe(16);
});

test("Lire un étudiant inexistant", async () => {
    const student = await getStudent("Inconnu");

    expect(student).toBeNull();
});

test("Calculer la moyenne des notes", async () => {
    await addStudent("Amel", 10);
    await addStudent("Karim", 14);

    expect(await averageGrade()).toBe(12);
});
