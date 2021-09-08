package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.adapters.ProductAdapter;
import dm114.br.inatel.pvilela.lojavirtual.models.Product;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductEvents;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductTasks;
import dm114.br.inatel.pvilela.lojavirtual.webservice.CheckNetworkConnection;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;


/**
 * Created by pedro on 14/06/16.
 */
public class ProductFragment extends Fragment implements ProductEvents {


    private ListView listViewProducts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products_list,
                container, false);
        getActivity().setTitle("Produtos");

        listViewProducts = (ListView) rootView.
                findViewById(R.id.products_list);

        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            ProductTasks productsTasks = new ProductTasks(getActivity(), this);
            productsTasks.getProducts();
        }

        return rootView;

    }

    @Override
    public void getProductsFinished(List<Product> products) {
        ProductAdapter productAdapter = new ProductAdapter(
                getActivity(), products);
        listViewProducts.setAdapter(productAdapter);
    }

    @Override
    public void getProductsFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(), "Falha na consulta da lista de produtos" +
                webServiceResponse.getResultMessage() + " - Código do erro: " +
                webServiceResponse.getResponseCode(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void postProductFinished() {

    }

    @Override
    public void postProductFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void getProductByIdFinished(Product product) {

    }

    @Override
    public void getProductByIdFailed(WebServiceResponse webServiceResponse) {

    }

}
