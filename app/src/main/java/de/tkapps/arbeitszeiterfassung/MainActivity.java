package de.tkapps.arbeitszeiterfassung;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import de.tkapps.arbeitszeiterfassung.helpers.TimeHelpers;
import de.tkapps.arbeitszeiterfassung.helpers.SavingHelpers;
import de.tkapps.arbeitszeiterfassung.models.Workday;

public class MainActivity extends AppCompatActivity {

    // prepare variables for UI elements
    Button btn_start, btn_end, btn_liste;
    TextView txt_beginn_arbeitszeit, txt_ende_arbeitszeit, txt_arbeitszeit_brutto, txt_pausenzeit, txt_arbeitszeit_netto;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIElements();

        // change start/end button
        btn_start.setVisibility(View.VISIBLE);

        if (SavingHelpers.fileHasStartedWorkday(MainActivity.this)) {
            resetUIAfterBtn(0);
            Workday startedWorkday = SavingHelpers.getStartedWorkday(MainActivity.this);
            Date startDateTime = TimeHelpers.saveStringToDate(startedWorkday.getDateTimeStart());
            String startTime = TimeHelpers.dateToShowString(startDateTime);
            txt_beginn_arbeitszeit.setText("Beginn: " + startTime);
        }
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
     * a .csv file or a .xml file.
     * @param view parse view into function
     */
    @SuppressLint("SetTextI18n")
    public void startTimeTracking(View view) {
        // set UI elements
        resetUIAfterBtn(0);

        // get time of work beginnig
        Date dateTime = new Date();
        String dateTimeStart = TimeHelpers.dateToShowString(dateTime);
        txt_beginn_arbeitszeit.setText(getText(R.string.beginnArbeitszeit) + dateTimeStart);

        // save time to file and db if possible
        SavingHelpers.saveStartTime(this, TimeHelpers.dateToSaveString(dateTime));
        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_LONG).show();
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
        String dateTimeEnd = TimeHelpers.dateToShowString(dateEnd);
        txt_ende_arbeitszeit.setText(getText(R.string.endeArbeitszeit) + dateTimeEnd);

        // get start time
        String timeStart = (String) txt_beginn_arbeitszeit.getText();
        Date dateStart = TimeHelpers.showStringToDate(timeStart);

        // calc difference in time between work beginning and end (brutto time)
        Date dateDifference = TimeHelpers.calcTimeDiff(dateStart, dateEnd);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("MEZ"));
        txt_arbeitszeit_brutto.setText("Arbeitszeit (brutto): " + formatter.format(dateDifference));
        String workTimeBrutto = formatter.format(dateDifference);
        String workTimeNetto;
        String pauseTime;

        // if date diff is bigger than 6 hours, substract 30 mins. If it is bigger than 9 hours substract 45 mins
        int halfHour = 1800000;
        int quaterHour = 2700000;
        int sixHours = 21600000;
        int nineHours = 32400000;
        if (dateDifference.getTime() >= nineHours) {
            // substract 45 mins
            Date date = new Date(dateDifference.getTime() - quaterHour);
            txt_arbeitszeit_netto.setText("Arbeitszeit (netto): " + formatter.format(date));
            workTimeNetto = formatter.format(date);
            date = new Date(quaterHour);
            txt_pausenzeit.setText("Pausenzeit: " + formatter.format(date));
            pauseTime = formatter.format(date);
            txt_pausenzeit.setVisibility(View.VISIBLE);
        } else if (dateDifference.getTime() >= sixHours) {
            // substract 30 mins
            Date date = new Date(dateDifference.getTime() - halfHour);
            txt_arbeitszeit_netto.setText("Arbeitszeit (netto): " + formatter.format(date));
            workTimeNetto = formatter.format(date);
            date = new Date(halfHour);
            txt_pausenzeit.setText("Pausenzeit: " + formatter.format(date));
            pauseTime = formatter.format(date);
            txt_pausenzeit.setVisibility(View.VISIBLE);
        } else {
            // dont need to substract anything
            Date date = new Date(dateDifference.getTime());
            txt_arbeitszeit_netto.setText("Arbeitszeit (netto): " + formatter.format(date));
            workTimeNetto = formatter.format(date);
            date = new Date(0);
            pauseTime = formatter.format(date);
        }

        // get start time
        String startTime = (String) txt_beginn_arbeitszeit.getText();
        startTime = startTime.split(": ")[1];
        Date dateTimeStart = TimeHelpers.showStringToDate(startTime);

        // save time to file and db if possible
        SavingHelpers.saveEndTime(MainActivity.this,TimeHelpers.dateToSaveString(dateTimeStart), TimeHelpers.dateToSaveString(dateEnd), workTimeBrutto, workTimeNetto, pauseTime);
        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_LONG).show();

        resetUIAfterBtn(1);
    }

}