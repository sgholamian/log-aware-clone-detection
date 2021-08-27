//,temp,sample_2451.java,2,9,temp,sample_2449.java,2,9
//,3
public class xxx {
public void getStatus() throws IOException {
MethodCallRetVal p = doHttpCall(templetonBaseUrl + "/status", HTTP_METHOD_TYPE.GET);
Assert.assertEquals(p.getAssertMsg(), HttpStatus.OK_200, p.httpStatusCode);
Assert.assertTrue(p.getAssertMsg(), jsonStringToSortedMap("{\"status\":\"ok\",\"version\":\"v1\"}").equals( jsonStringToSortedMap(p.responseBody)));


log.info("getstatus");
}

};