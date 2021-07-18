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
import javafx.scene.Node;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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

    //JFXDialog
    public static String getMessage(int codeMessage) {
        String msg = "";
        switch (codeMessage) {


            case 2801:
                msg = "Pas de classe a vérifier!!";
                break;

            case 2802:
                msg = "Séléctionner une classe !!";
                break;
            case 2803:
                msg = "Séléctionner un document !!";
                break;
            case 2804:
                msg = "Séléctionner un utilisateur !!";
                break;
            case 2805:
                msg = "Aucune liste des valeurs associée !!";
                break;
            case 2806:
                msg = "N'appartien pas a un groupe !!";
                break;
            case 2807:
                msg = "Aucune catégorie n'est pas spécifiée";
                break;
            case 2808:
                msg = "Aucune sous-catégorie n'est spécifiée";
                break;
            case 2809:
                msg = "Aucun thème n'est spécifié";
                break;
            case 2810:
                msg = "Aucun sujet n'est spécifier";
                break;
            case 2811:
                msg = "Aucun lot a afficher";
                break;
            case 2812:
                msg = "Pas encors publié";
                break;
            case 2813:
                msg = "Aucun lot a afficher";
                break;
            case 2814:
                msg = "Le champ ";
                break;
            case 2815:
                msg = " est obligatoire !!";
                break;
            case 2816:
                msg = "Entrez l'alias de cette configuration";
                break;
            case 2817:
                msg = "Impression annulé";
                break;
            case 2818:
                msg = "Aucun dossier n'est séléctionné";
                break;




//</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Notification">
            case 2901:
                msg = "L'élément est ajouté avec succés";
                break;

            case 2902:
                msg = "L'élément est supprimé avec succés";
                break;

            case 2903:
                msg = "L'élément est modifié avec succés";
                break;
            case 2904:
                msg = "L'opéation est réussi";
                break;

            default:
                msg = "Une erreur est survenue";
                break;
        }
        return msg;
    }

    public void test() throws IOException {
        ObservableList<TranslationRow> rows = FXCollections.observableArrayList();
        for (int i = 2800; i < 2906; i++) {
            String s = getMessage(i);
            if (!s.equals("Une erreur est survenue")) {
                TranslationRow translationRow = new TranslationRow();
                translationRow.setId(String.valueOf(i));
                translationRow.getMap().put(Locale.FRENCH, s);
                translationRow.getMap().put(Locale.ENGLISH,GoogleTranslate.translate("en", s));
                translationRow.getMap().put(Locale.forLanguageTag("ar"),GoogleTranslate.translate("ar", s));
                rows.add(translationRow);
            }
        }

        translateView.getDictTable().setItems(rows);
    }

    public GeneralController initView() {
translateView.addButton.setDisable(false);
        translateView.getTranslateButton().addEventHandler(ActionEvent.ACTION, e -> {
            translate();
        });

        translateView.getAddButton().addEventHandler(ActionEvent.ACTION, e -> {
            addToTable();
            clearFields();

        });

        translateView.getUpdateButton().addEventHandler(ActionEvent.ACTION, e -> {
            updateSelectedRow();

        });

        translateView.getClearButton().addEventHandler(ActionEvent.ACTION, e -> {
            clearFields();
        });

        translateView.clearTable.addEventHandler(ActionEvent.ACTION, e -> {
            translateView.dictTable.getItems().clear();
            openedFile = null;
            translateView.getSaveMenuItem().setDisable(true);
        });

        translateView.getSaveMenuItem().addEventHandler(ActionEvent.ACTION, event -> {
            try {
                saveIntheOpenedFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        translateView.getExportXls().addEventHandler(ActionEvent.ACTION, e -> {
            try {
                new XLSFile().exportTranslations(translateView.dictTable.getItems(), null);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        translateView.getExportProperties().addEventHandler(ActionEvent.ACTION, event -> {
            try {
                new PropertiesFile().exportTranslations(translateView.dictTable.getItems(), null);
            } catch (IOException e) {


            }
        });
        translateView.importXlsMI.addEventHandler(ActionEvent.ACTION, e -> {
            try {
                openedFile = new XLSFile().importTranslations(this);
                translateView.getSaveMenuItem().setDisable(false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        translateView.importPropertiesMI.addEventHandler(ActionEvent.ACTION, e -> {
            openedFile = new PropertiesFile().importTranslations(this);
            translateView.getSaveMenuItem().setDisable(false);
        });
        translateView.importJsonMI.addEventHandler(ActionEvent.ACTION, event -> {
            openedFile = new JSONFile().importJSONFile();
            translateView.getSaveMenuItem().setDisable(false);
        });
        translateView.initTable(locales);
        initSelectAllCB();

       /* try {
            test();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return this;
    }

    private void initSelectAllCB() {
        translateView.getSelectAllCB().selectedProperty().addListener((observable, oldValue, newValue) -> {
            translateView.getDictTable().getItems().forEach(translationRow -> translationRow.getSelect().setSelected(newValue));
        });
    }

    private void saveIntheOpenedFile() throws IOException {
        if (openedFile.getName().endsWith("xls")) {
            new XLSFile().exportTranslations(translateView.dictTable.getItems(), openedFile);
        } else if (openedFile.getName().endsWith("json")) {
            new JSONFile().save(openedFile, translateView.dictTable.getItems());
        } else {
            new PropertiesFile().exportTranslations(translateView.dictTable.getItems(), openedFile);
        }
    }


    private void addToTable() {
        String id = translateView.getIDTF().getText().toLowerCase();
        String textInFr = translateView.getTextToTranslateTF().getText();
        String textInEn = translateView.getTextInEnglishTF_en().getText();
        String textInAr = translateView.getTextInArabTF_ar().getText();
        addItToTable(id, textInFr, textInEn, textInAr);
    }

    private void addItToTable(String id, String textInFr, String textInEn, String textInAr) {
        final TranslationRow row = new TranslationRow()
                .setId(id);
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
        translateView.getTextInEnglishTF_en().setText(textInEn);
        translateView.getTextInArabTF_ar().setText(textInAr);
        translateView.getClearButton().setDisable(false);
        translateView.getAddButton().setDisable(false);

    }

    public void fillTable(Row fileRow, ObservableList<Locale> locales) {
        Iterator<Cell> cellIterator = fileRow.cellIterator();
        final TranslationRow row = new TranslationRow();
        row.setId(cellIterator.next().getStringCellValue());
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            row.getMap().put(locales.get(cell.getColumnIndex()), cell.getStringCellValue());
        }
        translateView.getDictTable().getItems().add(row);
    }

    public void updateSelectedRow() {
        TranslationRow selectedTranslationRow = translateView.dictTable.getSelectionModel().getSelectedItem();
        if (selectedTranslationRow != null) {
            translateView.dictTable.setDisable(true);
            translateView.iDTF.setText(selectedTranslationRow.getId());
            for (Map.Entry map : selectedTranslationRow.getMap().entrySet()) {
                if (((Locale) map.getKey()).getLanguage().equals(Locale.FRENCH.toLanguageTag()))
                    translateView.textToTranslateTF.setText((String) map.getValue());
                else
                    for (Node node : translateView.translationHBox.getChildren())
                        if (node.getId().endsWith(((Locale) map.getKey()).toLanguageTag())) {
                            ((JFXTextField) node).setText((String) map.getValue());
                            break;
                        }
            }
            translateView.showButtons(false, false, false, false, false, true, true);
            initUpdateButtons();

        } else JOptionPane.showMessageDialog(null, "séléctionnez une ligne !");
    }

    private void initUpdateButtons() {
        translateView.getCancelButton().addEventHandler(ActionEvent.ACTION, e -> {
            cancelOp();
        });
        translateView.getConfirmButton().addEventHandler(ActionEvent.ACTION, event -> {
            final TranslationRow row = getRow();
            translateView.dictTable.getItems().set(translateView.dictTable.getSelectionModel().getSelectedIndex(), row);
            cancelOp();
        });
    }

    private void cancelOp() {
        clearFields();
        translateView.showButtons(true, true, true, true, true, false, false);
        translateView.dictTable.setDisable(false);
    }

    private TranslationRow getRow() {
        TranslationRow row = new TranslationRow()
                .setId(translateView.iDTF.getText());
        row.getMap().put(Locale.FRENCH, translateView.textToTranslateTF.getText());
        for (Node node : translateView.translationHBox.getChildren()) {
            Locale local = Locale.forLanguageTag(node.getId().substring(node.getId().length() - 2));
            System.out.println(local + " " + ((JFXTextField) node).getText());
            row.getMap().put(local, ((JFXTextField) node).getText());
        }
        return row;
    }

    public void clearFields() {
        translateView.getTextToTranslateTF().setText("");
        translateView.getIDTF().setText("");
        translateView.getTranslationHBox().getChildren().forEach(node -> {
            ((JFXTextField) node).setText("");
        });
        //translateView.getAddButton().setDisable(true);
        translateView.getClearButton().setDisable(true);
    }


    public void fillTable(HashMap<String, HashMap<Locale, String>> words) {
        for (Map.Entry map : words.entrySet()) {
            TranslationRow row = new TranslationRow().
                    setId((String) map.getKey());
            row.getMap().putAll((HashMap<Locale, String>) map.getValue());
            translateView.getDictTable().getItems().add(row);
        }
    }
}
