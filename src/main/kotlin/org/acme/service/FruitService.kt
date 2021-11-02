package org.acme.service

import org.acme.model.repo.Fruit
import org.acme.repo.FruitRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FruitService(private val fruitRepository: FruitRepository) {

    fun getAll(): List<Fruit> = fruitRepository.listAll()

    fun getById(id: Long): Fruit? = fruitRepository.findById(id)

    fun getByName(name: String): Fruit? = fruitRepository.find("name", name).singleResult()

    fun create(fruit: Fruit): Boolean {
        fruitRepository.persistAndFlush(fruit)
        return fruitRepository.isPersistent(fruit)
    }

    fun delete(id: Long): Boolean = fruitRepository.deleteById(id)
}