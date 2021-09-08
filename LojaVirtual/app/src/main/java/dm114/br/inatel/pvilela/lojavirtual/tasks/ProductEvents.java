package dm114.br.inatel.pvilela.lojavirtual.tasks;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.models.Product;
import dm114.br.inatel.pvilela.lojavirtual.models.ProductInterest;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 14/06/16.
 */
public interface ProductEvents {
    /**
     * GET Pegar lista de produtos
     */
    void getProductsFinished(List<Product> products);
    void getProductsFailed(WebServiceResponse webServiceResponse);

    /**
     * POST Inserir produto
     */
    void postProductFinished();
    void postProductFailed(WebServiceResponse webServiceResponse);

    /**
     * GET Pegar lista de produtos por ID
     */
    void getProductByIdFinished(Product product);
    void getProductByIdFailed(WebServiceResponse webServiceResponse);
}
