/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author omp
 * This is the monitor. It provides two methods
 * to change the value of a shared variable: count.
 * The exclusive access is granted by a ReentrantLock.
 */
class MonitorReentrantLock implements IIncrement, IDecrement {
    private final ReentrantLock rl;
    // create condition to allow Add Threads to wait while count >= 10.
    private final Condition increment;
    // create condition to allow Dec Thread to wait whiel count <5.
    private final Condition decrement;
    private int count =0;
    
    @Override
    public void increment(int thread) {
        rl.lock();
        try {
            System.out.println("Before increment: (" + thread + "): " + count);
            // do not increment if count >= 10
            while ( count >= 10)
                // wait for Dec Thread to decrement count
                increment.await();
            count++;
            System.out.println("After increment: (" + thread + "): " + count);
            // wake Thread waiting in codition decrement. Why signal and not signalAll?
            decrement.signal();
        } catch (Exception ex) {}
        finally {
            // always inside a finally block.
            rl.unlock();
        }
    }
    @Override
    public void decrement() {
        rl.lock();
        try {
            System.out.println("     Enter decrement: " + count);
            try {
                // do not derement if count <5
                while (count < 5) {
                    // wait for new values of count
                    decrement.await();
                    System.out.println("     Trying to decrement: " + count);
                }
            } catch( Exception ex) {}
            count -=5;
            System.out.println("     After decrement: " + count);
            // why signalAll?
            increment.signalAll();
        }
        finally {
            rl.unlock();
        }
    }
    // create lock and conditions
    public MonitorReentrantLock() {
        rl = new ReentrantLock(true);
        increment = rl.newCondition();
        decrement = rl.newCondition();
    }
    
}
