package org.acme.repo

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.acme.model.repo.Fruit
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FruitRepository : PanacheRepository<Fruit>