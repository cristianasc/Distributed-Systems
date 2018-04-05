/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ReentrantLock;

/**
 *
 * @author omp
 * This category of Thread increments count by invoking the 
 * monitor's increment method. 
 */
public class MyThreadAdd extends Thread {

    private final int id;
    private final IIncrement monitor;
    
    // Monitors(interfaces) are passed as arguments
    public MyThreadAdd(int id, IIncrement monitor) {
        this.id = id;
        this.monitor=monitor;
    }
    // the Thread lives inside the run method
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1);
            } catch ( Exception ex ) {}
            // increment count
            monitor.increment(id);
        }   
    } 
}
