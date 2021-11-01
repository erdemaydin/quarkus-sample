package org.acme.proxy

import org.acme.model.error.ErrorCode.COUNTRY_API_CLIENT_ERROR
import org.acme.model.error.ErrorCode.COUNTRY_API_SERVER_ERROR
import org.acme.model.proxy.Country
import org.acme.model.proxy.CountryApiError
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR

@RegisterProvider(CountryApiExceptionMapper::class)
@RegisterRestClient(configKey = "country-api")
@Path("/v3.1")
interface CountryProxy {

    @GET
    @Path("/all")
    @Throws(CountryApiError::class)
    fun findAll(): List<Country>

    @GET
    @Path("/name/{name}")
    @Throws(CountryApiError::class)
    fun searchByName(@PathParam("name") name: String): List<Country>
}


@ApplicationScoped
class CountryApiExceptionMapper : ResponseExceptionMapper<CountryApiError?> {
    override fun toThrowable(response: Response): CountryApiError? {
        val error: CountryApiError = response.readEntity(CountryApiError::class.java)
        error.code = takeErrorCode(response.statusInfo.family)
        throw error
    }

    private fun takeErrorCode(family: Response.Status.Family) =
        if (CLIENT_ERROR == family) COUNTRY_API_CLIENT_ERROR
        else COUNTRY_API_SERVER_ERROR
}