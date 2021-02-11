//,temp,sample_8109.java,2,17,temp,sample_8129.java,2,17
//,2
public class xxx {
public void dummy_method(){
requestToSign = requestToSign.toLowerCase();
signature = signRequest(requestToSign, s_secretKey.get());
encodedSignature = URLEncoder.encode(signature, "UTF-8");
url = developerServer + "?command=enableStaticNat&apikey=" + encodedApiKey + "&signature=" + encodedSignature + "&id=" + encodedSourceNatPublicIpId + "&virtualMachineId=" + encodedVmId;
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, String> success = getSingleValueFromXML(is, new String[] { "success" });


log.info("enable static nat success success");
}
}

};