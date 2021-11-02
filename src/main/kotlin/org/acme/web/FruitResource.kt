package org.acme.web

import org.acme.model.repo.Fruit
import org.acme.service.FruitService
import java.net.URI
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.BAD_REQUEST
import javax.ws.rs.core.Response.Status.NOT_FOUND

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/fruit")
class FruitResource(private val fruitService: FruitService) {

    @GET
    fun getAll(): Response = Response.ok(fruitService.getAll()).build()

    @GET
    @Path("{id}")
    fun getById(@PathParam("id") id: Long): Response {
        val fruit = fruitService.getById(id)
        return if (fruit != null) Response.ok(fruit).build()
        else Response.status(NOT_FOUND).build()
    }

    @GET
    @Path("name/{name}")
    fun getByName(@PathParam("name") name: String): Response {
        val fruit = fruitService.getByName(name)
        return if (fruit != null) Response.ok(fruit).build()
        else Response.status(NOT_FOUND).build()
    }

    @POST
    @Transactional
    fun create(fruit: Fruit): Response {
        return if (fruitService.create(fruit)) Response.created(URI.create("/fruit/${fruit.id}")).build()
        else Response.status(NOT_FOUND).build()
    }

    @DELETE
    @Path("{id}")
    @Transactional
    fun delete(@PathParam("id") id: Long): Response {
        return if (fruitService.delete(id)) Response.noContent().build()
        else Response.status(BAD_REQUEST).build()
    }
}