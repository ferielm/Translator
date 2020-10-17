package Model;

import javafx.scene.control.CheckBox;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Locale;

/**
 * @Author YoucTagh Created On 14/10/2020.
 */
@Getter
@Setter
@Accessors(chain = true)
public class TranslationRow {
    private String id;
    private final HashMap<Locale, String> map = new HashMap<>();
    private CheckBox select;

    public TranslationRow() {
        select = new CheckBox();
    }

    public String getString(Locale locale) {
        return map.get(locale);
    }
}
