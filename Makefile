#*
#*  The MIT License
#*
#*  Copyright 2012 Georgios Migdos <cyberpython@gmail.com>
#*		           Savvas Radevic  <vicedar@gmail.com>
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
all:
	ant clean jar
	jar umf manifest-libs dist/lingua.jar
	
clean:
	ant clean
	
distclean:
	rm -rf dist build
	
uninstall: 
	rm -rf $(DESTDIR)/usr/local/lingua
	rm -rf $(DESTDIR)/usr/share/doc/lingua
	rm $(DESTDIR)/usr/share/icons/hicolor/scalable/apps/lingua.svg
	rm $(DESTDIR)/usr/share/applications/lingua.desktop
	rm $(DESTDIR)/usr/bin/lingua
	
install:
	mkdir -p $(DESTDIR)/usr/local/lingua
	mkdir -p $(DESTDIR)/usr/share/gtksourceview-3.0/language-specs
	mkdir -p $(DESTDIR)/usr/share/gtksourceview-3.0/styles
	mkdir -p $(DESTDIR)/usr/share/icons/hicolor/scalable/apps
	mkdir -p $(DESTDIR)/usr/share/applications
	mkdir -p $(DESTDIR)/usr/share/doc/lingua
	cp dist/lingua.jar $(DESTDIR)/usr/local/lingua/lingua.jar
	chmod ugo+rx $(DESTDIR)/usr/local/lingua/lingua.jar
	cp misc/source_printer.py $(DESTDIR)/usr/local/lingua/source_printer.py
	cp -R help $(DESTDIR)/usr/share/doc/lingua
	cp misc/lingua-icon.svg $(DESTDIR)/usr/share/icons/hicolor/scalable/apps/lingua.svg
	chmod ugo+r $(DESTDIR)/usr/share/icons/hicolor/scalable/apps/lingua.svg
	cp sourceview-files/glossa.lang $(DESTDIR)/usr/share/gtksourceview-3.0/language-specs/glossa.lang
	cp sourceview-files/glossa.xml $(DESTDIR)/usr/share/gtksourceview-3.0/styles/glossa.xml
	cp misc/lingua.desktop $(DESTDIR)/usr/share/applications/lingua.desktop
	chmod ugo+x $(DESTDIR)/usr/share/applications/lingua.desktop
	touch $(DESTDIR)/usr/bin/lingua
	echo "#!/bin/sh" >> $(DESTDIR)/usr/bin/lingua
	echo "java -jar $(DESTDIR)/usr/local/lingua/lingua.jar" >> $(DESTDIR)/usr/bin/lingua
	chmod ugo+x $(DESTDIR)/usr/bin/lingua
