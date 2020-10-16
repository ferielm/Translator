package Model;

import View.TranslateView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class XLSFile {
    public void exportXLS(ObservableList<TranslationRow> items, File file) throws IOException {

        if (file == null) {
            String name = JOptionPane.showInputDialog("Insérer le nom de fichier XLS :");

            file = createFileXls(name + ".xls", items.get(0));
        }
        // Read XSL file
        FileInputStream inputStream = new FileInputStream(file);

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        //ajouter une ligne
        int indexRow = 1;
        ObservableList<TranslationRow> rows = items;
        for (TranslationRow tableRow : rows) {
            HSSFRow row = sheet.createRow(indexRow);
            int cellIndex = 0;
            for (Map.Entry map : tableRow.getMap().entrySet()) {
                HSSFCell cell = row.createCell(cellIndex++, CellType.STRING);
                cell.setCellValue((String) map.getValue());
            }
            indexRow++;
        }
        inputStream.close();
        // Write File
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
    }

    private File createFileXls(String fileName, TranslationRow translationRow) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet");
        int rownum = 0;
        Cell cell;
        Row row;
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);
        int cellIndex = 0;
        for (Map.Entry map : translationRow.getMap().entrySet()) {
            cell = row.createCell(cellIndex++, CellType.STRING);
            cell.setCellValue(((Locale) map.getKey()).getDisplayLanguage() + "_" + map.getKey());
            cell.setCellStyle(style);
        }
        File file = null;
        file = new File(getClass().getResource("/excel").getPath(), fileName);

        file.getParentFile().mkdirs();

        FileOutputStream outFile;
        try {
            outFile = new FileOutputStream(file);
            workbook.write(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Created file: " + file.getAbsolutePath());
        return file;

    }

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public File importXLS(TranslateView translateView) throws IOException {
        translateView.dictTable.getItems().clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichier Config", "*xls"));
        fileChooser.setInitialDirectory(new File(getClass().getResource("/excel").getPath()));
        File f = fileChooser.showOpenDialog(null);
        if (f != null) {
            FileInputStream inputStream = new FileInputStream(f);

            // Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

            // Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = sheet.iterator();

            Row row = rowIterator.next();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                translateView.fillTable(row, getLocales(sheet));

            }
        }
        return f;
    }

    private ObservableList<Locale> getLocales(HSSFSheet sheet) {
        ObservableList<Locale> locales = FXCollections.observableArrayList();
        Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            locales.add(Locale.forLanguageTag(cell.getStringCellValue().
                    substring(cell.getStringCellValue().length() - 2)));
        }
        return locales;
    }

   /* public void save(File file, ObservableList<TranslationRow> items) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        //ajouter une ligne
        int indexRow = sheet.getPhysicalNumberOfRows();
        ObservableList<TranslationRow> rows = items;
        for (TranslationRow tableRow : rows) {
            HSSFRow row = sheet.createRow(indexRow);
            int cellIndex = 0;
            for (Map.Entry map : tableRow.getMap().entrySet()) {
                HSSFCell cell = row.createCell(cellIndex++, CellType.STRING);
                cell.setCellValue((String) map.getValue());
            }
        }
        inputStream.close();
        // Write File
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
    }*/
}

