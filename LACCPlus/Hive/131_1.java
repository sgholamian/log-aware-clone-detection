//,temp,HMSHandler.java,10090,10109,temp,HMSHandler.java,9981,10000
//,3
public class xxx {
  @Override
  public SchemaVersion get_schema_version(SchemaVersionDescriptor version) throws TException {
    startFunction("get_schema_version", ": " + version);
    Exception ex = null;
    SchemaVersion schemaVersion = null;
    try {
      schemaVersion = getMS().getSchemaVersion(version);
      if (schemaVersion == null) {
        throw new NoSuchObjectException("No schema version " + version + "exists");
      }
      firePreEvent(new PreReadhSchemaVersionEvent(this, Collections.singletonList(schemaVersion)));
      return schemaVersion;
    } catch (MetaException e) {
      LOG.error("Caught exception getting schema version", e);
      ex = e;
      throw e;
    } finally {
      endFunction("get_schema_version", schemaVersion != null, ex);
    }
  }

};