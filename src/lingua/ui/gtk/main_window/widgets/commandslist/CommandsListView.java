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

package lingua.ui.gtk.main_window.widgets.commandslist;

import lingua.ui.gtk.main_window.widgets.Editor;
import lingua.ui.gtk.main_window.widgets.EditorBuffer;
import lingua.utils.MiscUtils;
import org.gnome.gtk.*;

/**
 *
 * @author cyberpython
 */
public class CommandsListView extends  TreeView {
    
    private ListStore model;
    private final DataColumnString commandLabel;
    private final DataColumnReference<CommandsListItem> command;

    public CommandsListView() {
        this.model = new ListStore(new DataColumn[]{
                    commandLabel = new DataColumnString(),
                    command = new DataColumnReference<CommandsListItem>()
                });

        setModel(model);

        TreeViewColumn commandCol = appendColumn();
        setHeadersVisible(false);

        CellRendererText textRenderer = new CellRendererText(commandCol);
        textRenderer.setText(commandLabel);
        
        this.populateCommandsList();
        
        connect(new TreeView.RowActivated() {

            public void onRowActivated(TreeView source, TreePath path, TreeViewColumn vertical) {
                final TreeIter row;
                row = model.getIter(path);
                CommandsListItem item = model.getValue(row, command);
                
                Editor ed = Editor.getInstance();
                EditorBuffer buf = ed.getBuffer();
                
                buf.beginUserAction();
                
                TextIter start = buf.getIter(buf.getInsert());
                TextIter end = buf.getIter(buf.getSelectionBound());
                String selectedText = buf.getText(start, end, true);
                
                buf.delete(start, end);
                
                
                
                start = buf.getIter(buf.getInsert());
                start.setLineOffset(0);
                StringBuilder indentation = new StringBuilder();
                int c = start.getChar();
                while(MiscUtils.isSpaceOrTab(c)){
                    indentation.append(Character.toChars(c));
                    start.forwardChar();
                    c = start.getChar();
                }
                boolean isLineEmpty = start.endsLine();
                
                start = buf.getIter(buf.getInsert());
                buf.insert(start, item.getText(selectedText, indentation.toString(), isLineEmpty));
                
                buf.endUserAction();
                
                ed.grabFocus();
            }
        });
    }
    
