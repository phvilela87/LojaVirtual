package dm114.br.inatel.pvilela.lojavirtual.models;

import java.io.Serializable;

public class ProductItem implements Serializable {
    public int Id;
    public Product Product;
    public int ProductId;

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

    public dm114.br.inatel.pvilela.lojavirtual.models.Product getProduct() {
        return Product;
    }

    public void setProduct(dm114.br.inatel.pvilela.lojavirtual.models.Product product) {
        Product = product;
    }

    public int getOrderId() {
        return ProductId;
    }

    public void setOrderId(int orderId) {
        ProductId = orderId;
    }
}
