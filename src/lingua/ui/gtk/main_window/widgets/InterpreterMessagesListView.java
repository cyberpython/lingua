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

import glossa.interpreter.Interpreter;
import glossa.interpreter.InterpreterListener;
import glossa.interpreter.symboltable.SymbolTable;
import glossa.messages.ErrorMessage;
import glossa.messages.InterpreterMessage;
import glossa.messages.MessageLog;
import glossa.messages.WarningMessage;
import glossa.utils.Point;
import java.util.Iterator;
import java.util.List;
import lingua.resources.StringResources;
import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnReference;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.TextIter;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreePath;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class InterpreterMessagesListView extends TreeView implements InterpreterListener {

    private Interpreter interpreter;
    private ListStore model;
    private final DataColumnString messageType;
    private final DataColumnString pos;
    private final DataColumnString message;
    private final DataColumnReference<InterpreterMessage> msgRef;
    private String typeError;
    private String typeWarning;

    public InterpreterMessagesListView() {

        this.interpreter = null;

        this.model = new ListStore(new DataColumn[]{
                    messageType = new DataColumnString(),
                    pos = new DataColumnString(),
                    message = new DataColumnString(),
                    msgRef = new DataColumnReference<InterpreterMessage>()
                });

        setModel(model);

        TreeViewColumn msgTypeCol = appendColumn();
        TreeViewColumn msgPosCol = appendColumn();
        TreeViewColumn msgCol = appendColumn();

        StringResources res = StringResources.getInstance();

        msgTypeCol.setTitle(res.getString("type"));
        msgPosCol.setTitle(res.getString("position"));
        msgCol.setTitle(res.getString("message"));

        CellRendererText textRenderer = new CellRendererText(msgTypeCol);
        textRenderer.setText(messageType);

        textRenderer = new CellRendererText(msgPosCol);
        textRenderer.setText(pos);

        textRenderer = new CellRendererText(msgCol);
        textRenderer.setText(message);

        typeError = StringResources.getInstance().getString("error");
        typeWarning = StringResources.getInstance().getString("warning");

        //highlight the message's line:
        connect(new TreeView.RowActivated() {

            public void onRowActivated(TreeView source, TreePath path, TreeViewColumn vertical) {
                final TreeIter row;
                row = model.getIter(path);
                InterpreterMessage msg = model.getValue(row, msgRef);
                Point p = msg.getPoint();
                String type = "error";
                if(msg instanceof WarningMessage){
                    type = "warning";
                }
                Editor ed = Editor.getInstance();
                EditorBuffer buf = ed.getBuffer();
                buf.highlightLine(p.getX()-1, type);
                TextIter moveTo = buf.getIterStart();
                moveTo.setLine(p.getX()-1);
                ed.scrollTo(moveTo);
            }
        });

    }

    public void clear() {
        this.model.clear();
    }

    public void setInterpreter(Interpreter interpreter) {
        if (this.interpreter != null) {
            this.interpreter.removeListener(this);
        }
        this.interpreter = interpreter;
        if (this.interpreter != null) {
            this.interpreter.addListener(this);
        }
    }

    public void parsingAndSemanticAnalysisFinished(Interpreter sender, boolean success) {
        clear();
        if (this.interpreter != null) {
            MessageLog msgLog = interpreter.getMsgLog();
            List<ErrorMessage> errors = msgLog.getErrorMessages();
            for (Iterator<ErrorMessage> it = errors.iterator(); it.hasNext();) {
                addMessage(it.next());
            }
            List<WarningMessage> warnings = msgLog.getWarningMessages();
            for (Iterator<WarningMessage> it = warnings.iterator(); it.hasNext();) {
                addMessage(it.next());
            }
        }
    }

    public void addMessage(InterpreterMessage msg) {
        String type = "";
        if (msg instanceof ErrorMessage) {
            type = typeError;
        } else if (msg instanceof WarningMessage) {
            type = typeWarning;
        }
        TreeIter row = this.model.appendRow();
        this.model.setValue(row, messageType, type);
        Point p = msg.getPoint();
        String msgText = msg.getMsg();
        this.model.setValue(row, pos, p.getX() + ", " + (p.getY() + 1));
        this.model.setValue(row, message, msgText == null ? "" : msgText);
        this.model.setValue(row, msgRef, msg);
    }

    public void runtimeError() {
    }

    public void executionStarted(Interpreter i) {
    }

    public void readStatementExecuted(Interpreter sender, Integer line) {
    }

    public void executionPaused(Interpreter sender, Integer line, Boolean wasPrintStatement) {
    }

    public void executionStopped(Interpreter i) {
    }

    public void stackPopped() {
    }

    public void stackPushed(SymbolTable newSymbolTable) {
    }
}
