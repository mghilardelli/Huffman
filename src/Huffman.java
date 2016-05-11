import java.io.*;
import java.time.Clock;
import java.util.ArrayList;

/**
 * Created by marcoghilardelli on 09.05.16.
 */
public class Huffman {
    public static class Knoten {
        public char ascii;
        public int anzahl;
        public Knoten links;
        public Knoten rechts;

        public Knoten(char ascii, int anzahl, Knoten links, Knoten rechts) {
            this.ascii = ascii;
            this.anzahl = anzahl;
            this.links = links;
            this.rechts = rechts;

        }
    }

    public static int[][] table = new int[128][2];



    public static void main(String[] args) {

        initTable(table);

        String text = readTextfile("text.txt");

        fillTable(text, table);

        sortTable(table);

        table = deleteEmpty(table);

        print(table);

        buildTree(table);


    }

    public static void buildTree(int[][] array) {
        ArrayList<Knoten> tree = new ArrayList<>();

        // erster Knoten erstellen
        Knoten ersterKnoten = new Knoten((char)array[0][0], array[0][1], null, null);
        Knoten zweiterKnoten = new Knoten((char)array[1][0], array[1][1], null, null);
        // dem Baum hinzufügen
        tree.add(new Knoten((char)array[2][0], array[2][1], ersterKnoten, zweiterKnoten));

        for(int i = 2; i < table.length; i++) {
            tree.add(new Knoten((char)array[i][0], array[i][1], null, null));
        }
    }

    public static void initTable(int[][] array) {
        for(int i = 0; i < array.length; i++) {
            array[i][0] = i;
            array[i][1] = 0;
        }

    }

    public static void print(int[][] array) {
        for(int i = 0; i < array.length; ++i) {
            System.out.println((char)array[i][0] + " " + array[i][1]);
        }
    }

    public static void fillTable(String text, int[][] table) {
        for(int i = 0; i < text.length(); i++) {
            table[text.charAt(i)][1]++;
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
    public static void sortTable(int[][] array) {
        int[] temp = new int[2];
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < array.length - i; j++) {
                if (array[j][1] > array[j+1][1]) {
                    temp[0] = array[j][0];
                    temp[1] = array[j][1];
                    array[j][0] = array[j+1][0];
                    array[j][1] = array[j+1][1];
                    array[j+1][0] = temp[0];
                    array[j+1][1] = temp[1];
                }
            }
        }
    }

    public static int[][] deleteEmpty(int[][] array) {
        int firstFilled = -1, countFilled;
        int[][] temp;
        for(int i = 0; i < array.length; i++) {
            if(array[i][1] != 0 && firstFilled ==  -1) {
                firstFilled = i;
            }
        }
        countFilled = array.length - firstFilled; // oder weniger/mehr
        temp = new int[countFilled][2];

        for(int i = 0; i < temp.length; i++) {
            temp[i][0] = array[firstFilled][0];
            temp[i][1] = array[firstFilled++][1];
        }
        return temp;
    }
}
