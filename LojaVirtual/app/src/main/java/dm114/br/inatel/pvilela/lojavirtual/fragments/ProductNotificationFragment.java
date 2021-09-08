package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.models.Product;

/**
 * Created by pedro on 23/06/16.
 */
public class ProductNotificationFragment extends Fragment {

    private TextView productNotificationId;
    private TextView productNotificationName;
    private TextView productNotificationDescription;
    private TextView productNotificationCode;
    private TextView productNotificationPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_product_notification,
                container, false);
        getActivity().setTitle("Produto");

        Bundle bundle = this.getArguments();
        Product product = (Product) bundle.getSerializable("product");

        productNotificationId = (TextView) rootView.findViewById(R.id.product_notification_id);
        productNotificationName = (TextView) rootView.findViewById(R.id.product_notification_name);
        productNotificationDescription = (TextView) rootView.findViewById(R.id.product_notification_description);
        productNotificationCode = (TextView) rootView.findViewById(R.id.product_notification_code);
        productNotificationPrice = (TextView) rootView.findViewById(R.id.product_notification_price);

        if(product != null) {
            productNotificationId.setText(String.valueOf(product.getId()));
            productNotificationName.setText(String.valueOf(product.getNome()));
            productNotificationDescription.setText(String.valueOf(product.getDescricao()));
            productNotificationCode.setText(String.valueOf(product.getCodigo()));
            productNotificationPrice.setText(String.valueOf(product.getPreco()));
        }

        return rootView;
    }
}
