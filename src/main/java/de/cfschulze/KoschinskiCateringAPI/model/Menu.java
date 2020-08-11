package de.cfschulze.KoschinskiCateringAPI.model;

import java.util.ArrayList;
import java.util.Date;

public class Menu {
    int id;
    String tag;

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    String datum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ArrayList<String> getGerichte() {
        return gerichte;
    }

    public void setGerichte(ArrayList<String> gerichte) {
        this.gerichte = gerichte;
    }

    ArrayList<String> gerichte;

    public Menu(int id) {
        this.id = id;
    }





}
