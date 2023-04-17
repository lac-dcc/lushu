ZHE_FOLDER = ./
TEXTS_FOLDER = $(ZHE_FOLDER)text_files/

JAR_DEPENDECIES = ./target/Grammar-1.0-SNAPSHOT.jar 
JAVA = java

.PHONY: test build exec run

test: 
	java -jar ./build/main.jar $(TEXTS_FOLDER)test.txt $(TEXTS_FOLDER)out.txt
	echo "** original text **\n"
	cat $(TEXTS_FOLDER)test.txt

build:
	java_files=$$(find . -type f -name "*.java") && \
	  javac -d ./build $${java_files}
	cd build && \
	  jar cmvf ../META-INF/MANIFEST.MF main.jar com/grammar/*.class

exec:
	java -jar ./build/main.jar

run: build exec
