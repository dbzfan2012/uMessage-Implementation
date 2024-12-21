package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import datastructures.worklists.CircularArrayFIFOQueue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private float loadFactor;
    private int primesIndex;

    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};
    private Dictionary<K, V>[] table;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.primesIndex = 0;
        this.table = (Dictionary<K, V>[]) new Dictionary[PRIME_SIZES[primesIndex]];
        this.loadFactor = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value cannot be null");
        }
        int index = Math.abs(key.hashCode()) % table.length;
        if (this.table[index] == null) {
            this.table[index] = this.newChain.get();
        }
        Dictionary<K, V> dict = this.table[index];
        V returnValue = dict.find(key);
        dict.insert(key, value);
        if (returnValue == null) {
            this.size++;
        }
        this.loadFactor = (float) this.size / this.table.length;
        if (this.loadFactor > 1.5) {
            this.table = resizeTable();
        }
        return returnValue;
    }

    private Dictionary<K, V>[] resizeTable() {
        this.primesIndex++;
        Dictionary<K, V>[] newTable;
        if (this.primesIndex >= PRIME_SIZES.length) {
            newTable = (Dictionary<K, V>[]) new Dictionary[this.table.length * 2];
        }
        else {
            newTable = (Dictionary<K, V>[]) new Dictionary[PRIME_SIZES[primesIndex]];
        }
        this.size = 0;
        for (Dictionary<K, V> current : this.table) {
            if (current == null) {
                continue;
            }
            for (Item<K, V> kvItem : current) {
                int hash = Math.abs(kvItem.key.hashCode()) % newTable.length;
                if (newTable[hash] == null) {
                    newTable[hash] = newChain.get();
                }
                newTable[hash].insert(kvItem.key, kvItem.value);
                this.size++;
            }
        }
        this.loadFactor = this.size / newTable.length;
        return newTable;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = Math.abs(key.hashCode()) % table.length;
        Dictionary<K, V> dict = this.table[index];
        if (dict == null) {
            return null;
        }
        return dict.find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new CHTIterator();
    }

    private class CHTIterator extends SimpleIterator<Item<K, V>> {

        int arrayIndex;
        Iterator<Item<K, V>> dictIter;
        int elementsSeen;

        public CHTIterator() {
            this.arrayIndex = 0;
            this.elementsSeen = 0;
            dictIter = null;
            while (ChainingHashTable.this.table[arrayIndex] == null && hasNext()) {
                this.arrayIndex++;
            }
            if (ChainingHashTable.this.table[arrayIndex] != null) {
                dictIter = ChainingHashTable.this.table[arrayIndex].iterator();
            }
        }

        public Item<K, V> next() {
            Item<K, V> value = null;
            if (this.hasNext()) {
                if (!dictIter.hasNext()) {
                    this.arrayIndex++;
                    while (ChainingHashTable.this.table[this.arrayIndex] == null) {
                        this.arrayIndex++;
                    }
                    dictIter = ChainingHashTable.this.table[arrayIndex].iterator();
                }
                value = dictIter.next();
                this.elementsSeen++;
            }
            else {
                throw new NullPointerException("There are no more elements to iterate");
            }
            return value;
        }

        public boolean hasNext() {
            return this.elementsSeen < ChainingHashTable.this.size;
        }
    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        String str = "[";
        int line = 1;
        for (Item<K, V> item : this) {
            str += line + ": (" + item.key + ", " + item.value + ")\n";
            line++;
        }
        str += "]";
        return str;
    }
}
