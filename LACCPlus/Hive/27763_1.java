//,temp,TopNKeyPushdownProcessor.java,269,299,temp,TopNKeyPushdownProcessor.java,230,259
//,3
public class xxx {
  private void pushdownInnerJoin(TopNKeyOperator topNKey, int fkJoinInputIndex, boolean nonFkSideIsFiltered) throws SemanticException {
    TopNKeyDesc topNKeyDesc = topNKey.getConf();
    CommonJoinOperator<? extends JoinDesc> join =
            (CommonJoinOperator<? extends JoinDesc>) topNKey.getParentOperators().get(0);
    List<Operator<? extends OperatorDesc>> joinInputs = join.getParentOperators();
    ReduceSinkOperator fkJoinInput = (ReduceSinkOperator) joinInputs.get(fkJoinInputIndex);
    if (nonFkSideIsFiltered) {
      LOG.debug("Not pushing {} through {} as non FK side of the join is filtered", topNKey.getName(), join.getName());
      return;
    }
    CommonKeyPrefix commonKeyPrefix = CommonKeyPrefix.map(
            mapUntilColumnEquals(topNKeyDesc.getKeyColumns(), join.getColumnExprMap()),
            topNKeyDesc.getColumnSortOrder(),
            topNKeyDesc.getNullOrder(),
            fkJoinInput.getConf().getKeyCols(),
            fkJoinInput.getConf().getColumnExprMap(),
            fkJoinInput.getConf().getOrder(),
            fkJoinInput.getConf().getNullOrder());
    if (commonKeyPrefix.isEmpty() || commonKeyPrefix.size() == topNKeyDesc.getPartitionKeyColumns().size()) {
      return;
    }
    LOG.debug("Pushing a copy of {} through {} and {}",
            topNKey.getName(), join.getName(), fkJoinInput.getName());
    final TopNKeyDesc newTopNKeyDesc = topNKeyDesc.combine(commonKeyPrefix);
    pushdown((TopNKeyOperator) copyDown(fkJoinInput, newTopNKeyDesc));

    if (topNKeyDesc.getKeyColumns().size() == commonKeyPrefix.size()) {
      LOG.debug("Removing {} above {}", topNKey.getName(), join.getName());
      join.removeChildAndAdoptItsChildren(topNKey);
    }
  }

};