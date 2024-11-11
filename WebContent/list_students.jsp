<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.vaidehi.web.jdbc.Student" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<!--<%
   //get the students from the request object (sent by servlet)
   List<Student> theStudents = (List<Student>) request.getAttribute("STUDENT_LIST");
%> -->

<body>
    <div id="wrapper">
      <div id="header">
      <h2>SPPU University</h2>
      </div>
    </div>
    <div id="container">
    <div id="content">
    
    <!-- new button -->
    <input type="button" value="Add Student" onclick="window.location.href='add-student-form.jsp'; return false;" class="add-student-button"/>
        <table>
        <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Action</th>
        </tr>
        <c:forEach var="tempStudent" items="${STUDENT_LIST}">
           <tr>
           <!-- setup a link for each student -->
           <c:url var="templink" value="StudentControllerServlet">
              <c:param name="command" value="LOAD" />
              <c:param name="studentId" value="${tempStudent.id}" />
           </c:url>
           <!--  setup a delete link for each student -->
           <c:url var="deletelink" value="StudentControllerServlet">
              <c:param name="command" value="DELETE" />
              <c:param name="studentId" value="${tempStudent.id}" />
           </c:url>
              <td> ${tempStudent.firstName} </td>
              <td> ${tempStudent.lastName} </td>
              <td> ${tempStudent.email} </td>
              <!-- will load student from database pre-populate the form -->
              <td><a href="${templink}">Update</a>
              |
              <a href="${deletelink}" onclick="if(!(confirm('Are you really want to delete this entry?'))) return false">Delete</a>
              </td>
           </tr>
        </c:forEach>
        </table>
    </div>
    </div>
</body>
</html>