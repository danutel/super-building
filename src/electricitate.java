import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class electricitate extends Agent {
    public boolean electricitate;
    @Override
    public void setup(){
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage mesaj_receptionat = myAgent.receive();
                if(mesaj_receptionat!=null) {
                    if (mesaj_receptionat.getConversationId() == "electricitate") {
                        electricitate = Boolean.parseBoolean(mesaj_receptionat.getContent());
                        environment.curent_electric=electricitate;
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
