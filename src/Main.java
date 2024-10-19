import java.util.Random;
import java.util.Scanner;

public class Main {

    static Boolean checkString(String givenStr, String checkStr){
        int currState = 0, state = 0;
        char[] states = givenStr.toCharArray();
        for(int i = 0; i<checkStr.length(); i++){
            if(currState == 0){
                if(checkStr.charAt(i) == states[state]){
                    currState++;
                    state++;
                } else {
                    state = 0;
                    if (i + 1 >= checkStr.length()) {
                        break;
                    }
                }
            } else if(currState == 1){
                if(checkStr.charAt(i) == states[state]){
                    currState++;
                    state++;
                } else {
                    currState = 0;
                    state = 0;
                    if (i + 1 >= checkStr.length()) {
                        break;
                    }
                }
            } else if(currState == 2){
                if(checkStr.charAt(i) == states[state]){
                    currState++;
                    break;
                } else {
                    currState = 0;
                    state = 0;
                    if (i + 1 >= checkStr.length()) {
                        break;
                    }
                }
            }
        }
        return currState == 3;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        Boolean answer;
        Scanner sc = new Scanner(System.in);
        String[] strs = {"ses", "ath", "hum", "que", "com"};
        int[] life = {2,2};
        int currPlayer = 0;
        do {
            String str = strs[rand.nextInt(strs.length)];
            System.out.println("Word that contains " + str);
            System.out.println("Player " + (currPlayer + 1) + ": ");
            String checkStr = sc.nextLine();
            answer = checkString(str, checkStr);
            if (answer) {
                if (currPlayer == 1) {
                    currPlayer = 0;
                    System.out.println("Score Update\nPlayer 1: " + life[0] + "\nPlayer 2: "+ life[1]);
                    System.out.println("Player " + (currPlayer + 1) + "'s turn ");
                } else {
                    currPlayer = 1;
                    System.out.println("Score Update\nPlayer 1: " + life[0] + "\nPlayer 2: "+ life[1]);
                    System.out.println("Player " + (currPlayer + 1) + "'s turn ");
                }
            } else {
                life[currPlayer] = life[currPlayer] - 1;
                if(life[currPlayer] != 0){
                    if (currPlayer == 1) {
                        currPlayer = 0;
                        System.out.println("Score Update\nPlayer 1: " + life[0] + "\nPlayer 2: "+ life[1]);
                        System.out.println("Player " + (currPlayer + 1) + "'s turn ");
                    } else {
                        currPlayer = 1;
                        System.out.println("Score Update\nPlayer 1: " + life[0] + "\nPlayer 2: "+ life[1]);
                        System.out.println("Player " + (currPlayer + 1) + "'s turn ");
                    }
                } else {
                    break;
                }
            }
        } while (true);

        if(life[0] == 0){
            System.out.println("Score Update\nPlayer 1: " + life[0] + "\nPlayer 2: "+ life[1]);
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("Score Update\nPlayer 1: " + life[0] + "\nPlayer 2: "+ life[1]);
            System.out.println("Player 1 wins!");
        }
    }
}