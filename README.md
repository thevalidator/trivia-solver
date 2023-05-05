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
* [Video demonstration](#video-demonstration)
* [Guide](#guide)
    * [Run](#run)
    * [Additional arguments](#additional-arguments)
    * [Strategy mode](#strategy-mode)
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


+ **Quick start**
	1) Get the personal key
	2) Put the personal key file into the app folder
	3) Run `TriviaSolver.exe`
	4) Be sure to see the message in the console: `status: THE KEY IS ACTIVE`
	5) Add new person with the button `+` 
	6) Select the language server
	7) Select the topic
	8) Press “GO” button
	9) There are two options to stop the app:
		+ Hard stop - the app immediately stops its work. 
		+ Soft stop - the app finishes Trivia game round and then stops. (work not good at the moment).
------------

+ **Features**
	+ Headless mode (*Options -> Headless mode*)
		+ *If the headless mode is off the browser window will be opened during the programm work*
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
- **message "CRITICAL ERROR, THE APP WILL STOP"** 
	- The webdriver couldn't start. Update Chrome browser or use custom webdriver by using argument on app start. 

## Known bugs

- when headless mode is off it doesn't work if menu sidebar is hidden (this happens on small screen resolutions)


## Video demonstration

https://user-images.githubusercontent.com/53190369/232238176-b0d68ae6-627e-48db-b5df-290b6b5b6423.mp4



# Guide

### Run

- Windows
    1) Unzip the archive with the app
    2) Be sure the Google Chrome browser is already installed
    3) Put the personal key into the app folder
    4) Run `TriviaSolver.exe`
- Linux/MacOS
    1) Install [Java 17](https://adoptium.net/temurin/releases/) (JRE) or higher
    2) Unzip the archive with the app
    3) Be sure the Google Chrome browser is already installed
    4) Put the personal key into the app folder
    5) Run terminal from the app folder and use the command: `java -jar TriviaSolver.jar`

### Additional arguments

- *Browser args*
    - `-o`, `--origin` - browser option
    - `-w path`, `--webdriver path` - use custom webdriver, path - path to the driver

### App main window

<img width="602" alt="main" src="https://user-images.githubusercontent.com/53190369/236533441-c1c6b5a0-58b8-4489-8e0e-6c3ebd3443d0.png">


1. Menu
2. Add / remove person
3. Select person menu (stores 20 persons)
4. Select language server menu
5. Select topic menu
6. Strategy type menu
7. Start / Stop buttons
8. Log console
9. Stats

## Strategy mode

- ##### AutoAuto

	- You just need to start the app and it will do the rest. Use this mode to run the app 24 on 7.

	- Passive mode works only in auto mode. The app won’t buy unlim in this mode everytime it detects the person is out of TOP list. But it will buy unlim for sure in future if there is no any chance to get in to the top list.

- ##### Manual

	- Select this mode if:
    	- you don’t need to buy unlim 
    	- you want to buy the certain amount of unlims

	- If you don’t want to buy unlim just set the time to zero.
Or you may set the time for unlim play and the app will buy it.

## License
- GNU General Public License v3.0
