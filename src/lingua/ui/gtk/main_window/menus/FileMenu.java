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
import lingua.ui.gtk.main_window.actions.ExitApplicationAction;
import lingua.ui.gtk.main_window.actions.ExportHTMLAction;
import lingua.ui.gtk.main_window.actions.NewFileAction;
import lingua.ui.gtk.main_window.actions.OpenFileAction;
import lingua.ui.gtk.main_window.actions.PrintAction;
import lingua.ui.gtk.main_window.actions.SaveFileAction;
import lingua.ui.gtk.main_window.actions.SaveFileAsAction;
import org.gnome.gtk.Menu;
import org.gnome.gtk.MenuItem;
import org.gnome.gtk.SeparatorMenuItem;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class FileMenu extends MenuItem {

    public FileMenu() {
        super(StringResources.getInstance().getString("file"));
        Menu fileMenu = new Menu();
        fileMenu.append(NewFileAction.getInstance().createMenuItem());
        fileMenu.append(OpenFileAction.getInstance().createMenuItem());
        fileMenu.append(SaveFileAction.getInstance().createMenuItem());
        fileMenu.append(SaveFileAsAction.getInstance().createMenuItem());

        fileMenu.append(new SeparatorMenuItem());
        fileMenu.append(ExportHTMLAction.getInstance().createMenuItem());

        fileMenu.append(new SeparatorMenuItem());
        fileMenu.append(PrintAction.getInstance().createMenuItem());

        fileMenu.append(new SeparatorMenuItem());
        fileMenu.append(ExitApplicationAction.getInstance().createMenuItem());
        this.setSubmenu(fileMenu);
    }
}
