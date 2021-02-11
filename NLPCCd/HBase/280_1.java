//,temp,sample_3673.java,2,17,temp,sample_4664.java,2,16
//,3
public class xxx {
public void dummy_method(){
try {
final long startTime = EnvironmentEdgeManager.currentTime();
for (int i = 0; isAlive(); ++i) {
sendStopSignal();
join(250);
if (i > 0 && (i % 8) == 0) {
interrupt();
}
}
} catch (InterruptedException e) {


log.info("join wait got interrupted");
}
}

};