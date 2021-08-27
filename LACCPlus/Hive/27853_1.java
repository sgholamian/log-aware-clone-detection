//,temp,OrcRecordUpdater.java,232,255,temp,OrcOutputFormat.java,132,172
//,3
public class xxx {
  private static TypeDescription getTypeDescriptionFromTableProperties(Properties tableProperties) {
    TypeDescription schema = null;
    if (tableProperties != null) {
      final String columnNameProperty = tableProperties.getProperty(IOConstants.COLUMNS);
      final String columnTypeProperty = tableProperties.getProperty(IOConstants.COLUMNS_TYPES);
      if (!Strings.isNullOrEmpty(columnNameProperty) && !Strings.isNullOrEmpty(columnTypeProperty)) {
        List<String> columnNames =
          columnNameProperty.length() == 0 ? new ArrayList<String>() : Arrays.asList(columnNameProperty.split(","));
        List<TypeInfo> columnTypes = columnTypeProperty.length() == 0 ? new ArrayList<TypeInfo>() : TypeInfoUtils
          .getTypeInfosFromTypeString(columnTypeProperty);

        schema = TypeDescription.createStruct();
        for (int i = 0; i < columnNames.size(); i++) {
          schema.addField(columnNames.get(i), OrcInputFormat.convertTypeInfo(columnTypes.get(i)));
        }
      }
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("ORC schema = " + schema);
    }

    return schema;
  }

};