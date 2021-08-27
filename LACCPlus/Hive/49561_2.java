//,temp,HMSHandler.java,10048,10088,temp,HMSHandler.java,10002,10046
//,3
public class xxx {
  @Override
  public void drop_ischema(ISchemaName schemaName) throws TException {
    startFunction("drop_ischema", ": " + schemaName);
    Exception ex = null;
    boolean success = false;
    RawStore ms = getMS();
    try {
      // look for any valid versions.  This will also throw NoSuchObjectException if the schema
      // itself doesn't exist, which is what we want.
      SchemaVersion latest = ms.getLatestSchemaVersion(schemaName);
      if (latest != null) {
        ex = new InvalidOperationException("Schema " + schemaName + " cannot be dropped, it has" +
            " at least one valid version");
        throw (InvalidObjectException)ex;
      }
      ISchema schema = ms.getISchema(schemaName);
      firePreEvent(new PreDropISchemaEvent(this, schema));
      Map<String, String> transactionalListenersResponses = Collections.emptyMap();
      ms.openTransaction();
      try {
        ms.dropISchema(schemaName);
        if (!transactionalListeners.isEmpty()) {
          transactionalListenersResponses =
              MetaStoreListenerNotifier.notifyEvent(transactionalListeners,
                  EventType.DROP_ISCHEMA, new DropISchemaEvent(true, this, schema));
        }
        success = ms.commitTransaction();
      } finally {
        if (!success) {
          ms.rollbackTransaction();
        }
        if (!listeners.isEmpty()) {
          MetaStoreListenerNotifier.notifyEvent(listeners, EventType.DROP_ISCHEMA,
              new DropISchemaEvent(success, this, schema), null,
              transactionalListenersResponses, ms);
        }
      }
    } catch (MetaException|NoSuchObjectException e) {
      LOG.error("Caught exception dropping schema", e);
      ex = e;
      throw e;
    } finally {
      endFunction("drop_ischema", success, ex);
    }
  }

};