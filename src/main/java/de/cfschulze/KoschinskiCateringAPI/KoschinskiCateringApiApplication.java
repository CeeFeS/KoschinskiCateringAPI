package de.cfschulze.KoschinskiCateringAPI;

import de.cfschulze.KoschinskiCateringAPI.database.DBController;
import de.cfschulze.KoschinskiCateringAPI.model.Information;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class KoschinskiCateringApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoschinskiCateringApiApplication.class, args);

		/**Thread with infinite Loop for checking menu and inserting in Database (Database Thread)*/
		Runnable r = new Runnable() {
			public void run() {
				DBController dbc = DBController.getInstance();
				dbc.initDBConnection();
				try {
					dbc.createTable();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				while (true) {
					Information information = null;
					try {
						information = new Information();
					} catch (IOException e) {
						e.printStackTrace();
					}
					dbc.insertMenus(information.getTage());
					try {
						Thread.sleep(600000); //10 min
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		new Thread(r).start();
		//this line will execute immediately, not waiting for your task to complete
	}

}
