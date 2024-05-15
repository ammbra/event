#!/bin/bash
set -x
native-image --enable-preview -cp ./target -o ./target/nativeeventschedule EventSchedule