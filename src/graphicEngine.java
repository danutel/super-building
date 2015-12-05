import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import com.jme3.shadow.PointLightShadowFilter;
import com.jme3.shadow.PointLightShadowRenderer;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.util.SkyFactory;
import java.util.ArrayList;
import java.util.List;

public class graphicEngine extends SimpleApplication implements ActionListener{

    public static void main(String[] args) {

    }
    private BulletAppState bulletAppState;
    private CharacterControl player;
    private Spatial map,bloc;
    private Spatial bec;
    private Spatial detector_fum;
    private Spatial stropitoare;
    private Spatial vent;
    private Spatial barbat1;
    private Spatial femeie1;
    private ParticleEmitter smoketrail1;
    private ParticleEmitter water1;
    private ParticleEmitter fire1;
    private PointLight light1;
    private PointLightShadowRenderer dlsr1;
    private boolean left = false, right = false, up = false, down = false,camera=false,tp=false;
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private Vector3f walkDirection = new Vector3f();
    public static List<requestHandler> request = new ArrayList<requestHandler>();

    @Override
    public void simpleInitApp() {

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        Spatial sky = SkyFactory.createSky(assetManager, "Scenes/Beach/FullskiesSunset0068.dds", false);
        sky.setLocalScale(550);
        rootNode.attachChild(sky);
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);

        requestHandler m = new requestHandler("load","map.zip","main.j3o","map",0,0,0,1,1,1,0,0,0,0);
        requestHandler b = new requestHandler("load","bloc.zip","ggg.j3o", "bloc",-420,1,-110,0.5f,0.5f,0.5f,0,0,0,0);
        load_object(m);
        load_object(b);

