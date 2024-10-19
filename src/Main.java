import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    static Boolean checkString(String givenStr, String checkStr){
        int currState = 0, state = 0;
        char[] states = givenStr.toCharArray();
        for(int i = 0; i<checkStr.length(); i++){
            if(currState == 0){
                if(checkStr.charAt(i) == states[state]){
                    System.out.println("Current State 0  to State 1" + checkStr.charAt(i) + " "+ states[state]);
                    currState++;
                    state++;
                } else {
                    System.out.println("Current State 0 to State 0"+ checkStr.charAt(i) + " "+ states[state]);
                    currState = 0;
                    state = 0;
                    if(checkStr.charAt(i) == states[0]) i--;
                    if (i + 1 >= checkStr.length()) {
                        break;
                    }
                }
            } else if(currState == 1){
                if(checkStr.charAt(i) == states[state]){
                    System.out.println("Current State 1 to State 2"+ checkStr.charAt(i) + " "+ states[state]);
                    currState++;
                    state++;
                } else {
                    System.out.println("Current State 1 to State 0"+ checkStr.charAt(i) + " "+ states[state]);
                    currState = 0;
                    state = 0;
                    if(checkStr.charAt(i) == states[0]) i--;
                    if (i + 1 >= checkStr.length()) {
                        break;
                    }
                }
            } else if(currState == 2){
                if(checkStr.charAt(i) == states[state]){
                    System.out.println("Current State 2 to State 3"+ checkStr.charAt(i) + " "+ states[state]);
                    currState++;
                    break;
                } else {
                    System.out.println("Current State 2 State 0"+ checkStr.charAt(i) + " "+ states[state]);
                    currState = 0;
                    state = 0;
                    if(checkStr.charAt(i) == states[0]) i--;
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
        String[] strs = {"ses", "ath", "hum", "que", "com", "ack", "eed", "ing", "ick", "min"};
        int[] life = {2,2};
        int currPlayer = 0;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


        do {
            String str = strs[rand.nextInt(strs.length)];
            System.out.println("Word that contains " + str);
            System.out.println("Player " + (currPlayer + 1) + ": You have 5 seconds to answer!");
            Future<String> futureInput = executor.submit(() -> sc.nextLine());
            try {
                String checkStr = futureInput.get(5, TimeUnit.SECONDS);
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
            } catch (TimeoutException e) {
                // If the player takes too long, skip their turn
                System.out.println("Time's up! Player " + (currPlayer + 1) + " loses a life.");
                life[currPlayer]--;
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
                futureInput.cancel(true);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        } while (true);

        if(life[0] == 0){
            System.out.println("Score Update\nPlayer 1: " + life[0] + "\nPlayer 2: "+ life[1]);
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("Score Update\nPlayer 1: " + life[0] + "\nPlayer 2: "+ life[1]);
            System.out.println("Player 1 wins!");
        }

        executor.shutdown();
        scheduler.shutdown();
    }
}