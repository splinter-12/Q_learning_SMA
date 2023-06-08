package org.example;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.lang.acl.MessageTemplate;

public class MasterAgent extends Agent {
    protected void setup() {
        // Request the list of agents from the IslandAgent
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(new AID("IslandAgent", AID.ISLOCALNAME));
        request.setContent("getAgents");
        send(request);

        // Receive the list of agents from the IslandAgent
        addBehaviour(new ReceiveAgentsBehaviour());

        // Send the start signal to the IslandAgent to begin Q-learning
        addBehaviour(new StartQLearningBehaviour());
    }

    private class ReceiveAgentsBehaviour extends CyclicBehaviour {
        public void action() {
            // Receive the list of agents from the IslandAgent
            ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            if (msg != null) {
                String content = msg.getContent();
                String[] agents = content.split(",");
                for (String agent : agents) {
                    String[] parts = agent.split(":");
                    if (parts.length == 2) {
                        String agentName = parts[0].trim();
                        String agentType = parts[1].trim();
                        if (agentType.equals("MasterAgent")) {
                            // Perform MasterAgent actions
                            System.out.println("MasterAgent received agent: " + agentName);
                        }
                    } else {
                        System.out.println("Invalid agent format: " + agent);
                    }
                }
            } else {
                block();
            }
        }
    }

    private class StartQLearningBehaviour extends OneShotBehaviour {
        public void action() {
            // Send the start signal to the IslandAgent to begin Q-learning
            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.addReceiver(new AID("IslandAgent", AID.ISLOCALNAME));
            request.setContent("startQLearning");
            send(request);
        }
    }
}
