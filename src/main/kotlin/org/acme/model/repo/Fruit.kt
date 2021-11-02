package org.acme.model.repo

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Fruit(
    @Id
    @Column(name = "id")
    var id: Long,
    @Column(name = "name")
    var name: String
)
