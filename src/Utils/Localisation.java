package Utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localisation {
	
	private Locale curLoc;
	
	public static Locale[] LOCALES = {
            new Locale("en","US"),
            new Locale("cs", "CZ")
        };

    public Localisation() {
    	//curLoc =  new Locale("cs","CZ");
    	curLoc =  new Locale("en","US");
    }

    public String getWord(String key) {
        ResourceBundle words
                = ResourceBundle.getBundle("strings",  curLoc);

        return words.getString(key);
    }
}