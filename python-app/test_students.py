
import pytest
import psycopg2

from students import add_student, get_student, average_grade


@pytest.fixture
def conn():
    connection = psycopg2.connect(
        host="localhost",
        database="tp",
        user="postgres",
        password="postgres"
    )

    with connection.cursor() as cursor:
        cursor.execute("TRUNCATE TABLE students RESTART IDENTITY")

    connection.commit()

    yield connection

    connection.close()


def test_ajouter_et_relire(conn):
    add_student(conn, "Sara", 16)

    student = get_student(conn, "Sara")

    assert student is not None
    assert student["name"] == "Sara"
    assert student["grade"] == 16.0


def test_etudiant_inexistant(conn):
    student = get_student(conn, "Inconnu")

    assert student is None


def test_moyenne(conn):
    add_student(conn, "Amel", 10)
    add_student(conn, "Karim", 14)

    assert average_grade(conn) == 12.0
