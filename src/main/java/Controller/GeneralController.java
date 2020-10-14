package Controller;

import Model.TranslationRow;
import View.TranslateView;
import com.darkprograms.speech.translator.GoogleTranslate;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;


@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class GeneralController {

    private TranslateView translateView;

    private ObservableList<Locale> locales = FXCollections.observableArrayList(
            Locale.FRENCH, Locale.ENGLISH, Locale.forLanguageTag("ar"));

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public GeneralController initView() {
        translateView.getTranslateButton().addEventHandler(ActionEvent.ACTION, e -> {
            translate();
        });
        translateView.getAddButton().addEventHandler(ActionEvent.ACTION, e -> {
            addToTable();
        });
        translateView.getClearButton().addEventHandler(ActionEvent.ACTION, e -> {
            clearFields();
        });

        translateView.initTable(locales);
        return this;
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

    public void createFileXls(String fileName) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Server sheet");

        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        // fr
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Français");
        cell.setCellStyle(style);
        // en
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Englais");
        cell.setCellStyle(style);
        // ar
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Arabe");
        cell.setCellStyle(style);
        // System.out.println(getClass().getResource("\\").getPath());
        File file = new File("F:\\ferial\\JavaProject\\test\\src\\main\\resources\\excel\\" + fileName);
        file.getParentFile().mkdirs();

        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(file);
            workbook.write(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Created file: " + file.getAbsolutePath());


    }

    private void addItToFile(String texteInFr, String textInEn, String textInAr) throws IOException {


        //createFileXls("Server.xls");
        File file = new File(getClass().getResource("/excel/Server.xls").getFile());
        // Read XSL file
        FileInputStream inputStream = new FileInputStream(file);

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        //ajouter une ligne
        int indexRow = sheet.getPhysicalNumberOfRows();
        System.out.println(indexRow);
        HSSFRow row = sheet.createRow(indexRow);

        HSSFCell cell = row.createCell(0, CellType.STRING);
        cell.setCellValue(texteInFr);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue(textInEn);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(textInAr);

        inputStream.close();

        // Write File
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
    }

}
