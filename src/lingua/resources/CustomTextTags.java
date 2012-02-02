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

package lingua.resources;

import java.util.HashMap;
import org.gnome.gtk.TextTag;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class CustomTextTags {

    private HashMap<String, TextTag> customTags;

    private static CustomTextTags instance = null;

    public static CustomTextTags getInstance(){
        if(instance == null){
            instance = new CustomTextTags();
        }
        return instance;
    }

    private CustomTextTags() {
        this.customTags = new HashMap<String, TextTag>();
        initCustomTags();
    }

    private void initCustomTags(){
        
        TextTag tag = new TextTag();
        //error tag
        tag.setParagraphBackground("#FFA2A7");
        tag.setBackground("#FFA2A7");
        tag.setForeground("#000000");
        customTags.put("error", tag);

        tag = new TextTag();
        tag.setBackground("#FFD700");
        tag.setParagraphBackground("#FFD700");
        tag.setForeground("#000000");
        customTags.put("warning", tag);

        tag = new TextTag();
        tag.setBackground("#DCFF00");
        tag.setParagraphBackground("#DCFF00");
        tag.setForeground("#000000");
        customTags.put("exec_highlight", tag);

        tag = new TextTag();
        tag.setForeground("#FF0000");
        customTags.put("runtime_error", tag);

        tag = new TextTag();
        tag.setBackground("#FFDA00");
        tag.setForeground("#000000");
        customTags.put("search_result", tag);
    }

    public TextTag getTag(String tagName) {
        return this.customTags.get(tagName);
    }


}
