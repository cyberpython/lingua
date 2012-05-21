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

import glossa.ui.cli.CharsetDetector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.actions.NewInputFileAction;
import lingua.ui.gtk.main_window.actions.OpenInputFileAction;
import lingua.ui.gtk.main_window.actions.SaveInputFileAction;
import lingua.ui.gtk.main_window.dialogs.io.BufferSaver;
import lingua.ui.gtk.main_window.dialogs.io.DialogManager;
import lingua.ui.gtk.main_window.dialogs.io.OpenInputFileDialog;
import lingua.ui.gtk.main_window.dialogs.io.SaveInputFileDialog;
import org.gnome.gtk.Box;
import org.gnome.gtk.Orientation;
import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.TextBuffer;
import org.gnome.gtk.TextView;
import org.gnome.gtk.Toolbar;
import org.gnome.pango.FontDescription;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class InputFileWindow extends Box implements BufferSaver {

    private TextView view;
    private TextBuffer buf;
    private static InputFileWindow instance;

    public static InputFileWindow getInstance() {
        if (instance == null) {
            instance = new InputFileWindow();
        }
        return instance;
    }

    private InputFileWindow() {
        super(Orientation.VERTICAL, 0);

        Toolbar toolbar = new Toolbar();
        toolbar.add(NewInputFileAction.getInstance().createToolItem());
        toolbar.add(OpenInputFileAction.getInstance().createToolItem());
        toolbar.add(SaveInputFileAction.getInstance().createToolItem());
        packStart(toolbar, false, true, 0);

        this.buf = new TextBuffer();
        this.view = new TextView(buf);
        ScrolledWindow sc = new ScrolledWindow();
        sc.add(view);
        packStart(sc, true, true, 0);
    }

    public String getText() {
        return this.buf.getText();
    }

    public boolean isModified() {
        return buf.getModified();
    }

    public void setFont(String fontDescription){
        view.overrideFont(new FontDescription(fontDescription));
    }

    private void newBuffer() {
        buf.setText("");
        buf.placeCursor(buf.getIterStart());
        buf.setModified(false);
    }

    public void newBufferFromFile(File src) {
        try {
            CharsetDetector cd = new CharsetDetector();
            Charset c = cd.detectCharset(src,
                    new String[]{"UTF-8", "windows-1253", "ISO-8859-7"});
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(src),
                    c == null ? Charset.defaultCharset() : c));
            String buffer = "";
            StringBuilder sb = new StringBuilder();
            while ((buffer = reader.readLine()) != null) {
                sb.append(buffer);
                sb.append("\n");
            }
            buf.setText(sb.toString());
            buf.placeCursor(buf.getIterStart());
            buf.setModified(false);
        } catch (Exception e) {
        }
    }

    public void newFile() {
        if (buf.getModified()) {
            if (DialogManager.handleModifiedBuffer(true, StringResources.getInstance().getString("untitled"), this)) {
                newBuffer();
            } else {
                return;
            }
        } else {
            newBuffer();
        }
    }

    public void openFile() {
        boolean modified = buf.getModified();
        String filename = null;
        if (modified) {
            if (DialogManager.handleModifiedBuffer(true, StringResources.getInstance().getString("untitled"), this)) {
                filename = DialogManager.showOpenDialog(OpenInputFileDialog.getInstance());
            } else {
                return;
            }
        } else {
            filename = DialogManager.showOpenDialog(OpenInputFileDialog.getInstance());
        }

        if (filename != null) {
            newBufferFromFile(new File(filename));
        }
    }

    public boolean saveFile(boolean saveAs) {

        String filename = DialogManager.showSaveDialog(SaveInputFileDialog.getInstance(), null, StringResources.getInstance().getString("untitled"));
        if (filename != null) {
            File target = new File(filename);
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(target));
                out.write(buf.getText());
                out.flush();
                out.close();
                buf.setModified(false);
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
