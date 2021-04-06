package it.acoppola2000.publicItalianHolidays.cache;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CachedHoliday {

    private String date;
    private String name;
    private String localName;
    private boolean fixed;

    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public String getLocalName() {
        return localName;
    }

    boolean getFixed() {
        return fixed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CachedHoliday that = (CachedHoliday) o;
        return fixed == that.fixed &&
                Objects.equals(date, that.date) &&
                Objects.equals(name, that.name) &&
                Objects.equals(localName, that.localName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, name, localName, fixed);
    }

    public static CachedHoliday of(String date, String localName, String name, boolean fixed) {
        CachedHoliday item = new CachedHoliday();
        item.date = date;
        item.name = name;
        item.localName = localName;
        item.fixed = fixed;
        return item;
    }
}
