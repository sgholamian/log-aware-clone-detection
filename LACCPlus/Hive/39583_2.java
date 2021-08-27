//,temp,TopNKeyPushdownProcessor.java,180,198,temp,TopNKeyPushdownProcessor.java,151,169
//,2
public class xxx {
  private void pushdownThroughGroupBy(TopNKeyOperator topNKey) throws SemanticException {
    final GroupByOperator groupBy = (GroupByOperator) topNKey.getParentOperators().get(0);
    final GroupByDesc groupByDesc = groupBy.getConf();
    final TopNKeyDesc topNKeyDesc = topNKey.getConf();

    CommonKeyPrefix commonKeyPrefix = CommonKeyPrefix.map(topNKeyDesc, groupByDesc);
    if (commonKeyPrefix.isEmpty() || commonKeyPrefix.size() == topNKeyDesc.getPartitionKeyColumns().size()) {
      return;
    }

    LOG.debug("Pushing a copy of {} through {}", topNKey.getName(), groupBy.getName());
    final TopNKeyDesc newTopNKeyDesc = topNKeyDesc.combine(commonKeyPrefix);
    pushdown((TopNKeyOperator) copyDown(groupBy, newTopNKeyDesc));

    if (topNKeyDesc.getKeyColumns().size() == commonKeyPrefix.size()) {
      LOG.debug("Removing {} above {}", topNKey.getName(), groupBy.getName());
      groupBy.removeChildAndAdoptItsChildren(topNKey);
    }
  }

};