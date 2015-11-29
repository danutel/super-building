import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class agentTemperatura extends Agent {
    public float senzor_temperatura_interior;
    public boolean senzor_fum;
    public float senzor_temperatura_exterior;
    public float senzor_lumina;
    @Override
    public void setup(){

        addBehaviour(new Behaviour() {
            @Override
            public void action() {

            }

            @Override
            public boolean done() {
                return false;
            }
        });

    }

}
