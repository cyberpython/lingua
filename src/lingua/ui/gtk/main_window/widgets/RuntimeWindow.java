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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import lingua.resources.CustomTextTags;
import lingua.resources.StringResources;
import org.gnome.gdk.RGBA;
import org.gnome.gtk.Box;
import org.gnome.gtk.Button;
import org.gnome.gtk.Entry;
import org.gnome.gtk.Orientation;
import org.gnome.gtk.PolicyType;
import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.StateFlags;
import org.gnome.gtk.TextBuffer;
import org.gnome.gtk.TextIter;
import org.gnome.gtk.TextTag;
import org.gnome.gtk.TextView;
import org.gnome.gtk.WrapMode;
import org.gnome.pango.FontDescription;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class RuntimeWindow extends Box{

    private InputStream in;
    private OutputStream pipedOutput;
    
    private RuntimeWindowPrintStream out;
    private RuntimeWindowPrintStream err;

    private TextView view;
    private TextBuffer buf;
    private TextTag errorTag;

    private Button submitButton;
    private Entry inputEntry;

    private static RuntimeWindow instance = null;

    public static RuntimeWindow getInstance(){
        if(instance == null){
            instance = new RuntimeWindow();
        }
        return instance;
    }

    private RuntimeWindow() {
        super(Orientation.VERTICAL, 5);
        init();
    }

    private void init(){

        this.in = System.in;
        pipedOutput = new PipedOutputStream();
        try{
            in = new PipedInputStream((PipedOutputStream)pipedOutput);
        }catch(IOException ioe){
            System.err.println(ioe.getLocalizedMessage());
        }

        errorTag = CustomTextTags.getInstance().getTag("runtime_error");
        buf = new TextBuffer();
        view = new TextView(buf);
        view.setEditable(false);
        view.overrideBackground(StateFlags.NORMAL, RGBA.BLACK);
        view.overrideColor(StateFlags.NORMAL, RGBA.WHITE);
        view.setWrapMode(WrapMode.WORD_CHAR);
        ScrolledWindow sc = new ScrolledWindow();
        sc.setPolicy(PolicyType.NEVER, PolicyType.ALWAYS);
        sc.add(view);
        packStart(sc, true, true, 0);

        submitButton = new Button(StringResources.getInstance().getString("submit"));
        inputEntry = new Entry();

        Box wrapper = new Box(Orientation.HORIZONTAL, 5);
        wrapper.packStart(inputEntry, true, true, 0);
        wrapper.packStart(submitButton, false, true, 0);
        packStart(wrapper, false, true, 0);

        this.out = new RuntimeWindowPrintStream(false, this);
        this.err = new RuntimeWindowPrintStream(true, this);

        connectSignals();
    }

    public void setFont(String fontDescription){
        view.overrideFont(new FontDescription(fontDescription));
    }

    private void connectSignals(){
        submitButton.connect(new Button.Clicked() {

            public void onClicked(Button source) {
                submit();
            }
        });

        inputEntry.connect(new Entry.Activate() {

            public void onActivate(Entry source) {
                submit();
            }
        });
    }

    private void submit(){
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(pipedOutput));
        String text = inputEntry.getText();
        try{
            append(text, false);
            newline();
            w.write(text);
            w.newLine();
            w.flush();
        }catch(IOException ioe){
            System.err.println(ioe.getLocalizedMessage());
        }
        inputEntry.setText("");
    }

    public InputStream getInputStream(){
        return this.in;
    }

    public RuntimeWindowPrintStream getOut() {
        return out;
    }

    public RuntimeWindowPrintStream getErr() {
        return err;
    }

    public void clear(){
        buf.removeAllTags(buf.getIterStart(), buf.getIterEnd());
        buf.setText("");
    }

    public void append(String message, boolean isError){
        TextIter end = buf.getIterEnd();
        if(isError){
            if(errorTag!=null){
                buf.insert(end, message, errorTag);
            }else{
                buf.insert(end, message);
            }
        }else{
            buf.insert(end, message);
        }
        view.scrollTo(buf.getIterEnd());
    }

    public void newline(){
        TextIter end = buf.getIterEnd();
        buf.insert(end, "\n");
    }

    public void setFocusOnInputEntry(){
        inputEntry.grabFocus();
    }

}
