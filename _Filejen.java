import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class _Filejen {
    public static void main(String[] args) {
        _Filejen j = new _Filejen();
    }

    // Constant variables
    final private String presetsPath = "/Users/siddhantsingh/Desktop/Personal Projects.22/Filejen.22/presets.txt";

    // Filejen instance variables
    private String filename;
    private String fileType;
    private boolean completed;
    private boolean generateInputFile, generateOutputFile;
    private String presets;

    // presets.txt instance variables
    private int visitedId;
    private String username;
    private List<String> fileTypes;
    private List<Language> languages;

    public _Filejen() {
        System.out.println("Welcome to Filejen.");
        try {
            // parsing user information
            Scanner sc = new Scanner(new File(presetsPath));
            String s = sc.nextLine();
            this.presets = "";
            this.visitedId = Integer.parseInt(s.substring(s.indexOf("id: ") + ("id: ").length()));
            s = sc.nextLine();
            this.username = s.substring(s.indexOf("name: ") + ("name: ".length())).trim();
            s = sc.nextLine().substring(s.indexOf("supported filetypes: ") + ("supported filetypes: ").length() + 1);
            this.fileTypes = new ArrayList<String>();
            Scanner tempParse = new Scanner(s).useDelimiter(", ");
            while (tempParse.hasNext())
                fileTypes.add(tempParse.next().toLowerCase());
            tempParse.close();
            sc.close();
            // System.out.println(this.visitedId + ", " + this.username + "\n" +
            // this.fileTypes.toString());
            /*
             * 1) OK... up to this point all presets.txt instance variables are complete...
             * 2) need to complete Node class and initialize generate
             */
            sc = new Scanner(System.in);
            System.out.print("Press Q for Quick-input (anything else for regular input): ");
            if (sc.nextLine().trim().toLowerCase().equals("q"))
                quickInput();
            else
                input();
            sc.close();

            // TODO: COMPLETE THIS!!!
            parsePath();
            System.out.println("*".repeat(100) + "\n");
            System.out.println("*".repeat(100) + "\n");
            System.out.println("*".repeat(100));

            for (Language l : languages) {
                System.out.println(l.commandToOutputMap.toString());
            }

            System.out.println("*".repeat(100) + "\n");
            System.out.println("*".repeat(100) + "\n");
            System.out.println("*".repeat(100) + "\n");
            System.exit(0);
            // TODO: COMPLETE THIS!!!
            generate();

            // CREATE: update method that updates the presets.txt file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void quickInput() {
        System.out.println("[Filename, Filetype, Presets(a, b, c ...), Inputgen, Outputgen]");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        sc.close();

        String[] in = input.split(("(?<=.)(?=\\p{Lu})"));
        if (in.length < 4) {
            System.out.println("Invalid input.");
            System.exit(0);
        }
        for (int i = 0; i < in.length - 2; i++) {
            if (i == 0)
                this.filename = in[i];
            else if (i == 1)
                this.fileType = in[i];
            else {
                this.presets += in[i] + " ";
            }
        }
        this.generateInputFile = (in[in.length - 2].toLowerCase().trim().equals("y"));
        this.generateOutputFile = (in[in.length - 1].toLowerCase().trim().equals("y"));
    }

    private void input() {
        Scanner sc = new Scanner(System.in);

        this.completed = false;
        System.out.println("Enter filename: ");
        this.filename = sc.nextLine().trim();
        System.out.println("Enter filetype " + this.fileTypes.toString() + ": ");
        this.fileType = sc.nextLine().trim().toLowerCase();
        if (!this.fileTypes.contains(this.fileType)) {
            System.out.println("Faulty filetype input please try again.");
            System.exit(0);
        }

        // TODO: ADD PRESETS LISTS SPECIFIC TO THE LANGUAGE...
        System.out.println("Please enter presets: ADD PRESETS LISTS SPECIFIC TO THE LANGUAGE...");
        this.presets = sc.nextLine().trim(); // parsed in generate method

        System.out.println("Would you like to generate an input file? [Y/N]");
        if (sc.nextLine().trim().toLowerCase().equals("y"))
            this.generateInputFile = true;
        System.out.println("Would you like to generate an output file? [Y/N]");
        if (sc.nextLine().trim().toLowerCase().equals("y"))
            this.generateOutputFile = true;
        System.out.println("Would you like to open a vscode view with generated files? [Y/N]");
        if (sc.nextLine().trim().toLowerCase().equals("y")) {
            System.out.println("Opening new vscode view...");
            openView();
        }
        this.completed = true;
        if (this.completed) {
            System.out.println("Thank you for using Filejen.");
        }
        sc.close();
    }

    private void parsePath() {
        try {
            Scanner sc = new Scanner(new File(presetsPath));
            String s = "";
            for (int i = 0; i < 3; i++)
                sc.nextLine();
            // parsing language features
            int nLanguages = Integer.parseInt(sc.nextLine().trim());
            this.languages = new ArrayList<_Filejen.Language>();
            for (int i = 0; i < nLanguages; i++)
                this.languages.add(new Language(sc.nextLine().trim()));
            if (!sc.nextLine().trim().equals("$$$")) {
                System.out.println("Brocken");
                System.exit(0);
            }
            boolean reachedNewLang = true;
            for (int i = 0; i < this.languages.size(); i++) {
                String key = "", val = "";
                int k = 0;
                if (reachedNewLang) {
                    reachedNewLang = false;
                    System.out.println(s);
                    k = sc.nextInt(); // number of presets of this specific language
                    sc.nextLine();
                }
                s = sc.nextLine();
                in_outer: for (; k >= 0; k--) {
                    key = s.substring(1).trim();
                    while (sc.hasNextLine()) {
                        s = sc.nextLine();
                        if (s.trim().equals("$$$")) {
                            reachedNewLang = true;
                            break in_outer;
                        } else if (s.contains("$"))
                            break;
                        else if (s.contains("****"))
                            s = s.substring(0, s.indexOf("****")) + this.filename
                                    + s.substring(s.indexOf("****") + "****".length());
                        val += s + "\n";
                    }
                    this.languages.get(i).commandToOutputMap.put(key, val);
                    // System.out.println(String.format("%s%s", key, val.substring(0, 10)));
                }
            }

            // closing scanner
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(
                    "Unexpected Error: parsePath() failed to compute. Please check presets.txt or parsePath()");
            System.exit(0);
        }
    }

    private void generate() {
        File f = new File(this.filename + "." + this.fileType);

        if (f.exists()) {
            System.out.println(String.format("File: \"%s\" already exists.", f.toString()));
            System.exit(0);
        }

        try {
            FileWriter fw = new FileWriter(f);
            Scanner sc = new Scanner(this.presets);
            Language l = null;
            for (Language lang : this.languages)
                if (lang.type.equals(this.fileType)) {
                    l = lang;
                    break;
                }
            while (sc.hasNext()) {
                fw.write(l.commandToOutputMap.get(sc.next().trim()));
            }
            fw.close();
            sc.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            f.createNewFile();
        } catch (Exception e) {
            System.out.println("Error: filetype already exists in this directory");
            System.exit(0);
        }
        if (this.generateInputFile) {
            File in = new File(this.filename + ".in");
            try {
                in.createNewFile();
            } catch (Exception e) {
                System.out.println("Error: 'in' filetype already exists in this directory");
                System.exit(0);
            }
        }
        if (this.generateOutputFile) {
            File in = new File(this.filename + ".out");
            try {
                in.createNewFile();
            } catch (Exception e) {
                System.out.println("Error: 'out' filetype already exists in this directory");
                System.exit(0);
            }
        }
    }

    private void openView() {
        // TODO: Complete openView.
    }

    class Language {
        String type;
        HashMap<String, String> commandToOutputMap;

        public Language(String type) {
            this.type = type;
            this.commandToOutputMap = new HashMap<String, String>();
        }

        @Override
        public String toString() {
            return this.type;
        }
    }
}