package pl.spring.demo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.spring.demo.aop.AppConfiguration;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.service.mtch.CheckingAuthorInBookToMatcher;
import pl.spring.demo.service.mtch.CheckingTitleInBookToMatcher;
import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookTo;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//For load context from @Configuration class.
@ContextConfiguration(classes=AppConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;
//    @Autowired
//    private CacheManager cacheManager;

    @Before
    public void setUp() {
//        cacheManager.getCache("booksCache").clear();
    }

    @Test
    public void testShouldFindAllBooks() {
        // when
        List<BookTo> allBooks = bookService.findAllBooks();
        // then
        assertNotNull(allBooks);
        assertFalse(allBooks.isEmpty());
        assertEquals(6, allBooks.size());
    }
    
    @Test
    public void testShouldFindAllBooksByTitle() {
        // given
        final String title = "Opium w rosole";
        final CheckingTitleInBookToMatcher matcher = new CheckingTitleInBookToMatcher(title);
        // when
        List<BookTo> booksByTitle = bookService.findBooksByTitle(title);
        // then
        assertNotNull(booksByTitle);
        assertFalse(booksByTitle.isEmpty());
        for (BookTo bookTo : booksByTitle) {
        	assertThat(bookTo, matcher);
		}
    }
          
    @Test
    public void testShouldFindAllBooksByAuthor() {
        // given
        final String authorName = "Fredro alek";
        final CheckingAuthorInBookToMatcher matcher = new CheckingAuthorInBookToMatcher(authorName);
        // when
        List<BookTo> booksByTitle = bookService.findBooksByAuthor(authorName);
        // then
        assertNotNull(booksByTitle);
        assertFalse(booksByTitle.isEmpty());
        
        for (BookTo bookByTitle : booksByTitle){
        	assertThat(bookByTitle, matcher);
        }      
    }

    @Test(expected = BookNotNullIdException.class)
    public void testShouldThrowBookNotNullIdException() {
        // given
        final BookTo bookToSave = new BookTo(22L, "TitleTEST", Arrays.asList(new AuthorTo(99L, "AuthorFirstNameTEST", "AuthorLastNameTEST")));
        // when
        bookService.saveBook(bookToSave);
        // then
        fail("test should throw BookNotNullIdException");
    }
}
