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
import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.ComboBox;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.TreeIter;
import org.gnome.sourceview.StyleSchemeManager;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class SchemesCombo extends ComboBox {

    private ListStore model;
    private final DataColumnString schemeName;
    private final DataColumnString schemeId;

    public SchemesCombo() {
        this.model = new ListStore(new DataColumn[]{
                    schemeName = new DataColumnString(),
                    schemeId = new DataColumnString()
                });

        setModel(model);
        CellRendererText renderer = new CellRendererText(this);
        renderer.setText(schemeName);

        connect(new Changed() {

            public void onChanged(ComboBox source) {
                Preferences prefs = Preferences.getInstance();
                prefs.setEditorScheme(getActiveSchemeId());
                prefs.apply();
            }
        });
    }

    public void updateValues() {

        StyleSchemeManager sm = StyleSchemeManager.getDefault();
        clear();
        String[] schemeIds = sm.getSchemeIDs();
        for (String id : schemeIds) {
            addScheme(sm.getScheme(id).getName(), id);
        }
        setActiveScheme(Preferences.getInstance().getEditorScheme());
    }

    public void clear() {
        this.model.clear();
    }

    public void addScheme(String name, String id) {
        TreeIter row = this.model.appendRow();
        model.setValue(row, schemeName, name);
        model.setValue(row, schemeId, id);
    }

    public String getActiveSchemeId() {
        return model.getValue(getActiveIter(), schemeId);
    }

    public void setActiveScheme(String id) {
        id = id==null?Preferences.getInstance().getDefaultEditorScemeId():id;
        TreeIter iter = model.getIterFirst();
        do {
            if (id.equals(model.getValue(iter, schemeId))) {
                setActiveIter(iter);
                break;
            }
        } while (iter.iterNext());
    }
}
