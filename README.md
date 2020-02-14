# CSE 373: Data Structures and Algorithms, HW7
In this homework, you will implement Kruskal's algorithm to generate mazes and Dijkstra's algorithm to solve them.

Part 1: Try running the maze generator
Task: make sure you can run MainWindow.java
   - Navigate to the mazes.gui package and run MainWindow.java. This will launch a program that will (eventually) generate and solve          mazes.
   - The GUI consists of two main regions. The large region at the top, which will initially contain your maze, and a few options and        buttons at the bottom.
   - Here is a brief explanation of the user interface:
        - The "Base maze shape" combobox controls what underlying "shape" your maze will have. The default is "Grid", which creates a             maze where each room is a rectangle.
        - Try switching the option to "Voronoi" and click the "Generate new maze" button to the left. This will generate a maze where             the rooms are more irregular.
        - The "Maze generator" combobox controls the actual maze generation process – it takes the initial set of room and removes edges           to generate a plausible maze.
        - The default option is "Do not delete any edges", which, as you may have guessed, does not delete any edges.
        - Now, try picking one of the "Delete random edges" options and generate a new maze. You should now see some of the edges                 removed and see something that more closely resembles a maze.
        - Unfortunately, the "randomly remove edges" strategy is not that great. If we remove 30% of the edges, the maze is too easy. If           we remove 50% of the edges, we often end up with an unsolvable maze. (The red dots in the upper-left and lower-right corners             are our starting and ending points).
        - The final option labeled "Run (randomized) Kruskal" is what you will eventually need to implement. It turns out we can use the           properties of minimum spanning trees to generate a much more interesting (and yet always solvable) maze!
   - The "Find shortest path" button, once implemented, will draw the shortest path between the two red dots. Clicking this button            should currently cause the program to crash – we haven't implemented it yet.
   - If you want to customize the different options (e.g. change the number of rows and columns in the grid maze, change the percentage      of edges removed), look at the MainWindow.launch method. Feel free to change any of the constants there -- we will not be looking        at this file when grading, so we don't care what changes you make.

Part 2: Implement ArrayDisjointSet
Task: complete the ArrayDisjointSet class.
Notes:
    - Be sure to use implement your disjoint set using the array-based representation we discussed in lecture. This includes making           the optimizations we've discussed, specifically path compression.

Part 3: Implement constructor and basic methods of Graph
Task: implement the Graph constructor and numVertices and numEdges methods.
Notes:
   - You can find this class inside the misc.graphs package.
   - Your constructor is currently designed to accept a list of vertices and a list of edges. However, you may find this somewhat            inconvenient to work with, especially once you start implementing the findShortestPathBetween(...) method. You will probably want        to add extra fields so that you can store the graph in a more useful representation (e.g. as an adjacency list or adjacency              matrix).
   - While you're working through this problem, there will likely be some design decisions that come up, such as whether to use an            adjacency list or matrix, using weighted or unweighted edges, or other small details. If you're confused about what to do here, it      might benefit you to read further down in the spec to understand what your graph will represent later on in the project.
   - This class uses generics in a slightly-more complicated way than you've seen in previous homeworks. We've tried to insulate you          from this as much as possible, but it's possible you may run into unexpected issues or have difficulty getting your code to compile      depending on what exactly you're doing.
   - You should make sure to look over the datastructures.IEdge class. Any objects of type E implement the IEdge methods. However, be        sure not modify this class.
   - You can find (fairly minimal) tests for this class in TestGraph. Again, you'll want to write more tests to ensure your code matches      your intentions.

