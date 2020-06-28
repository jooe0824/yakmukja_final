package org.techtown.mediclock;

public class AlarmListFromDB {

    private String set_alarm_name;
    private String set_day;

    public String getSet_alarm_name() {
        return set_alarm_name;
    }

    public void setSet_alarm_name(String set_alarm_name) {
        this.set_alarm_name = set_alarm_name;
    }

    public String getSet_day() {
        return set_day;
    }

    public void setSet_day(String set_day) {
        this.set_day = set_day;
    }

    public AlarmListFromDB(String set_alarm_name, String set_day) {
        this.set_alarm_name = set_alarm_name;
        this.set_day = set_day;
    }
}
