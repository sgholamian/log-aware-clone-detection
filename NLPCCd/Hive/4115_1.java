//,temp,sample_602.java,2,17,temp,sample_603.java,2,17
//,3
public class xxx {
public void dummy_method(){
float tezMaxReserveFraction = conf.getFloatVar(ConfVars.TEZ_TASK_SCALE_MEMORY_RESERVE_FRACTION_MAX);
Resource resource = getContainerResource(conf);
long containerSize = (long) resource.getMemory() * 1024 * 1024;
String javaOpts = getContainerJavaOpts(conf);
long xmx = parseRightmostXmx(javaOpts);
if (xmx <= 0) {
xmx = (long) (tezHeapFraction * containerSize);
}
long actualMemToBeAllocated = (long) (tezMinReserveFraction * xmx);
if (actualMemToBeAllocated < memoryRequested) {


log.info("the actual amount of memory to be allocated is less than the amount of requested memory for map join conversion");
}
}

};