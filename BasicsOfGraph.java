import java.util.*;
public class BasicsOfGraph {
    public static class Edge{
        int src;
        int nbr;
        int wt;
    
        Edge(int src, int nbr, int wt){
            this.src=src;
            this.nbr=nbr;
            this.wt=wt;
        }
    }

    public static void addEdge(ArrayList<Edge>[]graph, int u, int v, int w){
        graph[u].add(new Edge(u,v,w));
        graph[v].add(new Edge(u,v,w));
    }

    public static void display(ArrayList<Edge>[]graph, int n){
        for(int i=0;i<n;i++){
            System.out.print(i+"->");
            for(Edge e: graph[i]){
                System.out.print("("+e.nbr+","+e.wt+")");
                
            }
            System.out.println();
        }
    }

    public static int findEdge(ArrayList<Edge>[]graph, int u, int v){
        ArrayList<Edge>list= graph[u];
        for(int i=0;i<list.size();i++){
            Edge e= list.get(i);
            if(e.nbr==v){
                return i;
            }
        }return -1;
    }

    public static void removeEdge(ArrayList<Edge>[]graph, int u, int v){
        int i1= findEdge(graph,u,v);
        int i2= findEdge(graph,v,u);
        graph[u].remove(i1);
        graph[v].remove(i2);
    }

    public static void removeVtx(ArrayList<Edge>[]graph, int u){
       ArrayList<Edge>list= graph[u];
       for(int i=list.size()-1;i>=0;i--){
           Edge e= list.get(i);
           removeEdge(graph,e.src,e.nbr);
       } 
    }

    public static boolean hasPath(ArrayList<Edge>[]graph, int src, int dest,boolean[]vis){
        if(src==dest)
           return true;
        vis[src]=true;
        boolean res=false;
        for(Edge e: graph[src]){
            if(!vis[e.nbr]){
                res=res|| hasPath(graph,e.nbr,dest,vis);
            }
        }
        return res;
    }

    public static int printAllPath(ArrayList<Edge>[]graph, int src, int dest,boolean[]vis, String psf){
        if(src==dest){
            System.out.println(psf+dest);
            return 1;
        }
        int count=0;
        vis[src]=true;
        for(Edge e: graph[src]){
            if(!vis[e.nbr]){
                count+=printAllPath(graph,e.nbr,dest,vis,psf+src);
            }
        }
        vis[src]=false;
        return count;
    }

    public static void preOrder(ArrayList<Edge>[]graph, int src,boolean[]vis, int wsf, String psf){
        System.out.println(src+"->"+psf+"@"+wsf);
        vis[src]=true;
        for(Edge e: graph[src]){
            if(!vis[e.nbr]){
               preOrder(graph,e.nbr,vis,wsf+e.wt,psf+e.nbr);
            }
        }
    }

    public static void postOrder(ArrayList<Edge>[]graph, int src,boolean[]vis,int wsf, String psf){
        vis[src]=true;
        for(Edge e: graph[src]){
            if(!vis[e.nbr]){
               postOrder(graph,e.nbr,vis,wsf+e.wt,psf+e.nbr);
            }
        }
        System.out.println(src+"->"+psf+"@"+wsf);
    }
    public static class PathPair{
        String psf="";
        int wsf=-1;
    }

    public static PathPair heaviestPath_(ArrayList<Edge>[]graph, int src, int dest, boolean[]vis){
        if(src==dest){
            PathPair base= new PathPair();
            base.psf+=src;
            base.wsf=0;
        }
        vis[src]=true;
        PathPair myAns= new PathPair(); 
        for(Edge e: graph[src]){
            if(!vis[e.nbr]){
               PathPair recAns=heaviestPath_(graph,e.nbr,dest,vis);
               if(recAns.wsf+e.wt>myAns.wsf){
                   myAns.wsf=recAns.wsf+e.wt;
                   myAns.psf=src+myAns.psf;
               }
            }
        }
        vis[src]=false;
        return myAns;
    }

    public static void heaviestPath(ArrayList<Edge>[] graph, int src, int dest){
        boolean[]vis= new boolean[graph.length];
        PathPair ans= heaviestPath_(graph,src,dest,vis);
        System.out.println("HeaviestPath:"+ans.psf+"HeaviestWt:"+ans.wsf);
    }

    public static class ceilFloorPair{
        int ceil= (int)1e9;
        int floor= -(int)1e9;
    }
    
