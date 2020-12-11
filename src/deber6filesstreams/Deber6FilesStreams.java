/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deber6filesstreams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    ObservableList<Laptop> laptops = FXCollections.observableArrayList();
    FilteredList<Laptop> filteredLaptops;
    
    @Override
    public void start(Stage primaryStage) {

        // arraylist<string> marcas;
        primaryStage.setTitle("Deber 6");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona el archivo XML");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Archivo XML", "*.xml"));

        Button openButton = new Button("Seleccionar Archivo");

        TableView<Laptop> table = new TableView<Laptop>();

        table.setEditable(false);

        TableColumn descripcionCol = new TableColumn("Descripcion");
        descripcionCol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn discoCol = new TableColumn("Disco");
        discoCol.setCellValueFactory(new PropertyValueFactory<>("disco"));

        TableColumn estrellasCol = new TableColumn("Estrellas");
        estrellasCol.setCellValueFactory(new PropertyValueFactory<>("estrellas"));

        TableColumn marcaCol = new TableColumn("Marca");
        marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn memoriaCol = new TableColumn("Memoria");
        memoriaCol.setCellValueFactory(new PropertyValueFactory<>("memoria"));

        TableColumn precioCol = new TableColumn("Precio");
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

        table.getColumns().addAll(descripcionCol, discoCol, estrellasCol, marcaCol, memoriaCol, precioCol);

        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    System.out.println(file.getPath());
                    try (BufferedReader input = Files.newBufferedReader(Paths.get(file.getPath()))) {
                        // https://www.baeldung.com/jaxb
                        GrupoLaptops grupo = JAXB.unmarshal(input, GrupoLaptops.class);
                        for (Laptop laptop : grupo.getLaptops()) {
                            laptops.add(laptop);
                        }

                        filteredLaptops = new FilteredList<>(laptops);

                        table.setItems(filteredLaptops);
                    } catch (IOException ex) {
                        Logger.getLogger(Deber6FilesStreams.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        Button saveButton = new Button("Guardar");
        saveButton.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    File myObj = new File("Laptop.txt");
                    if (myObj.createNewFile()) {
                        System.out.println("File created: " + myObj.getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {
                    FileWriter myWriter = new FileWriter("Laptop.txt");

                    int dim1 = 20;
                    int dim2 = 40;

                    for (Laptop laptop : filteredLaptops) {
                        myWriter.write(laptop.marca);
                        for (int espacio = 0; espacio < dim1 - laptop.marca.length(); espacio += 1) {
                            myWriter.write(" ");
                        }
                        myWriter.write(laptop.descripcion);
                        for (int espacio = 0; espacio < dim2 - laptop.descripcion.length(); espacio += 1) {
                            myWriter.write(" ");
                        }
                        myWriter.write(Integer.toString(laptop.memoria));
                        myWriter.write("\t");
                        myWriter.write(laptop.disco);
                        myWriter.write("\t");
                        myWriter.write("$" + Double.toString(laptop.precio));
                        myWriter.write("\t");
                        for (int estrella = 1; estrella <= laptop.estrellas; estrella += 1) {
                            myWriter.write("*");
                        }

                        myWriter.write("\n");
                    }

                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            }
        });

        CheckBox appleCheckbox = new CheckBox("Apple");
        CheckBox lenovoCheckbox = new CheckBox("Lenovo");
        CheckBox hpCheckbox = new CheckBox("HP");
        CheckBox dellCheckbox = new CheckBox("DELL");
        CheckBox mem32Checkbox = new CheckBox("32 de memoria");

        Button filterButton = new Button("Filtrar");
        filterButton.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                
                Predicate<Laptop> checkBoxesFilter = i -> true;
                
                if (appleCheckbox.isSelected() || lenovoCheckbox.isSelected() || hpCheckbox.isSelected() || dellCheckbox.isSelected()) {
                    checkBoxesFilter = i -> false;
                }

                if (appleCheckbox.isSelected()) {
                    Predicate<Laptop> appleFilter = i -> i.getMarca().compareTo("Apple") == 0;
                    checkBoxesFilter = checkBoxesFilter.or(appleFilter);
                }

                if (lenovoCheckbox.isSelected()) {
                    Predicate<Laptop> lenovoFilter = i -> i.getMarca().compareTo("Lenovo") == 0;
                    checkBoxesFilter = checkBoxesFilter.or(lenovoFilter);
                }

                if (hpCheckbox.isSelected()) {
                    Predicate<Laptop> hpFilter = i -> i.getMarca().compareTo("HP") == 0;
                    checkBoxesFilter = checkBoxesFilter.or(hpFilter);
                }

                if (dellCheckbox.isSelected()) {
                    Predicate<Laptop> dellFilter = i -> i.getMarca().compareTo("DELL") == 0;
                    checkBoxesFilter = checkBoxesFilter.or(dellFilter);
                }
                
                Predicate<Laptop> memFilter = i -> true;
                
                if (mem32Checkbox.isSelected()) {
                    memFilter = i -> false;
                }
                
                if (mem32Checkbox.isSelected()) {
                    Predicate<Laptop> mem32Filter = i -> i.getMemoria() == 32;
                    memFilter = memFilter.or(mem32Filter);
                }
                
                Predicate<Laptop> master = checkBoxesFilter.and(memFilter);
                
                filteredLaptops.setPredicate(master);

            }
        });

        Button clearButton = new Button("Limpiar Filtros");
        clearButton.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                filteredLaptops.setPredicate(null);

                appleCheckbox.setSelected(false);
                lenovoCheckbox.setSelected(false);
                hpCheckbox.setSelected(false);
                dellCheckbox.setSelected(false);

            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(openButton, table, filterButton, clearButton, saveButton, appleCheckbox, lenovoCheckbox, hpCheckbox, dellCheckbox, mem32Checkbox);

        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    
}
