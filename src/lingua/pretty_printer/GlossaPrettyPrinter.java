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
public class GlossaPrettyPrinter extends PrettyPrinter {

    @Override
    public String getPrettyText(String src) {
        StringBuilder result = new StringBuilder();

        int indentationLevel = 0;
        int numberOfPrecedingEmptyLines = 0;
        int numberOfEmptyLinesToAdd = 0;

        String[] lines = src.split("\\n|\\r|\\r\\n");
        String lowerCaseLine;
        
        boolean addNewLinesAfter = false;
        boolean firstCharIsAmp;
        String line;
        for (int i=0; i<lines.length; i++) {
            line = lines[i];
            
            line = line.trim();
            if (!"".equals(line)) {
                
                if((numberOfEmptyLinesToAdd!=0)&&(!nextNonEmptyLineAmpLine(lines, i-1))){
                    if(!isIgnoreEmptyLinesOn()){
                        numberOfPrecedingEmptyLines = numberOfEmptyLinesToAdd;
                        for(int j=0; j<numberOfEmptyLinesToAdd; j++){
                            result.append("\n");
                        }
                    }
                }
                numberOfEmptyLinesToAdd = 0;

                lowerCaseLine = line.toLowerCase();
                firstCharIsAmp = false;

                if (lowerCaseLine.startsWith("&")) {
                    line = line.substring(1).trim();
                    result.append("&");
                    lowerCaseLine = lowerCaseLine.substring(1).trim();
                    firstCharIsAmp = true;
                }
                
                addNewLinesAfter = firstCharIsAmp && addNewLinesAfter;

                if (lowerCaseLine.startsWith("αν")
                        || lowerCaseLine.startsWith("επίλεξε")
                        || lowerCaseLine.startsWith("επιλεξε")
                        || lowerCaseLine.startsWith("για")
                        || lowerCaseLine.startsWith("οσο")
                        || lowerCaseLine.startsWith("όσο")
                        || lowerCaseLine.startsWith("αρχη_επαν")
                        || lowerCaseLine.startsWith("αρχή_επαν")) {

                    result.append(getWhitespaceForIndentationLevel(indentationLevel, firstCharIsAmp));
                    if(numberOfPrecedingEmptyLines==0){
                        result.append(getNewLinesForBlocks());
                        result.append(getWhitespaceForIndentationLevel(indentationLevel, firstCharIsAmp));
                    }
                    indentationLevel++;
                    addNewLinesAfter = true;

                } else if (lowerCaseLine.startsWith("πρόγραμμα")
                        || lowerCaseLine.startsWith("προγραμμα")) {

                    indentationLevel = 1;
                    addNewLinesAfter = true;

                } else if (lowerCaseLine.startsWith("συνάρτηση")
                        || lowerCaseLine.startsWith("συναρτηση")
                        || lowerCaseLine.startsWith("διαδικασία")
                        || lowerCaseLine.startsWith("διαδικασια")) {
                    
                    if(numberOfPrecedingEmptyLines==0){
                        result.append(getNewLinesForSubPrograms());
                    }
                    
                    indentationLevel = 1;
                    addNewLinesAfter = true;

                } else if (lowerCaseLine.startsWith("σταθερέ")
                        || lowerCaseLine.startsWith("σταθερε")
                        || lowerCaseLine.startsWith("μεταβλητέ")
                        || lowerCaseLine.startsWith("μεταβλητε")) {

                    indentationLevel = 1;
                    result.append(getWhitespaceForIndentationLevel(indentationLevel, firstCharIsAmp));
                    
                    if(numberOfPrecedingEmptyLines==0){
                        result.append(getNewLinesForBlocks());
                        result.append(getWhitespaceForIndentationLevel(indentationLevel, firstCharIsAmp));
                    }
                    
                    indentationLevel = 2;

                } else if (lowerCaseLine.startsWith("αλλιω")
                        || lowerCaseLine.startsWith("αλλιώ")
                        || lowerCaseLine.startsWith("περίπτωση")
                        || lowerCaseLine.startsWith("περιπτωση")) {

                    indentationLevel--;
                    result.append(getWhitespaceForIndentationLevel(indentationLevel, firstCharIsAmp));
                    if(numberOfPrecedingEmptyLines==0){
                        result.append(getNewLinesForBlocks());
                        result.append(getWhitespaceForIndentationLevel(indentationLevel, firstCharIsAmp));
                    }
                    indentationLevel++;
                    addNewLinesAfter = true;

                } else if (lowerCaseLine.startsWith("αρχή")
                        || lowerCaseLine.startsWith("αρχη")){
                    
                    indentationLevel = 0;
                    if(numberOfPrecedingEmptyLines==0){
                        result.append(getNewLinesForBlocks());
                    }
                    indentationLevel = 1;
                    addNewLinesAfter = true;
                    
                } else if (lowerCaseLine.startsWith("τέλος_επαν")
                        || lowerCaseLine.startsWith("τελος_επαν")
                        || lowerCaseLine.startsWith("τέλοσ_επαν")
                        || lowerCaseLine.startsWith("τελοσ_επαν")
                        || lowerCaseLine.startsWith("τέλος_αν")
                        || lowerCaseLine.startsWith("τελος_αν")
                        || lowerCaseLine.startsWith("τέλοσ_αν")
                        || lowerCaseLine.startsWith("τελοσ_αν")
                        || lowerCaseLine.startsWith("τέλος_επιλογ")
                        || lowerCaseLine.startsWith("τελος_επιλογ")
                        || lowerCaseLine.startsWith("τέλοσ_επιλογ")
                        || lowerCaseLine.startsWith("τελοσ_επιλογ")
                        || lowerCaseLine.startsWith("μέχρις_ότου")
                        || lowerCaseLine.startsWith("μέχρις_οτου")
                        || lowerCaseLine.startsWith("μεχρις_ότου")
                        || lowerCaseLine.startsWith("μεχρις_οτου")
                        || lowerCaseLine.startsWith("μέχρισ_ότου")
                        || lowerCaseLine.startsWith("μέχρισ_οτου")
                        || lowerCaseLine.startsWith("μεχρισ_ότου")
                        || lowerCaseLine.startsWith("μεχρισ_οτου")
                        || lowerCaseLine.startsWith("μέχρις ότου")
                        || lowerCaseLine.startsWith("μέχρις οτου")
                        || lowerCaseLine.startsWith("μεχρις ότου")
                        || lowerCaseLine.startsWith("μεχρις οτου")
                        || lowerCaseLine.startsWith("μέχρισ ότου")
                        || lowerCaseLine.startsWith("μέχρισ οτου")
                        || lowerCaseLine.startsWith("μεχρισ ότου")
                        || lowerCaseLine.startsWith("μεχρισ οτου")) {

                    indentationLevel--;
                    result.append(getWhitespaceForIndentationLevel(indentationLevel, firstCharIsAmp));
                    if(numberOfPrecedingEmptyLines==0){
                        result.append(getNewLinesForBlocks());
                        result.append(getWhitespaceForIndentationLevel(indentationLevel, firstCharIsAmp));
                    }
                    addNewLinesAfter = true;

                } else if (lowerCaseLine.startsWith("τέλος_προγρ")
                        || lowerCaseLine.startsWith("τελος_προγρ")
                        || lowerCaseLine.startsWith("τέλοσ_προγρ")
                        || lowerCaseLine.startsWith("τελοσ_προγρ")
                        || lowerCaseLine.startsWith("τέλος_διαδικασ")
                        || lowerCaseLine.startsWith("τελος_διαδικασ")
                        || lowerCaseLine.startsWith("τέλοσ_διαδικασ")
                        || lowerCaseLine.startsWith("τελοσ_διαδικασ")
                        || lowerCaseLine.startsWith("τέλος_συν")
                        || lowerCaseLine.startsWith("τελος_συν")
                        || lowerCaseLine.startsWith("τέλοσ_συν")
                        || lowerCaseLine.startsWith("τελοσ_συν")) {

                    indentationLevel = 0;
                    if(numberOfPrecedingEmptyLines==0){
                        result.append(getNewLinesForBlocks());
                    }
                } else {
                    result.append(getWhitespaceForIndentationLevel(indentationLevel, firstCharIsAmp));
                }

                result.append(processLine(line));
                result.append("\n");
                if(addNewLinesAfter && (!nextNonEmptyLineAmpLine(lines, i))  ){
                    result.append(getNewLinesForBlocks());
                    numberOfPrecedingEmptyLines = getNumOfLinesBetweenBlocks();
                }else{
                    numberOfPrecedingEmptyLines = 0;
                }
            }else{
                //numberOfPrecedingEmptyLines++;
                //result.append("\n");
                numberOfEmptyLinesToAdd++;
            }
        }

        return result.toString();
    }
    
