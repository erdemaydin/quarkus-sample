package org.acme.health

import io.smallrye.health.checks.UrlHealthCheck
import org.acme.proxy.CountryProxy
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.health.HealthCheck
import org.eclipse.microprofile.health.HealthCheckResponse
import org.eclipse.microprofile.health.Liveness
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.enterprise.context.ApplicationScoped

@Liveness
class CountryProxyHealth(@RestClient private val countryProxy: CountryProxy) : HealthCheck {
    override fun call(): HealthCheckResponse {
        countryProxy.searchByName("turkey")
        return HealthCheckResponse.named("Countries API!").up().build()
    }
}

@ApplicationScoped
class CountryProxyURL {
    @ConfigProperty(name = "country-api/mp-rest/url")
    var message: String? = null

    @Liveness
    fun url(): HealthCheck = UrlHealthCheck("$message/v3.1/name/turkey").name("Api check uri")
}