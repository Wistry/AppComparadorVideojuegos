package priceplaypay;

import java.util.Vector;


public class pag {
    public String name;
    public Vector<game> juegos;

    public pag(String nombre) {
        this.name = nombre;
        this.juegos = new Vector<>();
    }

    public void establecer(int id, String name, String precio) {
        game juego = new game(name, precio);
        juegos.add(juego);
    }

    public game buscarJuego(String nombreJuego) {
        game juegoEncontrado = null;
        for (game juego : juegos) {
            if (juego.getName().toUpperCase().equals(nombreJuego.toUpperCase())) {
                juegoEncontrado = juego;
                break;
            }
        }
        return juegoEncontrado;
    }
    
}