package de.cfschulze.KoschinskiCateringAPI.api;

import de.cfschulze.KoschinskiCateringAPI.model.Information;
import de.cfschulze.KoschinskiCateringAPI.model.Menu;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
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
}
