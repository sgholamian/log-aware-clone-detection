//,temp,sample_8255.java,2,16,temp,sample_8256.java,2,16
//,2
public class xxx {
public void dummy_method(){
encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
String encodedPublicIpId = URLEncoder.encode(s_publicIpId.get(), "UTF-8");
requestToSign = "apikey=" + encodedApiKey + "&command=disableStaticNat" + "&id=" + encodedPublicIpId;
requestToSign = requestToSign.toLowerCase();
signature = signRequest(requestToSign, s_secretKey.get());
encodedSignature = URLEncoder.encode(signature, "UTF-8");
url = developerServer + "?command=disableStaticNat&apikey=" + encodedApiKey + "&id=" + encodedPublicIpId + "&signature=" + encodedSignature;
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);


log.info("list ip addresses for user response code");
}

};