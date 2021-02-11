//,temp,sample_5462.java,2,19,temp,sample_2521.java,2,19
//,2
public class xxx {
public void dummy_method(){
if (api.getResponseType() == ResponseType.EMPTY) {
if (api.isEmpty() == true) {
} else {
}
} else {
if (api.isEmpty() != false) s_logger.error("Test case " + api.getTestCaseInfo() + " failed. Non-empty response was expected. Command was sent with url " + api.getUrl());
else {
if (api.setParam(this.getParam()) == false) {
return false;
} else if (api.getTestCaseInfo() != null) {


log.info("test case passed command was sent with the url");
}
}
}
}

};