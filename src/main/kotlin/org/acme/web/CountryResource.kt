package org.acme.web

import org.acme.model.proxy.CountryApiError
import org.acme.service.CountryService
import org.eclipse.microprofile.metrics.MetricUnits
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.metrics.annotation.Metered
import org.eclipse.microprofile.metrics.annotation.Timed
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/country")
class CountryResource(private val countryService: CountryService) {

    @GET
    @Throws(CountryApiError::class)
    @Counted(
        name = "count-countryResource-findAll",
        description = "How many times the findAll() has been invoked")
    @Timed(
        name = "timeFetch-countryResource-findAll",
        description = "How long it takes to invoke the findAll()",
        unit = MetricUnits.MILLISECONDS)
    @Metered(
        name = "metered-countryResource-findAll",
        description = "Measures throughput of findAll() method")
    fun findAll(): Response = Response.ok(countryService.findAll()).build()

    @GET
    @Path("/{name}")
    @Throws(CountryApiError::class)
    fun findByName(@PathParam("name") name: String): Response = Response.ok(countryService.findByName(name)).build()

    @GET
    @Path("/name/{name}")
    @Throws(CountryApiError::class)
    fun searchByName(@PathParam("name") name: String): Response = Response.ok(countryService.searchByName(name)).build()
}

@Provider
class CountryApiExceptionMapper: ExceptionMapper<CountryApiError> {
    override fun toResponse(error: CountryApiError?): Response {
        return Response.status(Response.Status.BAD_REQUEST).entity(error!!.toApiError()).build()
    }
}
