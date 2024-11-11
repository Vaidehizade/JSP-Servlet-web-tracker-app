package com.vaidehi.web.jdbc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private StudentDbUtil studentDbUtil;
    
    @Resource(name="jdbc/web_student_tracker")
    private DataSource dataSource;
    @Override
    public void init() throws ServletException {
    	super.init();
    	//create our student db util....and pass in the connection pool / data source
    	try {
    		studentDbUtil = new StudentDbUtil(dataSource);
    	}
    	catch(Exception exc) {
    		throw new ServletException(exc);
    	}
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
    	//list all the students in MVC fashion
    	try {
    		
    		//read the command parameter
    		String theCommand = request.getParameter("command");
    		//If the command is missing then default to listing students
    		if(theCommand == null)
    		{
    			theCommand = "LIST";
    		}
    		
    		//route to the appropriate method
    		switch (theCommand) {
    		case "LIST":
    			listStudents(request,response);
    			break;
    		case "ADD":
    		    addStudents(request,response);
    			break;
    		case "LOAD":
    			loadStudents(request,response);
    		case "DELETE":
    			deleteStudents(request,response);
    			break;
    		case "UPDATE":
    			updateStudents(request,response);
    			break;
    		default:
    			listStudents(request,response);
    		}
    	}catch(Exception exc)
    	{
    		throw new ServletException(exc);
    	}
    }

	private void deleteStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read data from form
		String theStudentId = request.getParameter("studentId");

		//delete student from database
		studentDbUtil.deleteStudent(theStudentId);
		//send them back to "listStudents"
		listStudents(request,response);
	}

	private void updateStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//read the student id from form data
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		//create a new student object
		Student theStudent = new Student(id,firstName, lastName, email);
		//perform update on database
		studentDbUtil.updateStudent(theStudent);
		//send them back to "list students" page
		listStudents(request,response);
		
	}

	private void loadStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read the student id from form data
		String theStudentId = request.getParameter("studentId");
		//get the student from database (db util)
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		//place student in the request attribute
		request.setAttribute("THE_STUDENT",theStudent);
		//send to jsp page - update-student-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request,response);
		
	}

	private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read the student from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create a new student object
		Student theStudent = new Student(firstName,lastName,email);
		//add a new student to the database
		studentDbUtil.addStudent(theStudent);
		//send back to main page (the students list)
		listStudents(request,response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//get students from db util
		List<Student> students = studentDbUtil.getStudents();
		//add students to the request
		request.setAttribute("STUDENT_LIST", students);
		//send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_students.jsp");
		dispatcher.forward(request,response);
	}
}
