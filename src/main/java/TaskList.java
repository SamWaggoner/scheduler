/*
 * Class: TaskList
 * Description: Holds an ArrayList of Task objects with various useful methods
 * used for operating on that list, such as sorting.
 * Attributes: none
 * Bugs: none
 * Limitations: none
 * Author: Sam Waggoner <samuel.waggoner@maine.edu>
 * Created: 12/09/22
 * For: COS 470, scheduler
 * Modifications: none
 * 
 * Note: functions divide() and merge() are adapted from:
 * https://www.withexample.com/merge-sort-using-arraylist-java-example/,
 * written by Niravkumar-Patel. They have been changed to sort Task objects by
 * attribute, instead of integers.
 * 
◯ read a brick <5, 12.5, Dec 8> (Tomorrow)
◯ file a shoe <3, 4, Today> (Tomorrow)
◯ walk the plants <5, 7, Today> (Fri, Dec 9)
◯ sue a jaywalker <8, 3, Oct 31> (Wed, Dec 30)
 */


import java.util.ArrayList;


public class TaskList {

    static ArrayList<Task> tasks;
    public int size = tasks.size();
    

    public TaskList(){};


    /*
     * Method: reverse
     * Description: Reverses the order of an ArrayList. It is faster to sort a
     * list in ascending order and then reverse it, which is O(n^2) + O(n)
     * than to sort by both ascending and descending, O(n^2) + O(n^2). 
     */
    public static ArrayList<Task> reverse(ArrayList<Task> arr)
    {
        //System.out.println("STARTVAL 0\n\n\n\n" + arr.get(0));
        //System.out.println("STARTVAL END\n\n\n" + arr.get(arr.size()-1));
        int len = arr.size();
        Task temp;

        // Switch each element in the list (integer division rounds down)
        for (int i = 0; i < len/2; i++)
        {
            temp = arr.get(i);
            arr.set(i, arr.get(len-i-1));
            arr.set(len-i-1, temp);
        }
        //System.out.println("ENDVAL 0\n\n\n\n" + arr.get(0));
        //System.out.println("ENDVAL END\n\n\n\n" + arr.get(arr.size()-1));
        return arr;
    }


    /*
     * Method: printTasks
     * Description: Prints whatever the current task list is in the static object
     */
    public static void printTasks()
    {
        System.out.println("-----------------------------------------------\n");
        for (int i = 0; i < tasks.size(); i++)
        {
          System.out.println(tasks.get(i));
        }
        System.out.println("-----------------------------------------------\n");
    }


    /*
     * Method: printTasks
     * Description: Prints a given task list parameter
     */
    public static void printTasks(ArrayList<Task> arr)
    {
        System.out.println("-----------------------------------------------\n");
        for (int i = 0; i < arr.size(); i++)
        {
            System.out.println(arr.get(i));
        }
        System.out.println("-----------------------------------------------\n");
    }


    
    /*
     * Method: sortBy
     * Description: Sorts an ArrayList of Tasks by a given attribute in
     * ascending order.
     * Arguments:
     * - arr: ArrayList of Task objects
     * - att: String that represents the attribute by which to sort the Tasks.
     *        Valid are: importance, hoursRequired, start, due
     * Returns: sorted ArrayList<Task>, but sorting is done in-place
     * Uses: divide()
     */
    public static ArrayList<Task> sortBy(String att)
    {       
        divide(0, tasks.size()-1, att);
        return tasks;
    }


    /*
     * Method: divide
     * Description: In merge sort, a list is recursively divided into left and
     * right sublists until the list cannot be divided any more. Then, those
     * sublists are merged back together in a sorted fashion until the original
     * list is sorted. Each individual list is sorted. Thus, in order to combine
     * two sorted lists, you only need to compare the lowest (first indeces)
     * elements of each list repeatedly.
     * Arguments:
     * - startIndex: where the current sublist begins
     * - endIndex: where the current sublist ends
     * - att: String that represents the attribute by which to sort the Tasks.
     *        Valid are: importance, hoursRequired, start, due
     * Returns: void, sorting is done in-place
     * Uses: merger()
     */
    public static void divide(int startIndex,int endIndex, String att)
    {        
        //Divide till you breakdown your list to single element
        if (startIndex<endIndex && (endIndex-startIndex)>=1)
        {
            int mid = (endIndex + startIndex)/2;
            divide(startIndex, mid, att);
            divide(mid+1, endIndex, att);        
            
            //merging sorted array produce above into one sorted array
            merge(startIndex,mid,endIndex,att);            
        }       
    }   
    

    /*
     * Method: merge
     * Description: Merges two sorted sublists. Used in merge sort as described
     * in divide()'s code block.
     * Arguments:
     * - startIndex: where the current sublist begins
     * - midIndex: the middle of the array, (endIndex + startIndex)/2
     * - endIndex: where the current sublist ends
     * - att: String that represents the attribute by which to sort the Tasks.
     *        Valid are: importance, hoursRequired, start, due
     * Returns: void, sorting is done in-place
     * Uses: merger()
     */
    public static void merge(int startIndex,int midIndex,int endIndex,String att)
    {
        ArrayList<Task> mergedSortedArray = new ArrayList<Task>();
        
        int leftIndex = startIndex;
        int rightIndex = midIndex + 1;
        
        while(leftIndex<=midIndex && rightIndex<=endIndex)
        {
            boolean lte = false;
            switch (att)
            {
                case "importance":
                    if (tasks.get(leftIndex).importance <=
                        tasks.get(rightIndex).importance)
                        lte = true;
                    break;
                case "hoursRequired":
                    if (tasks.get(leftIndex).hoursRequired <= 
                        tasks.get(rightIndex).hoursRequired)
                        lte = true;
                    break;
                case "start":
                    if (tasks.get(leftIndex).start.isBefore
                        (tasks.get(rightIndex).start))
                        lte = true;
                    break;
                case "due":
                    if (tasks.get(leftIndex).due.isBefore
                        (tasks.get(rightIndex).due))
                        lte = true;
                    break;
                default:
                    System.err.println("Error: incorrect attribute given to sortBy");
            }
            if (lte)
            {
                mergedSortedArray.add(tasks.get(leftIndex));
                leftIndex++;
            }
            else {
                mergedSortedArray.add(tasks.get(rightIndex));
                rightIndex++;
            }
        }       
        
        //Either of below while loop will execute
        while(leftIndex<=midIndex)
        {
            mergedSortedArray.add(tasks.get(leftIndex));
            leftIndex++;
        }
        
        while(rightIndex<=endIndex)
        {
            mergedSortedArray.add(tasks.get(rightIndex));
            rightIndex++;
        }
        
        int i = 0;
        int j = startIndex;

        //Setting sorted array to original one
        while(i < mergedSortedArray.size())
        {
            tasks.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }


    // Not tested, since printing TaskList class generates an error
    @Override
    public String toString()
    {
        String summary = "\n\n";
        for (int i = 0; i < tasks.size(); i++)
        {
            summary = summary + String.valueOf(TaskList.tasks.get(i)) + '\n';
        }
        return summary;
    }

}