/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author paulones
 */
public class EnderecoPessoa implements Serializable{
    
    private Object pessoa;
    private Endereco endereco;

    public EnderecoPessoa() {
    }
    
    public EnderecoPessoa(Object pessoa, Endereco endereco) {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.pessoa);
        hash = 83 * hash + Objects.hashCode(this.endereco);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EnderecoPessoa other = (EnderecoPessoa) obj;
        if (!Objects.equals(this.pessoa, other.pessoa)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        return true;
    }
    
    
}
