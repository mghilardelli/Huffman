import java.io.*;
import java.util.PriorityQueue;

/**
 * Created by marcoghilardelli on 06.05.16.
 */
public class Huffman {
    public static class Knoten implements Comparable<Knoten> {

        public int ascii;
        public int anzahl;
        public Knoten links;
        public Knoten rechts;

        public Knoten(int ascii, int anzahl, Knoten links, Knoten rechts) {
            this.ascii = ascii;
            this.anzahl = anzahl;
            this.links = links;
            this.rechts = rechts;
        }

        @Override
        public int compareTo(Knoten comp) {
            return this.anzahl - comp.anzahl;
        }
    }

    public static int[] table = new int[128];



    public static void main(String[] args) {

        initTable(table);

        String text = readTextfile("text.txt");

        fillTable(text, table);
        /*for(int i = 0; i < table.length; i++) {
            System.out.println((char)i + ":" + table[i]);
        }*/

        PriorityQueue<Knoten> tree = new PriorityQueue<>();

        // alle ascii Zeichen in den Baum einfügen
        for(int i = 0; i < table.length; i++) {
            if(table[i] != 0) {
                Knoten newKnoten = new Knoten(i, table[i], null, null);
                tree.add(newKnoten);
            }
        }
        while(!tree.isEmpty()) {
            Knoten temp = tree.poll();

            System.out.println((char)temp.ascii + ":" +temp.anzahl);
        }

        Knoten myTree=  buildTree(table);
        String[] codeTable = new String[128];
        buildCode(codeTable, myTree, "");

        for(int i = 0; i < codeTable.length; i++) {
            System.out.println((char)i + ":" + codeTable[i]);
        }

        saveCode("dec_tab.txt", codeTable);

        System.out.println(encode(codeTable, text));

        byte[] byteArray = createByteArray(encode(codeTable, text));

        writeByteArray("output.dat", byteArray);

        //decode();

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

    public static void saveCode(String fileName, String[] codeTable) {
        String text = "";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
            for(int i = 0; i < codeTable.length; i++) {
                if(codeTable[i] != null)
                    text += i + ":" + codeTable[i] + "-";
            }

            bw.write(text);
            bw.close();

        }catch(IOException e) {
            System.out.println(e);
        }

    }

    public static String encode(String[] codeTable, String text) {
        String code = "";
        for(int i = 0; i < text.length(); i++) {
            code += codeTable[text.toCharArray()[i]];
        }

        code += "1";

        while(code.length() % 8 != 0) {
            code += "0";
        }

        return code;
    }

    public static void initTable(int[] array) {
        for(int i = 0; i < array.length; i++) {
            array[i] = 0;
        }

    }

    public static byte[] createByteArray(String code) {
        byte[] byteArray = new byte[code.length() / 8];
        for(int i = 0; i < byteArray.length; i++) {
            String byteString = code.substring(i * 8, (i*8) +8);
            byteArray[i] = (byte)Integer.parseInt(byteString, 2);

        }
        return byteArray;
    }

    public static void writeByteArray(String fileName, byte[] out) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(out);
            fos.close();
        }catch(IOException e) {
            System.out.println(e);
        }
    }

    public static void decode() {
        byte[] bFile;
        String codeStream = "";
        try {
            File f1 = new File("output.dat");
            File f2 = new File("dec_tab.txt");
            bFile = new byte[(int) f1.length()];
            FileInputStream fis = new FileInputStream(f1);
            BufferedReader br = new BufferedReader(new FileReader(f2));
            codeStream = br.readLine();
            fis.read(bFile);
            br.close();
            fis.close();
        }
        catch(IOException e) {
            System.out.println(e);
        }

        String[] asciiCode = codeStream.split("-");
        String[] codeTable = new String[asciiCode.length];
        for(int i = 0; i < asciiCode.length; i++) {
            char ascii = asciiCode[i].charAt(0);
            codeTable[ascii] = asciiCode[i].split(":")[1];
        }

        for(int i = 0; i < codeTable.length; i++) {
            System.out.println(i + ":" +codeTable[i]);
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

}