        lightSetup();
        cameraSetup();
        load_player();
        setUpKeys();
        hud();
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
        al.setColor(ColorRGBA.White.mult(1f));
        //rootNode.addLight(al);

        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-1f, -1f, -1f).normalizeLocal());
        //rootNode.addLight(sun);
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
                environment.foc=1;
            }
        }
    }

    public void hud(){

    }

    public void stropire(requestHandler x)
    {
        ParticleEmitter numeObiect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle,200000 );
        assetManager.registerLocator("assets/stropi.zip", ZipLocator.class);
        Material stropi = assetManager.loadMaterial("stropi.j3m");
        numeObiect.setMaterial(stropi);
        numeObiect.setLocalTranslation(x.translatie_x,x.translatie_y,x.translatie_z);
        numeObiect.setEndColor(  new ColorRGBA(0.8f, 0.8f, 1.0f, 0.5f));   // red
        numeObiect.setStartColor(new ColorRGBA(0.6f, 0.6f, 1.0f, 0.0f)); // yellow
        numeObiect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 14, 0));
        numeObiect.setStartSize(0.03f);
        numeObiect.setEndSize(0.08f);
        numeObiect.setGravity(0, 80, 0);
        numeObiect.setLowLife(0.5f);
        numeObiect.setHighLife(0.6f);
        numeObiect.setRandomAngle(true);
        numeObiect.setNumParticles(100000);
        numeObiect.setParticlesPerSec(4000);
        numeObiect.getParticleInfluencer().setVelocityVariation(2f);
        if(x.pornit) {
            switch (x.nume_obiect) {
                case "water1":
                    water1 = numeObiect;
                    rootNode.attachChild(water1);
                    break;
            }
        }
        else
        {
            switch (x.nume_obiect) {
                case "water1":
                    rootNode.detachChild(water1);
                    break;
            }
        }
    }

    public void foc_start(requestHandler x){
        ParticleEmitter numeObiect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 100*x.intensitate_foc);
        Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        //mat_red.getAdditionalRenderState().setDepthTest(true);
        //mat_red.getAdditionalRenderState().setDepthWrite(true);
        numeObiect.setMaterial(mat_red);
        numeObiect.setImagesX(2);
        numeObiect.setImagesY(2); // 2x2 texture animation
        numeObiect.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
        numeObiect.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        numeObiect.setInitialVelocity(new Vector3f(0, 8, 0));
        numeObiect.setStartSize((x.intensitate_foc/(float)10)*6.6f);
        numeObiect.setEndSize((x.intensitate_foc/(float)10)*0.5f);
        numeObiect.setGravity(0, 0, 0);
        numeObiect.setLowLife((x.intensitate_foc/(float)10)*2.5f);
        numeObiect.setHighLife((x.intensitate_foc/(float)10)*3.5f);
        numeObiect.setVelocityVariation((x.intensitate_foc/(float)10)*0.35f);
        numeObiect.setQueueBucket(RenderQueue.Bucket.Translucent);
        numeObiect.setLocalTranslation(x.translatie_x,x.translatie_y,x.translatie_z);
        if(x.pornit) {
            switch (x.nume_obiect) {
                case "fire1":
                    if(fire1!=null)
                        rootNode.detachChild(fire1);
                    fire1 = numeObiect;
                    rootNode.attachChild(fire1);
                    break;
            }
        }
        else
        {
            switch (x.nume_obiect) {
                case "fire1":
                    rootNode.detachChild(fire1);
                    break;
            }
        }
    }

    public void smoke(requestHandler x)
    {
        ParticleEmitter numeObiect= new ParticleEmitter("SmokeTrail", ParticleMesh.Type.Triangle, 2200);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/smoketrail.png"));
        numeObiect.setMaterial(mat);
        numeObiect.setImagesX(1);
        numeObiect.setImagesY(3);
        numeObiect.setStartColor(new ColorRGBA(1f, 0.8f, 0.36f, (float) (1.0f / 1)));
        numeObiect.setEndColor(new ColorRGBA(1f, 0.8f, 0.36f, 0f));
        numeObiect.setInitialVelocity(new Vector3f(0, 5, 0));
        numeObiect.setLocalTranslation(-670,-110,-130);
        numeObiect.setStartSize(2f);
        numeObiect.setEndSize(10f);
        numeObiect.setFacingVelocity(true);
        numeObiect.setParticlesPerSec(100);
        numeObiect.setGravity(0, 0, 0);
        numeObiect.setLowLife(1.4f);
        numeObiect.setHighLife(3.5f);
        numeObiect.setVelocityVariation(0.1f);
        numeObiect.setQueueBucket(RenderQueue.Bucket.Translucent);
        if(x.pornit) {
            switch (x.nume_obiect) {
                case "fire1":
                    smoketrail1 = numeObiect;
                    rootNode.attachChild(smoketrail1);
                    break;
            }
        }
        else
        {
            switch (x.nume_obiect) {
                case "fire1":
                    rootNode.detachChild(smoketrail1);
                    break;
            }
        }
    }

    public void load_light(requestHandler x) {
        PointLight lamp_light = new PointLight();
        if(x.alarma)
        {
            lamp_light.setColor(ColorRGBA.Red.mult(x.intensitate_lumina));
        }
        else {
            lamp_light.setColor(ColorRGBA.White.mult(x.intensitate_lumina));
        }
        lamp_light.setRadius(x.suprafata);
        lamp_light.setPosition(new Vector3f(x.translatie_x,x.translatie_y,x.translatie_z));

        final int SHADOWMAP_SIZE=1024;
        PointLightShadowRenderer dlsr = new PointLightShadowRenderer(assetManager, SHADOWMAP_SIZE);
        dlsr.setLight(lamp_light);
        dlsr.setShadowIntensity(1);

        switch (x.nume_obiect) {
            case "light1":
                if(light1!=null) {
                    rootNode.removeLight(light1);
                    viewPort.removeProcessor(dlsr1);
                }
                light1 = lamp_light;
                dlsr1 = dlsr;
                rootNode.addLight(light1);
                viewPort.addProcessor(dlsr1);
                break;
        }

        if(x.pornit==false)
        {
            switch (x.nume_obiect) {
                case "light1":
                    light1.setColor(ColorRGBA.randomColor().mult(0));
                    viewPort.removeProcessor(dlsr1);
                    break;
            }
        }

       /* PointLightShadowFilter dlsf = new PointLightShadowFilter(assetManager, SHADOWMAP_SIZE);
        dlsf.setLight(lamp_light);
        dlsf.setEnabled(true);

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(dlsf);
        viewPort.addProcessor(fpp);*/

    }

    public void load_object(requestHandler x)
    {
        Spatial numeObiect;
        assetManager.registerLocator(x.nume_arhiva, ZipLocator.class);
        numeObiect = assetManager.loadModel(x.nume_fisier);
        numeObiect.setLocalTranslation(x.translatie_x,x.translatie_y,x.translatie_z);
        numeObiect.scale(x.scalare_x,x.scalare_y,x.scalare_z);
        numeObiect.rotate(x.rotatie_x,x.rotatie_y,x.rotatie_z);
        if(x.nume_obiect.equals("bloc")) {
            numeObiect.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        }
        else if (x.nume_obiect.equals("map"))
        {
            numeObiect.setShadowMode(RenderQueue.ShadowMode.Receive);
        }
        else
        {
            numeObiect.setShadowMode(RenderQueue.ShadowMode.Cast);
        }
        RigidBodyControl numeObiect_PhysX = new RigidBodyControl(x.masa);
        numeObiect.addControl(numeObiect_PhysX);
        bulletAppState.getPhysicsSpace().add(numeObiect_PhysX);
        switch (x.nume_obiect)
        {
            case "map": map=numeObiect;
                rootNode.attachChild(map);
                break;
            case "bloc": bloc=numeObiect;
                rootNode.attachChild(bloc);
                break;
            case "detector_fum":
                detector_fum = numeObiect;
                rootNode.attachChild(detector_fum);
                break;
            case "bec":
                bec = numeObiect;
                rootNode.attachChild(bec);
                break;
            case "stropitoare":
                stropitoare = numeObiect;
                rootNode.attachChild(stropitoare);
                break;
            case "vent":
                vent = numeObiect;
                rootNode.attachChild(vent);
                break;
            case "barbat1":
                barbat1 = numeObiect;
                rootNode.attachChild(barbat1);
                break;
        }
    }



    @Override
    public void simpleUpdate(float tpf) {

         if (request.isEmpty()==false)
        {
            requestHandler x = request.get(0);
            switch (x.type)
            {
                case "load": load_object(x);
                    break;
                case "light": load_light(x);
                    break;
                case "foc_start": foc_start(x);
                    break;
                case "stropire": stropire(x);
                    break;
                case "smoke": smoke(x);
                    break;
            }
            request.remove(0);
        }
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