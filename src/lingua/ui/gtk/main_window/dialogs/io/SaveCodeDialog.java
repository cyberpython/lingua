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

package lingua.ui.gtk.main_window.dialogs.io;

import lingua.ui.gtk.main_window.dialogs.filters.FileFilterWithExtension;
import lingua.ui.gtk.main_window.dialogs.filters.GlossaDiermhneythsFileFilter;
import lingua.ui.gtk.main_window.dialogs.filters.GlossaFileFilter;
import org.gnome.gtk.FileFilter;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class SaveCodeDialog extends SaveDialog{
    
    private static SaveCodeDialog instance = null;

    public static SaveCodeDialog getInstance(){
        if(instance == null){
            instance = new SaveCodeDialog();
        }
        return instance;
    }

    private SaveCodeDialog() {
        FileFilterWithExtension filter = new GlossaFileFilter();
        addFilter(filter);
        setFilter(filter);
        filter = new GlossaDiermhneythsFileFilter();
        addFilter(filter);
    }

    @Override
    public String getFilename() {
        String result = super.getFilename();
        if(result!=null){
           FileFilter f = getFilter();
           if(new GlossaFileFilter().equals(f) &&!result.endsWith(".gls")){
               result = result.concat(".gls");
           }else if(new GlossaDiermhneythsFileFilter().equals(f) &&!result.endsWith(".γλώσσα")){
               result = result.concat(".γλώσσα");
           }
        }
        return result;
    }

}
