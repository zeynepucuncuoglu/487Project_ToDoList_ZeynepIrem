package com.ucuncuoglu.zeynepiremtermprj;

public class Activity {
    private int id;
    private String topic;
    private String duedate;

    public Activity(int id, String topic, String duedate) {
        this.id=id;
        this.topic = topic;
        this.duedate = duedate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() { return topic; }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }
}
