import java.util.Scanner;

public class BattleShip {
    public static void main(String[] args) {
        
        // Title + Instructions
        System.out.println("\n\nWelcome to Battleship! \n");
        System.out.println("The '-' are white spaces where you will place your ships (total 5 ships).\n" +
                           "Once the game begins each player will have a turn to fire to the enemies board.\n" +
                           "The 'X' on the board means you Hit the enemies ship.\n" + 
                           "The 'O' on the board means you missed :(\n" + 
                           "Which ever player hits all 5 enemies ship first, WINS!\n");

        // To take users input
        Scanner input = new Scanner(System.in);

        // Player Boards for ships + history board for every hit and miss
        char player1Board[][] = {{'-', '-', '-', '-', '-'},
                                 {'-', '-', '-', '-', '-'},
                                 {'-', '-', '-', '-', '-'},
                                 {'-', '-', '-', '-', '-'},
                                 {'-', '-', '-', '-', '-'}};

        char player2Board[][] = {{'-', '-', '-', '-', '-'},
                                 {'-', '-', '-', '-', '-'},
                                 {'-', '-', '-', '-', '-'},
                                 {'-', '-', '-', '-', '-'},
                                 {'-', '-', '-', '-', '-'}};

        char player1History[][] = {{'-', '-', '-', '-', '-'},
                                   {'-', '-', '-', '-', '-'},
                                   {'-', '-', '-', '-', '-'},
                                   {'-', '-', '-', '-', '-'},
                                   {'-', '-', '-', '-', '-'}};

        char player2History[][] = {{'-', '-', '-', '-', '-'},
                                   {'-', '-', '-', '-', '-'},
                                   {'-', '-', '-', '-', '-'},
                                   {'-', '-', '-', '-', '-'},
                                   {'-', '-', '-', '-', '-'}};

        // Method for validating players input for entering ship location
        validatingPlayerInput(input, player1Board, player2Board,  player1History);

        System.out.println("LET THE BATTLE BEGIN!");

        // Method for validating players input for hit or miss
        battleBegins(input, player1Board, player2Board, player1History, player2History);

        input.close();
    }

    // validating player coordinate inputs
    public static void validatingPlayerInput(Scanner input, char[][] player1Board, char[][] player2Board, char[][] playerHistory) {

        for(int n = 1; n <= 2; n++){

            // Display coordinates of board
            System.out.print("  0 1 2 3 4");
            for(int i = 0; i < playerHistory.length; i++){
                String column = "" + i;
                for(int j = 0; j < playerHistory[i].length; j++){
                    column += " " + playerHistory[i][j];
                    if(j == 4){
                        System.out.print("\n");
                    }
                }
                System.out.print(column);
            }
            System.out.println("\n");

            System.out.println("PLAYER " + n + ", ENTER YOUR 5 SHIP'S COORDINATES.");
            System.out.println("*coordinates are read rows by columns, press num + space + num*");

            int i = 1;
            int x = 0;
            int y = 0;
            
            do{
                boolean isNumber = false;
                do{

                    System.out.println("\nEnter ship " + i + " location:");

                    if(input.hasNextInt()){
                        x = input.nextInt();
                        y = input.nextInt();
                        if(x < 5 && y < 5){
                            
                            isNumber = true;

                            if(n == 1 && player1Board[x][y] != '@'){
                                player1Board[x][y] = '@';

                                printHistoryBoard(player1Board);
                            } else if (n == 2 && player2Board[x][y] != '@'){
                                player2Board[x][y] = '@';

                                printHistoryBoard(player2Board);
                            }
                            else {
                                System.out.println("You already have a ship there. Choose different coordinates!");
                                isNumber = false;
                                input.nextLine();
                            }

                        } else {
                            System.out.println("Invalid coordinates. Choose different coordinates!");
                            isNumber = false;
                            input.nextLine();
                        }
                    } else {
                        System.out.println("Invalid Input. Please try again!");
                        isNumber = false;
                        input.next();
                    }
                }while(!isNumber);

                // once player inputs coordinates print board
                if(i == player1Board.length && n == 1){
                    printPlayerBorad(player1Board);
                }
                if(i == player2Board.length && n == 2){
                    printPlayerBorad(player2Board);
                }
            
                i += 1;
            } while (i <= player1Board.length);
        }


    }


