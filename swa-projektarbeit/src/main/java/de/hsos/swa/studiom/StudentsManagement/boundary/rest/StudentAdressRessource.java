package de.hsos.swa.studiom.StudentsManagement.boundary.rest;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.hsos.swa.studiom.StudentsManagement.boundary.dto.AdressDTO;
import de.hsos.swa.studiom.StudentsManagement.control.AddressService;
import de.hsos.swa.studiom.StudentsManagement.entity.Adress;
import de.hsos.swa.studiom.StudentsManagement.entity.Student;
import de.hsos.swa.studiom.StudentsManagement.gateway.StudentRepository;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/student/{matNr}/adress")
@ApplicationScoped
public class StudentAdressRessource {

    @Inject
    StudentRepository service;

    @GET
    public Response getAdress(@PathParam("matNr") int matNr) {
        Optional<Adress> opt = service.getAdress(matNr);
        if (opt.isPresent()) {
            return Response.ok(AdressDTO.Converter.toDto(opt.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @PUT
    public Response createAdress(@PathParam("matNr") int matNr, AdressDTO adress) {
        Optional<Adress> opt = service.createAdress(matNr, AdressDTO.Converter.toAdress(adress));
        if (opt.isPresent()) {
            return Response.ok(AdressDTO.Converter.toDto(opt.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    public Response changeAdress(@PathParam("matNr") int matNr, AdressDTO adress) {
        Optional<Adress> opt = service.changeAdress(matNr, AdressDTO.Converter.toAdress(adress));
        if (opt.isPresent()) {
            return Response.ok(AdressDTO.Converter.toDto(opt.get())).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @DELETE
    public Response deleteAdress(@PathParam("matNr") int matNr) {
        boolean deleted = service.deleteAdress(matNr);
        if (deleted) {
            return Response.ok().build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }
}
