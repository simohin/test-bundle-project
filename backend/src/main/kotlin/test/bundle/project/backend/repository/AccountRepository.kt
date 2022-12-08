package test.bundle.project.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import test.bundle.project.backend.entity.Account

@Repository
interface AccountRepository : JpaRepository<Account, Long> {

    @Modifying
    @Query("UPDATE Account a set a.amount = a.amount + :amount WHERE a.id = :accountId")
    fun deposit(accountId: Long, amount: Float)
}
