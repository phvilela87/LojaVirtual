package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.adapters.OrderAdapter;
import dm114.br.inatel.pvilela.lojavirtual.models.Order;
import dm114.br.inatel.pvilela.lojavirtual.tasks.OrderEvents;
import dm114.br.inatel.pvilela.lojavirtual.tasks.OrderTasks;
import dm114.br.inatel.pvilela.lojavirtual.webservice.CheckNetworkConnection;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 15/06/16.
 */
public class OrderFragment extends Fragment implements OrderEvents {

    private ListView listViewOrders;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_orders_list,
                container, false);
        getActivity().setTitle("Pedidos");

        listViewOrders = (ListView) rootView.
                findViewById(R.id.orders_list);

        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            OrderTasks productsTasks = new OrderTasks(getActivity(), this);
            productsTasks.getOrders();
        }

        listViewOrders.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        Order orderSelected = (Order)
                                listViewOrders.getItemAtPosition(position);
                        startOrderDetail(orderSelected);
                    }
                });

        return rootView;
    }

    private void startOrderDetail(Order orderSelected) {
        try {
            int backStackEntryCount;
            backStackEntryCount = getFragmentManager().getBackStackEntryCount();
            for (int j = 0; j < backStackEntryCount; j++) {
                getFragmentManager().popBackStack();
            }

            Class fragmentClass = OrderDetailFragment.class;
            Fragment fragment = (Fragment) fragmentClass.newInstance();

            Bundle args = new Bundle();
            args.putSerializable("order", orderSelected);
            fragment.setArguments(args);

            if (fragment != null) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        } catch(Exception e){
            Toast.makeText(getActivity(),
                    "Falha ao tentar abrir detalhe do pedido",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getOrdersFinished(List<Order> orders) {
        OrderAdapter orderAdapter = new OrderAdapter(
                getActivity(), orders);
        listViewOrders.setAdapter(orderAdapter);

    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(), "Falha na consulta da lista de pedidos" +
                webServiceResponse.getResultMessage() + " - Código do erro: " +
                webServiceResponse.getResponseCode(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void getOrderByIdFinished(Order order) {

    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {

    }
}
