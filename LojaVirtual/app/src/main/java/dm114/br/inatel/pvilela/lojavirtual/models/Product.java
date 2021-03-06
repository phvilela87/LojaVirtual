package dm114.br.inatel.pvilela.lojavirtual.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pedro on 14/06/16.
 */
public class Product implements Serializable {
    public int Id;
    public String nome;
    public String descricao;
    public String codigo;
    public double preco;
    
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

}
