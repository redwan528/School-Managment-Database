package sba.sms.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import sba.sms.dao.CourseI;
import sba.sms.models.Course;

public class CourseService implements CourseI {

	@Override
	public void createCourse(Course course) {
		// TODO Auto-generated method stub
		SessionFactory sf = new Configuration().configure().buildSessionFactory(); // connects to database using //																				// hibernate
		Session s = sf.openSession();
		Transaction transaction = s.beginTransaction();

		try {

			s.persist(course);
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback(); //

			e.printStackTrace();
		} finally {
			// Close session and session factory
			if (s != null)
				s.close();

			if (sf != null)
				sf.close();

		}

	}

	@Override
	public Course getCourseById(int courseId) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory(); // connects to database using
		// hibernate
		Session s = sf.openSession();
		Transaction transaction = s.beginTransaction();
		Course c = null;
		try {

			// retrieve course by its ID
			c = s.get(Course.class, courseId);
			// Commit the transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			// Close session and session factory
			if (s != null) {
				s.close();
			}
			if (sf != null) {
				sf.close();
			}
		}

		return c;
	}

	@Override
	public List<Course> getAllCourses() {
		SessionFactory sf = null; // connects to database using hibernate
		Session s = null;

		List<Course> coursesList = new ArrayList<>();

		try {
			sf = new Configuration().configure().buildSessionFactory();
			s = sf.openSession();

			coursesList = s.createQuery("FROM Course", Course.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
			if (sf != null)
				sf.close();
		}

		return coursesList;
	}

}
