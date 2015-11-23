import com.jme3.scene.Spatial;
import jade.core.Agent;

public class unAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new clientRegisterBehaviour());

        graphicEngine app = new graphicEngine();
        app.start();
        //app.load_object("map.zip","main.j3o",mac,0,0,0,1,1,1,0,0,0,0);
    }
}
