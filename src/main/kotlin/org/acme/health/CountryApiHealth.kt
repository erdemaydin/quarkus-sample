package org.acme.health

import io.smallrye.health.checks.UrlHealthCheck
import org.acme.client.CountryClient
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.health.HealthCheck
import org.eclipse.microprofile.health.HealthCheckResponse
import org.eclipse.microprofile.health.Liveness
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@Liveness
class CountryProxyHealth(@RestClient private val countryClient: CountryClient) : HealthCheck {
    override fun call(): HealthCheckResponse {
        countryClient.searchByName("turkey")
        return HealthCheckResponse.named("Countries API!").up().build()
    }
}

@ApplicationScoped
class CountryProxyURL {
    @Inject
    @ConfigProperty(name = "country-api/mp-rest/url")
    val message: String? = ""

    @Liveness
    fun url(): HealthCheck = UrlHealthCheck("$message/v3.1/name/turkey").name("Api check uri")
}