package adaptivehuffman;

import java.util.HashMap;

public class AdaptiveHuffman {

    public static void main(String[] args) throws CloneNotSupportedException {
        String text = "ABCCCAAAA";
        String compressedText = compress(text);
        System.out.println("The Comporessed Text: " + compressedText);
        
        HashMap<String, Character> shortCodeTable = new HashMap<>();
        shortCodeTable.put("00", 'A');
        shortCodeTable.put("01", 'B');
        shortCodeTable.put("10", 'C');
        
        String decompressedText = deCompress(compressedText, shortCodeTable);
        System.out.println("The decompressed Text: " + decompressedText);
    }

    public static String compress(String text) throws CloneNotSupportedException{
        Tree tree = new Tree();
        for (int i = 0; i < text.length(); i++) {
            tree.insert(text.charAt(i));
        }
        return tree.compressedText;
    }
    
    public static String deCompress(String compressedText, HashMap<String, Character> shortCodeTable) throws CloneNotSupportedException {
        String decompressedText = "";
        Tree tree = new Tree();
        String firstSymbolCode = compressedText.substring(0, 2);
        decompressedText += shortCodeTable.get(firstSymbolCode);
        tree.insert(shortCodeTable.get(firstSymbolCode));
        String code = "";
        for (int i = 2; i < compressedText.length(); i++) {
            code += compressedText.charAt(i);
            if (code.equals(tree.emptyNode.huffmanCode)){
                code = compressedText.substring(i + 1, i + 3);
                decompressedText += shortCodeTable.get(code);
                tree.insert(shortCodeTable.get(code));
                i = i+2;
                code = "";
            }
            else {
                for (Node node : tree.symbolsList){
                    if (node.huffmanCode.equals(code)){
                        decompressedText += node.letter;
                        tree.insert(node.letter);
                        code = "";
                        break;
                    }
                }
            }
        }
        return decompressedText;
    }
}
