package it.acoppola2000.publicItalianHolidays;

import it.acoppola2000.publicItalianHolidays.cache.CachedHoliday;
import it.acoppola2000.publicItalianHolidays.cache.HolidaysCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class PublicItalianHolidaysApi {

	@Autowired
	private HolidaysCache cache;

	@GetMapping("/")
	public List<PublicHoliday> allHolidays() {
		List<PublicHoliday> list = new ArrayList<>();
		for (CachedHoliday cacheHoliday : cache.getAllHolidays()) {
			PublicHoliday publicHoliday = new PublicHoliday(cacheHoliday.getDate(),cacheHoliday.getName(), cacheHoliday.getLocalName());
			list.add(publicHoliday);
		}
		return list;
	}

	@GetMapping("/notFixed")
	public List<PublicHoliday> notFixedHolidays() {
		List<PublicHoliday> list = new ArrayList<>();
		for (CachedHoliday cacheHoliday : cache.getNonFixedHolidays()) {
			PublicHoliday publicHoliday = new PublicHoliday(cacheHoliday.getDate(),cacheHoliday.getName(), cacheHoliday.getLocalName());
			list.add(publicHoliday);
		}
		return list;
	}


}