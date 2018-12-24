GoOut-Stalker is microservice application which watches out for new events of your favourite artists in the selected city. If new events are found, the application will send you the email. The application checks for new events every 12 hours and the email it send only if the results are different from the previous scan. Currently only events published on goout.net website are being taken into the consideration.


### Prerequisitse
Since the docker image of this application is not (yet) published in the Docker Hub you will need to build it yourself. For this, the prerequisities are:
 - docker (engine 18.09.0+)
 - docker-compose (3.7+)
 - mvn (3+)
 - jdk (8+)


### Sample usage
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
Now all it remain is to wait. If some events have been found, you should receive an email. Example:

```json
Hi,
 here are some new events for you. 

{
    "Aurora":[
        {
            "city":"Prague",
            "name":"Aurora + support: Vesna",
            "date":"2019-01-17T20:00",
            "url":"http://goout.net/en/concerts/aurora+support-vesna/xjgcd/+jojmj/",
            "venue":"Forum Karlín"
        }
    ],
    "Jessie":[
        {
            "city":"Prague",
            "name":"Jessie J",
            "date":"2019-04-23T20:00",
            "url":"http://goout.net/en/concerts/jessie-j/ygsud/+hovgl/",
            "venue":"Forum Karlín"
        }
    ],
    "dné":[
        {
            "city":"Prague",
            "name":"Barrandov a jeho slavné a prázdné vily",
            "date":"2019-01-06T10:00",
            "url":"http://goout.net/en/other-events/barrandov-a-jeho-slavne-a-prazdne-vily/ejwhd/+lmibl/",
            "venue":"Filmové ateliéry"
        },
        {
            "city":"Prague",
            "name":"Slavné a prázdné vily Vinohrad a Vršovic",
            "date":"2019-01-20T15:00",
            "url":"http://goout.net/en/other-events/slavne-a-prazdne-vily-vinohrad-a-vrsovic/civud/+hadhl/",
            "venue":"náměstí U Orionky"
        }
    ]
}
```
### Application properties

There are other properties which can influence the the application besides those present in the goout-stalker.env. Here is their exhaustive list:

Property name | Property explanation | Default Value
------------ | ------------- | -----------------
| NOTIFICATIONS_ENABLED | When set to true, emails notifications will be enabled. This requires additional mail related properties to be configured properly | false |
| SMTP_SERVER | Required when NOTIFICATIONS_ENABLED=true, i.e. smtp.gmail.com | N/A |
| SMTP_PORT | Requires when NOTIFICATIONS_ENABLED=true , i.e. 465 | N/A |
| MAIL_USERNAME | Required when NOTIFICATIONS_ENABLED=true, i.e. yourEmail@gmail.com | N/A |
| MAIL_PASSWORD | Required when NOTIFICATIONS_ENABLED=true, password for "yourEmail@gmail.com" | N/A |
| DB_PORT | Port for MongoDB, required | 27017 |
| MONGODB_DATABASE | Name of the MongoDB| goout-stalker |
| MONGODB_USER | User for MongoDB | goout-admin |
| MONGODB_PASSWORD | Password for MongoDB user | password1! | 
| DB_HOST | Mongo DB host | mongodb. Set automagically by docker networking |
| GO_OUT_CITY | City you want to scan, possible values are: Prague, Brno, Ostrava, Pilsen, Berlin, Warsaw, Cracow, Wroclaw | N/A |
TIMER_INTERVAL | Scanning interval in hours | 12 |
|TESTING | When testing is set to true, the scanning interval shrinks from hours to seconds | false |


