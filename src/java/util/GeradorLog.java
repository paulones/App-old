/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import bo.LogBO;
import bo.UsuarioBO;
import bo.UtilBO;
import entidade.Log;

/**
 *
 * @author paulones
 */
public class GeradorLog {
    
    
    public static void criar(Integer idFk, String tabela, char operacao){
        Log log = new Log();
        log.setDataDeCriacao(UtilBO.findServerTime());
        log.setIdFk(idFk);
        log.setOperacao(operacao);
        log.setTabela(tabela);
        log.setInstituicaoFk(UsuarioBO.findAutorizacaoByCPF(Cookie.getCookie("usuario")).getInstituicaoFk());
        LogBO.create(log);
    }
}
