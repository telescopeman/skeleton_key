import javax.swing.JSpinner;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Manages root note.
 *
 * @author Caleb Copeland, GingerHead [setWidth() method only]
 * @version 5/23/21
 * @since 4/6/21
 */
public class OffsetEditor extends EasyPanel
{
    private int offset;
    private JSpinner spinner;
    private final int WIDTH = 3;

    private final String[] SCALE = TheoryObj.CHROMATICSCALE;

    /**
     * Constructor for objects of class OffsetEditor
     */
    public OffsetEditor()
    {
        super("Change Key");

        offset = 0;
        CyclingSpinnerListModel mdl = new CyclingSpinnerListModel(TheoryObj.CHROMATICSCALE);
        spinner = new JSpinner((mdl));
        setWidth(WIDTH);
        add(spinner);

        ChangeListener listener = new ChangeListener()
        {
                public void stateChanged(ChangeEvent e) {
                    String val = (String) spinner.getValue();
                    //System.out.println(val);
                    int n = ArrayHelper.search(SCALE,val);
                    if (n < 0)
                    {
                        throw new IllegalArgumentException("Illegal selection "+ n);
                    }
                    else
                    {
                        offset = n;
                        StateWatcher.setOffset(n);
                        SpeedCache.setOffset(n);
                        UIStuff.refresh();
                    }
                }
            };
        
        spinner.addChangeListener(listener);
    }

    public void setWidth(int w)
    {
        JComponent mySpinnerEditor = spinner.getEditor();
        JFormattedTextField jftf = ((JSpinner.DefaultEditor) mySpinnerEditor).getTextField();
        jftf.setColumns(w);
    }

    /**
     * Gets the offset of the current root note. C = 0 and D = 2, and so on.
     */
    public int getOffset()
    {
        return offset;
    }
}
