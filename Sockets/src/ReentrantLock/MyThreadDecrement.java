/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ReentrantLock;

/**
 *
 * @author omp
 * This Thread decremsnts couunt by involking the
 * monitor's decrement method
 */
class MyThreadDecrement extends Thread {

    private final IDecrement monitor;
    
    // Monitors(interfaces) are passed as arguments
    public MyThreadDecrement(IDecrement monitor) {
        this.monitor=monitor;
    }
    // the Thread lives inside the run method
    @Override
    public void run() {
        
        while (true) {
            monitor.decrement();
            try {
                Thread.sleep(0);
            } catch (Exception e) {}
        }
    }
    
    
    
    
}
