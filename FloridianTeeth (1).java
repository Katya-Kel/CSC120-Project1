import java.util.Scanner;

/**
 * Project 1 : Floridian Tooth Records program for the Florida Dental Association.
 * Stores the teeth of each family member in a 3D char array.
 * (person, upper/lower row, tooth number) and lets the user print the teeth, extract a tooth, or report the quadratic root canals.
 * 
 */

public class FloridianTeeth {

    private static final Scanner keyboard = new Scanner(System.in);

    private static final int MAX_PEOPLE = 6;
    private static final int MAX_TEETH = 8;
    private static final int UPPER = 0;
    private static final int LOWER = 1;

    /**
    * Runs the program: reads the family, then repeats the menu until the user exits.
    * 
    * @param args command line arguments is not used
    */

    public static void main(String args[]) {

        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        int numberOfPeople = inputNumberOfPeople();

        String[] names = new String[numberOfPeople];
        char[][][] teeth = new char[numberOfPeople][2][MAX_TEETH];

        inputFamily(names, teeth);

        char choice = inputMenuChoice();

        while (choice != 'X') {
            if (choice == 'P') {
                printTeeth(names, teeth);
            } else if (choice == 'E') {
                extractTooth(names, teeth);
            } else {
                reportRootCanals(teeth);
            }
            choice = inputMenuChoice();
        }
        System.out.println();
        System.out.println("Exiting the Floridian Tooth Records :-)");

    }//end of main method
    
    /**
    * Asks the user for the number of people in the family and
    * keeps asking until the number is between 1 and MAX_PEOPLE.
    *
    * @return the valid number of people in the family
    */

    private static int inputNumberOfPeople() {

        //input number of people in family
        System.out.print("Please enter number of people in the family: ");
        int number = keyboard.nextInt();

        while (number < 1 || number > MAX_PEOPLE) {
            //input number of people again because the last one was invalid
            System.out.print("Invalid number of people, try again: ");
            number = keyboard.nextInt();
        }

        //returning valid number of people
        return number;

    }//end of inputNumberOfPeople method
    
    /**
    * Fills in the name and the upper and lower teeth of every family member.
    *
    * @param names the array of family member names to fill in
    * @param teeth the 3D array of teeth to fill in [person][row][tooth]
    */
    
    private static void inputFamily(String[] names, char[][][] teeth) {

        for (int person = 0; person < names.length; person++) {
    
            //input name of family member
            System.out.print("Please enter the name for family member " + (person + 1) + "   : ");
            names[person] = keyboard.next();

            // the uppers for this person are input inside fillRow
            System.out.print("Please enter the uppers for " + names[person] + " : ");
            fillRow(teeth[person][UPPER]);

            //the lowers for this person are input inside fillRow
            System.out.print("Please enter the lowers for " + names[person] + " : ");
            fillRow(teeth[person][LOWER]);
        }

    }//end of inputFamily method
    
    /**
    * Reads one row of teeth (letters I, B or M), checks it is valid and not
    * longer than MAX_TEETH, then stores it in the row.
    * Empty spots at the end of the row are marked with a space.
    *
    * @param row the row of teeth to fill in
    */

    private static void fillRow(char[] row) {
        //input row of teeth as a string of letters
        String input = keyboard.next().toUpperCase();

        while (!validTeethTypes(input) || input.length() > MAX_TEETH) {
            if (!validTeethTypes(input)) {
                System.out.print("Invalid teeth types, try again: ");
            } else {
                System.out.print("Too many teeth, try again: ");
            }
        //input row of teeth again because the last one was invalid
        input = keyboard.next().toUpperCase();
    }

    //empty spots are marked with a space
    for (int index = 0; index < row.length; index++) {
        if (index < input.length()) {
            row[index] = input.charAt(index);
        } else {
            row[index] = ' ';
        }
    }

}//end of fillRow method

