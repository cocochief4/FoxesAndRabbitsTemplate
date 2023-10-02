package Animals;

import java.util.ArrayList;

public class Node {

    private Node parentNode;
    
    private ArrayList<Node> childNodes;

    public Node (Node parentNode) {
        this.parentNode = parentNode;
        parentNode.addChild(this);
        childNodes = new ArrayList<Node>();
    }

    public void addChild (Node childNode) {
        childNodes.add(childNode);
    }

    public boolean isRoot () {
        return parentNode == null;
    }

    public int distToRoot() {

    }

    public int longestBranch() {
        int distFromRoot = distToRoot();
    }
}
