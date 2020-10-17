package View;


import Model.TranslationRow;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;
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
    public CheckBox selectAllCB;
    @FXML
    public JFXTextField iDTF;
    @FXML
    private MenuItem exportJson;
    @FXML
    private MenuItem exportProperties;
    @FXML
    private MenuItem saveMenuItem;
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
    @FXML
    private MenuItem exportXls;
    @FXML
    public Button clearTable;

    public void initTable(ObservableList<Locale> locales) {

        TableColumn<TranslationRow, CheckBox> selectCol = new TableColumn<>("Select");
        selectCol.setCellValueFactory(new PropertyValueFactory<>("select"));
        dictTable.getColumns().add(selectCol);

        TableColumn<TranslationRow, String> column = new TableColumn<>();
        column.setText("ID");
        column.setMinWidth(100);
        column.prefWidthProperty().bind(dictTable.widthProperty().subtract(selectCol.getWidth()).divide(locales.size() + 1));
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        dictTable.getColumns().add(column);

        for (Locale locale : locales) {
            column = new TableColumn<>();
            column.setText(locale.getDisplayLanguage());
            column.setMinWidth(100);
            column.prefWidthProperty().bind(dictTable.widthProperty().subtract(selectCol.getWidth()).divide(locales.size() + 1));
            column.setCellValueFactory((TableColumn.CellDataFeatures<TranslationRow, String> row) ->
                    new SimpleStringProperty(row.getValue().getString(locale)));
            dictTable.getColumns().add(column);
        }
    }

    public void fillTable(Row fileRow, ObservableList<Locale> locales) {
        Iterator<Cell> cellIterator = fileRow.cellIterator();
        final TranslationRow row = new TranslationRow();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            row.getMap().put(locales.get(cell.getColumnIndex()), cell.getStringCellValue());
        }
        getDictTable().getItems().add(row);
    }
}
