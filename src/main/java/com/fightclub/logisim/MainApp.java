package com.fightclub.logisim;
mkdir -p bin
javac -d bin src/main/java/com/fightclub/logisim/PathFinder.java src/main/java/com/fightclub/logisim/XorGate.java src/main/java/com/fightclub/logisim/ComponentType.java src/main/java/com/fightclub/logisim/QuadBool.java src/main/java/com/fightclub/logisim/Window.java src/main/java/com/fightclub/logisim/ConnectionPoint.java src/main/java/com/fightclub/logisim/Wire.java src/main/java/com/fightclub/logisim/NandGate.java src/main/java/com/fightclub/logisim/MainApp.java src/main/java/com/fightclub/logisim/AndGate.java src/main/java/com/fightclub/logisim/MemoryComponent.java src/main/java/com/fightclub/logisim/CircuitInstableException.java src/main/java/com/fightclub/logisim/LedLight.java src/main/java/com/fightclub/logisim/OrGate.java src/main/java/com/fightclub/logisim/NotGate.java src/main/java/com/fightclub/logisim/Circuit.java src/main/java/com/fightclub/logisim/ConstantComponent.java
src/main/java/com/fightclub/logisim/MainApp.java:10: error: cannot find symbol
        SwingUtilities.invokeLater(Window::new); 
                                   ^
  symbol:   class Window
  location: class MainApp
1 error
make: *** [Makefile:11: compile] Error 1

import javax.swing.SwingUtilities;

/**
 * MainApp class serves as the entry point for the application.
 */
public class MainApp {
    public static void main(String[] args) {
        // Démarre l'application (appel au constructeur de CurcuitUI)
        SwingUtilities.invokeLater(Window::new); 
    }
}
