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

package lingua.preferences;

import lingua.ui.gtk.main_window.widgets.Editor;
import lingua.ui.gtk.main_window.widgets.InputFileWindow;
import lingua.ui.gtk.main_window.widgets.RuntimeWindow;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class Preferences extends PreferencesStorage{

    private static Preferences instance;

    private final String DEFAULT_EDITOR_FONT_FAMILY = "Monospace";
    private final int DEFAULT_EDITOR_FONT_SIZE = 11;
    private final int DEFAULT_EDITOR_RIGHT_MARGIN_POSITION = 80;
    private final int DEFAULT_EDITOR_TAB_WIDTH = 4;
    private final boolean DEFAULT_EDITOR_SHOWS_LINE_NUMBERS = true;
    private final boolean DEFAULT_EDITOR_SHOWS_RIGHT_MARGIN = true;
    private final boolean DEFAULT_EDITOR_HIGLIGHTS_CURRENT_LINE = true;
    private final String DEFAULT_EDITOR_SCHEME = "glossa";

    public static Preferences getInstance(){
        if(instance == null){
            instance = new Preferences();
        }
        return instance;
    }

    private Preferences(){
        super("lingua");
    }

    @Override
    public void setupDefaultValues(){
        setValue("editor-font-family", DEFAULT_EDITOR_FONT_FAMILY);
        setValue("editor-font-size", String.valueOf(DEFAULT_EDITOR_FONT_SIZE));
        setValue("editor-shows-line-numbers", String.valueOf(DEFAULT_EDITOR_SHOWS_LINE_NUMBERS));
        setValue("editor-highlights-current-line", String.valueOf(DEFAULT_EDITOR_HIGLIGHTS_CURRENT_LINE));
        setValue("editor-shows-right-margin", String.valueOf(DEFAULT_EDITOR_SHOWS_RIGHT_MARGIN));
        setValue("editor-right-margin-position", String.valueOf(DEFAULT_EDITOR_RIGHT_MARGIN_POSITION));
        setValue("editor-tab-width", String.valueOf(DEFAULT_EDITOR_TAB_WIDTH));
        setValue("editor-scheme", DEFAULT_EDITOR_SCHEME);
    }

    public void apply(){
        String fontDescription = getPangoCompatibleFontDescriptionString();
        InputFileWindow.getInstance().setFont(fontDescription);

        RuntimeWindow.getInstance().setFont(fontDescription);

        Editor ed = Editor.getInstance();
        ed.setFont(fontDescription);
        ed.setShowLineNumbers(getEditorShowsLineNumbers());
        ed.setShowRightMargin(getEditorShowsRightMargin());
        ed.setRightMarginPosition(getEditorRightMarginPosition());
        ed.setTabWidth(getEditorTabWidth());
        ed.setHighlightCurrentLine(getEditorHighlightsCurrentLine());
        ed.getBuffer().setScheme(getEditorScheme());
    }

    public String getPangoCompatibleFontDescriptionString(){
        return getEditorFontFamily() + " " + getEditorFontSize();
    }

    /**
     * @return the editorFontFamily
     */
    public String getEditorFontFamily() {
        String result = getValue("editor-font-family");
        return result==null?DEFAULT_EDITOR_FONT_FAMILY:result;
    }

    /**
     * @param editorFontFamily the editorFontFamily to set
     */
    public void setEditorFontFamily(String editorFontFamily) {
        setValue("editor-font-family", editorFontFamily);
    }
    
    /**
     * @return the editorScheme or null for default
     */
    public String getEditorScheme() {
        String result = getValue("editor-scheme");
        return result==null?DEFAULT_EDITOR_SCHEME:result;
    }

    /**
     * @param editorScheme the editorFontFamily to set
     */
    public void setEditorScheme(String editorScheme) {
        setValue("editor-scheme", "".equals(editorScheme)?null:editorScheme);
    }

    /**
     * @return the editorFontSize
     */
    public int getEditorFontSize() {
        String result =  getValue("editor-font-size");
        return result==null?DEFAULT_EDITOR_FONT_SIZE:Integer.valueOf(result);
    }

    /**
     * @param editorFontSize the editorFontSize to set
     */
    public void setEditorFontSize(int editorFontSize) {
        setValue("editor-font-size", String.valueOf(editorFontSize));
    }

    /**
     * @return the editorShowsLineNumbers
     */
    public boolean getEditorShowsLineNumbers() {
        String result =  getValue("editor-shows-line-numbers");
        return result==null?DEFAULT_EDITOR_SHOWS_LINE_NUMBERS:Boolean.parseBoolean(result);
    }

    /**
     * @param editorShowsLineNumbers the editorShowsLineNumbers to set
     */
    public void setEditorShowsLineNumbers(boolean editorShowsLineNumbers) {
        setValue("editor-shows-line-numbers", String.valueOf(editorShowsLineNumbers));
    }

    /**
     * @return the editorHighlightsCurrentLine
     */
    public boolean getEditorHighlightsCurrentLine() {
        String result =  getValue("editor-highlights-current-line");
        return result==null?DEFAULT_EDITOR_HIGLIGHTS_CURRENT_LINE:Boolean.parseBoolean(result);
    }

    /**
     * @param editorHighlightsCurrentLine the editorHighlightsCurrentLine to set
     */
    public void setEditorHighlightsCurrentLine(boolean editorHighlightsCurrentLine) {
        setValue("editor-highlights-current-line", String.valueOf(editorHighlightsCurrentLine));
    }

    /**
     * @return the editorShowsRightMargin
     */
    public boolean getEditorShowsRightMargin() {
        String result =  getValue("editor-shows-right-margin");
        return result==null?DEFAULT_EDITOR_SHOWS_RIGHT_MARGIN:Boolean.parseBoolean(result);
    }

    /**
     * @param editorShowsRightMargin the editorShowsRightMargin to set
     */
    public void setEditorShowsRightMargin(boolean editorShowsRightMargin) {
        setValue("editor-shows-right-margin", String.valueOf(editorShowsRightMargin));
    }

    /**
     * @return the editorRightMarginPosition
     */
    public int getEditorRightMarginPosition() {
        String result =  getValue("editor-right-margin-position");
        return result==null?DEFAULT_EDITOR_RIGHT_MARGIN_POSITION:Integer.valueOf(result);
    }

    /**
     * @param editorRightMarginPosition the editorRightMarginPosition to set
     */
    public void setEditorRightMarginPosition(int editorRightMarginPosition) {
        setValue("editor-right-margin-position", String.valueOf(editorRightMarginPosition));
    }

    /**
     * @return the editorTabWidth
     */
    public int getEditorTabWidth() {
        String result =  getValue("editor-tab-width");
        return result==null?DEFAULT_EDITOR_TAB_WIDTH:Integer.valueOf(result);
    }

    /**
     * @param editorTabWidth the editorTabWidth to set
     */
    public void setEditorTabWidth(int editorTabWidth) {
        setValue("editor-tab-width", String.valueOf(editorTabWidth));
    }

    public String getDefaultEditorScemeId(){
        return DEFAULT_EDITOR_SCHEME;
    }


}
