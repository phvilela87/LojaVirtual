package dm114.br.inatel.pvilela.lojavirtual.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.models.Order;

/**
 * Created by pedro on 15/06/16.
 */
public class OrderAdapter extends BaseAdapter {

    private final Activity activity;
    List<Order> orders;

    public  OrderAdapter(Activity activity, List<Order> orders) {
        this.activity = activity;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orders.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(
                R.layout.fragment_order_list_item, null);

        Order order = orders.get(position);

        TextView listItem = (TextView) view.
                findViewById(R.id.list_item);
        listItem.setText(Integer.toString(position + 1));

        TextView orderItemId = (TextView) view.
                findViewById(R.id.order_item_id);
        orderItemId.setText(Integer.toString(order.getId()));

        TextView orderUsername = (TextView) view.
                findViewById(R.id.order_username);
        orderUsername.setText(order.getUserName());

        TextView orderShippingCost = (TextView) view.
                findViewById(R.id.order_shipping_cost);
        orderShippingCost.setText(String.valueOf(order.getPrecoFrete()));

        return view;
    }
}
