package dm114.br.inatel.pvilela.lojavirtual.models;

import java.io.Serializable;

/**
 * Created by pedro on 23/06/16.
 */
public class OrderInfo implements Serializable {

    public int orderID;
    public String infoReason;
    public String userEmail;
    public String newOrderStatus;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderId(int orderId) {
        this.orderID = orderId;
    }

    public String getInfoReason() {
        return infoReason;
    }

    public void setInfoReason(String infoReason) {
        this.infoReason = infoReason;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getNewOrderStatus() {
        return newOrderStatus;
    }

    public void setNewOrderStatus(String newOrderStatus) {
        this.newOrderStatus = newOrderStatus;
    }
}
