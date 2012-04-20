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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import lingua.preferences.Preferences;
import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.MainWindow;
import lingua.ui.gtk.main_window.widgets.Editor;
import org.freedesktop.icons.ActionIcon;
import org.gnome.gdk.Keyval;
import org.gnome.gdk.ModifierType;
import org.gnome.gtk.Action;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class PrintAction extends Action {

    private static PrintAction instance = null;

    public static PrintAction getInstance() {
        if (instance == null) {
            instance = new PrintAction();
        }
        return instance;
    }

    private PrintAction() {
        super("PrintAction", StringResources.getInstance().getString("file_print"),
                StringResources.getInstance().getString("tooltip_file_print"),
                ActionIcon.DOCUMENT_PRINT, new Activate() {

            public void onActivate(Action source) {
                try {
                    File path = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile();
                    File tmpFile = File.createTempFile("lingua", ".gls");
                    tmpFile.deleteOnExit();
                    BufferedWriter out = new BufferedWriter(new FileWriter(tmpFile));
                    out.write(Editor.getInstance().getBuffer().getText());
                    out.flush();
                    out.close();
                    Preferences prefs = Preferences.getInstance();
                    String scheme = "glossa";//prefs.getEditorScheme();
                    String[] cmdarr = {
                        "/usr/bin/python",
                        path.getAbsolutePath()+"/source_printer.py",
                        "glossa",
                        scheme,//scheme==null?"glossa":scheme,
                        prefs.getPangoCompatibleFontDescriptionString(),
                        tmpFile.getAbsolutePath()
                    };
                    ProcessBuilder pb = new ProcessBuilder(cmdarr);
                    pb.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        setAccelerator(MainWindow.getAcceleratorGroup(), Keyval.P, ModifierType.CONTROL_MASK);

    }
}
