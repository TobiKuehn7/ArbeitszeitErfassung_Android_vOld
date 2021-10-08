package de.tkapps.arbeitszeiterfassung;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import de.tkapps.arbeitszeiterfassung.helpers.timeHelpers;

public class MainActivity extends AppCompatActivity {

    // prepare variables for UI elements
    Button btn_start, btn_end, btn_liste;
    TextView txt_beginn_arbeitszeit, txt_ende_arbeitszeit, txt_arbeitszeit_brutto, txt_pausenzeit, txt_arbeitszeit_netto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIElements();

        // change start/end button
        btn_start.setVisibility(View.VISIBLE);

    }

    /**
     * method to init the UI elements called by onCreate method
     */
    private void initUIElements() {
        // init UI elements
        btn_start = findViewById(R.id.btn_start);
        btn_end = findViewById(R.id.btn_end);
        btn_liste = findViewById(R.id.btn_liste);

        txt_beginn_arbeitszeit = findViewById(R.id.txt_beginn_arbeitszeit);
        txt_ende_arbeitszeit = findViewById(R.id.txt_ende_arbeitszeit);
        txt_arbeitszeit_brutto = findViewById(R.id.txt_arbeitszeit_brutto);
        txt_pausenzeit = findViewById(R.id.txt_pausenzeit);
        txt_arbeitszeit_netto = findViewById(R.id.txt_arbeitszeit_netto);
    }

    /**
     * this method resets all UI elements at startup of the start method to clean out all old and
     * already saved data
     * @param state represents the state the buttons are in (0 = Button was 'START', 1 = Button was 'ENDE')
     */
    private void resetUIAfterBtn(int state) {

        if (state == 0) {
            // reset all buttons
            btn_start.setVisibility(View.GONE);
            btn_end.setVisibility(View.VISIBLE);

            // reset all texts
            txt_beginn_arbeitszeit.setText(R.string.beginnArbeitszeit);
            txt_ende_arbeitszeit.setText(R.string.endeArbeitszeit);
            txt_arbeitszeit_brutto.setText(R.string.arbeitszeitBrutto);
            txt_pausenzeit.setText(R.string.pausenzeit);
            txt_arbeitszeit_netto.setText(R.string.arbeitszeitNetto);
        } else {
            btn_start.setVisibility(View.VISIBLE);
            btn_end.setVisibility(View.GONE);
        }

    }

    /**
     * This method is used for starting the time tracking. This method is called when the start button on the
     * main view is clicked.
     * After clicking the button to start, it splits into two buttons. One for pausing and one for
     * ending the time tracking.
     * This method should show the starting point of time tracking in the view and also save it to
     * a .csv file or a .xml file. TODO: Save time data as .csv or .xml file
     * @param view parse view into function
     */
    @SuppressLint("SetTextI18n")
    public void startTimeTracking(View view) {
        // set UI elements
        resetUIAfterBtn(0);

        // get time of work beginnig
        Date dateTime = new Date();
        String dateTimeStart = timeHelpers.dateToShowString(dateTime);
        txt_beginn_arbeitszeit.setText(getText(R.string.beginnArbeitszeit) + dateTimeStart);

        // save time to file and db if possible
        //save();
    }

    /**
     * This method controls what happens, if the end button is pressed
     * @param view parse view into function
     */
    @SuppressLint("SetTextI18n")
    public void endTimeTracking(View view) {
        // set UI elements
        resetUIAfterBtn(1);

        // get time of work ending
        Date dateEnd = new Date();
        String dateTimeEnd = timeHelpers.dateToShowString(dateEnd);
        txt_ende_arbeitszeit.setText(getText(R.string.endeArbeitszeit) + dateTimeEnd);

        // get start time
        String timeStart = (String) txt_beginn_arbeitszeit.getText();
        Date dateStart = timeHelpers.showStringToDate(timeStart);

        // calc difference in time between work beginning and end (brutto time)
        Date dateDifference = timeHelpers.calcTimeDiff(dateStart, dateEnd);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("MEZ"));
        txt_arbeitszeit_brutto.setText("Arbeitszeit (brutto): " + formatter.format(dateDifference));

        // if date diff is bigger than 6 hours, substract 30 mins. If it is bigger than 9 hours substract 45 mins
        int halfHour = 1800000;
        int quaterHour = 2700000;
        int sixHours = 21600000;
        int nineHours = 32400000;
        if (dateDifference.getTime() >= nineHours) {
            // substract 45 mins
            Date date = new Date(dateDifference.getTime() - quaterHour);
            txt_arbeitszeit_netto.setText("Arbeitszeit (netto): " + formatter.format(date));
        } else if (dateDifference.getTime() >= sixHours) {
            // substract 30 mins
            Date date = new Date(dateDifference.getTime() - halfHour);
            txt_arbeitszeit_netto.setText("Arbeitszeit (netto): " + formatter.format(date));
        } else {
            // dont need to substract anything
            Date date = new Date(dateDifference.getTime());
            txt_arbeitszeit_netto.setText("Arbeitszeit (netto): " + formatter.format(date));
        }

        // save time to file and db if possible
        //save();
    }

}