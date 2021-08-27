//,temp,sample_28.java,2,11,temp,sample_1524.java,2,11
//,2
public class xxx {
public void stop() throws Exception {
int running = runningMessageListeners.get();
if (running <= 0) {
super.stop();
} else {


log.info("there are still running message listeners cannot stop endpoint");
}
}

};