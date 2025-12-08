package model;

import java.util.Date;

public abstract class PessoaFisica extends Pessoa {
    
    // atributos encapsulados da entidade PessoaFisica
    private Date dataNascimento;
    private String cpf;
    
    //getters e setters
    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
        
}
