### Design: 2/3

### Documentation & Specification (including JavaDoc): 3/3

### Code quality (code and internal comments including RI/AF when appropriate): 2/3

### Testing (test suite quality & implementation): 3/3

### Mechanics: 3/3

#### Overall Feedback
Well done! Take a look at the feedback below. :-)

#### More Details
- Your `CampusMap` is an ADT, so it must have a RI, AF, and checkRep().
- The graph building routine should be factored out into its own method. Otherwise, you cannot test the graph building on arbitrary data files or some mock parser.
- Because we have applications for the graph ADT that do not deal with comparable data, do not require the type parameters of graph to extend Comparable. Instead, use Comparable data types or define a Comparator where appropriate at the application of the graph.

- Just like with the BFS implementation from MarvelPaths, Dijkstra's algorithm probably ought to have internal comments to improve readability.
- You implemented your `Dijkstra` class as an ADT and should have an AF/RI.
    - This also makes it so that you have to externally modify the graph which implicitly updates the `Dijkstra`'s graph.
    - Consider making `Dijkstra` a static 'utility' class.
