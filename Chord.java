import java.awt.Color;
/**
 * Represents a chord.
 *
 * @author Caleb Copeland
 * @version 5/23/21
 */
public class Chord extends BasicTheoryObj
{
    private int root, third, fifth;
    private final int permRoot;

    /**
     * Creates a chord with the given notes.
     */
    public Chord(int one, int two, int three)
    {
        // initialise instance variables
        root = one;
        permRoot = root;
        third = two;
        fifth = three;
        sort();
    }

    private void sort()
    {
        while (root >= third)
        {
            third = third + 12;
        }

        while (third >= fifth)
        {
            fifth = fifth + 12;
        }

    }

    
    private void invert()
    {
        int temp = root;
        root = third - 12;
        third = fifth - 12;
        fifth = temp;
    }

    /**
     * Returns the name of the chord.
     */
    public String toString()
    {
        String rootname = getNoteName(root);
        String thirdname = getThirdType();
        String fifthname  = getFifthType();
        if (fifthname.equals("?")) //invert twice if getting null values
        {
            invert();
            rootname = getNoteName(root);
            thirdname = getThirdType();
            fifthname  = getFifthType();
            if (fifthname.equals("?"))
            {
                invert();
                rootname = getNoteName(root);
                thirdname = getThirdType();
                fifthname  = getFifthType();
            }
        }

        if (fifthname.equals("Augmented") || fifthname.equals("Diminished"))
        {
            thirdname = "";

        }
        return getNoteName(permRoot) + " " + thirdname + " " + fifthname;
    }

    private String getFifthType()
    {
        String fifthname = "";
        int fifthint = fifth - root;
        int thirdinterval = third - root;
        if (fifthint == 7)
        {
            fifthname = "";
        }
        else if (fifthint == 8 && thirdinterval != 3)
        {
            fifthname = "Augmented";
        }
        else if (fifthint == 6 && thirdinterval == 3)
        {
            fifthname = "Diminished";
        }
        else if (fifthint == 6 && thirdinterval != 3)
        {
            fifthname = "♭5";
        }
        else if (fifthint == 5)
        {
            fifthname = "♭♭5";
        }
        else
        {
            fifthname = "?";
        }
        return fifthname;

    }

    private String getThirdType()
    {
        String thirdname = "";
        int thirdinterval = third - root;
        if (thirdinterval == 4)
        {
            thirdname = "Major";

        }
        else if (thirdinterval == 3)
        {
            thirdname = "Minor";

        }
        else if (thirdinterval == 2)
        {
            thirdname = "sus2";
        }
        else if (thirdinterval == 5)
        {
            thirdname = "sus4";
        }
        else
        {
            thirdname = "?";
        }

        return thirdname;
    }

    /**
     * Gets a color representing the chord.
     */
    public Color toColor()
    {
        switch (getFifthType())
        {
            case "Augmented":

            return ColorsHelper.soften(Color.yellow);

            case "Diminished":

            case "♭5":

                return ColorsHelper.soften(Color.green);

            case "♭♭5":

            return new Color(144,144,144);

            default:
            switch (getThirdType())
            {
                case "Minor":

                    return new Color(30,100,200);

                case "Major":

                    return ColorsHelper.soften(Color.red);

                case "sus2":

                case "sus4":

                    return new Color(100,200,200);

                default:
                    return Color.gray;
            }
        }
       
    }

    
    

    

    /**
     * Returns the notes of the chord, formatted.
     */
    public String getNotes()
    {
        return getNoteName(root) + " + " + getNoteName(third) + " + " + getNoteName(fifth);

    }
    
    

}
