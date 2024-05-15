# Simple Event Schedule

This application loads sessions (from a .csv file) that occur at a conference and schedules them accordingly.
The following requirements are the base for modeling it:
* session can be a conference, a keynote, a workshop
* slots define event sessions with a timeframe
* a timeframe has a start and end time
* a conference has a title, a duration and a speaker
* a keynote has a title, a theme, a duration and a speaker
* a workshop has a title, steps to execute, a duration and a speaker

There are a few special features:
* for conference sessions you can determine their popularity using Structured Concurrency and by implementing `StructuredTaskScope<Conference>`.
The conference sessions gets to store the maximum popularity score. 
* workshops can have tags and those can be assigned upon creation using ScopedValues and Structured Concurrency.

To run this application you need minimum `JDK 22` installed on your machine and to input the following command in a terminal window:

```shell
java --enable-preview --source 22 src/EventSchedule.java
```

## Run with GraalVM Native Image

Package and build:
```shell
./package.sh
./native-compile.sh
```
Run as a native image:

```shell
./target/nativeeventschedule
```

Compare execution time, CPU and memory usage:

```shell
./time-jvm.sh
./time-native.sh
```