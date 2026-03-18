package com.studentmanagement.utils;

import java.util.Scanner;

/**
 * Utility class for console operations and formatting
 */
public class ConsoleUtils {

    /**
     * Clears the console screen
     */
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Displays a formatted header with title
     * @param title Header title
     */
    public static void showHeader(String title) {
        int width = 50;
        String line = repeatChar('═', width);

        System.out.println("╔" + line + "╗");
        System.out.println("║" + centerText(title, width) + "║");
        System.out.println("╚" + line + "╝");
        System.out.println();
    }

    /**
     * Repeats a character a specified number of times
     * @param c Character to repeat
     * @param count Number of repetitions
     * @return String of repeated characters
     */
    public static String repeatChar(char c, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Displays a loading animation
     * @param message Loading message
     */
    public static void showLoading(String message) {
        System.out.print(" " + message);

        String[] dots = {".  ", ".. ", "..."};
        for (int i = 0; i < 10; i++) {
            System.out.print("\r " + message + dots[i % 3]);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\r " + message + " completed!");
    }

    /**
     * Prompts user to press Enter to continue
     * @param scanner Scanner for input
     */
    public static void pressEnterToContinue(Scanner scanner) {
        System.out.print("\n Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Displays a success message
     * @param message Success message
     */
    public static void showSuccess(String message) {
        System.out.println(" " + message);
    }

    /**
     * Displays an error message
     * @param message Error message
     */
    public static void showError(String message) {
        System.out.println(" " + message);
    }

    /**
     * Displays a warning message
     * @param message Warning message
     */
    public static void showWarning(String message) {
        System.out.println(" " + message);
    }

    /**
     * Displays an info message
     * @param message Info message
     */
    public static void showInfo(String message) {
        System.out.println(" " + message);
    }

    /**
     * Centers text within a specified width
     * @param text Text to center
     * @param width Total width
     * @return Centered text
     */
    private static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }

        int padding = (width - text.length()) / 2;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < padding; i++) {
            result.append(" ");
        }

        result.append(text);

        while (result.length() < width) {
            result.append(" ");
        }

        return result.toString();
    }
}