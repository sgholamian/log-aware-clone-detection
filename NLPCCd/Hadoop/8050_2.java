//,temp,sample_2670.java,2,15,temp,sample_7119.java,2,16
//,3
public class xxx {
public void dummy_method(){
final Thread toInterrupt = Thread.currentThread();
Thread interrupter = new Thread() {
public void run() {
try {
Thread.sleep(120*1000);
toInterrupt.interrupt();
} catch (InterruptedException ie) {}
}
};
job.submit();


log.info("starting thread to interrupt main thread in minutes");
}

};