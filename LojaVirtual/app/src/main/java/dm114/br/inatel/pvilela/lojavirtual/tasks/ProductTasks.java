package dm114.br.inatel.pvilela.lojavirtual.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.models.Product;
import dm114.br.inatel.pvilela.lojavirtual.util.WSUtil;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceSalesProviderClient;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 14/06/16.
 */
public class ProductTasks {

    private static final String GET_PRODUCTS = "/api/Products";
    private static final String GET_PRODUCT_BY_ID = "/api/Products/";
    private static final String POST_PRODUCT = "/api/Products";

    private Context context;
    private ProductEvents productEvent;
    private String baseAddress;

    public ProductTasks(Context context, ProductEvents productEvent) {
        this.context = context;
        this.productEvent = productEvent;
        baseAddress = WSUtil.getSalesProviderHostAddress(context);
    }

    /**
     * GET Pegar lista de produtos no provedor de vendas
     */
    public void getProducts() {
        new AsyncTask<Void, Void, WebServiceResponse>() {

            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                return WebServiceSalesProviderClient.get(context,
                        baseAddress + GET_PRODUCTS);
            }

            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {

                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        // deserializa a lista de objetos
                        List<Product> products = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                new TypeToken<List<Product>>() {
                                }.getType());
                        productEvent.getProductsFinished(products);
                    } catch (Exception e) {
                        productEvent.getProductsFailed(webServiceResponse);
                    }
                } else {
                    productEvent.getProductsFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }

    /**
     * POST Cadastrar produto no provedor de vendas
     */
    public void postProduct(final Product product) {
        new AsyncTask<Void, Void, WebServiceResponse>() {

            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                Gson gson = new Gson();
                String jsonProduct = gson.toJson(product);

                return WebServiceSalesProviderClient.post(context,
                        baseAddress + POST_PRODUCT, jsonProduct);
            }

            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {

                if (webServiceResponse.getResponseCode() == 201) {
                    try {
                        productEvent.postProductFinished();
                    } catch (Exception e) {
                        productEvent.postProductFailed(webServiceResponse);
                    }
                } else {
                    productEvent.postProductFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }

    /**
     * GET
     */
    public void getProductById(final String productId) {
        new AsyncTask<Void, Void, WebServiceResponse>() {

            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                String host = baseAddress + GET_PRODUCT_BY_ID + productId;
                return WebServiceSalesProviderClient.get(context, host);
            }

            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {

                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        Product product = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                new TypeToken<Product>() {
                                }.getType());
                        productEvent.getProductByIdFinished(product);
                    } catch (Exception e) {
                        productEvent.getProductByIdFailed(webServiceResponse);
                    }
                } else {
                    productEvent.getProductByIdFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }
}


