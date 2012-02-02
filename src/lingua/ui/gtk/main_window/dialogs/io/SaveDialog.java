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

package lingua.ui.gtk.main_window.dialogs.io;

import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.MainWindow;
import org.gnome.gdk.EventCrossing;
import org.gnome.gtk.Button;
import org.gnome.gtk.Dialog;
import org.gnome.gtk.FileChooserAction;
import org.gnome.gtk.FileChooserDialog;
import org.gnome.gtk.FileChooserWidget;
import org.gnome.gtk.FileFilter;
import org.gnome.gtk.ResponseType;
import org.gnome.gtk.Stock;
import org.gnome.gtk.Widget;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class SaveDialog extends Dialog{
    private FileChooserWidget chooserWidget;

    public SaveDialog() {
        super(StringResources.getInstance().getString("save_file"), MainWindow.getInstance(), true);

        chooserWidget = new FileChooserWidget(FileChooserAction.SAVE);

        this.add(chooserWidget);

        this.addButton(Stock.CANCEL, ResponseType.CANCEL);
        Button saveButton = addButton(Stock.SAVE, ResponseType.OK);
        saveButton.setCanDefault(true);
        this.setDefaultResponse(ResponseType.OK);

        FileChooserDialog fcd= new FileChooserDialog("", MainWindow.getInstance(), FileChooserAction.SAVE);
        this.setDefaultSize(fcd.getWidth(), fcd.getHeight());

        showAll();
    }



    public String getFilename(){
        return chooserWidget.getFilename();
    }

    public void setFilename(String filename, String filePath){
        if(filePath == null){
            filePath = System.getProperty("user.home");
        }
        chooserWidget.setFilename(filePath+System.getProperty("file.separator")+filename);
    }

    public void addFilter(FileFilter filter){
        chooserWidget.addFilter(filter);
    }

    public void setFilter(FileFilter filter){
        chooserWidget.setFilter(filter);
    }

}
