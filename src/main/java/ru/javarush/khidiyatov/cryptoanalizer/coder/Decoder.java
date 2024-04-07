package ru.javarush.khidiyatov.cryptoanalizer.coder;

import ru.javarush.khidiyatov.cryptoanalizer.coder.Coder;

public class Decoder extends Coder {
    public Decoder(int key, String pathInput, String pathOutput) {
        super(key, pathInput, pathOutput);
    }

    @Override
    protected char encodedCharacter(char c, int key) {
        return super.encodedCharacter(c, -key);
    }
}
