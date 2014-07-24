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
            System.out.println("key: " + crypt);
            String licenca = DesEncriptonator(crypt);
            System.out.println("descriptografia: " + licenca);
            if (licenca.length() == 31) {
                String tipo = licenca.substring(0, 1);
                System.out.println("soft: " + tipo);
                String cnpj = licenca.substring(1, 15);
                System.out.println("cnpj: " + cnpj);
                String iniData = licenca.substring(15, 23);
                String endData = licenca.substring(23, 31);
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                Date dataChave = sdf.parse(endData);
                Date dataAtual = new Date();
                System.out.println("end data: " + dataChave.toString());
                System.out.println("data atual: " + dataAtual.toString());
                if (dataAtual.before(dataChave)) {
                    Config config = new Config();
                    /*if (configDAO.getConfigCount() == 0){
                        config.setCnpj(cnpj);
                        config.setKey(crypt);
                        config.setLastLogin(null);
                        //System.out.println("new config: "+config.getCnpj()+", "+config.getKey()+", "+config.getLastLogin().toString());
                        configDAO.create(config);
                    } else {
                        config = configDAO.findConfig(cnpj);
                        config.setKey(crypt);
                        config.setLastLogin(null);
                        System.out.println("edit config: "+config.getCnpj()+", "+config.getKey());
                        configDAO.edit(config);
                    }*/
                    ok = true;
                }
            } else {
                System.out.println("tamanho errado: "+licenca.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    //Verifica se o tempo da última licença já expirou
    public Boolean expirado() {
        Boolean ok = true;
        //Consulta em uma persistênca a string de licença
        System.out.println("teste expirado: " + chaveTeste);
        String descriptografia = DesEncriptonator(chaveTeste);
        System.out.println("descriptografia: " + descriptografia);
        return ok;
    }

    public String DesEncriptonator(String chave) {
        Criptografia encrypter = new Criptografia("aabbccaa");
        return encrypter.decrypt(chave);
    }

}
