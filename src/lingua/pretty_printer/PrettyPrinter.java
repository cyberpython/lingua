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
package lingua.pretty_printer;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public abstract class PrettyPrinter {

    private int numOfWhitespaceCharsPerIndentationLevel;
    private boolean useSpaces;
    
    boolean ignoreEmptyLines;
    
    private boolean replaceTabsWithSpaces;
    private int numOfSpacesPerTab;
    
    private char whitespaceChar;
    private int numOfLinesBetweenSubprograms;
    private int numOfLinesBetweenBlocks;
    private int numOfWhitespaceCharsBetweenAssignmentOperands;
    private int numOfWhitespaceCharsBeforeAndAfterConditions;
    private String spacesForTab;
    private String whitespaceUnit;
    private String newLinesForBlocks;
    private String newLinesForSubPrograms;

    public PrettyPrinter() {
        ignoreEmptyLines = true;
        numOfWhitespaceCharsPerIndentationLevel = 4;
        whitespaceChar = ' ';
        useSpaces = true;
        replaceTabsWithSpaces = true;
        numOfSpacesPerTab = 4;
        numOfLinesBetweenSubprograms = 4;
        numOfWhitespaceCharsBeforeAndAfterConditions = 1;
        numOfLinesBetweenBlocks = 1;
        setSpacesForTab();
        setWhitespaceUnit();
        setNewLinesForBlocks();
        setNewLinesForSubprograms();
    }

    public abstract String getPrettyText(String src);

    public String getWhitespaceForIndentationLevel(int level, boolean reduceFirstUnitByOneAndOneChar) {
        if (level <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;

        if (reduceFirstUnitByOneAndOneChar) {
            boolean tmpUseSpaces = useSpaces;
            numOfWhitespaceCharsPerIndentationLevel--;

            setUseSpaces(true);
            sb.append(whitespaceUnit);

            numOfWhitespaceCharsPerIndentationLevel++;
            setUseSpaces(tmpUseSpaces);
            i = 2;
        }

        for (; i < level; i++) {
            sb.append(whitespaceUnit);
        }

        return sb.toString();
    }

    public void setUseSpaces(boolean useSpaces) {
        this.useSpaces = useSpaces;
        if (useSpaces) {
            setWhitespace(' ');
        } else {
            setWhitespace('\t');
        }
        setWhitespaceUnit();
    }
    
    private void setSpacesForTab(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numOfSpacesPerTab; i++) {
            sb.append(" ");
        }
        spacesForTab = sb.toString();
    }

    private void setWhitespaceUnit() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numOfWhitespaceCharsPerIndentationLevel; i++) {
            sb.append(whitespaceChar);
        }

        whitespaceUnit = sb.toString();
    }

    private void setNewLinesForBlocks() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numOfLinesBetweenBlocks; i++) {
            sb.append("\n");
        }
        newLinesForBlocks = sb.toString();
    }

    private void setNewLinesForSubprograms() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numOfLinesBetweenSubprograms; i++) {
            sb.append("\n");
        }
        newLinesForSubPrograms = sb.toString();
    }

    public String getNewLinesForBlocks() {
        return newLinesForBlocks;
    }

    public String getNewLinesForSubPrograms() {
        return newLinesForSubPrograms;
    }

    /**
     * @return the numOfWhitespaceCharsPerIndentationLevel
     */
    public int getNumOfWhitespaceCharsPerIndentationLevel() {
        return numOfWhitespaceCharsPerIndentationLevel;
    }

    /**
     * @param numOfWhitespaceCharsPerIndentationLevel the
     * numOfWhitespaceCharsPerIndentationLevel to set
     */
    public void setNumOfWhitespaceCharsPerIndentationLevel(int numOfWhitespaceCharsPerIndentationLevel) {
        this.numOfWhitespaceCharsPerIndentationLevel = numOfWhitespaceCharsPerIndentationLevel;
        setWhitespaceUnit();
    }

    /**
     * @return the useSpaces
     */
    public boolean isUseSpacesOn() {
        return useSpaces;
    }

    /**
     * @return the whitespace
     */
    public char getWhitespace() {
        return whitespaceChar;
    }

    public String getSpacesForTab() {
        return spacesForTab;
    }

    /**
     * @param whitespace the whitespace to set
     */
    private void setWhitespace(char whitespace) {
        this.whitespaceChar = whitespace;
    }

    /**
     * @return the numOfLinesBetweenSubprograms
     */
    public int getNumOfLinesBetweenSubprograms() {
        return numOfLinesBetweenSubprograms;
    }

    /**
     * @param numOfLinesBetweenSubprograms the numOfLinesBetweenSubprograms to
     * set
     */
    public void setNumOfLinesBetweenSubprograms(int numOfLinesBetweenSubprograms) {
        this.numOfLinesBetweenSubprograms = numOfLinesBetweenSubprograms;
        setNewLinesForSubprograms();
    }

    /**
     * @return the numOfWhitespaceCharsBetweenAssignmentOperands
     */
    public int getNumOfWhitespaceCharsBetweenAssignmentOperands() {
        return numOfWhitespaceCharsBetweenAssignmentOperands;
    }

    /**
     * @param numOfWhitespaceCharsBetweenAssignmentOperands the
     * numOfWhitespaceCharsBetweenAssignmentOperands to set
     */
    public void setNumOfWhitespaceCharsBetweenAssignmentOperands(int numOfWhitespaceCharsBetweenAssignmentOperands) {
        this.numOfWhitespaceCharsBetweenAssignmentOperands = numOfWhitespaceCharsBetweenAssignmentOperands;
    }

    /**
     * @return the numOfWhitespaceCharsBeforeAndAfterConditions
     */
    public int getNumOfWhitespaceCharsBeforeAndAfterConditions() {
        return numOfWhitespaceCharsBeforeAndAfterConditions;
    }

    /**
     * @param numOfWhitespaceCharsBeforeAndAfterConditions the
     * numOfWhitespaceCharsBeforeAndAfterConditions to set
     */
    public void setNumOfWhitespaceCharsBeforeAndAfterConditions(int numOfWhitespaceCharsBeforeAndAfterConditions) {
        this.numOfWhitespaceCharsBeforeAndAfterConditions = numOfWhitespaceCharsBeforeAndAfterConditions;
    }

    /**
     * @return the numOfLinesBetweenBlocks
     */
    public int getNumOfLinesBetweenBlocks() {
        return numOfLinesBetweenBlocks;
    }

    /**
     * @param numOfLinesBetweenBlocks the numOfLinesBetweenBlocks to set
     */
    public void setNumOfLinesBetweenBlocks(int numOfLinesBetweenBlocks) {
        this.numOfLinesBetweenBlocks = numOfLinesBetweenBlocks;
        setNewLinesForBlocks();
    }

    public void setNumOfSpacesPerTab(int numOfSpacesPerTab) {
        this.numOfSpacesPerTab = numOfSpacesPerTab;
        setSpacesForTab();
    }

    public void setReplaceTabsWithSpaces(boolean replaceTabsWithSpaces) {
        this.replaceTabsWithSpaces = replaceTabsWithSpaces;
    }

    public int getNumOfSpacesPerTab() {
        return numOfSpacesPerTab;
    }

    public boolean isReplaceTabsWithSpacesOn() {
        return replaceTabsWithSpaces;
    }

    public boolean isIgnoreEmptyLinesOn() {
        return ignoreEmptyLines;
    }

    public void setIgnoreEmptyLines(boolean collapseEmptyLines) {
        this.ignoreEmptyLines = collapseEmptyLines;
    }
    
}
