//,temp,OrcRecordUpdater.java,232,255,temp,OrcOutputFormat.java,132,172
//,3
public class xxx {
  private OrcFile.WriterOptions getOptions(JobConf conf, Properties props) {
    OrcFile.WriterOptions result = OrcFile.writerOptions(props, conf);
    if (props != null) {
      final String columnNameProperty =
          props.getProperty(IOConstants.COLUMNS);
      final String columnTypeProperty =
          props.getProperty(IOConstants.COLUMNS_TYPES);
      if (columnNameProperty != null &&
          !columnNameProperty.isEmpty() &&
          columnTypeProperty != null &&
          !columnTypeProperty.isEmpty()) {
        List<String> columnNames;
        List<TypeInfo> columnTypes;
        final String columnNameDelimiter = props.containsKey(serdeConstants.COLUMN_NAME_DELIMITER) ? props
            .getProperty(serdeConstants.COLUMN_NAME_DELIMITER) : String.valueOf(SerDeUtils.COMMA);
        if (columnNameProperty.length() == 0) {
          columnNames = new ArrayList<String>();
        } else {
          columnNames = Arrays.asList(columnNameProperty.split(columnNameDelimiter));
        }

        if (columnTypeProperty.length() == 0) {
          columnTypes = new ArrayList<TypeInfo>();
        } else {
          columnTypes =
              TypeInfoUtils.getTypeInfosFromTypeString(columnTypeProperty);
        }

        TypeDescription schema = TypeDescription.createStruct();
        for (int i = 0; i < columnNames.size(); ++i) {
          schema.addField(columnNames.get(i),
              OrcInputFormat.convertTypeInfo(columnTypes.get(i)));
        }
        if (LOG.isDebugEnabled()) {
          LOG.debug("ORC schema = " + schema);
        }
        result.setSchema(schema);
      }
    }
    return result;
  }

};