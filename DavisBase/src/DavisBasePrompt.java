import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DavisBasePrompt {


    static String prompt = "davisql> ";
    static String copyright = "Tejaswi Macharla";
    static String version = "v1.0";
    static boolean isExit = false;

    public static int pageSize = 512;

    static Scanner scanner = new Scanner(System.in).useDelimiter(";");


    public static void main(String[] args) {
        Init.init();

        splashScreen();


        String userCommand = "";

        while (!isExit) {
            System.out.print(prompt);
            userCommand = scanner.next().replace("\n", " ").replace("\r", "").trim().toLowerCase();
            parseUserCommand(userCommand);
        }
        System.out.println("Exiting...");
        System.out.println("DavisBase Exited");

    }

    public static void splashScreen() {
        System.out.println(line("-", 80));
        System.out.println("Welcome to DavisBase");
        System.out.println("DavisBase Version " + version);
        System.out.println(copyright);
        System.out.println("\nType \"help;\" to display supported commands.");
        System.out.println(line("-", 80));
    }

    public static String line(String s, int num) {
        String a = "";
        for (int i = 0; i < num; i++) {
            a += s;
        }
        return a;
    }

    public static void help() {
        System.out.println(line("*", 80));
        System.out.println("List of all DavisBase commands:");
        System.out.println("All commands below are case insensitive");
        System.out.println("1.DDL Commands:");
        System.out.println("\t(a)SHOW TABLES;                                                  Displays a list of all tables in DavisBase");
        System.out.println("\t(b)CREATE TABLE <table_name>;                                    Creates a new table schema, i.e. a new empty table");
        System.out.println("\t(c)DROP TABLE <table_name>;                                      Remove a table schema, and all of its contained data");
        System.out.println();
        System.out.println("2.DML Commands:");
        System.out.println("\t(a)INSERT INTO table_name [column_list] VALUES value_list;       Inserts a single record into a table");
        System.out.println();
        System.out.println("3.VDL Commands: ");
        System.out.println("\t(a)SELECT * FROM <table_name>;                                   Display all records in the table");
        System.out.println("\t(b)SELECT * FROM <table_name> WHERE column_name = <value>;       Display records satisfying a particular condition");
        System.out.println();
        System.out.println("4.EXIT;                                                            Exit the program and save");
        System.out.println();
        System.out.println("5.HELP;                                                            Show this help information");
        System.out.println();

        System.out.println(line("*", 80));
    }

    public static boolean tableExists(String tablename) {
        tablename = tablename + ".tbl";

        try {
            File dataDir = new File("data");
            String[] oldTableFiles;
            oldTableFiles = dataDir.list();
            for (int i = 0; i < oldTableFiles.length; i++) {
                if (oldTableFiles[i].equals(tablename))
                    return true;
            }
        } catch (SecurityException se) {
            System.out.println("Unable to create data container directory");
            System.out.println(se);
        }

        return false;
    }

    public static String[] parserEquation(String equ) {
        String comparator[] = new String[3];
        String temp[] = new String[2];
        if (equ.contains("=")) {
            temp = equ.split("=");
            comparator[0] = temp[0].trim();
            comparator[1] = "=";
            comparator[2] = temp[1].trim();
        }

        if (equ.contains("<")) {
            temp = equ.split("<");
            comparator[0] = temp[0].trim();
            comparator[1] = "<";
            comparator[2] = temp[1].trim();

        }

        if (equ.contains(">")) {
            temp = equ.split(">");
            comparator[0] = temp[0].trim();
            comparator[1] = ">";
            comparator[2] = temp[1].trim();
        }

        if (equ.contains("<=")) {
            temp = equ.split("<=");
            comparator[0] = temp[0].trim();
            comparator[1] = "<=";
            comparator[2] = temp[1].trim();
        }

        if (equ.contains(">=")) {
            temp = equ.split(">=");
            comparator[0] = temp[0].trim();
            comparator[1] = ">=";
            comparator[2] = temp[1].trim();
        }
        return comparator;
    }

    public static void parseUserCommand(String userCommand) {

        ArrayList < String > commandTokens = new ArrayList < String > (Arrays.asList(userCommand.split(" ")));

        switch (commandTokens.get(0)) {

            case "show":
                ShowTables.showTables();
                break;

            case "create":
                CreateTable.parseCreateString(userCommand);
                break;

            case "insert":
                Insert.parseInsertString(userCommand);
                break;

            case "select":
                parseQueryString(userCommand);
                break;

            case "drop":
                DropTable.dropTable(userCommand);
                break;

            case "help":
                help();
                break;

            case "exit":
                isExit = true;
                break;

            case "quit":
                isExit = true;
                break;

            default:
                System.out.println("command not recognized: \"" + userCommand + "\"" + "Please use help options");
                System.out.println();
                break;
        }
    }
    public static void parseQueryString(String queryString) {
        String[] cmp;
        String[] column;
        String[] temp = queryString.split("where");
        if (temp.length > 1) {
            String tmp = temp[1].trim();
            cmp = parserEquation(tmp);
        } else {
            cmp = new String[0];
        }
        String[] select = temp[0].split("from");
        String tableName = select[1].trim();
        String cols = select[0].replace("select", "").trim();
        if (cols.contains("*")) {
            column = new String[1];
            column[0] = "*";
        } else {
            column = cols.split(",");
            for (int i = 0; i < column.length; i++)
                column[i] = column[i].trim();
        }

        if (!tableExists(tableName)) {
            System.out.println("Table " + tableName + " does not exist.");
        } else {
            ShowTables.select(tableName, column, cmp);
        }
    }

}