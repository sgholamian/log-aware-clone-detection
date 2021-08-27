//,temp,sample_2797.java,2,11,temp,sample_2911.java,2,13
//,3
public class xxx {
public synchronized Service addAndAcquire(Key key, Service service) {
BlockingQueue<Service> entry = pool.get(key);
if (entry == null) {
entry = new ArrayBlockingQueue<Service>(capacity);
pool.put(key, entry);
}


log.info("addandacquire key service");
}

};