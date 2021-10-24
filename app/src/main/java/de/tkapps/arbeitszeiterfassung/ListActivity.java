package de.tkapps.arbeitszeiterfassung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import de.tkapps.arbeitszeiterfassung.helpers.SavingHelpers;
import de.tkapps.arbeitszeiterfassung.models.Workday;

public class ListActivity extends AppCompatActivity {

    ListView wordayListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        wordayListView = findViewById(R.id.workdayList);

        ArrayList<Workday> workdays = (ArrayList<Workday>) SavingHelpers.getAllWorkdays(ListActivity.this);

        ArrayList<String> workdayTimes = new ArrayList<>();

        for (Workday workday:workdays) {
            String stringToSave = workday.getDateTimeStart() + ", " + workday.getDateTimeEnd() + "; " +workday.getWorkTimeBrutto();
            workdayTimes.add(stringToSave);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, workdayTimes);

        wordayListView.setAdapter(arrayAdapter);
    }
}