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
package lingua.utils;

import java.io.File;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class MiscUtils {

    /**
     * Opens a file with the default program using the xdg-open command
     *
     * @param f The file to be opened
     * 
     */
    public static void xdgOpenFile(File f) {
        String CROSS_DESKTOP_OPEN_FILE_COMMAND = "/usr/bin/xdg-open";
        try {
            File parentDir = f.getParentFile();
            String fname = f.getName();
            String cmd = "/bin/sh " + CROSS_DESKTOP_OPEN_FILE_COMMAND + " " + fname;
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(cmd, null, parentDir);
            p.waitFor();
        } catch (Exception e) {
        }
    }
}
