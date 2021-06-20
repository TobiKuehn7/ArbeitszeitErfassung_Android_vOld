package de.tkapps.arbeitszeiterfassung.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class timeHelpers {

    /**
     * method to get date and convert to string
     * @return date as string
     */
    public static String getDate() {

        // make a formatter that can parse the date
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat formatter= new SimpleDateFormat(pattern, Locale.GERMAN);

        // get datetime
        Date date = new Date(System.currentTimeMillis());

        // return formatted date
        return formatter.format(date);
    }

    /**
     * method to get time and convert to string
     * @return time as string
     */
    public static String getTime() {

        // make a formatter that can parse the date
        String pattern = "HH:mm:ss";
        SimpleDateFormat formatter= new SimpleDateFormat(pattern, Locale.GERMAN);

        // get datetime
        Date time = new Date(System.currentTimeMillis());

        // return formatted time
        return formatter.format(time);
    }

    /**
     * method to convert a datetime string into a date object
     * @param date string that contains a date in format "dd.MM.yyyy"
     * @param time string that contains a time in format "HH:mm:ss"
     * @return returns the date object of the datetime strings passed in
     */
    public static Date makeDateTime(String date, String time) {

        // make new Date object which will be returned
        Date dateTime = new Date();

        // make a formatted datetime string from input
        String strDateTime = date + " " + time;

        // make a formatter that can parse that date
        String pattern = "dd.MM.yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.GERMAN);

        // parse datetime
        try {
            dateTime = simpleDateFormat.parse(strDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // return date object of passed strings
        return dateTime;
    }

    /**
     * methode to calculate the netto work time and the total pause time
     * @param dateDifference difference between work start and work end
     * @param datePause1Start datetime object of pause 1 beginning
     * @param datePause1End datetime object of pause 1 ending
     * @param datePause2Start datetime object of pause 2 beginning
     * @param datePause2End datetime object of pause 2 ending
     * @return the pause time and the netto working time
     */
    public static Date[] calcTimeDiffWithPauses(Date dateDifference, Date datePause1Start, Date datePause1End, Date datePause2Start, Date datePause2End) {
        // prepare array for returning
        Date[] datesWithPauses = new Date[2];

        // date 1 for array
        long pause1Diff = datePause1End.getTime() - datePause1Start.getTime();

        Date arbeitszeitNetto;
        Date pausenzeit;
        String pausenZeitFormatted;

        if (!datePause2Start.equals(new Date(0))) {

            // date 2 for array
            long pause2Diff = datePause2End.getTime() - datePause2Start.getTime();

            // pause time total
            long pauseTotal = pause1Diff + pause2Diff;
            pausenzeit = new Date(pauseTotal);

            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("MEZ"));
            pausenZeitFormatted = formatter.format(pausenzeit);

            // work time netto
            long arbeitszeit = dateDifference.getTime() - pausenzeit.getTime();
            arbeitszeitNetto = new Date(arbeitszeit);


        } else {

            // pause time total
            long pauseTotal = pause1Diff;
            pausenzeit = new Date(pauseTotal);
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("MEZ"));
            pausenZeitFormatted = formatter.format(pausenzeit);

            // work time netto
            long arbeitszeit = dateDifference.getTime() - pausenzeit.getTime();
            arbeitszeitNetto = new Date(arbeitszeit);

        }

        // set dates into array
        datesWithPauses[0] = arbeitszeitNetto;
        datesWithPauses[1] = pausenzeit;


        // return array
        return datesWithPauses;
    }

    /**
     * methode to calculate the time difference between start and end of working day
     * @param dateStart datetime from work start
     * @param dateEnd datetime from work end
     * @return the time difference between work start and work end
     */
    public static Date calcTimeDiff(Date dateStart, Date dateEnd) {
        long timeDifference = dateEnd.getTime() - dateStart.getTime();
        Date diff = new Date(timeDifference);

        return diff;
    }
}