    public static void ceilAndFloor(ArrayList<Edge>[]graph, int src, int data, boolean[] vis, int wsf, ceilFloorPair pair){
     if(wsf<data){
        pair.floor= Math.max(pair.floor,wsf);
     }
     if(wsf>data){
         pair.ceil= Math.min(pair.ceil,wsf);
     }
     vis[src]=true;
     for(Edge e: graph[src]){
         if(!vis[e.nbr]){
             ceilAndFloor(graph,e.nbr,data,vis,wsf+e.wt,pair);
         }
     }
     vis[src]=false;
    }

    public static void ceilAndFloor(ArrayList<Edge>[]graph, int src, int data){
        ceilFloorPair pair= new ceilFloorPair();
        boolean[] vis= new boolean[graph.length];
        ceilAndFloor(graph,src,data,vis,0,pair);
    }

    public static void dfs_GCC(ArrayList<Edge>[]graph, int src, boolean[]vis){
        vis[src]=true;
        for(Edge e: graph[src]){
            if(!vis[e.nbr]){
                dfs_GCC(graph,e.nbr,vis);
            }
        }
    }

    public static void GCC(ArrayList<Edge>[]graph){
        int n= graph.length;
        boolean[]vis= new boolean[n];
        int CountComponents=0;
        for(int i=0;i<n;i++){
            if(!vis[i]){
                dfs_GCC(graph,i,vis);
                CountComponents++;
            }
           
        }
        System.out.println(CountComponents);
    }

    public static void dfs(char[][]grid,int[][]dir, int sr, int sc){
        grid[sr][sc]='0';
        for(int d=0;d<4;d++){
            int r= sr+dir[d][0];
            int c= sc+dir[d][1];
            if(r>=0 && c>=0 && r<grid.length && c<grid[0].length && grid[r][c]=='1'){
                  dfs(grid,dir,r,c);
            }
        }
    }