Part 4: Implement Graph.findMinimumSpanningTree(...)
Task: implement the Graph.findMinimumSpanningTree(...)
Notes:
   - You should implement findMinimumSpanningTree(...) using Kruskal's algorithm.
   - You can use top topKSort(...) to sort your edges if you'd like.
   - If you do want to implement Kruskal's algorithm as efficiently as possible, feel free to implement a linear sort such as bucket          sort and use that instead. The only caveat is that your sorting algorithm needs to be a private helper method within your Graph          class. It doesn't really make sense for the sorting algorithm to live in a class about graphs, but we need to do this due to            limitations in our grading scripts. (They copy specific files from your homeworks, so if you add extra code in unexpected places,        they may not be copied over.) If you choose to do this, make sure that your implementation works on the runners, and specifically        that you pass the "compile-for-grading" job.

Part 5: Implement KruskalMazeCarver
Task: implement the KruskalMazeCarver.returnWallsToRemove(...) method
   - If you remember when we were running the maze program from before, the "remove random walls" algorithms ended up generating pretty      poor mazes. They either removed too many walls and created trivial mazes, or removed too few and created impossible ones.
   - What we really want is an algorithm that (a) generates a random-looking maze, (b) makes sure the maze is actually solvable, and (c)      removes as few walls as possible.
   - It turns out that we can use algorithms such as Prim and Kruskal to do exactly that!
   - Here's the trick: we take the maze, treat each room as a vertex and each wall as an edge, assign each wall a random weight, and run      any MST-finding algorithm. We then remove any wall that was a part of that MST.
   - This will end up satisfying all three criteria! By randomizing the wall weights, we remove random walls which satisfies criteria        (a). A MST-finding algorithm, by definition, will ensure there exists a path from every vertex (every room) to every other one,          satisfying criteria (b). And finally, because MST-finding algorithms try and avoid cycles, we avoid removing unnecessary edges and      end up with a maze where there really is only one solution, satisfying criteria (c).
   - Your task here is to implement this algorithm within the KruskalMazeCarver class.
   - Other notes:
        - You can find this class inside the mazes.generators.maze package.
        - Make sure you understand how to use the Maze and Wall objects. The Wall object is a subclass of IEdge, which means you should           be able to pass a list or set of Walls into your Graph constructor (along with the corresponding list of Rooms).
        - You may import and use java.util.Random while implementing this class.
        - If you're stuck, try taking a look at RandomMazeCarver, which is located within the same package. Your algorithm will be a               little more complicated, but it may serve as a good source of inspiration.
        - To test your method, try running the program and generate a few mazes after selecting the "Run (randomized) Kruskal" option.

Part 6: Implement Graph.findShortestPathBetween(...)
Task: implement the Graph.findShortestPathBetween(...) method
   - Finally, you will implement the code to actually solve the mazes.
   - Notes:
        - You should implement findShortestPathBetween(...) using Dijkstra's algorithm.
        - To represent infinity, use the Double.POSITIVE_INFINITY constant.
        - While we definitely do encourage you to look at the pseudocode we provided in lecture, please be sure to take them with a               grain of salt.
        - While we tried to write the pseudocode in a way that stuck a good balance between giving you a high-level overview and making           details clear, it's possible that we accidentally omitted a few details. The pseudocode is meant to be a guide, not an                   instructional manual, so include other details as you see fit.
        - In addition, you may find it convenient to arrange your code in a slightly different way than what the pseudocode suggests,             especially since you're trying to solve a slightly different problem than what the lecture slides are solving: The version of           Dijkstra's algorithm we provided in lecture is trying to find the shortest paths from the start to every other node; you're             trying to find the shortest path from the start to a specific node.
        - This doesn't mean you'll be rewriting Dijkstra's, but you should be aware that like the pseudocode we provided for PageRank,             you might need to change things around to better fit your own needs in this project.
        - One challenge you may run into is figuring out how exactly to update the costs of the vertices. Instead of trying to update             costs, you should add the duplicate elements into your heap, and account for this separately.
        - Rather than inserting your vertices directly into your heap, you may want to create and insert a custom inner class instead.             (Why do you suppose that is?)
        - Once you're done and all your tests are passing, try re-running the program and click the "Find shortest path" button. If               everything went well, the program should now draw a red line connecting the start and the end!
          (Or, if there is no valid path, display an alert box stating so.)
