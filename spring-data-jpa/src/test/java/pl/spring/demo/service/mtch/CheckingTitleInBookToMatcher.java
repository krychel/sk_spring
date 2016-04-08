package pl.spring.demo.service.mtch;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import pl.spring.demo.to.BookTo;

public class CheckingTitleInBookToMatcher extends BaseMatcher<BookTo> {

	private final String findingPhrase;
	
	public CheckingTitleInBookToMatcher(String findingPhrase) {
		this.findingPhrase = findingPhrase.toLowerCase();
	}
	
	@Override
	public boolean matches(Object item) {
		return ((BookTo) item).getTitle().toLowerCase().contains(findingPhrase);
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText("There is no title with '" + findingPhrase + "'.");
	}


}
