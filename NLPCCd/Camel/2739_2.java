//,temp,sample_2798.java,2,9,temp,sample_2799.java,2,11
//,3
public class xxx {
public synchronized Service acquire(Key key) {
BlockingQueue<Service> services = pool.get(key);
if (services == null || services.isEmpty()) {
return null;
}
Service answer = services.poll();


log.info("acquire service");
}

};