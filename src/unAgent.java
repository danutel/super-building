import jade.core.Agent;

public class unAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new clientRegisterBehaviour());

        graphicEngine app = new graphicEngine();
        app.start();
    }
}
