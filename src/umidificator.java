import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class umidificator extends Agent {
    public double umidificator;
    @Override
    public void setup(){
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null) {
                    if (mesaj_receptionat.getConversationId() == "umidificator") {
                        umidificator = Double.parseDouble(mesaj_receptionat.getContent());
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        });
    }
}
