package com.lushu;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author vitor
 */
public class Node {

    private String regex;
    private boolean sensitive;
    private ArrayList<Node> children;

    public Node(String regex, boolean sensitive, ArrayList<Node> children) {
        setRegex(regex);
        setSensitive(sensitive);
        setChildren(children);
    }

    public Node(String regex, boolean sensitive) {
        this(regex, sensitive, new ArrayList<>());
    }

    public Node(String regex) {
        this(regex, false, new ArrayList<>());
    }

    public Node() {
        this("", false, new ArrayList<>());
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public void setSensitive(boolean sensitive) {
        this.sensitive = sensitive;
    }

    public void setChildren(ArrayList<Node> children) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children = children;
    }

    public String getRegex() {
        return this.regex;
    }

    public boolean getSensitive() {
        return this.sensitive;
    }

    public Pattern getPattern() {
        return Pattern.compile(this.regex);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public Node findChild(String childString) {
        if (children == null) {
            return null;
        }
        return children.stream()
                .filter(child -> child.getPattern().matcher(childString).matches())
                .findFirst()
                .orElse(null);
    }

    public String mergeRegex(String regex1, String regex2) {
        if (regex1.isBlank()) {
            return regex2;
        } else if (regex2.isBlank()) {
            return regex1;
        }
        return regex1 + "|" + regex2;
    }

    public void mergeChild(Node child1, Node child2) {
        String mergedRegex = mergeRegex(child1.getRegex(), child2.getRegex());

        boolean mergedBoolean = child1.getSensitive() || child2.getSensitive();

        child1.getChildren().addAll(child2.getChildren());
        this.children.add(new Node(mergedRegex, mergedBoolean, child1.getChildren()));

        this.children.removeIf(node -> node == child1 || node == child2);
    }

    public void mergeChildren(boolean sensitive) {
        String[] mergedRegex = {""};
        if (this.getChildren() == null || this.getChildren().isEmpty()) {
            return;
        }
        ArrayList<Node> mergedChildren = this.children.stream()
                .filter(child -> child.sensitive == sensitive)
                .peek(child -> mergedRegex[0] = mergeRegex(mergedRegex[0], child.getRegex()))
                .flatMap(child -> child.getChildren().stream())
                .collect(Collectors.toCollection(ArrayList::new));

        this.children.removeIf(child -> child.sensitive == sensitive);

        if (mergedChildren.isEmpty()) {
            this.children.add(new Node(mergedRegex[0], sensitive, null));
        } else {
            this.children.add(new Node(mergedRegex[0], sensitive, mergedChildren));
        }

    }

    public void mergeChildrenByIndex(ArrayList<Integer> indexes) {
        if (this.getChildren() != null) {
            for (int i = indexes.size() - 1; i >= 0; i--) {
                int index = indexes.get(i);
                if (index >= 0 && index < children.size()) {
                    children.remove(index);
                }
            }
        }
    }
}
