//,temp,ModifyNamespaceProcedure.java,60,93,temp,CreateNamespaceProcedure.java,56,98
//,3
public class xxx {
  @Override
  protected Flow executeFromState(final MasterProcedureEnv env, final ModifyNamespaceState state)
      throws InterruptedException {
    LOG.trace("{} execute state={}", this, state);
    try {
      switch (state) {
        case MODIFY_NAMESPACE_PREPARE:
          boolean success = prepareModify(env);
          releaseSyncLatch();
          if (!success) {
            assert isFailed() : "Modify namespace should have an exception here";
            return Flow.NO_MORE_STATE;
          }
          setNextState(ModifyNamespaceState.MODIFY_NAMESPACE_UPDATE_NS_TABLE);
          break;
        case MODIFY_NAMESPACE_UPDATE_NS_TABLE:
          addOrUpdateNamespace(env, newNsDescriptor);
          return Flow.NO_MORE_STATE;
        case MODIFY_NAMESPACE_UPDATE_ZK:
          // not used any more
          return Flow.NO_MORE_STATE;
        default:
          throw new UnsupportedOperationException(this + " unhandled state=" + state);
      }
    } catch (IOException e) {
      if (isRollbackSupported(state)) {
        setFailure("master-modify-namespace", e);
      } else {
        LOG.warn("Retriable error trying to modify namespace=" + newNsDescriptor.getName() +
          " (in state=" + state + ")", e);
      }
    }
    return Flow.HAS_MORE_STATE;
  }

};