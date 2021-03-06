
/**
 * Handles creation of the huge list of scales.
 *
 * @author Caleb Copeland
 * @version 5/23/21
 */
public abstract class ScalesGenerator
{
    private static int[][] abstractList;
    private static final int[] initKey = new int[]{1,1,1,1,1,1,1};
    private static int counter;

    /**
     * Generates the list of all possible seven-note scales.
     */
    public static int[][] getAllKeys()
    {
        
        abstractList = new int[462][7];
        
        counter = 0;

        makeKey(initKey,1);
        return abstractList;

    }

    private static void setKey(int[] k, int i)
    {
        abstractList[i] = k;
    }

    private static void makeKey(int[] curArr, int index)
    {
        if (index == 7)
        {
            setKey(curArr,counter);
            counter++;
        }
        else
        {
            
            int lastNote = curArr[index-1];
            

            for(int branch = lastNote + 1; branch < 7 + index;branch++)
            {
                 int[] passAlong = new int[curArr.length];
 
        // Copy elements of a[] to b[]
                System.arraycopy(curArr, 0, passAlong, 0, passAlong.length);
                //int[] passAlong = curArr;
                passAlong[index] = branch;
                
                makeKey(passAlong,index+1);
            }
        }

    }


}
