package pl.spring.demo.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import pl.spring.demo.common.Mapper;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.service.impl.BookServiceImpl;
import pl.spring.demo.to.BookTo;

import static org.junit.Assert.assertEquals;

public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookDao bookDao;
    
    private Mapper mapper = new Mapper();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShouldSaveBook() {
        // given
        BookEntity bookEntity = new BookEntity(null, "title", "authorFirstName authorLastName");
        BookTo bookTo = mapper.mapBookEntityToBookTo(bookEntity);
        Mockito.when(bookDao.saveBook(bookEntity)).thenReturn(new BookEntity(1L, "title", "authorFirstName authorLastName"));
        // when
        BookTo result = bookService.saveBook(bookTo);
        // then
        Mockito.verify(bookDao).saveBook(bookEntity);
        assertEquals(1L, result.getId().longValue());
    }
}
