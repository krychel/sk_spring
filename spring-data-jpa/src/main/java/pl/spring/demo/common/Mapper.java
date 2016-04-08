package pl.spring.demo.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookTo;

@Service
public class Mapper {

    public List<BookTo> mapBookEntityToBookTo(List<BookEntity> bookEntityList){
    	List<BookTo> bookToList = new ArrayList<>();
		for (BookEntity bookEntity : bookEntityList) {
			bookToList.add(mapBookEntityToBookTo(bookEntity));
		}
        return bookToList;
    }
    
    public BookTo mapBookEntityToBookTo(BookEntity bookEntity){
    	BookTo bookTo = new BookTo(bookEntity.getId(), bookEntity.getTitle(), getAuthorsAsAuthorToList(bookEntity.getAuthors()));
    	return bookTo;
    }
    
	private List<AuthorTo> getAuthorsAsAuthorToList(String authors) {
		List<AuthorTo> authorToList = new ArrayList<>();
		String[] authorArray = authors.split(", ");
		for (String author : authorArray) {
			authorToList.add(new AuthorTo(0L, author.split(" ")[0], author.split(" ")[1]));
		}
		return authorToList;
	}
	
    public List<BookEntity> mapBookToToBookEntity(List<BookTo> bookToList){
    	List<BookEntity> bookEntityList = new ArrayList<>();
		for (BookTo bookTo : bookToList) {
			bookEntityList.add(mapBookToToBookEntity(bookTo));
		}
        return bookEntityList;
    }
	
    public BookEntity mapBookToToBookEntity(BookTo bookTo){
    	return new BookEntity(bookTo.getId(), bookTo.getTitle(), getAuthorsToListAsString(bookTo.getAuthors()));
    }

	private String getAuthorsToListAsString(List<AuthorTo> authorToList) {
		String authorsString = null;
		for (AuthorTo authorTo : authorToList) {
			authorsString += authorTo.getFirstName() + " " + authorTo.getLastName() + ", ";
		}
		return authorsString.substring(0, authorsString.length()-2 );
	}	
}
