package de.tkapps.arbeitszeiterfassung;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.tkapps.arbeitszeiterfassung.helpers.timeHelpers;

import static org.junit.Assert.assertEquals;

public class DateAndTimeHelpersTest {
    @Test
    public void makeDateTime_isCorrect () {
        String expected_result = "Thu May 20 15:05:23 CEST 2021";

        String strTime = "15:05:23";
        String strDate = "20.05.2021";

        Date actual_result = timeHelpers.makeDateTime(strDate, strTime);

        assertEquals(expected_result, actual_result.toString());
    }

    @Test
    public void makeDateTimeAndConvertToString_isCorrect () {

        String expected_result = "20.05.2021 15:05:23";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        String strTime = "15:05:23";
        String strDate = "20.05.2021";

        Date date = timeHelpers.makeDateTime(strDate, strTime);
        String actual_result = simpleDateFormat.format(date);

        assertEquals(expected_result, actual_result);
    }

}
