import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class senzorFum extends Agent {
   private boolean fum;
   private int valoare_fum;

   @Override
   public void setup(){
addBehaviour(new Behaviour() {
   @Override
   public void action() {
      valoare_fum = environment.fum;
      if(valoare_fum>50)
         fum=true;
      else
         fum=false;
   }

   @Override
   public boolean done() {
      return false;
   }
});
   }
}
