package com.bookstore.dao;

// su dung JUnit de kiem thu cho UserDAO
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.dom4j.Entity;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Users;

public class UserDAOTest {
	private static UserDAO userDAO;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userDAO = new UserDAO();
	}
	@Test
	public void testCreateUsers() {
		Users user1 = new Users();
		user1.setEmail("Hang@gmail.com");
		user1.setFullName("Phan Hang");
		user1.setPassword("10165");
		user1 = userDAO.create(user1);
		assertTrue(user1.getUserId() > 0);
		//success
	}
	@Test(expected = PersistenceException.class)
	public void testCreateUsersFieldsNotSet() {
		Users user1 = new Users();
		user1 = userDAO.create(user1);
	}		
	@Test
	public void testUpdateUsers() {
		Users user = new Users();
		user.setUserId(4);
		user.setEmail("tanh@gmail.com");
		user.setFullName("Tran Tuan Anh");
		user.setPassword("6878");
		user = userDAO.update(user);
		String ex = "123456";
		String rs = user.getPassword();
		assertEquals(ex,rs);
	}
	@Test
	public void testGetUsersFound() { 
		Integer userId = 1;
		Users user = userDAO.get(userId);
		if(user!=null) {
			System.out.print(user.getEmail());
		}
		assertNotNull(user);
	}
	@Test
	public void testGetUsersNotFound() { 
		Integer userId = 100;
		Users user = userDAO.get(userId);
		if(user==null) {
		
			assertNull(user);
		}
	}
	@Test
	public void testDeleteUser() {
		Integer userId = 5;
		userDAO.delete(userId);
		Users user = userDAO.get(userId);
		assertNull(user);
		
	}@Test(expected= EntityNotFoundException.class)
	public void testDeleteNonExistUser() {
		Integer userId = 100;
		userDAO.delete(userId);
	}
	@Test
	public void testListAll() {
		List<Users> listUsers= userDAO.listAll();
		for (Users user:listUsers) {
			System.out.println(user.getEmail());
		}
		assertTrue(listUsers.size()>0);
	}
	@Test
	public void testCount() {
		
		long totalUsers = userDAO.count();
		assertEquals(4,totalUsers);
		
	}
	@Test
	public void testCheckLogin() {
		String email = "nttung1106@gmail.com";
		String password = "123456";
		boolean loginResult = userDAO.checkLogin(email, password);
		assertTrue(loginResult);
	}
	@Test
	public void testCheckLoginFailed() {
		String email = "n.com";
		String password = "1233456";
		boolean loginResult = userDAO.checkLogin(email, password);
		assertFalse(loginResult);
	}
	@Test
	public void testFindByEmail() {
		String email="nttung1106@gmail.com";
		Users user = userDAO.findByEmail(email);
		assertNotNull(user);
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		userDAO.close();
	}
}