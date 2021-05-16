package de.tkapps.arbeitszeiterfassung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    // prepare variables for UI elements
    Button btn_start, btn_pause, btn_end, btn_end_full, btn_liste;
    TextView txt_datum_arbeitszeit, txt_beginn_arbeitszeit, txt_beginn_pause_1, txt_ende_pause_1, txt_beginn_pause_2, txt_ende_pause_2, txt_ende_arbeitszeit, txt_arbeitszeit_brutto, txt_pausenzeit, txt_arbeitszeit_netto;
    LinearLayout pauseEndBtnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init UI elements
        btn_start = findViewById(R.id.btn_start);
        btn_pause = findViewById(R.id.btn_pause);
        btn_end = findViewById(R.id.btn_end);
        btn_end_full = findViewById(R.id.btn_end_full);
        btn_liste = findViewById(R.id.btn_liste);

        pauseEndBtnView = findViewById(R.id.pauseEndBtnView);

        txt_datum_arbeitszeit = findViewById(R.id.txt_datum_arbeitszeit);
        txt_beginn_arbeitszeit = findViewById(R.id.txt_beginn_arbeitszeit);
        txt_beginn_pause_1 = findViewById(R.id.txt_beginn_pause_1);
        txt_ende_pause_1 = findViewById(R.id.txt_ende_pause_1);
        txt_beginn_pause_2 = findViewById(R.id.txt_beginn_pause_2);
        txt_ende_pause_2 = findViewById(R.id.txt_ende_pause_2);
        txt_ende_arbeitszeit = findViewById(R.id.txt_ende_arbeitszeit);
        txt_arbeitszeit_brutto = findViewById(R.id.txt_arbeitszeit_brutto);
        txt_pausenzeit = findViewById(R.id.txt_pausenzeit);
        txt_arbeitszeit_netto = findViewById(R.id.txt_arbeitszeit_netto);

        // change start/end button
        btn_start.setVisibility(View.VISIBLE);
        pauseEndBtnView.setVisibility(View.GONE);

        // get date of work day
        SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date(System.currentTimeMillis());
        txt_datum_arbeitszeit.setText("Datum: " + formatter.format(date));

    }

    public void startTimeTracking(View view) {
        btn_start.setVisibility(View.GONE);
        pauseEndBtnView.setVisibility(View.VISIBLE);
        txt_beginn_pause_1.setVisibility(View.GONE);
        txt_ende_pause_1.setVisibility(View.GONE);
        txt_beginn_pause_2.setVisibility(View.GONE);
        txt_ende_pause_2.setVisibility(View.GONE);

        // further actions

        // reset all texts
        txt_beginn_arbeitszeit.setText("Datum");
        txt_beginn_pause_1.setText("1. Pause Beginn: ");
        txt_ende_pause_1.setText("1. Pause Ende: ");
        txt_beginn_pause_2.setText("2. Pause Beginn: ");
        txt_ende_pause_2.setText("2. Pause Ende: ");
        txt_ende_arbeitszeit.setText("Ende: ");
        txt_arbeitszeit_brutto.setText("Arbeitszeit (brutto): ");
        txt_pausenzeit.setText("Pausenzeit: ");
        txt_arbeitszeit_netto.setText("Arbeitszeit (netto): ");

        // get time of work beginnig
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        txt_beginn_arbeitszeit.setText("Beginn: " + formatter.format(date));

    }

    public void pauseUnpauseTimeTracking(View view) {
        if (btn_pause.getText().equals("PAUSE")) {
            // change pause button
            btn_pause.setText("WEITER");

            // further actions

            // get time of pause begin
            SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
            Date pauseStart = new Date(System.currentTimeMillis());

            // add pause texts to view
            if (txt_beginn_pause_1.getVisibility() == View.GONE) {

                txt_beginn_pause_1.setVisibility(View.VISIBLE);
                txt_ende_pause_1.setVisibility(View.VISIBLE);
                txt_pausenzeit.setVisibility(View.VISIBLE);

                txt_beginn_pause_1.setText("1. Pause Beginn: " + formatter.format(pauseStart));

            } else {

                txt_beginn_pause_2.setVisibility(View.VISIBLE);
                txt_ende_pause_2.setVisibility(View.VISIBLE);

                txt_beginn_pause_2.setText("2. Pause Beginn: " + formatter.format(pauseStart));

            }



        } else {
            // change pause button
            btn_pause.setText("PAUSE");

            // further actions

            // get time of pause end
            SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
            Date pauseEnde = new Date(System.currentTimeMillis());

            // add pause texts to view
            if (txt_ende_pause_1.getVisibility() == View.VISIBLE && txt_ende_pause_2.getVisibility() == View.GONE) {

                txt_ende_pause_1.setText("1. Pause Ende: " + formatter.format(pauseEnde));

            } else {

                // change start/end button
                pauseEndBtnView.setVisibility(View.GONE);
                btn_end_full.setVisibility(View.VISIBLE);

                txt_ende_pause_2.setText("2. Pause Ende: " + formatter.format(pauseEnde));

            }

        }
    }

    public void endTimeTracking(View view) {
        // change start/end button
        pauseEndBtnView.setVisibility(View.GONE);
        btn_end_full.setVisibility(View.GONE);
        btn_start.setVisibility(View.VISIBLE);

        // get time of work end
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        Date dateEnd = new Date(System.currentTimeMillis());
        txt_ende_arbeitszeit.setText("Ende: " + formatter.format(dateEnd));

        // get time of work beginnig
        Date dateStart = new Date();
        try {
            String datumArbeitszeit = (String) txt_datum_arbeitszeit.getText();
            String beginnArbeitszeit = (String) txt_beginn_arbeitszeit.getText();
            SimpleDateFormat formatFull = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
            dateStart = formatFull.parse(datumArbeitszeit.split(": ")[1] + " " + beginnArbeitszeit.split(": ")[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // calc difference in time between work beginning and end (brutto time)
        Date dateDifference = calcTimeDiff(dateStart, dateEnd);
        txt_arbeitszeit_brutto.setText("Arbeitszeit (brutto): " + formatter.format(dateDifference));

        // calc difference in time between work beginnig and end with pauses (netto time)

        Date datePause1Start, datePause1End, datePause2Start, datePause2End;
        String datumArbeitszeit = (String) txt_datum_arbeitszeit.getText();

        // were not used
        datePause1Start = null;
        datePause1End = null;
        datePause2Start = null;
        datePause2End = null;

        if (txt_beginn_pause_1.getVisibility() == View.GONE) {

            // were not used
            datePause1Start = new Date(0);
            datePause1End = new Date(0);
            datePause2Start = new Date(0);
            datePause2End = new Date(0);

        } else if (txt_beginn_pause_1.getVisibility() == View.VISIBLE && txt_beginn_pause_2.getVisibility() == View.GONE) {

            try {

                SimpleDateFormat formatFull = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");

                String pause1Start = (String) txt_beginn_pause_1.getText();
                datePause1Start = formatFull.parse(datumArbeitszeit.split(": ")[1] + " " + pause1Start.split(": ")[1]);

                String pause1Ende = (String) txt_ende_pause_1.getText();
                datePause1End = formatFull.parse(datumArbeitszeit.split(": ")[1] + " " + pause1Ende.split(": ")[1]);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            // were not used
            datePause2Start = new Date(0);
            datePause2End = new Date(0);

        } else {

            try {

                SimpleDateFormat formatFull = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");

                String pause1Start = (String) txt_beginn_pause_1.getText();
                datePause1Start = formatFull.parse(datumArbeitszeit.split(": ")[1] + " " + pause1Start.split(": ")[1]);

                String pause1Ende = (String) txt_ende_pause_1.getText();
                datePause1End = formatFull.parse(datumArbeitszeit.split(": ")[1] + " " + pause1Ende.split(": ")[1]);

                String pause2Start = (String) txt_beginn_pause_2.getText();
                datePause2Start = formatFull.parse(datumArbeitszeit.split(": ")[1] + " " + pause2Start.split(": ")[1]);

                String pause2Ende = (String) txt_ende_pause_2.getText();
                datePause2End = formatFull.parse(datumArbeitszeit.split(": ")[1] + " " + pause2Ende.split(": ")[1]);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        try {
            SimpleDateFormat formatFull = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
            String arbeitszeitBrutto = (String) txt_arbeitszeit_brutto.getText();
            dateDifference = formatFull.parse(datumArbeitszeit.split(": ")[1] + " " + arbeitszeitBrutto.split(": ")[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date[] timesWithPauses = new Date[2];
        timesWithPauses = calcTimeDiffWithPauses(dateDifference, datePause1Start, datePause1End, datePause2Start, datePause2End);
        txt_arbeitszeit_netto.setText("Arbeitszeit (netto): " + formatter.format(timesWithPauses[0]));
        formatter.applyPattern("mm:ss");
        txt_pausenzeit.setText("Pausenzeit: " + formatter.format(timesWithPauses[1]));

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
    private Date[] calcTimeDiffWithPauses(Date dateDifference, Date datePause1Start, Date datePause1End, Date datePause2Start, Date datePause2End) {
        // prepare returning array
        Date[] datesWithPauses = new Date[2];


        // date 1 for array
        long pause1Diff = datePause1End.getTime() - datePause1Start.getTime();
        Date pausenzeit1 = new Date(pause1Diff);

        // date 2 for array
        long pause2Diff = datePause2End.getTime() - datePause2Start.getTime();
        Date pausenzeit2 = new Date(pause2Diff);

        // pause time total
        long pauseTotal = pausenzeit1.getTime() + pausenzeit2.getTime();
        Date pausenzeit = new Date(pauseTotal);

        // work time netto
        long arbeitszeit = dateDifference.getTime() - pausenzeit.getTime();
        Date arbeitszeitNetto = new Date(arbeitszeit);

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
    private Date calcTimeDiff(Date dateStart, Date dateEnd) {
        long timeDifference = dateEnd.getTime() - dateStart.getTime();
        Date diff = new Date(timeDifference);

        return diff;
    }
}