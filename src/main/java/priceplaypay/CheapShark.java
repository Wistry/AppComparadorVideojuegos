package priceplaypay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class CheapShark {   

    pag apiPag;

    CheapShark(String nombre){
        this.apiPag = new pag(nombre);
    }

    public void busquedaAPI(String title) {
        String titulo = title.replace(" ", "%20");
        try {
            URL url = new URL("https://www.cheapshark.com/api/1.0/games?title=" + titulo + "&limit=1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nombre = jsonObject.getString("external");
                String precio = jsonObject.getString("cheapest");
                this.apiPag.establecer(1, nombre, precio);
            }
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrar() {
        for (int i = 0; i < apiPag.juegos.size(); i++) {
            System.out.println("Nombre: " + apiPag.juegos.get(i).getName());
            System.out.println("Precio: " + apiPag.juegos.get(i).getPrecio());
            System.out.println();
        }
    }

    public String devolverPrecio() {
        if (this.apiPag.juegos.size() == 0) {
            return "NADA";
        } else {
            return this.apiPag.juegos.get(0).getPrecio(); 
        }
    }

    public static void main(String[] args){
        CheapShark api = new CheapShark("CheapShark");
        api.busquedaAPI("FIFA");
        api.mostrar();
    }
}
