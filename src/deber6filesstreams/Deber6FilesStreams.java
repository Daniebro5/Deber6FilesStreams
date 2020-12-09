/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deber6filesstreams;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.xml.bind.JAXB;

/**
 *
 * @author dannibrito
 */
public class Deber6FilesStreams extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Deber 6");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona el archivo XML");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Archivo XML", "*.xml"));

        Button openButton = new Button("Seleccionar Archivo");

        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    System.out.println(file.getPath());
                    try (BufferedReader input = Files.newBufferedReader(Paths.get(file.getPath()))) {
                        GrupoLaptops grupo = JAXB.unmarshal(input, GrupoLaptops.class);
                        for(Laptop laptop: grupo.getLaptops()) {
                            System.out.println(laptop.descripcion);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Deber6FilesStreams.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        );

        TableView table = new TableView();
        table.setEditable(false);

        TableColumn descripcionCol = new TableColumn("Descripcion");
        TableColumn discoCol = new TableColumn("Disco");
        TableColumn estrellasCol = new TableColumn("Estrellas");
        TableColumn marcaCol = new TableColumn("Marca");
        TableColumn memoriaCol = new TableColumn("Memoria");
        TableColumn precioCol = new TableColumn("Precio");

        table.getColumns().addAll(descripcionCol, discoCol, estrellasCol, marcaCol, memoriaCol, precioCol);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(openButton, table);

        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public Laptop[] ordenarPorPrecio(boolean ascendente, Laptop[] laptops) {
        Laptop ordenado[] = laptops;

        if (ascendente) {
            for (int i = 0; i < laptops.length; i++) {
                for (int j = 1; j < laptops.length - i; j++) {
                    if (ordenado[j - 1].precio > ordenado[j].precio) {
                        Laptop aux = ordenado[j - 1];
                        ordenado[j - 1] = ordenado[j];
                        ordenado[j] = aux;
                    }
                }
            }
        } else {
            for (int i = 0; i < laptops.length; i++) {
                for (int j = 1; j < laptops.length - i; j++) {
                    if (ordenado[j - 1].precio < ordenado[j].precio) {
                        Laptop aux = ordenado[j - 1];
                        ordenado[j - 1] = ordenado[j];
                        ordenado[j] = aux;
                    }
                }
            }
        }

        return ordenado;
    }

    public void imprimirLaptops(Laptop laptops[]) {
        for (Laptop laptop : laptops) {
            System.out.println(laptop.precio);
        }
    }

}
