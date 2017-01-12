package cs430greedy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set; 

public class LocalAlgo 
{
    public static void main(String[] args) throws FileNotFoundException, IOException
    { 
        String token1 = "";
        int size_of_array;
        File folder = new File(".\\input"); // reading from the folder
     
    for(File f : folder.listFiles())
    {
        
         List<Double> list_of_lines_x=new ArrayList<Double>(); // list for storing the output
         List<Double> list_of_lines_y=new ArrayList<Double>();  // list for storing the output
         
         
         Scanner inFile1 = new Scanner(f); // reading the input
         List<String> temps = new ArrayList<String>(); 
          
        while (inFile1.hasNext()) 
        {
          token1 = inFile1.next();
          temps.add(token1);
        }

        String[] tempsArray = temps.toArray(new String[0]);
        String size = temps.get(0);
        
        size_of_array=Integer.parseInt(size);
        
        int [] x_array=new int[size_of_array];  // array for holding the values of x co-ordinates
        int [] y_array=new int[size_of_array];  // array for holding the values of y co-ordinates
        

        int k=1;
        int[][] a2 = new int[size_of_array][2];
        for(int i=0;i<size_of_array;i++)
        {
            for(int j=0;j<2;j++)
            {
                a2[i][j] = Integer.parseInt(tempsArray[k]);
                k++;
            }
        }
         
        int jj=0;
        for(int i=0;i<size_of_array;i++)        
        {
            x_array[i]=a2[i][jj];    // storing the x co-ordinates
        }
    
        /* Putting y only in its y_array matrix*/
        int j=1;
        for(int i=0;i<size_of_array;i++)        
        {
            y_array[i]=a2[i][j];   // storing the y co-ordinates
        } 
        
        int sum_x=0;
        int sum_y=0;
        for(int i=0;i<size_of_array;i++)
        {
            sum_x+=x_array[i];  
            sum_y+=y_array[i];
        }
        
        Double avg_x=(double)sum_x/size_of_array;    // calculateing the average of x co-ordintates
        Double avg_y=(double)sum_y/size_of_array;    // calculateing the average of y co-ordintates
        
        int flag_x=0;
        for(int i=0;i<size_of_array;i++)
        {
            if(avg_x==x_array[i])         // checking if the y average exist in the input x_array
            {
                flag_x=1;
                break;
            }
        }
        
        if(flag_x==1)
        {
            avg_x=avg_x+0.1;  // if the x average is present add 0.1 to the average
        }
        
        int flag_y=0;
        for(int i=0;i<size_of_array;i++)
        {
            if(avg_y==y_array[i])  // // checking if the  y average exist in the input y_array
            {
                flag_y=1;
                break;
            }
        }
        
        if(flag_y==1)
        {
            avg_y=avg_y+0.1; // if the x average is present add 0.1 to the average
        }
        
        list_of_lines_x.add(avg_x);  // storing the lines in a x list

        list_of_lines_y.add(avg_y);  // storing the lines in a  y list
       
        for(int i=0;i<list_of_lines_x.size();i++)
        {
            list_of_lines_x.get(i);
        }
        
        for(int i=0;i<list_of_lines_y.size();i++)
        {
            list_of_lines_y.get(i);
        }
        
        LocalAlgo fl=new LocalAlgo();
        
        fl.devide_rec(x_array,y_array,list_of_lines_x,list_of_lines_y,0,0,avg_x,avg_y);  // calling the bottom left quadarant
        fl.devide_rec(x_array,y_array,list_of_lines_x,list_of_lines_y,0,avg_y,avg_x,100);    // calling the top left quadarant
        fl.devide_rec(x_array,y_array,list_of_lines_x,list_of_lines_y,avg_x,avg_y,100,100);  // calling the top right quadarant
        fl.devide_rec(x_array,y_array,list_of_lines_x,list_of_lines_y,avg_x,0,100,avg_y);  // calling the bottom right quadarant
        
        Set<Double> final_lines_x = new HashSet<>();
        final_lines_x.addAll(list_of_lines_x);
        list_of_lines_x.clear();
        list_of_lines_x.addAll(final_lines_x);
        
        Set<Double> final_lines_y = new HashSet<>();
        final_lines_y.addAll(list_of_lines_y);
        list_of_lines_y.clear();
        list_of_lines_y.addAll(final_lines_y);
        
        
        String newFileName="local_solution_"+f.getName();
        
        newFileName= newFileName.replace("_instance", "");
        
        /* Writing the output to the files */
        File  output_file = new File(".\\output_local\\"+newFileName);
        
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
 
            pw.println("v "+list_of_lines_x.get(i));  /* Writing the output to the files */

        }
        
        for(int i=0;i<list_of_lines_y.size();i++)
        {
             pw.println("h "+list_of_lines_y.get(i));  /* Writing the output to the files */
            
        }
        pw.close();
        inFile1.close();
   
    }      
            System.out.println("Successful. Please check the local output folder");

 }

 /* Below function to calculate and plot the lines */ 
 /* Initially in the main function, we first devide the plane into four quadarant.
    Then we call the below function for each quadarant recursively */   
    
