package de.tkapps.arbeitszeiterfassung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.tkapps.arbeitszeiterfassung.helpers.SavingHelpers;
import de.tkapps.arbeitszeiterfassung.models.Workday;

public class ListActivity extends AppCompatActivity {

    ListView wordayListView;

    ArrayList<Workday> workdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        workdays = (ArrayList<Workday>) SavingHelpers.getAllWorkdays(ListActivity.this);

        wordayListView = findViewById(R.id.workdayList);


        CustomAdapter customAdapter = new CustomAdapter();
        wordayListView.setAdapter(customAdapter);
        wordayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListActivity.this, workdays.get(i).getHash(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openMoreInformation(View view) {
        /*Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);*/
        Toast.makeText(ListActivity.this, "Open More Information", Toast.LENGTH_LONG).show();
    }

    public void addWorkday(View view) {
        Toast.makeText(ListActivity.this, "Add a new Workday", Toast.LENGTH_LONG).show();
    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return workdays.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View layout = getLayoutInflater().inflate(R.layout.workday_list_item, null);
            TextView startTime = layout.findViewById(R.id.txt_beginn_arbeitszeit);
            TextView endTime = layout.findViewById(R.id.txt_ende_arbeitszeit);
            TextView nettoTime = layout.findViewById(R.id.txt_arbeitszeit_netto);
            Button moreInformationBtn = layout.findViewById(R.id.moreInformationButton);

            startTime.setText("Beginn: " + workdays.get(i).getDateTimeStart());
            endTime.setText("Ende: " + workdays.get(i).getDateTimeEnd());
            nettoTime.setText("Brutto Arbeitszeit: " + workdays.get(i).getWorkTimeNetto());

            return layout;
        }
    }
}