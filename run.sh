#!/usr/bin/env bash
# ==============================================================================
# Project 40 Companion App - Run on Device Script
# ==============================================================================
# Usage:
#   ./run.sh               # Auto-detects device/emulator, builds, installs, and launches
#   ./run.sh --logs        # Runs the app and streams filtered logcat output
#   ./run.sh <device-id>   # Targets a specific device serial
# ==============================================================================

set -euo pipefail

# ANSI Color Codes
BOLD="\033[1m"
GREEN="\033[1;32m"
CYAN="\033[1;36m"
YELLOW="\033[1;33m"
RED="\033[1;31m"
RESET="\033[0m"

APP_PACKAGE="com.project40.memories"
MAIN_ACTIVITY=".MainActivity"
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo -e "${CYAN}${BOLD}╔══════════════════════════════════════════════════════════════════╗${RESET}"
echo -e "${CYAN}${BOLD}║           PROJECT 40 COMPANION - BUILD & RUN RUNNER              ║${RESET}"
echo -e "${CYAN}${BOLD}╚══════════════════════════════════════════════════════════════════╝${RESET}"

# 1. Resolve Android SDK Location
SDK_DIR=""
if [ -f "$PROJECT_DIR/local.properties" ]; then
    SDK_DIR=$(grep -E '^[[:space:]]*sdk\.dir[[:space:]]*=' "$PROJECT_DIR/local.properties" | cut -d'=' -f2- | tr -d '\r' | xargs)
fi

if [ -z "$SDK_DIR" ] || [ ! -d "$SDK_DIR" ]; then
    if [ -n "${ANDROID_HOME:-}" ] && [ -d "$ANDROID_HOME" ]; then
        SDK_DIR="$ANDROID_HOME"
    elif [ -n "${ANDROID_SDK_ROOT:-}" ] && [ -d "$ANDROID_SDK_ROOT" ]; then
        SDK_DIR="$ANDROID_SDK_ROOT"
    elif [ -d "$HOME/Library/Android/sdk" ]; then
        SDK_DIR="$HOME/Library/Android/sdk"
    fi
fi

# Locate adb
ADB_BIN=""
if [ -n "$SDK_DIR" ] && [ -x "$SDK_DIR/platform-tools/adb" ]; then
    ADB_BIN="$SDK_DIR/platform-tools/adb"
elif command -v adb >/dev/null 2>&1; then
    ADB_BIN="$(command -v adb)"
fi

if [ -z "$ADB_BIN" ]; then
    echo -e "${RED}${BOLD}Error: Could not locate 'adb'.${RESET}"
    echo -e "Please ensure Android SDK platform-tools are installed or set ANDROID_HOME."
    exit 1
fi

echo -e "${GREEN}✓${RESET} Found ADB: ${CYAN}$ADB_BIN${RESET}"

# 2. Check Java Environment
if [ -z "${JAVA_HOME:-}" ] || [ ! -d "${JAVA_HOME:-}" ]; then
    if [ -d "/Applications/Android Studio.app/Contents/jbr/Contents/Home" ]; then
        export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
    elif command -v /usr/libexec/java_home >/dev/null 2>&1; then
        export JAVA_HOME="$(/usr/libexec/java_home -v 17 2>/dev/null || /usr/libexec/java_home 2>/dev/null || true)"
    fi
fi

if [ -n "${JAVA_HOME:-}" ]; then
    echo -e "${GREEN}✓${RESET} Java Home: ${CYAN}$JAVA_HOME${RESET}"
fi

# 3. Detect Connected Devices / Emulators
STREAM_LOGS=false
TARGET_DEVICE=""

for arg in "$@"; do
    if [ "$arg" == "--logs" ] || [ "$arg" == "-l" ]; then
        STREAM_LOGS=true
    elif [[ "$arg" != -* ]] && [ -z "$TARGET_DEVICE" ]; then
        TARGET_DEVICE="$arg"
    fi
done

# Get list of online devices
ONLINE_DEVICES=($("$ADB_BIN" devices | awk 'NR>1 && $2=="device" {print $1}'))

