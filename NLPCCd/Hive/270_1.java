//,temp,sample_2041.java,2,8,temp,sample_5190.java,2,6
//,3
public class xxx {
private void updateColStats(HiveConf conf, Statistics stats, long interimNumRows, long newNumRows, CommonJoinOperator<? extends JoinDesc> jop, Map<Integer, Long> rowCountParents) {
if (newNumRows < 0) {


log.info("stats overflow in number of rows rows will be set to long max value");
}
}

};