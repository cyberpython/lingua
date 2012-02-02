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

package lingua.ui.gtk.main_window.actions;

import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.widgets.Editor;
import org.freedesktop.icons.ActionIcon;
import org.gnome.gtk.Action;
import org.gnome.gtk.Action.Activate;
import org.gnome.gtk.Clipboard;
import org.gnome.gtk.TextIter;
import org.gnome.sourceview.SourceBuffer;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class CopyAction extends Action{
    private static CopyAction instance = null;

    public static CopyAction getInstance(){
        if(instance == null){
            instance = new CopyAction();
        }
        return instance;
    }

    public CopyAction() {
        super("CopyAction",
                StringResources.getInstance().getString("edit_copy"),
                StringResources.getInstance().getString("tooltip_edit_copy"),
                ActionIcon.EDIT_COPY, new Activate() {

            public void onActivate(Action action) {
                SourceBuffer srcBuf = Editor.getInstance().getBuffer();
                if(srcBuf.getHasSelection()){
                    TextIter start = srcBuf.getSelectionBound().getIter();
                    TextIter end = srcBuf.getInsert().getIter();
                    String text = srcBuf.getText(start, end, true);
                    Clipboard clip = Clipboard.getDefault();
                    clip.setText(text);
                    clip.store();
                }

            }
        });
    }

}
