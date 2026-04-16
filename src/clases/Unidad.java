package clases;

import java.util.HashMap;
import java.util.Random;

public class Unidad {

    /**
     * Atriubutos de una unidad.
     */
    private String nombre;                              //Nombre de la unidad
    private String raza;                                //Raza de la unidad. Puede ser "Humano, Elfo, Enano, Orco, No-muerto, Demonio, HombreLagarto o neutral."
    private int level;                                  //Nivel de la unidad. De 1-5
    private int precio;                                 //Precio de contratar una unidad
    private String liderazgo;                           //Liderazgo necesario para controlar una unidad.
    private int ataque;                                 //Ataque de la unidad. Si es superior a la defensa de su objetivo, el daño será mayor.
    private int defensa;                                //Defensa de la unidad. Si es superior al ataque de su atacante, el daño será menor.
    private int iniciativa;                             //Capacidad de la unidad de moverse antes durante un turno.
    private int velocidad;                              //Número de casillas que la unidad puede recorrer.
    private int vida;                                   //Daño que una unidad puede recibir antes de morir.
    private int danoMinimo;                             //Daño minimo de la unidad.
    private int danoMaximo;                             //Daño máximo de la unidad. Puede ser igual al minimo en según que casos.
    private String danoTipo;                            //Tipo de daño. Puede ser físico, mágico, ígneo, venenoso, sagrado, o de hielo.
    private String resistencia;                         //Resistencia a un tipo de daño.
    private HashMap <String, Boolean> pasivas;          //Paquete de habilidades que porta el individuo.
    private HashMap <String, Boolean> talentos;         //Paquete de talentos que porta el individuo.

    /**
     * Constructor de una unidad.
     */
    public Unidad(){
        this.nombre = null;
        this.raza = "";
        this.level = 0;
        this.precio = 0;
        this.liderazgo = "";
        this.ataque = 0;
        this.defensa = 0;
        this.iniciativa = 0;
        this.velocidad = 0;
        this.vida = 0;
        this.danoMinimo = 0;
        this.danoMaximo = 0;
        this.danoTipo = "";
        this.resistencia = "";
        this.talentos = new HashMap<>();
        this.pasivas = new HashMap<>();
        for (int i = 0; i < 222; i++) {
            this.pasivas.put(String.valueOf(i), false);
        }
        for (int i = 0; i < 222; i++) {
            this.talentos.put(String.valueOf(i), false);
        }
    }

    /**
     * Métodos definidos
     */
    /**
     * @param num Número alatorio de pasivas, definido por el nivel de la unidad.
     * @param random Objeto que permite definir números aleatorios.
     */
    public void AlterarPasivas (int num, Random random){
        for (int i = 0; i < num; i++) {
            int randomNum = random.nextInt(222) + 1;
            this.pasivas.replace(String.valueOf(randomNum), true);
        }
    }

    /**
     * @return Cadena de texto que imprime en pantalla la información de un objeto.
     */
    @Override
    public String toString() {
        return "Unidad:" +
                "\n\tNombre=" + nombre +
                "\n\tRaza= " + raza +
                "\n\tLevel=" + level +
                "\n\tPrecio=" + precio +
                "\n\tLiderazgo='" + liderazgo + 
                "\n\tAtaque=" + ataque +
                "\n\tDefensa=" + defensa +
                "\n\tIniciativa=" + iniciativa +
                "\n\tVelocidad=" + velocidad +
                "\n\tVida=" + vida +
                "\n\tDaño Minimo=" + danoMinimo +
                "\n\tDano Máximo=" + danoMaximo +
                "\n\tDano Tipo=" + danoTipo +
                "\n\tResistencia='" + resistencia + 
                "\n\tPasivas=" + HashMapToString(this.pasivas) +
                "\n\tTalentos=" + HashMapToString(this.talentos);
    }

    /**
     * @return Cadena de texto que sirve para escribir directamente en un fichero.
     */
    public String toFile() {
        return "Nombre: " + nombre + "; Raza: " + raza + "; Nivel: " + level  + "; Precio: " + precio  + "; Liderazgo: " + liderazgo  + "; Ataque: " + ataque  + "; Defensa: " + defensa  +
                "; Iniciativa: " +  iniciativa  + "; Velocidad: " + velocidad  + "; Vida: " +  vida + "; Daño: " + danoMinimo  + "-" + danoMaximo  + "; Tipo de daño: " + danoTipo  +
                "; Resistencia: " + resistencia + "; Pasivas: (" + HashMapToString(pasivas) + "); Talentos: (" + HashMapToString(talentos) + ")";
    }

    public String HashMapToString(HashMap<String, Boolean> hashMap) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int i = 0; i < hashMap.size(); i++) {
            Boolean value = hashMap.get(String.valueOf(i));
            if (Boolean.TRUE.equals(value)) {
                if (!first) {
                    sb.append("; ");
                }
                sb.append(i);
                first = false;
            }
        }
        return sb.toString();
    }

    public void AlterarTalentos(int num, Random random){
        for (int i = 0; i < num; i++) {
            int randomNum = random.nextInt(222) + 1;
            this.talentos.replace(String.valueOf(randomNum), true);
        }
    }
    
    /**
     * Métodos auto-generados.
     */

    /**
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return nombre
     */
    public String getRaza() {
        return raza;
    }

    /**
     * @param nombre
     */
    public void setRaza(String raza) {
        this.raza = raza;
    }

    /**
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     * @return
     */
    public int getPrecio() {
        return precio;
    }

    /**
     *
     * @param precio
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     *
     * @return
     */
    public String getLiderazgo() {
        return liderazgo;
    }

    /**
     *
     * @param liderazgo
     */
    public void setLiderazgo(String liderazgo) {
        this.liderazgo = liderazgo;
    }

    /**
     *
     * @return
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     *
     * @param ataque
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     *
     * @return
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     *
     * @param defensa
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     *
     * @return
     */
    public int getIniciativa() {
        return iniciativa;
    }

    /**
     *
     * @param iniciativa
     */
    public void setIniciativa(int iniciativa) {
        this.iniciativa = iniciativa;
    }

    /**
     *
     * @return
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     *
     * @param velocidad
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     *
     * @return
     */
    public int getVida() {
        return vida;
    }

    /**
     *
     * @param vida
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     *
     * @return
     */
    public int getDanoMinimo() {
        return danoMinimo;
    }

    /**
     *
     * @param danoMinimo
     */
    public void setDanoMinimo(int danoMinimo) {
        this.danoMinimo = danoMinimo;
    }

    /**
     *
     * @return
     */
    public int getDanoMaximo() {
        return danoMaximo;
    }

    /**
     *
     * @param danoMaximo
     */
    public void setDanoMaximo(int danoMaximo) {
        this.danoMaximo = danoMaximo;
    }

    /**
     *
     * @return
     */
    public String getDanoTipo() {
        return danoTipo;
    }

    /**
     *
     * @param danoTipo
     */
    public void setDanoTipo(String danoTipo) {
        this.danoTipo = danoTipo;
    }

    /**
     *
     * @return
     */
    public String getResistencia() {
        return resistencia;
    }

    /**
     *
     * @param resistencia
     */
    public void setResistencia(String resistencia) {
        this.resistencia = resistencia;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Boolean> getPasivas() {
        return pasivas;
    }

    public void setTalentos(HashMap<String, Boolean> talentos) {
        this.talentos = talentos;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Boolean> getTalentos() {
        return talentos;
    }
}