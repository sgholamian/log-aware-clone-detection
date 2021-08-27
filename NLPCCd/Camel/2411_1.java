//,temp,sample_58.java,2,17,temp,sample_6456.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (timeout > 0) {
joinThread(thread, timeout);
}
if (!thread.isAlive()) {
continue;
}
uncooperativeThreads.add(thread);
if (stopUnresponsiveDaemonThreads) {
thread.stop();
} else {


log.info("thread will linger despite being asked to die via interruption");
}
}

};