package com.vaidehi.web.jdbc;

public class Student {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	
	public Student(String firstName, String lastName, String email){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public Student(int id, String firstName, String lastName, String email){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public int getid() {
		return id;
	}
    public String getFirstName() {
    	return firstName;
    }
	public String getLastName() {
    	return lastName;
    }
    public String getEmail() {
    	return email;
    }
    public void setid(int id) {
    	this.id = id;
    }
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }
    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }
    public void setEmail(String email) {
    	this.email = email;
    }
    @Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
    
}
