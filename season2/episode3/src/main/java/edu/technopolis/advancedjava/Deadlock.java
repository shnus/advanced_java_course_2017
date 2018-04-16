package edu.technopolis.advancedjava;

public class Deadlock {
    private static final Object FIRST_LOCK = new Object();
    private static final Object SECOND_LOCK = new Object();

    public static void main(String[] args) throws Exception {
        Thread ft = new Thread(Deadlock::first);
        Thread st = new Thread(Deadlock::second);
        ft.start();
        st.start();
        ft.join();
        st.join();
        //never going to reach this point
    }

    private static void first() {
        synchronized(FIRST_LOCK) {
            try {
                FIRST_LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            FIRST_LOCK.notify();
            synchronized(SECOND_LOCK) {
                //unreachable

            }
        }
    }

    private static void second() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //
        }
        //reverse order of monitors
        synchronized(SECOND_LOCK) {

            synchronized(FIRST_LOCK) {
                FIRST_LOCK.notify();
                try {
                    FIRST_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //unreachable

            }
        }

    }

}