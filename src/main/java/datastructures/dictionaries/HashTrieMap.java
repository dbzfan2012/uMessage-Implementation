package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<A, HashTrieNode>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return new HTNIterator();
        }

        private class HTNIterator extends SimpleIterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> {

            Iterator pointersIter;
            public HTNIterator() {
                pointersIter = HashTrieNode.this.pointers.iterator();
            }

            public Entry<A, HashTrieMap<A, K, V>.HashTrieNode> next() {
                Item<A, HashTrieNode> item = (Item<A, HashTrieNode>) pointersIter.next();
                Entry<A, HashTrieMap<A, K, V>.HashTrieNode> value = new SimpleEntry<>(item.key, item.value);
                return value;
            }

            public boolean hasNext() {
                return pointersIter.hasNext();
            }
        }
    }

    // Creates a new HashTrieMap
    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
        this.size = 0;
    }

    // Inserts a new value at the specified key into the map
    // Returns the key's previous value
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        HashTrieNode current = (HashTrieNode) this.root;
        for (A character : key) {
            if (current.pointers.find(character) == null) {
                current.pointers.insert(character, new HashTrieNode());
                this.size++;
            }
            current = current.pointers.find(character);
        }
        V oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    // Finds the value in the map at the specified key
    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        HashTrieNode current = (HashTrieNode) this.root;
        for (A character : key) {
            if (current.pointers.find(character) == null) {
                return null;
            }
            current = current.pointers.find(character);
        }
        return current.value;
    }

    // Checks whether the key prefix exists in the map
    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        HashTrieNode current = (HashTrieNode) this.root;
        if (current.pointers.isEmpty() && current.value == null) {
            return false;
        }
        for (A character : key) {
            if (current.pointers.find(character) == null) {
                return false;
            }
            current = current.pointers.find(character);
        }
        return true;
    }

    // Deletes the key-value pair at the specified key
    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException("No longer works with Chaining Hash Table...");
//        if (key == null) {
//            throw new IllegalArgumentException("Arguments cannot be null");
//        }
//        HashTrieNode current = (HashTrieNode) this.root;
//        HashTrieNode toRemove = current;
//        A removeChar = null;
//        for (A character : key) {
//            if (!current.pointers.containsKey(character)) {
//                return;
//            }
//            // Marks the node for deletion
//            if (current.pointers.size() > 1 || current.value != null) {
//                toRemove = current;
//                removeChar = character;
//            }
//            current = current.pointers.get(character);
//        }
//        if (current.value == null) {
//            return;
//        }
//        if (!current.pointers.isEmpty()) {
//            current.value = null;
//        }
//        else if (removeChar == null){
//            toRemove.pointers.clear();
//        }
//        else {
//            toRemove.pointers.remove(removeChar);
//        }
//        this.size--;
    }

    // Resets the map, removes all previous key-value pairs
    @Override
    public void clear() {
        throw new UnsupportedOperationException("No longer works with Chaining Hash Table...");
//        this.root = new HashTrieNode();
//        this.size = 0;
    }
}
