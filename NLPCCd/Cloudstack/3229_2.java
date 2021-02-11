//,temp,sample_2521.java,2,19,temp,sample_7340.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (api.getResponseType() == ResponseType.EMPTY) {
if (api.isEmpty() == true) {
} else {
}
} else {
if (api.isEmpty() != false) s_logger.error("Test case " + api.getTestCaseInfo() + " failed. Non-empty response was expected. Command was sent with url " + api.getUrl());
else {
if (api.setParam(getParam()) == false) {
return false;
} else if (api.getTestCaseInfo() != null) {


log.info("test case passed");
}
}
}
}

};