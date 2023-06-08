package org.example;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.lang.acl.MessageTemplate;
import org.example.QLearning.Qlearning;

import java.util.ArrayList;
import java.util.List;

public class IslandAgent extends Agent {
    private List<AID> agents = new ArrayList<>();
    private Qlearning qLearningAgent;

    protected void setup() {
        // Register the IslandAgent with the DF agent
        getAID().setLocalName("IslandAgent");
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("customIslandAgentType");
        sd.setName("customIslandAgentName");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }

        // Add the IslandAgent to the list of agents
        agents.add(getAID());

        // Add the behavior to handle incoming messages
        addBehaviour(new MessageHandlerBehaviour());
        qLearningAgent = new Qlearning();
        qLearningAgent.setAgents(new int[][]{{1, 1}, {2, 2}}); // Example agent positions
        qLearningAgent.updateState(new int[][]{{0, 0, 0, 0}, {0, -1, 0, -1}, {0, 0, -1, 0}, {-1, 0, 0, 1}}); // Example grid environment

        // Add the behavior to handle incoming messages
        addBehaviour(new MessageHandlerBehaviour());
    }

    private class MessageHandlerBehaviour extends CyclicBehaviour {
        public void action() {
            // Receive the next message from the queue
            ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

            if (msg != null) {
                String content = msg.getContent();
                String[] parts = content.split(",");
                String command = parts[0];
                if (command.equals("getAgents")) {
                    // ... (existing code)
                } else if (command.equals("setAgents")) {
                    // ... (existing code)
                } else if (command.equals("updateState")) {
                    // ... (existing code)
                } else if (command.equals("startQLearning")) {
                    // Start the Q-learning algorithm
                    startQLearning();
                }
            } else {
                block();
            }
        }
    }

    private void startQLearning() {
        // Start the Q-learning algorithm in the Q-learning agent
        qLearningAgent.run();
    }
}