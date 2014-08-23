/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import org.json.JSONArray;
import org.json.JSONObject;

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
    @Path("/pessoaFisica")
    @Produces("application/json")
    public String carregarPessoasFisicas(){
        System.out.println("ae");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonArray.put("<span class='row-details row-details-close'></span>");
        jsonArray.put("1");
        jsonArray.put("2");
        jsonArray.put("3");
        jsonArray.put("4");
        jsonArray.put("5");
        jsonArray.put("6");
        jsonArray.put("7");
        jsonObject.put("pessoaFisica",jsonArray);
        System.out.println(jsonArray);
        return jsonArray.toString();
    }
}
