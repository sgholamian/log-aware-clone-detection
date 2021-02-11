//,temp,ModifyNamespaceProcedure.java,60,93,temp,CreateNamespaceProcedure.java,56,98
//,3
public class xxx {
  @Override
  protected Flow executeFromState(final MasterProcedureEnv env, final CreateNamespaceState state)
      throws InterruptedException {
    LOG.trace("{} execute state={}", this, state);
    try {
      switch (state) {
        case CREATE_NAMESPACE_PREPARE:
          boolean success = prepareCreate(env);
          releaseSyncLatch();
          if (!success) {
            assert isFailed() : "createNamespace should have an exception here";
            return Flow.NO_MORE_STATE;
          }
          setNextState(CreateNamespaceState.CREATE_NAMESPACE_CREATE_DIRECTORY);
          break;
        case CREATE_NAMESPACE_CREATE_DIRECTORY:
          createDirectory(env, nsDescriptor);
          setNextState(CreateNamespaceState.CREATE_NAMESPACE_INSERT_INTO_NS_TABLE);
          break;
        case CREATE_NAMESPACE_INSERT_INTO_NS_TABLE:
          addOrUpdateNamespace(env, nsDescriptor);
          setNextState(CreateNamespaceState.CREATE_NAMESPACE_SET_NAMESPACE_QUOTA);
          break;
        case CREATE_NAMESPACE_UPDATE_ZK:
          // not used any more
          setNextState(CreateNamespaceState.CREATE_NAMESPACE_SET_NAMESPACE_QUOTA);
          break;
        case CREATE_NAMESPACE_SET_NAMESPACE_QUOTA:
          setNamespaceQuota(env, nsDescriptor);
          return Flow.NO_MORE_STATE;
        default:
          throw new UnsupportedOperationException(this + " unhandled state=" + state);
      }
    } catch (IOException e) {
      if (isRollbackSupported(state)) {
        setFailure("master-create-namespace", e);
      } else {
        LOG.warn("Retriable error trying to create namespace=" + nsDescriptor.getName() +
          " (in state=" + state + ")", e);
      }
    }
    return Flow.HAS_MORE_STATE;
  }

};