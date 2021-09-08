package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
 * Created by pedro on 22/06/16.
 */
public class EditProductInterestFragment extends Fragment implements ProductInterestEvents {

    private TextView productInterestListItemId;
    private TextView productInterestListItemClientEmail;
    private TextView productInterestListItemProductID;
    private EditText productInterestListItemPrice;
    private Button buttonProductInterestDeleteItem;
    private Button buttonProductInterestUpdateItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_product_interest_edit_list_item,
                container, false);
        getActivity().setTitle("Editar Produto");

        Bundle bundle = this.getArguments();
        final ProductInterest productInterest = (ProductInterest) bundle.getSerializable("productInterestSelected");

        productInterestListItemId = (TextView) rootView.findViewById(R.id.product_interest_list_item_id);
        productInterestListItemClientEmail = (TextView) rootView.findViewById(R.id.product_interest_list_item_client_email);
        productInterestListItemProductID = (TextView) rootView.findViewById(R.id.product_interest_list_item_product_ID);
        productInterestListItemPrice = (EditText) rootView.findViewById(R.id.product_interest_list_item_price);

        if(productInterest != null) {
            productInterestListItemId.setText(String.valueOf(productInterest.getId()));
            productInterestListItemClientEmail.setText(productInterest.getClientEmail());
            productInterestListItemProductID.setText(String.valueOf(productInterest.getProductID()));
            productInterestListItemPrice.setText(String.valueOf(productInterest.getPrice()));
        }

        buttonProductInterestDeleteItem = (Button) rootView.findViewById(R.id.button_product_interest_delete_item);
        buttonProductInterestDeleteItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonProductInterestDeleteItem.setEnabled(false);
                deleteProductInterest(String.valueOf(productInterest.getProductID()));
            }
        });

        buttonProductInterestUpdateItem = (Button) rootView.findViewById(R.id.button_product_interest_update_item);
        buttonProductInterestUpdateItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonProductInterestUpdateItem.setEnabled(false);
                productInterest.setPrice(Float.parseFloat(productInterestListItemPrice.getText().toString()));
                updateProductInterest(productInterest);
            }
        });

        return rootView;
    }

    private void deleteProductInterest(String productID) {
        ProductInterestTasks productInterestTasks = new ProductInterestTasks(getActivity(), this);
        productInterestTasks.deleteProductsInterest(productID);
    }

    private void updateProductInterest(ProductInterest productInterest) {
        ProductInterestTasks productInterestTasks = new ProductInterestTasks(getActivity(), this);
        productInterestTasks.putProductsInterest(productInterest);
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
        Toast.makeText(getActivity(),
                "Produto excluido com sucesso!",
                Toast.LENGTH_SHORT).show();
        updateProductInterestList();
    }

    @Override
    public void deleteProductsInterestFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(),
                "Falha ao excluir produto",
                Toast.LENGTH_SHORT).show();
        buttonProductInterestDeleteItem.setEnabled(true);
    }

    @Override
    public void postProductsInterestFinished() {

    }

    @Override
    public void postProductsInterestFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void putProductsInterestFinished() {
        Toast.makeText(getActivity(),
                "Produto atualizado com sucesso!",
                Toast.LENGTH_SHORT).show();
        updateProductInterestList();
    }

    @Override
    public void putProductsInterestFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(),
                "Falha ao atualizar produto",
                Toast.LENGTH_SHORT).show();
        buttonProductInterestUpdateItem.setEnabled(true);
    }
}
