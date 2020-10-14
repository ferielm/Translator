package Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Locale;

/**
 * @Author YoucTagh Created On 14/10/2020.
 */
@Getter
@Setter
@NoArgsConstructor
public class TranslationRow {
    private final HashMap<Locale, String> map = new HashMap<>();

    public String getString(Locale locale) {
        return map.get(locale);
    }
}
