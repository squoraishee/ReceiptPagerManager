package simplereceiptmanager.mobilonix.com.simplereceiptmanager.settings.model;

/**
 * We can use this option to store the type of options we're interested when rendering in to the options
 * fragment setting
 *
 */
public class SettingsOption {

    public enum OptionType {
        CHECK,
        TOGGLE
    }

    OptionType type;
    String optionText;
    SettingsFunction function;

    public SettingsOption(String optionText, OptionType type, SettingsFunction function) {
        this.optionText = optionText;
        this.type = type;
        this.function = function;
    }

    public OptionType getType() {
        return type;
    }

    public String getOptionText() {
        return optionText;
    }

    public SettingsFunction getFunction() {
        return function;
    }

    public void setFunction(SettingsFunction function) {
        this.function = function;
    }
}
