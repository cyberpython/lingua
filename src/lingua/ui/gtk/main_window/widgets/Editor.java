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

import glossa.html_generator.GlossaHTMLGenerator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import lingua.preferences.Preferences;
import lingua.pretty_printer.GlossaPrettyPrinter;
import lingua.pretty_printer.PrettyPrinter;
import lingua.ui.gtk.main_window.MainWindow;
import lingua.ui.gtk.main_window.dialogs.filters.GlossaDiermhneythsFileFilter;
import lingua.ui.gtk.main_window.dialogs.io.DialogManager;
import lingua.ui.gtk.main_window.dialogs.io.BufferSaver;
import lingua.ui.gtk.main_window.dialogs.io.OpenCodeDialog;
import lingua.ui.gtk.main_window.dialogs.io.SaveCodeDialog;
import lingua.ui.gtk.main_window.menus.EditorPopupMenu;
import lingua.utils.CharsetDetector;
import lingua.utils.MiscUtils;
import org.gnome.gdk.*;
import org.gnome.gtk.Menu;
import org.gnome.gtk.TextBuffer;
import org.gnome.gtk.Widget;
import org.gnome.pango.FontDescription;
import org.gnome.sourceview.SourceView;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class Editor extends SourceView implements BufferSaver{

    private static Editor instance = null;
    private final Menu popupMenu;
    private PrettyPrinter prettyPrinter;

    public static Editor getInstance() {
        if (instance == null) {
            instance = new Editor();
        }
        return instance;
    }

    private Editor() {
        super(new EditorBuffer());
        popupMenu = new EditorPopupMenu();
        prettyPrinter = new GlossaPrettyPrinter();
        init();
    }

    private void init() {
        EditorBuffer buf = this.getBuffer();
        buf.setEditor(this);
        buf.placeCursor(buf.getIterStart());

        connect(new ButtonPressEvent() {

            public boolean onButtonPressEvent(Widget widget, EventButton eb) {
                if (eb.getButton().equals(MouseButton.RIGHT)) {
                    popupMenu.popup();
                    return true;
                }
                return false;
            }
        });
        
        connect(new KeyReleaseEvent() {

            public boolean onKeyReleaseEvent(Widget widget, EventKey ek) {
                if(ek.getKeyval().equals(Keyval.F) && ek.getState().contains(ModifierType.SHIFT_MASK) && ek.getState().contains(ModifierType.CONTROL_MASK) ){
                    EditorBuffer buf = getBuffer();
                    if(buf!=null){
                        String text = buf.getText();
                        buf.beginUserAction();
                        buf.setText(prettyPrinter.getPrettyText(text));
                        buf.endUserAction();
                    }
                    return true;
                }
                return false;
            }
        });

        connect(new PopupMenu() {

            public boolean onPopupMenu(Widget source) {
                popupMenu.popup();
                return true;
            }
        });
        
        setAutoIndent(true);
        setInsertSpacesInsteadOfTabs(true);
    }

    public void setFont(String fontDescription){
        this.overrideFont(new FontDescription(fontDescription));
    }

    @Override
    public void setEditable(boolean editable) {
        super.setEditable(editable);
        getBuffer().updateControlsSensitivity();
    }



    @Override
    public EditorBuffer getBuffer() {
        return (EditorBuffer) super.getBuffer();
    }

    @Override
    public void setBuffer(TextBuffer buffer) {
        super.setBuffer(buffer);
        buffer.placeCursor(buffer.getIterStart());
        ((EditorBuffer)buffer).setEditor(this);
        MainWindowStatusbar.getInstance().updateMessage();
        MainWindow.getInstance().updateTitle();
        Preferences.getInstance().apply();
    }

    public void newBuffer() {
        EditorBuffer buf = new EditorBuffer();
        buf.setSrc(null);
        buf.setModified(false);
        this.setBuffer(buf);
    }

    public void newBufferFromFile(File src) {
        EditorBuffer buf = new EditorBuffer();
        buf.beginNotUndoableAction();

        try {
            CharsetDetector cd = new CharsetDetector();
            Charset c = cd.detectCharset(src,
                    new String[]{"UTF-8", "windows-1253", "ISO-8859-7"});
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(src),
                    c == null ? Charset.defaultCharset() : c));
            String buffer;
            StringBuilder sb = new StringBuilder();
            while ((buffer = reader.readLine()) != null) {
                sb.append(buffer);
                sb.append("\n");
            }
            buf.setText(sb.toString());
            buf.setSrc(src);
            buf.setModified(false);
        } catch (Exception e) {
        }

        buf.endNotUndoableAction();
        this.setBuffer(buf);
    }

    public void newFile() {
        if(MainWindow.getInstance().stopInterpreterAndContinue()){
            EditorBuffer buf = this.getBuffer();
            boolean modified = buf.getModified();


            if (modified) { // buffer has changes
                if (DialogManager.handleModifiedBuffer(false, buf.getDocumentTitle(), this)) { // The user saved or chose not to
                    this.newBuffer();
                }
            } else {
                this.newBuffer();
            }
        }
    }

    public boolean saveFile(boolean saveAs) {
        EditorBuffer buf = this.getBuffer();
        boolean modified = buf.getModified();
        if (modified || saveAs) {
            File target = buf.getSrc();
            String glossaDiermhneythsExtension = new GlossaDiermhneythsFileFilter().getExtension();
            if ((target == null) || saveAs) { // We have a new file
                String filename = DialogManager.showSaveDialog(SaveCodeDialog.getInstance(), buf.getSrc(), buf.getDocumentTitle());
                if (filename != null) {
                    target = new File(filename);
                    Charset c;
                    if(filename.endsWith(glossaDiermhneythsExtension)){
                        c = Charset.forName("windows-1253");
                    }else{
                        c = Charset.forName("UTF-8");
                    }
                    try {
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), c));
                        out.write(buf.getText());
                        out.flush();
                        out.close();
                        buf.setSrc(target);
                        buf.setModified(false);
                        return true;
                    } catch (IOException e) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else { // We are editing an existing file
                Charset c;
                if(target.getAbsolutePath().endsWith(glossaDiermhneythsExtension)){
                    c = Charset.forName("windows-1253");
                }else{
                    c = Charset.forName("UTF-8");
                }
                try {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), c));
                    out.write(buf.getText());
                    out.flush();
                    out.close();
                    buf.setModified(false);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        } else {
            return true;
        }
    }

    public void openFile() {
        if(MainWindow.getInstance().stopInterpreterAndContinue()){
             EditorBuffer buf = this.getBuffer();
            boolean modified = buf.getModified();
            String filename;
            if (modified) { // buffer has changes
                if (DialogManager.handleModifiedBuffer(false, buf.getDocumentTitle(), this)) { // The user saved or chose not to
                    filename = DialogManager.showOpenDialog(OpenCodeDialog.getInstance());
                } else { // The user cancelled the save dialog
                    return;
                }
            } else {
                filename = DialogManager.showOpenDialog(OpenCodeDialog.getInstance());
            }

            if (filename != null) {
                newBufferFromFile(new File(filename));
            }
        }
    }

    public boolean exportHTML() {
        EditorBuffer buf = this.getBuffer();
        String filename = DialogManager.showExportToHTMLDialog(buf.getSrc(), buf.getDocumentTitle());
        if (filename != null) {
            File target = new File(filename);
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(target));
                out.write(new GlossaHTMLGenerator().getFullHTML(buf.getDocumentTitle(), buf.getText()));
                out.flush();
                out.close();
                MiscUtils.xdgOpenFile(target);
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }

}
