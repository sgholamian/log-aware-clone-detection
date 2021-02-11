//,temp,JHLogAnalyzer.java,402,428,temp,JHLogAnalyzer.java,311,346
//,3
public class xxx {
    private void updateJob(StringTokenizer tokens) throws IOException {
      while(tokens.hasMoreTokens()) {
        String t = tokens.nextToken();
        String[] keyVal = getKeyValue(t);
        if(keyVal.length < 2) continue;

        if(keyVal[0].equals("JOBID")) {
          if(JOBID == null)
            JOBID = new String(keyVal[1]);
          else if(!JOBID.equals(keyVal[1])) {
            LOG.error("Incorrect JOBID: "
                + keyVal[1].substring(0, Math.min(keyVal[1].length(), 100)) 
                + " expect " + JOBID);
            return;
          }
        }
        else if(keyVal[0].equals("JOB_STATUS"))
          JOB_STATUS = new String(keyVal[1]);
        else if(keyVal[0].equals("SUBMIT_TIME"))
          SUBMIT_TIME = Long.parseLong(keyVal[1]);
        else if(keyVal[0].equals("LAUNCH_TIME"))
          LAUNCH_TIME = Long.parseLong(keyVal[1]);
        else if(keyVal[0].equals("FINISH_TIME"))
          FINISH_TIME = Long.parseLong(keyVal[1]);
        else if(keyVal[0].equals("TOTAL_MAPS"))
          TOTAL_MAPS = Long.parseLong(keyVal[1]);
        else if(keyVal[0].equals("TOTAL_REDUCES"))
          TOTAL_REDUCES = Long.parseLong(keyVal[1]);
        else if(keyVal[0].equals("FINISHED_MAPS"))
          FINISHED_MAPS = Long.parseLong(keyVal[1]);
        else if(keyVal[0].equals("FINISHED_REDUCES"))
          FINISHED_REDUCES = Long.parseLong(keyVal[1]);
        else if(keyVal[0].equals("USER"))
          USER = new String(keyVal[1]);
      }
    }

};