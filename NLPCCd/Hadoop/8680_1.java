//,temp,sample_4670.java,2,16,temp,sample_4665.java,2,16
//,3
public class xxx {
public void dummy_method(){
final AtomicReference<Throwable> err = new AtomicReference<Throwable>();
Thread t = new Thread() {
public void run() {
try {
stm.close();
} catch (Throwable t) {
err.set(t);
}
}};
t.start();


log.info("waiting for close to get to latch");
}

};