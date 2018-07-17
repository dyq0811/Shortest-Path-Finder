/**
 * The class that holds the node.
 * @author Yingqi Ding, 2018-04-xx
 */

public class Vertex
{
   private final int vertexID;
   private final String name;
   private boolean visited;
   private int distance;

   public Vertex(int num, String str)
   {
      vertexID = num;
      name = str;
      distance = 9999;
      visited = false;
   }

   public int getVertexID()
   {
      return vertexID;
   }

   public boolean isVisited()
   {
      return visited;
   }

   public int getDistance()
   {
      return distance;
   }

   public void setVisited(boolean input)
   {
      visited = input;
   }

   public void setDistance(int dis)
   {
      distance = dis;
   }

   public int compareDistance(Vertex a, Vertex b)
   {
      return (a.getDistance() - b.getDistance());
   }


}