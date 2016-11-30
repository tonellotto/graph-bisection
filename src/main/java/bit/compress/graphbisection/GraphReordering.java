package bit.compress.graphbisection;

import java.util.HashMap;
import java.util.Map;
/**
 * An implementation for the compression friendly graph reordering using graph bisection
 * <p>
 * 
 * {@link} http://arxiv.org/pdf/1602.08820
 * @see Dhulipala, Laxman, et al. "Compressing Graphs and Indexes with Recursive Graph Bisection." arXiv preprint arXiv:1602.08820 (2016).
 * 
 * @author bit-whacker
 *
 */
public class GraphReordering {
  /**
   * graph data
   * <p>
   * 
   * holds graph data e.g. v1, v2, v3 ... vn
   */
  private int[] D;
  
  /**
   * vertex gains
   * <p>
   * 
   * holds the gain value of each of the vertex in D
   */
  private Map<Integer, Double> gains;
  
  /**
   * adjacent vertex
   * <p>
   * 
   * holds adjacency list for each q e.g. the key is the q and the value int[] array holds the adjacent vertices of q
   */
  private Map<Integer, Integer[]> adjacencyList;
  
  
  /**
   * Instantiates GraphReordering with Graph D and adjacency list adjacencyList
   * <p>
   * 
   * @param D
   * @param adjacencyList
   */
  public GraphReordering(int[] D, Map<Integer, Integer[]> adjacencyList) {
    gains = new HashMap<Integer, Double>();
    this.adjacencyList = adjacencyList;
    this.D = D;
    
    int s=0, e=(D.length-1);
    
    reorder(s, e);
  }
  
  /**
   * starts re-ordering vertices in the graph D, compression friendly re-ordering of the graph D
   * <p>
   * 
   * @param s
   * @param e
   */
  public void reorder(int s, int e) {
    if(s < e){
      int m = s + (e - s) / 2;
      bisection(s, m, e);
      reorder(s, m);
    }
  }
  
  /**
   * graph bisector
   * <p>
   * 
   * @param s
   * @param m
   * @param e
   */
  public void bisection(int s, int m, int e) {
    for (int i = s; i <= e; i++) {
      gains.put(D[i], computeMoveGain(D[i]));
    }
    //. sort v1, and v2 in desc order by gain
    sortByGainDesc(s, m, e);
    int v = s, u = m+1;
    while(v<=m && u<=e){
      if((gains.get(D[v]) + gains.get(D[u])) > 0){
        swap(v, u);
      }
      ++v;++u;
    }
  }
  
  /**
   * sorts left list v1, and right list v2 in DESC order by gain
   * <p>
   * 
   * @param s
   * @param m
   * @param e
   */
  public void sortByGainDesc(int s, int m, int e) {
    insertionSort(s, m);
    insertionSort(m+1, e);
  }
  
  /**
   * insertion sort, to sort the list in desc order by gain
   * <p>
   * 
   * @param s
   * @param e
   */
  public void insertionSort(int s, int e) {
    for (int j = s; j <= e; j++) {
      int key = D[j];
      int i = j - 1;
      while ((i > -1) && (gains.get(D[i]) < gains.get(key))) {
        D[i + 1] = D[i];
        i--;
      }
      D[i + 1] = key;
    }
  }
  
  /**
   * swap v, and u
   * <p>
   * 
   * @param v
   * @param u
   */
  public void swap(int v, int u){
    int temp = D[v];
    D[v] = D[u];
    D[u] = temp;
  }
  
  /**
   * computes move gain of the vertex v, but don't know how :(
   * <p>
   * 
   * @param v
   * @return double gain
   */
  public double computeMoveGain(int v){
    // TODO need to know how to compute move gain( if any one have idea plz share)
    
    return 0.0;
  }
}
