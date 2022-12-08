package test.bundle.project.backend.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.OffsetDateTime

@Entity
data class Account(
    @Id
    val id: Long? = null,
    var amount: Float? = 0F,
    var updated: OffsetDateTime? = OffsetDateTime.now()
) {
    fun deposit(amount: Float) {
        this.amount = this.amount?.plus(amount)
        updated = OffsetDateTime.now()
    }
}
