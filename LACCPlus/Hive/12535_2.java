//,temp,LazyDouble.java,46,65,temp,LazyFloat.java,46,65
//,2
public class xxx {
  @Override
  public void init(ByteArrayRef bytes, int start, int length) {
    String byteData = null;
    if (!LazyUtils.isNumberMaybe(bytes.getData(), start, length)) {
      isNull = true;
      return;
    }
    try {
      byteData = Text.decode(bytes.getData(), start, length);
      data.set(Float.parseFloat(byteData));
      isNull = false;
    } catch (NumberFormatException e) {
      isNull = true;
      LOG.debug("Data not in the Float data type range so converted to null. Given data is :"
          + byteData, e);
    } catch (CharacterCodingException e) {
      isNull = true;
      LOG.debug("Data not in the Float data type range so converted to null.", e);
    }
  }

};