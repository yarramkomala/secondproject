package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import oracle.sql.CLOB;



@Entity
@Table(name="c_blog")
public class Blog extends BaseDomain {
public Blog(){
	
}
@Id
@GeneratedValue
private int bid;

private String user_id;
private String title;

private String description;
@Temporal(TemporalType.DATE)
private Date posted_date;
private String status;
private String reason;

private String email;
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public int getBid() {
	return bid;
}
public void setBid(int bid) {
	this.bid = bid;
}

public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

public Date getPosted_date() {
	return posted_date;
}
public void setPosted_date(Date posted_date) {
	this.posted_date = posted_date;
}

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}

}
