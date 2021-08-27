//,temp,HMSHandler.java,10331,10349,temp,HMSHandler.java,10132,10151
//,3
public class xxx {
  @Override
  public List<SchemaVersion> get_schema_all_versions(ISchemaName schemaName) throws TException {
    startFunction("get_all_schema_versions", ": " + schemaName);
    Exception ex = null;
    List<SchemaVersion> schemaVersions = null;
    try {
      schemaVersions = getMS().getAllSchemaVersion(schemaName);
      if (schemaVersions == null) {
        throw new NoSuchObjectException("No versions of schema " + schemaName + "exist");
      }
      firePreEvent(new PreReadhSchemaVersionEvent(this, schemaVersions));
      return schemaVersions;
    } catch (MetaException e) {
      LOG.error("Caught exception getting all schema versions", e);
      ex = e;
      throw e;
    } finally {
      endFunction("get_all_schema_versions", schemaVersions != null, ex);
    }
  }

};