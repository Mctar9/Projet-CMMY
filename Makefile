SRC_DIR=src/main/java
BIN_DIR=bin
MAIN_CLASS=com.fightclub.logisim.MainApp

SOURCES=$(shell find $(SRC_DIR) -name "*.java")

all: compile

compile:
	mkdir -p bin
	javac -d bin $(shell find src/main/java -name "*.java")

run: compile
	java -cp $(BIN_DIR) $(MAIN_CLASS)

clean:
	rm -rf $(BIN_DIR)
