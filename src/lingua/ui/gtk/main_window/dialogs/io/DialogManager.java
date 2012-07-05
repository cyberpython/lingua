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

import java.io.File;
import lingua.ui.gtk.main_window.dialogs.filters.FileFilterWithExtension;
import lingua.ui.gtk.main_window.dialogs.messages.FileExistsMessageDialog;
import lingua.ui.gtk.main_window.dialogs.messages.FileModifiedMessageDialog;
import lingua.ui.gtk.main_window.dialogs.messages.InterpreterStillRunningDialog;
import org.gnome.gtk.FileChooserDialog;
import org.gnome.gtk.ResponseType;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class DialogManager {

    public static boolean verifyStopInterpreter() {
        InterpreterStillRunningDialog dlg = new InterpreterStillRunningDialog();
        ResponseType response = dlg.run();
        dlg.hide();
        if (response == ResponseType.YES) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean handleModifiedBuffer(boolean isInputFileBuffer, String filename, BufferSaver dc) {
        FileModifiedMessageDialog dlg = new FileModifiedMessageDialog(isInputFileBuffer, filename);
        ResponseType response = dlg.run();
        dlg.hide();
        if (response == ResponseType.YES) { // The user selected to save the changes.
            return dc.saveFile(false);
        } else if (response == ResponseType.NO) {
            return true; // The user selected not to save the changes.
        }else{
            return false; // The user canceled;
        }
    }

    public static String showSaveDialog(SaveDialog saveDialog, File initialFile, String initialFileName) {
        saveDialog.setFilename(initialFileName, initialFile == null ? null : initialFile.getParent());
        ResponseType response = saveDialog.run();
        saveDialog.hide();
        String fname = saveDialog.getFilename();
        if ((response == ResponseType.OK) &&(fname!=null)){
            FileFilterWithExtension f = saveDialog.getFilter();
            String extension = f.getExtension();
            if(!fname.endsWith(extension)){
                fname += extension;
            }
            File target = new File(fname);
            if (target.exists()) {
                if (confirmOverwriteFile(target)) {
                    return target.getAbsolutePath();
                } else {
                    return null;
                }
            } else {
                return target.getAbsolutePath();
            }
        }
        return null;
    }

    public static String showExportToHTMLDialog(File initialFile, String initialFileName) {
        ExportToHTMLDialog saveDialog = ExportToHTMLDialog.getInstance();
        if(initialFileName.endsWith(".gls")){
            initialFileName = initialFileName.substring(0, initialFileName.length()-4);
            initialFileName = initialFileName.concat(".html");
        }
        saveDialog.setFilename(initialFileName, initialFile == null ? null : initialFile.getParent());
        ResponseType response = saveDialog.run();
        saveDialog.hide();
        String fname = saveDialog.getFilename();
        if ((response == ResponseType.OK) &&(fname!=null)){
            File target = new File(fname);
            if (target.exists()) {
                if (DialogManager.confirmOverwriteFile(target)) {
                    return target.getAbsolutePath();
                } else {
                    return null;
                }
            } else {
                return target.getAbsolutePath();
            }
        }
        return null;
    }

    public static boolean confirmOverwriteFile(File f) {
        FileExistsMessageDialog dlg = new FileExistsMessageDialog(f.getAbsolutePath());
        ResponseType response = dlg.run();
        dlg.hide();
        if (response == ResponseType.YES) {
            return true;
        } else {
            return false;
        }
    }

    public static String showOpenDialog(FileChooserDialog openDialog) {
        ResponseType response = openDialog.run();
        openDialog.hide();
        if (response == ResponseType.OK) {
            return openDialog.getFilename();
        }
        return null;
    }

}
