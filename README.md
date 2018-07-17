# ShortestPathFinder

You may have played a game where starting from some Wikipedia page, you try to get to another Wikipedia page in as few pages as possible (there's even a Wikipedia page about the idea). You can think about the web as a directed graph, where a link from one page to another corresponds to an edge. The pages and links between pages in Wikipedia correspond to a subgraph of the graph of the entire web, and the goal of this project is to find a shortest path between two vertices in that subgraph.


java PathFinder vertexFile edgeFile


vertexFile is the file that has the vertex names for your graph and edgeFile is the file that has the edges for your graph. PathFinder will load the data, and then choose two random vertices, a start vertex and an end vertex. The program will find a shortest path from the start vertex to the end vertex and then display the length of the path found and the complete path.

For example, if the articles randomly chosen were Pythagorean_theorem and Pride_and_Prejudice, the program might output:

Shortest path from Pythagorean_theorem to Pride_and_Prejudice, length = 4
Pythagorean_theorem --> British_Isles --> United_Kingdom --> Jane_Austen --> Pride_and_Prejudice
