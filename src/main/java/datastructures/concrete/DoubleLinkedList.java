package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Note: For more info on the expected behavior of your methods:
 * @see datastructures.interfaces.IList
 * (You should be able to control/command+click "IList" above to open the file from IntelliJ.)
 */
public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;

    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void add(T item) {
        Node<T> newItem = new Node<T>(item);
        if (size > 0) {
            back.next = newItem;
            newItem.prev = back;
            back = back.next;
        }
        else   {
            front = newItem;
            back = newItem;
        }
        size++;
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new EmptyContainerException();
        }
        else if (size == 1) {
            Node<T> current = front;
            front = null;
            back = null;
            size--;
            return current.data;
        }
        else   {
            Node<T> temp = back;
            back = back.prev;
            back.next = null;
            size--;
            return temp.data;
        }
    }

    @Override
    public T get(int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        else {
            if (index < size / 2) {
                Node<T> curr = front;
                for (int i = 0; i < index; i++) {
                    curr = curr.next;
                }
                return curr.data;
            }
            else {
                Node<T> curr = back;
                for (int i = size-1; i > index; i--) {
                    curr = curr.prev;
                }
                return curr.data;
            }
        }
    }

    @Override
    public void set(int index, T item) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        else {
            Node<T> curr = front;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            Node<T> newValue = new Node<T>(item);
            if (index == 0) {
                curr = curr.next;
                front = newValue;
                front.next = curr;
                if (curr != null) {
                    curr.prev = front;
                }
                else   {
                    back = newValue;
                }
            }
            else if (index == size - 1) {
                curr = curr.prev;
                curr.next = newValue;
                back = newValue;
                back.prev = curr;
            } else {
                Node<T> beforeCurr = curr.prev;
                Node<T> afterCurr = curr.next;
                beforeCurr.next = newValue;
                afterCurr.prev = newValue;
                newValue.next = afterCurr;
                newValue.prev = beforeCurr;
            }
        }
    }

    @Override
    public void insert(int index, T item) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        else {
            Node<T> newValue = new Node<T>(item);
            if (front == null) {
                front = newValue;
                back = newValue;
            }
            else {
                Node<T> curr = front;
                if (index == 0) {
                    front = newValue;
                    front.next = curr;
                    curr.prev = front;
                } else if (index + 1 == size) {
                    back.prev.next = newValue;
                    newValue.next = back;
                    newValue.prev = back.prev;
                    back.prev = newValue;
                } else if (index == size) {
                    back.next = newValue;
                    newValue.prev = back;
                    back = back.next;
                } else {
                    if (index > (size/2)) {
                        curr = back;
                        for (int i = size-1; i >= index; i--) {
                            curr = curr.prev;
                        }
                        Node<T> beforeCurr = curr.prev;
                        curr.prev = newValue;
                        newValue.next = curr;
                        beforeCurr.next = newValue;
                        newValue.prev = beforeCurr;
                    }
                    else   {
                        for (int i = 0; i < index; i++) {
                            curr = curr.next;
                        }
                        Node<T> beforeCurr = curr.prev;
                        curr.prev = newValue;
                        newValue.next = curr;
                        beforeCurr.next = newValue;
                        newValue.prev = beforeCurr;
                    }
                }
            }
            size++;
        }
    }

    @Override
    public T delete(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        size--;
        if (index == 0) {
            Node<T> temp = front;
            if (size == 0) {
                front = null;
                back = null;
            } else   {
                front = front.next;
                front.prev = null;
            }
            return temp.data;
        }
        else if (index == size) {
            Node<T> temp = back;
            back = back.prev;
            back.next = null;
            return temp.data;
        }
        else {
            Node<T> current = front;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            if (current.next.next != null) {
                Node<T> temp = current.next;
                current.next = current.next.next;
                current.next.prev = current;
                temp.next = null;
                temp.prev = null;
                return temp.data;
            }
            else {
                Node<T> temp = current.next;
                current.next = current.next.next;
                current.next.prev = current;
                return temp.data;
            }
        }
    }

    @Override
    public int indexOf(T item) {
        if (size > 0) {
            int index = 0;
            Node<T> current = front;
            while (current != null) {
                if (item == null || current.data == null) {
                    if (item == current.data) {
                        return index;
                    }
                    else {
                        current = current.next;
                        index++;
                    }
                } else {
                    if (current.data.equals(item)) {
                        return index;
                    } else {
                        current = current.next;
                        index++;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean contains(T other) {
        return indexOf(other) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }

    private static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Node(E data) {
            this(null, data, null);
        }

        // Feel free to add additional constructors or methods to this class.
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;

        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        public boolean hasNext()   {
            return current != null;
        }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *         there are no more elements to look at.
         */
        public T next() {
            if (!hasNext())   {
                throw new NoSuchElementException();
            }
            T res = current.data;
            current = current.next;
            return res;
        }
    }
}