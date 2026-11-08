<?php

use PHPUnit\Framework\TestCase;

require_once __DIR__ . "/Students.php";

class StudentsTest extends TestCase
{
    public function testAddStudent(): void
    {
        $name = "TestPHP_" . uniqid();
        $id = Students::addStudent($name, 16.5);

        $this->assertIsInt($id);
        $this->assertGreaterThan(0, $id);
    }

    public function testGetStudent(): void
    {
        $name = "TestPHP_" . uniqid();
        Students::addStudent($name, 14.5);

        $student = Students::getStudent($name);

        $this->assertSame($name . ":14.5", $student);
    }

    public function testGetStudentNotFound(): void
    {
        $student = Students::getStudent("Etudiant_Inexistant_123456");

        $this->assertNull($student);
    }

    public function testAverageGrade(): void
    {
        $average = Students::averageGrade();

        $this->assertIsFloat($average);
        $this->assertGreaterThan(0, $average);
    }
}
