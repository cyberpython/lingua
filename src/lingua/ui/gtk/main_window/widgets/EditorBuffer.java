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

import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.MainWindow;
import lingua.ui.gtk.main_window.actions.RedoAction;
import lingua.ui.gtk.main_window.actions.UndoAction;
import java.io.File;
import lingua.resources.CustomTextTags;
import lingua.ui.gtk.main_window.actions.CopyAction;
import lingua.ui.gtk.main_window.actions.CutAction;
import lingua.ui.gtk.main_window.actions.DeleteAction;
import lingua.ui.gtk.main_window.actions.PasteAction;
import org.gnome.gtk.TextBuffer;
import org.gnome.gtk.TextIter;
import org.gnome.gtk.TextMark;
import org.gnome.gtk.TextTag;
import org.gnome.sourceview.LanguageManager;
import org.gnome.sourceview.SourceBuffer;
import org.gnome.sourceview.StyleSchemeManager;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class EditorBuffer extends SourceBuffer {

    private File src;
    private boolean modified;
    private String searchTerm;
    private Editor editor;

    public EditorBuffer() {
        src = null;
        searchTerm = "";
        editor = null;
        updateUndoRedoSensitivity();
        updateSelectionActionsSensitivity(false);

        setLanguage(LanguageManager.getDefault().getLanguage("glossa"));

        connect(new Changed() {

            public void onChanged(TextBuffer tb) {
                clearHighlight("error");
                clearHighlight("warning");
                clearHighlight("exec_highlight");
                highlightSearchTerm(searchTerm);
                updateUndoRedoSensitivity();
                setModified(true);
                TextIter cursor = getInsert().getIter();
                MainWindowStatusbar.getInstance().updateCursor(cursor.getLineOffset(), cursor.getLine());
            }
        });

        connect(new NotifyCursorPosition() {

            public void onNotifyCursorPosition(TextBuffer tb) {
                TextIter cursor = getInsert().getIter();
                MainWindowStatusbar.getInstance().updateCursor(cursor.getLineOffset(), cursor.getLine());
            }
        });

        connect(new MarkSet() {

            public void onMarkSet(TextBuffer tb, TextIter ti, TextMark tm) {
                boolean hasSelection = getHasSelection();
                updateSelectionActionsSensitivity(hasSelection);
            }
        });
    }

    public void setScheme(String schemeID){
        StyleSchemeManager styleMgr = StyleSchemeManager.getDefault();
        if (schemeID != null) {
            String[] ids = styleMgr.getSchemeIDs();
            for (String id : ids) {
                if(id.equalsIgnoreCase(schemeID)){
                    this.setStyleScheme(styleMgr.getScheme(id));
                    break;
                }
            }
        }
    }

    public void updateControlsSensitivity() {
        boolean hasSelection = getHasSelection();
        updateSelectionActionsSensitivity(hasSelection);
        updateUndoRedoSensitivity();
        PasteAction.getInstance().setSensitive(editor == null ? true : editor.getEditable());
    }

    private void updateUndoRedoSensitivity() {
        boolean editable = editor == null ? true : editor.getEditable();
        RedoAction.getInstance().setSensitive(canRedo() && editable);
        UndoAction.getInstance().setSensitive(canUndo() && editable);
    }

    private void updateSelectionActionsSensitivity(boolean sensitive) {
        boolean editable = editor == null ? true : editor.getEditable();
        CutAction.getInstance().setSensitive(sensitive && editable);
        CopyAction.getInstance().setSensitive(sensitive);
        DeleteAction.getInstance().setSensitive(sensitive && editable);
    }

    public void setEditor(Editor e) {
        editor = e;
    }

    @Override
    public boolean getModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean modified) {
        super.setModified(modified);
        this.modified = modified;
        updateStatusbarAndTitle(modified);
    }

    private void updateStatusbarAndTitle(boolean modified) {
        MainWindowStatusbar.getInstance().updateModified(modified);
        MainWindow.getInstance().updateTitle();
    }

    public String getDocumentTitle() {
        if (src == null) {
            TextIter firstLineEnd = getIterStart();
            firstLineEnd.forwardLine();
            String firstLine = getText(getIterStart(), firstLineEnd, false).trim().toLowerCase();
            if(firstLine.startsWith("πρόγραμμα")||firstLine.startsWith("πρoγραμμα")){
                return firstLine.substring(9).trim();
            }else{
                return StringResources.getInstance().getString("untitled");
            }
        }
        return src.getName();
    }

    @Override
    public void undo() {
        super.undo();
        updateUndoRedoSensitivity();
    }

    @Override
    public void redo() {
        super.redo();
        updateUndoRedoSensitivity();
    }

    public File getSrc() {
        return src;
    }

    public void setSrc(File src) {
        this.src = src;
        MainWindowStatusbar.getInstance().updateSrcFile(src);
    }

    public void clearHighlight(String tagName) {
        TextTag tag = CustomTextTags.getInstance().getTag(tagName);
        if (tag != null) {
            removeTag(tag, getIterStart(), getIterEnd());
        }
    }

    public void highlightLine(int line, String tagName) {
        TextTag tag = CustomTextTags.getInstance().getTag(tagName);
        if (tag != null) {
            clearHighlight(tagName);
            TextIter iter = getIterStart();
            iter.setLine(line);
            TextIter iter2 = getIterStart();
            iter2.setLine(line);
            iter2.forwardLine();
            applyTag(tag, iter, iter2);
        }
    }

    public void highlightSearchTerm(String searchTerm) {
        clearHighlight("search_result");

        String term = searchTerm.toLowerCase();
        this.searchTerm = term;
        int currentIndex = 0;
        int foundAtIndex = -1;
        int termLength = term.length();
        TextTag searchTag = CustomTextTags.getInstance().getTag("search_result");

        if (termLength > 0) {

            String text = this.getText().toLowerCase();
            foundAtIndex = text.indexOf(term);
            currentIndex = foundAtIndex + termLength;
            while (foundAtIndex >= 0) {
                TextIter start = getIterStart();
                TextIter end = getIterStart();
                start.forwardChars(foundAtIndex);
                end.forwardChars(foundAtIndex + termLength);
                applyTag(searchTag, start, end);
                foundAtIndex = text.indexOf(term, currentIndex);
                currentIndex = foundAtIndex + termLength;
            }

        }
    }

    public void search(String searchTerm) {
        String term = searchTerm.toLowerCase();
        int termLength = term.length();
        if (termLength > 0) {
            String text = this.getText().toLowerCase();
            int currentIndex = getCursorPosition();

            int foundAtIndex = text.indexOf(term, currentIndex);
            if (foundAtIndex >= 0) {
                TextIter start = getIterStart();
                TextIter end = getIterStart();
                start.forwardChars(foundAtIndex);
                end.forwardChars(foundAtIndex + termLength);
                selectRange(start, end);
                Editor.getInstance().scrollTo(start);
            } else {
                foundAtIndex = text.indexOf(term);
                if (foundAtIndex >= 0) {
                    TextIter start = getIterStart();
                    TextIter end = getIterStart();
                    start.forwardChars(foundAtIndex);
                    end.forwardChars(foundAtIndex + termLength);
                    this.selectRange(start, end);
                    Editor.getInstance().scrollTo(start);
                }
            }

        }
    }
}
