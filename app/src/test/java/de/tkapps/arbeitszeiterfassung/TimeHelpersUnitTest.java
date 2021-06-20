package de.tkapps.arbeitszeiterfassung;

import android.provider.ContactsContract;
import android.view.DisplayCutout;

import org.junit.Test;

import java.security.Signature;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.tkapps.arbeitszeiterfassung.helpers.timeHelpers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TimeHelpersUnitTest {

    @Test
    public void getDate_isCorrect() {
        // expected date
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String expectedDate = simpleDateFormat.format(date);

        // actual date from function
        String actualDate = timeHelpers.getDate();

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void getTime_isCorrect() {
        // expected date
        Date time = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String expectedTime = simpleDateFormat.format(time);

        // actual date from function
        String actualTime = timeHelpers.getTime();

        assertEquals(expectedTime, actualTime);
    }

    @Test
    public void makeDateTime_isCorrect() {
        // expected datetime
        String expectedDateTime = "25.06.2021 10:15:32";

        // actual datetime from function
        String date = expectedDateTime.split(" ")[0];
        String time = expectedDateTime.split(" ")[1];

        Date dteActualDateTime = timeHelpers.makeDateTime(date, time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMAN);
        String actualDateTime = simpleDateFormat.format(dteActualDateTime);

        assertEquals(expectedDateTime, actualDateTime);
    }

    @Test
    public void calcTimeDiff() {

        // get a formatter and prepare data
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMAN);
        Long dateBegin = null;
        Long dateEnd = null;

        String stringBegin = "25.06.2021 08:15:32";
        String stringEnd = "25.06.2021 17:10:38";

        try {
            dateBegin = simpleDateFormat.parse(stringEnd).getTime();
            dateEnd = simpleDateFormat.parse(stringBegin).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // get expected date
        Date diffExpected = new Date(dateEnd - dateBegin);

        // get actual date
        Date diffActual = timeHelpers.calcTimeDiff(new Date(dateBegin), new Date(dateEnd));

        // compare expected and actual date
        assertEquals(diffExpected, diffActual);

    }

    @Test
    public void calcTimeDiffWithPauses_1Pause_isCorrect() {

        // prepare data

        String stringBegin = "08:15:32";
        String stringPauseBegin = "12:10:38";
        String stringPauseEnd = "12:40:38";
        String stringEnd = "17:10:38";

        Date dateBegin = null;
        Date datePauseBegin = null;
        Date datePauseEnd = null;
        Date dateEnd = null;


        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("MEZ"));

        try {
            dateBegin = formatter.parse(stringBegin);
            datePauseBegin = formatter.parse(stringPauseBegin);
            datePauseEnd = formatter.parse(stringPauseEnd);
            dateEnd = formatter.parse(stringEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // calc diffs
        Date diffDay = timeHelpers.calcTimeDiff(dateBegin, dateEnd);
        Date diffPause = timeHelpers.calcTimeDiff(datePauseBegin, datePauseEnd);

        // get expected data ready
        Date expectedNettoWorkTime = timeHelpers.calcTimeDiff(diffPause, diffDay);
        Date expectedPauseTime = diffPause;
        Date[] expetedDates = new Date[2];
        expetedDates[0] = expectedNettoWorkTime;
        expetedDates[1] = expectedPauseTime;

        // get actual data ready
        Date[] actualDates = timeHelpers.calcTimeDiffWithPauses(diffDay, datePauseBegin, datePauseEnd, new Date(0), new Date(0));

        // compare expected and actual data
        assertArrayEquals(expetedDates, actualDates);

    }

    @Test
    public void calcTimeDiffWithPauses_2Pauses_isCorrect() {

        // prepare data

        String stringBegin = "08:15:32";
        String stringPauseBegin1 = "12:10:38";
        String stringPauseEnd1 = "12:40:38";
        String stringPauseBegin2 = "16:10:38";
        String stringPauseEnd2 = "16:40:38";
        String stringEnd = "17:10:38";

        Date dateBegin = null;
        Date datePauseBegin1 = null;
        Date datePauseEnd1 = null;
        Date datePauseBegin2 = null;
        Date datePauseEnd2 = null;
        Date dateEnd = null;


        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("MEZ"));

        try {
            dateBegin = formatter.parse(stringBegin);
            datePauseBegin1 = formatter.parse(stringPauseBegin1);
            datePauseEnd1 = formatter.parse(stringPauseEnd1);
            datePauseBegin2 = formatter.parse(stringPauseBegin2);
            datePauseEnd2 = formatter.parse(stringPauseEnd2);
            dateEnd = formatter.parse(stringEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // calc diffs
        Date diffDay = timeHelpers.calcTimeDiff(dateBegin, dateEnd);
        Date diffPause1 = timeHelpers.calcTimeDiff(datePauseBegin1, datePauseEnd1);
        Date diffPause2 = timeHelpers.calcTimeDiff(datePauseBegin2, datePauseEnd2);
        Date diffPauses = new Date(diffPause1.getTime() + diffPause2.getTime());

        // get expected data ready
        Date expectedNettoWorkTime = timeHelpers.calcTimeDiff(diffPause1, timeHelpers.calcTimeDiff(diffPause2, diffDay));
        Date expectedPauseTime = diffPauses;
        Date[] expetedDates = new Date[2];
        expetedDates[0] = expectedNettoWorkTime;
        expetedDates[1] = expectedPauseTime;

        // get actual data ready
        Date[] actualDates = timeHelpers.calcTimeDiffWithPauses(diffDay, datePauseBegin1, datePauseEnd1, datePauseBegin1, datePauseEnd1);

        // compare expected and actual data
        assertArrayEquals(expetedDates, actualDates);

    }

}
