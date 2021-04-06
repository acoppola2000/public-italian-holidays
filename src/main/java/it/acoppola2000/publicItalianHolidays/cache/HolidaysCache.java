package it.acoppola2000.publicItalianHolidays.cache;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class HolidaysCache {

    private List<CachedHoliday> list;
    private LocalDateTime lastUpdateDateTime;

    @PostConstruct
    void init() {
        list = new ArrayList<>();
    }

    void update(List<CachedHoliday> newList) {
        List<CachedHoliday> updatedList = new ArrayList<>(newList);
        //change the referenced main list
        //no error for concurrent iterators
        list = updatedList;
        lastUpdateDateTime = LocalDateTime.now();
    }


    public List<CachedHoliday> getAllHolidays() {
        List<CachedHoliday> localReference = list;
        List<CachedHoliday> returnList = new ArrayList<>();
        for (int i = 0; i < localReference.size(); i++) {
            returnList.add(localReference.get(i));
        }
        return returnList;
    }

    public List<CachedHoliday> getNonFixedHolidays() {
        List<CachedHoliday> localReference = list;
        List<CachedHoliday> returnList = new ArrayList<>();
        for (int i = 0; i < localReference.size(); i++) {
            if (!localReference.get(i).getFixed()) {
                returnList.add(localReference.get(i));
            }
        }
        return returnList;
    }

    public LocalDateTime getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }
}
