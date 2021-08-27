//,temp,HMSHandler.java,10048,10088,temp,HMSHandler.java,10002,10046
//,3
public class xxx {
  @Override
  public void add_schema_version(SchemaVersion schemaVersion) throws TException {
    startFunction("add_schema_version", ": " + schemaVersion);
    boolean success = false;
    Exception ex = null;
    RawStore ms = getMS();
    try {
      // Make sure the referenced schema exists
      if (ms.getISchema(schemaVersion.getSchema()) == null) {
        throw new NoSuchObjectException("No schema named " + schemaVersion.getSchema());
      }
      firePreEvent(new PreAddSchemaVersionEvent(this, schemaVersion));
      Map<String, String> transactionalListenersResponses = Collections.emptyMap();
      ms.openTransaction();
      try {
        ms.addSchemaVersion(schemaVersion);

        if (!transactionalListeners.isEmpty()) {
          transactionalListenersResponses =
              MetaStoreListenerNotifier.notifyEvent(transactionalListeners,
                  EventType.ADD_SCHEMA_VERSION, new AddSchemaVersionEvent(true, this, schemaVersion));
        }
        success = ms.commitTransaction();
      } finally {
        if (!success) {
          ms.rollbackTransaction();
        }
        if (!listeners.isEmpty()) {
          MetaStoreListenerNotifier.notifyEvent(listeners, EventType.ADD_SCHEMA_VERSION,
              new AddSchemaVersionEvent(success, this, schemaVersion), null,
              transactionalListenersResponses, ms);
        }
      }
    } catch (MetaException|AlreadyExistsException e) {
      LOG.error("Caught exception adding schema version", e);
      ex = e;
      throw e;
    } finally {
      endFunction("add_schema_version", success, ex);
    }
  }

};