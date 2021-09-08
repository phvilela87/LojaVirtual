package dm114.br.inatel.pvilela.lojavirtual.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dm114.br.inatel.pvilela.lojavirtual.R;

/**
 * Created by pedro on 21/06/16.
 */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,
                container, false);
        getActivity().setTitle("Home");

        return rootView;

    }
}
