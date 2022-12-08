package test.bundle.project.backend.controller

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import test.bundle.project.backend.dto.DepositAccountDto
import test.bundle.project.backend.entity.Account
import test.bundle.project.backend.repository.AccountRepository

@RestController
@RequestMapping("/account")
@CrossOrigin(maxAge = 3600)
class AccountController(
    private val accountRepository: AccountRepository
) {

    @PostMapping("/deposit")
    @ResponseBody
    @Transactional
    fun deposit(@RequestBody dto: DepositAccountDto): Account {
        val account = accountRepository.findById(dto.accountId)
            .orElse(Account(dto.accountId))
        account.deposit(dto.amount)
        return accountRepository.save(account)
    }
}
