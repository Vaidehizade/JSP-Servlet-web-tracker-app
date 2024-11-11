package com.vaidehi.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;	

public class StudentDbUtil {
    private DataSource dataSource; //poolconnection
    public StudentDbUtil(DataSource theDataSource) {
    	dataSource = theDataSource;
    }
    public List<Student> getStudents() throws Exception {
    	List<Student>students = new ArrayList<>();
		Connection myConn = null;
		Statement myStm = null;
		ResultSet myRs = null;
    	
		try {
		// get a connection 
		myConn = dataSource.getConnection();
		// create sql statement
		String sql = "select * from student order by last_name";
		myStm = myConn.createStatement();
		myRs= myStm.executeQuery(sql);
		 while(myRs.next()) {
			 //retrieve data from result set row
			 int id = myRs.getInt("id");
			 String firstName = myRs.getString("first_name");
			 String lastName = myRs.getString("last_name");
			 String email = myRs.getString("email");
			 //create new student object
			 Student tempStudent = new Student(id,firstName, lastName, email);
			 //add it to list of Students
			 students.add(tempStudent);
		 }
		 return students;
       }finally {
    	   //close JDBC Objects
    	   close(myConn,myStm, myRs);
       }
}
	private void close(Connection myConn, Statement myStm, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myRs !=null) {
				myRs.close();
			}
			if(myStm !=null) {
				myStm.close();
			}
			if(myConn !=null) {
				myConn.close(); //just put back the connection in connection pull
			}
		}catch(Exception exc)
		{
			exc.printStackTrace();
		}
	}
	public void addStudent(Student theStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			//get db connection
			myConn = dataSource.getConnection();
			
			//create sql for insert
			String sql = "insert into student "
			             + "(first_name,last_name,email) "
					     + "values(?,?,?)";
			//set the param values for the student
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			//execute sql query
			myStmt.execute();
			
		} finally {
			//clean up JDBC Object
			close(myConn,myStmt,null);
			
		}
		
	}
	public Student getStudent(String theStudentId) throws Exception{
		// TODO Auto-generated method stub
		Student theStudent = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;
		try {
			//convert student id into int
			studentId= Integer.parseInt(theStudentId);
			//get connection to database 
			myConn = dataSource.getConnection();
			//create sql to get selected student
			String sql = "select * from student where id=?";
			//create prepare statement
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1,studentId);
			myRs= myStmt.executeQuery();
			//retrieve data from result set row
			if(myRs.next())
			{
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				theStudent = new Student(studentId,firstName,lastName,email);
			}
			else {
				throw new Exception("Could not find student id: "+ studentId);
			}
			return theStudent;
		 }finally {
			 close(myConn,myStmt,myRs);
		 }
	}
	public void updateStudent(Student theStudent) throws SQLException {
		// TODO Auto-generated method stub
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			//get db connection
			myConn = dataSource.getConnection();
			
			//create sql for insert
			String sql = "update student "
			             + "set first_name=?,last_name=?,email=? "
					     + "where id=?";
			//set the param values for the student
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getid());
			//execute sql query
			myStmt.execute();
			
		} finally {
			//clean up JDBC Object
			close(myConn,myStmt,null);
			
		}
	}
	public void deleteStudent(String theStudentId) throws SQLException{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			//get db connection
			myConn = dataSource.getConnection();
			int studentId = Integer.parseInt(theStudentId);
			//create sql for insert
			String sql = "delete from student where id=?";
			//set the param values for the student
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, studentId);
			//execute sql query
			myStmt.execute();
			
		} finally {
			//clean up JDBC Object
			close(myConn,myStmt,null);
		}
	}

}