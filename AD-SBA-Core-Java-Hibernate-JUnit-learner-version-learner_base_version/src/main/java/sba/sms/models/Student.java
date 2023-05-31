package sba.sms.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "student")
public class Student {
	@Id // gives email primary key
	@Column(name = "email", nullable = false, length = 50, unique = true)
	private String email;
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	@Column(name = "password", nullable = false, length = 50)
	private String password;

//	 @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//	    @JoinTable(
//	        name = "student_courses",
//	        joinColumns = @JoinColumn(name = "FK_student_email"/*, referencedColumnName = "email"*/),
//	        inverseJoinColumns = @JoinColumn(name = "courses_id"/*, referencedColumnName = "id"*/),
//	        inverseForeignKey = @ForeignKey(name = "Fk_courses_id")
//	        
//	        
//	    )
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_email"), inverseJoinColumns = @JoinColumn(name = "courses_id"))

	private List<Course> courses;

	public Student() {
	} // empty constructor

	public Student(String email, String name, String password, List<Course> courses) {

		this.email = email;
		this.name = name;
		this.password = password;
		this.courses = courses;

	}

	public Student(String email, String name, String password) {

		this.email = email;
		this.name = name;
		this.password = password;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Student [email=" + email + ", name=" + name + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(courses, email, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(courses, other.courses) && Objects.equals(email, other.email)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password);
	}

	// intelij's generator for equals and hashcode()
//	public boolean equals(Object object) {
//		if (this == object)
//			return true;
//		if (object == null || getClass() != object.getClass())
//			return false;
//		if (!super.equals(object))
//			return false;
//		Student student = (Student) object;
//		return email.equals(student.email) && name.equals(student.name) && password.equals(student.password)
//				&& courses.equals(student.courses);
//	}
//
//	public int hashCode() {
//		return java.util.Objects.hash(super.hashCode(), email, name, password, courses);
//	}

}
