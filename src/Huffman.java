import java.io.*;
import java.util.PriorityQueue;

/**
 * Created by marcoghilardelli on 09.05.16.
 */
public class Huffman {
    public static class Knoten implements Comparable<Knoten> {

        public int ascii;
        public int anzahl;
        public Knoten links;
        public Knoten rechts;
        public String code;

        public Knoten(int ascii, int anzahl, Knoten links, Knoten rechts) {
            this.code = "";
            this.ascii = ascii;
            this.anzahl = anzahl;
            this.links = links;
            this.rechts = rechts;
        }

        public boolean isBlatt() {
            return this.links == null && this.rechts == null;
        }
        @Override
        public int compareTo(Knoten comp) {
            if(this.anzahl == comp.anzahl)
                return 0;
            else
            return 1;
        }
    }

    public static int[] table = new int[128];



    public static void main(String[] args) {

        initTable(table);

        String text = readTextfile("text.txt");

        fillTable(text, table);

        //sortTable(table);

        //table = deleteEmpty(table);

        //print(table);

        Knoten myTree=  buildTree(table);
        String[] codeTable = new String[128];
        buildCode(codeTable, myTree, "");

        for(int i = 0; i < codeTable.length; i++) {
            System.out.println((char)i + ":" + codeTable[i]);
        }
        //System.out.println(myTree.links.links.code);

        //System.out.println((int)myTree.ascii + " " + myTree.anzahl + ":" + (int)myTree.links.ascii + " " + myTree.links.anzahl);



    }

    public static Knoten buildTree(int[] array) {
        PriorityQueue<Knoten> tree = new PriorityQueue<>();

        // alle ascii Zeichen in den Baum einfügen
        for(int i = 0; i < array.length; i++) {
            if(array[i] != 0) {
                Knoten newKnoten = new Knoten(i, array[i], null, null);
                tree.add(newKnoten);
            }
        }

        while(tree.size() > 1) {
            Knoten links = tree.poll();
            Knoten rechts = tree.poll();
            tree.add(new Knoten(0, links.anzahl + rechts.anzahl, links, rechts));
        }
        return tree.poll();


    }

    public static void buildCode(String[] table, Knoten knoten, String code) {
        if(knoten.links != null && knoten.rechts != null) {
            buildCode(table, knoten.links, code + "0");
            buildCode(table, knoten.rechts, code + "1");
        }
        else {
            table[knoten.ascii] = code;
        }
    }

    public static void initTable(int[] array) {
        for(int i = 0; i < array.length; i++) {
            array[i] = 0;
        }

    }

    public static void print(int[][] array) {
        for(int i = 0; i < array.length; ++i) {
            System.out.println((char)array[i][0] + " " + array[i][1]);
        }
    }

    public static void fillTable(String text, int[] table) {
        for(int i = 0; i < text.length(); i++) {
            table[text.charAt(i)]++;
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
