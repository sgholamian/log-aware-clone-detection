//,temp,sample_5538.java,2,9,temp,sample_5537.java,2,9
//,3
public class xxx {
public RelDistribution getDistribution(HiveJoin join) {
final MapJoinStreamingRelation streamingSide = join.getStreamingSide();
if (streamingSide != MapJoinStreamingRelation.LEFT_RELATION && streamingSide != MapJoinStreamingRelation.RIGHT_RELATION) {


log.info("streaming side for map join not chosen");
}
}

};