package dm114.br.inatel.pvilela.lojavirtual.tasks;


import android.content.Context;
import android.os.AsyncTask;

import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceAdminMessageProviderClient;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceSalesProviderClient;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceResponse;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceUserMessageProviderClient;

public class UserTasks {

    private UserEvents userEvents;
    private String email;
    private String password;
    private Context context;

    public UserTasks(UserEvents userEvents, Context context, String email, String password) {

        this.userEvents = userEvents;
        this.context = context;
        this.email = email;
        this.password = password;
    }

    public UserTasks(UserEvents userEvents, Context context) {

        this.userEvents = userEvents;
        this.context = context;
    }

    /**
     * Autenticação do usuário no provedor de vendas
     */
    public void loginValidationOnSalesProvider() {

        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                return WebServiceSalesProviderClient.loginAuthentication(context, email, password);
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    userEvents.loginSuccessOnSalesProvider();
                } else {
                    userEvents.loginFailOnSalesProvider();
                }
            }
        }.execute(null, null, null);

    }

    /**
     * Autentição do usuário no provedor de mensagens
     */
    public void loginValidationOnUserMessageProvider() {

        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                return WebServiceUserMessageProviderClient.loginAuthentication(context, email, password);
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    userEvents.loginSuccessOnUserMessageProvider();
                } else {
                    userEvents.loginFailOnUserMessageProvider();
                }
            }
        }.execute(null, null, null);

    }

    /**
     * Autenticação do Admin no provedor de mensagens
     */
    public void loginValidationOnAdminMessageProvider() {

        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                String adminUsername = context.getResources().getString(R.string.pref_ws_admin_username);
                String adminPassword = context.getResources().getString(R.string.pref_ws_admin_password);
                return WebServiceAdminMessageProviderClient.loginAuthentication(context, adminUsername, adminPassword);
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    userEvents.loginSuccessOnAdminMessageProvider();
                } else {
                    userEvents.loginFailOnAdminMessageProvider();
                }
            }
        }.execute(null, null, null);

    }

}
