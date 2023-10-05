package Animals;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Node {

    // ArrayList of all of the nodes created. Sorted into ArrayLists of a certain generation
    private static ArrayList<ArrayList<Node>> allNodes = new ArrayList<ArrayList<Node>>();

    private Node parentNode;

    private int nodeNumber;

    public Node (Node parentNode) {
        if (parentNode instanceof Node) {
            this.parentNode = parentNode;
            nodeNumber = parentNode.nodeNumber + 1;
        } else {
            this.parentNode = null;
            nodeNumber = 0;
        }
        
        // Add the new node to the list of all nodes.
        if (allNodes.size() == nodeNumber) {
            ArrayList<Node> newArr = new ArrayList<Node>();
            newArr.add(this);
            allNodes.add(newArr);
        } else {
            allNodes.get(nodeNumber).add(this);
        }
    }

    public boolean isRoot () {
        return parentNode == null;
    }

    // The second generation node would be one away.
    public int distToRoot() {
        return nodeNumber;
    }

    // Checks if the node is any way related to some old node.
    public boolean isSomeChildOf(Node parent) {
        if (parentNode.nodeNumber == parent.nodeNumber) {
            return parentNode == parent;
        } else {
            return this.parentNode.isSomeChildOf(parent);
        }
    }

    public int longestBranch() {
        for (int i = allNodes.size()-1; i > -1; i++) {
            for (Node node : allNodes.get(i)) {
                if (node.isSomeChildOf(this)) {
                    return node.distToRoot();
                }
            }
        }
    }
}
