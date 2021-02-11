//,temp,sample_5581.java,2,17,temp,sample_2203.java,2,17
//,3
public class xxx {
public void dummy_method(){
long startTime = 0;
if (LOG.isDebugEnabled()) {
startTime = clock.getTime();
}
toPreempt = selector.selectCandidates(toPreempt, clusterResources, totalPreemptionAllowed);
if (LOG.isDebugEnabled()) {
int totalSelected = 0;
for (Set<RMContainer> set : toPreempt.values()) {
totalSelected += set.size();
}


log.info("so far total containers selected to be preempted");
}
}

};