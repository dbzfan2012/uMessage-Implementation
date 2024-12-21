package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {

    V oldValue;

    public AVLTree() {
        super();
        oldValue = null;
    }

    public V insert(K key, V value) {
        this.root = insert((AVLNode) this.root, key, value);
        V toReturn = this.oldValue;
        this.oldValue = null;
        return toReturn;
    }

    public AVLNode insert(AVLNode current, K key, V value) {
        if (current == null) {
            this.size++;
            return new AVLNode(key, value);
        }
        else if (key.compareTo(current.key) < 0) {
            current.children[0] = insert((AVLNode) current.children[0], key, value);
        }
        else if (key.compareTo(current.key) > 0) {
            current.children[1] = insert((AVLNode) current.children[1], key, value);
        }
        else {
            this.oldValue = current.value;
            current.value = value;
            return current;
        }
        AVLNode left = (AVLNode) current.children[0];
        AVLNode right = (AVLNode) current.children[1];
        current.height = Math.max(getHeight(left), getHeight(right)) + 1;
        int balance = getHeight(left) - getHeight(right);
        //case 3 or 4
        if (balance < -1) {
            //case3 - right rotation, then left rotation
            if (key.compareTo(right.key) < 0) {
                current.children[1] = rightRotation(right);
            }
            //case 4 - left rotation
            return leftRotation(current);
        }
        //case 1 or 2
        else if (balance > 1) {
            //case 2
            if (key.compareTo(left.key) > 0) {/*case2 - left rotation, then right rotation*/
                current.children[0] = leftRotation(left);
            }
            //case1 - right rotation
            return rightRotation(current);
        }
        return current;
    }

    public int getHeight(AVLNode root) {
        if (root == null) {
            return -1;
        }
        else {
            return root.height;
        }
    }

    //rotate given middle of rotation
    public AVLNode leftRotation(AVLNode parent) {
        AVLNode node = (AVLNode) parent.children[1];
        AVLNode child = (AVLNode) node.children[0];
        parent.children[1] = child;
        node.children[0] = parent;

        parent.height = Math.max(getHeight((AVLNode) parent.children[0]), getHeight((AVLNode) parent.children[1])) + 1;
        node.height = Math.max(getHeight((AVLNode) node.children[0]), getHeight((AVLNode) node.children[1])) + 1;

        return node;
    }

    public AVLNode rightRotation(AVLNode parent) {
        AVLNode node = (AVLNode) parent.children[0];
        AVLNode child = (AVLNode) node.children[1];
        parent.children[0] = child;
        node.children[1] = parent;

        parent.height = Math.max(getHeight((AVLNode) parent.children[0]), getHeight((AVLNode) parent.children[1])) + 1;
        node.height = Math.max(getHeight((AVLNode) node.children[0]), getHeight((AVLNode) node.children[1])) + 1;

        return node;
    }

    public void preorder() {
        preorder((AVLNode) this.root);
    }

    public void preorder(AVLNode node) {
        if (node == null)
            return;

        // traverse the root node
        System.out.print(node.key + "->");
        // traverse the left child
        preorder((AVLNode) node.children[0]);
        // traverse the right child
        preorder((AVLNode) node.children[1]);
    }

    public class AVLNode extends BSTNode {
        public int height;
        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }
    }
}