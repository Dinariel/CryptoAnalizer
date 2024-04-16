package ru.javarush.khidiyatov.cryptoanalizer;

import ru.javarush.khidiyatov.cryptoanalizer.coder.Decoder;
import ru.javarush.khidiyatov.cryptoanalizer.coder.Encoder;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        mainMenu();
        makeChoice();
    }

    private static void makeChoice() {
        Scanner scanner;
        while (true) {
            scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if (choice == 1) {
                encoder();
                continue;
            }
            if (choice == 2) {
                decoder();
                continue;
            }
            if (choice == 3) {
                brutForce();
                continue;
            }
            if (choice == 0) {
                System.out.println("Досвидания!");
                break;
            }
        }
    }

    private static void mainMenu() {
        System.out.println("Вас приветствует программа дешифратор!");
        System.out.println("Укажите, что нужно сделать:");
        System.out.println("1 - зашифровать файл по ключу");
        System.out.println("2 - расшифровать файл по ключу");
        System.out.println("3 - расшифровать файл методом BrutForce");
        System.out.println("0 - выход из программы");
    }

    public static void encoder() {
        System.out.println("Шифруем файл, который находится тут: texts/text.txt");
        String inputText = "texts/text.txt";
        String resultEncoder = "texts/resultEncoder.txt";
        int key = -4;
        System.out.println("Шифруем по ключу: " + key);
        new Encoder(key, inputText, resultEncoder).run();
        System.out.println("результат находится тут: texts/resultEncoder.txt");
    }

    public static void decoder() {
        System.out.println("расшифровываем файл, который находится тут: texts/resultEncoder.txt");
        String resultEncoder = "texts/resultEncoder.txt";
        String resultDecoder = "texts/resultDecoder.txt";
        int key = -4;
        System.out.println("Расшифровываем по ключу: " + key);
        new Decoder(key, resultEncoder, resultDecoder).run();
        System.out.println("результат находится тут: texts/resultDecoder.txt");
    }

    public static void brutForce() {
        System.out.println("работает оперативная группа BrutForce");
        System.out.println("расшифровываем файл, который находится тут: texts/resultEncoder.txt");
        String resultBrutForce = "texts/resultBrutForce.txt";
        String encryptedFile = "texts/resultEncoder.txt";
        new BruteForceDecryption(encryptedFile, resultBrutForce).run();
        System.out.println("результат можно найти тут: texts/resultBrutForce.txt");
    }
}
