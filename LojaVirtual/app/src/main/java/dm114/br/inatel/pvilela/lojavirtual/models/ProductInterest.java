package dm114.br.inatel.pvilela.lojavirtual.models;

import java.io.Serializable;

/**
 * Created by pedro on 21/06/16.
 */
public class ProductInterest implements Serializable {
    public long id;
    public String clientEmail;
    public String productID;
    public float price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
