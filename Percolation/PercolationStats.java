import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.lang.*;

public class PercolationStats {
   double[] percolationTrialResults;
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
       percolationTrialResults = new double[trials];
       for(int i=0; i<trials; i++){
           Percolation percolationTrial = new Percolation(n);
           while(percolationTrial.percolates() == false)
           {
               int x = StdRandom.uniform(n) + 1;
               int y = StdRandom.uniform(n) + 1;
               if(percolationTrial.isOpen(x, y) == false)
               {
                   percolationTrial.open(x, y);
               }
           }
           percolationTrialResults[i] =
               (double) percolationTrial.numberOfOpenSites() / (double) (n*n);
       }
       
   }
   public double mean()                          // sample mean of percolation threshold
   {
       return StdStats.mean(percolationTrialResults);
   }
   public double stddev()                        // sample standard deviation of percolation threshold
   {
       return StdStats.stddev(percolationTrialResults);
   }
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
       return mean() - 2 * stddev();
   }
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {
       return mean() + 2 * stddev();
   }
   public static void main(String[] args)        // test client (described below)
   {
       PercolationStats percolationTest = new
           PercolationStats(Integer.parseInt(args[0]),
                            Integer.parseInt(args[1]));
       StdOut.println("mean                    = "
                          + Double.toString(percolationTest.mean()));
       StdOut.println("stddev                  = "
                          + Double.toString(percolationTest.stddev()));
       StdOut.println("95% confidence interval = ["
                          + Double.toString(percolationTest.confidenceLo())
                          + ", "
                          + Double.toString(percolationTest.confidenceHi())
                          + "]");
   }
}