package pl.spring.demo.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import common.IdAware;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.exception.BookNotNullIdException;
import java.util.Collection;

import javax.annotation.Resource;

@Aspect
@Component("bookDaoAdvisor")
public class BookDaoAdvisor{
	
	@Resource(name = "sequence")
    private Sequence sequence;
	
	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	@Before("@annotation(pl.spring.demo.annotation.NullableId)")
    public void before(JoinPoint joinPoint) throws Throwable {
		Object[] objects = joinPoint.getArgs();
		IdAware idAwareObject = (IdAware) objects[0];
		Object targetObject = joinPoint.getTarget();
    	checkNotNullId(idAwareObject);  
    	idAwareObject.setId(sequence.nextValue(getCollection(targetObject)));
    }
	
	private void checkNotNullId(Object o) {
        if (o instanceof IdAware && ((IdAware) o).getId() != null) {
            throw new BookNotNullIdException();
        }
    }
	
    private Collection<? extends IdAware> getCollection(Object o) {
    	BookDao bookDao = (BookDao)o;
    	return bookDao.findAll();
	}
}