    private boolean nextNonEmptyLineAmpLine(String[] lines, int index){
        String line;
        int i=index+1;
        if(index<lines.length-1){
            while(i<lines.length){
                line = lines[i].trim();
                if(!"".equals(line)){
                    if(line.startsWith("&")){
                        return true;
                    }else{
                        return false;
                    }
                }
                i++;
            }
            return false;
        }
        return false;
        
    }
    
    private String processLine(String line){
        StringBuilder result = new StringBuilder();
        
        boolean inSingleQuotedString = false;
        boolean inDoubleQuotedString = false;
        boolean inComment = false;
        char c = 0;
        char lastAppended = 0;
        char lastChar;
        char[] chars = line.toCharArray();
        
        for(int i=0; (i<chars.length)&&(!inComment); i++){
            lastChar = c;
            c = chars[i];
            if(c=='!'){
                if(!(inSingleQuotedString||inDoubleQuotedString)){
                    inComment = true;
                    if( (lastAppended!=' ') && (lastAppended!='\t') && (i!=0)){
                        result.append(' ');
                    }
                    result.append(line.substring(i)); //the rest of the line is comment
                }
            }else if(c=='\''){
                if(inSingleQuotedString){
                    inSingleQuotedString = false;
                }else if(!inDoubleQuotedString){
                    inSingleQuotedString = true;
                    if( (lastAppended!=' ') && (lastAppended!='\t') ){
                        result.append(' ');
                    }
                }
            } else if(c=='"'){
                if(inDoubleQuotedString){
                    inDoubleQuotedString = false;
                }else if(!inSingleQuotedString){
                    inDoubleQuotedString = true;
                    if( (lastAppended!=' ') && (lastAppended!='\t') ){
                        result.append(' ');
                    }
                }
            }
            
            if(!inComment){
                if(!(inSingleQuotedString || inDoubleQuotedString)){
                    if((c!=' ')&&(c!='\t')){
                        if((lastChar==' ')||(lastChar=='\t')){
                            if( (  (c!='(') && (c!='[') && (c!=']') && (c!=',') && (c!=':') )
                                    &&
                                ( (lastAppended!=' ') && (lastAppended!='\t') && (lastAppended!='[') )
                              ){
                                if( (lastChar=='\t') && isReplaceTabsWithSpacesOn() ){
                                    result.append(getSpacesForTab());
                                }else{
                                    result.append(lastChar);
                                }
                            }
                        }else{
                            if( (c==')')  && (lastAppended!=' ') && (lastAppended!='\t')){
                                result.append(' ');
                            }
                        }
                        result.append(c);
                        lastAppended = c;
                        if( ((c=='(') || (c==']') ||(c==',') || (c==':')) && (lastAppended!=' ') && (lastAppended!='\t')){
                            result.append(' ');
                            lastAppended = ' ';
                        }
                    }
                } else { //always print in-string chars:
                    result.append(c);
                    lastAppended = c;
                }
            }
        }
        return result.toString();
    }
}
