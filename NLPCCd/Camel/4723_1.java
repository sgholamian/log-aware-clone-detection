//,temp,sample_6454.java,2,12,temp,sample_56.java,2,12
//,2
public class xxx {
private void terminateThreads(ThreadGroup threadGroup) {
long startTime = System.currentTimeMillis();
Set<Thread> uncooperativeThreads = new HashSet<Thread>();
for (Collection<Thread> threads = getActiveThreads(threadGroup); !threads.isEmpty(); threads = getActiveThreads(threadGroup), threads .removeAll(uncooperativeThreads)) {
for (Thread thread : threads) {


log.info("interrupting thread");
}
}
}

};