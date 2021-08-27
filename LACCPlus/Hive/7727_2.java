//,temp,HMSHandler.java,9940,9979,temp,HMSHandler.java,9902,9938
//,3
public class xxx {
  @Override
  public void create_ischema(ISchema schema) throws TException {
    startFunction("create_ischema", ": " + schema.getName());
    boolean success = false;
    Exception ex = null;
    RawStore ms = getMS();
    try {
      firePreEvent(new PreCreateISchemaEvent(this, schema));
      Map<String, String> transactionalListenersResponses = Collections.emptyMap();
      ms.openTransaction();
      try {
        ms.createISchema(schema);

        if (!transactionalListeners.isEmpty()) {
          transactionalListenersResponses =
              MetaStoreListenerNotifier.notifyEvent(transactionalListeners,
                  EventType.CREATE_ISCHEMA, new CreateISchemaEvent(true, this, schema));
        }
        success = ms.commitTransaction();
      } finally {
        if (!success) {
          ms.rollbackTransaction();
        }
        if (!listeners.isEmpty()) {
          MetaStoreListenerNotifier.notifyEvent(listeners, EventType.CREATE_ISCHEMA,
              new CreateISchemaEvent(success, this, schema), null,
              transactionalListenersResponses, ms);
        }
      }
    } catch (MetaException|AlreadyExistsException e) {
      LOG.error("Caught exception creating schema", e);
      ex = e;
      throw e;
    } finally {
      endFunction("create_ischema", success, ex);
    }
  }

};