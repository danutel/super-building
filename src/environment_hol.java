import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class environment_hol extends Agent{
    private String locatie;
    private float Y;
    public static int[] fum = new int[6];
    public static int[] foc = new int[6];
    public static  boolean[] curent_electric=new boolean[6];
    public static  boolean[] lumini_urgenta=new boolean[6];
    public static  boolean []sprinkler=new boolean[6];
    public static boolean alarma_incendiu;
    public static double ventilatie[]=new double[6]; //0-oprit 1-reglare temperatura 2-trage aer din interior(pentru situatii de urgenta)
    public static String adresa_server,nume_server;
    private boolean []sprinkler_activated=new boolean[6];
    private boolean[] curent_electric_activated=new boolean[6];
    private int[] foc_modificat=new int[6],fum_modificat=new int[6];
    private boolean lumini_urgenta_activated=false;
    private boolean alarma_incendiu_activated;
    public static boolean change_led=true;
    public static int mod_leduri=0;
    private leduri[] banda_leduri;
    private int index;
    private int [] X = {840,950,1050,1150,1250,1005};
    private int [] Z = {-1936,-1885,-1885,-1885,-1885,-1810};
    @Override
    public void setup()
    {
        locatie = getAID().getName().split("@")[1];

        locatie="hol4";

        switch (locatie){
            case "hol4":{Y=181.5f;index=9;break;}
            case "hol3":{Y=149;index=14;break;}
            default: {Y=0;}
        }

        requestHandler x = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[0]-10,Y-1,Z[0]-10,1,1,1,0,0,0,0);
        graphicEngine.request.add(x);
        requestHandler y = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[0],Y-1,Z[0],1,1,1,0,0,0,0);
        graphicEngine.request.add(y);
        requestHandler z = new requestHandler("light",index,true,false,1f,150f,X[0], (int) (Y-8),Z[0]);
        graphicEngine.request.add(z);
        requestHandler w  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[0]-25,Y-1,Z[0]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w);
        requestHandler w2 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[0]-25,Y-1,Z[0]+25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w2);
        requestHandler w3 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[0]+25,Y-1,Z[0]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w3);
        requestHandler w4 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[0]+25,Y-1,Z[0]+25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w4);
        requestHandler vent = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[0],Y+2,Z[0]-30,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent);

        requestHandler x2 = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[1]-10,Y-1,Z[1]-10,1,1,1,0,0,0,0);
        graphicEngine.request.add(x2);
        requestHandler y2 = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[1],Y-1,Z[1]-30,1,1,1,0,0,0,0);
        graphicEngine.request.add(y2);
        requestHandler z2 = new requestHandler("light",index+1,true,false,1f,150f,X[1],(int)Y-8,Z[1]-30);
        graphicEngine.request.add(z2);
        requestHandler w21  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[1]-25,Y-1,Z[1]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w21);
        requestHandler w32 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[1]+25,Y-1,Z[1]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w32);
        requestHandler vent2 = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[1],Y+2,Z[1],0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent2);

        requestHandler x22 = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[2]-10,Y-1,Z[2]-10,1,1,1,0,0,0,0);
        graphicEngine.request.add(x22);
        requestHandler y22 = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[2],Y-1,Z[2]-30,1,1,1,0,0,0,0);
        graphicEngine.request.add(y22);
        requestHandler z22 = new requestHandler("light",index+2,true,false,1f,150f,X[2],(int)Y-8,Z[2]-30);
        graphicEngine.request.add(z22);
        requestHandler w212  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[2]-25,Y-1,Z[2]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w212);
        requestHandler w322 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[2]+25,Y-1,Z[2]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w322);
        requestHandler vent22 = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[2],Y+2,Z[2],0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent22);

        requestHandler x222 = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[3]-10,Y-1,Z[3]-10,1,1,1,0,0,0,0);
        graphicEngine.request.add(x222);
        requestHandler y222 = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[3],Y-1,Z[3]-30,1,1,1,0,0,0,0);
        graphicEngine.request.add(y222);
        requestHandler z222 = new requestHandler("light",index+3,true,false,1f,150f,X[3],(int)Y-8,Z[3]-30);
        graphicEngine.request.add(z222);
        requestHandler w2122  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[3]-25,Y-1,Z[3]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w2122);
        requestHandler w3222 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[3]+25,Y-1,Z[3]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w3222);
        requestHandler vent222= new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[3],Y+2,Z[3],0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent222);

        requestHandler x2222 = new requestHandler("load","Modele/Obiecte/fum.zip","fum.j3o","detector_fum",X[4]-10,Y-1,Z[4]-10,1,1,1,0,0,0,0);
        graphicEngine.request.add(x2222);
        requestHandler y2222 = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[4],Y-1,Z[4]-30,1,1,1,0,0,0,0);
        graphicEngine.request.add(y2222);
        requestHandler z2222 = new requestHandler("light",index+4,true,false,1f,150f,X[4],(int)Y-8,Z[4]-30);
        graphicEngine.request.add(z2222);
        requestHandler w21222  = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[4]-25,Y-1,Z[4]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w21222);
        requestHandler w32222 = new requestHandler("load","Modele/Obiecte/stropitoare.zip","stropitoare.j3o","stropitoare",X[4]+25,Y-1,Z[4]-25,0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(w32222);
        requestHandler vent2222 = new requestHandler("load","Modele/Obiecte/vent.zip","vent.j3o","vent",X[4],Y+2,Z[4],0.04f,0.04f,0.04f,0,0,0,0);
        graphicEngine.request.add(vent2222);

        requestHandler y22222 = new requestHandler("load","Modele/Obiecte/bec.zip","bec.j3o","bec",X[5],Y-1,Z[5]-30,1,1,1,0,0,0,0);
        graphicEngine.request.add(y22222);
        requestHandler z22222 = new requestHandler("light",index+5,true,false,1f,150f,X[5],(int)Y-8,Z[5]-30);
        graphicEngine.request.add(z22222);



        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                if(foc[0]>0)
                {
                    if(fum[0]<35) {
                        fum[0] = 5+fum[0] + foc[0];
                        foc[0]= foc[0] + 1;
                    }
                }

                if(foc[1]>0)
                {
                    if(fum[1]<35) {
                        fum[1] = 5+fum[1] + foc[1];
                        foc[1] = foc[1] + 1;
                    }
                }

                if(foc[2]>0)
                {
                    if(fum[2]<35) {
                        fum[2] = 5+fum[2] + foc[2];
                        foc[2] = foc[2] + 1;
                    }
                }

                if(foc[3]>0)
                {
                    if(fum[3]<35) {
                        fum[3] = 5+fum[3] + foc[3];
                        foc[3] = foc[3] + 1;
                    }
                }

                if(foc[4]>0)
                {
                    if(fum[4]<35) {
                        fum[4] = 5+fum[4] + foc[4];
                        foc[4] = foc[4]+ 1;
                    }
                }

                if(foc[5]>0)
                {
                    if(fum[5]<35) {
                        fum[5] = 5+fum[5] + foc[5];
                        foc[5] = foc[5] + 1;
                    }
                }

                if(ventilatie[0]==2)
                {
                    if(fum[0]>=3)
                        fum[0]=fum[0]-4;
                    else
                        fum[0]=0;
                }

                if(ventilatie[1]==2)
                {
                    if(fum[1]>=3)
                        fum[1]=fum[1]-4;
                    else
                        fum[1]=0;
                }

                if(ventilatie[2]==2)
                {
                    if(fum[2]>=3)
                        fum[2]=fum[2]-4;
                    else
                        fum[2]=0;
                }

                if(ventilatie[3]==2)
                {
                    if(fum[3]>=3)
                        fum[3]=fum[3]-4;
                    else
                        fum[3]=0;
                }

                if(ventilatie[4]==2)
                {
                    if(fum[4]>=3)
                        fum[4]=fum[4]-4;
                    else
                        fum[4]=0;
                }

                if(ventilatie[5]==2)
                {
                    if(fum[5]>=3)
                        fum[5]=fum[5]-4;
                    else
                        fum[5]=0;
                }

                if(sprinkler[0]==true)
                {
                    if(foc[0]>=2)
                        foc[0]=foc[0]-2;
                    else
                        foc[0]=0;
                }

                if(sprinkler[1]==true)
                {
                    if(foc[1]>=2)
                        foc[1]=foc[1]-2;
                    else
                        foc[1]=0;
                }

                if(sprinkler[2]==true)
                {
                    if(foc[2]>=2)
                        foc[2]=foc[2]-2;
                    else
                        foc[2]=0;
                }

                if(sprinkler[3]==true)
                {
                    if(foc[3]>=2)
                        foc[3]=foc[3]-2;
                    else
                        foc[3]=0;
                }

                if(sprinkler[4]==true)
                {
                    if(foc[4]>=2)
                        foc[4]=foc[4]-2;
                    else
                        foc[4]=0;
                }

                if(sprinkler[5]==true)
                {
                    if(foc[5]>=2)
                        foc[5]=foc[5]-2;
                    else
                        foc[5]=0;
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
                for (int i = 0; i < 6; i++) {
                    if (curent_electric[i] == false && curent_electric_activated[i] == true) {
                        curent_electric_activated[i] = false;
                        requestHandler z = new requestHandler("light", index + i, false, false, 3f, 100f, X[i], (int) Y - 7, Z[i]);
                        graphicEngine.request.add(z);
                    } else if (curent_electric[i] == true && curent_electric_activated[i] == false) {
                        curent_electric_activated[i] = true;
                        requestHandler z = new requestHandler("light", index + i, true, false, 3f, 100f, X[i], (int) Y - 7, Z[i]);
                        graphicEngine.request.add(z);
                    }
                }

                for (int i = 0; i < 6; i++)
                {
                    if(foc[i]>0 && foc[i]!=foc_modificat[i])
                    {
                        foc_modificat[i]=foc[i];
                        requestHandler hf = new requestHandler("foc_start",index+i,true,foc[i],X[i],(int)Y-32,Z[i]);
                        graphicEngine.request.add(hf);
                    }
                    else if(foc[i]==0 && foc[i]!=foc_modificat[i])
                    {
                        foc_modificat[i]=foc[i];
                        requestHandler hf = new requestHandler("foc_start",index+i,false,foc[i],X[i],(int)Y-32,Z[i]);
                        graphicEngine.request.add(hf);
                    }
                }

                for (int i = 0; i < 6; i++) {
                    if (fum[i] > 0 && fum[i] != fum_modificat[i]) {
                        fum_modificat[i] = fum[i];
                        requestHandler hf = new requestHandler("smoke", index+i, true, fum[i], X[i], (int)Y - 32, Z[i]);
                        graphicEngine.request.add(hf);
                    } else if (fum[i] == 0 && fum[i] != fum_modificat[i]) {
                        fum_modificat[i] = fum[i];
                        requestHandler hf2 = new requestHandler("smoke", index+i, false, fum[i], X[i], (int)Y - 32, Z[i]);
                        graphicEngine.request.add(hf2);
                    }
                }

                for (int i = 0; i < 6; i++) {
                    if (sprinkler[i] == true && sprinkler_activated[i] == false) {
                        sprinkler_activated[i] = true;
                        requestHandler sprink = new requestHandler("stropire", index+(i*4), true, X[i] - 25, (int)Y - 2, Z[i] - 25);
                        graphicEngine.request.add(sprink);

                        requestHandler sprink2 = new requestHandler("stropire", index+(i*4)+1, true, X[i] - 25, (int)Y - 2, Z[i] + 25);
                        graphicEngine.request.add(sprink2);

                        requestHandler sprink3 = new requestHandler("stropire", index+(i*4)+2, true, X[i] + 25, (int)Y - 2, Z[i] - 25);
                        graphicEngine.request.add(sprink3);

                        requestHandler sprink4 = new requestHandler("stropire", index+(i*4)+3, true, X[i] + 25, (int)Y - 2, Z[i] + 25);
                        graphicEngine.request.add(sprink4);
                    } else if (sprinkler[i] == false && sprinkler_activated[i] == true) {
                        sprinkler_activated[i] = false;
                        requestHandler h = new requestHandler("stropire", index+(i*4), false, X[i] - 20, (int)Y - 2, Z[i] - 28);
                        graphicEngine.request.add(h);

                        requestHandler h2 = new requestHandler("stropire", index+(i*4)+1, false, X[i] - 20, (int)Y - 2, Z[i] - 28);
                        graphicEngine.request.add(h2);

                        requestHandler h3 = new requestHandler("stropire", index+(i*4)+2, false, X[i] - 20, (int)Y - 2, Z[i] - 28);
                        graphicEngine.request.add(h3);

                        requestHandler h4 = new requestHandler("stropire", index+(i*4)+3, false, X[i] - 20, (int)Y - 2, Z[i] - 28);
                        graphicEngine.request.add(h4);
                    }
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
    }
}
