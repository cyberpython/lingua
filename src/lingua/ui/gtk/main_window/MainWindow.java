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
package lingua.ui.gtk.main_window;

import glossa.interpreter.Interpreter;
import glossa.interpreter.InterpreterListener;
import glossa.interpreter.symboltable.SymbolTable;
import lingua.resources.StringResources;
import lingua.ui.gtk.main_window.menus.MenuBar;
import lingua.ui.gtk.main_window.toolbars.MainToolbar;
import lingua.ui.gtk.main_window.widgets.BottomPane;
import lingua.ui.gtk.main_window.widgets.EditorContainer;
import lingua.ui.gtk.main_window.widgets.MainWindowStatusbar;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import lingua.preferences.Preferences;
import lingua.resources.IconResources;
import lingua.ui.gtk.main_window.actions.RunAction;
import lingua.ui.gtk.main_window.actions.StopAction;
import lingua.ui.gtk.main_window.actions.ToggleStepByStepAction;
import lingua.ui.gtk.main_window.actions.ToggleUseInputFileAction;
import lingua.ui.gtk.main_window.dialogs.io.DialogManager;
import lingua.ui.gtk.main_window.widgets.Editor;
import lingua.ui.gtk.main_window.widgets.EditorBuffer;
import lingua.ui.gtk.main_window.widgets.InputFileWindow;
import lingua.ui.gtk.main_window.widgets.InterpreterMessagesListView;
import lingua.ui.gtk.main_window.widgets.RuntimeWindow;
import org.gnome.gdk.Event;
import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.AcceleratorGroup;
import org.gnome.gtk.Box;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Orientation;
import org.gnome.gtk.Paned;
import org.gnome.gtk.TextIter;
import org.gnome.gtk.Toolbar;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class MainWindow extends Window implements InterpreterListener{
    private static MainWindow instance;
    private final Box mainContainer;
    private final BottomPane bottomPane;
    private final MainWindowStatusbar statusBar;
    private final MenuBar mainMenubar;
    private final Toolbar mainToolbar;
    private final EditorContainer editorContainer;
    private final Paned verticalPaned;
    private static AcceleratorGroup accelGroupInstance;



    private Interpreter interpreter;
    private boolean runInteractively;
    private int interpreterDelayBetweenSteps;
    private boolean running;


    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    private MainWindow() {
        mainContainer = new Box(Orientation.VERTICAL, 0);

        statusBar = MainWindowStatusbar.getInstance();
        mainContainer.packEnd(statusBar, false, false, 0);

        mainMenubar = new MenuBar();
        mainContainer.packStart(mainMenubar, false, true, 0);

        mainToolbar = MainToolbar.getInstance();
        mainContainer.packStart(mainToolbar, false, true, 0);

        bottomPane = new BottomPane();

        verticalPaned = new Paned(Orientation.VERTICAL);
        editorContainer = new EditorContainer();
        verticalPaned.pack1(editorContainer, true, true);
        verticalPaned.pack2(bottomPane, false, true);
        verticalPaned.setPosition(300);

        mainContainer.packStart(verticalPaned, true, true, 0);

        this.add(mainContainer);

        this.addAcceleratorGroup(getAcceleratorGroup());

        this.setTitle(StringResources.getInstance().getString("untitled") + " - " + StringResources.getInstance().getString("app_name"));

        this.resize(800, 600);
        setIcon(IconResources.getIcon());

        connect(new Window.DeleteEvent() {

            public boolean onDeleteEvent(Widget source, Event event) {
                exitApp();
                return true;
            }
        });


        this.runInteractively = false;
        this.running = false;
        this.interpreterDelayBetweenSteps = 0;

        Preferences.getInstance().apply();
    }
    
    public void exitApp() {
        boolean exit = true;
        if(stopInterpreterAndContinue()){
            Editor editor = Editor.getInstance();
            EditorBuffer buf = editor.getBuffer();
            if(buf.getModified()){
                if(DialogManager.handleModifiedBuffer(false, buf.getDocumentTitle(), editor)){
                    exit = true;
                }else{
                    exit = false;
                }
            }else{
                exit = true;
            }
        }else{
            exit = false;
        }

        if(InputFileWindow.getInstance().isModified()){
            exit = exit && DialogManager.handleModifiedBuffer(true, StringResources.getInstance().getString("untitled"), InputFileWindow.getInstance());
        }

        if(exit){
            Gtk.mainQuit();
        }
    }

    public void updateTitle() {
        EditorBuffer buf= Editor.getInstance().getBuffer();
        StringBuilder title = new StringBuilder(buf.getDocumentTitle());
        if(buf.getModified()==true){
            title.append("*");
        }
        title.append(" - ");
        title.append(StringResources.getInstance().getString("app_name"));
        this.setTitle(title.toString());
    }

    public static AcceleratorGroup getAcceleratorGroup() {
        if (accelGroupInstance == null) {
            accelGroupInstance = new AcceleratorGroup();
        }
        return accelGroupInstance;
    }


    // <editor-fold defaultstate="collapsed" desc="Code execution">

    public void run() {
        RunAction.getInstance().setSensitive(false);
        if (running) {
            interpreter.resume();
        } else {
            runCurrentFile();
        }
    }

    public void stop() {
        if (interpreter != null) {
            interpreter.stop();
        }
    }

    public boolean stopInterpreterAndContinue(){
        boolean proceed = true;
        if(running && (interpreter!=null) ){
            if(DialogManager.verifyStopInterpreter()){
                stop();
                proceed = true;
            }else{
                proceed = false;
            }
        }
        return proceed;
    }

    public void parsingAndSemanticAnalysisFinished(Interpreter sender, boolean success) {
    }

    public void runtimeError() {
    }

    public void executionStarted(Interpreter sender) {
        running = true;
        Editor.getInstance().setEditable(false);
        RunAction.getInstance().setSensitive(false);
        StopAction.getInstance().setSensitive(true);
        MainToolbar.getInstance().setDelayControlsSensitive(false);
        ToggleStepByStepAction.getInstance().setSensitive(false);
        ToggleUseInputFileAction.getInstance().setSensitive(false);
        RuntimeWindow.getInstance().setFocusOnInputEntry();
    }

    public void executionStopped(Interpreter sender) {
        running = false;
        Editor.getInstance().getBuffer().clearHighlight("exec_highlight");
        Editor.getInstance().setEditable(true);
        RunAction.getInstance().setSensitive(true);
        MainToolbar.getInstance().setDelayControlsSensitive(true);
        ToggleStepByStepAction.getInstance().setSensitive(true);
        ToggleUseInputFileAction.getInstance().setSensitive(true);
        StopAction.getInstance().setSensitive(false);
    }

    public void readStatementExecuted(Interpreter sender, Integer line) {
        if (runInteractively || (interpreterDelayBetweenSteps > 0)) {
            Editor ed = Editor.getInstance();
            EditorBuffer buf = ed.getBuffer();
            buf.highlightLine(line.intValue() - 1, "exec_highlight");
            TextIter start = buf.getIterStart();
            start.forwardLines(line.intValue()-1);
            ed.scrollTo(start);
        }
    }

    public void executionPaused(Interpreter sender, Integer line, Boolean wasPrintStatement) {

        if (runInteractively || (interpreterDelayBetweenSteps > 0)) {
            Editor ed = Editor.getInstance();
            EditorBuffer buf = ed.getBuffer();
            buf.highlightLine(line.intValue() - 1, "exec_highlight");
            TextIter start = buf.getIterStart();
            start.forwardLines(line.intValue()-1);
            ed.scrollTo(start);
        }

        if (!runInteractively) {
            if (interpreterDelayBetweenSteps > 0) {
                final Interpreter inter = sender;
                Thread t = new Thread(new Runnable() {

                    public void run() {
                        try {
                            Thread.sleep(interpreterDelayBetweenSteps);
                        } catch (InterruptedException e) {
                        }
                        inter.resume();
                    }
                });
                t.start();
            } else {
                sender.resume();
            }
        } else {
            RunAction.getInstance().setSensitive(true);
        }
    }

    public void stackPopped() {
    }

    public void stackPushed(SymbolTable newSymbolTable) {
    }

    

    public void runCurrentFile() {
        String text = Editor.getInstance().getBuffer().getText();
        if (!text.endsWith("\n")) {
            text += "\n";
        }
        InterpreterMessagesListView interpreterMessagesListView = bottomPane.getMessagesListView();
        RuntimeWindow runtimeWindow = RuntimeWindow.getInstance();

        Editor.getInstance().getBuffer().clearHighlight("exec_highlight");
        Editor.getInstance().getBuffer().clearHighlight("error");
        Editor.getInstance().getBuffer().clearHighlight("warning");


        if (this.interpreter != null) {
            interpreter.removeListener(this);
            interpreterMessagesListView.setInterpreter(null);
        }

        this.runInteractively = ToggleStepByStepAction.getInstance().getActive();
        this.interpreterDelayBetweenSteps = MainToolbar.getInstance().getDelayBetweenSteps();

        runtimeWindow.clear();
        interpreterMessagesListView.clear();
        bottomPane.setCurrentPage(0);
        InputStream in;
        boolean useInputFile = ToggleUseInputFileAction.getInstance().getActive();
        if (useInputFile) {
            in = new ByteArrayInputStream(InputFileWindow.getInstance().getText().getBytes());
        } else {
            in = runtimeWindow.getInputStream();
        }
        interpreter = new Interpreter(text, System.out, System.err, runtimeWindow.getOut(), runtimeWindow.getErr(), in);
        interpreter.addListener(this);
        interpreterMessagesListView.setInterpreter(interpreter);
        boolean success = interpreter.parseAndAnalyzeSemantics(true);
        if (success) {
            bottomPane.setCurrentPage(1);
            interpreter.exec(useInputFile);
        } else {
            RunAction.getInstance().setSensitive(true);
        }
    }
    // </editor-fold>

}
