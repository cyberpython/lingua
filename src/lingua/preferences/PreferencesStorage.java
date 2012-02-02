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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public abstract class PreferencesStorage {

    private final String DELIMETER = ":";
    private final String TARGET_DIR = ".config";
    private final String FILE_NAME = "preferences.conf";
    private File targetDir;
    private String dirSep;
    private File targetFile;
    private HashMap<String, String> values;

    public PreferencesStorage(String appName) {
        this.values = new HashMap<String, String>();
        this.dirSep = System.getProperty("file.separator");
        this.targetDir = new File(System.getProperty("user.home")
                + dirSep + TARGET_DIR + dirSep + appName);
        this.targetFile = new File(targetDir, FILE_NAME);
        loadValues();
    }

    public abstract void setupDefaultValues();

    public String getValue(String field){
        return values.get(field);
    }

    public String setValue(String field, String value){
        return values.put(field, value);
    }

    private void parseLine(String line) {
        String[] components = line.split(DELIMETER, 2);
        if (components.length == 2) {
            values.put(components[0].trim(), components[1].trim());
        }
    }

    private void loadValues() {
        if (targetFile.exists()) {
            loadFromFile();
        } else {
            setupDefaultValues();
            writeToFile();
        }
    }

    private void loadFromFile() {
        values.clear();
        try {
            BufferedReader r = new BufferedReader(new FileReader(targetFile));
            String line;
            while ((line = r.readLine()) != null ) {
                parseLine(line);
            }
            r.close();
        } catch (FileNotFoundException fnfe) {
            System.err.println("Preferences file " + targetFile.getAbsolutePath() + "does not exist!");
        } catch (IOException ioe){
            System.err.println("Error reading preferences from: " + targetFile.getAbsolutePath());
        }

    }

    public void savePreferences(){
        writeToFile();
    }

    private void writeToFile() {
        try {
            targetDir.mkdirs();
            BufferedWriter w = new BufferedWriter(new FileWriter(targetFile));
            Set<String> keys = values.keySet();
            String field = null;
            String value = null;
            for (Iterator<String> it = keys.iterator(); it.hasNext();) {
                field = it.next();
                value = values.get(field);
                if(value!=null){
                    w.write(field);
                    w.write(DELIMETER);
                    w.write(value);
                    w.newLine();
                }
            }
            w.flush();
            w.close();
        } catch (IOException ioe) {
            System.err.println("Error writing preferences to: " + targetFile.getAbsolutePath());
            ioe.printStackTrace();
        }
    }
}
