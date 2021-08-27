//,temp,TopNKeyPushdownProcessor.java,269,299,temp,TopNKeyPushdownProcessor.java,230,259
//,3
public class xxx {
  private void pushdownThroughLeftOuterJoin(TopNKeyOperator topNKey) throws SemanticException {
    final TopNKeyDesc topNKeyDesc = topNKey.getConf();
    final CommonJoinOperator<? extends JoinDesc> join =
            (CommonJoinOperator<? extends JoinDesc>) topNKey.getParentOperators().get(0);
    final List<Operator<? extends OperatorDesc>> joinInputs = join.getParentOperators();
    final ReduceSinkOperator reduceSinkOperator = (ReduceSinkOperator) joinInputs.get(0);
    final ReduceSinkDesc reduceSinkDesc = reduceSinkOperator.getConf();

    CommonKeyPrefix commonKeyPrefix = CommonKeyPrefix.map(
            mapUntilColumnEquals(topNKeyDesc.getKeyColumns(), join.getColumnExprMap()),
            topNKeyDesc.getColumnSortOrder(),
            topNKeyDesc.getNullOrder(),
            reduceSinkDesc.getKeyCols(),
            reduceSinkDesc.getColumnExprMap(),
            reduceSinkDesc.getOrder(),
            reduceSinkDesc.getNullOrder());
    if (commonKeyPrefix.isEmpty() || commonKeyPrefix.size() == topNKeyDesc.getPartitionKeyColumns().size()) {
      return;
    }

    LOG.debug("Pushing a copy of {} through {} and {}",
            topNKey.getName(), join.getName(), reduceSinkOperator.getName());
    final TopNKeyDesc newTopNKeyDesc = topNKeyDesc.combine(commonKeyPrefix);
    pushdown((TopNKeyOperator) copyDown(reduceSinkOperator, newTopNKeyDesc));

    if (topNKeyDesc.getKeyColumns().size() == commonKeyPrefix.size()) {
      LOG.debug("Removing {} above {}", topNKey.getName(), join.getName());
      join.removeChildAndAdoptItsChildren(topNKey);
    }
  }

};