void devide_rec(int x_array[],int y_array[], List<Double> list_of_lines_x, List<Double> list_of_lines_y, double x_min, double y_min, double x_max, double y_max)
{
        int count_of_points=0;  // used to keep the count of points in each quadrant or subquadarant
        int size_of_array=x_array.length;
 
        for(int i=0;i<size_of_array;i++)  // this loop is used to find how much points are present in each subquadrant
        {
            if(x_array[i]>x_min && x_array[i]<x_max && y_array[i]>y_min && y_array[i]<y_max)
            {
                count_of_points++;  
            }  
        }
        
        //System.out.println("****************** device_rec count= "+count_of_points);
        
        if(count_of_points==0)  // if there is zero point in any quadarant do nothing
        {
            return;
        }
        else if(count_of_points==1)  // if there is only point in any quadarant do nothing
        {
            return;  
        }
         
        /* If there are two points in a quadarant then,
           find the average of x co-ordinates and average of y co-ordinates and then draw a line between them */
        
        else if(count_of_points==2) 
        {
            int x2[] =new int[2]; // arrays to hold the x-co-ordinates
            int y2[]=new int[2]; // arrays to hold the y-co-ordinates
            int counter=0;
            
            for(int i=0;i<size_of_array;i++)
            { 
                if(x_array[i]>x_min && x_array[i]<x_max && y_array[i]>y_min && y_array[i]<y_max)
                {
                    x2[counter]=x_array[i];  // geting those two points and storing it in array
                    y2[counter]=y_array[i];  // geting those two points and storing it in array
                    counter++;
                    
                }
            }
            
            double x2_avg=(x2[0]+x2[1])/2; // counting the average
           
               int x2_flag=0;
               for(int i=0;i<x_array.length;i++)
               {
                   if(x2_avg==x_array[i]) // checking if the average is present in our input array
                   {
                       x2_flag=1;
                       break;
                   }
               }
               if(x2_flag==1)
               {
                   x2_avg+=0.1; // if average is present then add 0.1 to the average
               }

                int flag21=0;
                for(int i=0;i<list_of_lines_x.size();i++)
                {
                    if(Math.ceil(list_of_lines_x.get(i))==Math.ceil(x2_avg)) // check if that average is already added to the list of lines
                    {
                        flag21=1;   
                        break;
                    }
                }
                
                if(flag21==0)
                {
                    list_of_lines_x.add(x2_avg);  // if average is not added in the listof lines then add it to list of lines

                }
         return;
        }
        
/*      If any quadrant or subqadarant has three points which needs to be seperated then,
        You need to find the average of the 1st point and 2nd point and then draw a line from that average.
        Similarly find the average of the 2nd point and the 3rd point and then draw a line between them
*/
        else if(count_of_points==3)
        {
            int[] x3=new int[3];
            int[] y3=new int[3];
            int counter1=0;
            
            for(int i=0;i<size_of_array;i++)
            { 
                if(x_array[i]>x_min && x_array[i]<x_max && y_array[i]>y_min && y_array[i]<y_max)
                {
                    x3[counter1]=x_array[i];  // geting those three points and storing it in array
                    y3[counter1]=y_array[i];  // geting those three points and storing it in array
                    counter1++;
                    
                }
            }
         
            int temp_x;
            int temp_y;
            for(int i=0;i<y3.length;i++)  // sorting the those arrays so that we can plot lines at the correct point
            {
                for(int j=1;j<y3.length-i;j++)
                {
                    if(y3[j-1]>y3[j])
                    {
                        temp_y=y3[j-1];
                        y3[j-1]=y3[j];
                        y3[j]=temp_y;
                       
                        temp_x=x3[j-1];
                        x3[j-1]=x3[j];
                        x3[j]=temp_x;
     
                    }
                }
            }
            
            double y3_avg1=(y3[0]+y3[1])/2; // calculating the average of 1st and 2nd element
            int y3_flag1=0; 
               for(int i=0;i<y_array.length;i++)
               {
                   if(y3_avg1==y_array[i]) // checkin if the average is present in our input array
                   {
                       y3_flag1=1;
                       break;
                   }
               }
               
               if(y3_flag1==1)
               {
                   y3_avg1+=0.1; // if the average is present then add 0.1
               }

                int flag31=0;
                for(int i=0;i<list_of_lines_y.size();i++)
                {
                    if(Math.ceil(list_of_lines_y.get(i))==Math.ceil(y3_avg1))  // check if the average is present in the list of lines
                    {
                        flag31=1;
                        break;
                    }
                }
                
                if(flag31==0)
                {
                    list_of_lines_y.add(y3_avg1); // if average is not present in the list then add them to the list
                    
                }
    
            double y3_avg2=(y3[1]+y3[2])/2;  //calculating the average of 2nd and 3rd element
                
            int y3_flag2=0;
               for(int i=0;i<y_array.length;i++)
               {
                   if(y3_avg2==y_array[i])  // checkin if the average is present in our input array
                   {
                       y3_flag2=1;
                       break;
                   }
               }
               
               if(y3_flag2==1)
               {
                   y3_avg2+=0.1; // if the average is present then add 0.1
               }

                int flag32=0;
                for(int i=0;i<list_of_lines_y.size();i++)
                {
                    if(Math.ceil(list_of_lines_y.get(i))==Math.ceil(y3_avg2)) // check if the average is present in the list of lines
                    {
                        flag32=1;
                        break;
                    }
                }
                
                if(flag32==0)
                {
                    list_of_lines_y.add(y3_avg2); // if average is not present in the list then add them to the list
                      
                }

            return;
            
        }
        
        /* if the quadarant has four points then first we will find if we can devide them by using two lines
           If we can then we would draw lines and store them in array */
        
        else if(count_of_points==4)
        {   
            int x4[]=new int[4];
            int y4[]=new int[4];
            int counter2=0;
            
            for(int i=0;i<size_of_array;i++)
            { 
                if(x_array[i]>x_min && x_array[i]<x_max && y_array[i]>y_min && y_array[i]<y_max)
                {
                    x4[counter2]=x_array[i];  // geting those points and storing it in array
                    y4[counter2]=y_array[i];  // geting those points and storing it in array
                    counter2++;  
                }
            }
            
            int temp_x4;
            int temp_y4;
            for(int i=0;i<x4.length;i++)  // sorting the arrays
            {
                for(int j=1;j<x4.length-i;j++)
                {
                    if(x4[j-1]>x4[j])
                    {
                        temp_y4=y4[j-1];
                        y4[j-1]=y4[j];
                        y4[j]=temp_y4;
                       
                        temp_x4=x4[j-1];
                        x4[j-1]=x4[j];
                        x4[j]=temp_x4;
     
                    }
                }
            }
            
        int count4=0; // gives the number of lines in that quadrant
            
        int sum_x4=0;
        int sum_y4=0;
        for(int i=0;i<4;i++)
        {
            sum_x4+=x4[i];   // calculating the sum of all the points
            sum_y4+=y4[i];   // calculating the sum of all the points
        }
        
        Double avg_x4=(double)sum_x4/4;    // calculating the average of the points
        Double avg_y4=(double)sum_y4/4;   // calculating the average of the points
        
        int flag_x4=0;
        for(int i=0;i<size_of_array;i++)
        {
            if(avg_x4==x_array[i]) // checking the average if it is present in our array
            {
                flag_x4=1;
                break;
            }
        }
        
        if(flag_x4==1)
        {
            avg_x4=avg_x4+0.1;  // if the average is present then add 0.1
        }
        
        int flag_y4=0;
        for(int i=0;i<size_of_array;i++)
        {
            if(avg_y4==y_array[i])  // checking the average if it is present in our array
            {
                flag_y4=1;
                break;
            }
        }
        
        if(flag_y4==1)
        {
            avg_y4=avg_y4+0.1;  // if the average is present then add 0.1
        }
        
        // after we devide that sub quadarant into four more sub quadarant we check if each smaller quadarant has how many points
        
        int countx41=0;
        for(int i=0;i<size_of_array;i++)
        {
            if(x_array[i]>x_min && x_array[i]<avg_x4 && y_array[i]>y_min && y_array[i]<avg_y4)
            {
                 countx41++;
            }
        }
        
        int countx42=0;
        for(int i=0;i<size_of_array;i++)
        {
            if(x_array[i]>x_min && x_array[i]<avg_x4 && y_array[i]>avg_y4 && y_array[i]<y_max)
            {
                countx42++;
            }
            
        }

        int countx43=0;
        for(int i=0;i<size_of_array;i++)
        {
            if(x_array[i]>avg_x4 && x_array[i]<x_max && y_array[i]>avg_y4 && y_array[i]<y_max)
            {
                countx43++;
            }
            
        }
        
        int countx44=0;
        for(int i=0;i<size_of_array;i++)
        {
            if(x_array[i]>avg_x4 && x_array[i]<x_max && y_array[i]>y_min && y_array[i]<avg_y4)
            {
                countx44++;
            }
            
        }
        
        if(countx41==1 && countx42==1 && countx43==1 && countx44==1)  // checking if each subquadrant has only one point
        {
            int flag441=0;
                for(int i=0;i<list_of_lines_x.size();i++)
                {
                    if(Math.ceil(list_of_lines_x.get(i))==Math.ceil(avg_x4))  
                    {
                        flag441=1;
                        break;
                    }
                }
                
                if(flag441==0)
                {
                    
                     count4++;
                }
                
                int flag442=0;
                for(int i=0;i<list_of_lines_y.size();i++)
                {
                    if(Math.ceil(list_of_lines_y.get(i))==Math.ceil(avg_y4))
                    {
                        flag442=1;
                        break;
                    }
                }
                
                if(flag442==0)
                {
                    
                     count4++;
                }
                
        }
        
/* If only two lines are separating the four points then add them to the list of lines of x and y */
        
        if(count4<3)
        {
            int flag441=0;
                for(int i=0;i<list_of_lines_x.size();i++)
                {
                    if(Math.ceil(list_of_lines_x.get(i))==Math.ceil(avg_x4))
                    {
                        flag441=1;
                        break;
                    }
                }
                
                if(flag441==0)
                {
                    list_of_lines_x.add(avg_x4);  
                     
                }
                
                int flag442=0;
                for(int i=0;i<list_of_lines_y.size();i++)
                {
                    if(Math.ceil(list_of_lines_y.get(i))==Math.ceil(avg_y4))
                    {
                        flag442=1;
                        break;
                    }
                }
                
                if(flag442==0)
                {
                    list_of_lines_x.add(avg_x4);     
                }     
        }  

        else
        {
        
 // if the four points are not geting separted by two lines then we need to draw lines between the two points by taking the average    
            double x4_avg1=(x4[0]+x4[1])/2; // first average
            
            int x4_flag1=0;
                for(int i=0;i<x_array.length;i++)
                {
                    if(x4_avg1==x_array[i])
                    {
                        x4_flag1=1;
                        break;
                    }
                }
            
                if(x4_flag1==1)
                {
                    x4_avg1+=0.1;
                }

                int flag41=0;
                for(int i=0;i<list_of_lines_x.size();i++)
                {
                    if(Math.ceil(list_of_lines_x.get(i))==Math.ceil(x4_avg1))
                    {
                        flag41=1;
                        break;
                    }
                }
                
                if(flag41==0)
                {
                    list_of_lines_x.add(x4_avg1);
                     
                }
            
            double x4_avg2=(x4[1]+x4[2])/2;  // second average
            
            int x4_flag2=0;
                for(int i=0; i<x_array.length;i++)
                {
                    if(x4_avg2==x_array[i])
                    {
                        x4_flag2=1;
                        break;
                    }
                }
                
                if(x4_flag2==1)
                {
                    x4_avg2+=0.1;
                }

                int flag42=0;
                for(int i=0;i<list_of_lines_x.size();i++)
                {
                    if(Math.ceil(list_of_lines_x.get(i))==Math.ceil(x4_avg2))
                    {
                        flag42=1;
                        break;
                    }
                }
                
                if(flag42==0)
                {
                    list_of_lines_x.add(x4_avg2);
                     
                }
                
                
             double x4_avg3=(x4[2]+x4[3])/2; // third average
             
             int x4_flag3=0;
                for(int i=0;i<x_array.length;i++)
                {
                    if(x4_avg3==x_array[i])
                    {
                        x4_flag3=1;
                        break;
                    }
                }
                
                if(x4_flag3==1)
                {
                    x4_avg3+=0.1;
                }

                int flag43=0;
                for(int i=0;i<list_of_lines_x.size();i++)
                {
                    if(Math.ceil(list_of_lines_x.get(i))==Math.ceil(x4_avg3))
                    {
                        flag43=1;
                        break;
                    }
                }
                
                if(flag43==0)
                {
                    list_of_lines_x.add(x4_avg3);
                     
                }
            
            }
            
            return;
            
        }
        
        else
        { 
            
/* if the points in quadarant is more than 4 then take the sum of all the points and find the average and draw the lines.

   then again devide that quadrant and repeat the above process again
*/
           
          int x_remaining[]=new int[count_of_points];
          int y_remaining[]=new int[count_of_points];
          int counter3=0;
          
            for(int i=0;i<x_array.length;i++)
            {
                if(x_array[i]>x_min && x_array[i]<x_max && y_array[i]>y_min && y_array[i]<y_max)
                {
                    x_remaining[counter3]=x_array[i];
                    y_remaining[counter3]=y_array[i];
                    counter3++;
                }
            }
            
            int x_sum_remaining=0;
            int y_sum_remaining=0;
            
            for(int i=0;i<x_remaining.length;i++)
            {
                x_sum_remaining+=x_remaining[i];
                y_sum_remaining+=y_remaining[i];
            }
            
            Double x_remaining_avg = (double)x_sum_remaining/count_of_points;
            Double y_remaining_avg = (double)y_sum_remaining/count_of_points;
            
            int flag_x1=0;
            for(int i=0;i<size_of_array;i++)
            {
                if(x_remaining_avg==x_array[i])
                {
                    flag_x1=1;
                    break;
                }
            }
        
            if(flag_x1==1)
            {
                x_remaining_avg=x_remaining_avg+0.1;
            }

            int flag_last_x=0;
                for(int i=0;i<list_of_lines_x.size();i++)
                {
                    if(Math.ceil(list_of_lines_x.get(i))==Math.ceil(x_remaining_avg))
                    {
                        flag_last_x=1;
                        break;
                    }
                }
                
                if(flag_last_x==0)
                {
                    list_of_lines_x.add(x_remaining_avg);
                     
                }
            
            int flag_y1=0;
            for(int i=0;i<size_of_array;i++)
            {
                if(y_remaining_avg==y_array[i])
                {
                    flag_y1=1;
                    break;
                }
            }
            
            if(flag_y1==1)
            {
                y_remaining_avg=y_remaining_avg+0.1;
            }

            int flag_last_y=0;
                for(int i=0;i<list_of_lines_y.size();i++)
                {
                    if(Math.ceil(list_of_lines_y.get(i))==Math.ceil(y_remaining_avg))
                    {
                        flag_last_y=1;
                        break;
                    }
                }
                
                if(flag_last_y==0)
                {
                    list_of_lines_y.add(y_remaining_avg);
                     
                }
            
            devide_rec(x_array,y_array, list_of_lines_x,list_of_lines_y,x_min,y_min,x_remaining_avg,y_remaining_avg);        // bottom left
    
            devide_rec(x_array,y_array, list_of_lines_x,list_of_lines_y,x_min,y_remaining_avg,x_remaining_avg,y_max);    // top left
              
            devide_rec(x_array,y_array, list_of_lines_x,list_of_lines_y,x_remaining_avg,y_remaining_avg,x_max,y_max);  // top right
            
           devide_rec(x_array,y_array, list_of_lines_x,list_of_lines_y,x_remaining_avg,y_min,x_max,y_remaining_avg);  // bottom right
     
            return;
        
        }

    }
    
}
