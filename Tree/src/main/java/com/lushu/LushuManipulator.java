package com.lushu;

import java.util.ArrayList;

/**
 *
 * @author vitor
 */
public class LushuManipulator {

    private Lushu tree;

    public LushuManipulator() {
        tree = new Lushu();
    }

    public void build(String text) {
        Node node = tree.getRoot();
        RegexManipulator rm = new RegexManipulator();
        for (String word : text.split(" ")) {

            String _word = rm.isSensitive(word);
            boolean sensitive = (_word == null ? word == null : _word.equals(word));

            Node child = node.findChild(_word);
            if (child == null) {
                node.addChild(new Node(_word, sensitive));
                node = node.findChild(_word);
            } else {
                node = child;
            }
        }

    }

    public boolean mergeTreeChildren(Node parentNode, String regexChild1, String regexChild2) {
        if (parentNode == null) {
            parentNode = tree.getRoot();
        }

        Node child1 = parentNode.findChild(regexChild1);
        Node child2 = parentNode.findChild(regexChild2);

        if (child1 == null || child2 == null) {
            return false;
        }

        parentNode.mergeChild(child1, child2);
        return true;
    }

    private void merge(Node node) {

        if (node == null || node.getChildren() == null || node.getChildren().isEmpty()) {
            return;
        }
        node.mergeChildren(false);
        node.mergeChildren(true);
        node.getChildren().forEach(this::merge);
    }

    public void mergeTree() {
        merge(tree.getRoot());

    }

    public void removeByIndex(Node parentNode, ArrayList<Integer> index) {
        if (parentNode == null) {
            parentNode = tree.getRoot();
        }
        parentNode.mergeChildrenByIndex(index);
    }

    private void print(Node node, int level) {
        if (node == null) {
            return;
        }
        if (node.getRegex().isBlank() && node != tree.getRoot()) {
            return;
        }
        System.out.println("-".repeat(level) + " " + node.getRegex());

        if (node.getChildren() == null || node.getChildren().isEmpty()) {
            return;
        }

        node.getChildren().forEach(child -> print(child, level + 1));
    }

    public void printTree() {
        print(tree.getRoot(), 0);
    }
}
