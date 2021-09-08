package dm114.br.inatel.pvilela.lojavirtual.fragments;



import android.app.Fragment;
import java.io.IOException;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.gcm.GCMRegister;
import dm114.br.inatel.pvilela.lojavirtual.gcm.GCMRegisterEvents;
import dm114.br.inatel.pvilela.lojavirtual.models.ProductInfo;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

public class GCMFragment extends Fragment implements GCMRegisterEvents {

    private String registrationID;
    private GCMRegister gcmRegister;
    private EditText edtSenderID;
    private TextView txtRegistrationID;
    private Button btnUnregister;
    private Button btnRegister;
    private Button btnUpdateRegId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gcm, container,
                false);
        getActivity().setTitle("GCM");
        edtSenderID = (EditText) rootView.findViewById(R.id.edtSenderID);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnUnregister = (Button) rootView.findViewById(R.id.btnUnregister);
        txtRegistrationID = (TextView) rootView.findViewById(
                R.id.txtRegistrationID);
        btnUpdateRegId = (Button) rootView.findViewById(R.id.update_regId_btn);

        if (gcmRegister == null)
            gcmRegister = new GCMRegister(getActivity(), this);
        edtSenderID.setText(gcmRegister.getSenderId());
        if (!gcmRegister.isRegistrationExpired()) {
            registrationID = gcmRegister.getCurrentRegistrationId();
            setForRegistered(registrationID);
        } else {
            setForUnregistered();
        }

        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationID = gcmRegister.getRegistrationId(
                        edtSenderID.getText().toString());
                if ((registrationID == null) ||
                        (registrationID.length() == 0)) {
                    Toast.makeText(getActivity(),
                            "Dispositivo ainda não registrado na nuvem" +
                                    "Tentando...",
                            Toast.LENGTH_SHORT).show();
                    setForUnregistered();
                }
                else {
                    Toast.makeText(getActivity(),
                            "Dispositivo já registrado na nuvem",
                            Toast.LENGTH_SHORT).show();
                    setForRegistered(registrationID);
                }
            }
        });

        btnUnregister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gcmRegister.unRegister();
            }
        });

        btnUpdateRegId.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRegId();
                btnUpdateRegId.setEnabled(false);
            }
        });

        return rootView;
    }

    private void updateRegId() {

        String regId = txtRegistrationID.getText().toString();
        if (!regId.equals("")) {
            gcmRegister.updateRegId(regId);
        }
    }


    @Override
    public void gcmRegisterFinished(String registrationID) {
        Toast.makeText(getActivity(),
                "Dispositivo registrado na nuvem com sucesso!",
                Toast.LENGTH_SHORT).show();
        setForRegistered(registrationID);
    }
    @Override
    public void gcmRegisterFailed(IOException ex) {
        Toast.makeText(getActivity(),
                "Falha ao registrar dispositivo na nuvem" +
                        ex.getMessage(), Toast.LENGTH_SHORT).show();
        setForUnregistered();
    }
    @Override
    public void gcmUnregisterFinished() {
        Toast.makeText(getActivity(),
                "Dispositivo desregistrado da nuvem",
                Toast.LENGTH_SHORT).show();
        setForUnregistered();
    }
    @Override
    public void gcmUnregisterFailed(IOException ex) {
        Toast.makeText(getActivity(),
                "Falha ao desregistrar o dispositivo na nuvem",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateRegIdFinished() {
        Toast.makeText(getActivity(),
                "RegID atualizado com sucesso!",
                Toast.LENGTH_SHORT).show();
        btnUpdateRegId.setEnabled(true);
    }

    @Override
    public void updateRedIdFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(),
                "Falha ao atualizar RegID",
                Toast.LENGTH_SHORT).show();
        btnUpdateRegId.setEnabled(true);
    }

    private void setForRegistered (String regID) {
        txtRegistrationID.setText(regID);
        btnUnregister.setEnabled(true);
        btnRegister.setEnabled(false);
        btnUpdateRegId.setEnabled(true);
    }
    private void setForUnregistered () {
        txtRegistrationID.setText("");
        btnUnregister.setEnabled(false);
        btnRegister.setEnabled(true);
        btnUpdateRegId.setEnabled(false);
    }


}


