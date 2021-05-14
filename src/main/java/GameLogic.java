import java.util.Random;
import java.util.Scanner;

public class GameLogic {
    private String answer;
    private String generatedRandomNumber;
    private int bulls = 0;
    private int cows = 0;
    private String AlphaNumericString = "0123456789abcdefghijklmnopqrstuvwxyz";
    private int codeLength;
    private int possibleSymbols;

    protected void runGame() {
        System.out.print("Input the length of the secret code: ");

        try {
            codeLength = Integer.parseInt(scanUserInput());
        } catch (Exception e) {
            System.out.println("Error: \"" + codeLength + "\" isn't a valid number.");
        }

        if (codeLength > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else if (codeLength < 1) {
            System.out.println("Error: code can't be less then 1 number");
        } else {
            System.out.print("Input the number of possible symbols in the code: ");
            possibleSymbols = Integer.parseInt(scanUserInput());
            if (possibleSymbols < codeLength) {
                System.out.println("Error: it's not possible to generate a code with a length of " + codeLength + " with " + possibleSymbols + " unique symbols.");
            } else if (possibleSymbols > 36) {
                System.out.printf("Error: can't generate a secret number with a length of %s because there aren't enough unique symbols.%n", possibleSymbols);
            } else {
                starGame();
            }
        }
    }

    private void starGame() {
        System.out.println("Okay, let's start a game!");
        int counter = 1;

        generatedRandomNumber = getCustomizedRandomNumber(codeLength, possibleSymbols);
        System.out.println("The secret is prepared: " + secretCodeSymbolGenerator(codeLength) + " (0-9, a-" + getLastIntervalLetter(possibleSymbols) + ").");

        System.out.println(generatedRandomNumber);

        while (!generatedRandomNumber.equals(answer)) {
            bulls = 0;
            cows = 0;
            System.out.println("Turn " + counter + ":");
            answer = scanUserInput();
            bullsCowsCounter();

            if (bulls == generatedRandomNumber.length()) {
                System.out.println("Grade: " + generatedRandomNumber.length() + " bulls");
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            } else if (bulls != 0 || cows != 0) {
                System.out.print("Grade: " + bulls + " bull(s) and "
                        + cows + " cow(s)");
            } else {
                System.out.print("Grade: None.");
            }

            System.out.println();
            counter++;
        }
    }

    private int bullsCowsCounter() {
        for (int i = 0; i < generatedRandomNumber.length(); i++) {
            if (generatedRandomNumber.charAt(i) == answer.charAt(i)) {
                bulls++;
            } else if (answer.contains(Character.toString(generatedRandomNumber.charAt(i)))) {
                cows++;
            }
        }
        return bulls & cows;
    }

    private String scanUserInput() {
        String userInput = "";

        Scanner scanner = new Scanner(System.in);
        userInput = scanner.next();

        return userInput;
    }

    protected String getCustomizedRandomNumber(int numberToGenerate, int possibleSymbols) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        if (numberToGenerate > 36) {
            System.out.println("Error: can't generate a secret number with a length of " + numberToGenerate + "" +
                    " because there aren't enough unique digits.");
        } else {
            for (int i = 0; i < numberToGenerate; i++) {
                if (i == 0) {
                    stringBuilder.append(AlphaNumericString.charAt(random.nextInt(possibleSymbols)));
                } else {
                    String generatedValue = Character.toString(AlphaNumericString.charAt(random.nextInt(possibleSymbols)));

                    while (stringBuilder.toString().contains(generatedValue)) {
                        generatedValue = Character.toString((AlphaNumericString.charAt(random.nextInt(possibleSymbols))));
                    }
                    if (!stringBuilder.toString().contains(generatedValue)) {
                        stringBuilder.append(generatedValue);
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private String secretCodeSymbolGenerator(int codeLength) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            stringBuilder.append("*");
        }
        return stringBuilder.toString();
    }

    private String getLastIntervalLetter(int possibleSymbols) {
        String letter = Character.toString(AlphaNumericString.charAt(possibleSymbols - 1));
        return letter;
    }
}
