public class Bankomat {
    private BankAccount accountA;
    private BankAccount accountB;
    private Thread a1Thread;
    private Thread a2Thread;

    public Bankomat(BankAccount accountA, BankAccount accountB) {
        this.accountA = accountA;
        this.accountB = accountB;

    }

    public void createTransaction(){
        a1Thread = new Thread(accountA);
        a2Thread = new Thread(accountB);

        a1Thread.start();
        a2Thread.start();


        try {
            a1Thread.join();
            a2Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
