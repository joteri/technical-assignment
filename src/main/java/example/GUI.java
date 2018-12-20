/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author jota
 */
public class GUI extends Application {

    private static TableView table = new TableView<City>();
    private static TextField newCity_txt = new TextField();
    private static Button newCity_btn = new Button("Añadir ciudad");
    private static Label newCity_lbl = new Label();

    @Override
    public void start(Stage primaryStage) {
        TableColumn city_col = new TableColumn("Ciudad");
        city_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn temp_col = new TableColumn("Temp (ºK)");
        temp_col.setCellValueFactory(new PropertyValueFactory<>("temp"));
        TableColumn humidity_col = new TableColumn("Humedad (%)");
        humidity_col.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        TableColumn pressure_col = new TableColumn("Presion");
        pressure_col.setCellValueFactory(new PropertyValueFactory<>("pressure"));
        TableColumn tempMax_col = new TableColumn("Temp Max (ºK)");
        tempMax_col.setCellValueFactory(new PropertyValueFactory<>("maxTemp"));
        TableColumn tempMin_col = new TableColumn("Temp Min (ºK)");
        tempMin_col.setCellValueFactory(new PropertyValueFactory<>("minTemp"));

        table.getColumns().addAll(city_col, temp_col, humidity_col, pressure_col, tempMax_col, tempMin_col);

        newCity_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                loadNewCity();
            }
        });
        newCity_lbl.setTextFill(Color.web("#ff0000"));

        VBox root = new VBox();
        root.setSpacing(5);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 700, 400);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loadNewCity();
            }
        });
        
        root.getChildren().addAll(table, newCity_txt, newCity_btn, newCity_lbl);

        primaryStage.setTitle("Current Weather APP");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Hace la carga inicial de los datos de las ciudades en la tabla.
     * @param cities La colección de ciudades a cargar.
     */
    public static void loadInitialData(ArrayList<City> cities) {
        for (City c : cities) {
            table.getItems().add(c);
        }
    }
    
    /**
     * Añade una nueva fila a la tabla con los datos de un nuevo objeto City
     * instanciado a partir del API REST mediante el envío del nombre de la
     * ciudad solicitada.
     */
    public static void loadNewCity() {
        boolean match = false;
        if (newCity_txt.getText() != null && !newCity_txt.getText().isEmpty()) {
            String[] cityName = {newCity_txt.getText()};
            for (City c : City.getCities()) {
                if (c.getName().equalsIgnoreCase(cityName[0])) {
                    newCity_lbl.setText("Esa ciudad ya ha sido añadida.");
                    match = true;
                    break;
                }
            }
            if (!match) {
                int inserted = ExecuteRest.execute(cityName);
                if (inserted > 0) {
                    newCity_lbl.setTextFill(Color.web("#00cc00"));
                    newCity_lbl.setText("La ciudad fue añadida con éxito.");
                    newCity_txt.clear();
                    City lastCity = City.getCities().get(City.getCities().size() - 1);
                    table.getItems().add(lastCity);
                } else {
                    newCity_lbl.setTextFill(Color.web("#ff0000"));
                    newCity_lbl.setText("No se encontró esa ciudad en España.");
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Ciudades que se consultan y se muestran sus datos por defecto 
        String[] cities = {"Vigo", "Ourense", "Noia", "A Coruña", "Lugo", "Pontevedra", "Santiago de Compostela"};
        ExecuteRest.execute(cities);
        loadInitialData(City.getCities());
        launch(args);
    }

}
