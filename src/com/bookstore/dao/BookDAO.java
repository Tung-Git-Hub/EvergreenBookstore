package com.bookstore.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.bookstore.entity.Book;

public class BookDAO extends JpaDAO<Book> implements GenericDAO<Book> {

	public BookDAO() {
	}
	public Book create(Book book) {
		book.setLastUpdateTime(new Date());
		return super.create(book);
	}
	@Override
	public Book update(Book book) {

		book.setLastUpdateTime(new Date());
		return super.update(book);
		
	}
	@Override
	public void delete(Object bookId) {
		
		super.delete(Book.class,bookId);
	}
	@Override
	public List<Book> listAll() {
		
		return super.findWithNamedQuery("Book.findAll");
	}
	@Override
	public long count() {
		return super.countWithNamedQuery("Book.countAll");
	}
	@Override
	public Book get(Object bookId) {
		return super.find(Book.class, bookId);
	}
	public Book findByTitle(String title) {
		List<Book> result = findWithNamedQuery("Book.findByTitle", "title",title);
		if(!result.isEmpty()) {
			return result.get(0);
		}
		
		return null;
	}
	public List<Book> listByCategory(int categoryId) {		
		return super.findWithNamedQuery("Book.findByCategory", "catId", categoryId);
	}
	public List<Book> listNewBooks(){
		return super.findWithNamedQuery("Book.listNew",0,4);		
		
	}
	public List<Book> search(String keyword){
		return super.findWithNamedQuery("Book.search", "keyword", keyword);
		
	}
	public long countByCategory(int categoryId){
		return super.countWithNamedQuery("Book.countByCategory","catId",categoryId);
	}

	public List<Book> listBestSellingBooks(){
		return	super.findWithNamedQuery("OrderDetail.bestSelling",0,4);
		 
	}
	
	public List<Book> listMostFavoredBooks(){ 
		List<Book> mostFavoredBooks = new ArrayList<>();
		List<Object[]> result =super.findWithNamedQueryObjects("Review.mostFavoredBooks",0,4);
		if(!result.isEmpty()){
			for(Object[] element : result){
				
				Book book = (Book) element[0];
				mostFavoredBooks.add(book);
			}
			
		}
		return mostFavoredBooks	;
	}
}
