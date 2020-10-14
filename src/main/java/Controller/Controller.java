package Controller;

import View.TranslateView;
import com.darkprograms.speech.translator.GoogleTranslate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;


@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Controller {
    TranslateView translateView;

    public Controller initView() {

        translateView.getButtonTraduire().addEventHandler(javafx.event.ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @SneakyThrows
            public void handle(ActionEvent e) {
                translate();
            }
        });
        translateView.getButtonAjouter().addEventHandler(javafx.event.ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @SneakyThrows
            public void handle(ActionEvent e) {
                ajouter();
            }
        });
        translateView.getButtonAnnuler().addEventHandler(javafx.event.ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @SneakyThrows
            public void handle(ActionEvent e) {
                annuler();
            }
        });


        return this;
    }

    private void annuler() {
        translateView.getTextFr().setText("");
        translateView.getTextEn().setText("");
        translateView.getTextAr().setText("");
        translateView.getButtonAjouter().setDisable(true);
        translateView.getButtonAnnuler().setDisable(true);
    }

    private void ajouter() throws IOException {
        String texteInFr = translateView.getTextFr().getText();
        String textInEn = translateView.getTextEn().getText();
        String textInAr = translateView.getTextAr().getText();
        addItToFile(texteInFr, textInEn, textInAr);
    }

    private void translate() throws IOException {

        String texteInFr = translateView.getTextFr().getText();
        String textInEn = GoogleTranslate.translate("en", texteInFr);
        String textInAr = GoogleTranslate.translate("ar", texteInFr);
        translateView.getTextEn().setText(textInEn);
        translateView.getTextAr().setText(textInAr);
        translateView.getButtonAnnuler().setDisable(false);
        translateView.getButtonAjouter().setDisable(false);

    }

    public void createFileXls(String fileName) throws IOException {
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

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
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

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

}
