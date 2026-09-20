package model;


public class FuncionarioModel {
    
    private int id;
    private String nome;
    private String sobrenome;
    private String codigo;
    private String senha;
    
    public FuncionarioModel() {
        
    }
    
    public FuncionarioModel(int id, String nome, String sobrenome, String codigo, String senha) {
        
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.codigo = codigo;
        this.senha = senha;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getSobrenome() {
        return sobrenome;
    }
    
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
