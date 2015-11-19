import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import javafx.scene.AmbientLight;
import javafx.scene.PointLight;

public class graphicEngine extends SimpleApplication {

    public static void main(String[] args) {

    }
    protected Geometry player;
    Boolean isRunning=true;
    @Override
    public void simpleInitApp() {
        assetManager.registerLocator("Models.zip", ZipLocator.class);
        Spatial gameLevel = assetManager.loadModel("main.j3o");
       // gameLevel.setLocalTranslation(0, -5.2f, 0);
       //cladire.setLocalScale(0.00001f);
      ///  cladire.rotate(120,0,0);
        rootNode.attachChild(gameLevel);
        //rootNode.attachChild(cladire);

        // You must add a light to make the model visible

        DirectionalLight sun2 = new DirectionalLight();
        sun2.setDirection(new Vector3f(-1.0f, -1.0f, -1.0f));
        rootNode.addLight(sun2);

        AmbientLight sun3=new AmbientLight();
        sun3.setLightOn(true);
        flyCam.setMoveSpeed(70);

    }


}