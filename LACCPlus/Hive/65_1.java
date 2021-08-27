//,temp,HiveOnTezCostModel.java,466,477,temp,HiveOnTezCostModel.java,305,316
//,3
public class xxx {
    @Override
    public ImmutableList<RelCollation> getCollation(HiveJoin join) {
      final MapJoinStreamingRelation streamingSide = join.getStreamingSide();
      if (streamingSide != MapJoinStreamingRelation.LEFT_RELATION
              && streamingSide != MapJoinStreamingRelation.RIGHT_RELATION) {
        // Error; default value
        LOG.warn("Streaming side for map join not chosen");
        return ImmutableList.of();
      }
      return HiveAlgorithmsUtil.getJoinCollation(join.getJoinPredicateInfo(),
              join.getStreamingSide());
    }

};