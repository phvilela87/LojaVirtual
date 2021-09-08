package dm114.br.inatel.pvilela.lojavirtual.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pedro on 15/06/16.
 */
public class OrderItem implements Serializable {

    public int Id;
    public int ProductId;
    public int OrderId;
    public Product Product;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product product) {
        Product = product;
    }

}
