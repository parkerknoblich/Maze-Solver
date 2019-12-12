package datastructures.sorting;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;
import misc.BaseTest;
import misc.Sorter;
import org.junit.Test;

//import java.util.ArrayList;

//import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapAndSorterStress extends BaseTest {
    @Test(timeout=10*SECOND)
    public void testInsertStress() {
        IPriorityQueue<Integer> heap = new ArrayHeap<>();
        for (int i = 0; i < 500000; i++) {
            heap.insert(i);
        }
        assertEquals(0, heap.peekMin());
    }

    @Test(timeout=10*SECOND)
    public void testRemoveMinStress() {
        IPriorityQueue<Integer> heap = new ArrayHeap<>();
        for (int i = 0; i < 5000001; i++) {
            heap.insert(i);
        }
        for (int i = 0; i < 500000; i++) {
            heap.removeMin();
        }
        assertEquals(500000, heap.removeMin());
    }

    @Test(timeout=10*SECOND)
    public void testSorterStress() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 500000; i++) {
           list.add(i);
        }
        IList<Integer> top = Sorter.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(499995 + i, top.get(i));
        }
    }

}
