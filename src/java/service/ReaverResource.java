/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import bo.PessoaFisicaBO;
import bo.PessoaJuridicaBO;
import entidade.PessoaFisica;
import entidade.PessoaJuridica;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Base64Crypt;

/**
 * REST Web Service
 *
 * @author paulones
 */
@Path("reaver")
public class ReaverResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ReaverResource
     */
    public ReaverResource() {
    }

    /**
     * Retrieves representation of an instance of service.ReaverResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ReaverResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
    
    @GET
    @Path("/getPessoasFisicas")
    @Produces("application/json")
    public String getPessoasFisicas(){
        PessoaFisicaBO pessoaFisicaBO = new PessoaFisicaBO();
        JSONArray jsonArray = new JSONArray();
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaBO.findAllActive();
        for (PessoaFisica pf: pessoaFisicaList){
            String cpf = pf.getCpf() == null ? "Sem CPF" : pf.getCpf().substring(0,3)+"."+pf.getCpf().substring(3,6)+"."+pf.getCpf().substring(6,9)+"-"+pf.getCpf().substring(9);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", Base64Crypt.encrypt(pf.getId().toString()));
            jsonObject.put("text", cpf + " - " + pf.getNome());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
    
    @GET
    @Path("/getPessoasJuridicas")
    @Produces("application/json")
    public String getPessoasJuridicas(){
        PessoaJuridicaBO pessoaJuridicaBO = new PessoaJuridicaBO();
        JSONArray jsonArray = new JSONArray();
        List<PessoaJuridica> pessoaJuridicaList = pessoaJuridicaBO.findAllActive();
        for (PessoaJuridica pj: pessoaJuridicaList){
            String cnpj = pj.getCnpj().substring(0,2)+"."+pj.getCnpj().substring(2,5)+"."+pj.getCnpj().substring(5,8)+"/"+pj.getCnpj().substring(8,12)+"-"+pj.getCnpj().substring(12);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", Base64Crypt.encrypt(pj.getId().toString()));
            jsonObject.put("text", cnpj + " - " + pj.getNome());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
    
}
