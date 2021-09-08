package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.models.ProductInterest;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductInterestEvents;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductInterestTasks;
import dm114.br.inatel.pvilela.lojavirtual.webservice.CheckNetworkConnection;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 22/06/16.
 */
public class RegisterProductInterestFragment extends Fragment implements ProductInterestEvents {

    private EditText productInterestAddProductProductID;
    private EditText productInterestAddProductPrice;
    private Button buttonProductInterestAddProductInsertProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_product_interest_add_product,
                container, false);
        getActivity().setTitle("Inserir Produto");

        productInterestAddProductProductID = (EditText) rootView.findViewById(R.id.product_interest_add_product_product_ID);
        productInterestAddProductPrice = (EditText) rootView.findViewById(R.id.product_interest_add_product_price);


        buttonProductInterestAddProductInsertProduct = (Button) rootView.findViewById(R.id.button_product_interest_add_product_insert_product);
        buttonProductInterestAddProductInsertProduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertProductInterest();
            }
        });

        return rootView;
    }

    private void insertProductInterest() {

        SharedPreferences sharedSettings =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(sharedSettings.contains(
                getActivity().getString(R.string.pref_user_email))) {
            String email = sharedSettings.getString(
                    getActivity().getString(R.string.pref_user_email), "");

            if (email.isEmpty()) {
                Toast.makeText(getActivity(),
                        "Falha ao obter o client email",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String productID = productInterestAddProductProductID.getText().toString();
            String productPrice = productInterestAddProductPrice.getText().toString();

            boolean cancel = false;
            View focusView = null;

            if (productID.isEmpty()) {
                productInterestAddProductProductID.setError(getString(R.string.error_field_required));
                focusView = productInterestAddProductProductID;
                cancel = true;
            }

            if (productPrice.isEmpty()) {
                productInterestAddProductPrice.setError(getString(R.string.error_field_required));
                focusView = productInterestAddProductPrice;
                cancel = true;
            }

            if (cancel) {
                focusView.requestFocus();
            } else {
                if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
                    ProductInterestTasks productInterestTasks = new ProductInterestTasks(getActivity(), this);

                    ProductInterest productInterest = new ProductInterest();
                    productInterest.setProductID(productInterestAddProductProductID.getText().toString());
                    productInterest.setPrice(Float.parseFloat(productInterestAddProductPrice.getText().toString()));
                    productInterest.setClientEmail(email);
                    productInterestTasks.postProductsInterest(productInterest);
                }
            }
        } else {
            Toast.makeText(getActivity(),
                    "Falha ao inserir produto de interesse",
                    Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void updateProductInterestList() {
        try {
            int backStackEntryCount;
            backStackEntryCount = getFragmentManager().getBackStackEntryCount();
            for (int j = 0; j < backStackEntryCount; j++) {
                getFragmentManager().popBackStack();
            }

            Class fragmentClass = ProductInterestFragment.class;
            Fragment fragment = (Fragment) fragmentClass.newInstance();

            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        } catch(Exception e){
            Toast.makeText(getActivity(),
                    "Falha ao atualizar lista de produtos",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getProductsInterestFinished(List<ProductInterest> productsInterest) {

    }

    @Override
    public void getProductsInterestFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void deleteProductsInterestFinished() {

    }

    @Override
    public void deleteProductsInterestFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void postProductsInterestFinished() {
        Toast.makeText(getActivity(),
                "Produto inserido com sucesso!",
                Toast.LENGTH_SHORT).show();
        updateProductInterestList();

    }

    @Override
    public void postProductsInterestFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(),
                "Falha ao inserir produto",
                Toast.LENGTH_SHORT).show();
        updateProductInterestList();
    }

    @Override
    public void putProductsInterestFinished() {

    }

    @Override
    public void putProductsInterestFailed(WebServiceResponse webServiceResponse) {

    }
}