    private void populateCommandsList(){
        addCommand(new CommandsListItemInline("div", "div", ""));
        addCommand(new CommandsListItemInline("mod", "mod", ""));
        addCommand(new CommandsListItemSeparator(null, null, null));
        addCommand(new CommandsListItemInline("Ή", "Ή", ""));
        addCommand(new CommandsListItemInline("ΚΑΙ", "ΚΑΙ", ""));
        addCommand(new CommandsListItemInline("ΌΧΙ", "ΌΧΙ", ""));
        addCommand(new CommandsListItemSeparator(null, null, null));
        addCommand(new CommandsListItemEncapsulatingTextEx("ΠΡΟΓΡΑΜΜΑ..ΑΡΧΗ..ΤΕΛΟΣ_ΠΡΟΓΡΑΜΜΑΤΟΣ", "ΠΡΟΓΡΑΜΜΑ ", "ΑΡΧΗ", "ΤΕΛΟΣ_ΠΡΟΓΡΑΜΜΑΤΟΣ "));
        addCommand(new CommandsListItemSeparator(null, null, null));
        addCommand(new CommandsListItemLineStartingBlock("ΣΤΑΘΕΡΕΣ", "ΣΤΑΘΕΡΕΣ", ""));
        addCommand(new CommandsListItemLineStartingBlock("ΜΕΤΑΒΛΗΤΕΣ", "ΜΕΤΑΒΛΗΤΕΣ", ""));
        addCommand(new CommandsListItemSeparator(null, null, null));
        addCommand(new CommandsListItemLineUnfinished("ΑΚΕΡΑΙΕΣ:", "ΑΚΕΡΑΙΕΣ: ", ""));
        addCommand(new CommandsListItemLineUnfinished("ΠΡΑΓΜΑΤΙΚΕΣ:", "ΠΡΑΓΜΑΤΙΚΕΣ: ", ""));
        addCommand(new CommandsListItemLineUnfinished("ΧΑΡΑΚΤΗΡΕΣ:", "ΧΑΡΑΚΤΗΡΕΣ: ", ""));
        addCommand(new CommandsListItemLineUnfinished("ΛΟΓΙΚΕΣ:", "ΛΟΓΙΚΕΣ: ", ""));
        addCommand(new CommandsListItemSeparator(null, null, null));
        addCommand(new CommandsListItemLineUnfinished("ΓΡΑΨΕ", "ΓΡΑΨΕ ", ""));
        addCommand(new CommandsListItemLineUnfinished("ΔΙΑΒΑΣΕ", "ΔΙΑΒΑΣΕ ", ""));
        addCommand(new CommandsListItemLineUnfinished("ΚΑΛΕΣΕ", "ΚΑΛΕΣΕ ", ""));
        addCommand(new CommandsListItemSeparator(null, null, null));
        addCommand(new CommandsListItemEncapsulatingText("ΑΝ..ΤΟΤΕ..ΤΕΛΟΣ_ΑΝ", "ΑΝ  ΤΟΤΕ", "ΤΕΛΟΣ_ΑΝ"));
        addCommand(new CommandsListItemLineStartingBlock("ΑΛΛΙΩΣ", "ΑΛΛΙΩΣ", ""));
        addCommand(new CommandsListItemLineUnfinishedStartingBlock("ΑΛΛΙΩΣ_AN..", "ΑΛΛΙΩΣ_AN ", ""));
        addCommand(new CommandsListItemSeparator(null, null, null));
        addCommand(new CommandsListItemEncapsulatingText("ΕΠΙΛΕΞΕ..ΤΕΛΟΣ_ΕΠΙΛΟΓΩΝ", "ΕΠΙΛΕΞΕ ", "ΤΕΛΟΣ_ΕΠΙΛΟΓΩΝ"));
        addCommand(new CommandsListItemLineUnfinishedStartingBlock("ΠΕΡΙΠΤΩΣΗ", "ΠΕΡΙΠΤΩΣΗ ", ""));
        addCommand(new CommandsListItemLineStartingBlock("ΠΕΡΙΠΤΩΣΗ ΑΛΛΙΩΣ", "ΠΕΡΙΠΤΩΣΗ ΑΛΛΙΩΣ", ""));
        addCommand(new CommandsListItemSeparator(null, null, null));
        addCommand(new CommandsListItemEncapsulatingText("ΓΙΑ..ΑΠΟ..ΜΕΧΡΙ..", "ΓΙΑ  ΑΠΟ  ΜΕΧΡΙ ", "ΤΕΛΟΣ_ΕΠΑΝΑΛΗΨΗΣ"));
        addCommand(new CommandsListItemEncapsulatingText("ΌΣΟ..ΕΠΑΝΑΛΑΒΕ..ΤΕΛΟΣ_ΕΠΑΝΑΛΗΨΗΣ", "ΌΣΟ  ΕΠΑΝΑΛΑΒΕ", "ΤΕΛΟΣ_ΕΠΑΝΑΛΗΨΗΣ"));
        addCommand(new CommandsListItemEncapsulatingText("ΑΡΧΗ_ΕΠΑΝΑΛΗΨΗΣ..ΜΕΧΡΙΣ_ΌΤΟΥ", "ΑΡΧΗ_ΕΠΑΝΑΛΗΨΗΣ", "ΜΕΧΡΙΣ_ΌΤΟΥ "));
        addCommand(new CommandsListItemSeparator(null, null, null));
        addCommand(new CommandsListItemEncapsulatingTextEx("ΔΙΑΔΙΚΑΣΙΑ..()..ΑΡΧΗ..ΤΕΛΟΣ_ΔΙΑΔΙΚΑΣΙΑΣ", "ΔΙΑΔΙΚΑΣΙΑ ()", "ΑΡΧΗ", "ΤΕΛΟΣ_ΔΙΑΔΙΚΑΣΙΑΣ"));
        addCommand(new CommandsListItemEncapsulatingTextEx("ΣΥΝΑΡΤΗΣΗ..():..ΑΡΧΗ..ΤΕΛΟΣ_ΣΥΝΑΡΤΗΣΗΣ", "ΣΥΝΑΡΤΗΣΗ ():", "ΑΡΧΗ", "ΤΕΛΟΣ_ΣΥΝΑΡΤΗΣΗΣ"));
        addCommand(new CommandsListItemInline("ΑΚΕΡΑΙΑ", "ΑΚΕΡΑΙΑ", ""));
        addCommand(new CommandsListItemInline("ΠΡΑΓΜΑΤΙΚΗ", "ΠΡΑΓΜΑΤΙΚΗ", ""));
        addCommand(new CommandsListItemInline("ΧΑΡΑΚΤΗΡΑΣ", "ΧΑΡΑΚΤΗΡΑΣ", ""));
        addCommand(new CommandsListItemInline("ΛΟΓΙΚΗ", "ΛΟΓΙΚΗ", ""));
    }
    
    public void addCommand(CommandsListItem item) {
        TreeIter row = this.model.appendRow();
        this.model.setValue(row, commandLabel, item.getLabel());
        this.model.setValue(row, command, item);
    }
    
}
