package pl.spring.demo.dao.impl;

import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.entity.BookEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("bookDao")
public class BookDaoImpl implements BookDao {

    private final Set<BookEntity> ALL_BOOKS = new HashSet<>();

    @Resource(name = "sequence")
    private Sequence sequence;

    public BookDaoImpl() {
        addTestBooks();
    }

    @Override
    public List<BookEntity> findAll() {
        return new ArrayList<>(ALL_BOOKS);
    }

    @Override
    public List<BookEntity> findBookByTitle(String title) {
		ArrayList<BookEntity> foundBooks = new ArrayList<>();
		title = title.toLowerCase();
		for (BookEntity bookEntity : ALL_BOOKS) {
			if(bookEntity.getTitle().toLowerCase().contains(title)){
				foundBooks.add(bookEntity);
			}
		}
		return foundBooks;
    }
    
    @Override
    public List<BookEntity> findBooksByAuthor(String findingAuthor) {
    	ArrayList<BookEntity> foundBooks = new ArrayList<>(0);
    	findingAuthor = findingAuthor.toLowerCase();
    	String[] authors;
        for (BookEntity bookEntity : ALL_BOOKS) {
        	authors = bookEntity.getAuthors().split(", ");
			for (String author : authors) {
				author = author.toLowerCase();
				if( author.contains(findingAuthor) || reverseNames(author).contains(findingAuthor)){
					foundBooks.add(bookEntity);
				}
			}
 		}
        return foundBooks;
    }
    
    private String reverseNames(String authorNames){
    	String[] names = authorNames.split(" ");
    	return String.format("%s %s", names[1], names[0]);
    }

    @Override
    @NullableId
    public BookEntity saveBook(BookEntity book) {
    	ALL_BOOKS.add(book);
        return book;
    }

    public void addTestBooks() {
        ALL_BOOKS.add(new BookEntity(0L, "Romeo i Julia", "Wiliam Szekspir"));
        ALL_BOOKS.add(new BookEntity(1L, "Opium w rosole", "Hanna Ożogowska"));
        ALL_BOOKS.add(new BookEntity(2L, "Przygody Odyseusza", "Jan Parandowski"));
        ALL_BOOKS.add(new BookEntity(3L, "Awantura w Niekłaju", "Edmund Niziurski"));
        ALL_BOOKS.add(new BookEntity(4L, "Pan Samochodzik i Fantomas", "Zbigniew Nienacki"));
        ALL_BOOKS.add(new BookEntity(5L, "Zemsta", "Aleksander Fredro"));
    }
}
