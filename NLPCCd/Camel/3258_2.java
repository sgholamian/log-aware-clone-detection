//,temp,sample_623.java,2,9,temp,sample_624.java,2,12
//,3
public class xxx {
protected String getSameDocumentReferenceUri(Reference ref) {
String refUri = ref.getURI();
if (refUri == null) {
return null;
}
if (!refUri.startsWith("#")) {


log.info("ignoring non same document reference");
}
}

};