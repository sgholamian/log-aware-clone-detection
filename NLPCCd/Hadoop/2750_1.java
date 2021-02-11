//,temp,sample_3625.java,2,10,temp,sample_379.java,2,9
//,2
public class xxx {
public void init(Configuration conf) {
allocateLeft = conf.getBoolean(FAVOR_EARLY_ALLOCATION, DEFAULT_GREEDY_FAVOR_EARLY_ALLOCATION);
if (allocateLeft) {
} else {


log.info("initializing the greedyreservationagent to favor right allocations controlled by parameter");
}
}

};