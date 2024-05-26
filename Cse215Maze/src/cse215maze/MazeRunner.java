/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cse215maze;



import java.util.Scanner;

public class MazeRunner {
    public static Maze myMap = new Maze();
    public static int userSteps = 0;
    static AccountManager accountManager = new AccountManager();
    
    public static void main(String[] args) {
        if (!loginOrRegister()) {
            System.out.println("Failed to login or register. Exiting game.");
            return;
        }

        intro();
        while (myMap.didIWin() == false) {
            //Part 2
            String userDirection = userMove();
            //Part 3 – Watch out for pits
            if (userDirection.equals("R") || userDirection.equals("L") || userDirection.equals("U") || userDirection.equals("D"))
                navigatePit(userDirection);
        }
        if (myMap.didIWin() == true) {
            System.out.print("Congratulations, you made it out alive!");
            System.out.println("and you did it in " + MazeRunner.userSteps + " moves");
        }
    }
    
    //Intro 
    public static void intro(){
    
        System.out.println("Welcome to Maze Runner!");
        System.out.println("Here is your current position:");
        myMap.printMap();
    }
      public static boolean loginOrRegister() {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Maze Runner!");
        //System.out.println("Do you have an account? (yes/no)");
          System.out.println("1. Login ");
          System.out.println("2. Create Account ");
        String response = input.nextLine();

        if (response.equalsIgnoreCase("2")) {
            System.out.println("Creating a new account.");
            System.out.print("Enter username: ");
            String username = input.nextLine();
            System.out.print("Enter password: ");
            String password = input.nextLine();
            return accountManager.createAccount(username, password);
        } else if (response.equalsIgnoreCase("1")){
            System.out.println("Logging in.");
            System.out.print("Enter username: ");
            String username = input.nextLine();
            System.out.print("Enter password: ");
            String password = input.nextLine();
            if (accountManager.login(username, password)) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid username or password.");
                return false;
            }
        }
        else
            return false;
        
    }
      
    
    //USER MOVE
     public static String userMove(){
        Scanner input = new Scanner(System.in);
        String direction = "";
        do{
            if(MazeRunner.userSteps != 101)
            {
                System.out.print("Where would you like to move? (R, L, U, D)    ");
                direction = input.next();
            }
            

             if(direction.equals("R") || direction.equals("L") || direction.equals("U") || direction.equals("D") || direction.equals("r") ||direction.equals("l") ||direction.equals("u") ||direction.equals("d") ){
                movesMessage(++MazeRunner.userSteps);
                
              if(myMap.canIMoveRight()== true && (direction.equals("R") || direction.equals("r")))
                  myMap.moveRight();
              else if(myMap.canIMoveLeft()== true && (direction.equals("L") || direction.equals("l")) )
                  myMap.moveLeft();
              else if(myMap.canIMoveUp()== true && (direction.equals("U") || direction.equals("u")))
                  myMap.moveUp();
              else if(myMap.canIMoveDown()== true && (direction.equals("D") || direction.equals("d")))
                  myMap.moveDown();
                else if(myMap.equals("-")){
                        System.out.println("Sorry, you've hit a wall.");
                }
                else {
                    if (MazeRunner.userSteps != 101) {
                        //System.out.println("Sorry, you've hit a wall.");
                        System.out.print("Where would you like to move? (R, L, U, D)    ");
                        direction = input.next();
                                                
                    }
                }

                movesMessage(++MazeRunner.userSteps);
                        
                myMap.printMap();
                break;
            }
        } while(direction.matches("[RLUD]") == false);

        return direction;
    }
     
     
     
     //MOVE MESSAGE
     
     public static void movesMessage(int moves){
        switch (moves) {
            case 50:
                System.out.println("Warning: You have made 50 moves, you have 50 remaining before the maze exit closes");
                break;
            case 75:
                System.out.println("Alert! You have made 75 moves, you only have 25 moves left to escape.");
                break;
            case 90:
                System.out.println("DANGER! You have made 90 moves, you only have 10 moves left to escape!!");
                break;
            case 100:
                System.out.println("Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:[");
                break;
            case 101:
                System.out.println("Sorry, but you didn't escape in time- you lose!");
                System.exit(0);
                break;
            default:
                System.out.println("TOTAL MOVES: " + moves);
                break;
        }
    }
     
     
     public static void navigatePit(String userDirection) {
        Scanner input = new Scanner(System.in);
        if(myMap.isThereAPit(userDirection) == true)
        {
            System.out.print("Watch out! There's a pit ahead, jump it?  ");
            String jump = input.next();
            if(jump.equalsIgnoreCase("yes") || jump.equalsIgnoreCase("y"))
                myMap.jumpOverPit(userDirection);
            else
            {
                System.out.println("Sorry, but you didn't jump- you lose!");
                System.exit(0);
            }
        }
        else {
            //System.out.println("Sorry, you've hit a wall.");
        }
    }
    
}
