import java.io.File;
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

            // TODO: COMPLETE THIS!!!
            generate();
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
            for (int i = 0; i < 3; ++i)
                sc.nextLine();
            int size = Integer.parseInt(sc.nextLine());
            if (size > this.fileTypes.size()) {
                System.out.println(
                        "Faulty setup in presets.txt file. Line 4 should be the number of precoded preset languages.");
                System.exit(0);
            }

            this.languages = new ArrayList<_Filejen.Language>();

            for (int i = 0; i < size; ++i)
                this.languages.add(new Language(sc.nextLine().trim().toLowerCase()));
            System.out.println(sc.nextLine()); // $$$
            for (int k = 0; k < this.languages.size(); k++) {
                currentLanguage: while (true && sc.hasNextLine()) {
                    int iterations = Integer.parseInt(sc.nextLine().trim());
                    String s = sc.nextLine();

                    currentLanguagePresetIndexI: for (int i = 0; i < iterations; i++) {
                        if (!sc.hasNextLine())
                            break currentLanguage;
                        String key = "", val = "";
                        if (s.contains("$"))
                            key = s.substring(s.indexOf("$") + ("$").length());
                        System.out.println(key);
                        valueInitialization: while (sc.hasNextLine()) {
                            String temp = sc.nextLine() + "\n";
                            if (temp.contains("****"))
                                temp = temp.substring(0, temp.indexOf("****")) + this.filename
                                        + temp.substring(temp.indexOf("****") + ("****").length());
                            if (temp.contains("$$$"))
                                break currentLanguagePresetIndexI;
                            else if (temp.contains("$")) {
                                System.out.println(key);
                                s = temp;
                                break valueInitialization;
                            }
                            val += temp;
                        }
                        this.languages.get(k).commandToOutputMap.put(key, val);
                    }
                }
            }
            for (Language l : this.languages) {
                System.out.println(l.type + "\n" + l.commandToOutputMap.toString());
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generate() {
        File f = new File(this.filename + "." + this.fileType);
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