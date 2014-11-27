/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author paulones
 */
public class EnderecoPessoa implements Serializable{
    
    private Object pessoa;
    private Endereco endereco;
    private List<Bem> bemList;

    public EnderecoPessoa() {
    }
    
    public EnderecoPessoa(Object pessoa, Endereco endereco, List<Bem> bemList) {
        this.pessoa = pessoa;
        this.endereco = endereco;
        this.bemList = bemList;
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

    public List<Bem> getBemList() {
        return bemList;
    }

    public void setBemList(List<Bem> bemList) {
        this.bemList = bemList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.pessoa);
        hash = 83 * hash + Objects.hashCode(this.endereco);
        hash = 83 * hash + Objects.hashCode(this.bemList);
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
        if (!Objects.equals(this.bemList, other.bemList)) {
            return false;
        }
        return true;
    }

    
    
    
}
