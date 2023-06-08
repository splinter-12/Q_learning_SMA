package org.example;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.lang.acl.MessageTemplate;

public class SimpleAgent extends Agent {
    protected void setup() {
        // Request the list of agents from the IslandAgent
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(new AID("IslandAgent", AID.ISLOCALNAME));
        request.setContent("getAgents");
        send(request);

        // Receive the list of agents from the IslandAgent
        addBehaviour(new ReceiveAgentsBehaviour());

        // Send the updated list of agents to the IslandAgent
        addBehaviour(new SetAgentsBehaviour());

        // Update the state in the IslandAgent
        addBehaviour(new UpdateStateBehaviour());
    }

    private class ReceiveAgentsBehaviour extends CyclicBehaviour {
        public void action() {
            // Receive the list of agents from the IslandAgent
            ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            if (msg != null) {
                String content = msg.getContent();
                String[] agents = content.split(",");
                if (agents.length == 0) {
                    System.out.println("Agent list is empty");
                } else {
                    for (String agent : agents) {
                        String[] parts = agent.split(":");
                        if (parts.length == 2) {
                            String agentName = parts[0];
                            String agentType = parts[1];
                            if (agentType.trim().equals("SimpleAgent")) {
                                // Perform SimpleAgent actions
                                System.out.println("SimpleAgent received agent: " + agentName);
                            }
                        } else {
                            System.out.println("Invalid agent format: " + agent);
                        }
                    }
                }
            } else {
                block();
            }
        }
    }

    private class SetAgentsBehaviour extends OneShotBehaviour {
        public void action() {
            // Send the updated list of agents to the IslandAgent
            String agents = "SimpleAgent:MasterAgent";
            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.addReceiver(new AID("IslandAgent", AID.ISLOCALNAME));
            request.setContent("setAgents," + agents);
            send(request);
        }
    }

    private class UpdateStateBehaviour extends OneShotBehaviour {
        public void action() {
            // Update the state in the IslandAgent
            int stateI = 1;
            int stateJ = 1;
            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.addReceiver(new AID("IslandAgent", AID.ISLOCALNAME));
            request.setContent("updateState," + stateI + "," + stateJ);
            send(request);
        }
    }
}
