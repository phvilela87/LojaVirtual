package dm114.br.inatel.pvilela.lojavirtual.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.models.ProductInterest;

/**
 * Created by pedro on 21/06/16.
 */
public class ProductInterestAdapter extends BaseAdapter {

    private final Activity activity;
    private List<ProductInterest> productsInterest;

    public ProductInterestAdapter(Activity activity, List<ProductInterest> productsInterest) {
        this.activity = activity;
        this.productsInterest = productsInterest;
    }

    @Override
    public int getCount() {
        return productsInterest.size();
    }
    @Override
    public Object getItem(int position) {
        return productsInterest.get(position);
    }
    @Override
    public long getItemId(int position) {
        return productsInterest.get(position).getId();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(
                R.layout.fragment_product_interest_list_item, null);

        ProductInterest productInterest = productsInterest.get(position);

        TextView productInterestListItemId = (TextView) view.
                findViewById(R.id.product_interest_list_item_id);
        productInterestListItemId.setText(Long.toString(productInterest.getId()));

        TextView productInterestListItemClientEmail = (TextView) view.
                findViewById(R.id.product_interest_list_item_client_email);
        productInterestListItemClientEmail.setText(productInterest.getClientEmail());

        TextView productInterestListItemProductID = (TextView) view.
                findViewById(R.id.product_interest_list_item_product_ID);
        productInterestListItemProductID.setText(productInterest.getProductID());

        TextView productInterestListItemPrice = (TextView) view.
                findViewById(R.id.product_interest_list_item_price);
        productInterestListItemPrice.setText(String.valueOf(productInterest.getPrice()));
        return view;
    }

}
