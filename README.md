# Galaxy Trivia solver
######  Trivia game solver for Galaxy



# Contents

<!-- START_TOC -->

* [General Information](#general-information)
    * [About](#about)
    * [Trivia game](#trivia-game)
    * [Requirements](#requirements)
* [Compile and Run](#compile-and-run)
* [App usage](#app-usage)
* [Troubleshooting](#troubleshooting)
* [Known bugs](#known-bugs)
* [License](#license)

<!-- END_TOC -->

# General Information


## About

This application can play the Trivia game on the [Galaxy](https://in-galaxy.com/en "Galaxy") website. With this app it is possible to get in the daily top-list and recieve the reward. It works only on PC. There are no any plans to create it for mobile phones.

## Trivia game

It's a kind of quiz. You choose the topic and then answer on 5 questions. Each question has four answers, your target is to choose the correct one. I found the [video of gameplay](https://www.youtube.com/watch?v=aHErRDo3Htc "video of gameplay")  somewhere on youtube.

## Requirements

- PC
- Java 17+ (JRE 17+)
- Google Chrome browser (ver.112+)

## Compile and Run

1. Download the project
2. Compile using command:
	+ for Linux: `./mvnw clean install`
	+ for Windows: `mvnw.cmd clean install`
3. The compiled artifact `TriviaSolver-jar-with-dependencies.jar` is created inside the folder `target`
4. Create a new folder for the app
5. Put there `TriviaSolver-jar-with-dependencies.jar`, `users.json` and `user.key` files
6. Run the app with command: `java -jar TriviaSolver-jar-with-dependencies.jar`

You may also download the archive with compiled app from the [release's page](https://github.com/thevalidator/trivia-solver/releases "release's page"). 

## App usage
- Be sure that you've already played at least once the Trivia game in Galaxy
- For Windows users: the app icon will be in the tray.

+ **Quick start**
	1) Check if the status is "GREEN" (*Menu -> Status*)
	2) Add your account (*Account -> Add account*)
	3) Choose server (*Options -> Server*)
	4) Choose topic (*Main window -> topic*)
	5) Press `START` button
	6) To stop the app you have two options:
		+ Hard stop - the app immediately stops its work. 
		+ Soft stop - the app finishes Trivia game round and then stops. (work not good at the moment).
------------

+ **Features**
	+ Headless mode (*Options -> Headless mode*)
		+ *If the headless mode is off the browser window will be opened during the program work*
	+ Anonymous mode (*Options -> Anonymous mode*)
		+ *Option to hide the nickname in the Trivia game*
	+ Passive mode (*Options -> Passive mode*)
		+ *In case of using the top list strategies buys unlimit mode only if needed at the end of the day*
	+ Two Top list strategies (*Options -> TOP list strategy*)
		+ *Stay in top strategy - will try to keep you in the top list by the end of the day*
		+ *Get in top strategy - will try to keep you on the top of the list by the end of the day*

## Troubleshooting

- **message ERROR: NO USER KEY DATA** 
	- Ask me about demo user  key. The app works using connection with the external server for getting correct answer. The user key is needed for getting response from the server.
- **status "BLACK"** 
	- No connection with the external server. Check your internet connection. 
- **status "RED"** 
	- The external server didn't find user key in the database. Contact me.
- **status "YELLOW"** 
	- Demo period is expired, the app will continue to work, but will choose random answers in the Trivia game.

## Known bugs

- when headless mode is off it doesn't work if menu sidebar is hidden (this happans on small screen resolutions)
- soft stop button doesn't work correct in some cases

## License
- GNU General Public License v3.0
