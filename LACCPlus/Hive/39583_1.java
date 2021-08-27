//,temp,TopNKeyPushdownProcessor.java,180,198,temp,TopNKeyPushdownProcessor.java,151,169
//,2
public class xxx {
  private void pushdownThroughReduceSink(TopNKeyOperator topNKey) throws SemanticException {
    ReduceSinkOperator reduceSink = (ReduceSinkOperator) topNKey.getParentOperators().get(0);
    final ReduceSinkDesc reduceSinkDesc = reduceSink.getConf();
    final TopNKeyDesc topNKeyDesc = topNKey.getConf();

    CommonKeyPrefix commonKeyPrefix = CommonKeyPrefix.map(topNKeyDesc, reduceSinkDesc);
    if (commonKeyPrefix.isEmpty() || commonKeyPrefix.size() == topNKeyDesc.getPartitionKeyColumns().size()) {
      return;
    }

    LOG.debug("Pushing a copy of {} through {}", topNKey.getName(), reduceSink.getName());
    final TopNKeyDesc newTopNKeyDesc = topNKeyDesc.combine(commonKeyPrefix);
    pushdown((TopNKeyOperator) copyDown(reduceSink, newTopNKeyDesc));

    if (topNKeyDesc.getKeyColumns().size() == commonKeyPrefix.size()) {
      LOG.debug("Removing {} above {}", topNKey.getName(), reduceSink.getName());
      reduceSink.removeChildAndAdoptItsChildren(topNKey);
    }
  }

};