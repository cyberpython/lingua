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
import lingua.ui.gtk.main_window.dialogs.messages.LinguaAboutDialog;
import org.freedesktop.icons.ActionIcon;
import org.gnome.gtk.Action;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class ShowAboutDialogAction extends Action{
    private static ShowAboutDialogAction instance = null;

    public static ShowAboutDialogAction getInstance(){
        if(instance == null){
            instance = new ShowAboutDialogAction();
        }
        return instance;
    }


    private ShowAboutDialogAction() {
        super("ShowAboutDialogAction",
                StringResources.getInstance().getString("show_about_dialog"),
                StringResources.getInstance().getString("show_about_dialog"),
                ActionIcon.HELP_ABOUT, new Activate() {

            public void onActivate(Action source) {
                LinguaAboutDialog dlg = LinguaAboutDialog.getInstance();
                dlg.present();
            }
        });
    }

}
