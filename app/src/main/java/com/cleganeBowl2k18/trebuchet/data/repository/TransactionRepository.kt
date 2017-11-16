package com.cleganeBowl2k18.trebuchet.data.repository

import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionCreator
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionResolver
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.network.TransactionService
import com.cleganeBowl2k18.trebuchet.presentation.view.FakeTransactionFactory
import io.reactivex.Observable

/**
 * Facilitates Transaction API calls
 */
class TransactionRepository(private val transactionService: TransactionService) {

    fun fakeAllTransactionsForUser(userId: Long): Observable<List<Transaction>> {
        var transactionFactory : FakeTransactionFactory = FakeTransactionFactory()

        var transaction1 = transactionFactory.generateTransaction("Scuba Gear", 15999, "Scuba Guys")
        var transaction2 = transactionFactory.generateTransaction("Expedition to Cape Cod", 145999, "Scuba Guys")
        var transaction3 = transactionFactory.generateTransaction("Mr. Fluffles food", 4800, "cat ladyz")
        var transaction4 = transactionFactory.generateTransaction("Beer", 2000000, "fer tha boys")

        var transactionList : List<Transaction> = listOf(transaction1, transaction2, transaction3, transaction4)
        return Observable.just(transactionList)
    }

    fun createTransaction(transaction: TransactionCreator): Observable<TransactionReceiver> {
        return transactionService.createTransaction(transaction)
    }

    fun resolveTransaction(transactionResolver: TransactionResolver): Observable<TransactionReceiver> {
        return transactionService.resolveTransaction(transactionResolver)
    }

}
