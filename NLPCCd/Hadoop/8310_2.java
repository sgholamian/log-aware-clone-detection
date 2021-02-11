//,temp,sample_5212.java,2,12,temp,sample_4268.java,2,14
//,3
public class xxx {
private void assign(List<Container> allocatedContainers) {
Iterator<Container> it = allocatedContainers.iterator();
containersAllocated += allocatedContainers.size();
int reducePending = reduces.size();
while (it.hasNext()) {
Container allocated = it.next();
if (LOG.isDebugEnabled()) {


log.info("assigning container with priority to nm");
}
}
}

};