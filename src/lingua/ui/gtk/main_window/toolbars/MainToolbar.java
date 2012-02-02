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

package lingua.ui.gtk.main_window.toolbars;

import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.actions.CopyAction;
import lingua.ui.gtk.main_window.actions.CutAction;
import lingua.ui.gtk.main_window.actions.NewFileAction;
import lingua.ui.gtk.main_window.actions.OpenFileAction;
import lingua.ui.gtk.main_window.actions.PasteAction;
import lingua.ui.gtk.main_window.actions.PrintAction;
import lingua.ui.gtk.main_window.actions.RedoAction;
import lingua.ui.gtk.main_window.actions.RunAction;
import lingua.ui.gtk.main_window.actions.SaveFileAction;
import lingua.ui.gtk.main_window.actions.StopAction;
import lingua.ui.gtk.main_window.actions.ToggleStepByStepAction;
import lingua.ui.gtk.main_window.actions.ToggleUseInputFileAction;
import lingua.ui.gtk.main_window.actions.UndoAction;
import lingua.ui.gtk.main_window.widgets.Editor;
import org.gnome.gtk.Adjustment;
import org.gnome.gtk.Entry;
import org.gnome.gtk.EntryIconPosition;
import org.gnome.gtk.Orientation;
import org.gnome.gtk.PositionType;
import org.gnome.gtk.Scale;
import org.gnome.gtk.SeparatorToolItem;
import org.gnome.gtk.Stock;
import org.gnome.gtk.StyleClass;
import org.gnome.gtk.ToolItem;
import org.gnome.gtk.Toolbar;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class MainToolbar extends Toolbar{

    private final Entry searchBox;
    private final Scale scale;
    private static MainToolbar instance = null;

    public static MainToolbar getInstance(){
        if(instance == null){
            instance = new MainToolbar();
        }
        return instance;
    }

    private MainToolbar() {
        this.getStyleContext().addClass(StyleClass.PRIMARY_TOOLBAR);

        add(NewFileAction.getInstance().createToolItem());
        add(OpenFileAction.getInstance().createToolItem());
        add(SaveFileAction.getInstance().createToolItem());

        add(new SeparatorToolItem());

        add(PrintAction.getInstance().createToolItem());

        add(new SeparatorToolItem());

        add(UndoAction.getInstance().createToolItem());
        add(RedoAction.getInstance().createToolItem());

        add(new SeparatorToolItem());

        add(CutAction.getInstance().createToolItem());
        add(CopyAction.getInstance().createToolItem());
        add(PasteAction.getInstance().createToolItem());

        add(new SeparatorToolItem());


        StringResources strRes = StringResources.getInstance();

        add(RunAction.getInstance().createToolItem());
        add(ToggleStepByStepAction.getInstance().createToolItem());
        add(StopAction.getInstance().createToolItem());
        add(ToggleUseInputFileAction.getInstance().createToolItem());


        ToolItem wrapper = new ToolItem();
        scale = new Scale(Orientation.HORIZONTAL, new Adjustment(100, 0, 120, 1, 20, 20));
        scale.setSizeRequest(100, 20);
        scale.setValuePosition(PositionType.LEFT);
        scale.setDigits(0);
        scale.setTooltipText(strRes.getString("tooltip_execution_speed"));
        wrapper.add(scale);
        add(wrapper);

        SeparatorToolItem expander = new SeparatorToolItem();
        expander.setDraw(false);
        expander.setExpand(true);
        add(expander);

        wrapper = new ToolItem();
        searchBox = new Entry();
        searchBox.setIconFromStock(EntryIconPosition.PRIMARY, Stock.FIND);
        wrapper.add(searchBox);
        searchBox.connect(new Entry.Changed() {

            public void onChanged(Entry source) {
                Editor.getInstance().getBuffer().highlightSearchTerm(source.getText());
            }
        });
        searchBox.connect(new Entry.Activate() {

            public void onActivate(Entry source) {
                Editor.getInstance().getBuffer().search(source.getText());
            }
        });
        add(wrapper);
        
    }

    public void setDelayControlsSensitive(boolean sensitive){
        this.scale.setSensitive(sensitive);
    }

    public int getDelayBetweenSteps(){
        return 5000 -  ((int)this.scale.getValue())*50;
    }

}
