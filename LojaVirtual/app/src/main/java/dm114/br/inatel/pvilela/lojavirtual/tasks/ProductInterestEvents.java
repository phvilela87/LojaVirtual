package dm114.br.inatel.pvilela.lojavirtual.tasks;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.models.Product;
import dm114.br.inatel.pvilela.lojavirtual.models.ProductInterest;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 21/06/16.
 */
public interface ProductInterestEvents {

    /**
     * GET Lista produtos de interesse
     */
    void getProductsInterestFinished(List<ProductInterest> productsInterest);
    void getProductsInterestFailed(WebServiceResponse webServiceResponse);


    /**
     * DELETE Remove produto de interesse
     */
    void deleteProductsInterestFinished();
    void deleteProductsInterestFailed(WebServiceResponse webServiceResponse);


    /**
     * POST Insere produto de interesse
     */
    void postProductsInterestFinished();
    void postProductsInterestFailed(WebServiceResponse webServiceResponse);


    /**
     * PUT Atualiza produto de interesse
     */
    void putProductsInterestFinished();
    void putProductsInterestFailed(WebServiceResponse webServiceResponse);
}
