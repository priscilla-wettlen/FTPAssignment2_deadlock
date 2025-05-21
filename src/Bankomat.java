import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bankomat {
    private BankAccount bankAccountA;
    private BankAccount bankAccountB;
    private Thread threadA;
    private Thread threadB;

    public Bankomat(BankAccount bankAccountA, BankAccount bankAccountB) {
        this.bankAccountA = bankAccountA;
        this.bankAccountB = bankAccountB;
    }

    public void initiateTreads(){
        threadA = new Thread(bankAccountA);
        threadB = new Thread(bankAccountB);
        threadA.start();
        threadB.start();
    }


}
