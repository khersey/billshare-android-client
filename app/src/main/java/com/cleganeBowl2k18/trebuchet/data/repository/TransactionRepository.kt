package com.cleganeBowl2k18.trebuchet.data.repository

import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import com.cleganeBowl2k18.trebuchet.data.network.TransactionService
import com.cleganeBowl2k18.trebuchet.presentation.view.FakeTransactionFactory
import io.reactivex.Observable

/**
 * Created by khersey on 2017-10-20.
 */
class TransactionRepository(private val transactionService: TransactionService) {

    fun allTransactionsForUser(userId: Long): Observable<List<Transaction>> = transactionService.getTransactionsByUser(userId)

    fun fakeAllTransactionsForUser(userId: Long): Observable<List<Transaction>> {
        var transactionFactory : FakeTransactionFactory = FakeTransactionFactory()

        var transaction1 = transactionFactory.generateTransaction("Scuba Gear", 159.99, "Scuba Guys")
        var transaction2 = transactionFactory.generateTransaction("Expedition to Cape Cod", 1459.99, "Scuba Guys")
        var transaction3 = transactionFactory.generateTransaction("Mr. Fluffles food", 48.00, "cat ladyz")
        var transaction4 = transactionFactory.generateTransaction("Beer", 20000.00, "fer tha boys")

        var transactionList : List<Transaction> = listOf(transaction1, transaction2, transaction3, transaction4)
        return Observable.just(transactionList)
    }

}
