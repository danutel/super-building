import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class agentFum extends Agent {
    private boolean senzor_fum;
    @Override
    public void setup()
    {
        requestHandler x = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",-665,-87,-150,1,1,1,0,0,0,0);
        graphicEngine.request.add(x);
        requestHandler y = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",-670,-87,-150,1,1,1,0,0,0,0);
        graphicEngine.request.add(y);
        requestHandler z = new requestHandler("light","Modele/Obiecte/bec.zip","bec.j3o","bec",-670,-87,-150,1,1,1,0,0,0,0);
        graphicEngine.request.add(z);
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                senzor_fum=environment.fum;
                if(senzor_fum) {
                    environment.alarma_incendiu = true;
                    environment.ventilatie = 2;
                    environment.curent_electric = false;
                    environment.lumini_urgenta = true;
                    environment.sprinkler = true;
                }
                else
                {
                    environment.alarma_incendiu = false;
                    environment.ventilatie = 1;
                    environment.curent_electric = true;
                    environment.lumini_urgenta = false;
                    environment.sprinkler = false;
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        });
    }
}