package org.acme


import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.acme.model.error.ErrorCode.COUNTRY_API_CLIENT_ERROR
import org.acme.web.CountryResource
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import javax.ws.rs.core.Response.Status.*

@TestHTTPEndpoint(CountryResource::class)
@QuarkusTest
class CountryResourceTest {
    @Test
    fun testFindAll_recordsFound_returnsRecordsWithSuccessStatus() {
        given()
            .`when`()
            .get()
            .then()
            .statusCode(OK.statusCode)
            .body("$.size()", Matchers.greaterThan(1))
            .body("[0].name.common", `is`("Malaysia"))
            .body(containsString("Turkey"))
    }

    @Test
    fun testFindByName_recordFound_returnsRecordWithSuccessStatus() {
        given()
            .`when`()["/turkey"]
            .then()
            .statusCode(OK.statusCode)
            .body("$.size()", `is`(1))
            .body("[0].name.common", `is`("Turkey"))
    }

    @Test
    fun testSearchByName_recordNotFound_clientThrowsException() {
        given()
            .`when`()["/name/asdadad"]
            .then()
            .statusCode(BAD_REQUEST.statusCode)
            .body(
                "code", `is`(COUNTRY_API_CLIENT_ERROR.value),
                "status", `is`(NOT_FOUND.statusCode),
                "message", `is`("Not Found")
            )
    }

    @Test
    fun testSearchByName_recordFound_returnsRecordWithSuccessStatus() {
        given()
            .`when`()["/name/turkey"]
            .then()
            .statusCode(OK.statusCode)
            .body("$.size()", `is`(1))
            .body("[0].name.common", `is`("Turkey"))
    }
}