 /**
* Checks that every letter in the input is a valid tooth type (I, B or M).
*
* @param input the string of teeth letters to check
* @return true if every letter is I, B or M, otherwise false
*/

private static boolean validTeethTypes(String input) {

   for (int index = 0; index < input.length(); index++) {
        char letter = input.charAt(index);
        if (letter != 'I' && letter != 'B' && letter != 'M') {
            //returning false becuase this letter is not a valid tooth type
            return false;
        }
    }
    //returning true because every letter was valid tooth type
    return true;

}//end of validTeethTypes method

/**
* Shows the menu and keeps asking until the user enters P, E, R or X.
*
* @return the menu choice as an uppercase character (P, E, R or X)
*/

private static char inputMenuChoice() {

    System.out.println();
    //input menu choice
    System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it: ");
    String choice = keyboard.next().toUpperCase();

    while (!choice.equals("P") && !choice.equals("E") && !choice.equals("R") && !choice.equals("X")) {
        //input menu chocie again because last one was invalid
        System.out.print("Invalid menu option, try again: ");
        choice = keyboard.next().toUpperCase();
    }

    return choice.charAt(0);

}//end of inputMenuChoice method

/**
* Prints the name and the uppers and lowers of every family member.
*
* @param names the array of family member names
* @param teeth the 3D array of teeth [person][row][tooth]
*/

private static void printTeeth(String[] names, char[][][] teeth) {

    System.out.println();

    for (int person = 0; person < names.length; person++) {
        System.out.println(names[person]);

        System.out.print("Uppers: ");
        printRow(teeth[person][UPPER]);

        System.out.print("Lowers: ");
        printRow(teeth[person][LOWER]);
    }

}//end of printTeeth method

/**
* Prints one row of teeth as tooth number and type, skipping empty spots.
*
* @param row the row of teeth to print
*/

private static void printRow(char[] row) {

    for (int index = 0; index < row.length; index++) {
        if (row[index] != ' ') {
            System.out.print(" " + (index + 1) + ":" + row[index] + " ");
        }
    }
    System.out.println();

}//end of printRow method
    
/**
* Asks which family member, which layer and which tooth to extract,
* then marks that tooth as missing (M).
*
* @param names the array of family member names
* @param teeth the 3D array of teeth [person][row][tooth]
*/
    
private static void extractTooth(String[] names, char[][][] teeth) {

    //which person
    System.out.print("Which family member: ");
    String name = keyboard.next();
    int person = findPerson(names, name);

    while (person == -1) {
        System.out.print("Invalid family member, try again: ");
        name = keyboard.next();
        person = findPerson(names, name);
    }

    //which layer
    System.out.print("Which tooth layer (U)pper or (L)ower : ");
    String layerInput = keyboard.next().toUpperCase();

    while (!layerInput.equals("U") && !layerInput.equals("L")) {
        System.out.print("Invalid layer, try again : ");
        layerInput = keyboard.next().toUpperCase();
    }

    int layer;
    if (layerInput.equals("U")) {
        layer = UPPER;
    } else {
        layer = LOWER;
    }

   //which tooth
    System.out.print("Which tooth number: ");
    int tooth = keyboard.nextInt();

    while (true) {
        if (tooth < 1 || tooth > MAX_TEETH || teeth[person][layer][tooth - 1] == ' ') {
            System.out.print("Invalid tooth number, try again : ");
        } else if (teeth[person][layer][tooth - 1] == 'M') {
            System.out.print("Missing tooth, try again : ");
        } else {
            break;
        }
        tooth = keyboard.nextInt();
    }

        teeth[person][layer][tooth - 1] = 'M';

}//end of extractTooth method
    
/**
* Searches for a family member by name, ignoring upper and lower case.
*
* @param names the array of family member names
* @param name the name to search for
* @return the index of the person in the array, or -1 if not found
*/

    private static int findPerson(String[] names, String name) {

        for (int person = 0; person < names.length; person++) {
            if (names[person].equalsIgnoreCase(name)) {
                return person;
            }
        }
        return -1;

}//end of findPerson method
    
/**
* Counts the incisors (I), bicuspids (B) and missing teeth (M) of the whole
* family and prints the roots of the quadratic equation
* incisors*x^2 + bicuspids*x - missing = 0.
*
* @param teeth the 3D array of teeth [person][row][tooth]
*/

private static void reportRootCanals(char[][][] teeth) {

    int incisors = 0;
    int bicuspids = 0;
    int missing = 0;

    for (int person = 0; person < teeth.length; person++) {
        for (int row = 0; row < teeth[person].length; row++) {
            for (int tooth = 0; tooth < teeth[person][row].length; tooth++) {
                if (teeth[person][row][tooth] == 'I') {
                    incisors++;
                } else if (teeth[person][row][tooth] == 'B') {
                    bicuspids++;
                } else if (teeth[person][row][tooth] == 'M') {
                    missing++;
                }
            }
        }
    }

    double discriminant = bicuspids * bicuspids + 4.0 * incisors * missing;

    if (incisors == 0) {
        System.out.println("No quadratic root canals (no incisors)");
    } else {
        double root1 = (-bicuspids + Math.sqrt(discriminant)) / (2.0 * incisors);
        double root2 = (-bicuspids - Math.sqrt(discriminant)) / (2.0 * incisors);
        System.out.printf("One root canal at     %.2f %n", root1);
        System.out.printf("Another root canal at %.2f %n", root2);
    }

}//end of reportRootCanals method

}//end of FloridianTeeth class