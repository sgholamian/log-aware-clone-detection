//,temp,DirectAgentAttache.java,295,350,temp,DirectAgentAttache.java,227,285
//,3
public class xxx {
        @Override
        protected void runInContext() {
            long seq = _req.getSequence();
            try {
                if (_outstandingCronTaskCount.incrementAndGet() >= _agentMgr.getDirectAgentThreadCap()) {
                    s_logger.warn("CronTask execution for direct attache(" + _id + ") has reached maximum outstanding limit(" + _agentMgr.getDirectAgentThreadCap() + "), bailing out");
                    bailout();
                    return;
                }

                ServerResource resource = _resource;
                Command[] cmds = _req.getCommands();
                boolean stopOnError = _req.stopOnError();

                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(log(seq, "Executing request"));
                }
                ArrayList<Answer> answers = new ArrayList<Answer>(cmds.length);
                for (int i = 0; i < cmds.length; i++) {
                    Answer answer = null;
                    Command currentCmd = cmds[i];
                    if (currentCmd.getContextParam("logid") != null) {
                        MDC.put("logcontextid", currentCmd.getContextParam("logid"));
                    }
                    try {
                        if (resource != null) {
                            answer = resource.executeRequest(cmds[i]);
                            if (answer == null) {
                                s_logger.warn("Resource returned null answer!");
                                answer = new Answer(cmds[i], false, "Resource returned null answer");
                            }
                        } else {
                            answer = new Answer(cmds[i], false, "Agent is disconnected");
                        }
                    } catch (Exception e) {
                        s_logger.warn(log(seq, "Exception Caught while executing command"), e);
                        answer = new Answer(cmds[i], false, e.toString());
                    }
                    answers.add(answer);
                    if (!answer.getResult() && stopOnError) {
                        if (i < cmds.length - 1 && s_logger.isDebugEnabled()) {
                            s_logger.debug(log(seq, "Cancelling because one of the answers is false and it is stop on error."));
                        }
                        break;
                    }
                }

                Response resp = new Response(_req, answers.toArray(new Answer[answers.size()]));
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(log(seq, "Response Received: "));
                }

                processAnswers(seq, resp);
            } catch (Exception e) {
                s_logger.warn(log(seq, "Exception caught "), e);
            } finally {
                _outstandingCronTaskCount.decrementAndGet();
            }
        }

};