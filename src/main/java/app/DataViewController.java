package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import personnes.Manager;
import personnes.Programmeur;

import java.lang.reflect.Field;
import java.util.List;

public class DataViewController{
    private GestionBddApp mainApp;

    @FXML private TableView<Object> tableView = new TableView<>();

    public void initializeTableView(List<?> data) {
        tableView.setItems(FXCollections.observableArrayList(data));

        if (!data.isEmpty()) {
            setUpColumnsForClass(data.get(0).getClass());
        }
    }

    public void updateTableView(List<?> data) {
        tableView.getItems().setAll(data);
    }

    private void setUpColumnsForClass(Class<?> clazz) {
        tableView.getColumns().clear();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {

                if(field.getName().equals("manager")){
                    setUpManagerColumn();
                    continue;
                }

                TableColumn<Object, Object> column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(column);
            }

            clazz = clazz.getSuperclass();
        }
    }

    public void setUpManagerColumn() {
        TableColumn<Object, String> column = new TableColumn<>("manager");
        column.setCellValueFactory(cellData -> {
            Manager man = ((Programmeur) cellData.getValue()).getManager();
            if (man != null) {
                return new SimpleStringProperty(man.getFullName());
            } else {
                return new SimpleStringProperty(" X ");
            }
        });

        tableView.getColumns().add(column);

        column.setCellFactory(c -> {
            TableCell<Object, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item);
                    }
                }
            };

            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    Programmeur rowData = (Programmeur) cell.getTableRow().getItem();
                    Manager manager = rowData.getManager();

                    if (rowData != null) {
                        Pages.showProfileData(manager);
                    }
                }
            });

            return cell;
        });
    }

}