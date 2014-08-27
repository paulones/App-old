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
public class EnderecoPessoaFisicaJuridicaHistorico implements Serializable{
    
    private Object pessoaHistorico;
    private EnderecoHistorico enderecoHistorico;
    private List<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoList;

    public EnderecoPessoaFisicaJuridicaHistorico() {
    }

    
    public EnderecoPessoaFisicaJuridicaHistorico(Object pessoaHistorico, EnderecoHistorico enderecoHistorico) {
        this.pessoaHistorico = pessoaHistorico;
        this.enderecoHistorico = enderecoHistorico;
    }

    public Object getPessoaHistorico() {
        return pessoaHistorico;
    }

    public void setPessoaHistorico(Object pessoaHistorico) {
        this.pessoaHistorico = pessoaHistorico;
    }

    public EnderecoHistorico getEnderecoHistorico() {
        return enderecoHistorico;
    }

    public void setEnderecoHistorico(EnderecoHistorico enderecoHistorico) {
        this.enderecoHistorico = enderecoHistorico;
    }

    public List<PessoaFisicaJuridicaHistorico> getPessoaFisicaJuridicaHistoricoList() {
        return pessoaFisicaJuridicaHistoricoList;
    }

    public void setPessoaFisicaJuridicaHistoricoList(List<PessoaFisicaJuridicaHistorico> pessoaFisicaJuridicaHistoricoList) {
        this.pessoaFisicaJuridicaHistoricoList = pessoaFisicaJuridicaHistoricoList;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.pessoaHistorico);
        hash = 13 * hash + Objects.hashCode(this.enderecoHistorico);
        hash = 13 * hash + Objects.hashCode(this.pessoaFisicaJuridicaHistoricoList);
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
        final EnderecoPessoaFisicaJuridicaHistorico other = (EnderecoPessoaFisicaJuridicaHistorico) obj;
        if (!Objects.equals(this.pessoaHistorico, other.pessoaHistorico)) {
            return false;
        }
        if (!Objects.equals(this.enderecoHistorico, other.enderecoHistorico)) {
            return false;
        }
        if (!Objects.equals(this.pessoaFisicaJuridicaHistoricoList, other.pessoaFisicaJuridicaHistoricoList)) {
            return false;
        }
        return true;
    }
    
    

    
}
