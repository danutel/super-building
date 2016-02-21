import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public class server extends Agent{
    @Override
    public void setup(){
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                int counter=0;
                List<String> lista_celule = new ArrayList<String>();
                while(counter<50) {
                    ACLMessage mesaj_receptionat = myAgent.receive();
                    if (mesaj_receptionat != null) {
                        if(mesaj_receptionat.getConversationId()=="ospf")
                        {
                            System.out.println(mesaj_receptionat.getContent());
                        }
                    }
                    counter++;
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
