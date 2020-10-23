package View;


import Model.TranslationRow;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;


@Getter
public class TranslateView {
    //<editor-fold desc="FXML">
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
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
    private JFXTextField textInArabTF_ar;
    @FXML
    private JFXTextField textInEnglishTF_en;
    @FXML
    private MenuItem exportXls;
    @FXML
    public Button clearTable;
    @FXML
    private Button updateButton;
    //</editor-fold>

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
    public void showButtons(boolean add, boolean translate, boolean clearT, boolean clearF,
                            boolean update, boolean confirm, boolean cancel) {
        addButton.setVisible(add);
        translateButton.setVisible(translate);
        clearTable.setVisible(clearT);
        clearButton.setVisible(clearF);
        updateButton.setVisible(update);
        confirmButton.setVisible(confirm);
        cancelButton.setVisible(cancel);
    }

}
