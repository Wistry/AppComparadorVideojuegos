package priceplaypay;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class VideojuegoScrapper {
    private static final Logger logger = Logger.getLogger(VideojuegoScrapper.class.getName());
    private WebDriver driver;
    private String url;
    private WebDriverWait wait;

    public void whosys() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            System.setProperty("webdriver.chrome.driver", "tools/drivers/chromedriver_windows.exe");
        } else if (os.contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "tools/drivers/chromedriver_ubuntu");
        }
    }

    public VideojuegoScrapper(String url) {
        this.url = url;
        this.whosys();
        this.driver = new ChromeDriver();
        this.wait = new WebDriverWait(driver, 10);
    }

    public pag scrap() {
        driver.get(url);
        pag pag = null;
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnCookiesAcceptAll")));
            cookieButton.click();
        } catch (TimeoutException e) {
            logger.warning("No se encontró la ventana emergente general de cookies");
        }

        if (url.equals(Global.game)) {
            pag = this.scrap_game();
        } else if (url.equals(Global.instantgaming)) {
            pag = this.scrap_instantgaming();
        }

        driver.quit();
        return pag;
    }

    public pag scrap_game() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("search-item")));
        } catch (TimeoutException e) {
            logger.warning("No se encontró el elemento 'search-item'");
            return null;
        }

        pag game = new pag("Game");
        List<WebElement> juegos = driver.findElements(By.className("search-item"));

        for (int i = 0; i < juegos.size(); i++) {
            WebElement juego = juegos.get(i);
            String titulo = juego.findElement(By.cssSelector(".title > a")).getText();
            String parte_entera = "N/A";
            String parte_decimal = "";
            try {
                parte_entera = juego.findElement(By.cssSelector(".buy--price > .int")).getText();
            } catch (NoSuchElementException e) {
                logger.warning("No se encontró la parte entera del precio para el juego: " + titulo);
            }
            try {
                parte_decimal = juego.findElement(By.cssSelector(".buy--price > .decimal")).getText();
            } catch (NoSuchElementException e) {
                logger.warning("No se encontró la parte decimal del precio para el juego: " + titulo);
            }

            // Reemplaza caracteres no numéricos en las partes entera y decimal
            parte_entera = parte_entera.replaceAll("[^0-9]", "");
            parte_decimal = parte_decimal.replaceAll("[^0-9]", "");

            String precio = parte_entera;
            if (!parte_decimal.isEmpty()) {
                precio += "." + parte_decimal;
            }

            game.establecer(i, titulo, precio);
        }
        return game;
    }

    public pag scrap_instantgaming() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".item.force-badge")));
        } catch (TimeoutException e) {
            logger.warning("No se encontró el elemento '.item.force-badge'");
            return null;
        }

        pag instantgaming = new pag("Instant Gaming");
        List<WebElement> juegos_instantgaming = driver.findElements(By.cssSelector(".item.force-badge"));

        for (int i = 0; i < juegos_instantgaming.size(); i++) {
            WebElement juego = juegos_instantgaming.get(i);
            String titulo = juego.findElement(By.cssSelector(".name > .title")).getText();
            String precio = "N/A";
            try {
                precio = juego.findElement(By.cssSelector(".price")).getText();
                // Reemplaza caracteres no numéricos o puntos
                precio = precio.replaceAll("[^0-9.]", "");
            } catch (NoSuchElementException e) {
                logger.warning("No se encontró el precio para el juego: " + titulo);
            }
            instantgaming.establecer(i, titulo, precio);
        }
        return instantgaming;
    }

    public static void main(String[] args) {
        VideojuegoScrapper tiendaGame = new VideojuegoScrapper(Global.game);
        pag resultadosGame = tiendaGame.scrap();

        VideojuegoScrapper tiendaInstantGaming = new VideojuegoScrapper(Global.instantgaming);
        pag resultadosInstantGaming = tiendaInstantGaming.scrap();

        // Convertir los resultados a JSON y guardarlos en archivos separados
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        try (FileWriter writer = new FileWriter("ResultadosGame.json")) {
            gson.toJson(resultadosGame, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("ResultadosInstantGaming.json")) {
            gson.toJson(resultadosInstantGaming, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
