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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import org.gnome.gdk.Pixbuf;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class IconResources {

    private static Pixbuf icon = loadIcon();

    private static Pixbuf loadIcon(){
        try {
            BufferedInputStream bis = new BufferedInputStream(IconResources.class.getResourceAsStream("/lingua/resources/icon.svg"));
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] buf = new byte[512];
            int read = 0;
            while ((read = bis.read(buf)) > -1) {
                buffer.write(buf, 0, read);
            }
            bis.close();
            buffer.flush();
            byte[] bytes = buffer.toByteArray();
            buffer.close();
            return new Pixbuf(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Pixbuf getIcon(){
        return icon;
    }
}
