package com.bookstore.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Book;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Customer;
import com.bookstore.entity.Review;

public class ReviewDAOTest {
	private static ReviewDAO reviewDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reviewDao = new ReviewDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		reviewDao.close();
	}

	@Test
	public void testCreateReview() {
		Review review = new Review();
		Book book = new Book();
		book.setBookId(10);
		Customer customer = new Customer();
		customer.setCustomerId(3);
		
		review.setBook(book);
		review.setCustomer(customer);
		review.setBook(book);
		review.setHeadline("Sach hay");
		review.setRating(4);
		review.setComment("Cuon sach rat tuyet voi ");
		Review savedReview =  reviewDao.create(review);
		assertTrue(savedReview.getReviewId()>0);
		
	}
	@Test
    public void testGet() {
		Integer reviewId = 1;
		Review review = reviewDao.get(reviewId);
		assertNotNull(review);
	}
	@Test
	public void testUpdateReview() {
		Integer reviewId = 1;
		Review review = reviewDao.get(reviewId);
		review.setHeadline("Excellent book");
		Review updatedReview = reviewDao.update(review);
		assertEquals(review.getHeadline() , updatedReview.getHeadline());
		
	}
	@Test
	public void testListAll(){
		List<Review> listReview = reviewDao.listAll();
		for(Review review : listReview) {
			System.out.println(review.getReviewId()+" - "+review.getBook().getTitle()+" - "+review.getCustomer().getFullname()+" - "+
		review.getHeadline()+" - "+review.getRating());
		}
		
		assertTrue(listReview.size()>0);
		
	}
	@Test
	public void testCount() {
		long totalReviews = reviewDao.count();
		assertTrue(totalReviews > 0 );
	}
	@Test
	public void testDeleteReview() {
		int reviewId = 2;
		reviewDao.delete(reviewId);
		Review review = reviewDao.get(reviewId);
		assertNull(review);
	}
	
	@Test
	public void testFindByCustomerAndBookNotFound() {
		Integer customerId = 1000;
		Integer bookId = 416;
		Review rs = reviewDao.findByCustomerAndBook(customerId, bookId);
		assertNull(rs);
		
	}
	@Test
	public void testFindByCustomerAndBookFound() {
		Integer customerId = 4;
		Integer bookId = 2;
		Review rs = reviewDao.findByCustomerAndBook(customerId, bookId);
		assertNotNull(rs);
		
	}
	@Test
	public void testlistMostRecent() {
		List<Review> recentOrders = reviewDao.listMostRecent();
		assertEquals(3,recentOrders.size());
	}
	
}
