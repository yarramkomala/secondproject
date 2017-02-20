
package com.niit.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niit.dao.UserInfoDao;
import com.niit.model.UserInfo;
@Service
public class UserInfoDaoImpl implements UserInfoDao {
@Autowired
private SessionFactory sf;

public UserInfoDaoImpl(SessionFactory sf) {
	
	this.sf = sf;
}
@Transactional
	public UserInfo getuser(String user_id) {
		
		return (UserInfo) sf.getCurrentSession().get(UserInfo.class,user_id);
	}

@Transactional
	public List<UserInfo> list() {
		String hql="from UserInfo";
		
		 Query q=sf.getCurrentSession().createQuery(hql);
		 
		 return q.list();
	}
@Transactional
	public UserInfo validate(String id, String password) {
		String hql="from UserInfo where user_id='"+id+"'and password='" + password +"'";
		Query q=sf.getCurrentSession().createQuery(hql);
		 
		return (UserInfo) q.uniqueResult();
	}
@Transactional
	public void save(UserInfo userinfo) {
		try {
			sf.getCurrentSession().save(userinfo);
		} catch (HibernateException e) {
			
			e.printStackTrace();
		}
		
	}
@Transactional
	public UserInfo update(UserInfo userinfo){
		
		try {
			sf.getCurrentSession().update(userinfo);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return userinfo;
	}



}
