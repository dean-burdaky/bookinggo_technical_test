# BookingGo Technical Test
Technical test for the BookingGo application. Contains a few subprojects:
1) Base aggregator library
2) Console application to pull Dave's Taxis services.
3) Console application to list best service options from multiple service suppliers.
4) Web-based REST application that functions in the same way as (3).

## Submission Instructions

### Setup

> **IMPORTANT** This project uses Java 1.8 and will need a JDK to build. In addition, the gradle wrapper may download the
> gradle distribution package, so make sure you have some space on your drive for that.

Gradle will pull all dependencies upon running build tasks.

#### Windows
Make sure you have access to a command line or terminal that can run batch files with parameters. Also, how you execute
batch scripts may vary on the command line or terminal you use, refer to your command line's/terminal's documentation.

#### Linux
Make sure you have access to a terminal that can run shell scripts with parameters.

Depending on which commit you pulled/downloaded, you may need to set executable file permissions to the gradlew files.
If you pulled the latest commit then it is most likely you won't have to worry about this step.

Open a terminal and go to the project root directory. You can check if the `gradlew` files are executable with `ls -l`.
You should be looking for the file name `gradlew` and to the far left you should see a sequence of 10 characters. If the
rightmost character of the sequence is `x` then the `gradlew` files are executable and you are ready to move onto
[Part 1](#Part-1) and [Part 2](#Part-2).

If you don't see `x` then you may instead see `-`. Run 
```
chmod +x gradlew */gradlew
```
to change the `gradlew` file permissions to make them executable. Run `ls -l` again and you should see that the
rightmost character is now `x`.

### Part 1

#### Console application to print the search results for Dave's Taxis
Open a command line or a terminal and go to the project root. Run
```
WINDOWS: gradlew.bat :dave_console_app:run --args="{pickup lat} {pickup lon} {dropoff lat} {dropoff lon}"
LINUX: ./gradlew :dave_console_app:run --args="{pickup lat} {pickup lon} {dropoff lat} {dropoff lon}"
```
For more information on the parameters, go [here](#Information-on-the-parameters).

#### Console application to filter by number of passengers
Open a command line or a terminal and go to the project root. Run
```
WINDOWS: gradlew.bat :console_app:run --args="{pickup lat} {pickup lon} {dropoff lat} {dropoff lon} {capacity}"
LINUX: ./gradlew :console_app:run --args="{pickup lat} {pickup lon} {dropoff lat} {dropoff lon} {capacity}"
```
For more information on the parameters, go [here](#Information-on-the-parameters).

### Part 2
Open a command line or a terminal and go to the project root. Run
```
WINDOWS: gradlew.bat bootRun
LINUX: ./gradlew bootRun
```
You can use a web browser or, if you have `curl` installed (mainly linux/unix feature), run that
instead to connect to the server.

#### Web browser
Open a web browser and in the address bar, enter
```
http://{host}:8080/?pickup.lat={pickup lat}&pickup.lon={pickup lon}&dropoof.lat={dropoff lat}&dropoff.lon={dropoff lon}&capacity={capacity}
```
Information on the parameters can be found [here](#Information-on-the-parameters).

##### Example
```
http://localhost:8080/?pickup.lat=10&pickup.lon=135.5&dropoff.lat=-90.00&dropoff.lon=0.00127&capacity=3
```

#### Curl command
In a command line or terminal, run
```
curl -G -H "application/json" "http://{host}:8080/?pickup.lat={pickup lat}&pickup.lon={pickup lon}&dropoof.lat={dropoff lat}&dropoff.lon={dropoff lon}&capacity={capacity}"
```
Information on the parameters can be found [here](#Information-on-the-parameters).

##### Example
```
curl -G -H "application/json" "http://localhost:8080/?pickup.lat=10&pickup.lon=135.5&dropoff.lat=-90.00&dropoff.lon=0.00127&capacity=3"
```

### Information on the parameters
* `{pickup lat}` - The pick up latitude. Restricted from -90 to 90 (inclusively).
* `{pickup lon}` - The pick up longitude. Restricted from -180 to 180 (inclusively).
* `{dropoff lat}` - The drop off latitude. Also, restricted from -90 to 90 (inclusively).
* `{dropoff lon}` - The drop off longitude. Also, restricted from -180 to 180 (inclusively).
* `{capacity}` - The number of seats required. Needs to be at least 1 seat required.
* `{host}` - The host name or domain name of the service. Will most likely be `localhost` if the request is being made
  from the same machine.