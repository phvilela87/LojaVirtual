package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import dm114.br.inatel.pvilela.lojavirtual.MainActivity;
import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.models.Product;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductEvents;
import dm114.br.inatel.pvilela.lojavirtual.tasks.ProductTasks;
import dm114.br.inatel.pvilela.lojavirtual.webservice.CheckNetworkConnection;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

/**
 * Created by pedro on 15/06/16.
 */
public class RegisterProductFragment extends Fragment implements ProductEvents {

    private EditText registerProductName;
    private EditText registerProductDescription;
    private EditText registerProductCode;
    private EditText registerProductPrice;
    private Button buttonRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_product,
                container, false);
        getActivity().setTitle("Cadastrar Produto");

        registerProductName = (EditText) rootView.findViewById(R.id.register_product_name);
        registerProductDescription = (EditText) rootView.findViewById(R.id.register_product_description);
        registerProductCode = (EditText) rootView.findViewById(R.id.register_product_code);
        registerProductPrice = (EditText) rootView.findViewById(R.id.register_product_price);

        buttonRegister = (Button) rootView.findViewById(R.id.register_product_register_btn);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerProduct();
            }
        });

        return rootView;

    }

    private void registerProduct(){

        registerProductName.setError(null);
        registerProductDescription.setError(null);
        registerProductCode.setError(null);
        registerProductPrice.setError(null);

        String name = registerProductName.getText().toString();
        String description = registerProductDescription.getText().toString();
        String code = registerProductCode.getText().toString();
        String price = registerProductPrice.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(name.isEmpty()) {
            registerProductName.setError(getString(R.string.error_field_required));
            focusView = registerProductName;
            cancel = true;
        }

        if(description.isEmpty()) {
            registerProductDescription.setError(getString(R.string.error_field_required));
            focusView = registerProductDescription;
            cancel = true;
        }

        if(code.isEmpty()) {
            registerProductCode.setError(getString(R.string.error_field_required));
            focusView = registerProductCode;
            cancel = true;
        }

        if(price.isEmpty()) {
            registerProductPrice.setError(getString(R.string.error_field_required));
            focusView = registerProductPrice;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (CheckNetworkConnection.isNetworkConnected(getActivity())) {

                buttonRegister.setEnabled(false);
                ProductTasks productsTasks = new ProductTasks(getActivity(), this);

                Product product = new Product();
                product.setNome(name);
                product.setDescricao(description);
                product.setCodigo(code);
                product.setPreco(Double.parseDouble(price));
                productsTasks.postProduct(product);
            }
        }
    }

    private void updateProductList() {
        try {
            int backStackEntryCount;
            backStackEntryCount = getFragmentManager().getBackStackEntryCount();
            for (int j = 0; j < backStackEntryCount; j++) {
                getFragmentManager().popBackStack();
            }

            Class fragmentClass = ProductFragment.class;
            Fragment fragment = (Fragment) fragmentClass.newInstance();

            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        } catch(Exception e){
            Toast.makeText(getActivity(),
                    "Falha ao atualizar lista de produtos",
                    Toast.LENGTH_SHORT).show();
            buttonRegister.setEnabled(true);
        }
    }

    @Override
    public void getProductsFinished(List<Product> products) {

    }

    @Override
    public void getProductsFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void postProductFinished() {
        Toast.makeText(getActivity(),
                "Produto cadastrado com sucesso!",
                Toast.LENGTH_SHORT).show();
        updateProductList();
    }

    @Override
    public void postProductFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(),
                "Falha ao cadastrar produto",
                Toast.LENGTH_SHORT).show();
        buttonRegister.setEnabled(true);
    }

    @Override
    public void getProductByIdFinished(Product product) {

    }

    @Override
    public void getProductByIdFailed(WebServiceResponse webServiceResponse) {

    }
}
