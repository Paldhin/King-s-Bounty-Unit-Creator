package clases;

import java.util.Random;

public class Utilidades {
    
    private final Random random = new Random(); //Número aleatorio genérico. Usado en la asignación de rasgos.

    /**
     * Método que devuelve un número aleatorio del 1-100. El programa, posteriormente, le da valor a esten número. No es un resultado final.
     * @return Número aleatorio del 1-100.
     */
    public int RandomNumber(){
        int num = random.nextInt(1000) + 1;
        return num;
    }

    public void CrearUnidad(Unidad unidad){
        SetRaza(unidad);
        SetLevel(unidad);
        SetPrecio(unidad);
        SetLiderazgo(unidad);
        SetAtaqueDefensa(unidad, 0);
        SetAtaqueDefensa(unidad, 1);
        SetIniciativaVelocidad(unidad, 0);
        SetIniciativaVelocidad(unidad, 1);
        SetVida(unidad);
        SetDano(unidad);
        SetTipoDano(unidad, 0);
        SetTipoDano(unidad, 1);
        SetPasivas(unidad);
        SetTalentos(unidad);
    }

    public void SetRaza(Unidad unidad){
        int numero = RandomNumber();
        if (numero > 0 && numero < 125) {
            unidad.setRaza("Humano");
        } else if (numero > 125 && numero < 250) {
            unidad.setRaza("Elfo");
        } else if (numero > 250 && numero < 375) {
            unidad.setRaza("Enano");
        } else if (numero > 375 && numero < 500) {
            unidad.setRaza("No-Muerto");
        } else if (numero > 500 && numero < 625) {
            unidad.setRaza("Orco");
        } else if (numero > 625 && numero < 750) {
            unidad.setRaza("Demonio");
        } else if (numero > 750 && numero < 875) {
            unidad.setRaza("Lizarman");
        } else if (numero > 875 && numero < 1000) {
            unidad.setRaza("Neutral");
        }
    }

    public void SetLevel(Unidad unidad) {
        int random = RandomNumber();
        if (random > 0 && random < 300) {
            unidad.setLevel(1);
        } else if (random > 300 && random < 600) {
            unidad.setLevel(2);
        } else if (random > 600 && random < 800) {
            unidad.setLevel(3);
        } else if (random > 800 && random < 950) {
            unidad.setLevel(4);
        } else if (random > 950 && random < 1000) {
            unidad.setLevel(5);
        }
    }

    public void SetPrecio(Unidad unidad) {
        int numero = 0;
        switch (unidad.getLevel()) {
            case 1:
                numero = random.nextInt(30) + 1;
                break;
            case 2:
                do{
                numero = random.nextInt(100 + 1);
                } while (numero < 30);
                break;
            case 3:
                do{
                numero = random.nextInt(300) + 1;
                } while (numero < 100);
                break;
            case 4:
                do{
                numero = random.nextInt(2000) + 1;
                } while (numero < 300);
                break;
            case 5:
                do{
                numero = random.nextInt(10000) + 1;
                } while (numero < 2000);
                break;
        }
        unidad.setPrecio(numero);
    }

    public void SetLiderazgo(Unidad unidad) {
        int numero = 0;
        switch (unidad.getLevel()) {
            case 1:
                numero = random.nextInt(10) + 1;
                break;
            case 2:
                do{
                numero = random.nextInt(50) + 1;
                } while (numero < 10);
                break;
            case 3:
                do{
                numero = random.nextInt(100) + 1;
                } while (numero < 50);
                break;
            case 4:
                do{
                numero = random.nextInt(300) + 1;
                } while (numero < 100);
                break;
            case 5:
                do{
                numero = random.nextInt(10000) + 1;
                } while (numero < 300);
                break;
        }
        unidad.setLiderazgo(String.valueOf(numero));
    }

    public void SetAtaqueDefensa (Unidad unidad, int AtDef) {
        int numero = 0;
        switch (unidad.getLevel()) {
            case 1:
                numero = random.nextInt(10) + 1;
                break;
            case 2:
                numero = random.nextInt(15) + 1;
                break;
            case 3:
                numero = random.nextInt(20) + 1;
                break;
            case 4:
                numero = random.nextInt(40) + 1;
                break;
            case 5:
                numero = random.nextInt(100) + 1;
                break;
        }
        if (AtDef == 0) {
            unidad.setAtaque(numero);
        } else {
            unidad.setDefensa(numero);
        }
    }    

    public void SetIniciativaVelocidad (Unidad unidad, int var) {
        int numero;
        numero = random.nextInt(10) + 1;
        if (var == 0) {
            unidad.setIniciativa(numero);
        } else {
            unidad.setVelocidad(numero);
        }
    }

    public void SetVida (Unidad unidad) {
        int numero = 0;
        switch (unidad.getLevel()) {
            case 1:
                numero = random.nextInt(10) + 1;
                break;
            case 2:
                do{
                numero = random.nextInt(50) + 1;
                } while (numero < 10);
                break;
            case 3:
                do{
                numero = random.nextInt(100) + 1;
                } while (numero < 50);
                break;
            case 4:
                do{
                numero = random.nextInt(300) + 1;
                } while (numero < 100);
                break;
            case 5:
                do{
                numero = random.nextInt(5000) + 1;
                } while (numero < 300);
                break;
        }
        unidad.setVida(numero);
    }

