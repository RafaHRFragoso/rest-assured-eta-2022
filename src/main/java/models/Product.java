package models;

public class Product {

    public String nome;
    public String preco;
    public String descricao;
    public String quantidade;
    public String _id;

    public Product(String nome, String preco, String descricao, String quantidade){
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public void setProdId(String prodId) {
        this._id = prodId;
    }

}
