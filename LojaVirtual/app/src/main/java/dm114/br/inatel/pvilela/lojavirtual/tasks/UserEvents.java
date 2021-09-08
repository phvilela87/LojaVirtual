package dm114.br.inatel.pvilela.lojavirtual.tasks;

public interface UserEvents {

    /**
     * Autenticação do usuário no provedor de vendas
     */
    void loginSuccessOnSalesProvider();
    void loginFailOnSalesProvider();

    /**
     * Autenticação do usuário no provedor de mensagens
     */
    void loginSuccessOnUserMessageProvider();
    void loginFailOnUserMessageProvider();

    /**
     * Autenticação do admin no provedor de mensagens
     */
    void loginSuccessOnAdminMessageProvider();
    void loginFailOnAdminMessageProvider();
}
