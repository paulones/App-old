/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import bo.LogBO;
import bo.UtilBO;
import entidade.Log;

/**
 *
 * @author paulones
 */
public class GeradorLog {
    
    private static LogBO logBO;
    private static UtilBO utilBO;
    
    public static void criar(Integer idFk, String tabela, char operacao){
        logBO = new LogBO();
        utilBO = new UtilBO();
        Log log = new Log();
        log.setDataDeCriacao(utilBO.findServerTime());
        log.setIdFk(idFk);
        log.setOperacao(operacao);
        log.setTabela(tabela);
        logBO.create(log);
    }
}
