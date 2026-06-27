package drintau.accountmanager.desktop;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PropertiesItem {

    private final StringProperty key = new SimpleStringProperty();
    private final StringProperty value = new SimpleStringProperty();

    private String oldValue;

    public PropertiesItem(String key, String value) {
        this.key.set(key);
        this.value.set(value);
        this.oldValue = value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

}