    public static int NumberOfIslands(char[][]grid){
        int n= grid.length;
        int m= grid[0].length;
        int[][] dir= {{1,0},{-1,0},{0,-1},{0,1}};
        int islands=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]=='1'){
                    dfs(grid,dir,i,j);
                        islands++;
                }
            }
        }
        return islands;
    }

    public static void hamiltonianPathCycle(ArrayList<Edge>[]graph, int osrc, int src, int EdgeCount, boolean[]vis, String ans){
        if(EdgeCount==graph.length-1){
            int idx= findEdge(graph, src, osrc);
            if(idx==-1){
                System.out.println(ans+"*");
            }else{
                System.out.println(ans+".");
            }
        }
        vis[src]= true;
        for(Edge e: graph[src]){
            if(!vis[e.nbr]){
                hamiltonianPathCycle(graph, osrc,e.nbr,EdgeCount+1,vis,ans+e.nbr);
            }
        }
        vis[src]=false;
    }

    public static void hamiltonianPathCycle(ArrayList<Edge>[]graph, int src){
        int n= graph.length;
        boolean[]vis= new boolean[n];
        hamiltonianPathCycle(graph, src, src, 0, vis, ""+src);
    }

    public static void BFS(ArrayList<Edge>[]graph, int src, int dest){
        LinkedList<Integer>qu= new LinkedList<>();
        qu.addLast(src);
        int n= graph.length;
        boolean[]vis= new boolean[n];
        int level=0;
        int shortestpath=-1;
        boolean isCyclePresent=false;
        while(qu.size()!=0){
            int size= qu.size();
            while(size-->0){
                int rvtx= qu.removeFirst();
                //for cycle
                if(vis[rvtx]){
                    isCyclePresent=true;
                    continue;
                }
                //shortestPath
                if(rvtx==dest){
                    shortestpath=level;
                }

                vis[rvtx]=true;
                for(Edge e: graph[src]){
                    if(!vis[e.nbr]){
                        qu.add(e.nbr);
                    }
                } 
                // vis[rvtx]=false;
            }
            level++;
        }
    }

    public static boolean cycleDetection(ArrayList<Edge>[]graph, int src, boolean[]vis){
        LinkedList<Integer>qu= new LinkedList<>();
        qu.addLast(src);
        while(qu.size()!=0){
            int size= qu.size();
            while(size-->0){
                int rvtx= qu.removeFirst();
                if(vis[rvtx]){
                    return true;
                }
                vis[rvtx]=true;
                for(Edge e: graph[src]){
                    if(!vis[e.nbr]){
                        qu.addLast(e.nbr);
                    }
                }
            }
        }
        return false;
    }

    public static boolean cycleDetection(ArrayList<Edge>[]graph){
        int n= graph.length;
        boolean[]vis= new boolean[n];
        boolean res=false;
        for(int i=0;i<n;i++){
            if(!vis[i]){
               res=res|| cycleDetection(graph,i,vis);
            }
        }
        return res;
    }

    public static class BFS_Pair{
      int vtx;
      String psf;
      int wsf;

      BFS_Pair(int vtx, String psf, int wsf){
          this.vtx=vtx;
          this.psf=psf;
          this.wsf=wsf;
      }
    }

    public static void printBFSPath(ArrayList<Edge>[]graph ){
        int vtces= graph.length;
        boolean[]vis= new boolean[vtces];
        for(int i=0;i<vtces;i++){
            if(!vis[i]){
                continue;
            }
            LinkedList<BFS_Pair>qu= new LinkedList<>();
            qu.addLast(new BFS_Pair(i,i+"",0));
            while(qu.size()!=0){
                int size= qu.size();
                while(size-->0){
                    BFS_Pair rp= qu.removeFirst();
                    if(vis[rp.vtx])
                       continue;
                    System.out.println(rp.vtx+"->"+rp.psf+"@"+rp.wsf);
                    vis[rp.vtx]=true;
                    for(Edge e: graph[rp.vtx]){
                         if(!vis[e.nbr]){
                             qu.addLast(new BFS_Pair(e.nbr,rp.psf+e.nbr,rp.wsf+e.wt));
                         }
                    }
                }
            }

        }
    }

    public static int spreadInfection(ArrayList<Edge>[]graph, int infectedPerson, int noOfDays){
      LinkedList<Integer>qu= new LinkedList<>();
      qu.addLast(infectedPerson);
      boolean[]vis= new boolean[graph.length];
      int infectedCount=0,days=1;
      while(qu.size()!=0){
          int size= qu.size();
          while(size-->0){
              int rp= qu.removeFirst();
              if(vis[rp])
                 continue;
              vis[rp]=true;
              infectedCount++;
              for(Edge e: graph[rp]){
                  if(!vis[e.nbr]){
                      qu.addLast(e.nbr);
                  }
              }
          }
          days++;
          if(days>noOfDays)
             break;
      } 
      return infectedCount;
    }

    public static boolean bipartite(ArrayList<Edge>[]graph, int src, int[] vis){
        LinkedList<Integer>qu= new LinkedList<>();
        qu.addFirst(src);
        int color=0;
        boolean isBipartite=true;
        boolean cycle=false;
        while(qu.size()!=0){
            int size=qu.size();
            while(size-->0){
                int rvtx= qu.removeFirst();
                if(vis[rvtx]!=-1){
                    cycle=true;
                    if(vis[rvtx]!=color){
                        isBipartite=false;
                        break;
                    }else{
                        continue;
                    }
                }
                vis[rvtx]=color;
                for(Edge e: graph[rvtx]){
                    if(vis[e.nbr]==-1){
                        qu.addLast(e.nbr);
                    }
                }
            }
            color=(color+1)%2;
        }
        if(cycle){
            if(isBipartite){
                System.out.println("Even Length Cycle");
            }else{
                System.out.println("Odd Length Cycle");
            }
        }else{
            System.out.println("Acyclic and Bipartite");
        }
        return isBipartite;
    }

    public static void bipartite(ArrayList<Edge>[]graph){
      int n= graph.length;
      int[] vis= new int[n];
      Arrays.fill(vis,-1);
      boolean isBipartite=true;
      for(int i=0;i<n;i++){
          if(vis[i]==-1)
              isBipartite= isBipartite && bipartite(graph,i,vis);
      }
      System.out.println(isBipartite);
    }

    public static void construction(){
        int N=7;
        ArrayList<Edge>[]graph= new ArrayList[N];
        for(int i=0;i<N;i++){
            graph[i]= new ArrayList<>();
        }
        addEdge(graph,0,1,10);
        addEdge(graph,0,3,10);
        addEdge(graph,1,2,10);
        addEdge(graph,2,3,40);
        addEdge(graph,3,4,2);
        addEdge(graph,4,5,2);
        addEdge(graph,4,6,8);
        addEdge(graph,5,6,3);

        boolean[] vis= new boolean[N];
        // preOrder(graph,0,vis,0,"0");
        // heaviestPath(graph,0,6);
        printBFSPath(graph);
    }
}
