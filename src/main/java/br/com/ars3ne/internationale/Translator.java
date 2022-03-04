package br.com.ars3ne.internationale;

public interface Translator {

   String translate(String text, String destinationLanguage);
   String translate(String text, String destinationLanguage, String sourceLanguage);

   String getLanguage(String text);

}
