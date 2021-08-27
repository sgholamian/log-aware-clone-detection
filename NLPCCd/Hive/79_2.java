//,temp,sample_5544.java,2,13,temp,sample_5545.java,2,13
//,2
public class xxx {
public Double getRowCount(SemiJoin rel, RelMetadataQuery mq) {
PKFKRelationInfo pkfk = analyzeJoinForPKFK(rel, mq);
if (pkfk != null) {
double selectivity = (pkfk.pkInfo.selectivity * pkfk.ndvScalingFactor);
selectivity = Math.min(1.0, selectivity);
if (LOG.isDebugEnabled()) {


log.info("identified primary foreign key relation");
}
}
}

};