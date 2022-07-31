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
            System.out.println("         <<Guide>>");
            System.out.println("nothing before a word : and");
            System.out.println("+ before a word : or");
            System.out.println("- before a word : exclude");
            System.out.println("Enter your query:");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            System.out.println(controller.search(input));
        }
    }
}
