//,temp,LazyHiveChar.java,56,77,temp,LazyHiveVarchar.java,56,77
//,2
public class xxx {
  @Override
  public void init(ByteArrayRef bytes, int start, int length) {
    if (oi.isEscaped()) {
      Text textData =  data.getTextValue();
      // This is doing a lot of copying here, this could be improved by enforcing length
      // at the same time as escaping rather than as separate steps.
      LazyUtils.copyAndEscapeStringDataToText(bytes.getData(), start, length,
          oi.getEscapeChar(),textData);
      data.set(textData.toString(), maxLength);
      isNull = false;
    } else {
      String byteData = null;
      try {
        byteData = Text.decode(bytes.getData(), start, length);
        data.set(byteData, maxLength);
        isNull = false;
      } catch (CharacterCodingException e) {
        isNull = true;
        LOG.debug("Data not in the HiveChar data type range so converted to null.", e);
      }
    }
  }

};