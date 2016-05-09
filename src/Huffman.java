import java.io.*;
import java.time.Clock;

/**
 * Created by marcoghilardelli on 09.05.16.
 */
public class Huffman {
    public class Node {
        public char character;
        public int count;
        public Node left;
        public Node right;

        public Node(char character, int count, Node left, Node right) {
            this.character = character;
            this.count = count;
            this.left = left;
            this.right = right;
        }
    }

    public static Node[] table = new Node[128];

    public Huffman() {
        for(int i = 0; i < table.length; i++) {
            table[i] = new Node((char)i, 0, null, null);
        }

    }
    public static void main(String[] args) {

        int count = 0; // counter for ascii symbols

        String text = readTextfile("text.txt");

        Huffman huff = new Huffman();
        for(int i = 0; i < text.length(); ++i) {
            for(int j = 0; j < table.length; j++) {
                if(table[j].character == text.charAt(i)) {
                    table[j].count++;
                    count++;
                }
            }

        }
        table = sort(table);

        for(int i = 0; i < table.length; ++i) {
            System.out.println((char)table[i].character + " " + table[i].count);
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
    public static Node[] sort(Node[] array) {
        Node tempNode;
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < array.length - i; j++) {
                if (array[j].count > array[j + 1].count) {
                    tempNode = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tempNode;
                }
            }
        }
        return array;
    }
}
