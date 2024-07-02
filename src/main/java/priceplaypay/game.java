package priceplaypay;


public class game {
    private String _name;
    private String _precio;

    public game(String name, String precio) {
        this._name = name;
        this._precio = precio;
    }
    
    

    public String getName() {
        return this._name;
    }

    public String getPrecio() {
        return this._precio;
    }

    

    public void setName(String name) {
        this._name = name;
    }

    public void setPrecio(String precio) {
        this._precio = precio;
    }
}


