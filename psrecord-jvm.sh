#!/bin/bash
set -x
psrecord --plot plot-jvm.png --max-cpu 700 --max-memory 150 --include-children "java --enable-preview -cp ./target EventSchedule"