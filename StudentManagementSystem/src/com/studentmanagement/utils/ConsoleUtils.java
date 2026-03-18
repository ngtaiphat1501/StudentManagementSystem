package com.studentmanagement.utils;

import java.util.Scanner;

public class ConsoleUtils {

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // In nhiều dòng trống nếu không clear được
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void showHeader(String title) {
        int width = 50;
        String line = repeatChar('═', width);

        System.out.println("╔" + line + "╗");
        System.out.println("║" + centerText(title, width) + "║");
        System.out.println("╚" + line + "╝");
        System.out.println();
    }

// Hàm thay thế cho repeat()
    public static String repeatChar(char c, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static void showLoading(String message) {
        System.out.print("⏳ " + message);

        String[] dots = {".  ", ".. ", "..."};
        for (int i = 0; i < 10; i++) {
            System.out.print("\r⏳ " + message + dots[i % 3]);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\r✅ " + message + " hoàn thành!");
    }

    public static void pressEnterToContinue(Scanner scanner) {
        System.out.print("\n⏎ Nhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    public static void showSuccess(String message) {
        System.out.println("✅ " + message);
    }

    public static void showError(String message) {
        System.out.println("❌ " + message);
    }

    public static void showWarning(String message) {
        System.out.println("⚠️ " + message);
    }

    public static void showInfo(String message) {
        System.out.println("ℹ️ " + message);
    }

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
