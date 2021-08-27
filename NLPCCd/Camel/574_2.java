//,temp,sample_8472.java,2,8,temp,sample_8166.java,2,8
//,3
public class xxx {
public void read(String body, @Simple("${header.foo}") String foo) {
this.foo = foo;
this.body = body;


log.info("read method called on");
}

};