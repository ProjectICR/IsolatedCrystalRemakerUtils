#From https://github.com/ProjectHDS/HerodotusUtils/blob/main/build.sh

BUILD=icrutils
VERSION=1.0

FILE_NAME="$BUILD-$VERSION.jar"
FILE_NAME_DEV="$BUILD-$VERSION-build${GITHUB_RUN_NUMBER}.jar"

mv "$GITHUB_WORKSPACE/build/libs/$FILE_NAME" "$GITHUB_WORKSPACE/artifacts/$FILE_NAME_DEV"