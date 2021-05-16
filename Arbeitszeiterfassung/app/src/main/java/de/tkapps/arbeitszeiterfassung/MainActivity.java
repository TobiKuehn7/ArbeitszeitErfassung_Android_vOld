package de.tkapps.arbeitszeiterfassung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import de.tkapps.arbeitszeiterfassung.helpers.timeHelpers;

public class MainActivity extends AppCompatActivity {

    // prepare variables for UI elements
    Button btn_start, btn_pause, btn_end, btn_end_full, btn_liste;
    TextView txt_datum_arbeitszeit, txt_beginn_arbeitszeit, txt_beginn_pause_1, txt_ende_pause_1, txt_beginn_pause_2, txt_ende_pause_2, txt_ende_arbeitszeit, txt_arbeitszeit_brutto, txt_pausenzeit, txt_arbeitszeit_netto;
    LinearLayout pauseEndBtnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIElements();

        // change start/end button
        btn_start.setVisibility(View.VISIBLE);
        pauseEndBtnView.setVisibility(View.GONE);

        // get date of work day
        String date = timeHelpers.getDate();
        txt_datum_arbeitszeit.setText(getText(R.string.datum) + date);

    }

    /**
     * method to init the UI elements called by onCreate method
     */
    private void initUIElements() {
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
    }

    /**
     * This method is used for starting the time tracking. This method is called when the start button on the
     * main view is clicked.
     * After clicking the button to start, it splits into two buttons. One for pausing and one for
     * ending the time tracking.
     * This method should show the starting point of time tracking in the view and also save it to
     * a .csv file or a .xml file. TODO: Save time data as .csv or .xml file
     * @param view
     */
    public void startTimeTracking(View view) {
        resetUIStart();

        // get time of work beginnig
        String time = timeHelpers.getTime();
        txt_beginn_arbeitszeit.setText(getText(R.string.beginnArbeitszeit) + time);
    }

    /**
     * this method resets all UI elements at startup of the start method to clean out all old and
     * already saved data
     */
    private void resetUIStart() {

        // reset all buttons
        btn_start.setVisibility(View.GONE);
        pauseEndBtnView.setVisibility(View.VISIBLE);
        txt_beginn_pause_1.setVisibility(View.GONE);
        txt_ende_pause_1.setVisibility(View.GONE);
        txt_beginn_pause_2.setVisibility(View.GONE);
        txt_ende_pause_2.setVisibility(View.GONE);

        // reset all texts
        txt_beginn_arbeitszeit.setText(R.string.beginnArbeitszeit);
        txt_beginn_pause_1.setText(R.string.pause1Beginn);
        txt_ende_pause_1.setText(R.string.pause1Ende);
        txt_beginn_pause_2.setText(R.string.pause2Beginn);
        txt_ende_pause_2.setText(R.string.pause2Ende);
        txt_ende_arbeitszeit.setText(R.string.endeArbeitszeit);
        txt_arbeitszeit_brutto.setText(R.string.arbeitszeitBrutto);
        txt_pausenzeit.setText(R.string.pausenzeit);
        txt_arbeitszeit_netto.setText(R.string.arbeitszeitNetto);

    }

    /**
     * This method cotrolls the behavior of the pause and unpause button.
     * The expected behavior is the following:
     * If the button is clicked, showing the text 'PAUSE' the text shouldt change to 'WEITER' and the other way around.
     * If the information field above is not showing anything about pause, the first pause is added there.
     * If the information field above is already showing the first pause, a second pause can be added.
     * If the information field is showing two pauses the button is turned into a big button, which just lets you
     * end the time tracking.
     *
     * At every press on a pause or unpause button the date should be saved to a .csv or .xml file
     * TODO: Save time data as .csv or .xml file
     * @param view
     */
    public void pauseUnpauseTimeTracking(View view) {

        // first case: button is showing PAUSE
        // second case: button is showing WEITER
        if (btn_pause.getText().equals("PAUSE")) {

            // change pause button
            btn_pause.setText(R.string.weiter);

            // further actions

            // get time of pause begin
            String pauseStart = timeHelpers.getTime();

            // first option: no pauses in information field above -> need to add them to view
            // second option: first pause already in information field above -> need to add the second pause to view
            if (txt_beginn_pause_1.getVisibility() == View.GONE) {

                txt_beginn_pause_1.setVisibility(View.VISIBLE);
                txt_ende_pause_1.setVisibility(View.VISIBLE);
                txt_pausenzeit.setVisibility(View.VISIBLE);

                txt_beginn_pause_1.setText(getText(R.string.pause1Beginn) + pauseStart);

            } else {

                txt_beginn_pause_2.setVisibility(View.VISIBLE);
                txt_ende_pause_2.setVisibility(View.VISIBLE);

                txt_beginn_pause_2.setText(getText(R.string.pause2Beginn) + pauseStart);

            }

        } else {

            // change pause button
            btn_pause.setText(R.string.pause);

            // further actions

            // get time of pause end
            String pauseEnde = timeHelpers.getTime();

            // first option: pause 1 is already started, but not finished
            // second option: pause 2 is already started
            if (txt_ende_pause_1.getVisibility() == View.VISIBLE && txt_ende_pause_2.getVisibility() == View.GONE) {

                txt_ende_pause_1.setText(getText(R.string.pause1Ende) + pauseEnde);

            } else {

                // change start/end button
                pauseEndBtnView.setVisibility(View.GONE);
                btn_end_full.setVisibility(View.VISIBLE);

                txt_ende_pause_2.setText(getText(R.string.pause2Ende) + pauseEnde);

            }

        }
    }

    /**
     * This methd controlls what happens, if the end button is pressed
     * @param view
     */
    public void endTimeTracking(View view) {

        if (!btn_pause.getText().equals("WEITER")) {
            // change start/end button
            pauseEndBtnView.setVisibility(View.GONE);
            btn_end_full.setVisibility(View.GONE);
            btn_start.setVisibility(View.VISIBLE);

            // get date of work day
            String datumArbeitszeit = timeHelpers.getDate();

            // get datetime of work end
            String timeEnd = timeHelpers.getTime();
            txt_ende_arbeitszeit.setText(getText(R.string.endeArbeitszeit) + timeEnd);
            Date dateTimeEnd = timeHelpers.makeDateTime(datumArbeitszeit, timeEnd);

            // get datetime of work beginnig
            String beginnArbeitszeit = (String) txt_beginn_arbeitszeit.getText();
            Date dateTimeStart = timeHelpers.makeDateTime(datumArbeitszeit, beginnArbeitszeit.split(": ")[1]);

            // calc difference in time between work beginning and end (brutto time)
            Date dateDifference = timeHelpers.calcTimeDiff(dateTimeStart, dateTimeEnd);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("MEZ"));
            txt_arbeitszeit_brutto.setText("Arbeitszeit (brutto): " + formatter.format(dateDifference));

            // calc difference in time between work beginnig and end with pauses (netto time)
            Date datePause1Start, datePause1End, datePause2Start, datePause2End;

            // were not used yet
            datePause1Start = null;
            datePause1End = null;
            datePause2Start = null;
            datePause2End = null;

            if (txt_beginn_pause_1.getVisibility() == View.GONE) {

                // are not used because no pauses taken
                datePause1Start = new Date(0);
                datePause1End = new Date(0);
                datePause2Start = new Date(0);
                datePause2End = new Date(0);

            } else if (txt_beginn_pause_1.getVisibility() == View.VISIBLE && txt_beginn_pause_2.getVisibility() == View.GONE) {

                // only one pause taken
                try {

                    SimpleDateFormat formatFull = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");

                    String pause1Start = (String) txt_beginn_pause_1.getText();
                    datePause1Start = formatFull.parse(timeHelpers.getDate() + " " + pause1Start.split(": ")[1]);

                    String pause1Ende = (String) txt_ende_pause_1.getText();
                    datePause1End = formatFull.parse(timeHelpers.getDate() + " " + pause1Ende.split(": ")[1]);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // were not used
                datePause2Start = new Date(0);
                datePause2End = new Date(0);

            } else {

                // both pauses taken
                try {

                    SimpleDateFormat formatFull = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");

                    String pause1Start = (String) txt_beginn_pause_1.getText();
                    datePause1Start = formatFull.parse(timeHelpers.getDate() + " " + pause1Start.split(": ")[1]);

                    String pause1Ende = (String) txt_ende_pause_1.getText();
                    datePause1End = formatFull.parse(timeHelpers.getDate() + " " + pause1Ende.split(": ")[1]);

                    String pause2Start = (String) txt_beginn_pause_2.getText();
                    datePause2Start = formatFull.parse(timeHelpers.getDate() + " " + pause2Start.split(": ")[1]);

                    String pause2Ende = (String) txt_ende_pause_2.getText();
                    datePause2End = formatFull.parse(timeHelpers.getDate() + " " + pause2Ende.split(": ")[1]);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            try {
                SimpleDateFormat formatFull = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
                String arbeitszeitBrutto = (String) txt_arbeitszeit_brutto.getText();
                dateDifference = formatFull.parse(timeHelpers.getDate() + " " + arbeitszeitBrutto.split(": ")[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date[] timesWithPauses = timeHelpers.calcTimeDiffWithPauses(dateDifference, datePause1Start, datePause1End, datePause2Start, datePause2End);
            txt_pausenzeit.setText("Pausenzeit: " + formatter.format(timesWithPauses[1]));
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+01"));
            txt_arbeitszeit_netto.setText("Arbeitszeit (netto): " + formatter.format(timesWithPauses[0]));

        } else {
            Toast.makeText(MainActivity.this, "Du musst erst die Pause beenden, bevor du die Arbeitszeit beenden kannst!", Toast.LENGTH_LONG).show();
        }
    }

}