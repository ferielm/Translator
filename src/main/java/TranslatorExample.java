import com.darkprograms.speech.translator.GoogleTranslate;

import java.io.IOException;

public class TranslatorExample {
	
	public static void main(String[] args) {
		
		//Read this ma bro :)
		//When you go on google translate website and you translate from English to Igbo for example
		//you can see the url is :
		// https://translate.google.com/#en/ig/How%20are%20you
		//so the code for IGBO is "ig"
		//see my examples below , i will make tutorial on youtube don't worry
		
		try {
			//English to IGBO
			System.out.println(GoogleTranslate.translate("ar", "allo"));


			//English to GREEK
			System.out.println(GoogleTranslate.translate("fr", "how are you"));
			
			//English to HAUSA
			System.out.println(GoogleTranslate.translate("en", "how are you"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
