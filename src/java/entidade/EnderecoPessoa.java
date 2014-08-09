/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

/**
 *
 * @author paulones
 */
public class EnderecoPessoa {
    
    private Object pessoa;
    private Endereco endereco;
    
    public EnderecoPessoa(Object pessoa, Endereco endereco){
        this.pessoa = pessoa;
        this.endereco = endereco;
    }

    public Object getPessoa() {
        return pessoa;
    }

    public void setPessoa(Object pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    
}
