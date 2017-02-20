package com.niit.dao;

import java.util.List;

import com.niit.model.UserInfo;

public interface UserInfoDao {
	
	public UserInfo getuser(String user_id);
	public List<UserInfo> list();
	public UserInfo validate(String id,String password);
	public void save(UserInfo userinfo);
	public UserInfo update(UserInfo userinfo);
}
