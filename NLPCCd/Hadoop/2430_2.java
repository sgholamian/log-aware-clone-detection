//,temp,sample_7793.java,2,17,temp,sample_1502.java,2,19
//,3
public class xxx {
public void dummy_method(){
synchronized (AbstractLivelinessMonitor.this) {
Iterator<Map.Entry<O, Long>> iterator = running.entrySet().iterator();
long currentTime = clock.getTime();
while (iterator.hasNext()) {
Map.Entry<O, Long> entry = iterator.next();
O key = entry.getKey();
long interval = getExpireInterval(key);
if (currentTime > entry.getValue() + interval) {
iterator.remove();
expire(key);


log.info("expired timed out after secs");
}
}
}
}

};