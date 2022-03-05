# Internationale

Internationale is a simple library that allows you to translate your text into multiple languages using multiple translators for free!

Currently, we support [Google Translate](https://translate.google.com) and [Yandex Translate](https://translate.yandex.com). Pull requests to add more translators are appreciated!

## Instalation
Including Internationale with Maven:
```xml
<repositories>
	<repository>
		   <id>jitpack.io</id>
		   <url>https://jitpack.io</url>
	</repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.Ars3ne</groupId>
	<artifactId>internationale</artifactId>
	<version>1.0.0</version> <!-- Check the latest version on the repo! -->
</dependency>
```

Using Gradle:
```groovy
repositories {
	maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.Ars3ne:internationale:1.0.0' // Check the latest version on the repo!
}
```


## Usage
``` java
public class Example {

    public static final Translator googleTranslator = new GoogleTranslate();
    public static final Translator yandexTranslator = new YandexTranslate();

    public static void main(String[] args) {

        String translation = googleTranslator.translate("Olá, mundo!", "en"); // Returns "Hello World!"
        
        String language = yandexTranslator.getLanguage("всем привет"); // Returns "ru"

    }

}
```

## Credits
- [translatepy](https://github.com/animenosekai/translate) - For giving inspiration to the implementations of Google and Yandex.