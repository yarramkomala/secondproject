package com.niit.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.UserInfoDao;
import com.niit.model.UserInfo;

@RestController
public class Usercontroller {
	@Autowired(required=true)
	private UserInfoDao ud;
	@Autowired
	private HttpSession session;

	@RequestMapping("/")
	public String home(){
		return "index of home page";
	}
	@GetMapping("/hello")
	public String homes(){
		return "index";
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/getallusers")
public ResponseEntity<List<UserInfo>> list(UserInfo ui){
	List user= ud.list();
	if(user.isEmpty()){
		ui.setErrorCode("100");
		ui.setErrorMessage("no users are available");
		ud.save(ui);
		
	}
return	new ResponseEntity<List<UserInfo>>(user,HttpStatus.OK);
	
	//Http status
	//error code and error response
	//404 1nd page not found

//mappings
//get mapping->fetch data by sending few parameters
//post mapping->create a record 0r save
//put mapping->update record
//delete mapping->delete record
	
//how to test
//1.postman
//2.restclient
}
	
	//---------------for login----------------------------
	@GetMapping("/validate/{user_id}/{password}")
	public ResponseEntity<UserInfo> validatecredentials(@PathVariable("user_id") String id,@PathVariable("password") String pwd,UserInfo ui){
		ui=ud.validate(id, pwd);
		if(ui == null){ // null pointer exception
			ui=new UserInfo(); //solution creating object for UserInfo
			ui.setErrorCode("404");
			ui.setErrorMessage("invalidate credentials");
		
		}
		else{
			ui.setErrorCode("200");
			ui.setErrorMessage("you successfully logged in...");
			session.setAttribute("loggedInUserId", ui.getUser_id());
			session.setAttribute("role", ui.getRole());
		}
	return new ResponseEntity<UserInfo>(ui,HttpStatus.OK);
	}
	
	@GetMapping("/userlogout")
	public UserInfo logout(UserInfo ui){
		
		session.invalidate();
		ui.setErrorCode("200");
		ui.setErrorMessage("logged out successfully");
		return ui;
		
		
	}
	//------------------create a user-------------
	@PostMapping("/createuser/")
	public ResponseEntity<UserInfo> createuser(@RequestBody UserInfo userinfo)
	{
	if(ud.getuser(userinfo.getUser_id()) ==null){
		ud.save(userinfo);
		userinfo.setErrorCode("200");
		userinfo.setErrorMessage("you successfully registered");
	}	
	else{
		userinfo.setErrorCode("404");
		userinfo.setErrorMessage("user exist with this id:"+userinfo.getUser_id());
	}
	return new ResponseEntity<UserInfo>(userinfo,HttpStatus.OK);
	}
	
	 //------------------- Update a User --------------------------------------------------------
    
    @PutMapping(value = "/updateuser/{user_id}")
    public ResponseEntity<UserInfo> updateUser(@PathVariable("user_id") String id, @RequestBody UserInfo user) {
        System.out.println("Updating User " + id);
          
        UserInfo currentUser = ud.getuser(id);
          
        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<UserInfo>(HttpStatus.NOT_FOUND);
        }
  
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());
        currentUser.setMobilenumber(user.getMobilenumber());
        currentUser.setPassword(user.getPassword());
        currentUser.setRole(user.getRole());
        
          
        ud.update(currentUser);
        return new ResponseEntity<UserInfo>(currentUser, HttpStatus.OK);
    }
    //-------------------Retrieve Single User--------------------------------------------------------
    
    @GetMapping(value = "/user/{user_id}")
    public ResponseEntity<UserInfo> getUser(@PathVariable("user_id") String id) {
        System.out.println("Fetching User with id " + id);
        UserInfo user = ud.getuser(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<UserInfo>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
    }
}
