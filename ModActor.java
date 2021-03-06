import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A customized ActionListener that does stuff to the main UI.
 *
 * @author Caleb Copeland
 * @version 5/23/21
 */
public class ModActor implements ActionListener
{
    private int index;
    private SortOption setting;
    private final ModAction action;
    private Filter[] filters;
    
    /**
     * Constructor for objects of class ModActor
     */
    public ModActor()
    {
        index = -1;
        action = ModAction.DO_NOTHING;
    }
    
    /**
     * Constructor for objects of class ModActor
     */
    public ModActor(ModAction act, int ind)
    {
        index = ind;
        action = act;
    }

    /**
     * Constructor for objects of class ModActor
     */
    public ModActor(ModAction act)
    {
        action = act;
    }
    
    /**
     * Constructor for objects of class QuickParamHelp
     */
    public ModActor(SortOption sortOption)
    {
        setting = sortOption;
        action = ModAction.SET_SORT_STYLE;
    }

    /**
     * Template setter
     */
    public ModActor(Filter[] template)
    {
        filters = template;
        action = ModAction.SET_FILTER_TEMPLATE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(action)
        {
            case TOGGLE_FILTER:
            {
                FilterBank.toggleFilter(index);
                break;
            }
            case REMOVE_FILTER:
            {
                FilterBank.removeFilter(index);
                break;
            }
            case SET_SORT_STYLE:
            {
                Control.setSortStyle(setting);
                break;
            }
            case SET_FILTER_TEMPLATE:
            {
                int input = JOptionPane.showConfirmDialog(null,
                        "Applying a template will erase all current filters. Are you sure you want to do this?", "Confirmation",JOptionPane.YES_NO_OPTION);
                if (input == 0)
                {
                    FilterBank.setFilterStatuses(ArrayHelper.getGroupOf(true,filters.length));
                    FilterBank.setCurFilters(filters);
                }
                break;
            }
            case TOGGLE_DARK_MODE:
            {
                UI.toggle_dark_mode();
                break;
            }
            default:
                throw new IllegalArgumentException("Illegal ModActor action in constructor!");
        }
    }
}
