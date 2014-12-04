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
        LogBO logBO = new LogBO();
        UtilBO utilBO = new UtilBO();
        UsuarioBO usuarioBO = new UsuarioBO();
        Log log = new Log();
        log.setDataDeCriacao(utilBO.findServerTime());
        log.setIdFk(idFk);
        log.setOperacao(operacao);
        log.setTabela(tabela);
        log.setInstituicaoFk(usuarioBO.findAutorizacaoByCPF(Cookie.getCookie("usuario")).getInstituicaoFk());
        logBO.create(log);
    }
}