    public void SetDano(Unidad unidad) {
        int numero = 0;
        int numero2= 0;
        switch (unidad.getLevel()) {
            case 1:
                numero = random.nextInt(3) + 1;
                numero2 = numero + random.nextInt(3);
                break;
            case 2:
                numero = random.nextInt(5) + 1;
                numero2 = numero + random.nextInt(5);
                break;
            case 3:
                numero = random.nextInt(10) + 1;
                numero2 = numero + random.nextInt(10);
                break;
            case 4:
                numero = random.nextInt(15) + 1;
                numero2 = numero + random.nextInt(15);
                break;
            case 5:
                numero = random.nextInt(50) + 1;
                numero2 = numero + random.nextInt(50);
                break;
        }
        unidad.setDanoMinimo(numero);
        unidad.setDanoMaximo(numero2);
    }

    public void SetTipoDano(Unidad unidad, int num) {
        int numero = random.nextInt(6) + 1;
        String tmp = "";
        switch (numero) {
            case 1:
                tmp = ("Fisico ");
                break;
            case 2:
                tmp = ("Magico ");
                break;
            case 3:
                tmp = ("Igneo ");
                break;
            case 4:
                tmp = ("Sagrado ");
                break;
            case 5:
                tmp = ("Gelido ");
                break;
            case 6:
                tmp = ("Venenoso ");
                break;
        }
        if (num == 1){
            numero = random.nextInt(100) + 1;
            if (numero < 50) {
                switch (numero) {
                    case 1 -> tmp = tmp + "+ Rapier";
                    case 2 -> tmp = tmp + "+ Baston";
                    case 3 -> tmp = tmp + "+ Hacha";
                    case 4 -> tmp = tmp + "+ Espada";
                    case 5 -> tmp = tmp + "+ Arco";
                    case 6 -> tmp = tmp + "+ Canon";
                    case 7 -> tmp = tmp + "+ Catapulta";
                    case 8 -> tmp = tmp + "+ Latigo";
                    case 9 -> tmp = tmp + "+ Pico";
                    case 10 -> tmp = tmp + "+ Tomahawk";
                    case 11 -> tmp = tmp + "+ Mazo";
                    case 12 -> tmp = tmp + "+ Lanza";
                    case 13 -> tmp = tmp + "+ Libro";
                    case 14 -> tmp = tmp + "+ Mangual";
                    case 15 -> tmp = tmp + "+ Tridente";
                    case 16 -> tmp = tmp + "+ Corsaria";
                    case 17 -> tmp = tmp + "+ Martillo";
                    case 18 -> tmp = tmp + "+ Pocion";
                    case 19 -> tmp = tmp + "+ Cuchillas";
                    case 20 -> tmp = tmp + "+ Cuchillo";
                    case 21 -> tmp = tmp + "+ Lanza";
                    case 22 -> tmp = tmp + "+ Alabarda";
                    case 23 -> tmp = tmp + "+ Lucero del Alba";
                    case 24 -> tmp = tmp + "+ Katana";
                    case 25 -> tmp = tmp + "+ Marillo de guerra";
                    case 26 -> tmp = tmp + "+ Nunchaku";
                    case 27 -> tmp = tmp + "+ Puños de hierro";
                    case 28 -> tmp = tmp + "+ Maza";
                    case 29 -> tmp = tmp + "+ Lanza";
                    case 30 -> tmp = tmp + "+ Jabalina";
                    case 31 -> tmp = tmp + "+ Pica";
                    case 32 -> tmp = tmp + "+ Ballesta";
                    case 33 -> tmp = tmp + "+ Greatsword";
                    case 34 -> tmp = tmp + "+ Mandoble";
                    case 35 -> tmp = tmp + "+ Kanabo";
                    case 36 -> tmp = tmp + "+ Garra";
                    case 37 -> tmp = tmp + "+ Colmillo";
                    case 38 -> tmp = tmp + "+ Escudo";
                    case 39 -> tmp = tmp + "+ Falcata";
                    case 40 -> tmp = tmp + "+ Guadaña";
                    case 41 -> tmp = tmp + "+ Hoz";
                    case 42 -> tmp = tmp + "+ Balista";
                    case 43 -> tmp = tmp + "+ Antorcha";
                    case 44 -> tmp = tmp + "+ Cerbatana";
                    case 45 -> tmp = tmp + "+ Tirachinas";
                    case 46 -> tmp = tmp + "+ Honda";
                    case 47 -> tmp = tmp + "+ Bumerang";
                    case 48 -> tmp = tmp + "+ Mosquete";
                    case 49 -> tmp = tmp + "+ Kris";
                    case 50 -> tmp = tmp + "+ Daga";
                }
            } else {
                tmp = tmp + "+ Nada";
            }
            unidad.setDanoTipo(tmp);
        } else unidad.setResistencia(tmp);
    }

    public void SetPasivas(Unidad unidad){
        int num = random.nextInt();
        switch (unidad.getLevel()) {
            case 1:
                num = random.nextInt(5);
                break;
            case 2:
                num = random.nextInt(5) + 1;
                break;
            case 3:
                num = random.nextInt(5) + 2;
                break;
            case 4:
                num = random.nextInt(6) + 3;
                break;
            case 5:
                num = random.nextInt(7) + 3;
                break;
        }
        unidad.AlterarPasivas(num, random);
    }

    public void SetTalentos(Unidad unidad) {
        int num;
        int ran = random.nextInt(100) + 1;
        if (ran <= 40) {
            num = 0;
        } else if (ran <= 65) {
            num = 1;
        } else if (ran <= 80) {
            num = 2;
        } else num = 3;
        unidad.AlterarTalentos(num, random);
    }
}
