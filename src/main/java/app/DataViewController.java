package app;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import personnes.Manager;
import personnes.Personne;
import personnes.Programmeur;

import java.lang.reflect.Field;
import java.util.List;

public class DataViewController{

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

                if(field.getName().equals("coords")){
                    continue;
                }

                if(field.getName().equals("manager")){
                    setUpManagerColumn();
                    continue;
                }

                if(field.getName().equals("pictures")){
                    setUpPictureColumn();
                    continue;
                }

                TableColumn<Object, Object> column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(column);
            }

            clazz = clazz.getSuperclass();
        }

        reorderTableColumns(tableView, List.of("id", "profilePhoto", "pseudo", "firstName", "lastName", "gender", "manager"));
    }

    public void reorderTableColumns(TableView<Object> tableView, List<String> listOrderColumns) {
        List<TableColumn<Object, ?>> columns = tableView.getColumns();
        int numColumns = columns.size();

        TableColumn<Object, ?>[] reorderedColumns = new TableColumn[numColumns];

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

                    Pages.showProfileData(manager);

                }
            });

            return cell;
        });
    }

    public void setUpPictureColumn() {
        TableColumn<Object, Image> column = new TableColumn<>("profilePhoto");
        column.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
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

            if (pictureLink == null)
                pictureLink = "https://www.w3schools.com/howto/img_avatar.png"; // Default PP

            return new SimpleObjectProperty<>(new Image(pictureLink));
        });

        tableView.getColumns().add(column);
    }


}