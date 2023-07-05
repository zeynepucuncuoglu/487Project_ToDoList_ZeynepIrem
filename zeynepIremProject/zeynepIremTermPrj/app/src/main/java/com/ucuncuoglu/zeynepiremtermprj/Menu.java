package com.ucuncuoglu.zeynepiremtermprj;

public class Menu {
    private String field;
    private String info;


    public Menu(String field, String info) {
        this.field = field;
        this.info = info;

    }

    public String getField() {
        return field;
    }

    public void setName(String name) {
        this.field = name;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}