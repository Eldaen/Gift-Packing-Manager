#!/bin/bash
set -euo pipefail
TASK_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$TASK_ROOT"
./gradlew :shared:exportSwiftPackage
xcodebuild -project iosApp/iosApp.xcodeproj -scheme iosApp \
    -destination "${IOS_DESTINATION:-platform=iOS Simulator,name=iPhone 17 Pro}" \
    -derivedDataPath "$TASK_ROOT/build/ios" "$@" build
