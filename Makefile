# Variables
SRC_DIR=main/java
BIN_DIR=bin
MAIN_CLASS=src/main/java/com/fightclub/logisim/MainApp.java

# Récupère tous les fichiers .java
SOURCES=$(shell find $(SRC_DIR) -name "*.java")

# Cible par défaut
all: compile

compile:
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $(SOURCES)

run: compile
	java -cp $(BIN_DIR) $(MAIN_CLASS)

clean:
	rm -rf $(BIN_DIR)