if [ ${#ONLINE_DEVICES[@]} -eq 0 ]; then
    echo -e "${YELLOW}No active devices or running emulators detected.${RESET}"
    
    # Check if any AVD emulators are installed to offer launching
    EMULATOR_BIN=""
    if [ -n "$SDK_DIR" ] && [ -x "$SDK_DIR/emulator/emulator" ]; then
        EMULATOR_BIN="$SDK_DIR/emulator/emulator"
    elif command -v emulator >/dev/null 2>&1; then
        EMULATOR_BIN="$(command -v emulator)"
    fi

    AVDS=()
    if [ -n "$EMULATOR_BIN" ]; then
        while IFS= read -r line; do
            [ -n "$line" ] && AVDS+=("$line")
        done < <("$EMULATOR_BIN" -list-avds 2>/dev/null || true)
    fi

    if [ ${#AVDS[@]} -gt 0 ]; then
        FIRST_AVD="${AVDS[0]}"
        echo -e "${CYAN}Found available emulator:${RESET} ${BOLD}$FIRST_AVD${RESET}"
        echo -e "${YELLOW}Starting emulator in the background...${RESET}"
        "$EMULATOR_BIN" -avd "$FIRST_AVD" -netdelay none -netspeed full >/dev/null 2>&1 &
        echo -e "Waiting for emulator device to connect..."
        "$ADB_BIN" wait-for-device
        
        # Wait for boot completion
        while [ "$("$ADB_BIN" shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" != "1" ]; do
            sleep 1
        done
        echo -e "${GREEN}✓ Emulator boot complete!${RESET}"
        ONLINE_DEVICES=($("$ADB_BIN" devices | awk 'NR>1 && $2=="device" {print $1}'))
    else
        echo -e "${RED}${BOLD}No connected Android device or AVD found.${RESET}"
        echo -e "Please either:"
        echo -e "  1. Connect a physical Android phone via USB (with USB Debugging enabled)."
        echo -e "  2. Start an emulator from Android Studio Device Manager."
        exit 1
    fi
fi

# Pick target device
if [ -z "$TARGET_DEVICE" ]; then
    TARGET_DEVICE="${ONLINE_DEVICES[0]}"
fi

DEVICE_MODEL=$("$ADB_BIN" -s "$TARGET_DEVICE" shell getprop ro.product.model 2>/dev/null | tr -d '\r' || echo "$TARGET_DEVICE")
echo -e "${GREEN}✓${RESET} Target Device: ${BOLD}${DEVICE_MODEL}${RESET} (${CYAN}$TARGET_DEVICE${RESET})"

# 4. Build Debug APK
echo -e "\n${CYAN}${BOLD}[1/3] Building Debug APK with Gradle...${RESET}"
chmod +x ./gradlew
./gradlew assembleDebug --no-daemon

APK_PATH="$PROJECT_DIR/app/build/outputs/apk/debug/app-debug.apk"
if [ ! -f "$APK_PATH" ]; then
    echo -e "${RED}Error: Build succeeded but APK was not found at $APK_PATH${RESET}"
    exit 1
fi

# 5. Install APK
echo -e "\n${CYAN}${BOLD}[2/3] Installing APK onto ${DEVICE_MODEL}...${RESET}"
"$ADB_BIN" -s "$TARGET_DEVICE" install -r -d "$APK_PATH"

# 6. Launch Main Activity
echo -e "\n${CYAN}${BOLD}[3/3] Launching Project 40 Companion...${RESET}"
"$ADB_BIN" -s "$TARGET_DEVICE" shell am start -n "${APP_PACKAGE}/${MAIN_ACTIVITY}" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER

echo -e "\n${GREEN}${BOLD}🎉 SUCCESS! Project 40 Companion is now running on ${DEVICE_MODEL}.${RESET}\n"

# 7. Optional Log Streaming
if [ "$STREAM_LOGS" = true ]; then
    echo -e "${CYAN}Streaming logcat output (Ctrl+C to stop)...${RESET}"
    "$ADB_BIN" -s "$TARGET_DEVICE" logcat -c
    "$ADB_BIN" -s "$TARGET_DEVICE" logcat -v time | grep -E --color=auto "$APP_PACKAGE|AndroidRuntime|FATAL"
else
    echo -e "Tip: Run ${BOLD}./run.sh --logs${RESET} to stream live app logs."
fi
