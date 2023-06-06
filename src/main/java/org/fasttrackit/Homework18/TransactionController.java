package org.fasttrackit.Homework18;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")

public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") Long id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        Transaction newTransaction = transactionService.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable("id") Long id,
            @RequestBody Transaction updatedTransaction
    ) {
        Transaction transaction = transactionService.updateTransaction(id, updatedTransaction);
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reports/type")
    public ResponseEntity<Map<String, List<Transaction>>> getTypeReport() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        Map<String, List<Transaction>> typeReport = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType));
        return ResponseEntity.ok(typeReport);
    }

    @GetMapping("/reports/product")
    public ResponseEntity<Map<String, List<Transaction>>> getProductReport() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        Map<String, List<Transaction>> productReport = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getProduct));
        return ResponseEntity.ok(productReport);
    }
}
