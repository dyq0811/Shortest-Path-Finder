/**
 * The class that holds the path finder.
 * @author Yingqi Ding, 2018-04-xx
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NullPointerException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Iterator;
import java.util.Random;

public class PathFinder
{
   private UnweightedGraph wikiGraph;
   private Map<String, Integer> articleToVertex;
   private Map<Integer, String> vertexToArticle;

   public PathFinder(String vertexFile, String edgeFile)
   {
      wikiGraph = new CarlUnweightedGraph(true);
      articleToVertex = new HashMap<String, Integer>();
      vertexToArticle = new HashMap<Integer, String>();
      //load the files and construct the graph
      load(vertexFile, edgeFile);
   }

   private void load(String vertexFile, String edgeFile)
   {
      Scanner scanner = null;
      //load the vertex file
      try
      {
         scanner = new Scanner(new File(vertexFile));
         while (scanner.hasNextLine())
         {
            String articleName = scanner.nextLine();
            if (!(articleName.length() <= 1) && !articleName.contains("#"))
            {
               String line = java.net.URLDecoder.decode(articleName, "UTF-8");
               int vertexID = wikiGraph.addVertex();
               articleToVertex.put(line, vertexID);
               vertexToArticle.put(vertexID, line);
            }
         }
      }
      catch (FileNotFoundException e0)
      {
         System.err.println("The vertex file was not found.");
      }
      catch (UnsupportedEncodingException e1)
      {
         System.err.println("Oh, the encoding is not supported.");
      }

      //Load the edge file
      try
      {
         scanner = new Scanner(new File(edgeFile));
         while (scanner.hasNextLine())
         {
            String links = scanner.nextLine();
            if (links.isEmpty())
            {
               links = scanner.nextLine();
            }
            if (!(links.length() <= 1) && !links.contains("#"))
            {
               String line = java.net.URLDecoder.decode(links, "UTF-8");
               String[] lineArray = line.split("\t");
               int firstID = articleToVertex.get(lineArray[0]);
               int secondID = articleToVertex.get(lineArray[1]);
               wikiGraph.addEdge(firstID, secondID);
            }
         }
      }
      catch (FileNotFoundException e0)
      {
         System.err.println("The edge file was not found.");
      }
      catch (UnsupportedEncodingException e1)
      {
         System.err.println("Oh, the encoding is not supported.");
      }
      catch (ArrayIndexOutOfBoundsException e2)
      {
         System.err.println("Empty graph...No path exists.");
      }
      scanner.close();
   }

   private int getRandomID()
   {
      int total = wikiGraph.numVerts();
      Random creator = new Random();
      int randomID = creator.nextInt(total);
      return randomID;
   }

   public int getShortestPathLength(String vertex1, String vertex2)
   {
      //If the size of the list is 0, -1 will be returned.
      if (getShortestPath(vertex1, vertex2).size() == 0)
      {
         return -1;
      }
      return (getShortestPath(vertex1, vertex2).size() - 1);
   }

   public List<String> getShortestPath(String vertex1, String vertex2)
   {
      int startID = articleToVertex.get(vertex1);
      int endID = articleToVertex.get(vertex2);

      List<String> shortestPathList = new ArrayList<String>();
      Map<Integer, Integer> previousOne = new HashMap<Integer,Integer>();
      previousOne.put(startID, -1);

      List<Integer> visitOrder = new ArrayList<Integer>();
      Queue<Integer> vertexQueue = new ArrayDeque<Integer>();
      vertexQueue.add(startID);
      visitOrder.add(startID);

      Boolean finish = false;

      if (startID == endID)
      {
         shortestPathList.add(vertex1);
         return shortestPathList;
      }

      //utilize breadth-first to find the shortest path
      while(!vertexQueue.isEmpty() && !finish)
      {
         int frontVertex = vertexQueue.remove();
         for (int v: wikiGraph.getNeighbors(frontVertex))
         {
            if (!visitOrder.contains(v))
            {
               visitOrder.add(v);
               vertexQueue.add(v);
               previousOne.put(v, frontVertex);
            }
            if (v == endID)
            {
               finish = true;
            }
         }
      }

      if (finish)
      {
         String endArticle = vertexToArticle.get(endID);
         shortestPathList.add(endArticle);
         int currentID = endID;
         while (previousOne.get(currentID) != -1)
         {
            int previousID = previousOne.get(currentID);
            String previousArticle = vertexToArticle.get(previousID);
            shortestPathList.add(0, previousArticle);
            currentID = previousID;
         }
         return shortestPathList;
      }
      //if ther is no solution, return an empty list
      return shortestPathList;
   }

   public void showRandomPath()
   {
      int v1 = getRandomID();
      int v2 = getRandomID();
      String article1 = vertexToArticle.get(v1);
      String article2 = vertexToArticle.get(v2);

      int pathLength = getShortestPathLength(article1, article2);
      List<String> articleList = getShortestPath(article1, article2);
      System.out.println("Shortest path from " + article1 + " to "
         + article2 + ", length = " + pathLength + ".");
      if (pathLength >= 1)
      {
         for (String article: articleList)
         {
            if (!article.equals(articleList.get(0)))
            {
               System.out.print(" --> ");
            }
            System.out.print(article);
         }
         System.out.println();
      }
      else if (pathLength == -1)
      {
         System.out.println("No path was found from " + article1 + " to " + article2 + ".");
      }
      else if (pathLength == 0)
      {
         System.out.println("Oh, the article goes back to itself.");
      }
   }

   public static void main(String[] args)
   {
      if (args.length == 2)
      {
         String vertexFile = args[0];
         String edgeFile = args[1];
         PathFinder findPath = new PathFinder(vertexFile, edgeFile);
         findPath.showRandomPath();
         findPath.showRandomPath();
       }
       else
       {
         System.out.println("The command line argument is incorrect, please check.");
         System.exit(1);
       }
   }
}