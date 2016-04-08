package pl.spring.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pl.spring.demo.common.Mapper;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

	@Autowired
    private BookDao bookDao;
	@Autowired
	private Mapper mapper;

    @Override
    @Cacheable("booksCache")
    public List<BookTo> findAllBooks() {
		@SuppressWarnings("unchecked")
		List<BookEntity> bookEntityList = (List<BookEntity>) bookDao.findAll();
		return mapper.mapBookEntityToBookTo(bookEntityList);

    }

    @Override
    public List<BookTo> findBooksByTitle(String title) {
		return mapper.mapBookEntityToBookTo(bookDao.findBookByTitle(title));
        // Only for prepare fail in JUnit test.
		//	return mapper.mapBookEntityToBookTo((List<BookEntity>)bookDao.findAll());
    }

    @Override
    public List<BookTo> findBooksByAuthor(String author) {   	
        return mapper.mapBookEntityToBookTo((List<BookEntity>) bookDao.findBooksByAuthor(author));
        // Only for prepare fail in JUnit test.
        // return mapper.mapBookEntityToBookTo((List<BookEntity>)bookDao.findAll());
    }

    @Override
    public BookTo saveBook(BookTo book) {
    	return mapper.mapBookEntityToBookTo(
    			bookDao.saveBook(mapper.mapBookToToBookEntity(book)));
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
    
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
