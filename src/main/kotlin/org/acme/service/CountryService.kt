package org.acme.service

import io.quarkus.cache.CacheResult
import org.acme.client.CountryClient
import org.acme.model.client.Country
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.enterprise.context.ApplicationScoped
import javax.validation.constraints.NotNull

@ApplicationScoped
class CountryService(@RestClient private val countryClient: CountryClient) {

    @CacheResult(cacheName = "countries")
    fun findAll(): List<Country> = countryClient.findAll()

    fun findByName(@NotNull name: String): List<Country> = findAll().filter { it.name.common.lowercase().contains(name.lowercase()) }

    fun searchByName(@NotNull name: String) = countryClient.searchByName(name)
}