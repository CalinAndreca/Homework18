package org.fasttrackit.Homework18;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
}
