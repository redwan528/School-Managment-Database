package sba.sms.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import sba.sms.dao.StudentI;

import sba.sms.models.Course;
import sba.sms.models.Student;

public class StudentService implements StudentI {
//	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//	Session session = sessionFactory.openSession();
//	Transaction transaction = session.beginTransaction();

//	private Session openSession() {
//		 this.sessionFactory = new Configuration().configure().buildSessionFactory();
//		 
//		return this.sessionFactory.openSession();
//
//	}

//	public void rollbackAndPrintStackTrace(Transaction transaction, Exception exception) {
//		this.transaction = transaction;
//
//		if (transaction != null && transaction.isActive()) {
//			transaction.rollback();
//		}
//		exception.printStackTrace();
//	}

	@Override
	public List<Student> getAllStudents() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); // connects to database
																								// using hibernate
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		List<Student> students = new ArrayList<>();

		try /* (Session session = openSession()) */ {

			students = session.createQuery("FROM Student", Student.class).list();
		} catch (Exception e) {
//			rollbackAndPrintStackTrace(transaction, e);
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();

		} finally {
			session.close();
			sessionFactory.close();
		}
		return students;
	}

	@Override
	public void createStudent(Student student) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); // connects to database
		// using hibernate
Session session = sessionFactory.openSession();
Transaction transaction = session.beginTransaction();
		// TODO Auto-generated method stub
		try {
			session.persist(student);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		}

	}

	@Override
	public Student getStudentByEmail(String email) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); // connects to database
		// using hibernate
Session session = sessionFactory.openSession();
Transaction transaction = session.beginTransaction();
		// TODO Auto-generated method stub
	
	Student studentByEmail = new Student();
		
		try {
			studentByEmail = session.createQuery("From Student WHERE email = :email", Student.class)
					.setParameter("email", email).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return studentByEmail;
	}

	@Override
	public boolean validateStudent(String email, String password) {

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); // connects to database
		// using hibernate
Session session = sessionFactory.openSession();
Transaction transaction = session.beginTransaction();

		try {
			Student student = new Student();
			student = session.createQuery("From Student where email = :email and password = :password", Student.class)
					.setParameter("email", email).setParameter("password", password).uniqueResult();
			if (student != null)
				return true;
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return false;
	}

	@Override
	public void registerStudentToCourse(String email, int courseId) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); // connects to database
		// using hibernate
Session session = sessionFactory.openSession();
Transaction transaction = session.beginTransaction();
		try {
			// Retrieve the Volunteer by email
			Student student = session.createQuery("FROM Student WHERE email = :email", Student.class)
					.setParameter("email", email).uniqueResult();
			// Retrieve the Activity by ID
			Course course = session.get(Course.class, courseId);
			if (student != null && course != null) {
				// Register the Activity to the Volunteer (preventing duplication)
				if (!student.getCourses().contains(course)) {
					student.getCourses().add(course);
				}
			}
			// Commit the transaction
			transaction.commit();
		} catch (Exception e) {
			// Handle exceptions
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	@Override
	public List<Course> getStudentCourses(String email) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); // connects to database
		// using hibernate
Session session = sessionFactory.openSession();
Transaction transaction = session.beginTransaction();
		List<Course> coursesList = new ArrayList<>();
		try {
			// Perform the database query to retrieve the Volunteer activities using a
			// native query
			String nativeQuery = "SELECT c.* FROM Course c " +
        "INNER JOIN student_courses sc ON c.id = sc.courses_id " +
        "INNER JOIN Student s ON sc.student_email=s.email " +
        "WHERE s.email = :email";
			coursesList = session.createNativeQuery(nativeQuery, Course.class).setParameter("email", email).list();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return coursesList;
	}

}
