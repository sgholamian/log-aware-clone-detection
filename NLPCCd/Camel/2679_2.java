//,temp,sample_5707.java,2,17,temp,sample_5706.java,2,16
//,2
public class xxx {
public SolrCloudFixture(String solrHome) throws Exception {
String xml = IOHelper.loadText(new FileInputStream(new File(solrHome, "solr-no-core.xml")));
miniCluster = new MiniSolrCloudCluster(1, "/solr", new File("target/tmp").toPath(), xml, null, null);
String zkAddr = miniCluster.getZkServer().getZkAddress();
String zkHost = miniCluster.getZkServer().getZkHost();
buildZooKeeper(zkHost, zkAddr, new File(solrHome), "solrconfig.xml", "schema.xml");
List<JettySolrRunner> jettys = miniCluster.getJettySolrRunners();
for (JettySolrRunner jetty : jettys) {
if (!jetty.isRunning()) {


log.info("jetty not running");
}
}
}

};