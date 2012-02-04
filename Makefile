all:
	./build-all
	
clean:
	ant clean
	
distclean:
	rm -r dist build
	
uninstall: 
	rm -r /usr/local/bin/lingua	
	rm /usr/share/icons/hicolor/scalable/apps/lingua.svg
	rm /usr/share/applications/lingua.desktop
	
install:
	mkdir -p /usr/local/bin/lingua
	mkdir -p /usr/share/gtksourceview-3.0/language-specs
	mkdir -p /usr/share/gtksourceview-3.0/styles
	mkdir -p /usr/share/icons/hicolor/scalable/apps
	mkdir -p /usr/share/applications
	cp dist/lingua.jar /usr/local/bin/lingua/lingua.jar
	cp dist/source_printer.py /usr/local/bin/lingua/source_printer.py
	cp -R dist/lib /usr/local/bin/lingua
	cp -R dist/help /usr/local/bin/lingua
	cp dist/lingua-icon.svg /usr/share/icons/hicolor/scalable/apps/lingua.svg
	cp dist/glossa.lang /usr/share/gtksourceview-3.0/language-specs/glossa.lang
	cp dist/glossa.xml /usr/share/gtksourceview-3.0/styles/glossa.xml
	touch /usr/share/applications/lingua.desktop
	echo "[Desktop Entry]" | cat >> /usr/share/applications/lingua.desktop
	echo "Encoding=UTF-8" | cat >> /usr/share/applications/lingua.desktop
	echo "Name=Lingua" | cat >> /usr/share/applications/lingua.desktop
	echo "GenericName=Περιβάλλον Ανάπτυξης Για Τη ΓΛΩΣΣΑ" | cat >> /usr/share/applications/lingua.desktop
	echo "Comment=Γράψτε και εκτελέστε προγράμματα στη ΓΛΩΣΣΑ" | cat >> /usr/share/applications/lingua.desktop
	echo "Terminal=false" | cat >> /usr/share/applications/lingua.desktop
	echo "Type=Application" | cat >> /usr/share/applications/lingua.desktop
	echo "Categories=Education;" | cat >> /usr/share/applications/lingua.desktop
	echo "StartupNotify=true" | cat >> /usr/share/applications/lingua.desktop
	echo "MimeType=text/glossa;" | cat >> /usr/share/applications/lingua.desktop
	echo "Exec=java -jar /usr/local/bin/lingua/lingua.jar" | cat >> /usr/share/applications/lingua.desktop
	echo "Icon=/usr/share/icons/hicolor/scalable/apps/lingua.svg" | cat >> /usr/share/applications/lingua.desktop
	chmod +x /usr/share/applications/lingua.desktop
