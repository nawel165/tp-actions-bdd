
def add_student(conn, name, grade):
    """Ajouter un étudiant dans la base de données."""
    with conn.cursor() as cursor:
        cursor.execute(
            "INSERT INTO students (name, grade) VALUES (%s, %s) RETURNING id",
            (name, grade)
        )
        student_id = cursor.fetchone()[0]

    conn.commit()
    return student_id


def get_student(conn, name):
    """Rechercher un étudiant par son nom."""
    with conn.cursor() as cursor:
        cursor.execute(
            "SELECT id, name, grade FROM students WHERE name = %s ORDER BY id DESC LIMIT 1",
            (name,)
        )
        student = cursor.fetchone()

    if student is None:
        return None

    return {
        "id": student[0],
        "name": student[1],
        "grade": float(student[2])
    }


def average_grade(conn):
    """Calculer la moyenne des notes."""
    raise NotImplementedError("La fonction average_grade n'est pas encore implementee")
