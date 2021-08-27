//,temp,HMSHandler.java,10265,10307,temp,HMSHandler.java,10153,10192
//,3
public class xxx {
  @Override
  public void set_schema_version_state(SetSchemaVersionStateRequest rqst) throws TException {
    startFunction("set_schema_version_state, :" + rqst);
    boolean success = false;
    Exception ex = null;
    RawStore ms = getMS();
    try {
      SchemaVersion oldSchemaVersion = ms.getSchemaVersion(rqst.getSchemaVersion());
      if (oldSchemaVersion == null) {
        throw new NoSuchObjectException("No schema version " + rqst.getSchemaVersion());
      }
      SchemaVersion newSchemaVersion = new SchemaVersion(oldSchemaVersion);
      newSchemaVersion.setState(rqst.getState());
      firePreEvent(new PreAlterSchemaVersionEvent(this, oldSchemaVersion, newSchemaVersion));
      Map<String, String> transactionalListenersResponses = Collections.emptyMap();
      ms.openTransaction();
      try {
        ms.alterSchemaVersion(rqst.getSchemaVersion(), newSchemaVersion);
        if (!transactionalListeners.isEmpty()) {
          transactionalListenersResponses =
              MetaStoreListenerNotifier.notifyEvent(transactionalListeners,
                  EventType.ALTER_SCHEMA_VERSION, new AlterSchemaVersionEvent(true, this,
                      oldSchemaVersion, newSchemaVersion));
        }
        success = ms.commitTransaction();
      } finally {
        if (!success) {
          ms.rollbackTransaction();
        }
        if (!listeners.isEmpty()) {
          MetaStoreListenerNotifier.notifyEvent(listeners, EventType.ALTER_SCHEMA_VERSION,
              new AlterSchemaVersionEvent(success, this, oldSchemaVersion, newSchemaVersion), null,
              transactionalListenersResponses, ms);
        }
      }
    } catch (MetaException|NoSuchObjectException e) {
      LOG.error("Caught exception changing schema version state", e);
      ex = e;
      throw e;
    } finally {
      endFunction("set_schema_version_state", success, ex);
    }
  }

};