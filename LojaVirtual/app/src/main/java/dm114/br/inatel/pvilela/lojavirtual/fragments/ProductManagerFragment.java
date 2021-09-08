package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by pedro on 21/06/16.
 */
public class ProductManagerFragment extends Fragment {

    private Button buttonRegisterProductInterest;
    private Button buttonProductInterestList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_manager,
                container, false);
        getActivity().setTitle("Gerenciar Produtos");

        buttonRegisterProductInterest = (Button) rootView.findViewById(R.id.button_register_product_interest);
        buttonRegisterProductInterest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerProductInterest();
            }
        });

        buttonProductInterestList = (Button) rootView.findViewById(R.id.button_product_interest_list);
        buttonProductInterestList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                productInterestList();
            }
        });

        return rootView;

    }

    private void registerProductInterest() {
        try {
            int backStackEntryCount;
            backStackEntryCount = getFragmentManager().getBackStackEntryCount();
            for (int j = 0; j < backStackEntryCount; j++) {
                getFragmentManager().popBackStack();
            }

            Class fragmentClass = RegisterProductInterestFragment.class;
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

    private void productInterestList() {
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

}