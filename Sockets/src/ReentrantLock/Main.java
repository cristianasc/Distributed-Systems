/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ReentrantLock;
/**
 *
 * @author omp
 */
public class Main {
    
    
    public static void main(String[] args){
        
        int nIncThreads = 5;
        MyThreadAdd[] myThreadAdd = new MyThreadAdd[nIncThreads];
        
        MonitorReentrantLock monitor = new MonitorReentrantLock();
        for (int i=0; i<nIncThreads; i++) {
            // create instance of class extending Thread
            myThreadAdd[i] = new MyThreadAdd(i+1, (IIncrement) monitor);
            // launch thread
            myThreadAdd[i].start();
        }
        MyThreadDecrement tDecrement = new MyThreadDecrement((IDecrement) monitor);
        tDecrement.start(); 
        try {
            for (int i =0; i<nIncThreads; i++)
                // wait while the thread does not die
                myThreadAdd[i].join();
            tDecrement.join();
        } catch(Exception ex) {}
        
    }
           
}
