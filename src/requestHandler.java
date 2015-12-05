public class requestHandler {
    public String type = "none";
    public String nume_arhiva;
    public String nume_fisier;
    public String nume_obiect;
    public int translatie_x;
    public int translatie_y;
    public int translatie_z;
    public float scalare_x;
    public float scalare_y;
    public float scalare_z;
    public int rotatie_x;
    public int rotatie_y;
    public int rotatie_z;
    public float masa;
    public boolean pornit;
    public float intensitate_lumina;
    public float suprafata;
    public boolean alarma;
    public int intensitate_foc;

    public requestHandler(String type, String nume_arhiva, String nume_fisier, String nume_obiect, int translatie_x,
                          int translatie_y, int translatie_z, float scalare_x, float scalare_y, float scalare_z,
                          int rotatie_x, int rotatie_y, int rotatie_z, float masa) {
        this.type = type;
        this.nume_arhiva = nume_arhiva;
        this.nume_fisier = nume_fisier;
        this.nume_obiect = nume_obiect;
        this.translatie_x = translatie_x;
        this.translatie_y = translatie_y;
        this.translatie_z = translatie_z;
        this.scalare_x = scalare_x;
        this.scalare_y = scalare_y;
        this.scalare_z = scalare_z;
        this.rotatie_x = rotatie_x;
        this.rotatie_y = rotatie_y;
        this.rotatie_z = rotatie_z;
        this.masa = masa;
    }

    public requestHandler(String type, String nume_obiect, boolean pornit, boolean alarma, float intensitate_lumina, float suprafata, int translatie_x, int translatie_y, int translatie_z) {
        this.translatie_x = translatie_x;
        this.translatie_y = translatie_y;
        this.translatie_z = translatie_z;
        this.type = type;
        this.pornit = pornit;
        this.intensitate_lumina = intensitate_lumina;
        this.suprafata = suprafata;
        this.nume_obiect = nume_obiect;
        this.alarma = alarma;
    }

    public requestHandler(String type, String nume_obiect, boolean pornit, int translatie_x, int translatie_y, int translatie_z) {
        this.translatie_x = translatie_x;
        this.translatie_y = translatie_y;
        this.translatie_z = translatie_z;
        this.type = type;
        this.pornit = pornit;
        this.nume_obiect = nume_obiect;
    }

    public requestHandler(String type, String nume_obiect, boolean pornit, int intensitate, int translatie_x, int translatie_y, int translatie_z) {
        this.translatie_x = translatie_x;
        this.translatie_y = translatie_y;
        this.translatie_z = translatie_z;
        this.type = type;
        this.pornit = pornit;
        this.nume_obiect = nume_obiect;
        this.intensitate_foc = intensitate;
    }
}
