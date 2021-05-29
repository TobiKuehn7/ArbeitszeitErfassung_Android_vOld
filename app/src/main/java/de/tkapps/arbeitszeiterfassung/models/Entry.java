package de.tkapps.arbeitszeiterfassung.models;

public class Entry {

    String dateBegin;
    String timeBegin;
    String datePause1Begin;
    String timePause1Begin;
    String datePause1End;
    String timePause1End;
    String datePause2Begin;
    String timePause2Begin;
    String datePause2End;
    String timePause2End;
    String dateEnd;
    String timeEnd;
    String timeWorkingBrutto;
    String timePause;
    String timeWorkingNetto;


    public Entry() {

    }

    public Entry(String dateBegin, String timeBegin, String datePause1Begin, String timePause1Begin, String datePause1End, String timePause1End, String datePause2Begin, String timePause2Begin, String datePause2End, String timePause2End, String dateEnd, String timeEnd, String timeWorkingBrutto, String timePause, String timeWorkingNetto) {
        this.dateBegin = dateBegin;
        this.timeBegin = timeBegin;
        this.datePause1Begin = datePause1Begin;
        this.timePause1Begin = timePause1Begin;
        this.datePause1End = datePause1End;
        this.timePause1End = timePause1End;
        this.datePause2Begin = datePause2Begin;
        this.timePause2Begin = timePause2Begin;
        this.datePause2End = datePause2End;
        this.timePause2End = timePause2End;
        this.dateEnd = dateEnd;
        this.timeEnd = timeEnd;
        this.timeWorkingBrutto = timeWorkingBrutto;
        this.timePause = timePause;
        this.timeWorkingNetto = timeWorkingNetto;
    }



    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin;
    }

    public String getDatePause1Begin() {
        return datePause1Begin;
    }

    public void setDatePause1Begin(String datePause1Begin) {
        this.datePause1Begin = datePause1Begin;
    }

    public String getTimePause1Begin() {
        return timePause1Begin;
    }

    public void setTimePause1Begin(String timePause1Begin) {
        this.timePause1Begin = timePause1Begin;
    }

    public String getDatePause1End() {
        return datePause1End;
    }

    public void setDatePause1End(String datePause1End) {
        this.datePause1End = datePause1End;
    }

    public String getTimePause1End() {
        return timePause1End;
    }

    public void setTimePause1End(String timePause1End) {
        this.timePause1End = timePause1End;
    }

    public String getDatePause2Begin() {
        return datePause2Begin;
    }

    public void setDatePause2Begin(String datePause2Begin) {
        this.datePause2Begin = datePause2Begin;
    }

    public String getTimePause2Begin() {
        return timePause2Begin;
    }

    public void setTimePause2Begin(String timePause2Begin) {
        this.timePause2Begin = timePause2Begin;
    }

    public String getDatePause2End() {
        return datePause2End;
    }

    public void setDatePause2End(String datePause2End) {
        this.datePause2End = datePause2End;
    }

    public String getTimePause2End() {
        return timePause2End;
    }

    public void setTimePause2End(String timePause2End) {
        this.timePause2End = timePause2End;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeWorkingBrutto() {
        return timeWorkingBrutto;
    }

    public void setTimeWorkingBrutto(String timeWorkingBrutto) {
        this.timeWorkingBrutto = timeWorkingBrutto;
    }

    public String getTimePause() {
        return timePause;
    }

    public void setTimePause(String timePause) {
        this.timePause = timePause;
    }

    public String getTimeWorkingNetto() {
        return timeWorkingNetto;
    }

    public void setTimeWorkingNetto(String timeWorkingNetto) {
        this.timeWorkingNetto = timeWorkingNetto;
    }
}