    // validating player hits
    public static void battleBegins(Scanner input, char[][] player1Board, char[][] player2Board, char[][] player1History, char[][] player2History){

        int player1Hits = 0;
        int player2Hits = 0;
        boolean didPlayer1Hit = false;
        boolean didPlayer2Hit = false;
        boolean player1Turn = true;
        boolean player2Turn = false;
        boolean win = false;
        int playerId = 1;

        while (!win){

            if(player1Turn){

                boolean didPlayerHit = battleHitValidation(playerId, player2Board, player2History, didPlayer1Hit, input);
                printHistoryBoard(player2History);
                playerId = 2;
                player1Turn = false;
                player2Turn = true;
                didPlayer1Hit = didPlayerHit;

                if(didPlayer1Hit){
                    player1Hits += 1;
                }

                if(player1Hits == 5){
                    
                    System.out.println("\n\n\nPLAYER 1 WINS! YOU SUNK ALL OF YOUR OPPONENT'S SHIPS!");
                    System.out.println();
                    System.out.println("Player 1 board");
                    printHistoryBoard(player1History);
                    System.out.println("Player 2 board");
                    printHistoryBoard(player2History);

                    break;
                }
            }

            if(player2Turn){
                boolean didPlayerHit = battleHitValidation(playerId, player1Board, player1History, didPlayer2Hit, input);
                printHistoryBoard(player1History);
                playerId = 1;
                player1Turn = true;
                player2Turn = false;
                didPlayer2Hit = didPlayerHit;

                if(didPlayer2Hit){
                    player2Hits += 1;
                }

                if(player2Hits == 5){
                    
                    System.out.println("\n\n\nPLAYER 2 WINS! YOU SUNK ALL OF YOUR OPPONENT'S SHIPS!");
                    System.out.println();
                    System.out.println("Player 1 board");
                    printHistoryBoard(player1History);
                    System.out.println("\nPlayer 2 board");
                    printHistoryBoard(player2History);

                    break;
                }
            }
        }
    }



    public static boolean battleHitValidation(int playerId, char[][] playerBoard, char[][] playerHistory, boolean didPlayerHit, Scanner input ){

            System.out.println("\nPlayer "+ playerId +", enter hit row/column:");

            int x = 0;
            int y = 0;
            boolean isNumber = false;

            do{
                if(input.hasNextInt()){
                    x = input.nextInt();
                    y = input.nextInt();
                    if(x < 5 && y < 5){
                        isNumber = true;

                        // if player hits enemy
                        if(playerBoard[x][y] == '@'){

                            System.out.println("PLAYER " + playerId + " HIT PLAYER ENEMIE's SHIP!");

                            playerHistory[x][y] = 'X';
                            playerBoard[x][y] = 'X';

                            didPlayerHit = true;
                            
                        } else if(playerHistory[x][y] == '-') {
                            System.out.println("PLAYER " + playerId + " MISSED!");

                            playerHistory[x][y] = 'O';
                            playerBoard[x][y] = 'O';

                            didPlayerHit = false;

                        } else if(playerHistory[x][y] == 'X' || playerHistory[x][y] == 'O') {
                            System.out.println("You already fired on this spot. Choose different coordinates!");
                            isNumber = false;
                            input.nextLine();
                        }

                    } else {
                        System.out.println("Invalid coordinates. Choose different coordinates!");
                        isNumber = false;
                        input.nextLine();
                    }
                } else {
                    System.out.println("Invalid Input. Please try again!");
                    isNumber = false;
                    input.next();
                }
            }while(!isNumber);


            return didPlayerHit;

    }



    // print Enemy History board
    public static void printHistoryBoard(char[][] playerHistoryBoard){

        System.out.println("\n  0 1 2 3 4");
        for(int j = 0; j < playerHistoryBoard.length; j++){
            String col= "" + j;
            for(int k = 0; k < playerHistoryBoard[j].length; k++){
                col += " " + playerHistoryBoard[j][k];
            }
            System.out.println(col);
        }
    }


    // print board
    public static void printPlayerBorad(char[][] playerBoard){

        System.out.println("\n  0 1 2 3 4");
        for(int j = 0; j < playerBoard.length; j++){
            String col= "" + j;
            for(int k = 0; k < playerBoard[j].length; k++){
                col += " " + playerBoard[j][k];
            }
            System.out.println(col);
        }

        int i = 0;
        while(i  <= 100){
            System.out.print("\n");
            i++;
        }
    }

}
