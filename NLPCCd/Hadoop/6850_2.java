//,temp,sample_4095.java,2,9,temp,sample_4589.java,2,11
//,3
public class xxx {
public static URI stringAsURI(String s) throws IOException {
URI u = null;
try {
u = new URI(s);
} catch (URISyntaxException e){


log.info("syntax error in uri please check hdfs configuration");
}
}

};