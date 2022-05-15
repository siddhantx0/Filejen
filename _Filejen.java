import java.io.File;
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

    }

    private void input() {
        Scanner sc = new Scanner(System.in);

        this.completed = false;
        System.out.println("Enter filename: ");
        this.filename = sc.nextLine().trim();
        System.out.println("Enter filetype " + this.fileTypes.toString() + ": ");
        this.fileType = sc.nextLine().trim().toLowerCase();
        if (!this.fileTypes.contains(this.fileType))
            try {
                throw new Exception("Faulty filetype input please try again.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        System.out.println("Would you like to generate an input file? ");
        if (sc.nextLine().trim().toLowerCase().equals("y"))
            this.generateInputFile = true;
        System.out.println("Would you like to generate an output file? ");
        if (sc.nextLine().trim().toLowerCase().equals("y"))
            this.generateOutputFile = true;

        sc.close();
    }

    private void parsePath() {
        try {
            Scanner sc = new Scanner(new File(presetsPath));
            for (int i = 0; i < 3; ++i)
                sc.nextLine();
            int size = Integer.parseInt(sc.nextLine());
            if (size > fileTypes.size())
                throw new Exception("Faulty preset filetype setup...");
            this.languages = new ArrayList<Language>();
            for (int i = 0; i < size; ++i)
                this.languages.add(new Language(sc.nextLine().trim().toLowerCase()));
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generate() {

    }

    class Language {
        String type;
        HashMap<String, String> commandToOutputMap;

        public Language(String type) {
            this.type = type;
            this.commandToOutputMap = new HashMap<String, String>();
        }
    }
}