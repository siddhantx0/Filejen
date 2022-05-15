import java.io.*;
import java.util.*;

public class Filejen {
    public static void main(String[] args) {
        // Filejen Initialization
        try {
            Scanner sc = new Scanner(Filejen.presets); // must provide complete path to presets fi
            Scanner input = new Scanner(System.in);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static final File presets = new File("/Users/siddhantsingh/Desktop/Personal Projects.22/Filejen.22/presets.txt");
    private int loginIndex;
    private String username;
    private HashMap<String, Classjen> presetCommands;

    public Filejen(Scanner sc) {
        String s = sc.nextLine();
        this.loginIndex = Integer.parseInt(s.substring(s.indexOf(":") + 1));
        s = sc.nextLine();
        this.username = s.substring(s.indexOf(":") + 1);
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            if (s.contains("{")) {
                Classjen c = new Classjen(sc); // advances scanner input
                presetCommands.put(s.substring(0, s.indexOf(":")), c);
            }
        }
    }

    // object types for presets.txt file
    class Classjen {
        HashMap<String, String> commands;

        public Classjen(Scanner sc) {
            String prev = "";
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                if (s.contains("}"))
                    break;
                else if (s.contains("["))
                
            }
        }
    }
}

// public static final String ANSI_RESET = "\u001B[0m";
// public static final String ANSI_BLACK = "\u001B[30m";
// public static final String ANSI_RED = "\u001B[31m";
// public static final String ANSI_GREEN = "\u001B[32m";
// public static final String ANSI_YELLOW = "\u001B[33m";
// public static final String ANSI_BLUE = "\u001B[34m";
// public static final String ANSI_PURPLE = "\u001B[35m";
// public static final String ANSI_CYAN = "\u001B[36m";
// public static final String ANSI_WHITE = "\u001B[37m";
// public static void main(String[] args) {
// // Filejen Initialization

// try {
// File presets = new File("/Users/siddhantsingh/Desktop/Personal
// Projects.22/Filejen.22/presets.txt");
// Scanner sc = new Scanner(presets); // must provide complete path to presets
// fi
// Scanner input = new Scanner(System.in);
// // Main running...
// // rainbow();
// System.out.println(ANSI_RED + "Filejen " + ANSI_CYAN + "Commandline Runner: "
// + ANSI_RESET);
// System.out.println("Enter Filename: ");
// String name = input.nextLine().trim();
// File f = new File(name);
// f.createNewFile();

// sc.close();
// input.close();
// // rainbow();
// } catch (Exception e) {
// e.printStackTrace();
// }

// }

// public static void printSp() {
// System.out.println("\n\n");
// }

// public static void rainbow() {
// System.out.println(ANSI_WHITE + "*".repeat(40) + ANSI_RESET);
// System.out.println(ANSI_RED + "*".repeat(40) + ANSI_RESET);
// System.out.println(ANSI_YELLOW + "*".repeat(40) + ANSI_RESET);
// System.out.println(ANSI_GREEN + "*".repeat(40) + ANSI_RESET);
// System.out.println(ANSI_BLUE + "*".repeat(40) + ANSI_RESET);
// System.out.println(ANSI_PURPLE + "*".repeat(40) + ANSI_RESET);
// System.out.println(ANSI_WHITE + "*".repeat(40) + ANSI_RESET);
// }
// }