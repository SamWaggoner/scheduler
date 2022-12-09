/*
 * Class: TaskList
 * Description: Holds an ArrayList of Task objects with various useful methods
 * used for operating on that list, such as sorting.
 * Attributes: none
 * Bugs: none known
 * Limitations: none
 * Author: Sam Waggoner <samuel.waggoner@maine.edu>
 * Created: 12/09/22
 * For: COS 470, scheduler
 * Modifications: none
 * 
 * Notes:
 * functions divide() and merge() are adapted from:
 * https://www.withexample.com/merge-sort-using-arraylist-java-example/,
 * written by Niravkumar-Patel. They have been changed to sort Task objects by
 * attribute, instead of integers.
 */


import java.time.temporal.ChronoUnit;
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
     * Note: This is used instead of iterating backward through a list because
     * that would require multiple versions of big functions in the Week class,
     * or at least added complexity (this could be a future change).
     */
    public static ArrayList<Task> reverse(ArrayList<Task> arr)
    {
        int len = arr.size();
        Task temp;

        // Switch each element in the list (integer division rounds down)
        for (int i = 0; i < len/2; i++)
        {
            temp = arr.get(i);
            arr.set(i, arr.get(len-i-1));
            arr.set(len-i-1, temp);
        }
        return arr;
    }


    /*
     * Method: printTasks
     * Description: Prints whatever the current task list is in the static object
     * This is not a toString override method since it is a static class.
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
     * This is not a toString override method since it is a static class.  
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
     * - att: String that represents the attribute by which to sort the Tasks.
     *        Valid strings include:
     *          - random, importance, hoursRequired, start, due, timeframe,
     *            restriction, bySmallImportant, and byImportantSmall
     * Note: this list could be easily increased in size to sort by new
     * attributes/combinations of attributes.
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
     * - att: refer to sortBy() function description
     * Returns: void, sorting is done in-place
     * Uses: merge()
     */
    public static void divide(int startIndex,int endIndex, String att)
    {        
        // Divide till you breakdown your list to single element
        if (startIndex<endIndex && (endIndex-startIndex)>=1)
        {
            int mid = (endIndex + startIndex)/2;
            divide(startIndex, mid, att);
            divide(mid+1, endIndex, att);        
            
            // Merging sorted array produce above into one sorted array
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
     * - att: refer to sortBy() function description
     * Returns: void, sorting is done in-place
     * Uses: merge()
     */
    public static void merge(int startIndex,int midIndex,int endIndex,String att)
    {
        ArrayList<Task> mergedSortedArray = new ArrayList<Task>();
        
        int leftIndex = startIndex;
        int rightIndex = midIndex + 1;
        
        while(leftIndex<=midIndex && rightIndex<=endIndex)
        {
            // lte = left of, since sorts in ascending order by default
            // if you want to sort high->low, switch < to > below
            boolean lte = false;

            long timeframe1, timeframe2;
            int rating1, rating2;
            Task left = tasks.get(leftIndex);
            Task right = tasks.get(rightIndex);
            switch (att)
            {
                case "random":
                    if (Math.floor(Math.random()*(10-1+1)+1) <= 5)
                        lte = true;
                    break;
                case "importance":
                    if (left.importance <= right.importance)
                        lte = true;
                    break;
                case "hoursRequired":
                    if (left.hoursRequired <= right.hoursRequired)
                        lte = true;
                    break;
                case "start":
                    if (left.start.isBefore(right.start))
                        lte = true;
                    break;
                case "due":
                    if (left.due.isBefore(right.due))
                        lte = true;
                    break;
                case "timeframe":
                    timeframe1 = ChronoUnit.MINUTES.between(
                        tasks.get(leftIndex).start, tasks.get(leftIndex).due);
                    timeframe2 = ChronoUnit.MINUTES.between(
                        tasks.get(rightIndex).start, tasks.get(rightIndex).due);
                    if (timeframe1 < timeframe2)
                        lte = true;
                    break;
                case "restriction":
                    timeframe1 = ChronoUnit.MINUTES.between(
                        tasks.get(leftIndex).start, tasks.get(leftIndex).due);
                    timeframe2 = ChronoUnit.MINUTES.between(
                        tasks.get(rightIndex).start, tasks.get(rightIndex).due);
                    if ((timeframe1 - tasks.get(leftIndex).hoursRequired) <
                        (timeframe2 - tasks.get(rightIndex).hoursRequired))
                        lte = true;
                case "bySmallImportant":
                    rating1 = (int) (left.importance * (1/left.hoursRequired));
                    rating2 = (int) (left.importance * (1/left.hoursRequired));
                    if (rating1 > rating2)
                        lte = true;
                    break;
                case "byImportantSmall":
                    rating1 = (int) ((1/left.importance) * left.hoursRequired);
                    rating2 = (int) ((left.importance) * left.hoursRequired);
                    if (rating1 > rating2)
                        lte = true;
                    break;
                default:
                    Debug.err("Error: incorrect attribute given to sortBy");
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
        
        // Either of below while loop will execute
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

        // Set sorted array to original one
        while(i < mergedSortedArray.size())
        {
            tasks.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }
}