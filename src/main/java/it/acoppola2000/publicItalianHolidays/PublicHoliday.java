package it.acoppola2000.publicItalianHolidays;

class PublicHoliday {

    private String date;
    private String name;
    private String localName;

    PublicHoliday() {
    }

    PublicHoliday(String date, String name, String localName) {
        this.date = date;
        this.name = name;
        this.localName = localName;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getLocalName() {
        return localName;
    }
}
