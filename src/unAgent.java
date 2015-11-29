import com.jme3.scene.Spatial;
import jade.core.Agent;

public class unAgent extends Agent {
    @Override
    protected void setup() {
        graphicEngine app=new graphicEngine();
        app.start();
        requestHandler obiect = new requestHandler("load","Modele/Oameni/barbat1.zip", "barbat1.j3o", "barbat1", -400, 0, -20, 0.13f, 0.13f, 0.13f, 0, 0, 0, 0.5f);
        graphicEngine.request.add(obiect);



    }
}
