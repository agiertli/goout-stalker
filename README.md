GoOut-Stalker is microservice application which watches out for new events of your favourite artists in the selected city. If new events are found, the application will send you the email. The application checks for new events every 12 hours and the email it send only if the results are different from the previous scan. Currently only events published on goout.net website are being taken into the consideration.

Since the docker image of this application is not (yet) published in the Docker Hub you will need to build it yourself. For this, the prerequisities are:
 - docker (engine 18.09.0+)
 - docker-compose (3.7+)
 - mvn (3+)
 - jdk (8+)


 - Build the application
```bash
$ ./build.sh
```

This will built the binaries and store them under target/goout-stalker-thorntail.jar

 - Customize the application
   - Open the goout-stalker.env and uncomment the properties so they fit your needs.
   - Email integration is tested against gmail smtp server and in order to make this work you need to enable "Less secure apps" option in your gmail settings. More info [here](https://www.google.com/settings/security/lesssecureapps)
   - The email address provided will be the one used in authentication against the smtp server, as well as the one sending and receiving an email (this is by design)

 - Run the application:

```bash
$ docker-compose up
```