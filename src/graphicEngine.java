import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class graphicEngine extends SimpleApplication implements ActionListener{

    public static void main(String[] args) {

    }
    private BulletAppState bulletAppState;
    private CharacterControl player;
    private Spatial femeie1,map,bloc,barbat1,femeie2,barbat3,femeie3;
    private boolean left = false, right = false, up = false, down = false,camera=false,tp=false;
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private Vector3f walkDirection = new Vector3f();



    @Override
    public void simpleInitApp() {

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));

        load_object("map.zip","main.j3o",map,0,0,0,1,1,1,0,0,0,0);
        load_object("bloc.zip","ggg.j3o", bloc,-420,1,-110,0.5f,0.5f,0.5f,0,0,0,0);
        load_object("Modele/Oameni/femeie1.zip","femeie1.scene", femeie1,-500,-10,-10,0.0022f,0.0022f,0.0022f,0,0,0,1f);
        load_object("Modele/Oameni/barbat1.zip","barbat1.j3o", barbat1,-400,0,-20,0.13f,0.13f,0.13f,0,0,0,0.5f);
        load_object("Modele/Oameni/femeie2.zip","femeie2.j3o", femeie2,-400,0,0,0.15f,0.15f,0.15f,0,0,0,0.5f);
        load_object("Modele/Oameni/barbat3.zip","barbat3.j3o", barbat3,-420,0,-20,0.15f,0.12f,0.12f,0,0,0,0.5f);
        load_object("Modele/Oameni/femeie3.zip","femeie3.j3o", femeie3,-400,0,-30,0.12f,0.12f,0.12f,0,0,0,0.5f);
       // load_object("Modele/Oameni/femeie1.zip","femeie1.j3o", femeie1,0,0,0,0.25f,0.25f,0.25f,0,0,0,0.5f);
        //load_object("Modele/Oameni/femeie1.zip","femeie1.j3o", femeie1,0,0,0,0.25f,0.25f,0.25f,0,0,0,0.5f);

        lightSetup();
        cameraSetup();
        load_player();
        setUpKeys();
    }

    public  void load_player()
    {
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 15f, 1);
        player = new CharacterControl(capsuleShape, 2.5f);
        player.setJumpSpeed(20);
        player.setFallSpeed(50);
        player.setGravity(50);
        player.setPhysicsLocation(new Vector3f(0, 10, 0));
        bulletAppState.getPhysicsSpace().add(player);
    }
    public void cameraSetup()
    {
        cam.setFrustumFar(5000);
        cam.onFrameChange();
        flyCam.setMoveSpeed(300);
    }
    public void lightSetup()
    {
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(2f));
        rootNode.addLight(al);

        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-1f, -1f, -1f).normalizeLocal());
        rootNode.addLight(sun);


    }

    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Change_camera", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("Teleport", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
        inputManager.addListener(this, "Change_camera");
        inputManager.addListener(this, "Teleport");
    }

    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Left")) {
            left = isPressed;
        } else if (binding.equals("Right")) {
            right= isPressed;
        } else if (binding.equals("Up")) {
            up = isPressed;
        } else if (binding.equals("Down"))
        {
            down = isPressed;
        }
        else if (binding.equals("Change_camera"))
        {
            camera = !camera;
        }
        else if (binding.equals("Teleport"))
        {
            player.setPhysicsLocation(cam.getLocation());
        }
        else if (binding.equals("Jump")) {
            if (isPressed) {
                player.jump();
            }
        }
    }

    public void load_object( String fisier, String nume,Spatial numeObiect,int tX,int tY,int tZ,float sX, float sY, float sZ, int rX,int rY,int rZ,float masa)
    {
        assetManager.registerLocator(fisier, ZipLocator.class);
        numeObiect = assetManager.loadModel(nume);
        numeObiect.setLocalTranslation(tX,tY,tZ);
        numeObiect.scale(sX,sY,sZ);
        numeObiect.rotate(rX,rY,rZ);
        //CollisionShape sceneShape = CollisionShapeFactory.createMeshShape((Node) numeObiect);
        RigidBodyControl numeObiect_PhysX = new RigidBodyControl(masa);
        numeObiect.addControl(numeObiect_PhysX);
        bulletAppState.getPhysicsSpace().add(numeObiect_PhysX);
        rootNode.attachChild(numeObiect);
    }

    public void arunca()
    {

    }

    @Override
    public void simpleUpdate(float tpf) {
        if(!camera) {
            camDir.set(cam.getDirection()).multLocal(0.6f);
            camLeft.set(cam.getLeft()).multLocal(0.4f);
            walkDirection.set(0, 0, 0);
            if (left) {
                walkDirection.addLocal(camLeft);
            }
            if (right) {
                walkDirection.addLocal(camLeft.negate());
            }
            if (up) {
                walkDirection.addLocal(camDir);
            }
            if (down) {
                walkDirection.addLocal(camDir.negate());
            }
            player.setWalkDirection(walkDirection);
            cam.setLocation(player.getPhysicsLocation());
        }
    }


}