package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import misc.exceptions.NotYetImplementedException;

import java.rmi.NoSuchObjectException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @see IDictionary and the assignment page for more details on what each method should do
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private IDictionary<K, V>[] chains;
    private int numPairs;

    // You're encouraged to add extra fields (and helper methods) though!

    public ChainedHashDictionary() {
        this.chains = makeArrayOfChains(10);
        this.numPairs = 0;
    }

    public ChainedHashDictionary(int size) {
        this.chains = makeArrayOfChains(size);
        this.numPairs = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain IDictionary<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains(int size) {
        // Note: You do not need to modify this method.
        // See ArrayDictionary's makeArrayOfPairs(...) method for
        // more background on why we need this method.
        return (IDictionary<K, V>[]) new IDictionary[size];
    }

    @Override
    public V get(K key) {
        int hash = modedHashCode(key, chains.length);
        if (chains[hash] == null || !chains[hash].containsKey(key)) {
            throw new NoSuchKeyException();
        } else {
            return chains[hash].get(key);
        }
    }

    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            numPairs++;
        }
        int hash = modedHashCode(key, chains.length);
        if (chains[hash] == null) {
            chains[hash] = new ArrayDictionary<K, V>();
        }
        chains[hash].put(key, value);
        if (numPairs / chains.length == 1) {
            IDictionary<K, V>[] newChains = makeArrayOfChains(chains.length * 2 - 1);
            for (KVPair<K, V> currentPair : this) {
                K currentKey = currentPair.getKey();
                hash = modedHashCode(currentKey, newChains.length);
                if (newChains[hash] == null) {
                    newChains[hash] = new ArrayDictionary<K, V>();
                }
                newChains[hash].put(currentKey, currentPair.getValue());
            }
            chains = newChains;
        }
    }

    @Override
    public V remove(K key) {
        int hash = modedHashCode(key, chains.length);
        if (chains[hash] == null || !chains[hash].containsKey(key)) {
            throw new NoSuchKeyException();
        } else {
            numPairs--;
            return chains[hash].remove(key);
        }
    }

    @Override
    public boolean containsKey(K key) {
        int hash = modedHashCode(key, chains.length);
        if (chains[hash] == null || !chains[hash].containsKey(key)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return numPairs;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        return new ChainedIterator<>(this.chains);
    }

    private int modedHashCode(K key, int size) {
        if (key == null) {
            return 0;
        } else {
            return Math.abs(key.hashCode()) % size;
        }
    }

    /**
     * Hints:
     *
     * 1. You should add extra fields to keep track of your iteration
     *    state. You can add as many fields as you want. If it helps,
     *    our reference implementation uses three (including the one we
     *    gave you).
     *
     * 2. Before you try and write code, try designing an algorithm
     *    using pencil and paper and run through a few examples by hand.
     *
     *    We STRONGLY recommend you spend some time doing this before
     *    coding. Getting the invariants correct can be tricky, and
     *    running through your proposed algorithm using pencil and
     *    paper is a good way of helping you iron them out.
     *
     * 3. Think about what exactly your *invariants* are. As a
     *    reminder, an *invariant* is something that must *always* be
     *    true once the constructor is done setting up the class AND
     *    must *always* be true both before and after you call any
     *    method in your class.
     *
     *    Once you've decided, write them down in a comment somewhere to
     *    help you remember.
     *
     *    You may also find it useful to write a helper method that checks
     *    your invariants and throws an exception if they're violated.
     *    You can then call this helper method at the start and end of each
     *    method if you're running into issues while debugging.
     *
     *    (Be sure to delete this method once your iterator is fully working.)
     *
     * Implementation restrictions:
     *
     * 1. You **MAY NOT** create any new data structures. Iterators
     *    are meant to be lightweight and so should not be copying
     *    the data contained in your dictionary to some other data
     *    structure.
     *
     * 2. You **MAY** call the `.iterator()` method on each IDictionary
     *    instance inside your 'chains' array, however.
     */
    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        private int location;
        private Iterator<KVPair<K, V>> iterator;

        public ChainedIterator(IDictionary<K, V>[] chains) {
            this.chains = chains;
            this.location = 0;
            for (int i = 0; i < chains.length; i++) {
                if (chains[i] != null) {
                    this.iterator = chains[i].iterator();
                    location = i;
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            for (int i = location; i < chains.length; i++) {
                if (iterator != null && iterator.hasNext()) {
                        return true;
                }
                if (location == chains.length - 1) {
                    return false;
                }
                location++;
                if (chains[location] != null) {
                    iterator = chains[location].iterator();
                } else {
                    iterator = null;
                }
            }
            return false;
        }

        @Override
        public KVPair<K, V> next() {
            if (iterator == null || !hasNext()) {
                throw new NoSuchElementException();
            } else {
                return iterator.next();
            }
        }
    }
}
