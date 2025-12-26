#!/bin/bash
# Local build script that mirrors the GitHub Actions workflow
# This script helps debug CI failures locally

set -e  # Exit on first error

echo "========================================"
echo "Local Build Test Script"
echo "Mirrors: .github/workflows/build.yml"
echo "========================================"
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check Java version
echo -e "${YELLOW}Step 1: Checking Java version (expecting JDK 17)${NC}"
if command -v java &> /dev/null; then
    java -version
else
    echo -e "${RED}ERROR: Java not found. Install JDK 17.${NC}"
    exit 1
fi
echo ""

# Check ANDROID_HOME/ANDROID_SDK_ROOT
echo -e "${YELLOW}Step 2: Checking Android SDK${NC}"
if [ -n "$ANDROID_HOME" ]; then
    echo "ANDROID_HOME=$ANDROID_HOME"
elif [ -n "$ANDROID_SDK_ROOT" ]; then
    echo "ANDROID_SDK_ROOT=$ANDROID_SDK_ROOT"
    export ANDROID_HOME=$ANDROID_SDK_ROOT
else
    echo -e "${YELLOW}WARNING: ANDROID_HOME/ANDROID_SDK_ROOT not set${NC}"
    echo "The build may fail if Android SDK is not configured"
fi
echo ""

# Grant execute permission for gradlew
echo -e "${YELLOW}Step 3: Ensuring gradlew is executable${NC}"
chmod +x gradlew
echo "Done"
echo ""

# Run the build with full stack trace (same as CI)
echo -e "${YELLOW}Step 4: Running './gradlew build --stacktrace'${NC}"
echo "This mirrors the CI build step exactly"
echo "========================================"
echo ""

# Run gradle build and capture exit code
./gradlew build --stacktrace 2>&1 | tee build-output.log
BUILD_EXIT_CODE=${PIPESTATUS[0]}

echo ""
echo "========================================"
if [ $BUILD_EXIT_CODE -eq 0 ]; then
    echo -e "${GREEN}BUILD SUCCESSFUL${NC}"
else
    echo -e "${RED}BUILD FAILED (exit code: $BUILD_EXIT_CODE)${NC}"
    echo ""
    echo "Build output saved to: build-output.log"
    echo ""
    echo "To see just the errors, run:"
    echo "  grep -A5 'error:' build-output.log"
    echo ""
    echo "To see test failures, check:"
    echo "  app/build/reports/tests/"
fi
echo "========================================"

exit $BUILD_EXIT_CODE
