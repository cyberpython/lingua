/*
 *  The MIT License
 * 
 *  Copyright 2012 Georgios Migdos <cyberpython@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package lingua.ui.gtk.main_window.menus;

import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.actions.CopyAction;
import lingua.ui.gtk.main_window.actions.CutAction;
import lingua.ui.gtk.main_window.actions.DeleteAction;
import lingua.ui.gtk.main_window.actions.PasteAction;
import lingua.ui.gtk.main_window.actions.RedoAction;
import lingua.ui.gtk.main_window.actions.SelectAllAction;
import lingua.ui.gtk.main_window.actions.ShowPreferencesDialogAction;
import lingua.ui.gtk.main_window.actions.UndoAction;
import org.gnome.gtk.Menu;
import org.gnome.gtk.MenuItem;
import org.gnome.gtk.SeparatorMenuItem;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class EditMenu extends MenuItem {

    public EditMenu() {
        super(StringResources.getInstance().getString("edit"));
        Menu editMenu = new Menu();
        editMenu.append(UndoAction.getInstance().createMenuItem());
        editMenu.append(RedoAction.getInstance().createMenuItem());
        editMenu.append(new SeparatorMenuItem());
        editMenu.append(CutAction.getInstance().createMenuItem());
        editMenu.append(CopyAction.getInstance().createMenuItem());
        editMenu.append(PasteAction.getInstance().createMenuItem());
        editMenu.append(DeleteAction.getInstance().createMenuItem());
        editMenu.append(new SeparatorMenuItem());
        editMenu.append(SelectAllAction.getInstance().createMenuItem());
        editMenu.append(new SeparatorMenuItem());
        editMenu.append(ShowPreferencesDialogAction.getInstance().createMenuItem());
        this.setSubmenu(editMenu);
    }
}
