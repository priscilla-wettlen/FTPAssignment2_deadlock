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

//    public void transferLocks(double amountToTransfer) throws InterruptedException {
//        this.lock.lock();
//        balance -= amountToTransfer;
//        System.out.println("Lock acquired. Account " + accountName + "  transferred " + amountToTransfer + " to " + destinationAccount.getName());
//        Thread.sleep(50);
//
//        destinationAccount.lock.lock();
//        System.out.println("Lock B acquired");
//        double dBalance = destinationAccount.getBalance();
//        destinationAccount.setBalance(dBalance += amountToTransfer);
//        destinationAccount.lock.unlock();
//        System.out.println("Destination Account balance: " + dBalance);
//        this.lock.unlock();
//    }

    public void transferNoDeadlock() throws InterruptedException {
        Random random = new Random();
        criticalSectionSendingAccount(random.nextDouble(1000.00));
        criticalSectionReceivingAccount(random.nextDouble(1200.00));
    }

    public void criticalSectionSendingAccount(double amountToTransfer) throws InterruptedException {
        this.lock.lock();
        balance -= amountToTransfer;
        System.out.println("Lock acquired by " + accountName + ". Account " + accountName + "  transferred " + amountToTransfer + " to " + destinationAccount.getName());
        Thread.sleep(50);
        this.lock.unlock();
    }

    public void criticalSectionReceivingAccount(double amountToTransfer) throws InterruptedException {
        destinationAccount.lock.lock();
        System.out.println("Lock acquired by " + destinationAccount.getName());
        double dBalance = destinationAccount.getBalance();
        destinationAccount.setBalance(dBalance += amountToTransfer);
        Thread.sleep(50);
        destinationAccount.lock.unlock();
        System.out.println(destinationAccount.getName() + " balance: " + dBalance);

    }


    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            try {
                //transferLocks(random.nextDouble(100.00));
                transferNoDeadlock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Account final balance " + this.getBalance());
    }

}
