import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static Scanner scanner;
    public static Random rnd;
    /**
     * orientation check
     * @param flag_Extra is to recognize who is calling the method: the user or the computer
     * @param orient is the orientation
     * @return boolean flag in order to understand if the cordinations are ok
     */
    public static boolean check_Orient(boolean flag_Extra, int orient) {
        if (!(orient == 0 || orient == 1)) {
            if (flag_Extra) {
                System.out.println("Illegal orientation, try again!");
            }
            return true;
        }
        return false;
    }
    /**
     * check if the user/computer enters cordinations that out of borders
     * @param flag checks if the cordinations were failed in previous methods
     * @param flag_Extra is to recognize who is calling the method: the user or the computer
     * @param x is the x cordination
     * @param y is the y cordination
     * @param n is the number of raws
     * @param m is the number of cols
     * @return boolean flag in order to understand if the cordinations are ok
     */

    public static boolean check_Board_Limits(boolean flag, boolean flag_Extra, int x, int y, int n, int m) {//בדיקת חריגת נקודה מהלוח בדיקה מספר 2
        if (flag)
            return true;
        else if (!((0< x && x <=n) && (0< y && y <=m))) {//הלכנו על דרך השלילה
            if (flag_Extra) {
                System.out.println("Illegal tile, try again!");
            }
            return true;
        }
        return false;
    }
    /**
     * checks if the attack is valid and in the range of the board
     * @param flag_Extra checks if the cordinations were failed in previous methods
     * @param x is the x cordination
     * @param y is the y cordination
     * @param n is the number of raws
     * @param m is the number of cols
     * @return boolean flag in order to understand if the cordinations are ok
     */
     public static boolean good_Attack_Point(boolean flag_Extra, int x, int y, int n, int m){
     if (!((0 < x && x <= n ) && (0 < y && y <= m))) {
     if (flag_Extra) {
     System.out.println("Illegal tile, try again!");
     }
     return true;
     }
     return false;
     }
     /**
     *check if the whole ship is in the board
     * @param flag checks if the cordinations were failed in previous methods
     * @param flag_Extra checks if the cordinations were failed in previous methods
     * @param x is the x cordination
     * @param y is the y cordination
     * @param size is the size of the current ship
     * @param n is the number of raws
     * @param m is the number of cols
     * @param orient is the orientation
     * @return boolean flag in order to understand if the cordinations are ok
     */
     public static boolean check_Board_Limits_Ship(boolean flag, boolean flag_Extra, int x, int y
            , int size, int n, int m, int orient) {
        if (flag)
            return true;
        if (orient == 0) {
            if (((y + size - 1) > m)) {
                if (flag_Extra) {
                    System.out.println("Battleship exceeds the boundaries of the board, try again!");
                }
                return true;
            }
            return false;
        } else {
            if ((x + size - 1) > n) {
                if (flag_Extra) {
                    System.out.println("Battleship exceeds the boundaries of the board, try again!");
                }
                return true;
            }
            return false;
        }
    }
    /**
     * check if the current ship overlaps with the previous ships
     * @param flag checks if the cordinations were failed in previous methods
     * @param flag_Extra checks if the cordinations were failed in previous methods
     * @param matrix the board of the user/computer
     * @param x is the x cordination
     * @param y is the y cordination
     * @param size is the size of the current ship
     * @param orient is the orientation
     * @return boolean flag in order to understand if the cordinations are ok
     */
    public static boolean check_Overlaps(boolean flag, boolean flag_Extra, String[][] matrix
            , int x, int y, int size, int orient) {// בדיקה מספר 4 חריגה מהלוח
        if (flag)
            return true;
        if(orient == 0) {
            for (int j = y; j < y + size; j++) {
                if (!((matrix[x][j]).equals("–"))) {
                    if (flag_Extra) {
                        System.out.println("Battleship overlaps another battleship, try again!");
                    }
                    return true;
                }
                return false;
            }
        }else{
            for (int i = x; i < x + size; i++) {
                if (!((matrix[i][y]).equals("–"))) {
                    if (flag_Extra) {
                        System.out.println("Battleship overlaps another battleship, try again!");
                    }
                    return true;
                }
                return false;
            }
        }
        return flag;
    }

    /**
     * a method meant to ease the adjacent method. it checks the enviorment of each point in the ship in order to
     * know if there is another ship adjacent to her
     * @param matrix the user/computer board
     * @param x is the x cordination
     * @param y is the y cordination
     * @param n is the number of raws
     * @param m is the number of cols
     * @return boolean flag in order to understand if the cordinations are ok
     */
    public static boolean adjacent_Helper(String[][] matrix, int x,int y,int n,int m){
        if((0<y && y<m) && (0<x && x<n)){
            if(!(matrix[x+1][y+1].equals("#"))&&
                    !(matrix[x+1][y-1].equals("#"))&&
                    !(matrix[x-1][y+1].equals("#"))&&
                    !(matrix[x-1][y-1].equals("#"))&&
                    !(matrix[x-1][y].equals("#"))&&
                    !(matrix[x+1][y].equals("#"))&&
                    !(matrix[x][y-1].equals("#"))&&
                    !(matrix[x][y+1].equals("#")))
                return false;
        }
        else if(y==m && 0<x && x<n){
            if(!(matrix[x+1][y-1].equals("#"))&&
                    !(matrix[x-1][y-1].equals("#"))&&
                    !(matrix[x-1][y].equals("#"))&&
                    !(matrix[x+1][y].equals("#"))&&
                    !(matrix[x][y-1].equals("#")))
                return false;
        }
        else if(x==n && 0<y && y<m){
            if(!(matrix[x-1][y+1].equals("#"))&&
                    !(matrix[x-1][y-1].equals("#"))&&
                    !(matrix[x-1][y].equals("#"))&&
                    !(matrix[x][y-1].equals("#"))&&
                    !(matrix[x][y+1].equals("#")))
                return false;
        }
        else if(x==n && y==m){
            if(!(matrix[x-1][y-1].equals("#"))&&
                    !(matrix[x-1][y].equals("#"))&&
                    !(matrix[x][y-1].equals("#")))
                return false;
        }
        return true;
    }
    /**
     * it manages the adjacent_Helper
     * @param flag checks if the cordinations were failed in previous methods
     * @param flag_Extra checks if the cordinations were failed in previous methods
     * @param matrix the user/computer board
     * @param x is the x cordination
     * @param y is the y cordination
     * @param n is the number of raws
     * @param m is the number of cols
     * @param size is the size of the current ship
     * @param orient is the orientation
     * @return boolean flag in order to understand if the cordinations are ok
     */
    public static boolean check_Adjacent(boolean flag,boolean flag_Extra, String[][] matrix, int x, int y
            , int n, int m, int size, int orient) {//בדיקה מספר 5  סמיכות לגבולות ולאזורים אסורים** לא שלם- המשך של נדב -במידה ואתה משתמשש בטמפלט שלי- לשים לב שזה מערך של סטרינגים ולא צ'ארים ולכן צריך להשתמש בפונקציה equals()
        if (flag)
            return true;
        if (orient == 0) {//horizonal
            for (int j = 0; j < size; j++, y++){
                flag = adjacent_Helper(matrix, x, y, n, m);//if it is ok
                if (flag) {
                   if (flag_Extra) {
                    System.out.println("Adjacent battleship detected, try again!");
                    return true;
                    }
                    return true;
                }
            }
        }
        else{
            for (int i = 0; i<size; i++,x++) {// orien==1
                flag=adjacent_Helper(matrix,x,y,n,m);;
                if (flag) {
                    if (flag_Extra) {
                        System.out.println("Adjacent battleship detected, try again!");
                        return true;
                    }
                    return true;
                }
            }
        }
        return flag;
    }

    /**
     * checks how much ships remains on the board for the computer/user
     * @param hist the histogram that holds the size and the number of ships for each size(the index is the size)
     * @return the number of ships that remains
     */
    public static int remainingShips(int[] hist) {
        int sum = 0;
        int hist_len = hist.length;
        for (int k = 0; k < hist_len; k++) {
            sum += hist[k];
        }
        return sum;
    }
    /**
     * it is a method that checks who sent the parameters to check_If_Drowned and print message that depends on it
     * @param remain how much ships remain on the board
     * @param flag who sent the parameters(computer/user)
     */
    public static void drowned_Message(int remain, boolean flag) {
        if (flag)
            System.out.println("The computer's battleship has been drowned, " + remain + " more battleships to go!");
        else
            System.out.println("Your battleship has been drowned, you have left " + remain + " more battleships!");
    }
    /**
     * checks  how much ships were left in the board for the computer/user, after sinking a ship. it uses the
     * drowned_Message method to do this and the remainingShips method
     * @param matrix the user/computer board
     * @param n is the number of raws
     * @param m is the number of cols
     * @param x is the x cordination
     * @param y is the y cordination
     * @param hist the histogram that holds the size and the number of ships for each size(the index is the size)
     * @param symbol it is meant to work with either the computer or user. it search for "X" for the user(and the computer
     *               doesn't need it (so it gets:"")).
     * @param flag who sent the parameters(computer/user). it delivers it to the drowned_Message method
     */
    public static void check_If_Drowned(String[][] matrix, int n, int m, int x, int y, int[] hist, String symbol, boolean flag) {
        int counter_Hits = 1;
        for (int i = 1; (y - i) > 0; i++) {//checks for the left
            if (matrix[x][y - i].equals("#"))
                return;
            if (matrix[x][y - i].equals("–") || matrix[x][y - i].equals(symbol))
                break;
            counter_Hits++;
        }
        for (int j = 1; (y + j) <= m ; j++) {//checks for the right
            if (matrix[x][y + j].equals("#"))
                return;
            if (matrix[x][y + j].equals("–") || matrix[x][y + j].equals(symbol))
                break;
            counter_Hits++;
        }
        for (int k = 1; (x - k) > 0; k++) {//checks up
            if (matrix[x - k][y].equals("#"))
                return;
            if (matrix[x - k][y].equals("–") || matrix[x - k][y].equals(symbol))
                break;
            counter_Hits++;
        }
        for (int l = 1; (x + l) <= n; l++) {//checks down
            if (matrix[x + l][y].equals("#"))
                return;
            if (matrix[x + l][y].equals("–") || matrix[x + l][y].equals(symbol))
                break;
            counter_Hits++;
        }
        hist[counter_Hits]--;
        int remain = remainingShips(hist);//how much ships were left
        drowned_Message(remain, flag);//who attacked
    }

    /**
     * prints the board of the game/user/computer/guessing
     * @param matrix the board of the game/user/computer/guessing
     */
    public static void print_Board(String[][] matrix) {
        int num_rows = matrix.length;
        int num_cols = matrix[0].length;
        for (int i = 0; i < num_rows; i++) {
            System.out.print(matrix[i][0]);
            for (int j = 1; j < num_cols-1; j++){
            System.out.print(" " + matrix[i][j]);
        }
            System.out.println(" "+matrix[i][num_cols-1]);
        }
        System.out.println();
    }

    /**
     * it seprates the numbers that we get from the user with the char "X". it helps to create the histograms
     * @param str string from the user
     * @param array is array that helps to create the histogram
     * @param counter count the index of the next item in the array/hist
     */
    public static void split_X(String str, int[] array, int counter) {
        String[] S1 = str.split("X");
        int n = Integer.parseInt(S1[0]);
        int m = Integer.parseInt(S1[1]);
        array[counter++] = n;
        array[counter] = m;
    }

    /**
     * change the relevent board during the game
     * @param hist the array that holds the number of ships and their sizes
     * @param matrix the computer/user board
     * @param orient the orientation
     * @param x the x cordination
     * @param y the y cordination
     * @param size is the size of the current ship
     */
    public static void change_Board(int[] hist, String[][] matrix, int orient, int x, int y, int size) {
        if (orient == 0) {
            for (int j = 0; j < size; j++) {
                matrix[x][y + j] = "#";
            }
        }
        if (orient == 1) {
            for (int i = 0; i < size; i++) {
                matrix[x + i][y] = "#";
            }
        }
    }

    /**
     * it helps the matrix method to index raws and cols.it counts the number of digits of the given number
     * @param number number of raw/cols
     * @return number of digits
     */
    public static int sum_Digits(int number){
        int counter = 0;
        if(number ==0)
            return 1;
        while(number !=0){
            number /= 10;
            counter++;
        }
        return counter;
    }

    /**
     * copies the original histogram. creates histograms for the user and computer.
     * @param hist_Original the original histogram
     * @param hist_Copy the copy
     */
    public static void hist_Making(int[] hist_Original,int[]hist_Copy){
        for (int i = 0; i < hist_Original.length; i++)
            hist_Copy[i] = hist_Original[i];
    }

    /**
     * creates boards for the user/computer
     * @param n is the number of raws
     * @param m is the number of cols
     * @return the board
     */
    public static String[][] matrix(int n, int m) {
        String[][] matrix = new String[n + 1][m + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++)
                matrix[i][j] = "–";
        }
        int num_Spaces = sum_Digits(n);
        matrix[0][0] = "";
        for (int k = 0; k < num_Spaces; k++)
            matrix[0][0]+=" ";
        for (int j = 0; j + 1 < m + 1; j++) {
            matrix[0][j + 1] = j + "";
        }
        for (int i = 0; i + 1 < n + 1; i++) {
            int sum_Spaces =num_Spaces-sum_Digits(i);
            matrix[i+1][0]= "";
            for (int l = 0; l < sum_Spaces; l++)
                matrix[i+1][0]+= " ";
            matrix[i + 1][0]+= i + "";
        }
        return matrix;
    }

    public static void battleshipGame () {
        /** gets form the user the number of raws and cols*/
        System.out.println("Enter the board size");
        String str = scanner.nextLine();
        String[] S1 = str.split("X");
        int n = Integer.parseInt(S1[0]);
        int m = Integer.parseInt(S1[1]);
        String[][] matrix_User = matrix(n, m);
        String[][] matrix_Computer = matrix(n, m);
        String[][] matrix_Gussing_User = matrix(n, m);
        String[][] matrix_Gussing_Computer = matrix(n, m);
        /** gets form the user the battleships sizes*/
        System.out.println("Enter the battleships sizes");
        String zoollelot = scanner.nextLine();//
        String[] shlav1 = zoollelot.split(" ");
        int len = shlav1.length;
        int[] arr1 = new int[len * 2];//uses another array
        int counter = 0;
        for (int i = 0; i < len; i++) {
            split_X(shlav1[i], arr1, counter);
            counter += 2;
        }
        int hist_Len = arr1[(len * 2) - 1] + 1;//histogram length
        int[] hist_Original = new int[hist_Len];//making the histogram
        for (int j = 0; j + 1 < len * 2; j++)//the array length
            hist_Original[arr1[j + 1]] = arr1[j++];


        //זהו לגבי קליטת הצוללות
        /////////////////////////////////////////////////////////////////////*******
        int[] hist_User = new int[hist_Original.length];
        hist_Making(hist_Original,hist_User);
        int[] hist_User_Copy = new int[hist_Original.length];
        hist_Making(hist_Original,hist_User_Copy);
        int[] hist_Computer = new int[hist_Original.length];
        hist_Making(hist_Original,hist_Computer);
        int[] hist_Computer_Copy = new int[hist_Original.length];
        hist_Making(hist_Original,hist_Computer_Copy);

        /////            פונקציית בדיקות+הצבות ליוזר
        boolean flag_Extra;/////////הצהרה על פלגים
        boolean flag_Computer;
        boolean flag_User;
        boolean good_Point;
        boolean computer_Attack;
        boolean user_Attack;
        boolean good_Attack;
        System.out.println("Your current game board:");
        print_Board(matrix_Gussing_User);
        for (int size = 0; size < hist_User.length; size++) {
            while (hist_User_Copy[size] != 0) {
                System.out.println("Enter location and orientation for battleship of size " + size);
                flag_User = true;
                flag_Extra = true;
                int x = 0, y = 0, orient = 0;
                while (flag_User) {
                    String str_2 = scanner.nextLine();//קבלת עוד מחוזרת שפינקו אותנו בה במקום קלט רגיל ונורמלי
                    String[] S2 = str_2.split(", ");///// יש לשקול להפוך לפונקציה נפרדת שמפרידה לפי פסיק
                    x = Integer.parseInt(S2[0])+1;
                    y = Integer.parseInt(S2[1])+1;
                    orient = Integer.parseInt(S2[2]);
                    flag_User = check_Orient(flag_Extra, orient);
                    flag_User = check_Board_Limits(flag_User, flag_Extra, x, y, n, m);
                    flag_User = check_Board_Limits_Ship(flag_User, flag_Extra,x, y,size, n, m, orient);
                    flag_User = check_Overlaps(flag_User, flag_Extra, matrix_User, x, y, size, orient);
                    flag_User = check_Adjacent(flag_User,flag_Extra, matrix_User, x, y, n,m,size, orient);
                }
                change_Board(hist_User_Copy, matrix_User, orient, x, y, size);
                hist_User_Copy[size]--;
                System.out.println("Your current game board:");
                print_Board(matrix_User);
            }
        }/////// קליטת קלט למחשב+בדיקות+הדפסה למחשב
        for (int size = 0; size < hist_Computer.length; size++) {
            while (hist_Computer_Copy[size] != 0) {
                int xc = 0, yc = 0, orient_C = 0;
                flag_Computer = true;
                flag_Extra = false;
                while (flag_Computer) {
                    xc = rnd.nextInt(n)+1;
                    yc = rnd.nextInt(m)+1;
                    orient_C = rnd.nextInt(2);
                    flag_Computer = check_Orient(flag_Computer, orient_C);
                    flag_Computer = check_Board_Limits(flag_Computer, flag_Extra, xc, yc, n, m);
                    flag_Computer = check_Board_Limits_Ship(flag_Computer, flag_Extra, xc, yc,size, n, m, orient_C);
                    flag_Computer = check_Overlaps(flag_Computer, flag_Extra, matrix_Computer, xc, yc, size, orient_C);
                    flag_Computer = check_Adjacent(flag_Computer, flag_Extra, matrix_Computer, xc, yc, n,m, size, orient_C);
                }
                change_Board(hist_Computer_Copy, matrix_Computer, orient_C, xc, yc, size);
                hist_Computer_Copy[size]--;
            }
        }



/////////////////////////////////////////////////////////////////////////////attacks!
        int r_c = remainingShips(hist_Computer);
        int r_u = remainingShips(hist_User);
        while (r_c>0 && r_u>0) {
            System.out.println("Your current guessing board:");
            print_Board(matrix_Gussing_User);
            System.out.println("Enter a tile to attack");
            good_Point = true;
            flag_Extra = true;/////בן אדם שלח את זה ולא מחשב
            int x = 0, y = 0;
            while (good_Point) {// !!!בדיקת קלט של התקפה
                String str_3 = scanner.nextLine();
                String[] S3 = str_3.split(", ");
                x = Integer.parseInt(S3[0])+1;
                y = Integer.parseInt(S3[1])+1;
                good_Point=good_Attack_Point(flag_Extra, x, y, n, m);
                if (!good_Point) {
                    String cordinations = matrix_Computer[x][y];
                    if (cordinations.equals("X")|| cordinations.equals("V")) {
                        System.out.println("Tile already attacked, try again!");
                        good_Point = true;
                    }
                }
            }
            computer_Attack = false;
            user_Attack = true;
            /////// ההתקפות/////////////////////////////////////////////////////////////
            if (matrix_Computer[x][y].equals("–")) {
                System.out.println("That is a miss!");
                matrix_Computer[x][y] = "X";
                matrix_Gussing_User[x][y] = "X";
            }
            if (matrix_Computer[x][y].equals("#")) {
                System.out.println("That is a hit!");
                matrix_Gussing_User[x][y] = "V";
                matrix_Computer[x][y] = "V";
                check_If_Drowned(matrix_Computer, n, m, x, y, hist_Computer, "X", user_Attack);
            }
            r_c = remainingShips(hist_Computer);
            if (r_c == 0) {
                System.out.println("You won the game!");
                return;
            }
            good_Attack = true;///////משתנה בוליאני-פלאג להתקפות המחשב
            flag_Extra= false;
            while (good_Attack) {// !!!בדיקת קלט של התקפה
                x = rnd.nextInt(n)+1;
                y = rnd.nextInt(m)+1;
                good_Attack = good_Attack_Point(flag_Extra, x, y, n, m);
                if (!good_Attack) {
                    String cordinations = matrix_Gussing_Computer[x][y];
                    if (cordinations.equals("X") || cordinations.equals("V")) {
                        good_Attack = true;
                    }
                }
            }
            //----------------------------------------------computer attacks
            System.out.println("The computer attacked" + " (" + (x-1) + ", " + (y-1) + ")");
            if (matrix_User[x][y].equals("–")) {
                System.out.println("That is a miss!");
                matrix_Gussing_Computer[x][y] = "X";
                System.out.println("Your current game board:");
                print_Board(matrix_User);
            }
            if (matrix_User[x][y].equals("#")) {
                System.out.println("That is a hit!");
                matrix_User[x][y] = "X";
                matrix_Gussing_Computer[x][y] = "V";
                check_If_Drowned(matrix_User, n, m, x, y, hist_User, "", computer_Attack);
                System.out.println("Your current game board:");
                print_Board(matrix_User);
            }
            r_u = remainingShips(hist_User);
            if (r_u == 0) {
                System.out.println("You lost ):");
                return;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Total of " + numberOfGames + " games.");

        for (int i = 1; i <= numberOfGames; i++) {
            scanner.nextLine();
            int seed = scanner.nextInt();
            rnd = new Random(seed);
            scanner.nextLine();
            System.out.println("Game number " + i + " starts.");
            battleshipGame();
            System.out.println("Game number " + i + " is over.");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("All games are over.");
    }
}
