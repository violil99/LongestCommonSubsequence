
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class LCS{

    //Reads a file. returns an empty string if the file is not found
    public static String readFile(String filename){
        String fileString = "";
        try{
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                fileString+=scanner.nextLine();
            }
            scanner.close();
        }catch(FileNotFoundException e){
        }

        return fileString;
    }

    //removes whitespace and sets all characters to lower case
    public static String cleanString(String string){
        string = string.toLowerCase();
        string = string.replaceAll("\\p{Punct}", "");
        string = string.replace(" ","");
        return string;
    }

    //makes a matrix which finds the LCS at every point in the two strings, 
    //and maintains a count of the LCS in a diagonal (not necessarily straight)
    //along the matrix. Following this diagonal will give the indices of 
    //each character in the LCS
    public static int[][] makeLCSMatrix(String string1, String string2){
        int m = string1.length();
        int n = string2.length();
        int[][] matrix = new int[m+1][n+1];

        //filling first row and first column with 0s
        for (int i=0; i<=m; i++){
            matrix[i][0]=0;
        }
        for (int i=0; i<=n; i++){
            matrix[0][i]=0;
        }

        //big loop iterates rows
        for(int i=1; i<=m;i++){
            //inner loop iterates columns
            for(int j=1; j<=n; j++){

                //if there is a match, set the square to the square up and to the left +1
                if(string1.charAt(i-1)==string2.charAt(j-1)){
                    matrix[i][j]=1+matrix[i-1][j-1];
                }else{

                    //if there isn't a match, set the square to the max of the two squares beside it
                    matrix[i][j] = Math.max(matrix[i-1][j],matrix[i][j-1]);
                }
            }
        }
        //print2D(matrix);
        return matrix;
    }


    //Using the LCS matrix, this iterates backwards to find each character in the LCS
    //The string returned will be backwards
    public static String LCS_To_String(int[][] matrix, String s, int i, int j){
        String toReturn = "";

        //When i or j == 0, there are no more matches
        while (i!=0 && j!=0){

            //if the diagonal up/left is the same as the current square
            //the matches happened earlier
            if(matrix[i][j]==matrix[i-1][j-1]){
                i-=1;
                j-=1;
                
            }else{

                //if there is a matching square up or to the left, the change happened earlier
                if(matrix[i][j]==matrix[i][j-1] || matrix[i][j]==matrix[i-1][j]){

                    //matching square to the left
                    if(matrix[i][j]==matrix[i][j-1]){
                        j-=1;
                    
                    //matching square to the right
                    }else{
                        i-=1;
                    }

                //no matching squares beside or diagonal up/left from the current square. 
                //This is where the match happened
                }else{
                    i-=1;
                    j-=1;

                    //matrix index is always string index+1 (because of padded 0s)
                    //add the character 
                    toReturn+=s.charAt(i);
                }
            }
        }
        return toReturn;
    }

    public static void printLCS(String s1, String s2){

        //making a matrix
        int[][] matrix = makeLCSMatrix(s1,s2);

        //extracting the backwards LCS from the matrix
        String s = LCS_To_String(matrix, s1, s1.length(), s2.length());

        System.out.println("\nLCS: ");

        //reverse printing the string
        for (int i=s.length();i>0;i--){
            System.out.print(s.charAt(i-1));
        }
        System.out.println();
    }

    //This prints a 2D matrix, with one row per line
    //Not actively used in this program, but helpful for visualization
    public static void print2D(int mat[][])
    {
        for (int i = 0; i<mat.length;i++){
            System.out.println();
            for (int j = 0; j<mat[i].length;j++){
                System.out.print(mat[i][j]+", ");
            }
        }
    }
        

    public static void main(String[] args){

        //turning two input files into strings, removing their
        //whitespaces and punctuation, and setting all characters to lower case
        String fileString1 = readFile("input1.txt");
        fileString1 = cleanString(fileString1); 

        String fileString2 = readFile("input2.txt");
        fileString2 = cleanString(fileString2); 

        //Printing the LCS of the two strings/files
        printLCS(fileString1, fileString2);

    }
}
