/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lingua.ui.gtk.main_window.widgets.commandslist;

/**
 *
 * @author cyberpython
 */
public class CommandsListItemSeparator extends CommandsListItem{

    public CommandsListItemSeparator(String label, String textBefore, String textAfter) {
        super("", "", "");
    }

    @Override
    public String getText(String encapsulatedText, String indentation, boolean isLineEmpty) {
        return encapsulatedText;
    }
    
}
