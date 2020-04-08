package blake.rps;
/*******************************************************************
 *  RpsLogic class
 *  Description: This is where all of the logic decisions for
 *  playing the roshambo game go, along with the variable
 *  preparation that is used by the user interface.
 *******************************************************************/

// Imported Libraries
import java.util.Random;

public class RpsLogic {

    private static int counter = 1;
    private static String prevuser = " ";
    private static String prevcomp = " ";
    private static String curruser = " ";
    private static String currcomp = " ";
    private static String result = " ";
    private static String presult = " ";
    private static String ppresult = " ";
    private static int userscore = 0;
    private static int compscore = 0;
    private static String pprevuser = " ";
    private static String pprevcomp = " ";

    public static void getCurrentPlaySession(String cps)
    {
        curruser = cps;
        result = getCompResult();
        RpsSwingInterface.textarea.setText("Round [ " + counter + " ], You chose " + curruser + ", I chose " + currcomp + ", * " + result + " *, Your Score [ " + userscore + " ], My Score [ " + compscore + " ]\n" + RpsSwingInterface.textarea.getText());
        RpsJsonTools.addToJsonArray(String.valueOf(counter), curruser, currcomp, String.valueOf(userscore), String.valueOf(compscore), result);
        counter++;
    }

    public static String getCompMove()
    {
        String cm = " ";
        // beat key spams #1
        if ( ( prevcomp.equals(currcomp) || prevuser.equals(curruser) ) && ( presult.equals("You Win!") || presult.equals("We Tied!") ) ) {
            if (prevuser.equals("Rock")) {
                cm = "Paper";
            }
            else if (prevuser.equals("Paper")) {
                cm = "Scissors";
            }
            else if (prevuser.equals("Scissors")) {
                cm = "Rock";
            }
        }
        // beat key spams #2
        else if ( presult.equals("You Win!") && ppresult.equals("You Win!") ) {
            if (prevuser.equals("Rock")) {
                cm = "Paper";
            }
            else if (prevuser.equals("Paper")) {
                cm = "Scissors";
            }
            else if (prevuser.equals("Scissors")) {
                cm = "Rock";
            }
        }
        else if (prevuser.equals("Rock")) {
            if (prevcomp.equals("Rock")) {
                cm = "Paper";
            }
            else if (prevcomp.equals("Paper")) {
                cm = "Rock";
            }
            else if (prevcomp.equals("Scissors")) {
                cm = "Scissors";
            }
        }
        else if (prevuser.equals("Paper")) {
            if (prevcomp.equals("Rock")) {
                cm = "Rock";
            }
            else if (prevcomp.equals("Paper")) {
                cm = "Scissors";
            }
            else if (prevcomp.equals("Scissors")) {
                cm = "Paper";
            }
        }
        else if (prevuser.equals("Scissors")) {
            if (prevcomp.equals("Rock")) {
                cm = "Scissors";
            }
            else if (prevcomp.equals("Paper")) {
                cm = "Paper";
            }
            else if (prevcomp.equals("Scissors")) {
                cm = "Rock";
            }
        }
        else {
            cm = getRandomSeed();
        }
        return cm;
    }

    public static String getRandomSeed()
    {
        Random random = new Random();
        String rs = " ";
        int randomSeed = random.nextInt(3)+1;
        if (randomSeed == 1) {
            rs = "Rock";
        }
        else if (randomSeed == 2) {
            rs = "Paper";
        }
        else if (randomSeed == 3) {
            rs = "Scissors";
        }
        return rs;
    }

    public static String getCompResult()
    {
        String cr = " ";
        if (curruser == "Rock") {
            if (currcomp == "Rock") {
                cr = "We Tied!";
            }
            else if (currcomp == "Paper") {
                cr = "I Win!";
                compscore++;
            }
            else if (currcomp == "Scissors") {
                cr = "You Win!";
                userscore++;
            }
        }
        else if (curruser == "Paper") {
            if (currcomp == "Rock") {
                cr = "You Win!";
                userscore++;
            }
            else if (currcomp == "Paper") {
                cr = "We Tied!";
            }
            else if (currcomp == "Scissors") {
                cr = "I Win!";
                compscore++;
            }
        }
        else if (curruser == "Scissors") {
            if (currcomp == "Rock") {
                cr = "I Win!";
                compscore++;
            }
            else if (currcomp == "Paper") {
                cr = "You Win!";
                userscore++;
            }
            else if (currcomp == "Scissors") {
                cr = "We Tied!";
            }
        }
        return cr;
    }

    public static void setNeededVariables() {
        ppresult = presult;
        presult = result;
        pprevuser = prevuser;
        pprevcomp = prevcomp;
        prevuser = curruser;
        prevcomp = currcomp;
        currcomp = getCompMove();
    }
}
