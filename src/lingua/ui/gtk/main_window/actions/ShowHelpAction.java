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

import java.io.File;
import java.net.URISyntaxException;
import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.MainWindow;
import lingua.utils.MiscUtils;
import org.freedesktop.icons.ActionIcon;
import org.gnome.gdk.Keyval;
import org.gnome.gdk.ModifierType;
import org.gnome.gtk.Action;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class ShowHelpAction extends Action {

    private static ShowHelpAction instance = null;

    public static ShowHelpAction getInstance() {
        if (instance == null) {
            instance = new ShowHelpAction();
        }
        return instance;
    }

    private ShowHelpAction() {
        super("ShowHelpAction",
                StringResources.getInstance().getString("show_help_contents"),
                StringResources.getInstance().getString("show_help_contents"),
                ActionIcon.HELP_CONTENTS, new Activate() {

            public void onActivate(Action action) {
                try {
                    MiscUtils.xdgOpenFile(new File(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getAbsolutePath() + "/help/index.html"));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        setAccelerator(MainWindow.getAcceleratorGroup(), Keyval.F1, ModifierType.NONE);
    }
}
