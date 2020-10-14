package View;


import Model.TranslationRow;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import lombok.Getter;

import java.util.Locale;


@Getter
public class TranslateView {
    @FXML
    public MenuItem importXlsMI;
    @FXML
    public MenuItem importJsonMI;
    @FXML
    public MenuItem importPropertiesMI;
    @FXML
    public JFXTextField textToTranslateTF;
    @FXML
    public Button translateButton;
    @FXML
    public Button addButton;
    @FXML
    public Button clearButton;
    @FXML
    public HBox translationHBox;
    @FXML
    public TableView<TranslationRow> dictTable;
    @FXML
    public JFXTextField textInArabTF;
    @FXML
    public JFXTextField textInEnglishTF;

    public void initTable(ObservableList<Locale> locales) {
        for (Locale locale : locales) {
            TableColumn<TranslationRow, String> column = new TableColumn<>();
            column.setText(locale.getDisplayLanguage());
            column.prefWidthProperty().bind(dictTable.widthProperty().divide(locales.size()));
            column.setCellValueFactory((TableColumn.CellDataFeatures<TranslationRow, String> row) -> {
                return new SimpleStringProperty(row.getValue().getString(locale));
            });
            dictTable.getColumns().add(column);
        }
    }
}
