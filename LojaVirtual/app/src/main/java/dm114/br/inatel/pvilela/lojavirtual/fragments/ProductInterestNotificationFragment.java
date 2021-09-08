package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.models.Product;
import dm114.br.inatel.pvilela.lojavirtual.models.ProductInterest;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductEvents;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductInterestTasks;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductTasks;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 23/06/16.
 */
public class ProductInterestNotificationFragment extends Fragment implements ProductEvents {

    private TextView productInterestNotificationId;
    private TextView productInterestNotificationClientEmail;
    private TextView productInterestNotificationProductID;
    private TextView productInterestNotificationPrice;

    private TextView productNotificationId;
    private TextView productNotificationName;
    private TextView productNotificationDescription;
    private TextView productNotificationCode;
    private TextView productNotificationPrice;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_product_interest_notification,
                container, false);
        getActivity().setTitle("Produto de Interesse");

        Bundle bundle = this.getArguments();
        ProductInterest productInterest = (ProductInterest) bundle.getSerializable("productInterestNotification");

        productInterestNotificationId = (TextView) rootView.findViewById(R.id.product_interest_notification_id);
        productInterestNotificationClientEmail = (TextView) rootView.findViewById(R.id.product_interest_notification_client_email);
        productInterestNotificationProductID = (TextView) rootView.findViewById(R.id.product_interest_notification_product_ID);
        productInterestNotificationPrice = (TextView) rootView.findViewById(R.id.product_interest_notification_price);

        if(productInterest != null) {
            productInterestNotificationId.setText(String.valueOf(productInterest.getId()));
            productInterestNotificationClientEmail.setText(String.valueOf(productInterest.getClientEmail()));
            productInterestNotificationProductID.setText(String.valueOf(productInterest.getProductID()));
            productInterestNotificationPrice.setText(String.valueOf(productInterest.getPrice()));

            productNotificationId = (TextView) rootView.findViewById(R.id.product_notification_id);
            productNotificationName = (TextView) rootView.findViewById(R.id.product_notification_name);
            productNotificationDescription = (TextView) rootView.findViewById(R.id.product_notification_description);
            productNotificationCode = (TextView) rootView.findViewById(R.id.product_notification_code);
            productNotificationPrice = (TextView) rootView.findViewById(R.id.product_notification_price);

            ProductTasks productTasks = new ProductTasks(getActivity(), this);
            productTasks.getProductById(String.valueOf(productInterest.getProductID()));
        }

        return rootView;
    }


    @Override
    public void getProductsFinished(List<Product> products) {

    }

    @Override
    public void getProductsFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void postProductFinished() {

    }

    @Override
    public void postProductFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void getProductByIdFinished(Product product) {
        productNotificationId.setText(String.valueOf(product.getId()));
        productNotificationName.setText(String.valueOf(product.getNome()));
        productNotificationDescription.setText(String.valueOf(product.getDescricao()));
        productNotificationCode.setText(String.valueOf(product.getCodigo()));
        productNotificationPrice.setText(String.valueOf(product.getPreco()));
    }

    @Override
    public void getProductByIdFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(),
                "Este produto não existe no provedor de vendas",
                Toast.LENGTH_LONG).show();

        productNotificationId.setText("Não existe");
        productNotificationName.setText("Não existe");
        productNotificationDescription.setText("Não existe");
        productNotificationCode.setText("Não existe");
        productNotificationPrice.setText("Não existe");
    }
}
