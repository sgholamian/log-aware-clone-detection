//,temp,sample_2451.java,2,9,temp,sample_2449.java,2,9
//,3
public class xxx {
public void listDataBases() throws IOException {
MethodCallRetVal p = doHttpCall(templetonBaseUrl + "/ddl/database", HTTP_METHOD_TYPE.GET);
Assert.assertEquals(p.getAssertMsg(), HttpStatus.OK_200, p.httpStatusCode);
Assert.assertEquals(p.getAssertMsg(), "{\"databases\":[\"default\"]}", p.responseBody);


log.info("listdatabases");
}

};