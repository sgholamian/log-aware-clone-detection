//,temp,sample_4753.java,2,19,temp,sample_2471.java,2,17
//,3
public class xxx {
public void dummy_method(){
int exitCode = child.waitFor();
if (exitCode != 0) {
StringBuilder errStr = new StringBuilder();
synchronized(childErrorLog) {
Iterator iter = childErrorLog.iterator();
while(iter.hasNext()){
errStr.append(iter.next());
errStr.append('\n');
}
}


log.info("child process exited with code");
}
}

};