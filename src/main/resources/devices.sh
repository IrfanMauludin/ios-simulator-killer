#!/usr/bin/env bash
rm -rf src/main/resources/*.json
xcrun simctl list devices -j > src/main/resources/devices.json