//,temp,HMSHandler.java,10090,10109,temp,HMSHandler.java,9981,10000
//,3
public class xxx {
  @Override
  public ISchema get_ischema(ISchemaName schemaName) throws TException {
    startFunction("get_ischema", ": " + schemaName);
    Exception ex = null;
    ISchema schema = null;
    try {
      schema = getMS().getISchema(schemaName);
      if (schema == null) {
        throw new NoSuchObjectException("No schema named " + schemaName + " exists");
      }
      firePreEvent(new PreReadISchemaEvent(this, schema));
      return schema;
    } catch (MetaException e) {
      LOG.error("Caught exception getting schema", e);
      ex = e;
      throw e;
    } finally {
      endFunction("get_ischema", schema != null, ex);
    }
  }

};