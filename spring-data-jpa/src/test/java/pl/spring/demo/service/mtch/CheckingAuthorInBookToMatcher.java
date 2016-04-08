package pl.spring.demo.service.mtch;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookTo;

public class CheckingAuthorInBookToMatcher extends BaseMatcher<BookTo> {

	private final String findingPhrase;
	
	public CheckingAuthorInBookToMatcher(String findingPhrase) {
		this.findingPhrase = findingPhrase.toLowerCase();
	}
	
	@Override
	public boolean matches(Object item) {
		final BookTo bookTo = (BookTo) item;
		String firstName, lastName;
		for (AuthorTo authorTo : bookTo.getAuthors()) {
			firstName = authorTo.getFirstName().toLowerCase();
			lastName = authorTo.getLastName().toLowerCase();
			if( String.format("%s %s", lastName, firstName).contains(findingPhrase)
			 || String.format("%s %s", firstName, lastName).contains(findingPhrase)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText("There is no author with '" + findingPhrase + "'.");
	}


}
