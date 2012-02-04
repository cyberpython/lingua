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

package lingua.ui.gtk.main_window.dialogs.messages;

import lingua.resources.IconResources;
import lingua.resources.StringResources;
import org.gnome.gtk.AboutDialog;
import org.gnome.gtk.Dialog;
import org.gnome.gtk.ResponseType;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class LinguaAboutDialog extends AboutDialog{

    private static LinguaAboutDialog instance = null;

    public static LinguaAboutDialog getInstance(){
        if(instance == null){
            instance = new LinguaAboutDialog();
        }
        return instance;
    }

    private LinguaAboutDialog() {
        StringResources res = StringResources.getInstance();
        setProgramName("Lingua");
        setVersion(res.getString("app_version"));
        setLogo(IconResources.getIcon());
        setComments("Ολοκληρωμένο περιβάλλον ανάπτυξης για τη ΓΛΩΣΣΑ");
        setWebsite("http://cyberpython.github.com/lingua/");
        setAuthors(new String[]{"Georgios Migdos <cyberpython@gmail.com>"});
        setCopyright("Georgios Migdos");
        setLicense(" *  The MIT License\n * \n *  Copyright 2012 Georgios Migdos <cyberpython@gmail.com>.\n * \n *  Permission is hereby granted, free of charge, to any person obtaining a copy\n *  of this software and associated documentation files (the \"Software\"), to deal\n *  in the Software without restriction, including without limitation the rights\n *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n *  copies of the Software, and to permit persons to whom the Software is\n *  furnished to do so, subject to the following conditions:\n * \n *  The above copyright notice and this permission notice shall be included in\n *  all copies or substantial portions of the Software.\n * \n *  THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n *  THE SOFTWARE.");

        connect(new Response() {

            public void onResponse(Dialog source, ResponseType response) {
                hide();
            }
        });

        setDefaultSize(400, 400);
        setIcon(IconResources.getIcon());

    }

}
