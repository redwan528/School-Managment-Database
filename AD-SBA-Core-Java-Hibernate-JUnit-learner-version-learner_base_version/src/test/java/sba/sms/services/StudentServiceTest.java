package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

	static StudentService studentService;
	static CourseService courseService;

	@BeforeAll
	static void beforeAll() {
		studentService = new StudentService();
		courseService = new CourseService();
		CommandLine.addData();
	}

	@Test
	void testGetAllStudents() {

		List<Student> expected = new ArrayList<>(Arrays.asList(

				new Student("annette@gmail.com", "annette allen", "password"),
				new Student("anthony@gmail.com", "anthony gallegos", "password"),
				new Student("ariadna@gmail.com", "ariadna ramirez", "password"),
				new Student("bolaji@gmail.com", "bolaji saibu", "password"),
				new Student("reema@gmail.com", "reema brown", "password")));

		List<Student> actual = studentService.getAllStudents();

		// assertEquals(expected.size(), actual.size());

		for (int i = 0; i < expected.size(); i++) {

			Student expectedStudent = expected.get(i);
			Student actualStudent = actual.get(i);

			assertEquals(expectedStudent.getEmail(), actualStudent.getEmail());
			assertEquals(expectedStudent.getName(), actualStudent.getName());
			assertEquals(expectedStudent.getPassword(), actualStudent.getPassword());
		}

		// assertThat(studentService.getAllStudents()).hasSameElementsAs(expected);

	}

	@Test
	void testGetAllCourses() {
		List<Course> expectedCourses = new ArrayList<>(Arrays.asList(

				new Course("Java", "Phillip Witkin"), new Course("Frontend", "Kasper Kain"),
				new Course("JPA", "Jafer Alhaboubi"), new Course("Spring Framework", "Phillip Witkin"),
				new Course("SQL", "Phillip Witkin")

		));
		List<Course> actualCourses = courseService.getAllCourses();

		for (int i = 0; i < expectedCourses.size(); i++) {

			Course expectedCourse = expectedCourses.get(i);
			Course actualCourse = actualCourses.get(i);

			assertEquals(expectedCourse.getInstructor(), actualCourse.getInstructor());
			assertEquals(expectedCourse.getName(), actualCourse.getName());

		}

		// assertThat(courseService.getAllCourses()).hasSameElementsAs(expected);
	}
}