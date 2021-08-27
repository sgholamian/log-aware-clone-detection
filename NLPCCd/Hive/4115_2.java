//,temp,sample_602.java,2,17,temp,sample_603.java,2,17
//,3
public class xxx {
public void dummy_method(){
Resource resource = getContainerResource(conf);
long containerSize = (long) resource.getMemory() * 1024 * 1024;
String javaOpts = getContainerJavaOpts(conf);
long xmx = parseRightmostXmx(javaOpts);
if (xmx <= 0) {
xmx = (long) (tezHeapFraction * containerSize);
}
long actualMemToBeAllocated = (long) (tezMinReserveFraction * xmx);
if (actualMemToBeAllocated < memoryRequested) {
float frac = (float) memoryRequested / xmx;


log.info("fraction after calculation");
}
}

};