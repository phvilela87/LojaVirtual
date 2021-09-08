package dm114.br.inatel.pvilela.lojavirtual.gcm;

import java.io.IOException;

import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;

public interface GCMRegisterEvents {
    void gcmRegisterFinished (String registrationID);
    void gcmRegisterFailed (IOException ex);
    void gcmUnregisterFinished ();
    void gcmUnregisterFailed (IOException ex);

    void updateRegIdFinished();
    void updateRedIdFailed(WebServiceResponse webServiceResponse);
}