package com.niit.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niit.dao.BlogDao;
import com.niit.model.Blog;
@Service
public class BlogDaoImpl implements BlogDao {
	@Autowired(required=true)
	SessionFactory sf;
	
	@Transactional
	public void createBlog(Blog blog) {
		
	 sf.getCurrentSession().save(blog);
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.niit.dao.BlogDao#getAllBlogs()
	 *  it will return all the  blogs
	 *  if we want approved blogs we change condition
	 */
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Blog> getAllBlogs() {
		String hql="from Blog where status='"+1+"'";
		 Query q=sf.getCurrentSession().createQuery(hql);
		return q.list();
	}
	/*
	 * (non-Javadoc)
	 * @see com.niit.dao.BlogDao#getBlogDetails(int)
	 * 
	 */
	
	@Transactional
	public Blog getBlogDetails(int bid) {
		/*String q="from Blog where bid='"+bid+"'";
		Query hql=sf.getCurrentSession().createQuery(q);  (Blog) hql.uniqueResult()*/
		
		return (Blog) sf.getCurrentSession().get(Blog.class,bid);
	}
    /*@Transactional
	public Boolean acceptBlog(int bid) {
		
		return null;
	}
     @Transactional
	public Boolean rejectBlog(int bid, String reason) {
		
		return null;
	}*/
	
	/*
	 * (non-Javadoc)
	 * @see com.niit.dao.BlogDao#getMyBlogs(java.lang.String)
	 *it will return all the blogs written by particular user
	 */
	@Transactional
	public List<Blog> getMyBlogs(String user_id) {
		String hql="from Blog where user_id='"+user_id+"'";
		Query q=sf.getCurrentSession().createQuery(hql);
		return q.list();
	}
	@Transactional
	public boolean updateBlog(Blog blog) {
		
		try {
			sf.getCurrentSession().update(blog);
			
			return true;
		} catch (
				Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		}
		
	
		
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Blog> getAllBlogs(char status) {
		String q="from Blog where status='"+true+"'";
		Query hql=sf.getCurrentSession().createQuery(q);
		
		return hql.list();
	}
	
	

}
