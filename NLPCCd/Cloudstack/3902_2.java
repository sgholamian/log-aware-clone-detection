//,temp,sample_8110.java,2,17,temp,sample_8130.java,2,17
//,2
public class xxx {
public void dummy_method(){
signature = signRequest(requestToSign, s_secretKey.get());
encodedSignature = URLEncoder.encode(signature, "UTF-8");
url = developerServer + "?command=enableStaticNat&apikey=" + encodedApiKey + "&ipaddressid=" + encodedPublicIpId + "&signature=" + encodedSignature + "&virtualMachineId=" + encodedVmId;
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, String> success = getSingleValueFromXML(is, new String[] {"success"});
} else {


log.info("enable static nat failed with error code following url was sent");
}
}

};