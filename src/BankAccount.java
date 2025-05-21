import java.util.Random;

public class BankAccount implements Runnable {
    private double bankAccountBBalance;
    private double balance;
    private BankAccount destinationAccount;
    private String accountName;

    public BankAccount(double initialBalance, String accountName) {
        this.balance = initialBalance;
        this.accountName = accountName;
    }

    public void setDestinationAccount(BankAccount destinationAccount){
        this.destinationAccount = destinationAccount;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public String getName(){
        return accountName;
    }

    public void transfer(double amountToTransfer) {
        balance -= amountToTransfer;
        System.out.println("Account " + accountName + "  transferred " + amountToTransfer + destinationAccount.getName());

        double dBalance = destinationAccount.getBalance();
        destinationAccount.setBalance(dBalance += amountToTransfer);
        System.out.println("Destination Account balance: " + dBalance);
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            transfer(random.nextDouble(100.00));
        }

        System.out.println("Account final balance " + this.getBalance());
    }

}
