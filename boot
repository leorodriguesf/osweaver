#!/usr/bin/env bash

set -e

OSWEAVER_DIR=~/.osweaver
OS_TYPE=$(uname -s)
ASCII_ART='                                                                
  ____    ________  _  __  ____  _____   ___  __  ____  _______ 
 /  _ \  /  ___/\ \/ \/ /_/ __ \ \__  \  \  \/ /_/ __ \ \_  __ \
(  <_> ) \___ \  \     / \  ___/  / __ \_ \   / \  ___/  |  | \/
 \____/ /____  >  \/\_/   \___  >(____  /  \_/   \___  > |__|   
             \/               \/      \/             \/         
'

cd ~

echo -e "$ASCII_ART"

case "$OS_TYPE" in
Darwin) 
    OS=macos
    ;;
Linux)
    OS=linux
    ;;
*)
    echo "OS $OS_TYPE not yet supported. Stopping"
    exit 1
    ;;
esac

DEPS_URL="https://raw.githubusercontent.com/leorodriguesf/osweaver/refs/heads/main/deps/$OS"

echo -e "=> $OS detected\n"

# Check if profile exists by testing the curl command
if ! curl --fail --silent $DEPS_URL > /dev/null 2>&1; then
    echo "$OS dependencies not found, please check if the dependencies exists on $DEPS_URL. Stopping"
    exit 1
fi

DEPS=$(curl --fail --silent $DEPS_URL)

echo "Installing $OS dependencies"

eval "$DEPS"

echo "$OS dependencies installed"

echo "Cloning repository..."

rm -rf $OSWEAVER_DIR

git clone https://github.com/leorodriguesf/osweaver.git $OSWEAVER_DIR >/dev/null

cd $OSWEAVER_DIR

echo "Installation starting..."

source $OSWEAVER_DIR/install $OS

cd - >/dev/null