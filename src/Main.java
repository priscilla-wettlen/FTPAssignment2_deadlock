public class Main {
    public static void main(String[] args) {
        BankAccount accountA = new BankAccount(10000.00, "Account A");
        BankAccount accountB = new BankAccount(20000.00, "Account B");
        accountA.setDestinationAccount(accountB);
        accountB.setDestinationAccount(accountA);

        Bankomat bankomat = new Bankomat(accountA, accountB);

        bankomat.createTransaction();


    }
}
