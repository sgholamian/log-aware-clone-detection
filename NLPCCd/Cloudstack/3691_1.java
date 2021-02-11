//,temp,sample_2356.java,2,17,temp,sample_2357.java,2,17
//,2
public class xxx {
public void dummy_method(){
for (NfsStoragePool pool : _pools) {
Script cmd = new Script(s_heartBeatPath, _heartBeatCheckerTimeout, s_logger);
cmd.add("-i", pool._poolIp);
cmd.add("-p", pool._poolMountSourcePath);
cmd.add("-m", pool._mountDestPath);
cmd.add("-h", _hostIP);
cmd.add("-r");
cmd.add("-t", String.valueOf(_heartBeatUpdateFreq / 1000));
OutputInterpreter.OneLineParser parser = new OutputInterpreter.OneLineParser();
String result = cmd.execute(parser);


log.info("pool");
}
}

};