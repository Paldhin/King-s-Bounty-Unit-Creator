package clases;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
    private static final HashMap<String, String> pasivaNombres = loadNameMap("Pasivas.txt");
    private static final HashMap<String, String> talentoNombres = loadNameMap("Talentos.txt");

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
        for (int i = 0; i <= 222; i++) {
            this.pasivas.put(String.valueOf(i), false);
        }
        for (int i = 0; i <= 222; i++) {
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

    public void AlterarTalentos(int num, Random random){
        for (int i = 0; i < num; i++) {
            int randomNum = random.nextInt(94) + 1;
            this.talentos.replace(String.valueOf(randomNum), true);
        }
    }

    /**
     * @return Cadena de texto que imprime en pantalla la información de un objeto.
     */
    @Override
    public String toString() {
        return "Unidad:" +
                "\n\tNombre = " + nombre +
                "\n\tRaza = " + raza +
                "\n\tNivel = " + level +
                "\n\tPrecio = " + precio +
                "\n\tLiderazgo = " + liderazgo +
                "\n\tAtaque = " + ataque +
                "\n\tDefensa = " + defensa +
                "\n\tIniciativa = " + iniciativa +
                "\n\tVelocidad = " + velocidad +
                "\n\tVida = " + vida +
                "\n\tDaño Minimo = " + danoMinimo +
                "\n\tDaño Máximo = " + danoMaximo +
                "\n\tDaño Tipo = " + danoTipo +
                "\n\tResistencia = " + resistencia +
                "\n\tPasivas = " + HashMapToString(this.pasivas, "Ninguna", pasivaNombres) +
                "\n\tTalentos = " + HashMapToString(this.talentos, "Ninguno", talentoNombres);
    }

    /**
     * @return Cadena de texto que sirve para escribir directamente en un fichero.
     */
    public String toFile() {
        return "Nombre: " + nombre + "; Raza: " + raza + "; Nivel: " + level  + "; Precio: " + precio  + "; Liderazgo: " + liderazgo  + "; Ataque: " + ataque  + "; Defensa: " + defensa  +
                "; Iniciativa: " +  iniciativa  + "; Velocidad: " + velocidad  + "; Vida: " +  vida + "; Daño: " + danoMinimo  + "-" + danoMaximo  + "; Tipo de daño: " + danoTipo  +
                "; Resistencia: " + resistencia + "; Pasivas: (" + HashMapToString(pasivas, "Ninguna", pasivaNombres) + "); Talentos: (" + HashMapToString(talentos, "Ninguno", talentoNombres) + ")";
    }

    public String HashMapToString(HashMap<String, Boolean> hashMap) {
        return HashMapToString(hashMap, "Ninguno");
    }

    public String HashMapToString(HashMap<String, Boolean> hashMap, String emptyValue) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        List<String> keys = new ArrayList<>(hashMap.keySet());
        keys.sort(Comparator.comparingInt(Integer::parseInt));
        for (String key : keys) {
            Boolean value = hashMap.get(key);
            if (Boolean.TRUE.equals(value)) {
                if (!first) {
                    sb.append("; ");
                }
                sb.append(key);
                first = false;
            }
        }
        return first ? emptyValue : sb.toString();
    }

    public String HashMapToString(HashMap<String, Boolean> hashMap, String emptyValue, HashMap<String, String> nameMap) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        List<String> keys = new ArrayList<>(hashMap.keySet());
        keys.sort(Comparator.comparingInt(Integer::parseInt));
        for (String key : keys) {
            Boolean value = hashMap.get(key);
            if (Boolean.TRUE.equals(value)) {
                if (!first) {
                    sb.append("; ");
                }
                String name = nameMap.get(key);
                sb.append(name != null && !name.isBlank() ? name : key);
                first = false;
            }
        }
        return first ? emptyValue : sb.toString();
    }

    private static HashMap<String, String> loadNameMap(String fileName) {
        HashMap<String, String> names = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(fileName), StandardCharsets.UTF_8);
            int index = 1;
            for (String line : lines) {
                if (line == null || line.isBlank()) {
                    continue;
                }
                int separator = line.indexOf(';');
                String rawName = separator >= 0 ? line.substring(0, separator).trim() : line.trim();
                if (rawName.isEmpty()) {
                    continue;
                }
                names.put(String.valueOf(index), rawName);
                index++;
            }
        } catch (IOException ex) {
            // Si no se puede cargar, mantenemos nombres numéricos.
        }
        return names;
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