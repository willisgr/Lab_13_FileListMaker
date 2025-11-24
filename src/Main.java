import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
public class Main {
    static Scanner in = new Scanner(System.in);
    static ArrayList<String> list = new ArrayList<>();
    static JFileChooser chooser = new JFileChooser();
    static boolean isQuiting = false;
    static boolean fileNeedsSaved = false;
    static File currentFile;
    public static void main(String[] args) {
        String userInput;
        do {
            printListAndMenu();
            userInput = SafeInput.getRegExString(in, "Enter a command", "[AaDdIiVvQqMmOoSsCc]");
            switch (userInput) {
                case "a", "A" -> appendList();
                case "d", "D" -> deleteFromList();
                case "i", "I" -> insertList();
                case "m", "M" -> moveListItem();
                case "c", "C" -> list.clear();
                case "v", "V" -> printListAndIndices();
                case "o", "O" -> openListFile();
                case "s", "S" -> saveListFile();
                case "q", "Q" -> quitProgram();
                default -> System.out.println("Invalid command, try again.");
            }
        } while(!isQuiting);
    }
    private static void deleteFromList() {
        if (list.isEmpty()) {
            System.out.println("list has no values to delete");
            return;
        }
        int index = SafeInput.getRangedInt(in, "Enter index of item to delete", 1, list.size()) - 1;
        list.remove(index);
        fileNeedsSaved = true;
    }
    private static void insertList() {
        String value = SafeInput.getNonZeroLenString(in, "Enter value to insert");
        if (list.isEmpty()) {
            list.add(value);
            return;
        }
        int index = SafeInput.getRangedInt(in, "Enter location to insert at", 1, list.size()) - 1;
        list.add(index, value);
        fileNeedsSaved = true;
    }
    private static void appendList() {
        String value = SafeInput.getNonZeroLenString(in, "Enter value to add");
        list.add(value);
        fileNeedsSaved = true;
    }
    private static void printListAndMenu() {
        for (int i = 0; i <= 20; i++) {
            System.out.print("-");
        }
        System.out.print("""
                \nA - Add an item
                D - Delete an item
                I - Insert an item
                M - Move an item
                C - Clear the list
                V - View the list
                O - Open a list from disk
                S - Save the current list to disk
                Q - Quit program
                """);
        for (int i = 0; i <= 20; i++) {
            System.out.print("-");
        }
        for (String value: list) {
            System.out.println("\n" + value);
            for (int i = 0; i <= 20; i++) {
                System.out.print("-");
            }
        }
    }
    private static void printListAndIndices() {
        for (int i = 0; i <= 20; i++) {
            System.out.print("-");
        }
        for (String value: list) {
            System.out.println("\nIndex: " + (list.indexOf(value) + 1) + " | value: " + value);
            for (int i = 0; i <= 20; i++) {
                System.out.print("-");
            }
        }
        System.out.print("\n");
    }
    private static void moveListItem() {
        printListAndIndices();
        if (list.isEmpty()) {
            System.out.println("No items to move, List is empty.");
            return;
        }
        int movedItemIndex = SafeInput.getRangedInt(in, "Enter the index of the item you want to move", 1, list.size());
        String movedItem = list.get(movedItemIndex - 1);
        int targetIndex = SafeInput.getRangedInt(in, "Enter destination index of moved item", 1, list.size()) - 1;
        list.remove(movedItemIndex);
        list.add(targetIndex, movedItem);
        fileNeedsSaved = true;
    }
    private static void openListFile() {
        File selectedFile;
        String currentItem;
        if (fileNeedsSaved) {
            savePrompt();
        }
        list.clear();

        try {
            File workingDir = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDir);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                currentFile = selectedFile;
                Path filePath = selectedFile.toPath();
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(filePath, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));
                while (reader.ready()) {
                    currentItem = reader.readLine();
                    list.add(currentItem);
                }
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void saveListFile() {
        if (list.isEmpty()) {
            System.out.println("You cannot save an empty list");
            return;
        }
        if (currentFile == null) {
            String fileName = SafeInput.getNonZeroLenString(in, "Enter the name of the list file");
            File workingDir = new File(System.getProperty("user.dir"));
            currentFile = new File(Paths.get(workingDir.getPath() + "\\src\\" + fileName + ".txt").toString());
        }
        try {
            Path file = Paths.get(currentFile.getPath());
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            for (String value : list) {
                writer.write(value, 0, value.length());
                writer.newLine();
            }
            writer.close();
            fileNeedsSaved = false;
            System.out.println("List file saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void savePrompt() {
        System.out.println("You have unsaved changes.");
        if (SafeInput.getYNConfirm(in, "Save your changes?")) {
            saveListFile();
        }
    }
    private static void quitProgram() {
        if (!SafeInput.getYNConfirm(in, "Are you sure?")) {
            return;
        }
        if (fileNeedsSaved) {
            savePrompt();
        }
        isQuiting = true;
    }
}
