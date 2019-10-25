# BookingGo Technical Test
Technical test for BookingGo application.

## Project Submission

### Setup

> **WARNING** This project uses Java 1.8 and will need JDK to build. In addition, the gradle wrapper may download the
> gradle distribution package, so make sure you have some space on your drive for that.

#### Windows
Make sure you have access to a command line or terminal that can run batch files with parameters.

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

#### Windows
Open a command line or a terminal and go to the project root. Run
```
gradlew.bat run --args="{pickup lat} {pickup lon} {dropoff lat} {dropoff lon} {capacity}"
```
*Note: Depending on which terminal/command line you use (like Command Prompt or MinGW bash), you may have to prefix the
command with a `./`.*

For more information on the parameters, go [here](#Information-on-the-parameters).

#### Linux
Open a terminal and go the project root. Run
```
./gradlew run --args="{pickup lat} {pickup lon} {dropoff lat} {dropoff lon} {capacity}"
```
For more information on the parameters, go [here](#Information-on-the-parameters).

### Part 2

#### Windows
Open a command line or a terminal and go to the project root. Run
```
gradlew.bat bootRun
```
*Note: Depending on which terminal/command line you use (like Command Prompt or MinGW bash), you may have to prefix the
command with a `./`.*

You can use a [web browser](#Web-Browser) to connect with the server.

#### Linux
Open a terminal and go the project root. Run
```
./gradlew bootRun
```
You can use a [web browser](#Web-Browser) or `curl`, if you have it installed, to connect with the server.

##### Using Curl
To guarantee you are viewing the response as a JSON payload, you can use the `application/json` content type. Run
```
curl --get --header="application/json" "http://{host}:8080/?pickup.lat={pickup lat}&pickup.lon={pickup lon}&dropoof.lat={dropoff lat}&dropoff.lon={dropoff lon}&capacity={capacity}"
```
For information on the parameters (including `{host}`) can be found [here](#Information-on-the-parameters).

#### Web Browser
Before going to this section, complete either the [Windows](#Windows-2) or the [Linux](#Linux-2) steps.

In the address bar, enter
```
http://{host}:8080/?pickup.lat={pickup lat}&pickup.lon={pickup lon}&dropoof.lat={dropoff lat}&dropoff.lon={dropoff lon}&capacity={capacity}
```

For information on the parameters (including `{host}`) can be found [here](#Information-on-the-parameters).

### Information on the parameters
* `{pickup lat}` - The pick up latitude. Restricted from -90 to 90 (inclusively).
* `{pickup lon}` - The pick up longitude. Restricted from -180 to 180 (inclusively).
* `{dropoff lat}` - The drop off latitude. Also, restricted from -90 to 90 (inclusively).
* `{dropoff lon}` - The drop off longitude. Also, restricted from -180 to 180 (inclusively).
* `{capacity}` - The number of seats required. Needs to be at least 1 seat required.
*  `{host}` *(Part 2 only)* -  The host name or domain name of the service. Will most likely be `localhost` if the
request is being made from the same machine.
 