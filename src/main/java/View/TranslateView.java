package View;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import lombok.Getter;


@Getter
public class TranslateView {
    @FXML
    private JFXTextField textFr;

    @FXML
    private JFXButton buttonTraduire;

    @FXML
    private Text textAr;

    @FXML
    private Text textEn;
    @FXML
    private Button buttonAjouter;

    @FXML
    private Button buttonAnnuler;
}
