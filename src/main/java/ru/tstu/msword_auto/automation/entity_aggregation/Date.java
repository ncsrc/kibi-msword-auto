package ru.tstu.msword_auto.automation.entity_aggregation;


import java.util.HashMap;
import java.util.Map;

// wrapper that parses date entity in a way that automation requires
public class Date {
    /** Key which points on day of map with parsed data */
    private static final transient String KEY_DAY = "day";

    /** Key which points on month of map with parsed data */
    private static final transient String KEY_MONTH = "month";

    /** Key which points on year of map with parsed data*/
    private static final transient String KEY_YEAR = "year";

    private final ru.tstu.msword_auto.entity.Date date;

    /** Map containing parsed gos date in a way which satisfy document requirements. Contains day, month and year. */
    private transient Map<String, String> parsedGosDate;

    /** Map containing parsed vcr date in a way which satisfy document requirements. Contains day, month and year. */
    private transient Map<String, String> parsedVcrDate;


    public Date(ru.tstu.msword_auto.entity.Date date) {
        this.date = date;
        parsedGosDate = new HashMap<>();
        parsedVcrDate = new HashMap<>();
        parseDates();
    }

    /**
     * In case raw date entity would be needed.
     * @return wrapped date entity
     */
    public ru.tstu.msword_auto.entity.Date getEntityDate() {
        return this.date;
    }

    /**
     * Returns information about day of gos date in a way which document requires
     * @return day of gos date
     */
    public String getGosDay() {
        return parsedGosDate.get(KEY_DAY);
    }

    /**
     * Returns information about day of vcr date in a way which document requires
     * @return day of vcr date
     */
    public String getVcrDay() {
        return parsedVcrDate.get(KEY_DAY);
    }

    /**
     * Returns information about month of gos date in a way which document requires
     * @return month of gos date
     */
    public String getGosMonth() {
        return parsedGosDate.get(KEY_MONTH);
    }

    /**
     * Returns information about month of vcr date in a way which document requires
     * @return month of vcr date
     */
    public String getVcrMonth() {
        return parsedVcrDate.get(KEY_MONTH);
    }

    /**
     * Returns information about year of gos date in a way which document requires
     * @return year of gos date
     */
    public String getGosYear() {
        return parsedGosDate.get(KEY_YEAR);
    }

    /**
     * Returns information about year of vcr date in a way which document requires
     * @return year of vcr date
     */
    public String getVcrYear() {
        return parsedVcrDate.get(KEY_YEAR);
    }


    private void parseDates() {
        String gosDate = date.getGosDate();
        parseSingleDate(gosDate, this.parsedGosDate);

        String vcrDate = date.getVcrDate();
        parseSingleDate(vcrDate, this.parsedVcrDate);
    }

    private void parseSingleDate(String date, Map<String, String> map) {
        if("".equals(date)) {
            fillParsedDateWithEmptyStrings(map);
        } else {
            String day = date.substring(8);
            String month = parseMonth(date.substring(5, 7));
            String year = date.substring(2, 4);

            map.put(KEY_DAY, day);
            map.put(KEY_MONTH, month);
            map.put(KEY_YEAR, year);
        }

    }

    private String parseMonth(String month) {
        switch(month)
        {
            case "01":
                return "января";
            case "02":
                return "февраля";
            case "03":
                return "марта";
            case "04":
                return "апреля";
            case "05":
                return "мая";
            case "06":
                return "июня";
            case "07":
                return "июля";
            case "08":
                return "августа";
            case "09":
                return "сентября";
            case "10":
                return "октября";
            case "11":
                return "ноября";
            case "12":
                return "декабря";
            default:
                return "";
        }
    }

    private void fillParsedDateWithEmptyStrings(Map<String, String> parsedDate) {
        parsedDate.put(KEY_DAY, "");
        parsedDate.put(KEY_MONTH, "");
        parsedDate.put(KEY_YEAR, "");
    }



}
