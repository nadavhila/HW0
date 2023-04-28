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
     * @return boolean flag in order to understand if the the cordinations are ok
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
     * @param flag_Extra
     * @param x
     * @param y
     * @param n
     * @param m
     * @return
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
    public static boolean good_Attack_Point(boolean flag_Extra, int x, int y, int n, int m){
        if (!((0 < x && x <= n ) && (0 < y && y <= m))) {//הלכנו על דרך השלילה
            if (flag_Extra) {
                System.out.println("Illegal tile, try again!");
            }
            return true;
        }
        return false;
    }
    public static boolean check_Board_Limits_Ship(boolean flag, boolean flag_Extra, int x, int y
            , int size, int n, int m, int orient) {//   בדיקת חריגה מספר  3
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

    public static boolean adjacent_Helper(String[][] matrix, int x,int y,int n,int m){
        if((0<y&&y<m) && (0<x&& x<n)){
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
    public static boolean check_Adjacent(boolean flag,boolean flag_Extra, String[][] matrix, int x, int y
            , int n, int m, int size, int orient) {//בדיקה מספר 5  סמיכות לגבולות ולאזורים אסורים** לא שלם- המשך של נדב -במידה ואתה משתמשש בטמפלט שלי- לשים לב שזה מערך של סטרינגים ולא צ'ארים ולכן צריך להשתמש בפונקציה equals()
        if (flag)
            return true;
        if (orient == 0) {//בדיקה במידה והצוללת אופקית
            for (int j = 0; j < size; j++, y++){
                flag = adjacent_Helper(matrix, x, y, n, m);/// עבר את הבדיקה
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
            for (int i = 0; i<size; i++,x++) {// בדיקה במידה והצוללת אנכית
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

    public static int remainingShips(int[] hist) {////חישוב כמות הספינות שנשארו
        int sum = 0;
        int hist_len = hist.length;
        for (int k = 0; k < hist_len; k++) {//
            sum += hist[k];
        }
        return sum;
    }

    public static void drowned_Message(int r, boolean flag) {//////פונקציית עזר להדפסה במקרה שהוטבעה ספינה
        if (flag)//אנחנו תוקפים את המחשב(היוזר תוקף)
            System.out.println("The computer's battleship has been drowned, " + r + " more battleships to go!");
        else////המחשב תוקף אותנו(המחשב תוקף)
            System.out.println("Your battleship has been drowned, you have left " + r + " more battleships!");
    }


    public static void check_If_Drowned(String[][] matrix, int n, int m, int x, int y, int[] hist, String symbol, boolean flag) {
        int counter_Hits = 1;
        for (int i = 1; (y - i) > 0; i++) {////// מעבר שמאלה
            if (matrix[x][y - i].equals("#"))
                return;
            if (matrix[x][y - i].equals("–") || matrix[x][y - i].equals(symbol))
                break;
            counter_Hits++;
        }
        for (int j = 1; (y + j) <= m ; j++) {////מעבר ימינה
            if (matrix[x][y + j].equals("#"))
                return;
            if (matrix[x][y + j].equals("–") || matrix[x][y + j].equals(symbol))
                break;
            counter_Hits++;
        }
        for (int k = 1; (x - k) > 0; k++) {/////בדיקה למעלה
            if (matrix[x - k][y].equals("#"))
                return;
            if (matrix[x - k][y].equals("–") || matrix[x - k][y].equals(symbol))
                break;
            counter_Hits++;
        }
        for (int l = 1; (x + l) <= n; l++) {///בדיקה למטה
            if (matrix[x + l][y].equals("#"))
                return;
            if (matrix[x + l][y].equals("–") || matrix[x + l][y].equals(symbol))
                break;
            counter_Hits++;
        }
        hist[counter_Hits]--;
        int r = remainingShips(hist);//מעבירים את כמות הספינות הנוכחית
        drowned_Message(r, flag);//הדפסה של משפט לפי מי תקף את מי
    }

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


    public static void split_X(String str, int[] array, int counter) {
        String[] S1 = str.split("X");
        int n = Integer.parseInt(S1[0]);
        int m = Integer.parseInt(S1[1]);
        array[counter++] = n;
        array[counter] = m;
    }

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

    public static int sum_Digits(int n){
        int counter = 0;
        if(n==0)
            return 1;
        while(n!=0){
            n /= 10;
            counter++;
        }
        return counter;
    }
    public static void hist_Making(int[] hist_Original,int[]hist_Copy){
        for (int i = 0; i < hist_Original.length; i++)
            hist_Copy[i] = hist_Original[i];
    }
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
        //קליטת הקלט ויצירת מטריצה
        System.out.println("Enter the board size");
        String str = scanner.nextLine();
        String[] S1 = str.split("X");
        int n = Integer.parseInt(S1[0]);
        int m = Integer.parseInt(S1[1]);
        String[][] matrix_User = matrix(n, m);
        String[][] matrix_Computer = matrix(n, m);
        String[][] matrix_Gussing_User = matrix(n, m);
        String[][] matrix_Gussing_Computer = matrix(n, m);
        //*****קליטת הצוללות צריך לעטוף בפונקציה
        System.out.println("Enter the battleships sizes");
        String zoollelot = scanner.nextLine();//קליטת צוללות
        String[] shlav1 = zoollelot.split(" ");
        int len = shlav1.length;
        int[] arr1 = new int[len * 2];//מערך עזר בינתיים...
        int counter = 0;
        for (int i = 0; i < len; i++) {
            split_X(shlav1[i], arr1, counter);
            counter += 2;
        }
        int hist_Len = arr1[(len * 2) - 1] + 1;//אורך ההיסטוגרמה
        int[] hist_Original = new int[hist_Len];//יצירת ההיסטוגרמה
        for (int j = 0; j + 1 < len * 2; j++)//גודל מערך העזר
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
                    flag_User = check_Adjacent(flag_User,flag_Extra, matrix_User, x, y,n,m,size, orient);
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
                    flag_Computer = check_Adjacent(flag_Computer, flag_Extra, matrix_Computer, xc, yc,n,m, size, orient_C);
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
