package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieSet;
import cse332.types.BString;

public class HashTrieSet<A extends Comparable<A>, E extends BString<A>> extends TrieSet<A, E> {

    // Creates a new HashTrieSet
    public HashTrieSet(Class<E> Type) {
        // Call the correct super constructor...that's it!
        super(new HashTrieMap<>(Type));
    }
}
