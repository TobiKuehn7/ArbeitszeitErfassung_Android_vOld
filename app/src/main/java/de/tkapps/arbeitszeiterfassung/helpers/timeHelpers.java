package de.tkapps.arbeitszeiterfassung.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

public class timeHelpers {

    /**
     * method to get date and convert to string
     * @return date as string
     */
    public static String getDate() {
        SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static String getTime() {
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static Date makeDateTime(String date, String time) {

        String strDate = null;
        String strTime = null;

        Date dateTime = null;

        char[] dateC = date.toCharArray();
        char[] timeC = time.toCharArray();

        strDate = new String(dateC);
        strTime = new String(timeC);

        try {

            SimpleDateFormat formatFull =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            dateTime = formatFull.parse(strDate + " " + strTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime;
    }

    /**
     * methode to calculate the netto work time and the total pause time
     * @param dateDifference
     * @param datePause1Start
     * @param datePause1End
     * @param datePause2Start
     * @param datePause2End
     * @return
     */
    public static Date[] calcTimeDiffWithPauses(Date dateDifference, Date datePause1Start, Date datePause1End, Date datePause2Start, Date datePause2End) {
        // prepare returning array
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
     * @param dateStart
     * @param dateEnd
     * @return
     */
    public static Date calcTimeDiff(Date dateStart, Date dateEnd) {
        long timeDifference = dateEnd.getTime() - dateStart.getTime();
        Date diff = new Date(timeDifference);

        return diff;
    }
}
