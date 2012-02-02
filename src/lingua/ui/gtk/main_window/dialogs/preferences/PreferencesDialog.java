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
package lingua.ui.gtk.main_window.dialogs.preferences;

import lingua.preferences.Preferences;
import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.MainWindow;
import org.gnome.gtk.Align;
import org.gnome.gtk.Button;
import org.gnome.gtk.Dialog;
import org.gnome.gtk.FontButton;
import org.gnome.gtk.Grid;
import org.gnome.gtk.Label;
import org.gnome.gtk.ResponseType;
import org.gnome.gtk.Stock;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class PreferencesDialog extends Dialog {

    private Grid grid;
    private FontButton fontButton;
    private SchemesCombo schemesCombo;
    private static PreferencesDialog instance = null;

    public static PreferencesDialog getInstance() {
        if (instance == null) {
            instance = new PreferencesDialog();
        }
        return instance;
    }

    private PreferencesDialog() {
        super(StringResources.getInstance().getString("preferences"), MainWindow.getInstance(), false);
        createUI();

        connect(new Response() {

            public void onResponse(Dialog source, ResponseType response) {
                Preferences.getInstance().savePreferences();
                hide();
            }
        });
    }

    private void createUI() {
        Button closeButton = this.addButton(Stock.CLOSE, ResponseType.CLOSE);
        closeButton.setCanDefault(true);
        this.setDefaultResponse(ResponseType.CLOSE);

        StringResources strRes = StringResources.getInstance();

        grid = new Grid();
        grid.setColumnSpacing(10);

        Label lb1 = new Label(strRes.getString("font"));
        lb1.setAlignHorizontal(Align.START);
        grid.attach(lb1, 0, 0, 2, 1);

        lb1 = new Label(strRes.getString("editor_scheme"));
        lb1.setAlignHorizontal(Align.START);
        grid.attach(lb1, 2, 0, 2, 1);

        fontButton = new FontButton();
        fontButton.setExpandHorizontal(true);
        fontButton.connect(new FontButton.FontSet() {

            public void onFontSet(FontButton source) {
                String result = source.getFontName();
                int delimeterIndex = result.lastIndexOf(" ");
                if (delimeterIndex != -1) {
                    String fontFamily = result.substring(0, delimeterIndex);
                    int fontSize = Integer.parseInt(result.substring(delimeterIndex + 1));
                    Preferences prefs = Preferences.getInstance();
                    prefs.setEditorFontFamily(fontFamily);
                    prefs.setEditorFontSize(fontSize);
                    prefs.apply();
                }
            }
        });
        grid.attach(fontButton, 1, 1, 1, 1);

        schemesCombo = new SchemesCombo();
        schemesCombo.setExpandHorizontal(true);
        grid.attach(schemesCombo, 3, 1, 1, 1);

        this.add(grid);

        this.setValues();

        this.setDefaultSize(500, 400);

        this.showAll();
    }

    private void setValues() {
        Preferences prefs = Preferences.getInstance();
        fontButton.setFontName(prefs.getPangoCompatibleFontDescriptionString());
        schemesCombo.updateValues();
    }
}
