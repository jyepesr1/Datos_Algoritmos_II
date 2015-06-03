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
MANIFEST = META-INF/MANIFEST.MF#Manifest of the program
#------------------------------------------------------------

# show help message by default
deafult: help

# Compile, build jar and run
all: build jar run

#Compile the .classes files
build:
	mkdir -p build
	mkdir -p ${PATH_TO_CLASSES}
	javac -cp ${CP}${PATH_TO_LIBS} -d ${PATH_TO_CLASSES} ${PATH_TO_SRC}	
	cp -r src/Cities ${PATH_TO_CLASSES}

#Generate the .jar file
jar:
	mkdir -p dist
	mkdir -p dist/lib
	cd build/classes/; \
	jar -cvfm ../../${PATH_TO_JAR} ../../${MANIFEST} .
	cp ${PATH_TO_LIBS} dist/lib

#Run the jar file
run:
	@echo "Ejecutando el Jar"
	java -jar ${PATH_TO_JAR} 

# Clean all the generated files and leaves only the src, META-INF  and the lib folders
clean: 
	rm -rf build
	rm -rf dist

help:
	@echo "make build: Compile the source files into the .class files, to the directory './build/classes"
	@echo "make jar: Generate the file 'FindFood.jar', in the directory './dist/'"
	@echo "make run: Execute the file 'FindFood.jar'"
	@echo "make all: Same as 'make Build && make Jar && make Run'"
	@echo "make clean: Clean the project an leaves only the source files"
