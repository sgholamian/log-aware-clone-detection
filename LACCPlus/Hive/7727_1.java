//,temp,HMSHandler.java,9940,9979,temp,HMSHandler.java,9902,9938
//,3
public class xxx {
  @Override
  public void alter_ischema(AlterISchemaRequest rqst) throws TException {
    startFunction("alter_ischema", ": " + rqst);
    boolean success = false;
    Exception ex = null;
    RawStore ms = getMS();
    try {
      ISchema oldSchema = ms.getISchema(rqst.getName());
      if (oldSchema == null) {
        throw new NoSuchObjectException("Could not find schema " + rqst.getName());
      }
      firePreEvent(new PreAlterISchemaEvent(this, oldSchema, rqst.getNewSchema()));
      Map<String, String> transactionalListenersResponses = Collections.emptyMap();
      ms.openTransaction();
      try {
        ms.alterISchema(rqst.getName(), rqst.getNewSchema());
        if (!transactionalListeners.isEmpty()) {
          transactionalListenersResponses =
              MetaStoreListenerNotifier.notifyEvent(transactionalListeners,
                  EventType.ALTER_ISCHEMA, new AlterISchemaEvent(true, this, oldSchema, rqst.getNewSchema()));
        }
        success = ms.commitTransaction();
      } finally {
        if (!success) {
          ms.rollbackTransaction();
        }
        if (!listeners.isEmpty()) {
          MetaStoreListenerNotifier.notifyEvent(listeners, EventType.ALTER_ISCHEMA,
              new AlterISchemaEvent(success, this, oldSchema, rqst.getNewSchema()), null,
              transactionalListenersResponses, ms);
        }
      }
    } catch (MetaException|NoSuchObjectException e) {
      LOG.error("Caught exception altering schema", e);
      ex = e;
      throw e;
    } finally {
      endFunction("alter_ischema", success, ex);
    }
  }

};