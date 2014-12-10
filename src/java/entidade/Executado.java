/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author paulones
 */
public class Executado implements Serializable {

    private ProcessoJudicial processoJudicial;
    private EnderecoPessoa enderecoPessoa;
    private List<Citacao> citacaoList;
    private List<SocioRedirecionamento> socioRedirecionamentoList;
    private List<Penhora> penhoraList;
    private List<List<AquisicaoBem>> listOfAquisicaoBemList;

    public Executado() {
    }

    public Executado(ProcessoJudicial processoJudicial, EnderecoPessoa enderecoPessoa, List<Citacao> citacaoList, List<SocioRedirecionamento> socioRedirecionamentoList, List<Penhora> penhoraList, List<AquisicaoBem> aquisicaoBemList) {
        this.processoJudicial = processoJudicial;
        this.enderecoPessoa = enderecoPessoa;
        this.citacaoList = citacaoList;
        this.socioRedirecionamentoList = socioRedirecionamentoList;
        this.penhoraList = penhoraList;
        listOfAquisicaoBemList = new ArrayList<>();
        int i = 0;
        List<AquisicaoBem> aquisicaoBemListTemp = new ArrayList<>();
        while (i < aquisicaoBemList.size()) {
            aquisicaoBemListTemp.add(aquisicaoBemList.get(i));
            if ((i + 1) % 4 == 0) {
                listOfAquisicaoBemList.add(aquisicaoBemListTemp);
                aquisicaoBemListTemp = new ArrayList<>();
            }
            i++;
        }
    }

    public ProcessoJudicial getProcessoJudicial() {
        return processoJudicial;
    }

    public void setProcessoJudicial(ProcessoJudicial processoJudicial) {
        this.processoJudicial = processoJudicial;
    }

    public EnderecoPessoa getEnderecoPessoa() {
        return enderecoPessoa;
    }

    public void setEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoa = enderecoPessoa;
    }

    public List<Citacao> getCitacaoList() {
        return citacaoList;
    }

    public void setCitacaoList(List<Citacao> citacaoList) {
        this.citacaoList = citacaoList;
    }

    public List<SocioRedirecionamento> getSocioRedirecionamentoList() {
        return socioRedirecionamentoList;
    }

    public void setSocioRedirecionamentoList(List<SocioRedirecionamento> socioRedirecionamentoList) {
        this.socioRedirecionamentoList = socioRedirecionamentoList;
    }

    public List<Penhora> getPenhoraList() {
        return penhoraList;
    }

    public void setPenhoraList(List<Penhora> penhoraList) {
        this.penhoraList = penhoraList;
    }

    public List<List<AquisicaoBem>> getListOfAquisicaoBemList() {
        return listOfAquisicaoBemList;
    }

    public void setListOfAquisicaoBemList(List<List<AquisicaoBem>> listOfAquisicaoBemList) {
        this.listOfAquisicaoBemList = listOfAquisicaoBemList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.processoJudicial);
        hash = 11 * hash + Objects.hashCode(this.enderecoPessoa);
        hash = 11 * hash + Objects.hashCode(this.citacaoList);
        hash = 11 * hash + Objects.hashCode(this.socioRedirecionamentoList);
        hash = 11 * hash + Objects.hashCode(this.penhoraList);
        hash = 11 * hash + Objects.hashCode(this.listOfAquisicaoBemList);
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
        final Executado other = (Executado) obj;
        if (!Objects.equals(this.processoJudicial, other.processoJudicial)) {
            return false;
        }
        if (!Objects.equals(this.enderecoPessoa, other.enderecoPessoa)) {
            return false;
        }
        return true;
    }

}
