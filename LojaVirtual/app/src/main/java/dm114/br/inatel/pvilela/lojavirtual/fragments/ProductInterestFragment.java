package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.adapters.ProductInterestAdapter;
import dm114.br.inatel.pvilela.lojavirtual.models.Product;
import dm114.br.inatel.pvilela.lojavirtual.models.ProductInterest;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductInterestEvents;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductInterestTasks;
import dm114.br.inatel.pvilela.lojavirtual.webservice.CheckNetworkConnection;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 15/06/16.
 */
public class ProductInterestFragment extends Fragment implements ProductInterestEvents {

    private ListView listViewProductsInterest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_interest_list,
                container, false);
        getActivity().setTitle("Produtos de Interesse");

        listViewProductsInterest = (ListView) rootView.
                findViewById(R.id.product_interest_list);

        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            ProductInterestTasks productsInterestTasks = new ProductInterestTasks(getActivity(), this);
            productsInterestTasks.getProductsInterest();
        }

        listViewProductsInterest.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        ProductInterest productInterestSelected = (ProductInterest)
                                listViewProductsInterest.getItemAtPosition(position);
                        editProductInterest(productInterestSelected);
                    }
                });


        return rootView;

    }

    private void editProductInterest(ProductInterest productInterestSelected) {
        try {
            int backStackEntryCount;
            backStackEntryCount = getFragmentManager().getBackStackEntryCount();
            for (int j = 0; j < backStackEntryCount; j++) {
                getFragmentManager().popBackStack();
            }

            Class fragmentClass = EditProductInterestFragment.class;
            Fragment fragment = (Fragment) fragmentClass.newInstance();

            Bundle args = new Bundle();
            args.putSerializable("productInterestSelected", productInterestSelected);
            fragment.setArguments(args);

            if (fragment != null) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        } catch(Exception e){
            Toast.makeText(getActivity(),
                    "Falha ao tentar abrir edição do pedido",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getProductsInterestFinished(List<ProductInterest> productsInterest) {
        ProductInterestAdapter productInterestAdapter = new ProductInterestAdapter(
                getActivity(), productsInterest);
        listViewProductsInterest.setAdapter(productInterestAdapter);
    }

    @Override
    public void getProductsInterestFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(), "Falha na consulta da lista de produtos" +
                webServiceResponse.getResultMessage() + " - Código do erro: " +
                webServiceResponse.getResponseCode(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void deleteProductsInterestFinished() {

    }

    @Override
    public void deleteProductsInterestFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void postProductsInterestFinished() {

    }

    @Override
    public void postProductsInterestFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void putProductsInterestFinished() {

    }

    @Override
    public void putProductsInterestFailed(WebServiceResponse webServiceResponse) {

    }

}
