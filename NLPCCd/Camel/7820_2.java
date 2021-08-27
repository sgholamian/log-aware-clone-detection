//,temp,sample_4778.java,2,17,temp,sample_4783.java,2,16
//,3
public class xxx {
public void dummy_method(){
try {
for (it.seek(keyBuilder(repositoryName, "")); it.hasNext(); it.next()) {
if (!asString(it.peekNext().getKey()).startsWith(prefix)) {
break;
}
count++;
}
} finally {
IOHelper.close(it);
}


log.info("size of repository");
}

};