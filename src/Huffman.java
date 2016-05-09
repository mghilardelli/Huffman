import java.io.*;
import java.time.Clock;

/**
 * Created by marcoghilardelli on 09.05.16.
 */
public class Huffman {
    public static void main(String[] args) {
        int[][] asciiTable = new int[128][2];
        int count = 0; // counter for ascii symbols

        String text = readTextfile("text.txt");


        for(int i = 0; i < asciiTable.length; ++i) {
            asciiTable[i][1] = 0;
        }
        for(int i = 0; i < text.length(); ++i) {
            asciiTable[text.charAt(i)][1]++;
            asciiTable[text.charAt(i)][0] = text.charAt(i);
            count++;
            text.charAt(i);
        }
        for(int i = 0; i < asciiTable.length; ++i) {
            System.out.println((char)asciiTable[i][0] + " " + asciiTable[i][1]);
        }
        asciiTable = sort(asciiTable);

        for(int i = 0; i < asciiTable.length; ++i) {
            System.out.println((char)asciiTable[i][0] + " " + asciiTable[i][1]);
        }



    }

    public static String readTextfile(String fileName) {
        String text = "";
        try {

            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            String temp = br.readLine();
            while(temp != null) {
                text += temp;
                temp = br.readLine();
            }
        }
        catch(IOException e) { e.getMessage();}
        return text;
    }

    public static byte[] readFile(String fileName) {
        File file = new File(fileName);

        byte[] bFile = new byte[(int) file.length()];
        try {

            FileInputStream fis = new FileInputStream(file);

            fis.read(bFile);

            fis.close();

        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return bFile;

    }
    public static int[][] sort(int[][] array) {
        int tempCnt, tempChar;
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < array.length - i; j++) {
                if (array[j][1] > array[j + 1][1]) {
                    tempCnt = array[j][1];
                    tempChar = array[j][0];
                    array[j][1] = array[j + 1][1];
                    array[j][0] = array[j + 1][0];
                    array[j + 1][1] = tempCnt;
                    array[j + 1][0] = tempChar;
                }
            }
        }
        return array;
    }
}
