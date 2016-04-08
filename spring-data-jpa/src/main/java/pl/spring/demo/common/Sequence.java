package pl.spring.demo.common;

import java.util.Collection;
import org.springframework.stereotype.Service;

import common.IdAware;

@Service
public class Sequence {
	
	public long nextValue(Collection<? extends IdAware> existingIds) {
        long result = 0;
        for (IdAware nextExistingId : existingIds) {
            if (Long.compare(nextExistingId.getId(), result) > 0) {
                result = nextExistingId.getId();
            }
        }
        return result+1;
    }    
}
