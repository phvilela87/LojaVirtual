package dm114.br.inatel.pvilela.lojavirtual.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.models.Order;
import dm114.br.inatel.pvilela.lojavirtual.models.OrderItem;

/**
 * Created by pedro on 15/06/16.
 */
public class OrderDetailAdapter extends BaseAdapter {

    private final Activity activity;
    List<OrderItem> orderItems;

    public OrderDetailAdapter(Activity activity, List<OrderItem> orderItems) {
        this.activity = activity;
        this.orderItems = orderItems;
    }

    @Override
    public int getCount() {
        return orderItems.size();
    }

    @Override
    public Object getItem(int position) {
        return orderItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(
                R.layout.fragment_order_detail_list, null);

        try {
            OrderItem orderItem = orderItems.get(position);

            TextView orderDetailListItemNumber = (TextView) view.
                    findViewById(R.id.order_detail_list_item_number);
            orderDetailListItemNumber.setText(Integer.toString(position + 1));

            TextView orderDetailListItemProductName = (TextView) view.
                    findViewById(R.id.order_detail_list_item_product_name);
            orderDetailListItemProductName.setText(orderItem.getProduct().getNome());

            TextView orderDetailListItemProductDescription = (TextView) view.
                    findViewById(R.id.order_detail_list_item_product_description);
            orderDetailListItemProductDescription.setText(orderItem.getProduct().getDescricao());

            TextView orderDetailListItemProductCode = (TextView) view.
                    findViewById(R.id.order_detail_list_item_product_code);
            orderDetailListItemProductCode.setText(orderItem.getProduct().getCodigo());

            TextView orderDetailListItemProductPrice = (TextView) view.
                    findViewById(R.id.order_detail_list_item_product_price);
            orderDetailListItemProductPrice.setText(Double.toString(orderItem.getProduct().getPreco()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return view;
    }
}
