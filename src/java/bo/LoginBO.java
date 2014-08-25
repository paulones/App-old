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
import util.Base64Crypt;

/**
 *
 * @author Pedro
 */
public class LoginBO implements Serializable {

    private String chaveTeste = "79OPRx8E0xVmaqi950wC1pxBWBN3Jt1jh6qUrsDJJeY=";
    private String cnpj = "12345678901150";
    private String cryptKey = "deadwood8986deadwood8986";
    ConfigDAO configDAO = new ConfigDAO();

    public LoginBO() {

    }

    //Verifica a licença fornecida ao sistema
    public Boolean licenciar(String crypt) {
        Boolean ok = false;
        try {
            //Consulta em uma persistênca a string de licença
            String licenca = Base64Crypt.decrypt(crypt);
            //System.out.println("chave: "+crypt);
            //System.out.println("licenca: "+licenca);
            if (licenca != null && licenca.length() >= 31) {
                String tipo = licenca.substring(0, 1);
                String iniData = licenca.substring(1, 9);
                String endData = licenca.substring(9, 17);
                String cnpj = licenca.substring(17, 31);
                //System.out.println("string: "+tipo +" - "+ iniData +" - "+ endData+" - "+ cnpj);
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
                        System.out.println(cnpj);
                        config = configDAO.findConfigByCNPJ(cnpj);
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
            config = configDAO.findConfigByCNPJ(cnpj);
            String descriptografia;
            //System.out.println("config: "+config.getChave()+", "+config.getCnpj()+", "+config.getUltimoLogin().toString());
            if (config != null) {
                descriptografia = Base64Crypt.decrypt(config.getChave());
                String endData = descriptografia.substring(9, 17);
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

}
