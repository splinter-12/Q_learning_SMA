package org.example.QLearning;

import java.util.Random;

public class Qlearning {

    // Q Learning
    private final double ALPHA = 0.1;
    private final double GAMMA = 0.9;
    private final int MAX_EPOCH = 90000;
    private final int GRID_SIZE = 4;
    private final int ACTIONS_SIZE = 4;

    private int[][] grid = new int[GRID_SIZE][GRID_SIZE];
    private double[][] qTable = new double[GRID_SIZE * GRID_SIZE][ACTIONS_SIZE];
    private int[][] actions = new int[ACTIONS_SIZE][2];
    private int stateI;
    private int stateJ;

    public Qlearning() {
        // Define the actions that the agent can take
        actions = new int[][]{
                {0, -1}, //G (go left)
                {0, 1}, //D (go right)
                {1, 0}, //B (go down)
                {-1, 0} //H (go up)
        };

        // Define the grid environment
        grid = new int[][]{
                {0, 0, 0, 0},
                {0, -1, 0, -1},
                {0, 0, -1, 0},
                {-1, 0, 0, 1}
        };
    }

    // Check if the agent has reached the goal state
    private boolean finished() {
        return grid[stateI][stateJ] == 1;
    }

    // Display the final Q-table and the actions taken by the agent
    private void showResult() {
        System.out.println(" ----- QTable results :");
        for (double[] line : qTable) {
            System.out.print("[");
            for (double qv : line) {
                System.out.print(qv + " , ");
            }
            System.out.println("]");
        }
        System.out.println("---- Actions : ");
        resetState();
        System.out.println("| States |   Actions    |  ");
        System.out.println("-------------------------");
        while (!finished()) {
            int act = chooseAction(0);
            System.out.println("| " + stateI + " | " + stateJ + " |    action : " + act + " |");
            executeAction(act);
        }
    }

    // Run the Q-learning algorithm
    public void run() {
        int it = 0;
        int currentState;
        int nextState;
        int act, act1;
        while (it < MAX_EPOCH) {
            resetState();
            while (!finished()) {
                currentState = stateI * GRID_SIZE + stateJ;
                act = chooseAction(0.4);
                nextState = executeAction(act);
                act1 = chooseAction(0);
                // Update the Q-table using the Q-learning formula
                qTable[currentState][act] = qTable[currentState][act] + ALPHA * (grid[stateI][stateJ] + GAMMA * qTable[nextState][act1] - qTable[currentState][act]);
            }
            it++;
        }
        showResult();
    }

    // Reset the agent's state to the starting position
    private void resetState() {
        stateI = 0;
        stateJ = 0;
    }

    // Choose an action for the agent to take
    private int chooseAction(double eps) {
        Random random = new Random();
        double bestQ = 0;
        int act = 0;
        if (random.nextDouble() < eps) {
            // Exploration
            act = random.nextInt(ACTIONS_SIZE);
        } else {
            // Exploitation
            int st = stateI * GRID_SIZE + stateJ;
            for (int i = 0; i < ACTIONS_SIZE; i++) {
                if (qTable[st][i] > bestQ) {
                    bestQ = qTable[st][i];
                    act = i;
                }
            }
        }
        return act;
    }

    // Execute the chosen action and update the agent's state
    private int executeAction(int act) {
        stateI = Math.max(0, Math.min(actions[act][0] + stateI, GRID_SIZE - 1));
        stateJ = Math.max(0, Math.min(actions[act][1] + stateJ, GRID_SIZE - 1));
        return stateI * GRID_SIZE + stateJ;
    }

    // Set the agents in the grid environment
    public void setAgents(int[][] agents) {
        for (int i = 0; i < agents.length; i++) {
            int x = agents[i][0];
            int y = agents[i][1];
            grid[x][y] = -1;
        }
    }

    // Update the state of the grid environment
    public void updateState(int[][] newState) {
        grid = newState;
    }
}
