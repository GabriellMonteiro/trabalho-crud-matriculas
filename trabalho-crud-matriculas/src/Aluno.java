public class Aluno {
    private int id;
    private String nome;
    private int idade;
    private String email;
    private String endereco;
    private String cep;
    private String telefone;
    private String usuario;
    private String senha;
    private String curso;
    private String observacoes;
    private boolean ativo;

    public Aluno(int id, String nome, int idade, String email, String endereco, String cep, String telefone,
                 String usuario, String senha, String curso, String observacoes, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.endereco = endereco;
        this.cep = cep;
        this.telefone = telefone;
        this.usuario = usuario;
        this.senha = senha;
        this.curso = curso;
        this.observacoes = observacoes;
        this.ativo = ativo;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Object[] toArray() {
        return new Object[]{id, nome,idade,email, endereco,cep, telefone,curso, observacoes, ativo};
    }
}
