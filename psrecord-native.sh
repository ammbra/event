#!/bin/bash
set -x
psrecord --plot plot-native.png --max-cpu 700 --max-memory 150 --include-children ./target/nativeeventschedule