package dm114.br.inatel.pvilela.lojavirtual.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pedro on 15/06/16.
 */
public class Order implements Serializable {

    public int Id;
    public String userName;
    public double precoFrete;
    public List<OrderItem> OrderItems;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getPrecoFrete() {
        return precoFrete;
    }

    public void setPrecoFrete(double precoFrete) {
        this.precoFrete = precoFrete;
    }

    public List<OrderItem> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        OrderItems = orderItems;
    }
}
