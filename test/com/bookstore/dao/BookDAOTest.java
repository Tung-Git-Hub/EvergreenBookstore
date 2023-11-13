package com.bookstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookDAOTest{
	private static BookDAO bookDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bookDao = new BookDAO();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		bookDao.close();
	}

	@Test
	public void testCreateBook() throws ParseException, IOException {
		Book newBook = new Book();
		Category category = new Category("Advenced Java");
		category.setCategoryId(12);
		newBook.setCategory(category);
		newBook.setTitle("Effective Java (2nd Edition)");
		newBook.setAuthor("Joshua Bloch");
		newBook.setPrice(38.87f);
		newBook.setIsbn("0321356683");
		
		newBook.setDescription("New coverage of generics, enums, annotations, autoboxing");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("05/28/2008");
		newBook.setPublishDate(publishDate);
		String imagePath = "D:\\BookStoreProject\\books\\Effective Java.jpg";
		
		byte[] imageBytes= Files.readAllBytes(Paths.get(imagePath));
		newBook.setImage(imageBytes);
		Book createdBook = bookDao.create(newBook);
		assertTrue(createdBook.getBookId()>0);
	}
	@Test
	public void test2ndCreateBook() throws ParseException, IOException {
		Book newBook = new Book();
		Category category = new Category("Java Core");
		category.setCategoryId(1);
		newBook.setCategory(category);
		newBook.setTitle("Java 8 in Action");
		newBook.setAuthor("Raoul-Gabriel Urma");
		newBook.setPrice(36.99f);
		newBook.setIsbn("1617291994");
		
		newBook.setDescription("Java 8 in Action is a clearly written guide to the new features of Java 8.");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("08/28/2014");
		newBook.setPublishDate(publishDate);
		String imagePath = "D:\\BookStoreProject\\books\\Java 8 in Action.jpg";
		
		byte[] imageBytes= Files.readAllBytes(Paths.get(imagePath));
		newBook.setImage(imageBytes);
		Book createdBook = bookDao.create(newBook);
		assertTrue(createdBook.getBookId()>0);
	}
	@Test
	public void testUpdateBook()  throws ParseException, IOException {
		Book existBook = new Book();
		existBook.setBookId(1);
		Category category = new Category("Java Core");
		category.setCategoryId(1);
		existBook.setCategory(category);
		existBook.setTitle("Effective Java (3rd Edition)");
		existBook.setAuthor("Joshua Bloch");
		existBook.setPrice(40f);
		existBook.setIsbn("0321356683");
		
		existBook.setDescription("New coverage of generics, enums, annotations, autoboxing");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("05/28/2008");
		existBook.setPublishDate(publishDate);
		String imagePath = "D:\\BookStoreProject\\books\\Effective Java.jpg";
		
		byte[] imageBytes= Files.readAllBytes(Paths.get(imagePath));
		existBook.setImage(imageBytes);
		Book updatedBook = bookDao.update(existBook);
		assertEquals(updatedBook.getTitle(),"Effective Java (3rd Edition)"); ;
	}
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteBookFail() {
		Integer bookId = 100; 
		bookDao.delete(bookId);
		
	}
	@Test
	public void testDeleteBook() {
		Integer bookId = 2;
		bookDao.delete(bookId);
		assertTrue(true);
	}
	@Test
	public void testGetBookFail() {
		Integer bookId = 9;
		Book book = bookDao.get(bookId);
		assertNull(book);
	}
	@Test
	public void testGetBook() {
		Integer bookId = 3;
		Book book = bookDao.get(bookId);
		assertNotNull(book);
		
	}
	@Test
	public void testListAll() {
		List<Book> listBooks =	bookDao.listAll();
		for(Book book:listBooks) {
			System.out.println(book.getTitle() +" - " +book.getAuthor() );
		}
		
		assertFalse(listBooks.isEmpty());
		
	}
	@Test
	public void testFindByTitleNotExist() {
		String title = "Thinkin Java";
		Book book = bookDao.findByTitle(title);
		assertNull(book);
		
	}
	@Test
	public void testFindByTitleExist() {
		String title = "Java 8 in Action";
		Book book = bookDao.findByTitle(title);
		assertNotNull(book);
		
	}
	
	@Test
	public void testCount() {
		long totalBooks = bookDao.count();
		assertEquals(2,totalBooks);
		
	}
	@Test
	public void testListByCategory() {
		int categoryId = 12;
		List<Book> listBooks = bookDao.listByCategory(categoryId);
		assertTrue(listBooks.size()>0);
	}
	@Test
	public void testListNewBooks() {
		List<Book> listNewBooks = bookDao.listNewBooks();
		assertEquals(4,listNewBooks.size());
		for (Book book : listNewBooks) {
			System.out.println(book.getTitle()+book.getPublishDate());
		}
	}
	@Test
	public void testSearchBookInTitle() {
		String keyword = "Java";
		List<Book> result = bookDao.search(keyword);
		for(Book book:result) {
			System.out.println(book.getTitle());
		}
		assertEquals(4,result.size());
		
		
	}
	@Test
	public void testSearchBookInAuthor() {
		String keyword = "Bryan";
		List<Book> result = bookDao.search(keyword);
		for(Book book:result) {
			System.out.println(book.getAuthor());
		}
		assertEquals(1,result.size());
		
	}@Test
	public void testSearchBookInDescriptions() { 
		String keyword = "The Big Picture";
		List<Book> result = bookDao.search(keyword);
		for(Book book:result) {
			System.out.println(book.getTitle());
		}
		assertEquals(1,result.size());
		
	}
	@Test
	public void testCountByCategory() {
		int categoryId = 4;
		long num = bookDao.countByCategory(categoryId);
		assertTrue(num>0);
	}
	@Test
	public void listBestSellingBooks() {
		List<Book> topBestSellingBooks = bookDao.listBestSellingBooks();
		for(Book book : topBestSellingBooks) {
			System.out.println(book.getTitle());
			
		}
		assertEquals(4,topBestSellingBooks.size());
		
		
	}
	@Test
	public void listMostFavoredBooks() {
		List<Book> topFavoredBooks = bookDao.listMostFavoredBooks();
		for(Book book : topFavoredBooks) {
			System.out.println(book.getTitle());
			
		}
		assertEquals(4,topFavoredBooks.size());
		
		
	}
}
	

