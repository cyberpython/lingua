/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lingua.ui.gtk.main_window.dialogs.filters;

import org.gnome.gtk.FileFilter;

/**
 *
 * @author cyberpython
 */
public abstract class FileFilterWithExtension extends FileFilter{
    public abstract String getExtension();
}
