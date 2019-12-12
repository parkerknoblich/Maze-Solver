package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IEdge;
import datastructures.interfaces.IList;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.ISet;
import datastructures.interfaces.IDisjointSet;
import datastructures.interfaces.IPriorityQueue;
import misc.Sorter;
import misc.exceptions.NoPathExistsException;

/**
 * Represents an undirected, weighted graph, possibly containing self-loops, parallel edges,
 * and unconnected components.
 * <p>
 * Note: This class is not meant to be a full-featured way of representing a graph.
 * We stick with supporting just a few, core set of operations needed for the
 * remainder of the project.
 */
public class Graph<V, E extends IEdge<V> & Comparable<E>> {
    private IDictionary<V, IList<E>> map;
    private IList<V> vertices;
    private IList<E> edges;

    /**
     * Constructs a new graph based on the given vertices and edges.
     *
     * @throws IllegalArgumentException if any of the edges have a negative weight
     * @throws IllegalArgumentException if one of the edges connects to a vertex not
     *                                  present in the 'vertices' list
     * @throws IllegalArgumentException if vertices or edges are null or contain null
     */
    public Graph(IList<V> vertices, IList<E> edges) {
        this.map = new ChainedHashDictionary<>();
        this.vertices = vertices;
        this.edges = edges;
        for (V vertex : vertices) {
            map.put(vertex, new DoubleLinkedList<>());
        }
        for (E edge : edges) {
            if (edge.getWeight() < 0 || !vertices.contains(edge.getVertex1())
                    || !vertices.contains(edge.getVertex2())) {
                throw new IllegalArgumentException();
            }
            map.get(edge.getVertex1()).add(edge);
            map.get(edge.getVertex2()).add(edge);
        }
    }

    /**
     * Sometimes, we store vertices and edges as sets instead of lists, so we
     * provide this extra constructor to make converting between the two more
     * convenient.
     *
     * @throws IllegalArgumentException if any of the edges have a negative weight
     * @throws IllegalArgumentException if one of the edges connects to a vertex not
     *                                  present in the 'vertices' list
     * @throws IllegalArgumentException if vertices or edges are null or contain null
     */
    public Graph(ISet<V> vertices, ISet<E> edges) {
        // You do not need to modify this method.
        this(setToList(vertices), setToList(edges));
    }

    // You shouldn't need to call this helper method -- it only needs to be used
    // in the constructor above.
    private static <T> IList<T> setToList(ISet<T> set) {
        if (set == null) {
            throw new IllegalArgumentException();
        }
        IList<T> output = new DoubleLinkedList<>();
        for (T item : set) {
            output.add(item);
        }
        return output;
    }

    /**
     * Returns the number of vertices contained within this graph.
     */
    public int numVertices() {
        return vertices.size();
    }

    /**
     * Returns the number of edges contained within this graph.
     */
    public int numEdges() {
        return edges.size();
    }

    /**
     * Returns the set of all edges that make up the minimum spanning tree of
     * this graph.
     * <p>
     * If there exists multiple valid MSTs, return any one of them.
     * <p>
     * Precondition: the graph does not contain any unconnected components.
     */
    public ISet<E> findMinimumSpanningTree() {
        ISet<E> minSpanTree = new ChainedHashSet<>();
        IDisjointSet<V> disjointSet = new ArrayDisjointSet<V>();
        for (KVPair<V, IList<E>> pair : map) {
            disjointSet.makeSet(pair.getKey());
        }
        IList<E> sorted = Sorter.topKSort(edges.size(), edges);
        for (E edge : sorted) {
            V vertex1 = edge.getVertex1();
            V vertex2 = edge.getVertex2();
            if (disjointSet.findSet(vertex1) != disjointSet.findSet(vertex2)) {
                minSpanTree.add(edge);
                disjointSet.union(vertex1, vertex2);
            }
        }
        return minSpanTree;
    }

    /**
     * Returns the edges that make up the shortest path from the start
     * to the end.
     * <p>
     * The first edge in the output list should be the edge leading out
     * of the starting node; the last edge in the output list should be
     * the edge connecting to the end node.
     * <p>
     * Return an empty list if the start and end vertices are the same.
     *
     * @throws NoPathExistsException    if there does not exist a path from the start to the end
     * @throws IllegalArgumentException if start or end is null
     */
    public IList<E> findShortestPathBetween(V start, V end) {
        IList<E> shortestPath = new DoubleLinkedList<>();
        if (end.equals(start)) {
            return shortestPath;
        }
        ISet<V> visitedNodes = new ChainedHashSet<>();
        IPriorityQueue<Path<V, E>> allPaths = new ArrayHeap<>();
        allPaths.insert(new Path<>(new DoubleLinkedList<>(), start, 0));
        while (!allPaths.isEmpty() && allPaths.peekMin().end != end) {
            Path<V, E> currentPath = allPaths.removeMin();
            if (!visitedNodes.contains(currentPath.end)) {
                IList<E> allEdges = map.get(currentPath.end);
                for (E edge : allEdges) {
                    if (!visitedNodes.contains(edge.getOtherVertex(currentPath.end))) {
                        IList<E> newPath = new DoubleLinkedList<>();
                        for (E edg : currentPath.path) {
                            newPath.add(edg);
                        }
                        newPath.add(edge);
                        Path<V, E> updatedPath = new Path<V, E>(newPath, edge.getOtherVertex(currentPath.end),
                                currentPath.weight + edge.getWeight());
                        allPaths.insert(updatedPath);
                    }
                }
                visitedNodes.add(currentPath.end);
            }
        }
        if (!allPaths.isEmpty()) {
            return allPaths.peekMin().getPath();
        } else {
            throw new NoPathExistsException();
        }
    }

    private static class Path<V, E> implements Comparable<Path<V, E>> {
        private IList<E> path;
        private V end;
        private Double weight;

        public Path(IList<E> path, V end, double weight) {
            this.path = path;
            this.end = end;
            this.weight = weight;
        }

        public IList<E> getPath() {
            return path;
        }

        public int compareTo(Path<V, E> other) {
            return this.weight.compareTo(other.weight);
        }
    }
}


