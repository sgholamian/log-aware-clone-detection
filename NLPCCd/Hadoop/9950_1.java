//,temp,sample_1913.java,2,17,temp,sample_1914.java,2,17
//,3
public class xxx {
public void dummy_method(){
timestamp = Time.now();
pass = busyTest(xcievers, threads, fileLen, timeWin, retries);
timestamp2 = Time.now();
assertTrue("Something wrong! Test 2 got Exception with maxmum retries!", pass);
retries = 3;
timeWin = 1000;
timestamp = Time.now();
pass = busyTest(xcievers, threads, fileLen, timeWin, retries);
timestamp2 = Time.now();
if ( pass ) {


log.info("test succeeded time spent sec");
}
}

};