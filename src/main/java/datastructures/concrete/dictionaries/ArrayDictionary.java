package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @see datastructures.interfaces.IDictionary
 */

public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field.
    // We will be inspecting it in our private tests.
    private Pair<K, V>[] pairs;
    private int size;
    private int capacity;
    // You may add extra fields or helper methods though!

    public ArrayDictionary() {
        this.size = 0;
        this.capacity = 10;
        this.pairs = makeArrayOfPairs(10);
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);
    }

    @Override
    public V get(K key) {
        if(containsKey(key)) {
            return pairs[indexOf(key)].value;
        }
        throw new NoSuchKeyException();
    }

    @Override
    public void put(K key, V value) {
        if(!containsKey(key)) {
            if (size == capacity) {
                capacity *= 2;
                Pair<K, V>[] newPairs = makeArrayOfPairs(capacity);
                for (int i = 0; i < size; i++) {
                    newPairs[i] = pairs[i];
                }
                pairs = newPairs;
            }

            pairs[size] = new Pair<K, V>(key, value);
            size++;
        }
        else   {
            int ndx = indexOf(key);
            pairs[ndx].value = value;
        }
    }

    @Override
    public V remove(K key) {
        if(!containsKey(key)) {
            throw new NoSuchKeyException();
        }
        int ndx = indexOf(key);
        V ret = pairs[ndx].value;
        if(ndx != size - 1) {
            pairs[ndx] = pairs[size - 1];
        }
        size--;
        return ret;
    }

    @Override
    public boolean containsKey(K key) {
        return !(indexOf(key) == -1);
    }

    @Override
    public int size() {
        return this.size;
    }

    private int indexOf(K key)   {
        for (int i = 0; i < size; i++) {
            if ((key == null && pairs[i].key == null) || (pairs[i].key != null && pairs[i].key.equals(key))) {
                return i;
            }
        }
        return -1;
    }

    private static class Pair<K, V> {
        public K key;
        public V value;


        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }

    public Iterator<KVPair<K, V>> iterator() {
        return new ArrayDictionaryIterator<>(pairs, size);
    }

    private static class ArrayDictionaryIterator<K, V> implements Iterator<KVPair<K, V>> {

        private Pair<K,V>[] pairs;
        private int size;
        private int location;

        public ArrayDictionaryIterator(Pair<K, V>[] pairs, int size) {
            this.pairs = pairs;
            this.size = size;
            this.location = 0;
        }

        public boolean hasNext() {
            if (location < size) {
                return true;
            } else {
                return false;
            }
        }

        public KVPair<K, V> next() {
            if (hasNext()) {
                K key = pairs[location].key;
                V value = pairs[location].value;
                KVPair<K, V> currentPair = new KVPair<K, V>(key, value);
                location++;
                return currentPair;
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
