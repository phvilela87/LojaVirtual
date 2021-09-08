package dm114.br.inatel.pvilela.lojavirtual.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.models.Order;
import dm114.br.inatel.pvilela.lojavirtual.util.WSUtil;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceSalesProviderClient;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 15/06/16.
 */
public class OrderTasks {
    private static final String GET_ORDERS = "/api/orders";
    private static final String GET_ORDER_BY_ID = "/api/orders/";
    private OrderEvents orderEvents;
    private Context context;
    private String baseAddress;

    public OrderTasks(Context context, OrderEvents orderEvents) {

        this.context = context;
        this.orderEvents = orderEvents;
        baseAddress = WSUtil.getSalesProviderHostAddress(context);
    }

    public void getOrders() {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                return WebServiceSalesProviderClient.get(context,
                        baseAddress + GET_ORDERS);
            }
            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        List<Order> orders = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                new TypeToken<List<Order>>() {
                                }.getType());
                        orderEvents.getOrdersFinished(orders);
                    } catch (Exception e) {
                        orderEvents.getOrdersFailed(webServiceResponse);
                    }
                } else {
                    orderEvents.getOrdersFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }

    public void getOrderById(final String orderId) {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                String host = baseAddress + GET_ORDER_BY_ID + orderId;
                return WebServiceSalesProviderClient.get(context, host);
            }
            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        Order order = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                new TypeToken<Order>() {
                                }.getType());
                        orderEvents.getOrderByIdFinished(order);
                    } catch (Exception e) {
                        orderEvents.getOrderByIdFailed(webServiceResponse);
                    }
                } else {
                    orderEvents.getOrderByIdFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }
}
