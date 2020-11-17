package com.example.keepsafe_v2;

public class Node implements Comparable<Node> {
    public String title;
    public String user;
    public String pw;

    public Node(String title, String user, String pw) {
        this.title = title;
        this.user = user;
        this.pw = pw;
    }

    //returns title
    public String getTitle() {
        return title;
    }

    //returns string rep. of Node (using title)
    public String toString() {
        return title;
    }

    //compares nodes based on title in alphabetical order
    @Override
    public int compareTo(Node node) {
        if (this.title.compareToIgnoreCase(node.title) < 0) {
            return -1;
        } else if (this.title.compareToIgnoreCase(node.title) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
