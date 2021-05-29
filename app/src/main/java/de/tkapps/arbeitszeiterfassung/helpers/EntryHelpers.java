package de.tkapps.arbeitszeiterfassung.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.util.TimeZone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.tkapps.arbeitszeiterfassung.MainActivity;
import de.tkapps.arbeitszeiterfassung.models.Entry;

public class EntryHelpers {

    public String filename = "";
    public String filepath = "";
    public String fileContent = "";
    public List<Entry> entries = new ArrayList<>();

    public EntryHelpers(Context context) {
        if (filename.equals("") && filepath.equals("") && fileContent.equals("")) {

            // get file parts
            String[] filePathParts = getFilePath();
            filename = filePathParts[1] + ".csv";
            filepath = filePathParts[0];

            // load data from file to a string
            fileContent = loadAllData(filepath, filename, context);
            
            // put data into an entries list
            if (! fileContent.equals("")) {
                entries = dataToList();
            }

        }
    }

    private List<Entry> dataToList() {
        List<Entry> entryList = new ArrayList<>();
        String[] lines = fileContent.split("\n");
        for (String line : lines) {
            String[] entryData = line.split(",");

            Entry entry = new Entry();

            entry.setDateBegin(entryData[0]);
            entry.setTimeBegin(entryData[1]);
            entry.setDatePause1Begin(entryData[2]);
            entry.setTimePause1Begin(entryData[3]);
            entry.setDatePause1End(entryData[4]);
            entry.setTimePause1End(entryData[5]);
            entry.setDatePause2Begin(entryData[6]);
            entry.setTimePause2Begin(entryData[7]);
            entry.setDatePause2End(entryData[8]);
            entry.setTimePause2End(entryData[9]);
            entry.setDateEnd(entryData[10]);
            entry.setTimeEnd(entryData[11]);
            entry.setTimeWorkingBrutto(entryData[12]);
            entry.setTimePause(entryData[13]);
            entry.setTimeWorkingNetto(entryData[14]);

            entryList.add(entry);

        }
        return entryList;
    }

    private String loadAllData(String filepath, String filename, Context context) {
        String fileContents = "";
        FileReader fr = null;
        File dataFile = new File(context.getExternalFilesDir(filepath), filename);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fr = new FileReader(dataFile);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileContents = stringBuilder.toString();
        }
        return fileContents;
    }

    public boolean startedEntryOfTodayExists() {

        if (entries != null) {
            // check last entry of list
            Entry lastEntry = entries.get(entries.size() - 1);

            // get date of last entry (and format it)
            String dateLastEntry = lastEntry.getDateBegin();
            String timeLastEntry = lastEntry.getTimeBegin();
            Date date = timeHelpers.makeDateTime(dateLastEntry, timeLastEntry);

            // get actual date
            Date now = new Date(System.currentTimeMillis());

            // compare entry date and actual date
            if (now.compareTo(date) == 0) {
                if (lastEntry.getDateEnd().equals(" ")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        return false;

    }

    public Entry loadLastEntry() {

        if (entries != null) {
            // check last entry of list
            Entry lastEntry = entries.get(entries.size() - 1);

            // return last entry
            return lastEntry;
        }

        return null;

    }



    private static String[] getFilePath() {
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        String strDate = formatter.format(date);
        String [] dateParts = strDate.split("-");
        String year = dateParts[2];
        String month = dateParts[1];
        String [] fileData = new String[2];
        fileData[0] = year + "/" + month + "/";
        fileData[1] = year + month;
        return fileData;
    }

}
