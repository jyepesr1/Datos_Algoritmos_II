# FindFood
# Version 2.0.0 "Final" (02/06/2015)
# Authors: Luis Miguel Mejía Suárez (201410033010)
#          Johan Sebastían Yepes Rios (201410049010)

# Vars use in the makefile ----------------------------------
PATH_TO_JAR = dist/FindFood.jar#Location of the .jar file
PATH_TO_CLASSES = build/classes#Location of the .class files
PATH_TO_SRC = src/**/*.java#Location of the .java files
PATH_TO_LIBS = ./lib/**/*.jar#Location of the libs
CP = $$CLASSPATH:#Classpath
MANIFEST = src/MANIFEST.MF#Manifest of the program
#------------------------------------------------------------

# show help message by default
Defaulit: Help

# Compile, build jar and run
All: Build Jar Run

#Compile the .classes files
Build:
	mkdir build
	mkdir ${PATH_TO_CLASSES}
	javac -cp ${CP}${PATH_TO_LIBS} -d ${PATH_TO_CLASSES} ${PATH_TO_SRC}	
	cp -r src/Cities ${PATH_TO_CLASSES}

#Generate the .jar file
Jar:
	mkdir dist
	mkdir dist/lib
	jar -cvfm ${PATH_TO_JAR} ${MANIFEST} ${PATH_TO_CLASSES}/**/*
	cp ${PATH_TO_LIBS} dist/lib

#Run the jar file
Run:
	@echo "Ejecutando el Jar"
	java -cp ${CP}lib/*.jar -jar ${PATH_TO_JAR} 

# Clean all the generated files and leaves only the src and the lib folders
Clean: 
	rm -rf build
	rm -rf dist

Help:
	@echo "make Build: Compile the source files into the .class files, to the directory './build/classes"
	@echo "make Jar: Generate the file 'FindFood.jar', in the directory './dist/'"
	@echo "make Run: Execute the file 'FindFood.jar'"
	@echo "make All: Same as 'make Build && make Jar && make Run'"
	@echo "make Clean: Clean the project an leaves only the source files"
