package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * @see IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int numOfElements;

    // Feel free to add more fields and constants.

    public ArrayHeap() {
        this.heap = makeArrayOfT(10);
        this.numOfElements = 0;
    }

    public ArrayHeap(int size) {
        this.heap = makeArrayOfT(size);
        this.numOfElements = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int arraySize) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[arraySize]);
    }

    @Override
    public T removeMin() {
        if (numOfElements == 0) {
            throw new EmptyContainerException();
        }
        T minValue = heap[0];
        heap[0] = heap[numOfElements - 1];
        heap[numOfElements - 1] = null;
        numOfElements--;
        percolateDown(0);
        return minValue;
    }

    private void percolateDown(int location) {
        if (4 * location + 1 < heap.length && heap[4 * location + 1] != null) {
            T[] childValues = makeArrayOfT(4);
            if (4 * location + 1 < heap.length && heap[4 * location + 1] != null) {
                childValues[0] = heap[4 * location + 1];
            }
            if (4 * location + 2 < heap.length && heap[4 * location + 2] != null) {
                childValues[1] = heap[4 * location + 2];
            }
            if (4 * location + 3 < heap.length && heap[4 * location + 3] != null) {
                childValues[2] = heap[4 * location + 3];
            }
            if (4 * location + 4 < heap.length && heap[4 * location + 4] != null) {
                childValues[3] = heap[4 * location + 4];
            }
            T smallestChild = heap[4 * location + 1];
            int smallestChildLocation = 0;
            for (int i = 1; i < childValues.length; i++) {
                if (childValues[i] != null && childValues[i].compareTo(smallestChild) < 0) {
                    smallestChild = childValues[i];
                    smallestChildLocation = i;
                }
            }
            smallestChildLocation = 4 * location + 1 + smallestChildLocation;
            if (heap[location].compareTo(heap[smallestChildLocation]) > 0) {
                T parentData = heap[location];
                heap[location] = smallestChild;
                heap[smallestChildLocation] = parentData;
                percolateDown(smallestChildLocation);
            }
        }
    }

    @Override
    public T peekMin() {
        if (numOfElements == 0) {
            throw new EmptyContainerException();
        } else {
            return heap[0];
        }
    }

    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (numOfElements == heap.length) {
            T[] newHeap = makeArrayOfT(heap.length * 2);
            for (int i = 0; i < heap.length; i++) {
                newHeap[i] = heap[i];
            }
            numOfElements = heap.length;
            newHeap[numOfElements] = item;
            heap = newHeap;
        } else {
            heap[numOfElements] = item;
        }
        numOfElements++;
        percolateUp(numOfElements - 1);
    }

    private void percolateUp(int location) {
        if (location != 0) {
            int parentLocation = (location - 1) / 4;
            if (heap[parentLocation].compareTo(heap[location]) > 0) {
                T parentData = heap[parentLocation];
                heap[parentLocation] = heap[location];
                heap[location] = parentData;
                percolateUp(parentLocation);
            }
        }
    }

    @Override
    public int size() {
        return numOfElements;
    }
}
