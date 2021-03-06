import java.util.Hashtable;
import javax.swing.JLabel;

/**
 * Allows for changing the neutral point for strangeness measurements.
 *
 * @author Caleb Copeland
 * @version 5/23/21
 */
public class StrangeBox extends SliderBox
{

    /**
     * Constructor for objects of class StrangeBox
     */
    public StrangeBox()
    {
        super("Change Neutral Point", LONG,
                Control.getNeutralPoint(),20,440);
        addHeader("Change Neutral Point for Strangeness Sorting:");
        addLabels();
        add(getSlider());
        //addButton("Apply",this);
    }
    
    private void addLabels()
    {
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(41, new JLabel("Aerygian") );
        labelTable.put(111, new JLabel("Ultra Phrygian ♭♭3") );
        labelTable.put(166, new JLabel("Locrian") );
        labelTable.put(224, new JLabel("Enigmatic Scale") );
        labelTable.put(300, new JLabel("Dorian") );
        labelTable.put(336, new JLabel("Major") );
        labelTable.put(364, new JLabel("Nohkan") );
        labelTable.put(420, new JLabel("Super Lydian") );
        getSlider().setLabelTable( labelTable );
    }

    @Override
    protected void onClosed() {
        Control.setNeutral((int)getPos());
        UI.refresh();
    }

    protected void apply()
    {
        // do nothing lol
    }
}
