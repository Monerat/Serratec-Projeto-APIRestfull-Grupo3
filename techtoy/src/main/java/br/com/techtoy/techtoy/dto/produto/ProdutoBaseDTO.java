package br.com.techtoy.techtoy.dto.produto;

public abstract class ProdutoBaseDTO {

    protected Long id;
    protected String nome;
    protected String observacao;
    protected Integer estoque;
    protected Double valorUn;
    protected Boolean ativo;
    protected String imagem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Double getValorUn() {
        return valorUn;
    }

    public void setValorUn(Double valorUn) {
        this.valorUn = valorUn;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

}
