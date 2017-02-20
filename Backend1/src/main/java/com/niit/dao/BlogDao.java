package com.niit.dao;

import java.util.List;

import com.niit.model.Blog;

public interface BlogDao {
	public void createBlog(Blog blog);
	public List<Blog>  getAllBlogs();
	public Blog getBlogDetails(int bid);
	/*public Boolean acceptBlog(int bid);
	public Boolean rejectBlog(int bid, String reason);*/
	public List<Blog> getMyBlogs(String user_id);
	public boolean updateBlog(Blog blog);
    public List<Blog>  getAllBlogs(char status);


}

