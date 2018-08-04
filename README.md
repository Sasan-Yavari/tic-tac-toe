# Tic Tac Toe 2.0

We want to bring the pen-and-paper game Tic-tac-toe to the digital age,
but with a little twist: the size of the playfield should be
configurable between 3x3 and 10x10. And we also want the symbols
(usually O and X) to be configurable. Also it should be for 3 players
instead of just 2.

This program created based on [Metronom](https://www.metronom.com/) home assignment task for hiring process.

## Design

All the classes inside this program are created to be testable, readable and maintainable and the program itself is easy to deploy.
Followings are the packages and layers of the program:

- `entity` package is the lowest layer of the program and it's classes does not know anything about the other layers.
They have no dependencies to other layers and others are depended on them. That is the deepest layer of the business
and changing it must happen rarely.

- `controller` package is the middle layer that depends on the business and does not know anything about the higher ones.
Classes in this layer created in a way that we can test them easily. We can change these classes simpler than business layer.
Just we have to know that the `ui` layer is depended to this and if we change this layer, we have to change the `ui` too.

- `ui` layer is the highest layer that depends on almost everything and we can change it easily because no layer is depended on it.
We have a simple `CommandLineUserInterface` inside this package and we can implement any type of user interfaces without
a need to change other layers. We can implement a `SwingUserInterface` or `WebUserInterface` without going deep inside the other layers.

- We have two common packages that we may use their classes everywhere. `exceptions` and `lang` packages have no logic inside
them and they are just message and exception providers.

### Design Patterns

- `Board` is implemented according to `Singleton` pattern.
- `CommandLineUserInterface` is implemented according to `Singleton` pattern.
- `Player` is implemented according to `Builder` pattern.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
Read the deployment section for notes on how to deploy the project on a live system.

### Prerequisites

You need followings to build the program:
- Latest version of [Gradle](https://gradle.org/)
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

otherwise, the application will use the `Config.properties` file that exists inside the `conf` directory.

## Deployment

Copy the `tic-tac-toe` directory from `build/install` to where ever you want to deploy the build. This directory is the final runnable version of project.

## Built With

* [Gradle](https://gradle.org/) - Dependency Management

## Authors

* **Sasan Yavari** - [Sasan-Yavari](https://github.com/Sasan-Yavari)