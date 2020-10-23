package Model;

import Controller.GeneralController;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;

public interface TranslationManip {
    public File importTranslations(GeneralController generalController) throws IOException;

    public void exportTranslations(ObservableList<TranslationRow> items,File file) throws IOException;
}
