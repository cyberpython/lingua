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

package lingua.ui.gtk.main_window.widgets;

import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.widgets.commandslist.CommandsListView;
import org.gnome.gtk.Label;
import org.gnome.gtk.Notebook;
import org.gnome.gtk.ScrolledWindow;

/**
 *
 * @author cyberpython
 */
public class SidePane extends Notebook{
    
    private final Label commandsTabLabel;
    private final CommandsListView commandsListView;
    
    public SidePane() {
        StringResources strRes = StringResources.getInstance();
        this.commandsTabLabel = new Label(strRes.getString("commands_list"));
        this.commandsListView = new CommandsListView();

        ScrolledWindow sc1 = new ScrolledWindow();
        sc1.add(commandsListView);
        this.appendPage(sc1, commandsTabLabel);
    }

    public CommandsListView getCommandsListView(){
        return this.commandsListView;
    }
    
}
