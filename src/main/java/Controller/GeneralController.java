package Controller;

import Model.JSONFile;
import Model.PropertiesFile;
import Model.TranslationRow;
import Model.XLSFile;
import View.TranslateView;
import com.darkprograms.speech.translator.GoogleTranslate;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;


@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class GeneralController {

    private TranslateView translateView;
    File openedFile = null;

    private ObservableList<Locale> locales = FXCollections.observableArrayList(
            Locale.FRENCH, Locale.ENGLISH, Locale.forLanguageTag("ar"));


    public GeneralController initView() {

        translateView.getTranslateButton().addEventHandler(ActionEvent.ACTION, e -> {
            translate();
        });
        translateView.getAddButton().addEventHandler(ActionEvent.ACTION, e -> {
            addToTable();
            clearFields();

        });
        translateView.getClearButton().addEventHandler(ActionEvent.ACTION, e -> {
            clearFields();
        });
        translateView.getExportXls().addEventHandler(ActionEvent.ACTION, e -> {
            try {
                new XLSFile().exportXLS(translateView.dictTable.getItems(), null);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        translateView.importXlsMI.addEventHandler(ActionEvent.ACTION, e -> {
            try {
                openedFile = new XLSFile().importXLS(this.translateView);
                translateView.getSaveMenuItem().setDisable(false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        translateView.importPropertiesMI.addEventHandler(ActionEvent.ACTION, e -> {
            openedFile = new PropertiesFile().importPropertiesFile();
            translateView.getSaveMenuItem().setDisable(false);
        });
        translateView.importJsonMI.addEventHandler(ActionEvent.ACTION, event -> {
            openedFile = new JSONFile().importJSONFile();
            translateView.getSaveMenuItem().setDisable(false);
        });
        translateView.getSaveMenuItem().addEventHandler(ActionEvent.ACTION, event -> {
            try {
                saveIntheOpenedFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        translateView.clearTable.addEventHandler(ActionEvent.ACTION, e -> {
            translateView.dictTable.getItems().clear();
            openedFile = null;
            translateView.getSaveMenuItem().setDisable(true);
        });
        translateView.initTable(locales);
        return this;
    }

    private void saveIntheOpenedFile() throws IOException {
        if (openedFile.getName().endsWith("xls")) {
            new XLSFile().exportXLS(translateView.dictTable.getItems(), openedFile);
        } else if (openedFile.getName().endsWith("json")) {
            new JSONFile().save(openedFile, translateView.dictTable.getItems());
        } else {
            new PropertiesFile().save(openedFile, translateView.dictTable.getItems());
        }
    }

    private void clearFields() {
        translateView.getTextToTranslateTF().setText("");
        translateView.getTranslationHBox().getChildren().forEach(node -> {
            ((JFXTextField) node).setText("");
        });
        translateView.getAddButton().setDisable(true);
        translateView.getClearButton().setDisable(true);
    }

    private void addToTable() {
        String textInFr = translateView.getTextToTranslateTF().getText();
        String textInEn = translateView.getTextInEnglishTF().getText();
        String textInAr = translateView.textInArabTF.getText();
        addItToTable(textInFr, textInEn, textInAr);
    }

    private void addItToTable(String textInFr, String textInEn, String textInAr) {
        final TranslationRow row = new TranslationRow();
        row.getMap().put(Locale.FRENCH, textInFr);
        row.getMap().put(Locale.ENGLISH, textInEn);
        row.getMap().put(Locale.forLanguageTag("ar"), textInAr);
        translateView.getDictTable().getItems().add(row);
    }

    private void translate() {

        String textInFr = translateView.getTextToTranslateTF().getText();
        String textInAr = null;
        String textInEn = null;
        try {
            textInEn = GoogleTranslate.translate("en", textInFr);
            textInAr = GoogleTranslate.translate("ar", textInFr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        translateView.getTextInEnglishTF().setText(textInEn);
        translateView.getTextInArabTF().setText(textInAr);
        translateView.getClearButton().setDisable(false);
        translateView.getAddButton().setDisable(false);

    }


}
