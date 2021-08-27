//,temp,sample_1178.java,2,15,temp,sample_3872.java,2,14
//,3
public class xxx {
public void filter(Direction direction, List<Header> headers) {
if (headers == null) {
return;
}
Iterator<Header> iterator = headers.iterator();
while (iterator.hasNext()) {
Header header = iterator.next();
if (!(header instanceof SoapHeader)) {


log.info("skipped header since it is not a soapheader");
}
}
}

};