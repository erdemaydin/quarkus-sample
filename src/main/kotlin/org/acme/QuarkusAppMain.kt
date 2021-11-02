package org.acme

import io.quarkus.runtime.annotations.QuarkusMain
import kotlin.jvm.JvmStatic
import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication

@QuarkusMain
object QuarkusAppMain {
    @JvmStatic
    fun main(args: Array<String>) {
        println("App Main is running!")
        Quarkus.run(ApiMain::class.java, *args)
    }

    class ApiMain : QuarkusApplication {
        override fun run(vararg args: String): Int {
            println("Api Main is running!...")
            Quarkus.waitForExit()
            return 0
        }
    }
}