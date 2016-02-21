import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class lumini_urgenta extends Agent {
    public boolean lumini_urgenta;
    @Override
    public void setup(){
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null) {
                    if (mesaj_receptionat.getConversationId() == "lumini_urgenta") {
                        lumini_urgenta = Boolean.parseBoolean(mesaj_receptionat.getContent());
                        environment.lumini_urgenta=lumini_urgenta;
                    }
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        });
    }
}
