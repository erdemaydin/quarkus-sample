package org.acme.service

import io.quarkus.cache.CacheResult
import org.acme.proxy.CountryProxy
import org.acme.model.proxy.Country
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.enterprise.context.ApplicationScoped
import javax.validation.constraints.NotNull

@ApplicationScoped
class CountryService(@RestClient private val countryProxy: CountryProxy) {

    @CacheResult(cacheName = "countries")
    fun findAll(): List<Country> = countryProxy.findAll()

    fun findByName(@NotNull name: String): List<Country> = findAll().filter { it.name.common.lowercase().contains(name.lowercase()) }

    fun searchByName(@NotNull name: String) = countryProxy.searchByName(name)
}