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

import java.io.File;
import lingua.resources.StringResources;
import org.gnome.gtk.Statusbar;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class MainWindowStatusbar extends Statusbar{
    private static  MainWindowStatusbar instance = null;

    private String cursorColText;
    private String cursorLineText;
    private String modifiedText;

    private int cursorCol;
    private int cursorLine;
    private boolean modified;
    private File srcFile;

    private MainWindowStatusbar() {
        StringResources strRes = StringResources.getInstance();
        this.cursorColText = strRes.getString("cursor_col");
        this.cursorLineText = strRes.getString("cursor_line");
        this.modifiedText = strRes.getString("modified");
        this.cursorCol = 0;
        this.cursorLine = 0;
        this.modified = false;
        this.srcFile = null;
        updateMessage();
    }

    public static MainWindowStatusbar getInstance(){
        if(instance == null){
            instance = new MainWindowStatusbar();
        }
        return instance;
    }

    public void updateCursor(int x, int y){
        this.cursorCol = x;
        this.cursorLine = y;
        updateMessage();
    }

    public void updateModified(boolean modified){
        this.modified = modified;
        updateMessage();
    }

    public void updateSrcFile(File f){
        this.srcFile = f;
        updateMessage();
    }

    public void updateMessage(){
        StringBuilder msg = new StringBuilder();
        msg.append(cursorLineText);
        msg.append(": ");
        msg.append(cursorLine+1);
        msg.append(", ");
        msg.append(cursorColText);
        msg.append(": ");
        msg.append(cursorCol+1);
        if(modified){
            msg.append(" | ");
            msg.append(modifiedText);
        }
        if(srcFile != null){
            msg.append(" | ");
            msg.append(srcFile.getAbsolutePath());
        }
        this.setMessage(msg.toString());
    }



}
