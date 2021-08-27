//,temp,sample_4409.java,2,18,temp,sample_6368.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (index != -1) {
path = uri.substring(index + 1);
index = path.indexOf('?');
if (index != -1) {
path = path.substring(0, index);
}
path = path.replaceAll(":", "");
try {
path = URLDecoder.decode(path, "UTF-8");
} catch (UnsupportedEncodingException e) {


log.info("failed to decode url path ignoring exception");
}
}
}

};