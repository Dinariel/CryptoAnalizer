package ru.javarush.khidiyatov.cryptoanalizer.coder;

import ru.javarush.khidiyatov.cryptoanalizer.exception.InvalidKeyException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Coder {
    private static final List<Character> ALPHABET = Arrays.asList('а', 'б', 'в',
            'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
            'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»',
            ':', '!', '?', ' ');
    protected final int key;
    protected final Path input;
    protected final Path output;

    public Coder(int key, String pathInput, String pathOutput) {
        if (key != 0 && key != ALPHABET.size()) {
            this.key = key;
        } else {
            throw new InvalidKeyException();
        }
        this.input = Paths.get(pathInput);
        this.output = Paths.get(pathOutput);
    }
    public void run() {
        List<String> textInput;
        try {
            textInput = Files.readAllLines(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> textOutput = encryption(key, textInput);
        try {
            Files.write(output, textOutput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private ArrayList<String> encryption (int key, List<String> textInput) {
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
        if (!ALPHABET.contains(c)) {                            // если символа нет в алфавите
            // этот блок был написан, для удобства дешифрования
            // методом brutForce
            // что бы найти ключ в дипазоне размера алфавита
            if (key >= 0 && key < ALPHABET.size()){
                return (char)(c + key);                         // то шифруем по UTF-8
            }
            return (char)(c + key % ALPHABET.size());
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
