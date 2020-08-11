package de.cfschulze.KoschinskiCateringAPI.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Information {
    ArrayList<Menu> tage = new ArrayList<>();

    public Information() throws IOException {
        Document doc = Jsoup.connect("https://www.koschinski-catering.de/wochenspeiseplan-kantine/").get();


        Element table_div = doc.getElementById("c19");
        Element table = table_div.selectFirst("table");

        ArrayList<String> montag = new ArrayList<>();
        ArrayList<String> dienstag = new ArrayList<>();
        ArrayList<String> mittwoch = new ArrayList<>();
        ArrayList<String> donnerstag = new ArrayList<>();
        ArrayList<String> freitag = new ArrayList<>();


        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");

            montag.add(cols.get(0).text());
            dienstag.add(cols.get(1).text());
            mittwoch.add(cols.get(2).text());
            donnerstag.add(cols.get(3).text());
            freitag.add(cols.get(4).text());

        }



        Menu montag_menu = new Menu(1);
        montag_menu.setGerichte(montag);
        montag_menu.setTag("Montag");
        montag_menu.setDatum(rows.get(0).select("th").get(0).select("p").text());


        Menu dienstag_menu = new Menu(2);
        dienstag_menu.setGerichte(dienstag);
        dienstag_menu.setTag("Dienstag");
        dienstag_menu.setDatum(rows.get(0).select("th").get(1).select("p").text());

        Menu mittwoch_menu = new Menu(3);
        mittwoch_menu.setGerichte(mittwoch);
        mittwoch_menu.setTag("Mittwoch");
        mittwoch_menu.setDatum(rows.get(0).select("th").get(2).select("p").text());

        Menu donnerstag_menu = new Menu(4);
        donnerstag_menu.setGerichte(donnerstag);
        donnerstag_menu.setTag("Donnerstag");
        donnerstag_menu.setDatum(rows.get(0).select("th").get(3).select("p").text());

        Menu freitag_menu = new Menu(5);
        freitag_menu.setGerichte(freitag);
        freitag_menu.setTag("Freitag");
        freitag_menu.setDatum(rows.get(0).select("th").get(4).select("p").text());


        tage.add(montag_menu);
        tage.add(dienstag_menu);
        tage.add(mittwoch_menu);
        tage.add(donnerstag_menu);
        tage.add(freitag_menu);

    }


    public ArrayList<Menu> getTage() {
        return tage;
    }

    private Date dateFormat(String text){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            date = formatter.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


}
