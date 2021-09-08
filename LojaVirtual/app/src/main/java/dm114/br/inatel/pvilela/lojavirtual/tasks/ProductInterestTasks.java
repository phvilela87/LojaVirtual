package dm114.br.inatel.pvilela.lojavirtual.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.models.ProductInterest;
import dm114.br.inatel.pvilela.lojavirtual.util.WSUtil;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceSalesProviderClient;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceUserMessageProviderClient;

/**
 * Created by pedro on 21/06/16.
 */
public class ProductInterestTasks {
    private static final String GET_PRODUCTS = "/api/product/byemail";
    private static final String DELETE_PRODUCT_INTEREST = "/api/product/byemail/";
    private static final String POST_PRODUCT_INTEREST = "/api/product";

    private ProductInterestEvents productInterestEvents;
    private Context context;
    private String baseAddress;

    public ProductInterestTasks(Context context, ProductInterestEvents productInterestEvents) {
        this.context = context;
        this.productInterestEvents = productInterestEvents;
        baseAddress = WSUtil.getMessageProviderHostAddress(context);
    }

    /**
     * GET Pegar lista de produtos de interesse
     */
    public void getProductsInterest() {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                return WebServiceUserMessageProviderClient.get(context,
                        baseAddress + GET_PRODUCTS);
            }
            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        List<ProductInterest> productsInterest = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                new TypeToken<List<ProductInterest>>() {
                                }.getType());
                        productInterestEvents.getProductsInterestFinished(productsInterest);
                    } catch (Exception e) {
                        productInterestEvents.getProductsInterestFailed(webServiceResponse);
                    }
                } else {
                    productInterestEvents.getProductsInterestFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }

    public void deleteProductsInterest(final String productID) {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                String host = baseAddress + DELETE_PRODUCT_INTEREST + productID;
                return WebServiceUserMessageProviderClient.delete(context, host);
            }
            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    try {
                        productInterestEvents.deleteProductsInterestFinished();
                    } catch (Exception e) {
                        productInterestEvents.deleteProductsInterestFailed(webServiceResponse);
                    }
                } else {
                    productInterestEvents.deleteProductsInterestFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }

    /**
     * PUT Atualiza o preço de um produto
     * O método é chamado de PUT, porém, como não existe o método PUT, este método irá realizar
     * um DELETE e depois um POST para atualizar o preço do produto
     */
    public void putProductsInterest(final ProductInterest productInterest) {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {

                String productID = String.valueOf(productInterest.getProductID());
                String host = baseAddress + DELETE_PRODUCT_INTEREST + productID;

                WebServiceResponse webServiceResponse =
                        WebServiceUserMessageProviderClient.delete(context, host);

                if(webServiceResponse.getResponseCode() != 200) {
                    return webServiceResponse;
                }

                Gson gson = new Gson();
                String jsonProductInterest = gson.toJson(productInterest);
                host = baseAddress + POST_PRODUCT_INTEREST;

                return WebServiceUserMessageProviderClient.post(context, host, jsonProductInterest);

            }

            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    try {
                        productInterestEvents.putProductsInterestFinished();
                    } catch (Exception e) {
                        productInterestEvents.putProductsInterestFailed(webServiceResponse);
                    }
                } else {
                    productInterestEvents.putProductsInterestFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }

    /**
     * POST Inserir produto de interesse no provedor de mensagens
     */
    public void postProductsInterest(final ProductInterest productInterest) {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {

                Gson gson = new Gson();
                String jsonProductInterest = gson.toJson(productInterest);
                String host = baseAddress + POST_PRODUCT_INTEREST;

                return WebServiceUserMessageProviderClient.post(context, host, jsonProductInterest);

            }

            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    try {
                        productInterestEvents.postProductsInterestFinished();
                    } catch (Exception e) {
                        productInterestEvents.postProductsInterestFailed(webServiceResponse);
                    }
                } else {
                    productInterestEvents.postProductsInterestFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }
}
