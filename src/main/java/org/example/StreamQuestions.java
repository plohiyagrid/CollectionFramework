package org.example;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StreamQuestions {
    public static void main(String[] args) {
        List<Account> accounts = List.of(
                new Account("A001", "John Smith", List.of(
                        new Transaction("T001", "credit", 8000, LocalDate.of(2023, 9, 1)),
                        new Transaction("T002", "debit", 3000, LocalDate.of(2023, 9, 3)),
                        new Transaction("T003", "debit", 6000, LocalDate.of(2023, 9, 2))
                )),
                new Account("A002", "Alice Son", List.of(
                        new Transaction("T004", "debit", 4500, LocalDate.of(2023, 9, 1)),
                        new Transaction("T005", "credit", 12000, LocalDate.of(2023, 9, 3)),
                        new Transaction("T006", "credit", 15000, LocalDate.of(2023, 9, 3))
                )),
                new Account("A003", "Robert", List.of(
                        new Transaction("T007", "credit", 2000, LocalDate.of(2023, 9, 1)),
                        new Transaction("T008", "debit", 1000, LocalDate.of(2023, 9, 4))
                )),
                new Account("A004", "Martin Blaze", List.of(
                        new Transaction("T009", "credit", 5500, LocalDate.of(2023, 9, 4)),
                        new Transaction("T010", "debit", 7000, LocalDate.of(2023, 9, 3)),
                        new Transaction("T011", "debit", 2000, LocalDate.of(2023, 9, 4)),
                        new Transaction("T012", "credit", 9000, LocalDate.of(2023, 9, 4))
                ))
        );

        //1. Extract Transactions amount more than Rs 5000 or customer name is containing ‘JOHN’
        System.out.println("1. Extract Transactions amount more than Rs 5000 or customer name is containing ‘JOHN’ :");
        accounts.stream()
                .flatMap(account -> account.getTransactions().stream()
                        .filter(txn -> txn.getAmount() > 5000 || account.getCustomerName().toLowerCase().contains("john")))
                .map(Transaction::getTransactionId)
                .forEach(System.out::println);

        // 2. Print All Transactions done on each txnDate
        System.out.println("2. Print All Transactions done on each txnDate :");
        accounts.stream().flatMap(acc -> acc.getTransactions().stream())
                .collect(Collectors.groupingBy(Transaction::getTxnDate)).forEach((date, txns) -> {
                            System.out.println(date + " : ");
                            txns.stream().map(Transaction::getTransactionId).forEach(System.out::println);
                        }
                );

        //3. Store customers into List who made more than 2 Transactions on same day
        System.out.println("3. Store customers into List who made more than 2 Transactions on same day :");
        List<String> costumers = accounts.stream().filter(
                acc -> acc.getTransactions().stream().collect(Collectors.groupingBy(Transaction::getTxnDate, Collectors.counting()))
                        .values().stream().anyMatch(x -> x >= 2)).map(Account::getCustomerName).toList();

        System.out.println(costumers);

        //4. Calculate Total Transaction Amount for each Customer
        System.out.println("4. Calculate Total Transaction Amount for each Customer :");
        accounts.stream().collect(Collectors.toMap(Account::getCustomerName,
                        account -> account.getTransactions().stream().map(Transaction::getAmount).reduce(Double::sum)))
                .forEach((x, y) -> System.out.println(x + ": " + y.get()));

        //5. Find total transactions amount value of 2 highest valued credit type transactions
        System.out.println("5. Find total transactions amount value of 2 highest valued credit type transactions :");

        accounts.stream()
                .flatMap(account -> account.getTransactions().stream())
                .filter(txn -> txn.getType().equalsIgnoreCase("credit"))
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .limit(2)
                .map(Transaction::getAmount)
                .forEach(System.out::println);


    }
}

class Transaction {
    private final String transactionId;
    private final String type; // debit or credit
    private final double amount;
    private final LocalDate txnDate;


    public Transaction(String transactionId, String type, double amount, LocalDate txnDate) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.txnDate = txnDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getTxnDate() {
        return txnDate;
    }
}

class Account {
    private final String accountId;
    private String customerName;
    private List<Transaction> transactions;

    public Account(String accountId, String customerName, List<Transaction> transactions) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.transactions = transactions;
    }


    public String getAccountId() {
        return accountId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}



