//,temp,sample_2667.java,2,9,temp,sample_18.java,2,9
//,3
public class xxx {
public static MapJoinTableContainer get(Path path) {
MapJoinTableContainer tableContainer = tableContainerMap.get(path);
if (tableContainer != null && LOG.isDebugEnabled()) {


log.info("loaded small table file from cache for query");
}
}

};