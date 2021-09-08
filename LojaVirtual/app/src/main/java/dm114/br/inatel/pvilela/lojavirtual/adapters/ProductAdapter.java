package dm114.br.inatel.pvilela.lojavirtual.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.models.Product;


public class ProductAdapter extends BaseAdapter {

    private final Activity activity;
    private List<Product> products;

    public ProductAdapter(Activity activity, List<Product> products) {
        this.activity = activity;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }
    @Override
    public Object getItem(int position) {
        return products.get(position);
    }
    @Override
    public long getItemId(int position) {
        return products.get(position).getId();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(
                R.layout.fragment_product_list_item, null);
        Product product = products.get(position);

        TextView productListItemId = (TextView) view.
                findViewById(R.id.productListItemId);
        productListItemId.setText(Integer.toString(product.getId()));

        TextView productListItemName = (TextView) view.
                findViewById(R.id.productListItemName);
        productListItemName.setText(product.getNome());

        TextView productListItemDescription = (TextView) view.
                findViewById(R.id.productListItemDescription);
        productListItemDescription.setText(product.getDescricao());

        TextView productListItemCode = (TextView) view.
                findViewById(R.id.productListItemCode);
        productListItemCode.setText(product.getCodigo());

        TextView productListItemPrice = (TextView) view.
                findViewById(R.id.productListItemPrice);
        productListItemPrice.setText(String.valueOf(product.getPreco()));
        return view;
    }

}
