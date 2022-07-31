package view;

import controller.MainController;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Scanner;

public class MainMenu {
    MainController controller = new MainController();

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the address of your root folder:");
            String fileAddress = scanner.nextLine();
            try {
                controller.readAndStoreWordsInDirectory(fileAddress);
                System.out.println("Directory successfully added");
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Invalid address");
            }
        }
        while (true) {
            System.out.println("Enter a word to query:");
            String word = scanner.nextLine();
            if (word.contains(" ")) {
                System.out.println("You can't search a word with space!");
                continue;
            }
            System.out.println(controller.search(word));
        }
    }
}
