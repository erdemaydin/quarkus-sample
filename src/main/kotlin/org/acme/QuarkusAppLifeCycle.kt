package org.acme

import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.Startup
import io.quarkus.runtime.StartupEvent
import org.jboss.logging.Logger
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class QuarkusAppLifecycle {
    fun onStart(@Observes event: StartupEvent) = log.info("Application is starting!...")
    fun onStop(@Observes event: ShutdownEvent) = log.info("Application is stopping!...")

    companion object {
        private val log = Logger.getLogger(QuarkusAppLifecycle::class.java)
    }
}

@Startup
class AppStartUp {
    @PostConstruct
    fun init() = log.info("Application is start running!...")

    companion object {
        private val log = Logger.getLogger(AppStartUp::class.java)
    }
}