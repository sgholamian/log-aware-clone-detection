//,temp,HiveOnTezCostModel.java,466,477,temp,HiveOnTezCostModel.java,305,316
//,3
public class xxx {
    @Override
    public RelDistribution getDistribution(HiveJoin join) {
      final MapJoinStreamingRelation streamingSide = join.getStreamingSide();
      if (streamingSide != MapJoinStreamingRelation.LEFT_RELATION
              && streamingSide != MapJoinStreamingRelation.RIGHT_RELATION) {
        // Error; default value
        LOG.warn("Streaming side for map join not chosen");
        return RelDistributions.SINGLETON;
      }
      return HiveAlgorithmsUtil.getJoinDistribution(join.getJoinPredicateInfo(),
              join.getStreamingSide());
    }

};