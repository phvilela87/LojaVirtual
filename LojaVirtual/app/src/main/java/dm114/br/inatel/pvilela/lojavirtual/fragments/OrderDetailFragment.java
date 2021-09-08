package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.adapters.OrderDetailAdapter;
import dm114.br.inatel.pvilela.lojavirtual.models.Order;

/**
 * Created by pedro on 15/06/16.
 */
public class OrderDetailFragment extends Fragment {

    private ListView listViewOrderDetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_order_detail,
                container, false);
        getActivity().setTitle("Detalhe do Pedido");

        listViewOrderDetail = (ListView) rootView.findViewById(R.id.order_detail_list);

        Bundle bundle = this.getArguments();
        Order order = (Order) bundle.getSerializable("order");

        if (order != null) {
            OrderDetailAdapter orderItemAdapter = new OrderDetailAdapter(
                    getActivity(), order.getOrderItems());
            listViewOrderDetail.setAdapter(orderItemAdapter);
        } else {
            Toast.makeText(getActivity(), "Falha na consulta da lista de pedidos por id", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

}

