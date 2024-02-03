import java.util.Random;

public class SetPartition
{
    private final int[] myIntArray;

    public SetPartition(int numElements)
    {
        myIntArray = new int[numElements];
        for(int i = 0; i< numElements; i++)
            myIntArray[i] = i;
    }

    public int getRoot(int x)
    {
        return (myIntArray[x] != x) ? getRoot(myIntArray[x]) : myIntArray[x];
    }

    public boolean inSameSubset(int x, int y)
    {
        return getRoot(x) == getRoot(y);
    }

    public void merge(int x, int y)
    {
        if(!inSameSubset(x,y))
            myIntArray[getRoot(y)] = getRoot(x);
    }

    public int depth(int x)
    {
        return (myIntArray[x] != x) ? 1 + depth(myIntArray[x]) : 0;
    }

    public int maxDepth()
    {
        int depth = 0;
        for(int i = 0; i < myIntArray.length; i++)
            depth = Math.max(depth, depth(i));
        return depth;
    }

    public int getMyIntArrayLength()
    {
        return myIntArray.length;
    }

    public static void main(String[] args)
    {
        int averageDepth = 0;
        SetPartition setPartition = new SetPartition(1000);
        SetPartition setPartition2 = new SetPartition(1000);
        SetPartition setPartition3;

        for (int i = 0; i < setPartition.getMyIntArrayLength() - 1; i++)
        {
            setPartition.merge(i, i + 1);
            setPartition2.merge(setPartition.getMyIntArrayLength() - 1 - i - 1, setPartition.getMyIntArrayLength() - 1 - i);
        }
        for(int j = 0; j < 1000; j++)
        {
            setPartition3 = new SetPartition(1000);
            for (int i = 0; i < 750; i++)
            {
                setPartition3.merge(new Random().nextInt(1000), new Random().nextInt(1000));
            }
            averageDepth += setPartition3.maxDepth();

        }
        averageDepth = averageDepth / 1000;
        System.out.println("Question 1: Maximum node depth of tree is " + setPartition.maxDepth());
        System.out.println("Question 2: Maximum node depth of tree is " + setPartition2.maxDepth());
        System.out.println("Question 3: Average maximum node depth of tree is " + averageDepth);
    }
}
