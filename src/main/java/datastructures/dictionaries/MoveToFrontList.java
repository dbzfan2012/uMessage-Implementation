package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;
import datastructures.worklists.ListFIFOQueue;

import java.util.Iterator;

public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    private LinkedNode front;

    public MoveToFrontList() {
        this.front = null;
        this.size = 0;
    }
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("The arguments cannot be null!");
        }
        if (this.front == null) { //the list is empty
            this.front = new LinkedNode(key, value);
            this.size++;
            return null;
        }
        LinkedNode current = this.front;
        if (current.key.equals(key)) {
            V old = current.value;
            current.value = value;
            return old;
        }
        while (current.next != null) { //loop through list to find key
            if (current.next.key.equals(key)) { //found key and will replace value
                LinkedNode newFront = current.next;
                current.next = current.next.next;
                newFront.next = this.front;
                this.front = newFront;
                V old = newFront.value;
                newFront.value = value;
                return old;
            }
            current = current.next;
        } //no key found and will create new front node
        this.front = new LinkedNode(key, value, this.front);
        this.size++;
        return null;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key cannot be null!");
        }
        if (this.front == null) {
            return null;
        }
        if (this.front.key.equals(key)) {
            return this.front.value;
        }
        LinkedNode current = this.front;
        while (current.next != null) {
            if (current.next.key.equals(key)) {
                LinkedNode newFront = current.next;
                current.next = current.next.next;
                newFront.next = this.front;
                this.front = newFront;
                return newFront.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MTFLIterator();
    }

    private class MTFLIterator extends SimpleIterator<Item<K, V>> {

        private LinkedNode current;

        public MTFLIterator() {
            if (MoveToFrontList.this.front != null) {
                this.current = MoveToFrontList.this.front;
            }
        }
        public Item<K, V> next() {
            Item<K, V> value = null;
            if (hasNext()) {
                value = new Item<K, V>(this.current.key, this.current.value);
                this.current = this.current.next;
            }
            return value;
        }

        public boolean hasNext() {
            return this.current != null;
        }
    }

    private class LinkedNode {
        public LinkedNode next;
        public K key;
        public V value;
        public LinkedNode(K key, V value, LinkedNode next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public LinkedNode(K key, V value) {
            this(key, value, null);
        }
    }
}
