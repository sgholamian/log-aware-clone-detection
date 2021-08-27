//,temp,sample_7278.java,2,16,temp,sample_7277.java,2,13
//,3
public class xxx {
protected void addSignaturePolicyIdentifier(Document doc, Element signedProperties, Input input) throws XmlSignatureException, SAXException, IOException, ParserConfigurationException {
if (!isAddSignaturePolicy()) {
return;
}
Element signaturePolicyIdentifier = createElement("SignaturePolicyIdentifier", doc, input);
signedProperties.appendChild(signaturePolicyIdentifier);
if (SIG_POLICY_IMPLIED.equals(getSignaturePolicy())) {


log.info("adding implied signature policy");
}
}

};