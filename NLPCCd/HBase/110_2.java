//,temp,sample_2721.java,2,10,temp,sample_465.java,2,9
//,3
public class xxx {
protected void createTable(HTableDescriptor htd) throws Exception {
deleteTable();
if (util.getHBaseClusterInterface() instanceof MiniHBaseCluster) {


log.info("test does not make a lot of sense for minicluster will set flush size low");
}
}

};