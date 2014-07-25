/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import dao.ConfigDAO;
import entidade.Config;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.Criptografia;

/**
 *
 * @author Pedro
 */
public class LoginBO implements Serializable {

    private String chaveTeste = "l3Q3zmwpr2oRpzGH9pTcR+TDyYzj1264XX4keImFnHU=";
    ConfigDAO configDAO = new ConfigDAO();

    public LoginBO() {

    }

    //Verifica a licença fornecida ao sistema
    public Boolean licenciar(String crypt) {
        Boolean ok = false;
        try {
            //Consulta em uma persistênca a string de licença
            String licenca = DesEncriptonator(crypt);
            if (licenca.length() == 31) {
                String tipo = licenca.substring(0, 1);
                String cnpj = licenca.substring(1, 15);
                String iniData = licenca.substring(15, 23);
                String endData = licenca.substring(23, 31);
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                Date dataChave = sdf.parse(endData);
                Date dataAtual = new Date();
                if (dataAtual.before(dataChave)) {
                    Config config = new Config();
                    if (configDAO.getConfigCount() == 0) {
                        config.setCnpj(cnpj);
                        config.setChave(crypt);
                        config.setUltimoLogin(dataAtual);
                        configDAO.create(config);
                    } else {
                        config = configDAO.findConfig(cnpj);
                        config.setChave(crypt);
                        config.setUltimoLogin(dataAtual);
                        configDAO.edit(config);
                    }
                    ok = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    //Verifica se o tempo da última licença já expirou
    public Boolean expirado() {
        Boolean ok = true;
        Config config = new Config();
        //Consulta em uma persistênca a string de licença
        try {
            config = configDAO.findFirstConfig();
            String descriptografia;
            //System.out.println("config: "+config.getChave()+", "+config.getCnpj()+", "+config.getUltimoLogin().toString());
            if (!config.getChave().isEmpty()) {
                descriptografia = DesEncriptonator(config.getChave());
                String endData = descriptografia.substring(23, 31);
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                Date dataChave = sdf.parse(endData);
                if(!dataChave.before(new Date())){
                    ok = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    public String DesEncriptonator(String chave) {
        Criptografia encrypter = new Criptografia("aabbccaa");
        return encrypter.decrypt(chave);
    }

}
