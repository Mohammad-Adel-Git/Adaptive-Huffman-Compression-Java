package adaptivehuffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Tree {

    public Node root = new Node();
    public String compressedText = "";
    public Node emptyNode;
    public HashMap<Character, String> shortCodeTable = new HashMap<>();
    public HashMap<Character, Node> addedSymbols = new HashMap<>();
    public ArrayList<Node> symbolsList = new ArrayList<>();

    public Tree() {
        root.counter = 1;
        root.nodeNumber = 100;
        root.left = null;
        root.right = null;
        root.isNYT = false;
        shortCodeTable.put('A', "00");
        shortCodeTable.put('B', "01");
        shortCodeTable.put('C', "10");
    }

    public void insert(char letter) throws CloneNotSupportedException {
        // the first element to enter the tree
        if (root.right == null && root.left == null) {
            compressedText += shortCodeTable.get(letter);
            addFirstNodeToTree(letter);
        } else {            // the ordinary algorithm
            // the letter is already existed in the tree
            if (!firstOccurrence(letter)) {
                compressedText += addedSymbols.get(letter).huffmanCode;
            } 
            else { // the letter is first time to come
                compressedText += emptyNode.huffmanCode;
                compressedText += shortCodeTable.get(letter);
            }
            update(letter);
        }
    }

    public void update(char letter) throws CloneNotSupportedException {
        Node checkNode;
        if (firstOccurrence(letter)) {
            Node symbolNode = new Node(letter, emptyNode.nodeNumber - 1, emptyNode.huffmanCode + "1");
            symbolNode.counter++;
            emptyNode.right = symbolNode;
            symbolNode.parent = emptyNode;
            Node newNYT = new Node(emptyNode.nodeNumber - 2, emptyNode.huffmanCode + "0");
            emptyNode.left = newNYT;
            newNYT.parent = emptyNode;
            emptyNode.counter++;
            emptyNode = newNYT;
            addedSymbols.put(letter, symbolNode);
            symbolsList.add(symbolNode);
            checkNode = emptyNode.parent;   // the old NYT
        } else { 
            Node symbolNode = addedSymbols.get(letter);
            Node swapableNode = findSwapableNode(symbolNode);
            checkNode = symbolNode;
            if (swapableNode != null){  //swap conditions Exist ?
                swap(symbolNode, swapableNode);
                symbolNode.counter++;
            }
            else{
                symbolNode.counter++;
            }
        }
        while (checkNode != root){
            checkNode = checkNode.parent;
            Node swapableNode = findSwapableNode(checkNode);
            if (swapableNode != null){
                swap(checkNode, swapableNode);
                checkNode.counter++;
            }
            else{
                checkNode.counter++;
            }
        }
    }
    
    public void swap(Node symbolNode, Node swapableNode) throws CloneNotSupportedException{
        Node tempNode = (Node) symbolNode.clone();
        String swapableCode = swapableNode.huffmanCode;
        String symbolCode = symbolNode.huffmanCode;
        if (swapableCode.charAt(swapableCode.length() - 1) == '1'){
            symbolNode.parent = swapableNode.parent;
            swapableNode.parent.right = symbolNode;
            symbolNode.huffmanCode = symbolNode.parent.huffmanCode + "1";
            symbolNode.nodeNumber = swapableNode.nodeNumber;
        }
        else{
            symbolNode.parent = swapableNode.parent;
            swapableNode.parent.left = symbolNode;
            symbolNode.huffmanCode = symbolNode.parent.huffmanCode + "0";
            symbolNode.nodeNumber = swapableNode.nodeNumber;
        }
        if (symbolCode.charAt(symbolCode.length() - 1) == '1'){
            swapableNode.parent = tempNode.parent;
            tempNode.parent.right = swapableNode;
            swapableNode.huffmanCode = swapableNode.parent.huffmanCode + "1";
            swapableNode.nodeNumber = tempNode.nodeNumber;
        }
        else{
            swapableNode.parent = tempNode.parent;
            tempNode.parent.left = swapableNode;
            swapableNode.huffmanCode = swapableNode.parent.huffmanCode + "0";
            swapableNode.nodeNumber = tempNode.nodeNumber;
        }
        updateCodeForChildren(symbolNode);
        updateCodeForChildren(swapableNode);
    }
    
    public void updateCodeForChildren(Node root) {
        if (root.left == null && root.right == null) {            
            return;
        }
        root.left.huffmanCode = root.huffmanCode + "0";
        root.right.huffmanCode = root.huffmanCode + "1";
        updateCodeForChildren(root.left);
        updateCodeForChildren(root.right);
    }
    
    public void addFirstNodeToTree(char letter) {
        Node charNode = new Node(letter, root.nodeNumber - 1, "1");
        charNode.parent = root;
        charNode.counter++;
        root.right = charNode;
        emptyNode = new Node(root.nodeNumber - 2, "0");
        emptyNode.parent = root;
        root.left = emptyNode;
        addedSymbols.put(letter, charNode);
        symbolsList.add(charNode);
    }

    public boolean firstOccurrence(char letter) {
        if (addedSymbols.get(letter) != null) {
            return false;
        }
        return true;
    }

    public Node findSwapableNode(Node node) {
        Node findedNode = null;
        if (root == null) {
            return null;
        }

        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {

            Node swapableNode = nodes.remove();
            if (swapableNode.counter <= node.counter
                    && swapableNode.nodeNumber > node.nodeNumber
                    && swapableNode != node.parent) {
                findedNode = swapableNode;
                break;
            }

            if (swapableNode.nodeNumber <= node.nodeNumber) {
                break;
            }

            if (swapableNode.right != null) {
                nodes.add(swapableNode.right);
            }

            if (swapableNode.left != null) {
                nodes.add(swapableNode.left);
            }
        }
        return findedNode;
    }    
}
