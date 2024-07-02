package priceplaypay;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CheapSharkController {

    private static final Logger logger = LoggerFactory.getLogger(CheapSharkController.class);

    @GetMapping("/cheapshark")
    public List<game> search(@RequestParam(name = "gameName") String gameName) {
        logger.info("Búsqueda del juego: {}", gameName);

        List<game> juegosEncontrados = new ArrayList<>();
        juegosEncontrados.add(new game("CheapShark", "N/A"));
        juegosEncontrados.add(new game("Game", "N/A"));
        juegosEncontrados.add(new game("InstantGaming", "N/A"));

        // Invocación de la API CheapShark
        CheapShark api = new CheapShark("CheapShark");
        api.busquedaAPI(gameName);

        game juegoGame = null;
        game juegoInstantGaming = null;

        // Agregar resultados de CheapShark
        if (api.apiPag.juegos.size() > 0) {
            game juego = api.apiPag.juegos.get(0);
            juegosEncontrados.set(0, juego);
            logger.info("Juego encontrado en CheapShark: {} con precio {}", juego.getName(), juego.getPrecio());
        } else {
            juegosEncontrados.set(0, new game(gameName, "N/A"));
            logger.warn("No se encontró el juego en CheapShark: {}", gameName);
        }

        // Buscar en JSON de Game
        try {
            logger.info("Buscando en ResultadosGame.json...");
            juegoGame = buscarJuegoEnJson("ResultadosGame.json", gameName);
            if (juegoGame != null) {
                juegosEncontrados.set(1, juegoGame);
                logger.info("Juego encontrado en Game: {} con precio {}", juegoGame.getName(), juegoGame.getPrecio());
            } else {
                logger.warn("No se encontró el juego en Game: {}", gameName);
            }
        } catch (Exception e) {
            logger.error("Error buscando en ResultadosGame.json: {}", e.getMessage());
        }

        // Buscar en JSON de InstantGaming
        try {
            logger.info("Buscando en ResultadosInstantGaming.json...");
            juegoInstantGaming = buscarJuegoEnJson("ResultadosInstantGaming.json", gameName);
            if (juegoInstantGaming != null) {
                juegosEncontrados.set(2, juegoInstantGaming);
                logger.info("Juego encontrado en InstantGaming: {} con precio {}", juegoInstantGaming.getName(), juegoInstantGaming.getPrecio());
            } else {
                logger.warn("No se encontró el juego en InstantGaming: {}", gameName);
            }
        } catch (Exception e) {
            logger.error("Error buscando en ResultadosInstantGaming.json: {}", e.getMessage());
        }

        return juegosEncontrados;
    }

    @GetMapping("/Gamesgame")
    public List<game> showGamesgame() {
        logger.info("Obteniendo los juegos de Game");

        // Leer juegos de Game desde el archivo JSON
        List<game> gamesFromGameJson = readGamesFromJson("ResultadosGame.json");
        return gamesFromGameJson;
    }


    @GetMapping("/Gamesinstantgaming")
    public List<game> showGamesinstantgaming() {
        logger.info("Obteniendo los juegos de InstantGaming");

        // Leer juegos de Game desde el archivo JSON
        List<game> gamesFromGameJson = readGamesFromJson("ResultadosInstantGaming.json");
        return gamesFromGameJson;
    }
        
    private List<game> readGamesFromJson(String filePath) {
        List<game> games = new ArrayList<>();
        
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(filePath);
            GamesWrapper gamesWrapper = gson.fromJson(reader, GamesWrapper.class);
            games.addAll(gamesWrapper.getGames());
            reader.close();
        } catch (IOException e) {
            logger.error("Error al leer el archivo JSON {}: {}", filePath, e.getMessage());
        }
        
        return games;
    }
    


    private game buscarJuegoEnJson(String filePath, String gameName) throws IOException {
        Gson gson = new Gson();
        Type pagType = new TypeToken<pag>() {}.getType();
        try (FileReader reader = new FileReader(filePath)) {
            pag resultados = gson.fromJson(reader, pagType);
            return resultados.buscarJuego(gameName);
        }
    }
}

class GamesWrapper {
    private List<game> juegos;

    public List<game> getGames() {
        return juegos;
    }

    public void setGames(List<game> games) {
        this.juegos = games;
    }
}
