import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.LightScatteringFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import com.jme3.shadow.PointLightShadowFilter;
import com.jme3.shadow.PointLightShadowRenderer;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.ui.Picture;
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
    private BitmapText hudText,hudText2,hudText3;
    private boolean left = false, right = false, up = false, down = false,camera=false,tp=false;
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private Vector3f walkDirection = new Vector3f();
    public static List<requestHandler> request = new ArrayList<requestHandler>();

    @Override
    public void simpleInitApp() {

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        Spatial sky = SkyFactory.createSky(assetManager, "Textures/Sky/Bright/FullskiesBlueClear03.dds", false);
        sky.setLocalScale(1000);
        rootNode.attachChild(sky);
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);


        loadmap();
        load_hud();
        lightSetup();
        cameraSetup();
        load_player();
        setUpKeys();
        hud();
    }

    public void loadmap()
    {
        load_object(new requestHandler("load","Modele\\Harta\\6.zip","6.mesh.j3o", "map",-420,1,-110,7f,7f,7f,0,0,0,0));//harta
        load_object(new requestHandler("load","Modele\\Harta\\3.zip","3.mesh.j3o", "bloc",-420,1,-110,7f,7f,7f,0,0,0,0));//harta
        load_object(new requestHandler("load","Modele\\Harta\\1.zip","1.mesh.j3o", "bloc",-144,14,-1637,7f,7f,7f,0,0,0,0));//facultate
        load_object(new requestHandler("load","Modele\\Harta\\9.zip","9.mesh.j3o", "bloc",81,-12,-2603,7f,7f,7f,0,0,0,0));//parcare+terenuri
        load_object(new requestHandler("load","Modele\\Harta\\2.zip","2.mesh.j3o", "bloc",-89,13,-314,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\7.zip","7.mesh.j3o", "bloc",2029,10,-415,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\8.zip","8.mesh.j3o", "bloc",1750,10,-2600,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\5.zip","5.mesh.j3o", "bloc",920,13,-360,7f,7f,7f,0,0,0,0));//parc+stadion
        load_object(new requestHandler("load","Modele\\Harta\\5.zip","51.mesh.j3o", "bloc",1470,13,-960,7f,7f,7f,0,0,0,0));//parc+stadion
        load_object(new requestHandler("load","Modele\\Harta\\5.zip","52.mesh.j3o", "bloc",550,13,-1500,7f,7f,7f,0,0,0,0));//parc+stadion
        load_object(new requestHandler("load","Modele\\Harta\\5.zip","53.mesh.j3o", "bloc",0,13,-910,7f,7f,7f,0,0,0,0));//parc+stadion
        load_object(new requestHandler("load","Modele\\Harta\\12.zip","12.mesh.j3o", "bloc",270,8,-489,7f,7f,7f,0,0,0,0));//harta
        load_object(new requestHandler("load","Modele\\Harta\\13.zip","13.mesh.j3o", "bloc",270,8,-489,7f,7f,7f,0,0,0,0));//harta
        load_object(new requestHandler("load","Modele\\Harta\\14.zip","14.mesh.j3o", "bloc",2000,10,-1755,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\15.zip","15.mesh.j3o", "bloc",2000,10,-1755,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\16.zip","16.mesh.j3o", "bloc",2000,10,-1755,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\17.zip","173.mesh.j3o", "bloc",2440,13,-1555,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\17.zip","173.mesh.j3o", "bloc",3170,13,-1555,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\17.zip","173.mesh.j3o", "bloc",2440,13,-820,7f,7f,7f,0,0,0,0));//cladiri cercetare
        load_object(new requestHandler("load","Modele\\Harta\\17.zip","173.mesh.j3o", "bloc",3170,13,-820,7f,7f,7f,0,0,0,0));//cladiri cercetare
    }

    public void load_hud(){
        setDisplayStatView(false);

        Picture pic = new Picture("HUD Picture");
        assetManager.registerLocator("Modele\\Materiale\\hud1.zip", ZipLocator.class);
        pic.setImage(assetManager, "hud1.jpg", true);
        Material mat = pic.getMaterial().clone();
        mat.setColor("Color", new ColorRGBA(1,1,1,0.8f)); // Red with 50% transparency
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        pic.setMaterial(mat);
        pic.setWidth(settings.getWidth()/8);
        pic.setHeight(settings.getHeight()/4);
        pic.setPosition(0, 20);
        guiNode.attachChild(pic);

        BitmapFont myFont = assetManager.loadFont("Interface/Fonts/Console.fnt");
        hudText = new BitmapText(myFont, false);
        hudText.setSize(15);
        hudText.setColor(ColorRGBA.Green);           // the text
        hudText.setLocalTranslation(10, (settings.getHeight()/4), 300); // position
        guiNode.attachChild(hudText);

        hudText2 = new BitmapText(myFont, false);
        hudText2.setSize(15);
        hudText2.setColor(ColorRGBA.Green);           // the text
        hudText2.setLocalTranslation(10, (settings.getHeight()/4)-40, 300); // position
        guiNode.attachChild(hudText2);

        hudText3 = new BitmapText(myFont, false);
        hudText3.setSize(15);
        hudText3.setColor(ColorRGBA.Green);           // the text
        hudText3.setLocalTranslation(10, (settings.getHeight()/4)-80, 300); // position
        guiNode.attachChild(hudText3);
    }

    public  void load_player()
    {
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 12f, 1);
        player = new CharacterControl(capsuleShape, 2.5f);
        player.setJumpSpeed(20);
        player.setFallSpeed(50);
        player.setGravity(50);
        player.setPhysicsLocation(new Vector3f(0, 10, 0));
        player.setPhysicsLocation(new Vector3f(-143,94,-1640));
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
        PointLight lamp_light2 = new PointLight();
        lamp_light2.setColor(ColorRGBA.White.mult(0.4f));
        lamp_light2.setRadius(100000);
        lamp_light2.setPosition(new Vector3f(-5000,5000,5000));
        rootNode.addLight(lamp_light2);

        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.White.mult(1.7f));
        lamp_light.setRadius(100000);
        lamp_light.setPosition(new Vector3f(5000,5000,-5000));

        final int SHADOWMAP_SIZE=1024;
        PointLightShadowRenderer dlsr = new PointLightShadowRenderer(assetManager, SHADOWMAP_SIZE);
        dlsr.setLight(lamp_light);
        dlsr.setShadowIntensity(0.4f);
        rootNode.addLight(lamp_light);
        viewPort.addProcessor(dlsr);

        PointLightShadowFilter dlsf = new PointLightShadowFilter(assetManager, SHADOWMAP_SIZE);
        dlsf.setLight(lamp_light);
        dlsf.setEnabled(true);

      //  BloomFilter bloom=new BloomFilter();

        DepthOfFieldFilter dofFilter = new DepthOfFieldFilter();
        dofFilter.setFocusDistance(0);
        dofFilter.setFocusRange(1000);
        dofFilter.setBlurScale(1f);

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
        fpp.addFilter(dlsf);
        fpp.addFilter(ssaoFilter);
        //fpp.addFilter(bloom);
        fpp.addFilter(dofFilter);
        viewPort.addProcessor(fpp);

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
        dlsr.setShadowIntensity(0.95f);

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
            cam.setLocation(new Vector3f(player.getPhysicsLocation().getX(),player.getPhysicsLocation().getY()+3,player.getPhysicsLocation().getZ()));

        }
        hudText.setText("Coordonate:\n" + (int)cam.getLocation().getX() +"x"+ " "+(int)cam.getLocation().getY()+"y"+" "+(int)cam.getLocation().getZ()+"z");
        hudText2.setText("Locatie:");
        hudText3.setText("Temperatura:\n" + environment.temperatura_interior+" °C");
    }



}