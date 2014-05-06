io.iron.ironworker.sendgrid.simple
==================================

Java example of using SendGrid from IronWorker (http://www.iron.io/worker): Send emails via Sendgrid (https://www.sendgrid.com). Use IronMQ or a webhook to "pump" emails to this IronWorker module.

This example tries to emphasize some development best practises (or at least my view of them) when using Iron.io's systems (IronMQ, IronCache and IronWorker) with Java.

* Using Maven to manage the project and act as the build tool.
* Configuration file "config.json" is included in the IronWorker code package; always isolate all possibly changeable variables outside of code.
* Using "semaphore" as a way to stop the program execution temporarily, if e.g. the SendGrid username and password need to be changed.
* Extensive logging with SLF4J (Simple Logging Facade for Java). Users can change the actual logging framework to their preferred framework, e.g. Log4J.
* Argument parsing with JCommander Java package (http://jcommander.org/).
* Those parts of configuration which will need to be changed periodically are read from IronCache.
* All configuration and payloads are inputed as JSON. JSON is parsed with Google's GSON library (https://code.google.com/p/google-gson/).
* Pack everything (all Java packages and their related configuration/deployment options) into one jar file to ensure the worker has all dependencies it needs. (Use Maven option *assembly:single* for this.)

How to deploy to IronWorker
---------------------------

You need:

1. Iron.io account.
2. SendGrid account.
3. Iron.io's upload tool *iron_worker* (http://dev.iron.io/worker/reference/cli/).
4. The following files:
	* *iron.json*, your iron.io's IronWorker project_id and token.
	* *config.json*, the semaphore's IronCache location and SendGrid account's username and password.
5. Maven build management tool (http://maven.apache.org/index.html).

Upload:

```bash
maven install assembly:single
iron_worker update single
```

