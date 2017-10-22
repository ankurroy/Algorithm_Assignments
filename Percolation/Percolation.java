import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import java.lang.*;

public class Percolation {
   
   int dim;
   WeightedQuickUnionUF percolateUF;
   int[] openGrid;
   public Percolation(int n)                // create n-by-n grid, with all sites blocked
   {
       dim = n;
       percolateUF = new WeightedQuickUnionUF(n*n+2);
       openGrid = new int[n*n+2];
       for(int i=0; i<=n*n; i++){
           openGrid[i] = 0; // 0 indicates that the site is blocked
       }
       openGrid[0] = 1;
       openGrid[n*n+1] = 1;
       for(int i=1; i<=n; i++){
           percolateUF.union(0, i);
           percolateUF.union(n*n+1, n*n+1-i);
       }      
   }
   public void open(int row, int col)    // open site (row, col) if it is not open already
   {
       int pos = dim * (row - 1) + col;
       if(openGrid[pos] == 0){
           openGrid[pos] = 1;
       }
       if(row > 1){
           int top = pos - dim;
           if(percolateUF.connected(pos, top) == false &&
              isOpen(row-1, col)){
               percolateUF.union(pos, top);
           }
       }
       if(col > 1){
           int left = pos - 1;
           if(percolateUF.connected(pos, left) == false &&
              isOpen(row, col+1)){
               percolateUF.union(pos, left);
           }
       }
       if(row < dim){
           int bottom = pos + dim;
           if(percolateUF.connected(pos, bottom) == false &&
              isOpen(row+1, col)){
               percolateUF.union(pos, bottom);
           }
       }
       if(col < dim){
           int right = pos + 1;
           if(percolateUF.connected(pos, right) == false &&
              isOpen(row, col+1)){
               percolateUF.union(pos, right);
           }
       }
   }
   public boolean isOpen(int row, int col)  // is site (row, col) open?
   {
       return (openGrid[dim * (row - 1) + col] == 1);
   }
   public boolean isFull(int row, int col)  // is site (row, col) full?
   {
       int pos = dim * (row - 1) + col;
       return percolateUF.connected(pos, 0);
   }
   public int numberOfOpenSites()       // number of open sites
   {
       int count = 0;
       for(int i=1; i<dim*dim; i++){
           count += openGrid[i];
       }
       return count;
   }    
   public boolean percolates()              // does the system percolate?
   {
       return percolateUF.connected(0, dim*dim+1);
   }    
   //public static void main(String[] args)   // test client (optional)
}