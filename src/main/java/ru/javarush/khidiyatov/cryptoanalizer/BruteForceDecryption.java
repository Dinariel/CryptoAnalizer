package ru.javarush.khidiyatov.cryptoanalizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteForceDecryption {
    private static final List<Character> ALPHABET = Arrays.asList('а', 'б', 'в',
            'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
            'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»',
            ':', '!', '?', ' ');
    private final Path encryptedFile;
    private final Path result;
    private int key;
    private int maxMatch = 0;

    public BruteForceDecryption(String encryptedFile, String resultBrutForce) {
        this.encryptedFile = Paths.get(encryptedFile);
        this.result = Paths.get(resultBrutForce);
    }

    public void run() {
        List<String> textInput;
        try {
            textInput = Files.readAllLines(encryptedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> textOutput = decode(searchKey(textInput), textInput);
        try {
            Files.write(result, textOutput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int searchKey(List<String> textInput) {
        for (int i = 1; i < ALPHABET.size(); i++) {
            int countMatch = 0;
            List<String> decodeList = decode(i, textInput);
            for (String line : decodeList) {
                String[] split = line.split(".[.,;:!?]\\s");
                countMatch += split.length - 1;
            }
            if (countMatch > maxMatch) {
                maxMatch = countMatch;
                key = i;
            }
        }
        return key;
    }

    //методы ниже были скопированы из класса Coder
    private ArrayList<String> decode(int key, List<String> textInput) {
        ArrayList<String> textOutput = new ArrayList<>();
        for (String line : textInput) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                char c = encodedCharacter(line.charAt(i), key);
                builder.append(c);
            }
            textOutput.add(builder.toString());
        }
        return textOutput;
    }

    protected char encodedCharacter(char c, int key) {
        if (!ALPHABET.contains(c)) {
            if (key >= 0 && key < ALPHABET.size()) {
                return (char) (c + key);
            }
            return (char) (c + key % ALPHABET.size());
        }
        int indexInAlphabet = ALPHABET.indexOf(c);
        int encodedIndex = indexInAlphabet + key;
        if (encodedIndex >= 0 && encodedIndex < ALPHABET.size()) {  // если индекс закодированного символа находится в диапазоне алфавита,
            return ALPHABET.get(encodedIndex);                      // то возвращаем индекс закодированного символа.
        }
        encodedIndex = encodedIndex % ALPHABET.size();              // иначе берем остаток от деления индекса на размер алфавита
        if (encodedIndex >= 0) {                                    // если индекс положительный,
            return ALPHABET.get(encodedIndex);                      // то возвращаем его значение в алфавите
        }
        return ALPHABET.get(ALPHABET.size() + encodedIndex);        // если отрицательный, то высчитываем его из конца алфавита
    }
}
