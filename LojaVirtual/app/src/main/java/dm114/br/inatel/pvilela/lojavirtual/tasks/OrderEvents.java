package dm114.br.inatel.pvilela.lojavirtual.tasks;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.models.Order;
import dm114.br.inatel.pvilela.lojavirtual.models.Product;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 15/06/16.
 */
public interface OrderEvents {
    /**
     * GET PEgar lista de pedidos
     */
    void getOrdersFinished(List<Order> orders);
    void getOrdersFailed(WebServiceResponse webServiceResponse);

    /**
     * GET Pegar lista de pedidos por ID
     */
    void getOrderByIdFinished(Order order);
    void getOrderByIdFailed(WebServiceResponse webServiceResponse);
}
