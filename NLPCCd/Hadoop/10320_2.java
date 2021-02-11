//,temp,sample_1190.java,2,12,temp,sample_1189.java,2,11
//,3
public class xxx {
public void start() throws Exception {
thrs = new DummyZKFCThread[2];
thrs[0] = new DummyZKFCThread(ctx, svcs[0]);
assertEquals(0, thrs[0].zkfc.run(new String[]{"-formatZK"}));
ctx.addThread(thrs[0]);
thrs[0].start();


log.info("waiting for to enter active state");
}

};