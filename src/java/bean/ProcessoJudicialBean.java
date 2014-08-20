/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import entidade.Executado;
import entidade.ProcessoJudicial;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author paulones
 */
@SessionScoped
@ManagedBean(name = "processoJudicialBean")
public class ProcessoJudicialBean implements Serializable{
    
    private ProcessoJudicial processoJudicial;
    private Executado executado;
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()){
            executado = new Executado();
            processoJudicial = new ProcessoJudicial();
        }
    }

    public ProcessoJudicial getProcessoJudicial() {
        return processoJudicial;
    }

    public void setProcessoJudicial(ProcessoJudicial processoJudicial) {
        this.processoJudicial = processoJudicial;
    }

    public Executado getExecutado() {
        return executado;
    }

    public void setExecutado(Executado executado) {
        this.executado = executado;
    }
    
    
}
