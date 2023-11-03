package app;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import personnes.Manager;
import personnes.Personne;
import personnes.Programmeur;
import utils.MessageBar;

import java.lang.reflect.Field;
import java.util.List;

import static app.GestionBddApp.*;

public class DataViewController {

    @FXML
    public Button deleteButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<Object> tableView = new TableView<>();


    private ObservableList<Object> originalData;
    private FilteredList<Object> filteredData;
    private List<?> data;

    @FXML
    private void searchButtonClicked() {
        MessageBar messageBar = new MessageBar();

        String searchText = searchTextField.getText().toLowerCase();
        String idToSearch = idTextField.getText();

        if(searchText.isEmpty() && idToSearch.isEmpty()){
            messageBar.displayMessageBar("Please enter a search term", MessageBar.MessageType.WARNING);
            return;
        }

        if(!searchTextField.getText().isEmpty() && !idTextField.getText().isEmpty()){
            messageBar.displayMessageBar("Avoid searching by name and by ID simultaneously", MessageBar.MessageType.WARNING);
            return;
        }

        int _idToSearch = -1;
        if (!idToSearch.isEmpty()) {
            try {
                _idToSearch = Integer.parseInt(idToSearch);
            } catch (NumberFormatException e) {
                messageBar.displayMessageBar("ID must be an integer", MessageBar.MessageType.ERROR);
            }
        }

        final int finalIdToSearch = _idToSearch;

        filteredData.setPredicate(item -> {
            Personne p = (Personne) item;
            boolean nameMatch = searchText.isEmpty() || p.getFullName().toLowerCase().contains(searchText);
            boolean idMatch = finalIdToSearch == -1 || p.getId() == finalIdToSearch;

            return nameMatch && idMatch;
        });

        tableView.getSelectionModel().clearSelection();

        if (filteredData.size() > 0) {
            tableView.scrollTo(filteredData.get(0));
            tableView.getSelectionModel().select(filteredData.get(0));
            //TODO: select more than 1 row when its the case, or chhange color of the selected rows temporarily with setStyle?
            messageBar.displayMessageBar("Found " + filteredData.size() + " results", MessageBar.MessageType.SUCCESS);
        } else {
            messageBar.displayMessageBar("No results found :/ ", MessageBar.MessageType.ERROR);
        }
    }

    @FXML
    private void deleteButtonClicked(){

        Object p = tableView.getSelectionModel().getSelectedItem();

        if(tableView.getSelectionModel().getSelectedItem() == null){
            new MessageBar().displayMessageBar("Please select a data row to delete", MessageBar.MessageType.WARNING);
            return;
        }

        if(p instanceof Programmeur prog){
            try {
                getProgrammeurDAO().deleteById(prog.getId());
                new MessageBar().displayMessageBar("Programmeur "+prog.getFullName()+" successfully deleted !", MessageBar.MessageType.SUCCESS);
                updateTableView(getProgrammeurDAO().getAll());
            } catch (Exception e) {
                new MessageBar().displayMessageBar(e.getMessage(), MessageBar.MessageType.ERROR);
            }

        }else if(p instanceof Manager m){
            try {
                getManagerDAO().deleteById(m.getId());
                new MessageBar().displayMessageBar("Manager "+m.getFullName()+" successfully deleted !", MessageBar.MessageType.SUCCESS);
                updateTableView(getManagerDAO().getAll());
            } catch (Exception e) {
                new MessageBar().displayMessageBar(e.getMessage(), MessageBar.MessageType.ERROR);
            }

        }
    }



    public void initializeTableView(List<?> data) {
        this.data = data;

        originalData = FXCollections.observableArrayList(data);
        filteredData = new FilteredList<>(originalData, p -> true);

        tableView.setItems(filteredData);

        if (!data.isEmpty()) {
            setUpColumnsForClass(data.get(0).getClass());
        }
    }


    public void updateTableView(List<?> data) {
        tableView.setItems(FXCollections.observableArrayList(data));
    }


    private void setUpColumnsForClass(Class<?> clazz) {
        tableView.getColumns().clear();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {

                if (field.getName().equals("coords")) {
                    continue;
                }

                if (field.getName().equals("manager")) {
                    setUpManagerColumn();
                    continue;
                }

                if (field.getName().equals("pictures")) {
                    setUpPictureColumn();
                    continue;
                }

                TableColumn<Object, Object> column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(column);
            }

            clazz = clazz.getSuperclass();
        }

        reorderTableColumns(tableView, List.of("id", "profilePhoto", "pseudo", "firstName", "lastName", "title", "gender", "manager"));
    }

    public void reorderTableColumns(TableView<Object> tableView, List<String> listOrderColumns) {
        List<TableColumn<Object, ?>> columns = tableView.getColumns();
        int numColumns = columns.size();

        TableColumn[] reorderedColumns = new TableColumn[numColumns];

        int index = 0;
        for (String columnName : listOrderColumns) {
            TableColumn<Object, ?> column = findColumnByName(columns, columnName);
            if (column != null) {
                reorderedColumns[index++] = column;
            }
        }

        for (TableColumn<Object, ?> column : columns) {
            if (!containsColumn(reorderedColumns, column)) {
                reorderedColumns[index++] = column;
            }
        }

        tableView.getColumns().clear();
        tableView.getColumns().setAll(reorderedColumns);
    }

    private TableColumn<Object, ?> findColumnByName(List<TableColumn<Object, ?>> columns, String columnName) {
        for (TableColumn<Object, ?> column : columns) {
            if (column.getText().equals(columnName)) {
                return column;
            }
        }
        return null;
    }

    private boolean containsColumn(TableColumn<Object, ?>[] reorderedColumns, TableColumn<Object, ?> column) {
        for (TableColumn<Object, ?> c : reorderedColumns) {
            if (c == column) {
                return true;
            }
        }
        return false;
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
                    } else {
                        setText(item);
                        setTextFill(Color.BLUE);
                    }
                }
            };

            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Programmeur rowData = (Programmeur) cell.getTableRow().getItem();
                    Manager manager = rowData.getManager();

                    HBox box = Pages.getProfileDataPage(manager);
                    getContentOverlay().getChildren().add(box);

                }
            });

            return cell;
        });
    }

    public void setUpPictureColumn() {
        TableColumn<Object, Image> column = new TableColumn<>("profilePhoto");
        column.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();{
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);

                setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        Personne rowData = (Personne) getTableRow().getItem();

                        if (rowData != null) {
                            HBox box = Pages.getProfileDataPage(rowData);
                            getContentOverlay().getChildren().add(box);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                imageView.setImage(item);
                setGraphic(imageView);
            }
        });

        column.setCellValueFactory(cellData -> {
            String pictureLink = ((Personne) cellData.getValue()).getPictures().getThumbnail();
            System.out.println(cellData.getValue());
            if (pictureLink == null) {
                cellData.getValue();
                pictureLink = "https://www.w3schools.com/howto/img_avatar2.png"; // Default female PP
            }
            return new SimpleObjectProperty<>(new Image(pictureLink));
        });

        tableView.getColumns().add(column);
    }

}