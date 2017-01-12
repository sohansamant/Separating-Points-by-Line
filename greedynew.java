
package cs430greedy;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class greedynew 
{
    static double threshold; // global variables
    static int index;
    static double[] finald;
    static List<Integer> done_d;
    static int d;
    static List<Double> list_of_lines_x;      
    static List<Double> list_of_lines_y;
    static double x_array[];
    static double y_array[];
    static boolean flag;
    static int size;

    public static void main(String[] args) throws IOException 
    {
    
    String token1 = "";

    File folder = new File(".\\input");
   
    for(File f : folder.listFiles())
    {

        Scanner inFile1 = new Scanner(f);
        List<String> temps = new ArrayList<String>();

        // loop to read each line in a file 
    while (inFile1.hasNext()) 
    {
      token1 = inFile1.next();
      temps.add(token1);
    }
    
    String[] tempsArray = temps.toArray(new String[0]); // input from the file
    size = Integer.parseInt(temps.get(0));
    
    int k=1; // counter to increment the 2D array 
    d = 0;
    x_array=new double[size]; 
    y_array=new double[size];
    double[][] a2 = new double[size][2];
    done_d = new ArrayList<Integer>();
    for(int i=0;i<size;i++)     // creating 2D array from 1D array 
    {
      for(int j=0;j<2;j++)
      {
        a2[i][j] = Double.parseDouble(tempsArray[k]);
        k++;
      }
    }
   
    int jj=0;
    
        for(int i=0;i<size;i++)        //coping the values from 2D arrays into 1 x_array;
        {
            x_array[i]=a2[i][jj];
        }
               /* Putting y only in its y_array matrix*/
        int j=1;
        for(int i=0;i<size;i++)        //coping the values from 2D arrays into 1 y_array;
        {
            y_array[i]=a2[i][j];
        }
        
    // Distance between the points
    
    double dist_x[]=new double[size-1];
    double dist_y[]=new double[size-1];
    finald = new double[size-1];
    list_of_lines_x = new ArrayList<Double>();
    list_of_lines_y = new ArrayList<Double>();
    finald = new double[size-1];
    int s=0; // counter for distance
    for(int i=0;i<size-1;i++)
    {
         dist_x[s]= Math.abs(a2[i][0]-a2[i+1][0]);        
         dist_y[s] = Math.abs(a2[i][1]-a2[i+1][1]);
         s++;
    }

    for(int i=0;i<s;i++) // calculate distance between all points and store in an array 
    {
        finald[i] = Math.sqrt((dist_x[i]*dist_x[i])+(dist_y[i]*dist_y[i]));
    }

    boolean h = false; // To toggle between horizontal and vertical lines
    double array_max=0;
     for(int r=0;r<x_array.length;r++) // to calculate maximum distance for threshold stopping condition
    {
        if(x_array[r]>array_max)
        {
            array_max=x_array[r];  
        }
    }// Initial value of threshold is 20% * difference between maximum and minimum co-ordinate
    threshold = (0.2*(array_max-0)); 

    ArrayList<Integer> sepx = new ArrayList<Integer>(size); // Counter to check lines between all x points
    ArrayList<Integer> sepy = new ArrayList<Integer>(size); // Counter to check lines between all y points
    for(int i=0;i<size;i++)
    {
      sepx.add(0);
      sepy.add(0);
    }

    // loop to plot a line based on distance and threshold value 
    
    while(threshold<array_max && (sepx.contains(0)) && (sepy.contains(0)))
    {
      double line;
      th(); // calls function to check threshold
      threshold++;
      if(h!=true) // for vertical lines
      {
        line = ((a2[index][0]+a2[index+1][0])/2);
        sepx.set(index,1);
        sepx.set(index+1,1);
        h=true;
        list_of_lines_x.add(line); 
      }
      else // for horizontal lines
      {
        line = ((a2[index][1]+a2[index+1][1])/2);
        h=false;
        for(int i=0;i<y_array.length;i++) // add 0.5 so the line does not go through the point
        {
          if(line == y_array[i])
             line = line +0.5;
        }
        sepy.set(index,1);
        sepy.set(index+1,1);
        list_of_lines_y.add(line);
      }
      done_d.add(index); // To put processed indexes in one array
    }   
    
    String newFileName="greedy_solution_"+f.getName();
    
    newFileName= newFileName.replace("_instance", "");
	
    File  output_file = new File(".\\output_greedy\\"+newFileName);
    
    if (!output_file.exists()) 
    {
        output_file.createNewFile();
    }
    
        FileWriter fw = new FileWriter(output_file.getAbsoluteFile());

        PrintWriter pw=new PrintWriter(fw);
        
        String total_lines=""+(list_of_lines_x.size()+list_of_lines_y.size());
        
        pw.println(total_lines);
    
    for(int i=0;i<list_of_lines_x.size();i++)
    {
      pw.println("v "+list_of_lines_x.get(i));
    }
    
    for(int i=0;i<list_of_lines_y.size();i++)
    {
     
        pw.println("h "+list_of_lines_y.get(i));
    }
    
    pw.close();
    inFile1.close();
    }
    System.out.println("Successful. Please check the greedy output folder");
} // main ends here

  static void th()
  {
    // to check if distance between 2 points is less than threshold
    for(int r=0;r<size-1;r++)
    {
      if(finald[r]<threshold && !(done_d.contains(r))) // to check the index was already processed
      {
        index = r;
      }
    }
  }
}