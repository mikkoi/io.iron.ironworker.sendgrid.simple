io.iron.ironworker.sendgrid.simple
==================================

Java example of using SendGrid from IronWorker: Send emails via Sendgrid. Use IronMQ to "pump" emails to this IronWorker module.


This example tries to emphasize some development best practises in relation to Iron.io systems and using Java with them.

* Using Maven to manage the project and act as the build tool.
* Configuration file "config.json" is included in the IronWorker code package; always isolate all possibly changeable variables outside of code.
* Using "semaphore" as a way to stop the program execution temporarily, if e.g. the SendGrid username and password need to be changed.
* Extensive logging with SLF4J (Simple Logging Facade for Java). Users can change the actual logging framework to their preferred framework, e.g. Log4J.
* Those parts of configuration which will need to be changed eventually are read from IronCache.
* All configuration and payloads are inputed as JSON. JSON is parsed with Google's [GSON library](https://code.google.com/p/google-gson/).

*** How to deploy to IronWorker

You need:

1. Iron.io account.
2. SendGrid account.
3. Iron.io's upload tool [iron_worker]{http://dev.iron.io/worker/reference/cli/}.
4. The following files:
	* iron.json, your iron.io's IronWorker project_id and token.
	* config.json, the semaphore's IronCache location and SendGrid account's username and password.
5. [Maven]{http://maven.apache.org/index.html} build management tool.

Upload:

'''maven install assembly:single
iron_worker update single
'''

