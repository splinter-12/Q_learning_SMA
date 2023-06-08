package org.example;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;



public class MainContainerClass {
    public static void main(String[] args) {
        try {
            // Get the JADE runtime instance
            Runtime rt = Runtime.instance();

            // Create a profile for the main container
            Profile pMain = new ProfileImpl(null, 1099, null);

            // Create the main container
            AgentContainer mc = rt.createMainContainer(pMain);

            // Create the IslandAgent
            mc.createNewAgent("IslandAgent", "org.example.IslandAgent", null).start();

            // Create the MasterAgent
            mc.createNewAgent("MasterAgent", "org.example.MasterAgent", null).start();

            // Create the SimpleAgent
            mc.createNewAgent("SimpleAgent", "org.example.SimpleAgent", null).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
