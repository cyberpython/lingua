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
import static org.freedesktop.bindings.Internationalization._;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class StringResources {
    private HashMap<String, String> strings;

    private static StringResources instance = null;


    private StringResources() {
        this.strings = new HashMap<String, String>();
        strings.put("app_name", _("Περιβάλλον Ανάπτυξης Για Τη ΓΛΩΣΣΑ"));
        strings.put("app_version", "0.0.4");

        strings.put("file", _("_Αρχείο"));
        strings.put("file_new", _("_Δημιουργία"));
        strings.put("tooltip_file_new", _("Δημιουργία νέου αρχείου"));
        strings.put("file_open", _("_Άνοιγμα"));
        strings.put("tooltip_file_open", _("Άνοιγμα αρχείου"));
        strings.put("file_save", _("Α_ποθήκευση"));
        strings.put("tooltip_file_save", _("Αποθήκευση αρχείου"));
        strings.put("file_save_as", _("Απ_οθήκευση Ως..."));
        strings.put("tooltip_file_save_as", _("Αποθήκευση αρχείου ως..."));
        strings.put("export_html", _("Εξα_γωγή ως έγγραφο HTML..."));
        strings.put("tooltip_export_html",_("Εξαγωγή ως έγγραφο HTML..."));
        strings.put("file_print", _("_Εκτύπωση"));
        strings.put("tooltip_file_print", _("Εκτύπωση αρχείου"));
        strings.put("app_exit", _("Έ_ξοδος"));

        strings.put("edit", _("_Επεξεργασία"));
        strings.put("edit_undo", _("_Αναίρεση"));
        strings.put("tooltip_edit_undo", _("Αναίρεση"));
        strings.put("edit_redo", _("Α_κύρωση αναίρεσης"));
        strings.put("tooltip_edit_redo", _("Ακύρωση αναίρεσης"));
        strings.put("edit_cut", _("Α_ποκοπή"));
        strings.put("tooltip_edit_cut", _("Αποκοπή"));
        strings.put("edit_copy", _("Α_ντιγραφή"));
        strings.put("tooltip_edit_copy", _("Αντιγραφή"));
        strings.put("edit_paste", _("Επ_ικόλληση"));
        strings.put("tooltip_edit_paste", _("Επικόλληση"));
        strings.put("edit_delete", _("_Διαγραφή"));
        strings.put("tooltip_edit_delete", _("Διαγραφή"));
        strings.put("edit_select_all", _("Επι_λογή όλων"));
        strings.put("tooltip_edit_select_all", _("Επιλογή όλων"));

        strings.put("help", _("_Βοήθεια"));
        strings.put("show_help_contents", _("_Προβολή περιεχομένων βοήθειας"));
        strings.put("show_about_dialog", _("_Σχετικά με την εφαρμογή"));


        strings.put("program", _("_Πρόγραμμα"));
        strings.put("run", _("_Εκτέλεση"));
        strings.put("tooltip_run", _("Εκτέλεση"));
        strings.put("run_step_by_step", _("Εκτέλεση _βήμα προς βήμα"));
        strings.put("tooltip_run_step_by_step", _("Εκτέλεση βήμα προς βήμα"));
        strings.put("stop", _("_Διακοπή εκτέλεσης"));
        strings.put("tooltip_stop", _("Διακοπή εκτέλεσης"));
        strings.put("tooltip_execution_speed", _("Ταχύτητα εκτέλεσης (%)"));
        strings.put("use_input_file", _("_Χρήση αρχείου εισόδου"));
        strings.put("tooltip_use_input_file", _("Χρήση αρχείου εισόδου"));

        strings.put("untitled", _("Ανώνυμο"));
        strings.put("cursor_col", _("Στήλη"));
        strings.put("cursor_line", _("Γραμμή"));
        strings.put("modified", _("Τροποποιημένο"));

        strings.put("messages", _("Μηνύματα"));
        strings.put("runtime_window", _("Παράθυρο εκτέλεσης"));
        strings.put("input_file", _("Αρχείο εισόδου"));

        strings.put("save_file", _("Αποθήκευση αρχείου"));
        strings.put("open_file", _("Άνοιγμα αρχείου"));
        strings.put("glossa_files", _("Αρχεία κώδικα της ΓΛΩΣΣΑΣ (*.gls)"));
        strings.put("all_files", _("Όλα τα αρχεία"));
        strings.put("html_files", _("Αρχεία HTML(*.html)"));
        strings.put("txt_files", _("Αρχεία απλού κειμένου(*.txt)"));
        strings.put("msg_file_modified", _("Το αρχείο '%1$s' έχει αλλάξει! Θέλετε να αποθηκεύσετε τις αλλαγές;"));
        strings.put("msg_input_file_modified", _("Το αρχείο εισόδου έχει αλλάξει! Θέλετε να αποθηκεύσετε τις αλλαγές;"));
        strings.put("msg_file_exists", _("Το αρχείο '%1$s' υπάρχει ήδη! Θέλετε να συνεχίσετε;"));
        strings.put("msg_file_exists_secondary", _("Τα τρέχοντα περιεχόμενα το αρχείου θα χαθούν!"));
        strings.put("msg_interpreter_running", _("Ο διερμηνευτής \"τρέχει\" ακόμη! Θέλετε να τερματίσετε την εκτέλεση;"));

        strings.put("type", _("Τύπος"));
        strings.put("position", _("Θέση"));
        strings.put("message", _("Μήνυμα"));

        strings.put("error", _("Σφάλμα"));
        strings.put("warning", _("Προειδοποίηση"));

        strings.put("submit", _("Υποβολή"));
        strings.put("type_here", _("Πληκτρολογήστε εδώ και πατήστε το Enter"));

        strings.put("preferences", _("Προτιμήσεις"));
        strings.put("font", _("Γραμματοσειρά"));
        strings.put("editor_scheme", _("Χρώματα επεξεργαστή κώδικα"));
    }

    public static StringResources getInstance(){
        if(instance == null){
            instance = new StringResources();
        }
        return instance;
    }

    public String getString(String key){
        return strings.get(key);
    }


}
