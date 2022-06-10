import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {


    static String[][] tasks;

    public static void main(String[] args) {


        tasks = readTasksFromFile("tasks.csv");
        showMenu();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {

                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "list":
                    listTasks(tasks);
                    break;
                case "exit":
                    exit();
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
        }
    }


    public static void showMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:\n" + ConsoleColors.RESET + "add\nremove\nlist\nexit\n");
    }

    public static String[][] readTasksFromFile(String filename) {
        String[][] arr = new String[0][3];
        File file = new File(filename);
        //int lineCounter = 0;
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String[] stringTemp = new String[3];
                String line = scanner.nextLine();
                //System.out.println(line);
                stringTemp = line.split(",");
                //System.out.println(Arrays.toString(stringTemp));


                arr = Arrays.copyOf(arr, arr.length + 1);
                arr[arr.length - 1] = stringTemp;
                //System.out.println(Arrays.toString(arr));
            }

        } catch (FileNotFoundException e) {
            System.out.printf("Wystąpił problem z plikiem: %s", e.getMessage());
        }
        System.out.println(Arrays.toString(arr));

        return arr;
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        String[] singleTask = new String[3];
        System.out.println("Please add task description");
        singleTask[0] = scanner.nextLine();
        System.out.println("Please add task due date [YYYY-MM-DD]");
        singleTask[1] = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        singleTask[2] = scanner.nextLine();
        //System.out.println(Arrays.toString(singleTask));
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = singleTask;
    }

    public static void removeTask() {
        System.out.println("remove");
        System.out.println("Please select number to remove.");
        Scanner scanner = new Scanner(System.in);
        int numberToRemove = scanner.nextInt();
        if (numberToRemove < tasks.length && numberToRemove >= 0) {
            tasks = ArrayUtils.remove(tasks, numberToRemove);
        } else {
            System.out.println("Number to remove doesn't exist!!!");
        }
        System.out.println(" Value was successfully removed");
    }

    public static void listTasks(String[][] tab) {
        System.out.println("list\n");
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
        //showMenu();
    }

    public static void exit() {
        //saveTasks();
        System.out.println(ConsoleColors.RED + "Bye, bye." + ConsoleColors.RESET);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("tasks.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < tasks.length; i++) {

                printWriter.println(String.join(", ", tasks[i]));

        }
        printWriter.close();
        System.exit(0);
    }

}
