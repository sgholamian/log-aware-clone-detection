//,temp,sample_4852.java,2,17,temp,sample_1314.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (!LazyUtils.isNumberMaybe(bytes.getData(), start, length)) {
isNull = true;
return;
}
try {
byteData = Text.decode(bytes.getData(), start, length);
data.set(Double.parseDouble(byteData));
isNull = false;
} catch (NumberFormatException e) {
isNull = true;


log.info("data not in the double data type range so converted to null given data is");
}
}

};