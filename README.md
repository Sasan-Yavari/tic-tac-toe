# Tic Tac Toe 2.0

We want to bring the pen-and-paper game Tic-tac-toe to the digital age,
but with a little twist: the size of the playfield should be
configurable between 3x3 and 10x10. And we also want the symbols
(usually O and X) to be configurable. Also it should be for 3 players
instead of just 2.

This program created based on [Metronom](https://www.metronom.com/) home assignment task for hiring process.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You need followings to build the program:
- Latest version of gradle
- JDK 1.8+

You need followings to run the program:
- JVM 1.8+ or JDK 1.8+

### Installing

First of all run following commands to build the project:
```
git clone https://github.com/Sasan-Yavari/tic-tac-toe.git
cd tic-tac-toe
gradle installDist
```

Now you have the final build inside the following directory:
```
tic-tac-toe/build/install/tic-tac-toe
```

Now you can run the project using following command:
```
cd build/install/tic-tac-toe/bin
./run.sh
```

or simply
```
cd build/install/tic-tac-toe/bin
java -jar tic-tac-toe.jar
```

You can pass the configuration file path to the jar file like this:
```
java -jar tic-tac-toe.jar PATH_TO_CONFIG_FILE
```

## Running the tests

In order to run tests, inside the root of the project, run the following command:
```
gradle test
```

## Deployment

Copy the `tic-tac-toe` directory from `build/install` to where ever you want to deploy the build. This directory is the final runnable version of project.

## Built With

* [Gradle](https://gradle.org/) - Dependency Management

## Authors

* **Sasan Yavari** - *Initial work* - [Sasan-Yavari](https://github.com/Sasan-Yavari)