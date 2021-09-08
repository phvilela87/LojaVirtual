package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.adapters.OrderAdapter;
import dm114.br.inatel.pvilela.lojavirtual.adapters.OrderDetailAdapter;
import dm114.br.inatel.pvilela.lojavirtual.models.Order;
import dm114.br.inatel.pvilela.lojavirtual.models.OrderInfo;
import dm114.br.inatel.pvilela.lojavirtual.tasks.OrderEvents;
import dm114.br.inatel.pvilela.lojavirtual.tasks.OrderTasks;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 23/06/16.
 */
public class OrderInfoNotificationFragment extends Fragment implements OrderEvents {

    private TextView orderNotificationOrderID;
    private TextView orderNotificationInfoReason;
    private TextView orderNotificationUserEmail;
    private TextView orderNotificationNewOrderStatus;
    private TextView orderNotificationUsername;
    private TextView orderNotificationPrecoFrete;

    private ListView listViewOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_order_notification,
                container, false);
        getActivity().setTitle("Informações do Pedido");

        Bundle bundle = this.getArguments();
        OrderInfo orderInfo = (OrderInfo) bundle.getSerializable("orderInfo");

        orderNotificationOrderID = (TextView) rootView.findViewById(R.id.order_notification_order_ID);
        orderNotificationInfoReason = (TextView) rootView.findViewById(R.id.order_notification_info_reason);
        orderNotificationUserEmail = (TextView) rootView.findViewById(R.id.order_notification_user_email);
        orderNotificationNewOrderStatus = (TextView) rootView.findViewById(R.id.order_notification_new_order_status);

        listViewOrders = (ListView) rootView.
                findViewById(R.id.order_notification_list);

        if(orderInfo != null) {
            orderNotificationOrderID.setText(String.valueOf(orderInfo.getOrderID()));
            orderNotificationInfoReason.setText(String.valueOf(orderInfo.getInfoReason()));
            orderNotificationUserEmail.setText(String.valueOf(orderInfo.getUserEmail()));
            orderNotificationNewOrderStatus.setText(String.valueOf(orderInfo.getNewOrderStatus()));

            orderNotificationUsername = (TextView) rootView.findViewById(R.id.order_notification_username);
            orderNotificationPrecoFrete = (TextView) rootView.findViewById(R.id.order_notification_preco_frete);

            OrderTasks orderTasks = new OrderTasks(getActivity(), this);
            orderTasks.getOrderById(String.valueOf(orderInfo.getOrderID()));

        }

        return rootView;
    }

    @Override
    public void getOrdersFinished(List<Order> orders) {

    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void getOrderByIdFinished(Order order) {
        orderNotificationUsername.setText(String.valueOf(order.getUserName()));
        orderNotificationPrecoFrete.setText(String.valueOf(order.getPrecoFrete()));

        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(
                getActivity(), order.getOrderItems());
        listViewOrders.setAdapter(orderDetailAdapter);
    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(),
                "Este produto não existe no provedor de vendas",
                Toast.LENGTH_LONG).show();
        orderNotificationUsername.setText("Não existe");
        orderNotificationPrecoFrete.setText("Não existe");
    }
}
