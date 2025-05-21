import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount implements Runnable {
    private double balance;
    private BankAccount destinationAccount;
    private String accountName;
    private Lock lock = new ReentrantLock();

    public BankAccount(double initialBalance, String accountName) {
        this.balance = initialBalance;
        this.accountName = accountName;
    }

    public void setDestinationAccount(BankAccount destinationAccount){
        this.destinationAccount = destinationAccount;
    }

//    public synchronized double getBalance() {
//        return balance;
//    }

    public double getBalance() {
        return balance;
    }

//    public synchronized void setBalance(double newBalance) {
//        this.balance = newBalance;
//    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }


    public String getName(){
        return accountName;
    }

//    public synchronized void transferSync(double amountToTransfer) {
//        balance -= amountToTransfer;
//        System.out.println("Account " + accountName + "  transferred " + amountToTransfer + destinationAccount.getName());
//
//        double dBalance = destinationAccount.getBalance();
//        destinationAccount.setBalance(dBalance += amountToTransfer);
//        System.out.println("Destination Account balance: " + dBalance);
//    }

    public void transferLocks(double amountToTransfer) throws InterruptedException {
        this.lock.lock();
        balance -= amountToTransfer;
        System.out.println("Lock A acquired. Account " + accountName + "  transferred " + amountToTransfer + destinationAccount.getName());
        Thread.sleep(50);

        destinationAccount.lock.lock();
        System.out.println("Lock B acquired");
        double dBalance = destinationAccount.getBalance();
        destinationAccount.setBalance(dBalance += amountToTransfer);
        destinationAccount.lock.unlock();
        System.out.println("Destination Account balance: " + dBalance);
        this.lock.unlock();
    }


    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            try {
                transferLocks(random.nextDouble(100.00));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Account final balance " + this.getBalance());
    }

}
