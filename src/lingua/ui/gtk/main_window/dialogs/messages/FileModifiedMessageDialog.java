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

package lingua.ui.gtk.main_window.dialogs.messages;

import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.MainWindow;
import org.gnome.gtk.ButtonsType;
import org.gnome.gtk.MessageDialog;
import org.gnome.gtk.MessageType;
import org.gnome.gtk.ResponseType;
import org.gnome.gtk.Stock;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class FileModifiedMessageDialog extends MessageDialog{

    public FileModifiedMessageDialog(){
        this(false, "");
    }

    public FileModifiedMessageDialog(boolean isInputFile, String filename) {
        super(MainWindow.getInstance(), true, MessageType.WARNING,
                ButtonsType.YES_NO,
                isInputFile?
                    StringResources.getInstance().getString("msg_input_file_modified"):
                    String.format(StringResources.getInstance().getString("msg_file_modified"),
                filename));

        addButton(Stock.CANCEL, ResponseType.CANCEL).setCanDefault(true);
        setDefaultResponse(ResponseType.CANCEL);
    }

}
