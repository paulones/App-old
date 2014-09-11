/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import dao.InstituicaoDAO;
import entidade.Instituicao;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.Base64Crypt;

/**
 *
 * @author Pedro
 */
public class LoginBO implements Serializable {

    private InstituicaoDAO instituicaoDAO;

    public LoginBO() {
        instituicaoDAO = new InstituicaoDAO();
    }

    //Verifica a licença fornecida ao sistema
    public Boolean licenciar(String crypt) {
        Boolean ok = false;
        try {
            //Consulta em uma persistênca a string de licença
            Base64Crypt base64Crypt = new Base64Crypt();
            String licenca = base64Crypt.decrypt(crypt);
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
                    Instituicao instituicao = new Instituicao();
                    instituicao = instituicaoDAO.findInstituicaoByCNPJ(cnpj);
                    //System.out.println("instituicao: "+instituicao.getRazaoSocial());
                    if (instituicao == null) { // Cria instituição caso a mesma não exista
                        instituicao.setCnpj(cnpj);
                        instituicao.setChave(crypt);
                        instituicao.setUltimoLogin(base64Crypt.encrypt(sdf.format(dataAtual)));
                        instituicaoDAO.create(instituicao);
                    } else {
                        instituicao.setChave(crypt);
                        instituicao.setUltimoLogin(base64Crypt.encrypt(sdf.format(dataAtual)));
                        instituicaoDAO.edit(instituicao);
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
    public Boolean expirado(String cpf) {
        Boolean ok = true;
        Instituicao instituicao = new Instituicao();
        //Consulta em uma persistênca a string de licença
        try {
            instituicao = instituicaoDAO.findInstituicaoByCPF(cpf);
            String descriptografia;
            String ultimoLogin = "";
            if (instituicao.getChave() != null) {
                Base64Crypt base64Crypt = new Base64Crypt();
                descriptografia = base64Crypt.decrypt(instituicao.getChave());
                String endData = descriptografia.substring(9, 17);
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                Date dataChave = sdf.parse(endData);
                if (instituicao.getUltimoLogin() != null) { //Verifica se existe um último login realizado
                    ultimoLogin = base64Crypt.decrypt(instituicao.getUltimoLogin());
                    Date ultimoDateLogin = sdf.parse(ultimoLogin);
                    if (ultimoDateLogin.before(new Date())) {
                        instituicao.setUltimoLogin(base64Crypt.encrypt(sdf.format(new Date())));
                    }
                }
                //Criar regra para licença vitalicia
                if (!dataChave.before(new Date())) {
                    instituicaoDAO.edit(instituicao);
                    ok = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

}
