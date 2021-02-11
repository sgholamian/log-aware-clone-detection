//,temp,sample_4855.java,2,17,temp,sample_4856.java,2,18
//,2
public class xxx {
public void dummy_method(){
try {
String line = errReader.readLine();
while((line != null) && !isInterrupted()) {
errMsg.append(line);
errMsg.append(System.getProperty("line.separator"));
line = errReader.readLine();
}
} catch(IOException ioe) {
if (!isTimedOut()) {
} else {


log.info("error reading the error stream due to shell command timeout");
}
}
}

};