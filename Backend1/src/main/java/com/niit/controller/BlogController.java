package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BlogDao;
import com.niit.model.Blog;

@RestController
public class BlogController {
	
	@Autowired
	BlogDao bd;
	@Autowired
	HttpSession session;
	
	
//fetching a blog details from table
	@GetMapping("/blog/{bid}")
	public ResponseEntity<Blog> getblogInfo(@PathVariable("bid") int bid) {

		Blog b = bd.getBlogDetails(bid);
		if (b== null) {
			/*return new ResponseEntity("No blogInfo found for ID " + bid, HttpStatus.NOT_FOUND);*/
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Blog>(b, HttpStatus.OK);
	}

	

	/*@DeleteMapping("/blogInfos/{id}")
	public ResponseEntity deleteblogInfo(@PathVariable Long id) {

		if (null == ud.(id)) {
			return new ResponseEntity("No blogInfo found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(id, HttpStatus.OK);

	}*/

	//update blog details
	@PutMapping("/blog/{bid}")
	public ResponseEntity<Blog> updateblogInfo(@PathVariable int  bid, @RequestBody Blog blog) {

		Blog b = bd.getBlogDetails(bid);
        
        if (b==null) {
            System.out.println("blog with id " + bid + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
  
        b.setTitle(blog.getTitle());
       b.setEmail(blog.getEmail());;
       b.setDescription(blog.getDescription());
       b.setReason(blog.getReason());
       b.setPosted_date(blog.getPosted_date());
        
          
        bd.updateBlog(b);
        return new ResponseEntity<Blog>(b, HttpStatus.OK);
	}
	
	//saving into blog
	@PostMapping("/blog")
	public Blog createblog(@RequestBody Blog blog){

		String loggedInUserID = (String) session.getAttribute("loggedInUserId");
	/*	logger.debug(" Blog is creating by the blog :"+loggedInUserID);*/
		if(loggedInUserID==null){
			blog.setErrorCode("200");
			blog.setErrorCode("you have to login to create a blog");
			return blog;
		}
		blog.setUser_id(loggedInUserID);
		blog.setStatus("N");// A->Accepted,  R->Rejected
		blog.setPosted_date(new Date());
		
		
		if(bd.getBlogDetails(blog.getBid())==null){
			bd.createBlog(blog);
			blog.setErrorCode("200");
			blog.setErrorMessage("you successfully created a blog");
		}	
		else{
			blog.setErrorCode("404");
			blog.setErrorMessage("blog exist with this id:"+blog.getBid());
		}
		return blog;
	}
	
	@GetMapping("/getallblog")
	public List<Blog> getAllBlogs(){
		return bd.getAllBlogs();
	}
	
	//approving blogs
	@PutMapping("/approve/{bid}")
public Blog approveBlog(@PathVariable("bid") int blogid,Blog blog){
		//get the blog based on blog id
		//set the status as 0
		//call update method
		if (session.getAttribute("loggedInUserId") == null)
		{
			blog.setErrorMessage("Please login to approve the blog");
			blog.setErrorCode("404");
			return blog;
		}
		
		if (! session.getAttribute("role").equals("ROLE_ADMIN"))
		{
			blog.setErrorMessage("You are not authorized to approve the blog");
			blog.setErrorCode("404");
			return blog;
		}
		
		blog=bd.getBlogDetails(blogid);
		if(blog==null){
			blog=new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("no blog exixst with this id"+blogid);
			
		}
		if(blog.getStatus().equals("A")){
			blog.setErrorCode("200");
			blog.setErrorMessage("this blog is already approved"+blogid);
			return blog;
		}
			
		blog.setStatus("A");
		if(bd.updateBlog(blog))
				{
			blog.setErrorCode("200");
			blog.setErrorMessage("approved");
				}
		else{
			blog.setErrorCode("404");
			blog.setErrorMessage("not able to approve");
		}
		return blog;		
	}
	@PutMapping("/rejectblog/{id}/(reason)")
	public Blog rejectBlog(@PathVariable int id,@PathVariable String reason,Blog blog){
		
		
		if (session.getAttribute("loggedInUserId") == null)
		{
			blog.setErrorMessage("Please login to approve the blog");
			blog.setErrorCode("404");
			return blog;
		}
		
		if (! session.getAttribute("role").equals("ROLE_ADMIN"))
		{
			blog.setErrorMessage("You are not authorized to approve the blog");
			blog.setErrorCode("404");
			return blog;
		}
		
		blog=bd.getBlogDetails(id);
		if(blog==null){
			blog=new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("no blog exixst with this id"+id);
			return blog;
		}
		if(blog.getStatus().equals("A")){
			blog.setErrorCode("200");
			blog.setErrorMessage("this blog is already approved"+id);
			return blog;
		}
			
		blog.setStatus("A");
		if(bd.updateBlog(blog))
				{
			blog.setErrorCode("200");
			blog.setErrorMessage("approved");
				}
		else{
			blog.setErrorCode("404");
			blog.setErrorMessage("not able to approve");
		}
		return blog;		
	}
}
