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
    private Spatial map,bloc,bec,barbat1,detector_fum,stropitoare,vent;
    private boolean left = false, right = false, up = false, down = false,camera=false,tp=false;
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private Vector3f walkDirection = new Vector3f();
    public static List<requestHandler> request = new ArrayList<requestHandler>();

    @Override
    public void simpleInitApp() {

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        //viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));

        Spatial sky = SkyFactory.createSky(assetManager, "Scenes/Beach/FullskiesSunset0068.dds", false);
        sky.setLocalScale(550);
        rootNode.attachChild(sky);

        requestHandler m = new requestHandler("load","map.zip","main.j3o","map",0,0,0,1,1,1,0,0,0,0);
        requestHandler b = new requestHandler("load","bloc.zip","ggg.j3o", "bloc",-420,1,-110,0.5f,0.5f,0.5f,0,0,0,0);
        load_object(m);
        load_object(b);

        // load_object("Modele/Oameni/femeie1.zip","femeie1.j3o", femeie1,0,0,0,0.25f,0.25f,0.25f,0,0,0,0.5f);
        //load_object("Modele/Oameni/femeie1.zip","femeie1.j3o", femeie1,0,0,0,0.25f,0.25f,0.25f,0,0,0,0.5f);

        lightSetup();
        cameraSetup();
        load_player();
        setUpKeys();
        hud();
        foc_start();
        smoke();
        //stropire();
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

    public void hud(){

    }

    public void stropire()
    {
        ParticleEmitter water = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle,200000 );
        assetManager.registerLocator("assets/stropi.zip", ZipLocator.class);
        Material stropi = assetManager.loadMaterial("stropi.j3m");
        water.setMaterial(stropi);
        water.setLocalTranslation(-670,-88,-130);
        water.setEndColor(  new ColorRGBA(0.8f, 0.8f, 1.0f, 0.5f));   // red
        water.setStartColor(new ColorRGBA(0.6f, 0.6f, 1.0f, 0.0f)); // yellow
        water.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 14, 0));
        water.setStartSize(0.03f);
        water.setEndSize(0.08f);
        water.setGravity(0, 80, 0);
        water.setLowLife(0.5f);
        water.setHighLife(0.6f);
        water.setRandomAngle(true);
        water.setNumParticles(100000);
        water.setParticlesPerSec(4000);
        water.getParticleInfluencer().setVelocityVariation(2f);
        rootNode.attachChild(water);
    }

    public void foc_start(){
        ParticleEmitter fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 300);
        Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        //mat_red.getAdditionalRenderState().setDepthTest(true);
        //mat_red.getAdditionalRenderState().setDepthWrite(true);
        fire.setMaterial(mat_red);
        fire.setImagesX(2); fire.setImagesY(2); // 2x2 texture animation
        fire.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
        fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        fire.setInitialVelocity(new Vector3f(0, 8, 0));
        fire.setStartSize(6.6f);
        fire.setEndSize(0.5f);
        fire.setGravity(0, 0, 0);
        fire.setLowLife(2.5f);
        fire.setHighLife(3.5f);
        fire.setVelocityVariation(0.35f);
        fire.setQueueBucket(RenderQueue.Bucket.Translucent);
        fire.setLocalTranslation(-670,-110,-130);
        rootNode.attachChild(fire);
    }

    public void smoke()
    {
        ParticleEmitter smoketrail = new ParticleEmitter("SmokeTrail", ParticleMesh.Type.Triangle, 2200);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/smoketrail.png"));
        smoketrail.setMaterial(mat);
        smoketrail.setImagesX(1);
        smoketrail.setImagesY(3);
        smoketrail.setStartColor(new ColorRGBA(1f, 0.8f, 0.36f, (float) (1.0f / 1)));
        smoketrail.setEndColor(new ColorRGBA(1f, 0.8f, 0.36f, 0f));
        smoketrail.setInitialVelocity(new Vector3f(0, 5, 0));
        smoketrail.setLocalTranslation(-670,-110,-130);
        smoketrail.setStartSize(2f);
        smoketrail.setEndSize(10f);
        smoketrail.setFacingVelocity(true);
        smoketrail.setParticlesPerSec(100);
        smoketrail.setGravity(0, 0, 0);
        smoketrail.setLowLife(1.4f);
        smoketrail.setHighLife(3.5f);
        smoketrail.setVelocityVariation(0.1f);
        smoketrail.setQueueBucket(RenderQueue.Bucket.Translucent);
        rootNode.attachChild(smoketrail);
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

    public void load_light(requestHandler x)
    {
        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.White.mult(3f));
        lamp_light.setRadius(100f);
        lamp_light.setPosition(new Vector3f(x.translatie_x,x.translatie_y,x.translatie_z));
        rootNode.addLight(lamp_light);

        final int SHADOWMAP_SIZE=1024;
        PointLightShadowRenderer dlsr = new PointLightShadowRenderer(assetManager, SHADOWMAP_SIZE);
        dlsr.setLight(lamp_light);
        viewPort.addProcessor(dlsr);
        rootNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        PointLightShadowFilter dlsf = new PointLightShadowFilter(assetManager, SHADOWMAP_SIZE);
        dlsf.setLight(lamp_light);
        dlsf.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(dlsf);
        viewPort.addProcessor(fpp);
    }

    public void load_object(requestHandler x)
    {
        Spatial numeObiect;
        assetManager.registerLocator(x.nume_arhiva, ZipLocator.class);
        numeObiect = assetManager.loadModel(x.nume_fisier);
        numeObiect.setLocalTranslation(x.translatie_x,x.translatie_y,x.translatie_z);
        numeObiect.scale(x.scalare_x,x.scalare_y,x.scalare_z);
        numeObiect.rotate(x.rotatie_x,x.rotatie_y,x.rotatie_z);
        numeObiect.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
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