#!/usr/bin/env python

#*
#*  The MIT License
#* 
#*  Copyright 2012 Georgios Migdos <cyberpython@gmail.com>.
#* 
#*  Permission is hereby granted, free of charge, to any person obtaining a copy
#*  of this software and associated documentation files (the "Software"), to deal
#*  in the Software without restriction, including without limitation the rights
#*  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
#*  copies of the Software, and to permit persons to whom the Software is
#*  furnished to do so, subject to the following conditions:
#* 
#*  The above copyright notice and this permission notice shall be included in
#*  all copies or substantial portions of the Software.
#* 
#*  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
#*  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
#*  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
#*  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
#*  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
#*  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
#*  THE SOFTWARE.
#*

from gi.repository import Gtk, GtkSource
import sys

if len(sys.argv)!=5 :
    print '''
Wrong number of parameters:
Correct usage :
    
    python source_printer.py <language_id> <scheme_id> <font_description> <filename>
  
  e.g.
    python source_printer.py java classic "Liberation Mono 10" ~/test/Hello_World.java
    
  
  Parameters:
  
    language_id         : The GtkSourceView language definition id.
    
    scheme_id           : The GtkSourceView style scheme definition id.
    
    font_description    : The Pango font description string (font-family 
                          and size seperated by a space character).
    
    filename            : The file to print.
    '''
    exit()

def begin_print(operation, context, p):
    print "Initializing printing process..."
    while(not p.paginate(context)):
        pass
    n_pages = p.get_n_pages()
    operation.set_n_pages (n_pages);
    print "Sending", n_pages, "to printer"

def end_print(operation, context):
    print 'Document sent to printer.'

def draw_page(operation, context, page_nr, p):
    print 'Sending page:', (page_nr+1)
    p.draw_page (context,page_nr)

fname = sys.argv[4]

lang = GtkSource.LanguageManager.get_default().get_language(sys.argv[1])
scheme = GtkSource.StyleSchemeManager.get_default().get_scheme(sys.argv[2])

buf = GtkSource.Buffer()
buf.set_language(lang)
buf.set_style_scheme(scheme)
f = open(fname, 'r')
buf.set_text(f.read())
f.close()
p = GtkSource.PrintCompositor.new(buf)
p.set_print_line_numbers(1)
p.set_body_font_name(sys.argv[3])
p.set_line_numbers_font_name(sys.argv[3])
p.set_wrap_mode(Gtk.WrapMode.WORD_CHAR)
p.set_left_margin(20, Gtk.Unit.MM)
p.set_right_margin(20, Gtk.Unit.MM)
p.set_top_margin(20, Gtk.Unit.MM)
p.set_bottom_margin(30, Gtk.Unit.MM)
op = Gtk.PrintOperation()
op.connect("draw_page", draw_page, p)
op.connect("begin-print", begin_print, p)
op.connect("end-print", end_print)
op.run(Gtk.PrintOperationAction.PRINT_DIALOG, None)
