#!/usr/bin/env bash
set -euxo pipefail

# download Bitrise Build Cache CLI
curl -sSfL 'https://raw.githubusercontent.com/bitrise-io/bitrise-build-cache-cli/main/install/installer.sh' | sh -s -- -b /tmp/bin -d

# run the CLI to enable Bitrise build cache for Gradle
/tmp/bin/bitrise-build-cache enable-for gradle
