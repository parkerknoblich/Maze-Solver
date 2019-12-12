package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Sorter;
import org.junit.Assert;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSorterFunctionality extends BaseTest {
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Sorter.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }

    @Test(timeout=SECOND)
    public void testKLessThanZeroThrowsException() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(1);
        try {
            IList<Integer> top = Sorter.topKSort(-1, list);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // All good!
        }
    }

    @Test(timeout=SECOND)
    public void testNullListThrowsException() {
        IList<Integer> list = new DoubleLinkedList<>();
        try {
            IList<Integer> top = Sorter.topKSort(5, null);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // All good!
        }
    }

    @Test(timeout=SECOND)
    public void testSimpleUsage2() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(7);
        list.add(22);
        list.add(4);
        list.add(62);
        list.add(39);
        list.add(100);
        list.add(9);
        list.add(10);
        list.add(80);
        IList<Integer> top = Sorter.topKSort(4, list);
        assertEquals(4, top.size());
        assertEquals(39, top.get(0));
        assertEquals(62, top.get(1));
        assertEquals(80, top.get(2));
        assertEquals(100, top.get(3));
    }

    @Test(timeout=SECOND)
    public void testSimpleUsageLarge() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 500; i++) {
            list.add(i);
        }

        IList<Integer> top = Sorter.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(495 + i, top.get(i));
        }
    }

    @Test(timeout=SECOND)
    public void testKGreaterThanListSizeSmall() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(7);
        list.add(22);
        list.add(4);
        list.add(62);
        list.add(39);
        list.add(100);
        list.add(9);
        list.add(10);
        list.add(80);
        IList<Integer> top = Sorter.topKSort(10, list);
        assertEquals(list.size(), top.size());
        assertEquals(4, top.get(0));
        assertEquals(7, top.get(1));
        assertEquals(9, top.get(2));
        assertEquals(10, top.get(3));
        assertEquals(22, top.get(4));
        assertEquals(39, top.get(5));
        assertEquals(62, top.get(6));
        assertEquals(80, top.get(7));
        assertEquals(100, top.get(8));
    }

    @Test(timeout=SECOND)
    public void testKGreaterThanListSizeLarge() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 500; i++) {
            list.add(i);
        }
        IList<Integer> top = Sorter.topKSort(501, list);
        assertEquals(list.size(), top.size());
        for (int i = 0; i < 500; i++) {
            assertEquals(i, top.get(i));
        }
    }


    @Test(timeout=SECOND)
    public void testKEqualsZero() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 500; i++) {
            list.add(i);
        }
        IList<Integer> top = Sorter.topKSort(0, list);
        assertEquals(0, top.size());
    }

    @Test(timeout=SECOND)
    public void testKEqualToListSizeLarge() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 500; i++) {
            list.add(i);
        }
        IList<Integer> top = Sorter.topKSort(500, list);
        assertEquals(list.size(), top.size());
        for (int i = 0; i < 500; i++) {
            assertEquals(i, top.get(i));
        }
    }


}
