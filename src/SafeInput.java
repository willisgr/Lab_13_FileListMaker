import java.util.Scanner;

public class SafeInput {
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.isEmpty());
        return retString;
    }
    public static int getInt(Scanner pipe, String prompt) {
        int retInt = 0;
        String trash = "";
        boolean valid = false;
        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextInt()) {
                retInt = pipe.nextInt();
                pipe.nextLine();
                valid = true;
            } else {
                System.out.println("Invalid input: " + pipe.nextLine());
            }
        } while (!valid);
        return retInt;
    }
    public static double getDouble(Scanner pipe, String prompt) {
        double retDouble = 0;
        boolean valid = false;
        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextDouble()) {
                retDouble = pipe.nextDouble();
                pipe.nextLine();
                valid = true;
            } else {
                System.out.println("Invalid input: " + pipe.nextLine());
            }
        } while (!valid);
        return retDouble;
    }
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int retInt = 0;
        boolean valid = false;
        do {
            System.out.print("\n" + prompt + " [" + low + "-" + high + "]: ");
            if (pipe.hasNextInt()) {
                retInt = pipe.nextInt();
                if (retInt >= low && retInt <= high) {
                    valid = true;
                    pipe.nextLine();
                } else {
                    System.out.println("Invalid input: " + retInt);
                }
            } else {
                System.out.println("Invalid input: " + pipe.next());
            }
        } while (!valid);
        return retInt;
    }
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double retDouble = 0;
        boolean valid = false;
        do {
            System.out.print("\n" + prompt + " [" + low + "-" + high + "]: ");
            if (pipe.hasNextDouble()) {
                retDouble = pipe.nextDouble();
                if (retDouble >= low && retDouble <= high) {
                    valid = true;
                    pipe.nextLine();
                } else {
                    System.out.println("Invalid input: " + retDouble);
                }
            } else {
                System.out.println("Invalid input: " + pipe.next());
            }
        } while (!valid);
        return retDouble;
    }
    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String input = "";
        do {
            System.out.print("\n" + prompt + " [Y or N]: ");
            input = pipe.nextLine();
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("\n Invalid input: " + input);
                input = "";
            }
        } while (input.isEmpty());
        return false;
    }
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        boolean valid = false;
        String retString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
            if (retString.matches(regEx)) {
                valid = true;
            } else {
                System.out.println("Invalid input: " + retString);
            }
        } while (!valid);
        return retString;
    }
    public static void prettyHeader(String msg) {
        int w = 60;
        int ma = 3;
        int r = (w - (ma * 2) - msg.length()) / 2;
        int l = (w - (ma * 2) - msg.length()) - r;
        for (int i = 0; i < w; i++) {
            System.out.print("*");
        }
        System.out.print("\n");
        for (int i = 0; i < ma; i++) {
            System.out.print("*");
        }
        for (int i = 0; i < r; i++) {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < l; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < ma; i++) {
            System.out.print("*");
        }
        System.out.print("\n");
        for (int i = 0; i < w; i++) {
            System.out.print("*");
        }
    }
}
