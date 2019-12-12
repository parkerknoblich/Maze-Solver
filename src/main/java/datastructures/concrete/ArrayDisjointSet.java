package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IDisjointSet;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.exceptions.NoSuchKeyException;

/**
 * @see IDisjointSet for more details.
 */
public class ArrayDisjointSet<T> implements IDisjointSet<T> {
    private int[] pointers;
    private ISet<ISet> forest;
    private IDictionary<T, Integer> map;
    private int location;

    public ArrayDisjointSet() {
        pointers = new int[10000];
        forest = new ChainedHashSet<>();
        map = new ChainedHashDictionary<>();
        location = 0;
    }

    @Override
    public void makeSet(T item) {
        if (map.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        ISet<T> set = new ChainedHashSet<>();
        set.add(item);
        forest.add(set);
        if (location == pointers.length) {
            int[] newPointers = new int[2 * pointers.length];
            for (int i = 0; i < pointers.length; i++) {
                newPointers[i] = pointers[i];
            }
            pointers = newPointers;
        }
        pointers[location] = -1;
        map.put(item, location);
        location++;
    }

    @Override
    public int findSet(T item) {
        try {
            map.get(item);
        } catch (NoSuchKeyException ex) {
            throw new IllegalArgumentException();
        }
        int set = map.get(item);
        int rep = findSetHelper(set);
        IList<Integer> kids = updateKids(set, new DoubleLinkedList<>());
        for (Integer kid : kids) {
            pointers[kid] = rep;
        }
        return rep;
    }

    private int findSetHelper(int index) {
        if (pointers[index] < 0) {
            return index;
        } else {
            return findSetHelper(pointers[index]);
        }
    }

    private IList<Integer> updateKids(int index, IList<Integer> list) {
        if (pointers[index] < 0) {
            return list;
        } else {
            list.add(index);
            return updateKids(pointers[index], list);
        }
    }

    @Override
    public void union(T item1, T item2) {
        int oneRep = findSet(item1);
        int oneValue = pointers[oneRep];
        int twoRep = findSet(item2);
        int twoValue = pointers[twoRep];
        int smaller = Math.min(oneValue, twoValue);
        if (oneRep != twoRep) {
            if ((oneValue + twoValue) / 2 == smaller || smaller == oneValue) {
                pointers[twoRep] = oneRep;
                int oneRank = (oneValue * -1) - 1;
                int twoRank = (twoValue * -1) - 1;
                int newRank = 0;
                if (oneRank == twoRank) {
                    newRank = oneRank + 1;
                } else {
                    newRank = Math.max(oneRank, twoRank);
                }
                int newValue = (newRank * -1) - 1;
                pointers[oneRep] = newValue;
            } else {
                pointers[oneRep] = twoRep;
                int oneRank = (oneValue * -1) - 1;
                int twoRank = (twoValue * -1) - 1;
                int newRank = 0;
                if (oneRank == twoRank) {
                    newRank = oneRank + 1;
                } else {
                    newRank = Math.max(oneRank, twoRank);
                }
                int newValue = (newRank * -1) - 1;
                pointers[twoRep] = newValue;
            }
        }
    }
}
