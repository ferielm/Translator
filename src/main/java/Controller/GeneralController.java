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

            //<editor-fold defaultstate="collapsed" desc="AliasController">
            case 1:
                msg = "Un alias est on cour de modification";
                break;
            case 2:
                msg = "Alias mal formuler";
                break;
            case 3:
                msg = "Alias déja Existant";
                break;
            case 4:
                msg = "Serveur ne peut être vide";
                break;
            case 5:
                msg = "Nom de la BDD ne peut être vide";
                break;
            case 6:
                msg = "Nom d'utilisateur ne peut être vide";
                break;
            case 7:
                msg = "Le Mot de passe ne peut être vide";
                break;
            case 8:
                msg = "Connection réusite a la BDD";
                break;
            case 9:
                msg = "Connection echouer a la BDD";
                break;
//</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="LogInController">
            case 101:
                msg = "Veuillez sélectionner un alias";
                break;
            case 102:
                msg = "Serveur ne peut être vide";
                break;
            case 103:
                msg = "LogIn ne peut être vide";
                break;
            case 104:
                msg = "Password ne peut être vide";
                break;
            case 105:
                msg = "Login ou Mot de passe incorrecte !!";
                break;
//</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="ServeurCategoryTab">
            case 201:
                msg = "Veuillez selectionné une categorie a modifier";
                break;
            case 202:
                msg = "Veuillez selectionné qu'une seul categorie a modifier";
                break;
            case 203:
                msg = "Veuillez selectionné une categorie a supprimer";
                break;
            case 204:
                msg = "L'intitule categorie ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 205:
                msg = "La description de categorie ne doit contenir que [a-z A-Z 0-9]";
                break;

            case 206:
                msg = "Veuillez selectionné une sous categorie a modifier";
                break;
            case 207:
                msg = "Veuillez selectionné qu'une seul sous categorie a modifier";
                break;
            case 208:
                msg = "Veuillez selectionné une sous categorie a supprimer";
                break;
            case 209:
                msg = "L'intitule sous categorie ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 210:
                msg = "La description de sous categorie ne doit contenir que [a-z A-Z 0-9]";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurThemeTab">
            case 301:
                msg = "Veuillez selectionné un theme a modifier";
                break;
            case 302:
                msg = "Veuillez selectionné qu'un seul theme a modifier";
                break;
            case 303:
                msg = "Veuillez selectionné un theme a supprimer";
                break;
            case 304:
                msg = "L'intitule theme ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 305:
                msg = "La description de theme ne doit contenir que [a-z A-Z 0-9]";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurSubjectTab">
            case 401:
                msg = "Veuillez selectionné un sujet a modifier";
                break;
            case 402:
                msg = "Veuillez selectionné qu'un seul sujet a modifier";
                break;
            case 403:
                msg = "Veuillez selectionné un sujet a supprimer";
                break;
            case 404:
                msg = "L'intitule sujet ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 405:
                msg = "La description de sujet ne doit contenir que [a-z A-Z 0-9]";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurListOfValueTab">
            case 501:
                msg = "Veuillez selectionné une List a modifier";
                break;
            case 502:
                msg = "Veuillez selectionné qu'une seul list a modifier";
                break;
            case 503:
                msg = "Veuillez selectionné une list a supprimer";
                break;
            case 504:
                msg = "Le nom de la liste ne doit contenir que [a-z A-Z 0-9]";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurValueOfListTab">
            case 601:
                msg = "Veuillez selectionné une Valeur a modifier";
                break;
            case 602:
                msg = "Veuillez selectionné qu'une seul Valeur a modifier";
                break;
            case 603:
                msg = "Veuillez selectionné une Valuer a supprimer";
                break;
            case 604:
                msg = "la Valeur ne doit contenir que [a-z A-Z 0-9]";
                break;
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="ServeurStorageMediumTab">
            case 701:
                msg = "Veuillez selectionné un medium a modifier";
                break;
            case 702:
                msg = "Veuillez selectionné qu'un seul medium a modifier";
                break;
            case 703:
                msg = "Veuillez selectionné un medium a supprimer";
                break;
            case 704:
                msg = "Le nom du medium ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 705:
                msg = "La description du medium ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 706:
                msg = "Les info que vous avez donnée sont incorrect";
                break;
            case 707:
                msg = "PathFTP ne peux pas etre vide";
                break;
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="ServeurArmoireTab">
            case 801:
                msg = "Veuillez selectionné une Armoire a modifier";
                break;
            case 802:
                msg = "Veuillez selectionné qu'une seul Armoire a modifier";
                break;
            case 803:
                msg = "Veuillez selectionné une Armoire a supprimer";
                break;
            case 804:
                msg = "Le nom d'Armoire ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 805:
                msg = "La description d'Armoire ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 806:
                msg = "Veuillez selectionné un Media de stockage";
                break;
            case 807:
                msg = "Veuillez associer un ou plusieur Medias de stockage";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurPossibleFieldTab">
            case 901:
                msg = "Veuillez selectionné un champ a modifier";
                break;
            case 902:
                msg = "Veuillez selectionné qu'un seul champ a modifier";
                break;
            case 903:
                msg = "Veuillez selectionné un champ a supprimer";
                break;
            case 904:
                msg = "Le nom du champ ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 905:
                msg = "Le label du champ ne doit contenir que [a-z A-Z 0-9]";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurGroupTab">
            case 1001:
                msg = "Veuillez selectionné un groupe a modifier";
                break;
            case 1002:
                msg = "Veuillez selectionné qu'un seul groupe a modifier";
                break;
            case 1003:
                msg = "Veuillez selectionné un groupe a supprimer";
                break;
            case 1004:
                msg = "Le nom du groupe ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1005:
                msg = "La description du groupe ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1006:
                msg = "Veuillez selectionné un groupe a configurer";
                break;
            case 1007:
                msg = "Veuillez selectionné qu'un seul groupe a configurer";
                break;
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="ServeurGroupTab">
            case 1101:
                msg = "Veuillez selectionné un utilisateur a modifier";
                break;
            case 1102:
                msg = "Veuillez selectionné qu'un seul utilisateur a modifier";
                break;
            case 1103:
                msg = "Veuillez selectionné un utilisateur a supprimer";
                break;
            case 1104:
                msg = "Le nom d'utilisateur ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1105:
                msg = "La description d'utilisateur ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1106:
                msg = "Veuillez selectionné un utilisateur a configurer";
                break;
            case 1107:
                msg = "Veuillez selectionné qu'un seul utilisateur a configurer";
                break;
            case 1108:
                msg = "Le login ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1109:
                msg = "Les mot de passe ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1110:
                msg = "Les mot de passe ne sont pas les memes";
                break;
