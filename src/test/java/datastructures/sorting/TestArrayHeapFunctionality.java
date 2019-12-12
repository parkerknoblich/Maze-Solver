package datastructures.sorting;

import static org.junit.Assert.assertTrue;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }

    @Test(timeout=SECOND)
    public void testRemoveMinOnEmptyArrayThrowsException() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.removeMin();
            Assert.fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex){
            // All good!
        }
    }

    @Test(timeout=SECOND)
    public void testRemoveMinUpdatesSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(2);
        heap.insert(4);
        heap.insert(6);
        heap.insert(8);
        heap.removeMin();
        assertEquals(3, heap.size());
    }

    @Test(timeout=SECOND)
    public void testRemoveMinReturnsCorrectValueSmall() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(17);
        heap.insert(9);
        heap.insert(2);
        heap.insert(30);
        heap.insert(49);
        assertEquals(2, heap.removeMin());
    }

    @Test(timeout=SECOND)
    public void testRemoveMinReturnsCorrectValueLarge() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 5; i < 505; i++) {
            heap.insert(i);
        }
        heap.insert(4);
        heap.insert(506);
        assertEquals(4, heap.removeMin());
    }

    @Test(timeout=SECOND)
    public void testRemoveMinReplacesMinCorrectlySmall() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(17);
        heap.insert(9);
        heap.insert(2);
        heap.insert(30);
        heap.insert(49);
        heap.removeMin();
        assertEquals(9, heap.peekMin());
    }

    @Test(timeout=SECOND)
    public void testRemoveMinReplacesMinCorrectlyLarge() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 10; i < 510; i++) {
            heap.insert(i);
        }
        heap.insert(8);
        heap.insert(9);
        heap.removeMin();
        assertEquals(9, heap.removeMin());

    }

    @Test(timeout=SECOND)
    public void testRemoveMinOnOneElementArray() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(1);
        assertEquals(1, heap.removeMin());
    }

    @Test(timeout=SECOND)
    public void testRemoveMinAfterResizing() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 5; i < 505; i++) {
            heap.insert(i);
        }
        heap.insert(4);
        heap.insert(506);
        assertEquals(4, heap.removeMin());
    }

    @Test(timeout=SECOND)
    public void testRemoveMinWithIdenticalValues() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 500; i++) {
            heap.insert(5);
        }
        assertEquals(5, heap.removeMin());
    }

    @Test(timeout=SECOND)
    public void testPeekMinOnEmptyArrayThrowsException() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.peekMin();
            Assert.fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // All good!
        }
    }

    @Test(timeout=SECOND)
    public void testInsertNullItemThrowsException() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.insert(null);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // All good!
        }
    }

    @Test(timeout=SECOND)
    public void testInsertUpdatesSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(2);
        heap.insert(4);
        heap.insert(6);
        assertEquals(3, heap.size());
    }

    @Test(timeout=SECOND)
    public void testInsertSmall() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(17);
        heap.insert(9);
        heap.insert(30);
        heap.insert(2);
        heap.insert(49);
        assertEquals(2, heap.peekMin());
    }

    @Test(timeout=SECOND)
    public void testInsertLarge() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 5; i < 505; i++) {
            heap.insert(i);
        }
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        assertEquals(1, heap.peekMin());
    }

    @Test(timeout=SECOND)
    public void testInsertWithNegatives() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(-17);
        heap.insert(9);
        heap.insert(-30);
        heap.insert(2);
        heap.insert(49);
        assertEquals(-30, heap.peekMin());
    }

    @Test(timeout=SECOND)
    public void testInsertWithLettersUpdatesSize() {
        IPriorityQueue<String> heap = this.makeInstance();
        heap.insert("a");
        heap.insert("b");
        heap.insert("c");
        assertEquals(3, heap.size());
    }

    @Test(timeout=SECOND)
    public void testRemoveMinWithLetters() {
        IPriorityQueue<String> heap = this.makeInstance();
        heap.insert("t");
        heap.insert("f");
        heap.insert("z");
        heap.insert("a");
        heap.insert("j");
        assertEquals("a", heap.removeMin());
    }

    @Test(timeout=SECOND)
    public void testRemoveMinWithLettersReplacesMinCorrectly() {
        IPriorityQueue<String> heap = this.makeInstance();
        heap.insert("t");
        heap.insert("f");
        heap.insert("z");
        heap.insert("a");
        heap.insert("j");
        heap.removeMin();
        assertEquals("f", heap.peekMin());
    }
}
