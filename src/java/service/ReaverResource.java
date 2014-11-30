/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bo.BemBO;
import bo.ChartsBO;
import bo.EnderecoBO;
import bo.LogBO;
import bo.PessoaFisicaBO;
import bo.PessoaJuridicaBO;
import bo.PessoaJuridicaSucessaoBO;
import bo.ProcessoJudicialBO;
import bo.UsuarioBO;
import entidade.Bem;
import entidade.Endereco;
import entidade.Instituicao;
import entidade.Log;
import entidade.PessoaFisica;
import entidade.PessoaFisicaFisica;
import entidade.PessoaFisicaJuridica;
import entidade.PessoaJuridica;
import entidade.PessoaJuridicaJuridica;
import entidade.PessoaJuridicaSucessao;
import entidade.ProcessoJudicial;
import entidade.VinculoProcessual;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Cookie;
import util.TimestampUtils;

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
    public String getPessoasFisicas(@QueryParam("usuario") String usuario) {
        PessoaFisicaBO pessoaFisicaBO = new PessoaFisicaBO();
        JSONArray jsonArray = new JSONArray();
        UsuarioBO usuarioBO = new UsuarioBO();
        Instituicao instituicao = usuarioBO.findAutorizacaoByCPF(usuario).getInstituicaoFk();
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaBO.findAllActive(instituicao);
        for (PessoaFisica pf : pessoaFisicaList) {
            String cpf = pf.getCpf() == null ? "Sem CPF" : pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", pf.getId().toString());
            jsonObject.put("text", cpf + " - " + pf.getNome());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @GET
    @Path("/getPessoasJuridicas")
    @Produces("application/json")
    public String getPessoasJuridicas(@QueryParam("usuario") String usuario) {
        PessoaJuridicaBO pessoaJuridicaBO = new PessoaJuridicaBO();
        UsuarioBO usuarioBO = new UsuarioBO();
        Instituicao instituicao = usuarioBO.findAutorizacaoByCPF(usuario).getInstituicaoFk();
        JSONArray jsonArray = new JSONArray();
        List<PessoaJuridica> pessoaJuridicaList = pessoaJuridicaBO.findAllActive(instituicao);
        for (PessoaJuridica pj : pessoaJuridicaList) {
            String cnpj = pj.getCnpj().substring(0, 2) + "." + pj.getCnpj().substring(2, 5) + "." + pj.getCnpj().substring(5, 8) + "/" + pj.getCnpj().substring(8, 12) + "-" + pj.getCnpj().substring(12);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", pj.getId().toString());
            jsonObject.put("text", cnpj + " - " + pj.getNome());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @GET
    @Path("/getPessoasFisicasTable")
    @Produces("application/json")
    public String getPessoasFisicasTable(@QueryParam("usuario") String usuario) {
        PessoaFisicaBO pessoaFisicaBO = new PessoaFisicaBO();
        EnderecoBO enderecoBO = new EnderecoBO();
        BemBO bemBO = new BemBO();
        UsuarioBO usuarioBO = new UsuarioBO();
        Instituicao instituicao = usuarioBO.findAutorizacaoByCPF(usuario).getInstituicaoFk();
        JSONArray jsonArray = new JSONArray();
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaBO.findAllActive(instituicao);
        for (PessoaFisica pf : pessoaFisicaList) {
            Endereco end = enderecoBO.findPFAddress(pf.getId());
            List<Bem> bemList = bemBO.findPFBens(pf.getId());
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
                    + pf.getObservacoes() + " " + (pf.getCidadeFk() == null ? "" : pf.getCidadeFk().getNome()) + " " + (pf.getEstadoFk() == null ? "" : pf.getEstadoFk().getUf()) + " "
                    + (pf.getEstadoCivilFk() == null ? "" : pf.getEstadoCivilFk().getSituacao()) + " " + (pf.getNacionalidadeFk() == null ? "" : pf.getNacionalidadeFk().getTipo()) + " "
                    + (pf.getPaisFk() == null ? "" : pf.getPaisFk().getNome()) + " " + (pf.getCidadeEleitoralFk() == null ? "" : pf.getCidadeEleitoralFk().getNome()) + " "
                    + (pf.getEstadoEleitoralFk() == null ? "" : pf.getEstadoEleitoralFk().getUf()) + " " + pf.getZona() + " " + pf.getSecao() + " " + pf.getEndereco() + " "
                    + pf.getLocal() + " " + end.getBairro() + " " + end.getCep() + " " + (end.getCidadeFk() == null ? "" : end.getCidadeFk().getNome()) + " " + (end.getEstadoFk() == null ? "" : end.getEstadoFk().getUf()) + " "
                    + end.getNumero() + " " + end.getComplemento() + " " + end.getEndereco();
            for (PessoaFisicaJuridica pfj : (List<PessoaFisicaJuridica>) pf.getPessoaFisicaJuridicaCollection()) {
                String cnpj = pfj.getPessoaJuridicaFk().getCnpj().substring(0, 2) + "." + pfj.getPessoaJuridicaFk().getCnpj().substring(2, 5) + "." + pfj.getPessoaJuridicaFk().getCnpj().substring(5, 8) + "/" + pfj.getPessoaJuridicaFk().getCnpj().substring(8, 12) + "-" + pfj.getPessoaJuridicaFk().getCnpj().substring(12);
                detalhes += " " + cnpj + " " + pfj.getPessoaJuridicaFk().getNome() + " " + (pfj.getFuncaoFk() == null ? "" : pfj.getFuncaoFk().getFuncao()) + " " + pfj.getCapitalDeParticipacao() + " " + pfj.getDataDeInicio() + " " + pfj.getDataDeTermino();
            }
            for (PessoaFisicaFisica pff : (List<PessoaFisicaFisica>) pf.getPessoaFisicaFisicaCollection()) {
                detalhes += " " + pff.getVinculoSocialFk().getVinculo() + " " + pff.getPessoaFisicaPrimariaFk().getNome() + " " + pff.getPessoaFisicaSecundariaFk().getNome() + " "
                        + (pff.getPessoaFisicaPrimariaFk().getCpf() == null ? "" : pff.getPessoaFisicaPrimariaFk().getCpf().substring(0, 3) + "." + pff.getPessoaFisicaPrimariaFk().getCpf().substring(3, 6) + "." + pff.getPessoaFisicaPrimariaFk().getCpf().substring(6, 9) + "-" + pff.getPessoaFisicaPrimariaFk().getCpf().substring(9)) + " "
                        + (pff.getPessoaFisicaSecundariaFk().getCpf() == null ? "" : pff.getPessoaFisicaSecundariaFk().getCpf().substring(0, 3) + "." + pff.getPessoaFisicaSecundariaFk().getCpf().substring(3, 6) + "." + pff.getPessoaFisicaSecundariaFk().getCpf().substring(6, 9) + "-" + pff.getPessoaFisicaSecundariaFk().getCpf().substring(9));
            }
            for (PessoaFisicaFisica pff : (List<PessoaFisicaFisica>) pf.getPessoaFisicaFisicaCollection1()) {
                detalhes += " " + pff.getVinculoSocialFk().getVinculo() + " " + pff.getPessoaFisicaPrimariaFk().getNome() + " " + pff.getPessoaFisicaSecundariaFk().getNome() + " "
                        + (pff.getPessoaFisicaPrimariaFk().getCpf() == null ? "" : pff.getPessoaFisicaPrimariaFk().getCpf().substring(0, 3) + "." + pff.getPessoaFisicaPrimariaFk().getCpf().substring(3, 6) + "." + pff.getPessoaFisicaPrimariaFk().getCpf().substring(6, 9) + "-" + pff.getPessoaFisicaPrimariaFk().getCpf().substring(9)) + " "
                        + (pff.getPessoaFisicaSecundariaFk().getCpf() == null ? "" : pff.getPessoaFisicaSecundariaFk().getCpf().substring(0, 3) + "." + pff.getPessoaFisicaSecundariaFk().getCpf().substring(3, 6) + "." + pff.getPessoaFisicaSecundariaFk().getCpf().substring(6, 9) + "-" + pff.getPessoaFisicaSecundariaFk().getCpf().substring(9));
            }
            for (Bem bem : bemList) {
                detalhes += " " + bem.getDataDeAquisicao() + " " + bem.getDataDeTransferenciaOuExtincao() + " " + bem.getDescricao() + " " + bem.getEndereco() + " " + bem.getValor() + " " + (bem.getTipoBemFk() == null ? "" : bem.getTipoBemFk().getTipo());
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
            jsonObject.put("pf", pf.getId().toString());
            jsonArray.put(jsonObject);
        }
        JSONObject json = new JSONObject();
        json.put("data", jsonArray);
        return json.toString();
    }

    @GET
    @Path("/getPessoasJuridicasTable")
    @Produces("application/json")
    public String getPessoasJuridicasTable(@QueryParam("usuario") String usuario) {
        PessoaJuridicaBO pessoaJuridicaBO = new PessoaJuridicaBO();
        EnderecoBO enderecoBO = new EnderecoBO();
        BemBO bemBO = new BemBO();
        JSONArray jsonArray = new JSONArray();
        UsuarioBO usuarioBO = new UsuarioBO();
        Instituicao instituicao = usuarioBO.findAutorizacaoByCPF(usuario).getInstituicaoFk();
        List<PessoaJuridica> pessoaJuridicaList = pessoaJuridicaBO.findAllActive(instituicao);
        for (PessoaJuridica pj : pessoaJuridicaList) {
            Endereco end = enderecoBO.findPJAddress(pj.getId());
            List<Bem> bemList = bemBO.findPJBens(pj.getId());
            String cnpj = pj.getCnpj() == null ? "-" : pj.getCnpj().substring(0, 2) + "." + pj.getCnpj().substring(2, 5) + "." + pj.getCnpj().substring(5, 8) + "/" + pj.getCnpj().substring(8, 12) + "-" + pj.getCnpj().substring(12);
            String nomeFantasia = pj.getNomeFantasia() == null ? "-" : pj.getNomeFantasia();
            String tipoEmpresarial = pj.getTipoEmpresarialFk() == null ? "-" : pj.getTipoEmpresarialFk().getTipo();
            String detalhes = pj.getInscricaoEstadual() + " " + pj.getInscricaoMunicipal() + " " + (pj.getSituacao() == null ? "" : "A".equals(pj.getSituacao().toString()) ? "Ativo" : "Inativo") + " "
                    + pj.getMotivoDaDesativacao() + " " + pj.getDataDeCriacao() + " " + pj.getGrupoEconomico() + " " + pj.getCnae() + " " + pj.getNire() + " " + pj.getAtividadePrincipal() + " "
                    + pj.getAtividadeSecundaria() + " " + end.getBairro() + " " + end.getCep() + " " + (end.getCidadeFk() == null ? "" : end.getCidadeFk().getNome()) + " "
                    + (end.getEstadoFk() == null ? "" : end.getEstadoFk().getUf()) + " " + end.getNumero() + " " + end.getComplemento() + " " + end.getEndereco();

            for (PessoaFisicaJuridica pfj : (List<PessoaFisicaJuridica>) pj.getPessoaFisicaJuridicaCollection()) {
                detalhes += " " + (pfj.getPessoaFisicaFk().getCpf() == null ? "" : pfj.getPessoaFisicaFk().getCpf().substring(0, 3) + "." + pfj.getPessoaFisicaFk().getCpf().substring(3, 6) + "." + pfj.getPessoaFisicaFk().getCpf().substring(6, 9) + "-" + pfj.getPessoaFisicaFk().getCpf().substring(9)) + " "
                        + pfj.getPessoaFisicaFk().getNome() + " " + (pfj.getFuncaoFk() == null ? "" : pfj.getFuncaoFk().getFuncao()) + " "
                        + pfj.getCapitalDeParticipacao() + " " + pfj.getDataDeInicio() + " " + pfj.getDataDeTermino();
            }
            for (PessoaJuridicaJuridica pjj : (List<PessoaJuridicaJuridica>) pj.getPessoaJuridicaJuridicaCollection()) {
                detalhes += " " + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(0, 2) + "." + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(2, 5) + "." + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(5, 8) + "/" + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(8, 12) + "-" + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(12) + " "
                        + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(0, 2) + "." + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(2, 5) + "." + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(5, 8) + "/" + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(8, 12) + "-" + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(12) + " "
                        + pjj.getPessoaJuridicaPrimariaFk().getNome() + " " + pjj.getPessoaJuridicaSecundariaFk().getNome() + " "
                        + pjj.getCapitalDeParticipacao() + " " + pjj.getDataDeInicio() + " " + pjj.getDataDeTermino();
            }
            for (PessoaJuridicaJuridica pjj : (List<PessoaJuridicaJuridica>) pj.getPessoaJuridicaJuridicaCollection1()) {
                detalhes += " " + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(0, 2) + "." + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(2, 5) + "." + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(5, 8) + "/" + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(8, 12) + "-" + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(12) + " "
                        + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(0, 2) + "." + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(2, 5) + "." + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(5, 8) + "/" + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(8, 12) + "-" + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(12) + " "
                        + pjj.getPessoaJuridicaPrimariaFk().getNome() + " " + pjj.getPessoaJuridicaSecundariaFk().getNome() + " "
                        + pjj.getCapitalDeParticipacao() + " " + pjj.getDataDeInicio() + " " + pjj.getDataDeTermino();
            }
            for (Bem bem : bemList) {
                detalhes += " " + bem.getDataDeAquisicao() + " " + bem.getDataDeTransferenciaOuExtincao() + " " + bem.getDescricao() + " " + bem.getEndereco() + " " + bem.getValor() + " " + (bem.getTipoBemFk() == null ? "" : bem.getTipoBemFk().getTipo());
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
            jsonObject.put("pj", pj.getId().toString());
            jsonArray.put(jsonObject);
        }
        JSONObject json = new JSONObject();
        json.put("data", jsonArray);
        return json.toString();
    }

    @GET
    @Path("/getProcessosJudiciaisTable")
    @Produces("application/json")
    public String getProcessosJudiciaisTable() {
        PessoaFisicaBO pessoaFisicaBO = new PessoaFisicaBO();
        PessoaJuridicaBO pessoaJuridicaBO = new PessoaJuridicaBO();
        EnderecoBO enderecoBO = new EnderecoBO();
        BemBO bemBO = new BemBO();
        ProcessoJudicialBO processoJudicialBO = new ProcessoJudicialBO();
        JSONArray jsonArray = new JSONArray();
        List<ProcessoJudicial> processoJudicialList = processoJudicialBO.findAllActive();
        for (ProcessoJudicial pjud : processoJudicialList) {
            Endereco end = new Endereco();
            List<Bem> bemList = new ArrayList<>();
            PessoaFisica pf = new PessoaFisica();
            PessoaJuridica pj = new PessoaJuridica();
            String detalhes = "";
            String executado = "";
            if (pjud.getExecutado().equals("PF")) {
                pf = pessoaFisicaBO.findPessoaFisica(pjud.getExecutadoFk());
                end = enderecoBO.findPFAddress(pf.getId());
                bemList = bemBO.findPFBens(pf.getId());
                executado = pf.getNome() + " - " + (pf.getCpf() == null ? "Sem CPF" : pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9));
                detalhes += (pf.getSexo() == null ? "" : "M".equals(pf.getSexo().toString()) ? "Masculino" : "Feminino") + " " + pf.getApelido() + " " + pf.getTituloDeEleitor() + " " + pf.getInss() + " "
                        + pf.getNomeDoPai() + " " + pf.getNomeDaMae() + " " + pf.getNomeDoConjuge() + " " + pf.getRg() + " " + pf.getRgOrgaoEmissor() + " " + (pf.getRgUfFk() == null ? "" : pf.getRgUfFk().getUf()) + " "
                        + pf.getObservacoes() + " " + (pf.getCidadeFk() == null ? "" : pf.getCidadeFk().getNome()) + " " + (pf.getEstadoFk() == null ? "" : pf.getEstadoFk().getUf()) + " "
                        + (pf.getEstadoCivilFk() == null ? "" : pf.getEstadoCivilFk().getSituacao()) + " " + (pf.getNacionalidadeFk() == null ? "" : pf.getNacionalidadeFk().getTipo()) + " "
                        + (pf.getPaisFk() == null ? "" : pf.getPaisFk().getNome()) + " " + (pf.getCidadeEleitoralFk() == null ? "" : pf.getCidadeEleitoralFk().getNome()) + " "
                        + (pf.getEstadoEleitoralFk() == null ? "" : pf.getEstadoEleitoralFk().getUf()) + " " + pf.getZona() + " " + pf.getSecao() + " " + pf.getEndereco() + " "
                        + pf.getLocal();
                for (PessoaFisicaJuridica pfj : (List<PessoaFisicaJuridica>) pf.getPessoaFisicaJuridicaCollection()) {
                    String cnpj = pfj.getPessoaJuridicaFk().getCnpj().substring(0, 2) + "." + pfj.getPessoaJuridicaFk().getCnpj().substring(2, 5) + "." + pfj.getPessoaJuridicaFk().getCnpj().substring(5, 8) + "/" + pfj.getPessoaJuridicaFk().getCnpj().substring(8, 12) + "-" + pfj.getPessoaJuridicaFk().getCnpj().substring(12);
                    detalhes += " " + cnpj + " " + pfj.getPessoaJuridicaFk().getNome() + " " + (pfj.getFuncaoFk() == null ? "" : pfj.getFuncaoFk().getFuncao()) + " " + pfj.getCapitalDeParticipacao() + " " + pfj.getDataDeInicio() + " " + pfj.getDataDeTermino();
                }
                for (PessoaFisicaFisica pff : (List<PessoaFisicaFisica>) pf.getPessoaFisicaFisicaCollection()) {
                    detalhes += " " + pff.getVinculoSocialFk().getVinculo() + " " + pff.getPessoaFisicaPrimariaFk().getNome() + " " + pff.getPessoaFisicaSecundariaFk().getNome() + " "
                            + (pff.getPessoaFisicaPrimariaFk().getCpf() == null ? "" : pff.getPessoaFisicaPrimariaFk().getCpf().substring(0, 3) + "." + pff.getPessoaFisicaPrimariaFk().getCpf().substring(3, 6) + "." + pff.getPessoaFisicaPrimariaFk().getCpf().substring(6, 9) + "-" + pff.getPessoaFisicaPrimariaFk().getCpf().substring(9)) + " "
                            + (pff.getPessoaFisicaSecundariaFk().getCpf() == null ? "" : pff.getPessoaFisicaSecundariaFk().getCpf().substring(0, 3) + "." + pff.getPessoaFisicaSecundariaFk().getCpf().substring(3, 6) + "." + pff.getPessoaFisicaSecundariaFk().getCpf().substring(6, 9) + "-" + pff.getPessoaFisicaSecundariaFk().getCpf().substring(9));
                }
                for (PessoaFisicaFisica pff : (List<PessoaFisicaFisica>) pf.getPessoaFisicaFisicaCollection1()) {
                    detalhes += " " + pff.getVinculoSocialFk().getVinculo() + " " + pff.getPessoaFisicaPrimariaFk().getNome() + " " + pff.getPessoaFisicaSecundariaFk().getNome() + " "
                            + (pff.getPessoaFisicaPrimariaFk().getCpf() == null ? "" : pff.getPessoaFisicaPrimariaFk().getCpf().substring(0, 3) + "." + pff.getPessoaFisicaPrimariaFk().getCpf().substring(3, 6) + "." + pff.getPessoaFisicaPrimariaFk().getCpf().substring(6, 9) + "-" + pff.getPessoaFisicaPrimariaFk().getCpf().substring(9)) + " "
                            + (pff.getPessoaFisicaSecundariaFk().getCpf() == null ? "" : pff.getPessoaFisicaSecundariaFk().getCpf().substring(0, 3) + "." + pff.getPessoaFisicaSecundariaFk().getCpf().substring(3, 6) + "." + pff.getPessoaFisicaSecundariaFk().getCpf().substring(6, 9) + "-" + pff.getPessoaFisicaSecundariaFk().getCpf().substring(9));
                }
                for (Bem bem : bemList) {
                    detalhes += " " + bem.getDataDeAquisicao() + " " + bem.getDataDeTransferenciaOuExtincao() + " " + bem.getDescricao() + " " + bem.getEndereco() + " " + bem.getValor() + " " + (bem.getTipoBemFk() == null ? "" : bem.getTipoBemFk().getTipo());
                }
            } else {
                pj = pessoaJuridicaBO.findPessoaJuridica(pjud.getExecutadoFk());
                end = enderecoBO.findPJAddress(pj.getId());
                bemList = bemBO.findPJBens(pj.getId());
                executado = pj.getNome() + " - " + pj.getCnpj().substring(0, 2) + "." + pj.getCnpj().substring(2, 5) + "." + pj.getCnpj().substring(5, 8) + "/" + pj.getCnpj().substring(8, 12) + "-" + pj.getCnpj().substring(12);
                detalhes += pj.getNomeFantasia() + " " + (pj.getTipoEmpresarialFk() == null ? "" : pj.getTipoEmpresarialFk().getTipo()) + " " + pj.getInscricaoEstadual() + " " + pj.getInscricaoMunicipal() + " "
                        + (pj.getSituacao() == null ? "" : "A".equals(pj.getSituacao().toString()) ? "Ativo" : "Inativo") + " " + pj.getMotivoDaDesativacao() + " "
                        + pj.getDataDeCriacao() + " " + pj.getGrupoEconomico() + " " + pj.getCnae() + " " + pj.getNire() + " " + pj.getAtividadePrincipal() + " " + pj.getAtividadeSecundaria();
                for (PessoaFisicaJuridica pfj : (List<PessoaFisicaJuridica>) pj.getPessoaFisicaJuridicaCollection()) {
                    String cpf = (pfj.getPessoaFisicaFk().getCpf() == null ? "" : pfj.getPessoaFisicaFk().getCpf().substring(0, 3) + "." + pfj.getPessoaFisicaFk().getCpf().substring(3, 6) + "." + pfj.getPessoaFisicaFk().getCpf().substring(6, 9) + "-" + pfj.getPessoaFisicaFk().getCpf().substring(9));
                    detalhes += " " + cpf + " " + pfj.getPessoaFisicaFk().getNome() + " " + (pfj.getFuncaoFk() == null ? "" : pfj.getFuncaoFk().getFuncao()) + " " + pfj.getCapitalDeParticipacao() + " " + pfj.getDataDeInicio() + " " + pfj.getDataDeTermino();
                }
                for (PessoaJuridicaJuridica pjj : (List<PessoaJuridicaJuridica>) pj.getPessoaJuridicaJuridicaCollection()) {
                    detalhes += " " + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(0, 2) + "." + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(2, 5) + "." + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(5, 8) + "/" + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(8, 12) + "-" + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(12) + " "
                            + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(0, 2) + "." + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(2, 5) + "." + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(5, 8) + "/" + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(8, 12) + "-" + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(12) + " "
                            + pjj.getPessoaJuridicaPrimariaFk().getNome() + " " + pjj.getPessoaJuridicaSecundariaFk().getNome() + " "
                            + pjj.getCapitalDeParticipacao() + " " + pjj.getDataDeInicio() + " " + pjj.getDataDeTermino();
                }
                for (PessoaJuridicaJuridica pjj : (List<PessoaJuridicaJuridica>) pj.getPessoaJuridicaJuridicaCollection1()) {
                    detalhes += " " + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(0, 2) + "." + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(2, 5) + "." + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(5, 8) + "/" + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(8, 12) + "-" + pjj.getPessoaJuridicaPrimariaFk().getCnpj().substring(12) + " "
                            + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(0, 2) + "." + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(2, 5) + "." + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(5, 8) + "/" + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(8, 12) + "-" + pjj.getPessoaJuridicaSecundariaFk().getCnpj().substring(12) + " "
                            + pjj.getPessoaJuridicaPrimariaFk().getNome() + " " + pjj.getPessoaJuridicaSecundariaFk().getNome() + " "
                            + pjj.getCapitalDeParticipacao() + " " + pjj.getDataDeInicio() + " " + pjj.getDataDeTermino();
                }
                for (Bem bem : bemList) {
                    detalhes += " " + bem.getDataDeAquisicao() + " " + bem.getDataDeTransferenciaOuExtincao() + " " + bem.getDescricao() + " " + bem.getEndereco() + " " + bem.getValor() + " " + (bem.getTipoBemFk() == null ? "" : bem.getTipoBemFk().getTipo());
                }
            }
            detalhes += " " + end.getBairro() + " " + end.getCep() + " " + (end.getCidadeFk() == null ? "" : end.getCidadeFk().getNome()) + " " + (end.getEstadoFk() == null ? "" : end.getEstadoFk().getUf()) + " "
                    + end.getNumero() + " " + end.getComplemento() + " " + end.getEndereco();
            detalhes += " " + pjud.getAtoProcessual() + " " + pjud.getDataDeInscricao() + " " + pjud.getDespachoInicial() + " " + pjud.getDespachoInicialDataDoAto() + " "
                    + pjud.getDiscriminacaoDoCreditoImposto() + " " + pjud.getDiscriminacaoDoCreditoMulta() + " " + pjud.getDistribuicao() + " " + pjud.getDistribuicaoDataDoAto() + " " + (pjud.getFatosGeradores() == null ? "" : pjud.getFatosGeradores().replace(",", "; ")) + " "
                    + pjud.getFundamentacao() + " " + pjud.getGrupoDeEspecializacao() + " " + pjud.getNotificacaoAdministrativa() + " " + pjud.getNotificacaoAdministrativaDataDoAto() + " " + pjud.getNumeroDoProcessoAnterior() + " "
                    + pjud.getOutrasInformacoesAtoProcessual() + " " + pjud.getOutrasInformacoesExecutado() + " " + pjud.getOutrasInformacoesProcesso() + " " + (pjud.getProcuradorFk() == null ? "" : pjud.getProcuradorFk().getNome()) + " "
                    + pjud.getRecurso() + " " + pjud.getValorAtualizado() + " " + pjud.getValorDaCausa() + " " + pjud.getVara() + " " + pjud.getVaraAnterior() + " " + (pjud.getTipoDeRecursoFk() == null ? "" : pjud.getTipoDeRecursoFk().getTipo());
            for (VinculoProcessual vp : pjud.getVinculoProcessualCollection()) {
                detalhes += " " + vp.getProcesso() + " " + vp.getTipoDeProcessoFk().getTipo();
            }

            detalhes = detalhes.replace("null", "");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("row-details", "<span class='row-details row-details-close'></span>");
            jsonObject.put("numero_do_processo", pjud.getNumeroDoProcesso());
            jsonObject.put("comarca", pjud.getComarca());
            jsonObject.put("numero_da_cda", pjud.getNumeroDaCda());
            jsonObject.put("executado", executado);
            jsonObject.put("delete", "<a class='button-delete' href='javascript:;'><i class='glyphicon glyphicon-remove' style='color:red'></i></a>");
            jsonObject.put("detalhes", detalhes);
            jsonObject.put("pjud", pjud.getId().toString());
            jsonArray.put(jsonObject);
        }
        JSONObject json = new JSONObject();
        json.put("data", jsonArray);
        return json.toString();
    }

    @GET
    @Path("/getMovimentacao")
    @Produces("application/json")
    public String getMovimentacao(@QueryParam("ano") Integer ano) {
        ChartsBO chartsBO = new ChartsBO();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (Integer i = 1; i <= 12; i++) {
            jsonObject.put("pf" + i, chartsBO.countPFByMonth(ano, i));
        }
        for (Integer i = 1; i <= 12; i++) {
            jsonObject.put("pj" + i, chartsBO.countPJByMonth(ano, i));
        }
        for (Integer i = 1; i <= 12; i++) {
            jsonObject.put("pjud" + i, chartsBO.countPJUDByMonth(ano, i));
        }
        jsonArray.put(jsonObject);

        return jsonArray.toString();
    }

    @GET
    @Path("/checkCNPJ")
    @Produces("application/json")
    public String checkCNPJ(@QueryParam("cnpj") String cnpj) {
        PessoaJuridicaBO pessoaJuridicaBO = new PessoaJuridicaBO();
        PessoaJuridica pessoaJuridica = pessoaJuridicaBO.findByCNPJ(cnpj);
        if (pessoaJuridica == null) {
            return "true";
        } else {
            return "false";
        }
    }

    @GET
    @Path("/checkCPF")
    @Produces("application/json")
    public String checkCPF(@QueryParam("cpf") String cpf) {
        PessoaFisicaBO pessoaFisicaBO = new PessoaFisicaBO();
        PessoaFisica pessoaFisica = pessoaFisicaBO.findByCPF(cpf);
        if (pessoaFisica == null) {
            return "true";
        } else {
            return "false";
        }
    }

    @GET
    @Path("/getArrecadacao")
    @Produces("application/json")
    public String getArrecadacao(@QueryParam("ano") Integer ano) {
        ChartsBO chartsBO = new ChartsBO();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (Integer i = 1; i <= 12; i++) {
            jsonObject.put("value" + i, chartsBO.sumPJUDValueBeforeMonth(ano, i));
        }
        for (Integer i = 1; i <= 12; i++) {
            jsonObject.put("arrecadacao" + i, chartsBO.sumPJUDArrecadacaoBeforeMonth(ano, i));
        }
        jsonObject.put("count", chartsBO.countPJUDValueBeforeMonth(ano, 12));
        jsonArray.put(jsonObject);

        return jsonArray.toString();
    }

    @GET
    @Path("/getPizza")
    @Produces("application/json")
    public String getPizza() {
        ChartsBO chartsBO = new ChartsBO();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Andamento", chartsBO.getPJUDSituations("Andamento"));
        jsonObject.put("Arquivado", chartsBO.getPJUDSituations("Arquivado"));
        jsonObject.put("Extinto", chartsBO.getPJUDSituations("Extinto"));
        jsonObject.put("Julgado", chartsBO.getPJUDSituations("Julgado"));
        jsonObject.put("Suspenso", chartsBO.getPJUDSituations("Suspenso"));
        jsonArray.put(jsonObject);

        return jsonArray.toString();
    }

    @GET
    @Path("/getLogs")
    @Produces("application/json")
    public String getLogs(@QueryParam("quantidade") Integer quantidade, @QueryParam("indice") Integer indice) {
        LogBO logBO = new LogBO();
        List<Log> logList = logBO.findLogEntities(quantidade, indice);
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < logList.size(); i++) {
            String message = loadMessage(logList.get(i));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", i);
            jsonObject.put("mensagem", message);
            jsonObject.put("idfk", logList.get(i).getIdFk());
            jsonObject.put("tabela", logList.get(i).getTabela());
            jsonObject.put("operacao", logList.get(i).getOperacao());
            jsonObject.put("data", TimestampUtils.getISO8601StringForDate(logList.get(i).getDataDeCriacao()).replace("Z", ""));
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    private String loadMessage(Log log) {
        PessoaFisicaBO pfBO = new PessoaFisicaBO();
        PessoaJuridicaBO pjBO = new PessoaJuridicaBO();
        ProcessoJudicialBO pjudBO = new ProcessoJudicialBO();
        PessoaJuridicaSucessaoBO pjsBO = new PessoaJuridicaSucessaoBO();
        String tabela = "";
        String operacao = "";
        String detalhes = "";
        if (log.getTabela().equals("PF")) {
            PessoaFisica pf = pfBO.findPessoaFisica(log.getIdFk());
            String cpf = pf.getCpf() == null ? "Sem CPF" : pf.getCpf().substring(0, 3) + "." + pf.getCpf().substring(3, 6) + "." + pf.getCpf().substring(6, 9) + "-" + pf.getCpf().substring(9);
            tabela += "<span class='feed-label'>Pessoa Física ";
            String info = pf.getNome() + " - " + cpf;
            info = info.length() >= 35 ? info.substring(0, 32) + "..." : info;
            detalhes += "<strong>" + info + "</strong>";
        } else if (log.getTabela().equals("PJ")) {
            PessoaJuridica pj = pjBO.findPessoaJuridica(log.getIdFk());
            String cnpj = pj.getCnpj().substring(0, 2) + "." + pj.getCnpj().substring(2, 5) + "." + pj.getCnpj().substring(5, 8) + "/" + pj.getCnpj().substring(8, 12) + "-" + pj.getCnpj().substring(12);
            tabela += "<span class='feed-label'>Pessoa Juridica ";
            String info = pj.getNome() + " - " + cnpj;
            info = info.length() >= 35 ? info.substring(0, 32) + "..." : info;
            detalhes += "<strong>" + info + "</strong>";
        } else if (log.getTabela().equals("PJUD")) {
            ProcessoJudicial pjud = pjudBO.findProcessoJudicial(log.getIdFk());
            tabela += "<span class='feed-label'>Processo Judicial ";
            detalhes += "<strong>" + pjud.getNumeroDoProcesso() + "</strong>";
        } else if (log.getTabela().equals("PJS")) {
            PessoaJuridicaSucessao pjs = pjsBO.findPessoaJuridicaSucessao(log.getIdFk());
            tabela += "<span class='feed-label'>Sucessão Empresarial ";
            String info = pjs.getPessoaJuridicaSucedidaFk().getNome() + " -> " + pjs.getPessoaJuridicaSucessoraFk().getNome();
            info = info.length() >= 35 ? info.substring(0, 32) + "..." : info;
            detalhes += "<strong>" + info + "</strong>";
        }
        if (log.getOperacao().equals('C')) {
            operacao += log.getTabela().equals("PJUD") ? "cadastrado: " : "cadastrada: ";
        } else if (log.getOperacao().equals('U')) {
            operacao += log.getTabela().equals("PJUD") ? "alterado: " : "alterada: ";
        } else if (log.getOperacao().equals('D')) {
            operacao += log.getTabela().equals("PJUD") ? "desativado: " : "desativada: ";
        }
        operacao += "</span>";
        return tabela + operacao + detalhes;
    }

    @GET
    @Path("/getSucessoes")
    @Produces("application/json")
    public String getSucessoes(@QueryParam("id") Integer id) {
        PessoaJuridicaSucessaoBO pessoaJuridicaSucessaoBO = new PessoaJuridicaSucessaoBO();
        List<PessoaJuridicaSucessao> pessoaJuridicaSucessaoList = new ArrayList<>();
        pessoaJuridicaSucessaoList.addAll(pessoaJuridicaSucessaoBO.findSucedidas(id));
        pessoaJuridicaSucessaoList.addAll(pessoaJuridicaSucessaoBO.findSucessoras(id));
        JSONArray jsonArray = new JSONArray();
        for (PessoaJuridicaSucessao pjs : pessoaJuridicaSucessaoList) {
            JSONObject jsonObject = new JSONObject();
            String sucedidaCnpj = pjs.getPessoaJuridicaSucedidaFk().getCnpj().substring(0, 2) + "." + pjs.getPessoaJuridicaSucedidaFk().getCnpj().substring(2, 5) + "." + pjs.getPessoaJuridicaSucedidaFk().getCnpj().substring(5, 8) + "/" + pjs.getPessoaJuridicaSucedidaFk().getCnpj().substring(8, 12) + "-" + pjs.getPessoaJuridicaSucedidaFk().getCnpj().substring(12);
            String sucessoraCnpj = pjs.getPessoaJuridicaSucessoraFk().getCnpj().substring(0, 2) + "." + pjs.getPessoaJuridicaSucessoraFk().getCnpj().substring(2, 5) + "." + pjs.getPessoaJuridicaSucessoraFk().getCnpj().substring(5, 8) + "/" + pjs.getPessoaJuridicaSucessoraFk().getCnpj().substring(8, 12) + "-" + pjs.getPessoaJuridicaSucessoraFk().getCnpj().substring(12);
            jsonObject.put("sucessao_id", pjs.getId());
            jsonObject.put("sucedida_id", pjs.getPessoaJuridicaSucedidaFk().getId());
            jsonObject.put("sucedida_nome", pjs.getPessoaJuridicaSucedidaFk().getNome());
            jsonObject.put("sucedida_cnpj", sucedidaCnpj);
            jsonObject.put("sucedida_status", pjs.getPessoaJuridicaSucedidaFk().getStatus());
            jsonObject.put("sucessora_id", pjs.getPessoaJuridicaSucessoraFk().getId());
            jsonObject.put("sucessora_nome", pjs.getPessoaJuridicaSucessoraFk().getNome());
            jsonObject.put("sucessora_cnpj", sucessoraCnpj);
            jsonObject.put("sucessora_status", pjs.getPessoaJuridicaSucessoraFk().getStatus());
            jsonObject.put("data_de_sucessao", pjs.getDataDeSucessao());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

//    private List<PessoaJuridicaSucessao> getSucedidasRecursivas(List<PessoaJuridicaSucessao> pjSucessaoList, Integer id) {
//        PessoaJuridicaSucessaoBO pessoaJuridicaSucessaoBO = new PessoaJuridicaSucessaoBO();
//        List<PessoaJuridicaSucessao> pjsDB = pessoaJuridicaSucessaoBO.findSucedidas(id);
//        if (pjsDB.isEmpty()) {
//            return pjSucessaoList;
//        } else {
//            pjSucessaoList.addAll(0,pjsDB);
//            return getSucedidasRecursivas(pjSucessaoList, pjsDB.get(0).getPessoaJuridicaSucedidaFk().getId());
//        }
//    }
}
