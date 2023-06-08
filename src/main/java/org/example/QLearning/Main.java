package org.example.QLearning;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the Qlearning class
        Qlearning qlearning = new Qlearning();

        // Call the setAgents method
        int[][] agents = {{1, 2}, {3, 0}};
        qlearning.setAgents(agents);

        // Call the updateState method
        int[][] newState = {{0, 0, 0, 0}, {0, -1, 0, -1}, {0, 0, -1, 0}, {-1, 0, 0, 1}};
        qlearning.updateState(newState);

        // Call the run method
        qlearning.run();
    }
}
