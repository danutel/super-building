import jade.core.Agent;
import jade.core.behaviours.Behaviour;

/**
 * Created by Danutel on 12/18/15.
 */
public class senzorTemperatura extends Agent{
    public float temperatura;

    @Override
    public void setup(){

        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                temperatura=environment.temperatura_interior;
            }

            @Override
            public boolean done() {
                return false;
            }
        });
    }
}
