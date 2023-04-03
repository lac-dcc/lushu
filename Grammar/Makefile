ZHE_FOLDER = ./
TEXTS_FOLDER = $(ZHE_FOLDER)text_files/

JAR_DEPENDECIES = ./target/Grammar-1.0-SNAPSHOT.jar 
JAVA = java

all: 
	@$(JAVA) -jar $(JAR_DEPENDECIES) $(TEXTS_FOLDER)test.txt $(TEXTS_FOLDER)out.txt
	@echo "** original text **\n"
	@cat $(TEXTS_FOLDER)test.txt

clean:

