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

import lingua.ui.gtk.main_window.dialogs.filters.AllFilesFileFilter;
import lingua.ui.gtk.main_window.dialogs.filters.GlossaFileFilter;
import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.MainWindow;
import org.gnome.gtk.FileChooserAction;
import org.gnome.gtk.FileChooserDialog;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class OpenCodeDialog extends FileChooserDialog{

    private static OpenCodeDialog instance = null;

    public static OpenCodeDialog getInstance(){
        if(instance == null){
            instance = new OpenCodeDialog();
        }
        return instance;
    }

    private OpenCodeDialog() {
        super(StringResources.getInstance().getString("open_file"), MainWindow.getInstance(), FileChooserAction.OPEN);
        this.addFilter(new GlossaFileFilter());
        this.addFilter(new AllFilesFileFilter());
        this.setFilter(new GlossaFileFilter());
        this.setModal(true);
    }

}
