package de.cfschulze.KoschinskiCateringAPI.api;

import de.cfschulze.KoschinskiCateringAPI.database.DBController;
import de.cfschulze.KoschinskiCateringAPI.model.Gericht;
import de.cfschulze.KoschinskiCateringAPI.model.Information;
import de.cfschulze.KoschinskiCateringAPI.model.Menu;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/koschinski")
public class InformationController {


    @GetMapping("/today")
    public ArrayList<String> getToday() throws IOException {
        Information information = new Information();

        LocalDateTime now = LocalDateTime.now();

        for (Menu tag : information.getTage()) {
            if (tag.getId() == now.getDayOfWeek().getValue()) {
                return tag.getGerichte();
            }
        }
        return null;
    }


    @GetMapping("/week")
    public ArrayList<Menu> getWeek() throws IOException {
        Information information = new Information();
        return  information.getTage();
    }

    @GetMapping("/all")
    public ArrayList<Gericht> getAll() throws IOException, SQLException {
        DBController dbc = DBController.getInstance();
        dbc.initDBConnection();
        Information information = new Information();
        dbc.handleDB(information.getTage());
        return  dbc.getAll();
    }
}
