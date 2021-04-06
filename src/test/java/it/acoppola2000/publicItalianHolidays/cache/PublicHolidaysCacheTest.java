package it.acoppola2000.publicItalianHolidays.cache;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PublicHolidaysCacheTest {

    @Test
    void cache_after_first_update_should_return_allHolidays() {

        //Arrange
        HolidaysCache sut = new HolidaysCache();
        List<CachedHoliday> cachedHolidays = createData_firstUpdate_cachedHolidaysList();
        sut.update(cachedHolidays);

        //Act
        List<CachedHoliday> returnedList = sut.getAllHolidays();

        //Assert
        assertThat(returnedList).containsExactlyInAnyOrder(cachedHolidays.get(0),
                cachedHolidays.get(1),
                cachedHolidays.get(2),
                cachedHolidays.get(3),
                cachedHolidays.get(4));
    }

    @Test
    void cache_after_second_update_should_return_allHolidays() {

        //Arrange
        HolidaysCache sut = new HolidaysCache();
        List<CachedHoliday> cachedFirstUpdateHolidays = createData_firstUpdate_cachedHolidaysList();
        List<CachedHoliday> cachedSecondUpdateHolidays = createData_secondUpdate_cachedHolidaysList();
        sut.update(cachedFirstUpdateHolidays);
        sut.update(cachedSecondUpdateHolidays);

        //Act
        List<CachedHoliday> returnedList = sut.getAllHolidays();

        //Assert
        assertThat(returnedList).containsExactlyInAnyOrder(cachedSecondUpdateHolidays.get(0),
                cachedSecondUpdateHolidays.get(1),
                cachedSecondUpdateHolidays.get(2));
    }

    @Test
    void cache_should_return_notFixedHolidays() {

        //Arrange
        HolidaysCache sut = new HolidaysCache();
        List<CachedHoliday> cachedFirstUpdateHolidays = createData_firstUpdate_cachedHolidaysList();
        List<CachedHoliday> cachedFirstUpdateNotFixedHolidays = createData_firstUpdate_cachedNotFixedHolidaysList();
        sut.update(cachedFirstUpdateHolidays);

        //Act
        List<CachedHoliday> returnedNotFixedHolidaysList = sut.getNonFixedHolidays();

        //Assert
        assertThat(returnedNotFixedHolidaysList).containsExactlyInAnyOrder(
                cachedFirstUpdateNotFixedHolidays.get(0),
                cachedFirstUpdateNotFixedHolidays.get(1));
    }



    //********************* TEST DATA

    private List<CachedHoliday> createData_firstUpdate_cachedHolidaysList() {
        List<CachedHoliday> data = new ArrayList<>();
        data.add(CachedHoliday.of("2021-01-01", "Capodanno", "New Year's Day", true));
        data.add(CachedHoliday.of("2021-01-06", "Epifania", "Epiphany", true));
        data.add(CachedHoliday.of("2021-04-04", "Pasqua", "Easter Sunday", false));
        data.add(CachedHoliday.of("2021-04-05", "Lunedì dell'Angelo", "Easter Monday", false));
        data.add(CachedHoliday.of("2021-04-25", "Festa della Liberazione", "Liberation Day", true));
        return data;
    }

    private List<CachedHoliday> createData_firstUpdate_cachedNotFixedHolidaysList() {
        List<CachedHoliday> data = new ArrayList<>();
        data.add(CachedHoliday.of("2021-04-04", "Pasqua", "Easter Sunday", false));
        data.add(CachedHoliday.of("2021-04-05", "Lunedì dell'Angelo", "Easter Monday", false));
        return data;
    }

    private List<CachedHoliday> createData_secondUpdate_cachedHolidaysList() {
        List<CachedHoliday> data = new ArrayList<>();
        data.add(CachedHoliday.of("2022-01-01", "Capodanno", "New Year's Day", true));
        data.add(CachedHoliday.of("2022-01-06", "Epifania", "Epiphany", true));
        data.add(CachedHoliday.of("2022-04-17", "Pasqua", "Easter Sunday", false));
        return data;
    }


}