//,temp,sample_4176.java,2,16,temp,sample_4173.java,2,16
//,3
public class xxx {
public void dummy_method(){
hbaseAdmin.move(regionInfo.getEncodedNameAsBytes(), Bytes.toBytes(originServer.getServerName().getServerName()));
do {
Thread.sleep(1);
} while (cluster.getServerWith(regionInfo.getRegionName()) == targetServerNum);
putDataAndVerify(table, "r3", FAMILY, "v3", 3);
targetServer.kill();
cluster.getRegionServerThreads().get(targetServerNum).join();
while (master.getServerManager().areDeadServersInProgress()) {
Thread.sleep(5);
}


log.info("killing origin server");
}

};