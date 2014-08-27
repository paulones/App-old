/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bo.EnderecoBO;
import bo.PessoaFisicaBO;
import bo.PessoaJuridicaBO;
import entidade.Endereco;
import entidade.PessoaFisica;
import entidade.PessoaFisicaJuridica;
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
     *
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
     *
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
    public String getPessoasFisicas() {
        PessoaFisicaBO pessoaFisicaBO = new PessoaFisicaBO();
        JSONArray jsonArray = new JSONArray();
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaBO.findAllActive();
        for (PessoaFisica pf : pessoaFisicaList) {
            String cpf = pf.getCpf() == null ? "Sem CPF" : pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9);
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
    public String getPessoasJuridicas() {
        PessoaJuridicaBO pessoaJuridicaBO = new PessoaJuridicaBO();
        JSONArray jsonArray = new JSONArray();
        List<PessoaJuridica> pessoaJuridicaList = pessoaJuridicaBO.findAllActive();
        for (PessoaJuridica pj : pessoaJuridicaList) {
            String cnpj = pj.getCnpj().substring(0, 2) + "." + pj.getCnpj().substring(2, 5) + "." + pj.getCnpj().substring(5, 8) + "/" + pj.getCnpj().substring(8, 12) + "-" + pj.getCnpj().substring(12);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", Base64Crypt.encrypt(pj.getId().toString()));
            jsonObject.put("text", cnpj + " - " + pj.getNome());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @GET
    @Path("/getPessoasFisicasTable")
    @Produces("application/json")
    public String getPessoasFisicasTable() {
        PessoaFisicaBO pessoaFisicaBO = new PessoaFisicaBO();
        EnderecoBO enderecoBO = new EnderecoBO();
        JSONArray jsonArray = new JSONArray();
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaBO.findAllActive();
        for (PessoaFisica pf : pessoaFisicaList) {
            Endereco end = enderecoBO.findPFAddress(pf.getId());
            String sexo = pf.getSexo() == null ? "-" : "M".equals(pf.getSexo().toString()) ? "Masculino" : "Feminino";
            String cpf = pf.getCpf() == null ? "-" : pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9);
            String rg = pf.getRg() == null ? "-" : pf.getRg();
            if (pf.getRgOrgaoEmissor() != null) {
                rg += " - " + pf.getRgOrgaoEmissor();
            }
            if (pf.getRgUfFk() != null) {
                rg += "/" + pf.getRgUfFk().getUf();
            }
            String detalhes = pf.getApelido() + " " + pf.getTituloDeEleitor() + " " + pf.getInss() + " " + pf.getNomeDoPai() + " " + pf.getNomeDaMae() + " " + pf.getNomeDoConjuge() + " "
                    + pf.getObservacoes() + " " + (pf.getCidadeFk() == null ? "" : pf.getCidadeFk().getNome()) + " " + (pf.getEstadoFk() == null ? "" : pf.getEstadoFk().getNome()) + " " 
                    + (pf.getEstadoCivilFk() == null ? "" : pf.getEstadoCivilFk().getSituacao()) + " " + (pf.getNacionalidadeFk()== null ? "" : pf.getNacionalidadeFk().getTipo()) + " "
                    + (pf.getPaisFk() == null ? "" : pf.getPaisFk().getNome()) + " " + (pf.getCidadeEleitoralFk()== null ? "" : pf.getCidadeEleitoralFk().getNome()) + " "
                    + (pf.getEstadoEleitoralFk()  == null ? "" : pf.getEstadoEleitoralFk().getNome()) + " " + pf.getZona() + " " + pf.getSecao() + " " + pf.getEndereco() + " "
                    + pf.getLocal() + " " + end.getBairro() + " " + end.getCep() + " " + (end.getCidadeFk() == null ? "" : end.getCidadeFk().getNome()) + " " + (end.getEstadoFk()== null ? "" : end.getEstadoFk().getNome()) + " "
                    + end.getNumero() + " " + end.getComplemento() + " " + end.getEndereco();
            for (PessoaFisicaJuridica pfj : (List<PessoaFisicaJuridica>) pf.getPessoaFisicaJuridicaCollection()){
                detalhes += " " + pfj.getPessoaJuridicaFk().getCnpj() + " " + pfj.getPessoaJuridicaFk().getNome() + " " + (pfj.getFuncaoFk() == null ? "" : pfj.getFuncaoFk().getFuncao()) + " " + pfj.getCapitalDeParticipacao() + " " + pfj.getDataDeInicio() + " " + pfj.getDataDeTermino();
            }
            detalhes = detalhes.replace("null", "");
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("row-details", "<span class='row-details row-details-close'></span>");
            jsonObject.put("nome", pf.getNome());
            jsonObject.put("sexo", sexo);
            jsonObject.put("cpf", cpf);
            jsonObject.put("rg", rg);
            jsonObject.put("delete", "<a class='button-delete' href='javascript:;'><i class='glyphicon glyphicon-remove' style='color:red'></i></a>");
            jsonObject.put("detalhes", detalhes);
            jsonObject.put("pf", Base64Crypt.encrypt(pf.getId().toString()));
            jsonArray.put(jsonObject);
        }
        JSONObject json = new JSONObject();
        json.put("data", jsonArray);
        return json.toString();
    }
    
    @GET
    @Path("/getPessoasJuridicaTable")
    @Produces("application/json")
    public String getPessoasJuridicasTable() {
        PessoaJuridicaBO pessoaJuridicaBO = new PessoaJuridicaBO();
        EnderecoBO enderecoBO = new EnderecoBO();
        JSONArray jsonArray = new JSONArray();
        List<PessoaJuridica> pessoaJuridicaList = pessoaJuridicaBO.findAllActive();
        for (PessoaJuridica pj : pessoaJuridicaList) {
            Endereco end = enderecoBO.findPJAddress(pj.getId());
//            String cnpj = pj.getCnpj()== null ? "-" : pj.getCnpj();
            String cnpj = pj.getCnpj()== null ? "-" : pj.getCnpj().substring(0, 2) + "." + pj.getCnpj().substring(2, 5) + "." + pj.getCnpj().substring(5, 8) + "/" + pj.getCnpj().substring(8, 12) + "-" + pj.getCnpj().substring(12);
            String nomeFantasia = pj.getNomeFantasia() == null ? "-" : pj.getNomeFantasia();
            String tipoEmpresarial = pj.getTipoEmpresarialFk() == null ? "-" : pj.getTipoEmpresarialFk().getTipo();
            String detalhes = pj.getInscricaoEstadual() + " " + pj.getInscricaoMunicipal() + " " + (pj.getSituacao() == null ? "" : "A".equals(pj.getSituacao().toString()) ? "Ativo" : "Inativo")  + " "
                    + pj.getMotivoDaDesativacao() + " " + pj.getDataDeCriacao() + " " + pj.getGrupoEconomico() + " " + pj.getCnae() + " " + pj.getNire() + " " + pj.getAtividadePrincipal() + " "
                    + pj.getAtividadeSecundaria() + " " + end.getBairro() + " " + end.getCep() + " " + (end.getCidadeFk() == null ? "" : end.getCidadeFk().getNome()) + " " 
                    + (end.getEstadoFk()== null ? "" : end.getEstadoFk().getUf()) + " " + end.getNumero() + " " + end.getComplemento() + " " + end.getEndereco();
                    
            for (PessoaFisicaJuridica pfj : (List<PessoaFisicaJuridica>) pj.getPessoaFisicaJuridicaCollection()){
                detalhes += " " + (pfj.getPessoaFisicaFk().getCpf() == null ? "" : pfj.getPessoaFisicaFk().getCpf().substring(0, 3) + "." + pfj.getPessoaFisicaFk().getCpf().substring(3, 6) + "." + pfj.getPessoaFisicaFk().getCpf().substring(6, 9) + "-" + pfj.getPessoaFisicaFk().getCpf().substring(9)) + " " 
                         + pfj.getPessoaFisicaFk().getNome() + " " + (pfj.getFuncaoFk() == null ? "" : pfj.getFuncaoFk().getFuncao()) + " " 
                         + pfj.getCapitalDeParticipacao() + " " + pfj.getDataDeInicio() + " " + pfj.getDataDeTermino();
            }
            detalhes = detalhes.replace("null", "");
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("row-details", "<span class='row-details row-details-close'></span>");
            jsonObject.put("nome", pj.getNome());
            jsonObject.put("nomeFantasia", nomeFantasia);
            jsonObject.put("cnpj", cnpj);
            jsonObject.put("tipoEmpresarial", tipoEmpresarial);
            jsonObject.put("delete", "<a class='button-delete' href='javascript:;'><i class='glyphicon glyphicon-remove' style='color:red'></i></a>");
            jsonObject.put("detalhes", detalhes);
            jsonObject.put("pj", Base64Crypt.encrypt(pj.getId().toString()));
            jsonArray.put(jsonObject);
        }
        JSONObject json = new JSONObject();
        json.put("data", jsonArray);
        return json.toString();
    }

}
