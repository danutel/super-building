import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class environment extends Agent{
    public static float temperatura_interior;
    public static int fum;
    public static int foc;
    public static float temperatura_exterior;
    public static float lumina;
    public static  boolean curent_electric;
    public static  boolean lumini_urgenta;
    public static  boolean sprinkler;
    public static boolean alarma_incendiu;
    public static int ventilatie; //0-oprit 1-reglare temperatura 2-trage aer din interior(pentru situatii de urgenta)
    private boolean sprinkler_activated=false;
    private boolean curent_electric_activated=true;
    private int foc_modificat;
    private boolean lumini_urgenta_activated=false;
    private boolean alarma_incendiu_activated;

    @Override
    public void setup(){

        requestHandler x = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",-665,-87,-140,1,1,1,0,0,0,0);
        graphicEngine.request.add(x);
        requestHandler y = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",-670,-87,-150,1,1,1,0,0,0,0);
        graphicEngine.request.add(y);
        requestHandler z = new requestHandler("light","light1",true,false,3f,100f,-670,-90,-150);
        graphicEngine.request.add(z);
        requestHandler w = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",-670,-87,-130,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w);
        requestHandler vent = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",-660,-84,-120,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent);



        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                if(foc>0)
                {
                    if(fum<30) {
                        fum = fum + foc;
                    }
                    foc=foc+1;
                }

                if(ventilatie==2)
                {
                    if(fum>=3)
                        fum=fum-3;
                    else
                        fum=0;
                }

                if(sprinkler==true)
                {
                    if(foc>=2)
                        foc=foc-2;
                    else
                        foc=0;
                }


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        });

        addBehaviour(new Behaviour() {
            @Override
            public void action() {

                if(sprinkler== true && sprinkler_activated==false)
                {
                    sprinkler_activated=true;
                    requestHandler sprink = new requestHandler("stropire","water1",true,-670,-88,-130);
                    graphicEngine.request.add(sprink);
                }
                else if(sprinkler== false && sprinkler_activated==true)
                {
                    sprinkler_activated=false;
                    requestHandler h = new requestHandler("stropire","water1",false,-670,-88,-130);
                    graphicEngine.request.add(h);
                }

                if(curent_electric==false && curent_electric_activated==true)
                {
                    curent_electric_activated=false;
                    requestHandler z = new requestHandler("light","light1",false,false,3f,100f,-670,-90,-150);
                    graphicEngine.request.add(z);
                }
                else if(curent_electric==true && curent_electric_activated==false)
                {
                    curent_electric_activated=true;
                    requestHandler z = new requestHandler("light","light1",true,false,3f,100f,-670,-90,-150);
                    graphicEngine.request.add(z);
                }

                if(foc>0 && foc!=foc_modificat)
                {
                    foc_modificat=foc;
                    requestHandler hf = new requestHandler("foc_start","fire1",true,foc,-670,-110,-130);
                    graphicEngine.request.add(hf);
                }

                if(lumini_urgenta==true && lumini_urgenta_activated==false)
                {
                    lumini_urgenta_activated=true;
                    requestHandler z = new requestHandler("light","light1",true,true,3f,100f,-670,-90,-150);
                    graphicEngine.request.add(z);
                }
                else if (lumini_urgenta==false && lumini_urgenta_activated==true)
                {
                    lumini_urgenta_activated=false;
                    requestHandler z = new requestHandler("light","light1",true,false,3f,100f,-670,-90,-150);
                    graphicEngine.request.add(z);
                }

                if(alarma_incendiu==true && alarma_incendiu_activated==false)
                {
                    alarma_incendiu_activated=true;
                }
                else if(alarma_incendiu==false && alarma_incendiu_activated==true)
                {
                    alarma_incendiu_activated=false;
                    foc_modificat=foc;
                    requestHandler hf = new requestHandler("foc_start","fire1",false,foc,-670,-110,-130);
                    graphicEngine.request.add(hf);


                }
              /*  try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
*/
            }

            @Override
            public boolean done() {
                return false;
            }
        });

    }
}
