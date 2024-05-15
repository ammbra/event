#!/usr/bin/env bash
set -x
perf stat java --enable-preview -cp ./target EventSchedule