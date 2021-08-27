//,temp,HMSHandler.java,10265,10307,temp,HMSHandler.java,10153,10192
//,3
public class xxx {
  @Override
  public void drop_schema_version(SchemaVersionDescriptor version) throws TException {
    startFunction("drop_schema_version", ": " + version);
    Exception ex = null;
    boolean success = false;
    RawStore ms = getMS();
    try {
      SchemaVersion schemaVersion = ms.getSchemaVersion(version);
      if (schemaVersion == null) {
        throw new NoSuchObjectException("No schema version " + version);
      }
      firePreEvent(new PreDropSchemaVersionEvent(this, schemaVersion));
      Map<String, String> transactionalListenersResponses = Collections.emptyMap();
      ms.openTransaction();
      try {
        ms.dropSchemaVersion(version);
        if (!transactionalListeners.isEmpty()) {
          transactionalListenersResponses =
              MetaStoreListenerNotifier.notifyEvent(transactionalListeners,
                  EventType.DROP_SCHEMA_VERSION, new DropSchemaVersionEvent(true, this, schemaVersion));
        }
        success = ms.commitTransaction();
      } finally {
        if (!success) {
          ms.rollbackTransaction();
        }
        if (!listeners.isEmpty()) {
          MetaStoreListenerNotifier.notifyEvent(listeners, EventType.DROP_SCHEMA_VERSION,
              new DropSchemaVersionEvent(success, this, schemaVersion), null,
              transactionalListenersResponses, ms);
        }
      }
    } catch (MetaException|NoSuchObjectException e) {
      LOG.error("Caught exception dropping schema version", e);
      ex = e;
      throw e;
    } finally {
      endFunction("drop_schema_version", success, ex);
    }
  }

};