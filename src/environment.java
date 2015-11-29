import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class environment extends Agent{
    public static float temperatura_interior;
    public static boolean fum;
    public static float temperatura_exterior;
    public static float lumina;
    public static  boolean curent_electric;
    public static  boolean lumini_urgenta;
    public static  boolean sprinkler;
    public static boolean alarma_incendiu;
    public static int ventilatie; //0-oprit 1-reglare temperatura 2-trage aer din interior(pentru situatii de urgenta)
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