//</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="ServeurTemplateTab">
            case 1201:
                msg = "Veuillez selectionné un Template a modifier";
                break;
            case 1202:
                msg = "Veuillez selectionné qu'un Template sujet a modifier";
                break;
            case 1203:
                msg = "Veuillez selectionné un Template a supprimer";
                break;
            case 1204:
                msg = "Le nom du Template ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1205:
                msg = "La description de Template ne doit contenir que [a-z A-Z 0-9]";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurViewTab">
            case 1301:
                msg = "Veuillez selectionné une Vue a modifier";
                break;
            case 1302:
                msg = "Veuillez selectionné qu'une seul Vue a modifier";
                break;
            case 1303:
                msg = "Veuillez selectionné une Vue a supprimer";
                break;
            case 1304:
                msg = "Le nom de la Vue ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1305:
                msg = "La description de la Vue ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1306:
                msg = "Veuillez selectionné un Champ ";
                break;
            case 1307:
                msg = "Veuillez associer un ou plusieur Champs ";
                break;
            case 1308:
                msg = "Veuillez selectionné une Vue a modifier";
                break;
            case 1309:
                msg = "Veuillez selectionné qu'une seul Vue a modifier";
                break;
            case 1310:
                msg = "Cette Vue n'a pas de champ associer";
                break;
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="ServeurClasseTab">
            case 1401:
                msg = "Veuillez selectionné une Classe a modifier";
                break;
            case 1402:
                msg = "Veuillez selectionné qu'une Classe sujet a modifier";
                break;
            case 1403:
                msg = "Veuillez selectionné une Classe a supprimer";
                break;
            case 1404:
                msg = "Le nom de la Classe ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1405:
                msg = "La description de la Classe ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1406:
                msg = "Veiller selectioné un Template";
                break;
            case 1407:
                msg = "Le Template selectioné n'a pas de vue ";
                break;
            case 1408:
                msg = "Veiller selectioné une Category";
                break;
            case 1409:
                msg = "Veiller selectioné un Theme";
                break;
            case 1410:
                msg = "Veiller selectioné un Sujet";
                break;
            case 1411:
                msg = "Veiller selectioné un Format de fichier";
                break;
            case 1412:
                msg = "Veiller selectioné une Resolution";
                break;
            case 1413:
                msg = "Cette Classe contient des Dossiers";
                break;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurFolderTab">
            case 1501:
                msg = "Veuillez selectionné un Dossier a modifier";
                break;
            case 1502:
                msg = "Veuillez selectionné qu'un seul Dossier a modifier";
                break;
            case 1503:
                msg = "Veuillez selectionné un Dossier a supprimer";
                break;
            case 1504:
                msg = "L'intitule Dossier ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1505:
                msg = "La description de Dossier ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1506:
                msg = "La Classe doit contenir au moin Un Dossier";
                break;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurSiteTab">
            case 1601:
                msg = "Veuillez selectionné un Site a modifier";
                break;
            case 1602:
                msg = "Veuillez selectionné qu'un seul Site a modifier";
                break;
            case 1603:
                msg = "Veuillez selectionné un Site a supprimer";
                break;
            case 1604:
                msg = "Le nom du Site ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1605:
                msg = "L'addresse ne doit pas etre vide ";
                break;
            case 1606:
                msg = "Le Numero de fax ne doit pas etre vide ";
                break;
            case 1607:
                msg = "Le Numero de tel ne doit pas etre vide ";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurRoomTab">
            case 1701:
                msg = "Veuillez selectionné une salle a modifier";
                break;
            case 1702:
                msg = "Veuillez selectionné qu'une seul Salle a modifier";
                break;
            case 1703:
                msg = "Veuillez selectionné une Salle a supprimer";
                break;
            case 1704:
                msg = "Le nom de la Salle ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1705:
                msg = "La description de la Salle ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1706:
                msg = "Le Numero de tel ne doit pas etre vide ";
                break;
            case 1707:
                msg = "Le nom du respensable ne doit pas etre vide ";
                break;
            case 1708:
                msg = "Veuiller selectionné un Site ";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurDrivewayTab">
            case 1801:
                msg = "Veuillez selectionné une Allée a modifier";
                break;
            case 1802:
                msg = "Veuillez selectionné qu'une seul Allée a modifier";
                break;
            case 1803:
                msg = "Veuillez selectionné une Allée a supprimer";
                break;
            case 1804:
                msg = "Le nom de l'Allée ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1805:
                msg = "La description de l'Allée ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1806:
                msg = "Veuiller selectionné un Salle ";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurShelvingTab">
            case 1901:
                msg = "Veuillez selectionné un Rayonnage a modifier";
                break;
            case 1902:
                msg = "Veuillez selectionné qu'un seul Rayonnage a modifier";
                break;
            case 1903:
                msg = "Veuillez selectionné un Rayonnage a supprimer";
                break;
            case 1904:
                msg = "Le nom du Rayonnage ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1905:
                msg = "La description du Rayonnage ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 1906:
                msg = "Veuiller selectionné une Allée ";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ServeurCuTab">
            case 2001:
                msg = "Veuillez selectionné une Uc a modifier";
                break;
            case 2002:
                msg = "Veuillez selectionné qu'une seul Uc a modifier";
                break;
            case 2003:
                msg = "Veuillez selectionné une Uc a supprimer";
                break;
            case 2004:
                msg = "Le nom du Uc ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 2005:
                msg = "La description du Uc ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 2006:
                msg = "Veuiller selectionné un Rayonnage ";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ScanConfigScan">
            case 2101:
                msg = "Veuillez selectionné une configuration a modifier";
                break;
            case 2102:
                msg = "Veuillez selectionné qu'une seul configuration a modifier";
                break;
            case 2103:
                msg = "Veuillez selectionné une configuration a supprimer";
                break;
            case 2104:
                msg = "Le nom de la configuration ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 2105:
                msg = "Le nom des page du document ne doit contenir que [a-z A-Z]";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ScanLot">
            case 2201:
                msg = "Veuillez selectionné un lot a supprimer";
                break;
            case 2202:
                msg = "La description d'un Lot ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 2203:
                msg = "Veuillez Selectionné Une Classe pour ce Lot";
                break;
            case 2204:
                msg = "Veuillez Selectionné l'emplacement physique de ce Lot";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ScanOther">
            case 2301:
                msg = "Veuillez selectionné un lot";
                break;
            case 2302:
                msg = "Veuillez indiqué un Répertoire de Scan";
                break;
            case 2303:
                msg = "Le répertoire de scan est invalide";
                break;
            case 2304:
                msg = "Aucun Fichier n'a était Séléctionée";
                break;
            case 2305:
                msg = "Partie de Document introuvable";
                break;
            case 2306:
                msg = "Veuillez selectionné un Document a supprimer";
                break;
            case 2307:
                msg = "Veuillez selectionné une Partie du Document a supprimer";
                break;
            case 2308:
                msg = "Veuillez selectionné un Document pour modifier son Dossier";
                break;
            case 2309:
                msg = "Veuillez selectionné un Document ";
                break;
            case 2310:
                msg = "Operation Annuler";
                break;
            case 2311:
                msg = "Aucune Image Séléctionée";
                break;
            case 2312:
                msg = "Séléctionée un Lot A Publier";
                break;
            case 2313:
                msg = "Séléctionée Un Média pour Publier ";
                break;
            case 2314:
                msg = "Séléctionée Un nouveau Dossier pour ce Document ";
                break;
            case 2315:
                msg = "Aucun Dossier n'a était Séléctionée";
                break;
            case 2316:
                msg = "Dossier Vide";
                break;
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="IndexationOther">
            case 2401:
                msg = "Veuillez selectionné une Classe a Indexer";
                break;
            case 2402:
                msg = "Il reste qu'une seule partie Veuillez Supprimer le Document";
                break;
            case 2403:
                msg = "Veuillez selectionné une Partie du Document a supprimer";
                break;
            case 2404:
                msg = "Veuillez selectionné un Document a supprimer";
                break;
            case 2405:
                msg = "Veuillez selectionné un Document";
                break;
            case 2406:
                msg = "Le Titre d'Erreur ne doit contenir que [a-z A-Z 0-9]";
                break;
            case 2407:
                msg = "La Description d'Erreur ne peux pas être vide";
                break;

            case 2408:
                msg = "Il faut d'abord resoudre les erreurs de ce document !!";
                break;

            case 2409:
                msg = "Veuillez selectionné un Document ainsi que la vue désirer";
                break;

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ResearchOther">
            case 2501:
                msg = "Veuillez selectionné un Dossier ";
                break;
            case 2502:
                msg = "Veuillez selectionné un Document a Publier";
                break;
            case 2503:
                msg = "";
                break;
            case 2504:
                msg = "";
                break;
            case 2505:
                msg = "Veuillez selectionné un Document";
                break;
            case 2506:
                msg = "";
                break;
            case 2507:
                msg = "";
                break;
            case 2508:
                msg = "";
                break;
            case 2509:
                msg = "Veuillez selectionné un Document ainsi que la vue désirer";
                break;
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="ReScan">
            case 2601:
                msg = "Veuillez selectionné un Dossier ";
                break;
            case 2602:
                msg = "Il reste qu'une seule partie Veuillez Supprimer le Document";
                break;
            case 2603:
                msg = "Il faut d'abord resoudre les erreurs de ce document !!";
                break;
            case 2604:
                msg = "Il faut d'abord supprimer les erreurs de ce document !!";
                break;
            case 2605:
                msg = "Veuillez selectionné un Document";
                break;
//</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Confirm Dialog">
            case 2701:
                msg = "Voulez vous confirmer cette opération ?";
                break;

            case 2702:
                msg = "Voulez vous confirmer cette opération ?\n" +
                        "Les documents non publiés vont etres corrompu !";
                break;

//</editor-fold>
            default:
                msg = "Une erreur est survenue";
                break;
        }
        return msg;
    }

    public void test() throws IOException {
        ObservableList<TranslationRow> rows = FXCollections.observableArrayList();
        for (int i = 0; i < 5000; i++) {
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

      /*  try {
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
