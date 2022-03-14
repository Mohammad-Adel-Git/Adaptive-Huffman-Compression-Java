package adaptivehuffman;

public class Node implements Cloneable {

    public char letter;
    public String huffmanCode;
    public int counter;
    public int nodeNumber;
    
    public Node parent = null;
    public Node right = null;
    public Node left = null;
    
    public boolean isNYT = false;

    public Node() {
        huffmanCode = "";
        counter = 0;
    }

    public Node(char letter, int nodeNumber, String code) {
        this.letter = letter;
        this.nodeNumber = nodeNumber;
        huffmanCode = code;
    }
    
    public Node(int nodeNumber, String code) {
        this.nodeNumber = nodeNumber;
        huffmanCode = code;        
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();    // return shallow copy
    }

}
