import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterBoxShape;
import com.jme3.effect.shapes.EmitterSphereShape;
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
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.SceneProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.LightScatteringFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import com.jme3.shadow.*;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;

import java.io.IOException;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class graphicEngine extends SimpleApplication implements ActionListener{

    public static void main(String[] args) {

    }
    private BulletAppState bulletAppState;
    private CharacterControl player;
    private Spatial map,bloc,led;
    private Spatial bec;
    private Spatial detector_fum;
    private Spatial stropitoare;
    private Spatial vent;
    private Spatial barbat1;
    private Spatial femeie1;
    private ParticleEmitter smoketrail1;
    private ParticleEmitter water1,water2,water3,water4;
    private ParticleEmitter fire1;
    private PointLight light1;
    private PointLightShadowRenderer dlsr1;
    private PointLightShadowFilter dlsf1;
    private SpotLightShadowRenderer[] dlsr_led=new SpotLightShadowRenderer[61];
    private SpotLightShadowFilter[] dlsf_led=new  SpotLightShadowFilter[61];
    private BitmapText hudText,hudText2,hudText3,hudText4,hudText5,hudText6,hudText7,hudText8,hudText9,hudText12,hudText13,hudText14,hudText15,hudText16,hudText17,hudText18,hudText19;
    private boolean left = false, right = false, up = false, down = false,camera=false,tp=false;
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private Vector3f walkDirection = new Vector3f();
    private int intensitate_fum=0;
    private String locatie = "";
    public static List<requestHandler> request = new ArrayList<requestHandler>();
    private SpotLight[] lumina_leduri = new SpotLight[61];

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
       // load_object(new requestHandler("load","Modele\\Harta\\6.zip","6.mesh.j3o", "map",-420,1,-110,7f,7f,7f,0,0,0,0));//harta
       // load_object(new requestHandler("load","Modele\\Harta\\3.zip","3.mesh.j3o", "map",-420,1,-110,7f,7f,7f,0,0,0,0));//harta
        load_object(new requestHandler("load","Modele\\Harta\\1.zip","1.mesh.j3o", "map",-144,14,-1637,7f,7f,7f,0,0,0,0));//facultate
       /* load_object(new requestHandler("load","Modele\\Harta\\9.zip","9.mesh.j3o", "bloc",81,-12,-2603,7f,7f,7f,0,0,0,0));//parcare+terenuri
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
        load_object(new requestHandler("load","Modele\\Harta\\17.zip","173.mesh.j3o", "bloc",3170,13,-820,7f,7f,7f,0,0,0,0));//cladiri cercetare*/

        for(int i = 1288;i>=869;i=i-22)
        {
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",i,149.8f,-1907,1f,1f,1f,0,0,0,0));
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",i,117.2f,-1907,1f,1f,1f,0,0,0,0));
        }

        for(int i = 1912;i<=1979;i=i+22)
        {
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",867,149.8f,-i,1f,1f,1f,0,1.57f,0,0));
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",867,117.2f,-i,1f,1f,1f,0,1.57f,0,0));
        }

        for(int i = 1806;i<=1920;i=i+22)
        {
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",1010,149.8f,-i,1f,1f,1f,0,1.57f,0,0));
            load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",1010,117.2f,-i,1f,1f,1f,0,1.57f,0,0));
        }

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
       // pic.setWidth(settings.getWidth()/8);
      //  pic.setHeight(settings.getHeight()/2);
        pic.setWidth(210);
        pic.setHeight(360);
        pic.setPosition(0, 20);
        guiNode.attachChild(pic);

        Picture pic2 = new Picture("HUD Picture");
        assetManager.registerLocator("Modele\\Materiale\\hud1.zip", ZipLocator.class);
        pic2.setImage(assetManager, "hud1.jpg", true);
        Material mat2 = pic2.getMaterial().clone();
        mat2.setColor("Color", new ColorRGBA(1,1,1,0.8f)); // Red with 50% transparency
        mat2.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        pic2.setMaterial(mat2);
        // pic2.setWidth(settings.getWidth()/8);
        //  pic2.setHeight(settings.getHeight()/2);
        pic2.setWidth(210);
        pic2.setHeight(360);
        pic2.setPosition(settings.getWidth()-120, 20);
        guiNode.attachChild(pic2);

        BitmapFont myFont = assetManager.loadFont("Interface/Fonts/Console.fnt");
        hudText = new BitmapText(myFont, false);
        hudText.setSize(15);
        hudText.setColor(ColorRGBA.Green);           // the text
        hudText.setLocalTranslation(10, (settings.getHeight()/2), 300); // position
        guiNode.attachChild(hudText);

        hudText2 = new BitmapText(myFont, false);
        hudText2.setSize(15);
        hudText2.setColor(ColorRGBA.Green);           // the text
        hudText2.setLocalTranslation(10, (settings.getHeight()/2)-40, 300); // position
        guiNode.attachChild(hudText2);

        hudText3 = new BitmapText(myFont, false);
        hudText3.setSize(15);
        hudText3.setColor(ColorRGBA.Green);           // the text
        hudText3.setLocalTranslation(10, (settings.getHeight()/2)-80, 300); // position
        guiNode.attachChild(hudText3);

        hudText4 = new BitmapText(myFont, false);
        hudText4.setSize(15);
        hudText4.setColor(ColorRGBA.Green);           // the text
        hudText4.setLocalTranslation(10, (settings.getHeight()/2)-120, 300); // position
        guiNode.attachChild(hudText4);

        hudText5 = new BitmapText(myFont, false);
        hudText5.setSize(15);
        hudText5.setColor(ColorRGBA.Green);           // the text
        hudText5.setLocalTranslation(10, (settings.getHeight()/2)-140, 300); // position
        guiNode.attachChild(hudText5);

        hudText6 = new BitmapText(myFont, false);
        hudText6.setSize(15);
        hudText6.setColor(ColorRGBA.Green);           // the text
        hudText6.setLocalTranslation(10, (settings.getHeight()/2)-160, 300); // position
        guiNode.attachChild(hudText6);

        hudText7 = new BitmapText(myFont, false);
        hudText7.setSize(15);
        hudText7.setColor(ColorRGBA.Green);           // the text
        hudText7.setLocalTranslation(10, (settings.getHeight()/2)-180, 300); // position
        guiNode.attachChild(hudText7);

        hudText8 = new BitmapText(myFont, false);
        hudText8.setSize(15);
        hudText8.setColor(ColorRGBA.Green);           // the text
        hudText8.setLocalTranslation(10, (settings.getHeight()/2)-200, 300); // position
        guiNode.attachChild(hudText8);

        hudText9 = new BitmapText(myFont, false);
        hudText9.setSize(15);
        hudText9.setColor(ColorRGBA.Green);           // the text
        hudText9.setLocalTranslation(10, (settings.getHeight()/2)-220, 300); // position
        guiNode.attachChild(hudText9);

        hudText12 = new BitmapText(myFont, false);
        hudText12.setSize(15);
        hudText12.setColor(ColorRGBA.Green);           // the text
        hudText12.setLocalTranslation(settings.getWidth()-100, 350, 300); // position
        hudText12.setText("Camera 1");
        guiNode.attachChild(hudText12);

        hudText13 = new BitmapText(myFont, false);
        hudText13.setSize(15);
        hudText13.setColor(ColorRGBA.Green);           // the text
        hudText13.setLocalTranslation(settings.getWidth()-100, 330, 300); // position
        hudText13.setText("Camera 2");
        guiNode.attachChild(hudText13);

        hudText14 = new BitmapText(myFont, false);
        hudText14.setSize(15);
        hudText14.setColor(ColorRGBA.Green);           // the text
        hudText14.setLocalTranslation(settings.getWidth()-100, 310, 300); // position
        hudText14.setText("Camera 3");
        guiNode.attachChild(hudText14);

        hudText15 = new BitmapText(myFont, false);
        hudText15.setSize(15);
        hudText15.setColor(ColorRGBA.Green);           // the text
        hudText15.setLocalTranslation(settings.getWidth()-100, 290, 300); // position
        hudText15.setText("Camera 4");
        guiNode.attachChild(hudText15);

        hudText16 = new BitmapText(myFont, false);
        hudText16.setSize(15);
        hudText16.setColor(ColorRGBA.Green);           // the text
        hudText16.setLocalTranslation(settings.getWidth()-100, 270, 300); // position
        hudText16.setText("Camera 5");
        guiNode.attachChild(hudText16);

        hudText17 = new BitmapText(myFont, false);
        hudText17.setSize(15);
        hudText17.setColor(ColorRGBA.Green);           // the text
        hudText17.setLocalTranslation(settings.getWidth()-100, 250, 300); // position
        hudText17.setText("Camera 6");
        guiNode.attachChild(hudText17);

        hudText18 = new BitmapText(myFont, false);
        hudText18.setSize(15);
        hudText18.setColor(ColorRGBA.Green);           // the text
        hudText18.setLocalTranslation(settings.getWidth()-100, 230, 300); // position
        hudText18.setText("Camera 7");
        guiNode.attachChild(hudText18);

        hudText19 = new BitmapText(myFont, false);
        hudText19.setSize(15);
        hudText19.setColor(ColorRGBA.Green);           // the text
        hudText19.setLocalTranslation(settings.getWidth()-100, 210, 300); // position
        hudText19.setText("server");
        guiNode.attachChild(hudText19);
    }

    public  void load_player()
    {
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 12f, 1);
        player = new CharacterControl(capsuleShape, 2.5f);
        player.setJumpSpeed(20);
        player.setFallSpeed(50);
        player.setGravity(40);
        player.setPhysicsLocation(new Vector3f(0, 10, 0));
        player.setPhysicsLocation(new Vector3f(1028,160,-2078));
        bulletAppState.getPhysicsSpace().add(player);
    }
    public void cameraSetup()
    {
        cam.setFrustumFar(4000);
        cam.onFrameChange();
        flyCam.setMoveSpeed(300);
    }
    public void lightSetup()
    {
        PointLight lamp_light2 = new PointLight();
        lamp_light2.setColor(ColorRGBA.White.mult(0.2f));
        lamp_light2.setRadius(100000);
        lamp_light2.setPosition(new Vector3f(-5000,5000,5000));
        rootNode.addLight(lamp_light2);

        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.White.mult(0.7f));
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

    public void load_leds()
    {
        load_object(new requestHandler("load","Modele\\Harta\\spot.zip","bec2.j3o", "map",1050,149.8f,-2078,1f,1f,1f,0,0,0,0));


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
                case "water2":
                    water2 = numeObiect;
                    rootNode.attachChild(water2);
                    break;
                case "water3":
                    water3 = numeObiect;
                    rootNode.attachChild(water3);
                    break;
                case "water4":
                    water4 = numeObiect;
                    rootNode.attachChild(water4);
                    break;
            }
        }
        else
        {
            switch (x.nume_obiect) {
                case "water1":
                    rootNode.detachChild(water1);
                    break;
                case "water2":
                    rootNode.detachChild(water2);
                    break;
                case "water3":
                    rootNode.detachChild(water3);
                    break;
                case "water4":
                    rootNode.detachChild(water4);
                    break;
            }
        }
    }

    public void foc_start(requestHandler x){
        ParticleEmitter numeObiect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 100*x.intensitate_foc);
        Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        numeObiect.setMaterial(mat_red);
        numeObiect.setImagesX(2);
        numeObiect.setImagesY(2); // 2x2 texture animation
        numeObiect.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
        numeObiect.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        numeObiect.setInitialVelocity(new Vector3f(0, 8, 0));
        numeObiect.setStartSize((8/(float)10)*6.6f);
        numeObiect.setEndSize((8/(float)10)*0.5f);
        numeObiect.setGravity(0, 0, 0);
        numeObiect.setLowLife((x.intensitate_foc/(float)10)*2.5f);
        numeObiect.setHighLife((x.intensitate_foc/(float)10)*3.5f);
        numeObiect.setVelocityVariation((x.intensitate_foc/(float)10)*0.35f);
        numeObiect.setQueueBucket(RenderQueue.Bucket.Translucent);
        numeObiect.setLocalTranslation((x.translatie_x-(10f*x.intensitate_foc)/2),x.translatie_y,(x.translatie_z-(15f*x.intensitate_foc)/2));
        numeObiect.setShape(new EmitterBoxShape(new Vector3f(0, 0, 0), new Vector3f(10f*x.intensitate_foc, 0.8f*x.intensitate_foc, 15f*x.intensitate_foc)));
        numeObiect.setNumParticles(1000*x.intensitate_foc);
        numeObiect.setParticlesPerSec(100*x.intensitate_foc);
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
        numeObiect.setStartColor(new ColorRGBA(0.05f, 0.05f, 0.05f,0.5f));
        numeObiect.setEndColor(new ColorRGBA(0.3f,0.3f ,0.3f, 0.5f));
        numeObiect.setLocalTranslation((x.translatie_x-(3f*x.intensitate_foc)/2),x.translatie_y+5,(x.translatie_z-(4f*x.intensitate_foc)/2));
        numeObiect.setInitialVelocity(new Vector3f(0, 8, 0));
        numeObiect.setGravity(0, 0, 0);
        numeObiect.setStartSize((8/(float)10)*6.6f);
        numeObiect.setEndSize((8/(float)10)*0.5f);
        numeObiect.setLowLife((30/(float)10)*2.5f);
        numeObiect.setHighLife((30/(float)10)*3.5f);
        numeObiect.setShape(new EmitterBoxShape(new Vector3f(0, 0, 0), new Vector3f(3f*30, 0.6f*30, 4f*30)));
        numeObiect.setNumParticles(800*x.intensitate_foc);
        numeObiect.setQueueBucket(RenderQueue.Bucket.Translucent);
        numeObiect.setParticlesPerSec(100*x.intensitate_foc);

        if(x.pornit) {
            switch (x.nume_obiect) {
                case "smoketrail1":
                    if(smoketrail1!=null)
                        rootNode.detachChild(smoketrail1);
                    smoketrail1 = numeObiect;
                    rootNode.attachChild(smoketrail1);
                    break;
            }
        }
        else
        {
            switch (x.nume_obiect) {
                case "smoketrail1":
                    rootNode.detachChild(smoketrail1);
                    break;
            }
        }
    }

    public void load_light(requestHandler x) {
        PointLight lamp_light = new PointLight();
        final int SHADOWMAP_SIZE = 1024;
        PointLightShadowRenderer dlsr = new PointLightShadowRenderer(assetManager, SHADOWMAP_SIZE);
        PointLightShadowFilter dlsf = new PointLightShadowFilter(assetManager, SHADOWMAP_SIZE);
        lamp_light.setRadius(x.suprafata);
        lamp_light.setPosition(new Vector3f(x.translatie_x, x.translatie_y, x.translatie_z));


        if(x.nume_obiect.equals("led"))
        {


            if(x.pornit==false && lumina_leduri[x.index]!=null)
            {
                    rootNode.removeLight(lumina_leduri[x.index]);
                    viewPort.removeProcessor(dlsr_led[x.index]);
                    dlsf_led[x.index].setEnabled(false);
            }
            else
            {
                lumina_leduri[x.index] = new SpotLight();
                dlsr_led[x.index]=new SpotLightShadowRenderer(assetManager, SHADOWMAP_SIZE);
                dlsf_led[x.index]=new SpotLightShadowFilter(assetManager, SHADOWMAP_SIZE);

                lumina_leduri[x.index].setColor(x.culoare.mult(x.intensitate_lumina));
                lumina_leduri[x.index].setSpotRange(x.suprafata);
                lumina_leduri[x.index].setPosition(new Vector3f(x.translatie_x, x.translatie_y, x.translatie_z));
                lumina_leduri[x.index].setDirection(new Vector3f(0, -1, 0));
                lumina_leduri[x.index].setSpotInnerAngle(10f * FastMath.DEG_TO_RAD);
                lumina_leduri[x.index].setSpotOuterAngle(85f * FastMath.DEG_TO_RAD);
                dlsr_led[x.index].setLight(lumina_leduri[x.index]);
                dlsr_led[x.index].setShadowIntensity(0.8f);
                dlsf_led[x.index].setLight(lumina_leduri[x.index]);

                rootNode.addLight(lumina_leduri[x.index]);
                viewPort.addProcessor(dlsr_led[x.index]);
                dlsf_led[x.index].setEnabled(true);
            }
        }
        else
        {
            if (x.alarma) {
                lamp_light.setColor(ColorRGBA.Red.mult(x.intensitate_lumina));
            } else {
                lamp_light.setColor(ColorRGBA.White.mult(x.intensitate_lumina));
            }
            dlsr.setLight(lamp_light);
            dlsr.setShadowIntensity(0.8f);
            dlsf.setLight(lamp_light);
        }

        switch (x.nume_obiect) {
            case "light1":
                if(light1!=null) {
                    rootNode.removeLight(light1);
                    viewPort.removeProcessor(dlsr1);
                    dlsf1.setEnabled(false);
                }
                light1 = lamp_light;
                dlsr1 = dlsr;
                dlsf1 =dlsf;
                rootNode.addLight(light1);
                viewPort.addProcessor(dlsr1);
                dlsf1.setEnabled(true);
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
                case "leduri": load_light(x);
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

        float x = cam.getLocation().getX();
        float y = cam.getLocation().getY();
        float z = cam.getLocation().getZ();

        if((x<1100 && x>935) && (y<180&&y>150) && (z<-1948&&z>-2164))
            locatie = "Camera 1";
        else if((x<1267 && x>1162) && (y<180&&y>150) && (z>-2015&&z<-1935))
            locatie = "Camera 2";
        else if((x<1182 && x>1077) && (y<180&&y>150) && (z>-1875&&z<-1795))
            locatie = "Camera 3";
        else if((x<1296 && x>1189) && (y<180&&y>150) && (z>-1860&&z<-1780))
            locatie = "Camera 4";
        else if((x<1267 && x>1162) && (y<148&&y>120) && (z>-2015&&z<-1935))
            locatie = "Camera 5";
        else if((x<1182 && x>1077) && (y<148&&y>120) && (z>-1875&&z<-1795))
            locatie = "Camera 6";
        else if((x<1296 && x>1189) && (y<148&&y>120) && (z>-1860&&z<-1780))
            locatie = "Camera 7";
        else locatie = "Neacoperita";


        hudText.setText("Coordonate:\n" + (int)cam.getLocation().getX() +"x"+ " "+(int)cam.getLocation().getY()+"y"+" "+(int)cam.getLocation().getZ()+"z");
        hudText2.setText("Locatie: "+locatie);
        hudText3.setText("Temperatura:\n" + environment.temperatura_interior+" °C");
        hudText4.setText("Foc: "+ environment.foc);
        hudText5.setText("Fum: "+environment.fum);
        String blabla = null;
        if(environment.ventilatie==2)
            blabla = "Trage aer";
        if(environment.ventilatie>=-1&&environment.ventilatie<=1)
            blabla = "Auto";
        if(environment.ventilatie==3)
            blabla = "Manual";
        hudText6.setText("Ventilatie: "+ blabla);
        hudText7.setText("Sprinkler: "+ (boolean)environment.sprinkler);
        hudText8.setText("Electricitate: "+ (boolean)environment.curent_electric);
        hudText9.setText("L. urgenta: "+ (boolean)environment.lumini_urgenta);

        /*
        if(controller.lista_celule.get(0).contains("Camera1"))
            hudText12.setColor(ColorRGBA.Green);
        else
            hudText12.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera2"))
            hudText13.setColor(ColorRGBA.Green);
        else
            hudText13.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera3"))
            hudText14.setColor(ColorRGBA.Green);
        else
            hudText14.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera4"))
            hudText15.setColor(ColorRGBA.Green);
        else
            hudText15.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera5"))
            hudText16.setColor(ColorRGBA.Green);
        else
            hudText16.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera6"))
            hudText17.setColor(ColorRGBA.Green);
        else
            hudText17.setColor(ColorRGBA.Gray);
        if(controller.lista_celule.get(0).contains("Camera7"))
            hudText18.setColor(ColorRGBA.Green);
        else
            hudText18.setColor(ColorRGBA.Gray);*/
        if(controller.lista_celule.get(0).contains("server"))
            hudText19.setColor(ColorRGBA.Green);
        else
            hudText19.setColor(ColorRGBA.Gray);


    }
}