# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)
NAME="DrawingProgram" 

# HACK: vecmath is included regardless if needed
all:
	@echo "Compiling..."
	javac -cp vecmath.jar *.java model/*.java view/*.java

run: all
	@echo "Running ..."
	java -cp "vecmath.jar:." $(NAME)

clean:
	rm -rf *.class
	rm -rf view/*.class
	rm -rf model/*.class
