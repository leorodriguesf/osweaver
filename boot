#!/usr/bin/env bash

set -e

export OSWEAVER_DIR=~/.osweaver
OS_TYPE=$(uname -s)
ASCII_ART='                                                                

 ██████╗   ███████╗  ██╗    ██╗  ███████╗   █████╗   ██╗   ██╗  ███████╗  ██████╗ 
██╔═══██╗  ██╔════╝  ██║    ██║  ██╔════╝  ██╔══██╗  ██║   ██║  ██╔════╝  ██╔══██╗
██║   ██║  ███████╗  ██║ █╗ ██║  █████╗    ███████║  ██║   ██║  █████╗    ██████╔╝
██║   ██║  ╚════██║  ██║███╗██║  ██╔══╝    ██╔══██║  ╚██╗ ██╔╝  ██╔══╝    ██╔══██╗
╚██████╔╝  ███████║  ╚███╔███╔╝  ███████╗  ██║  ██║   ╚████╔╝   ███████╗  ██║  ██║
 ╚═════╝   ╚══════╝   ╚══╝╚══╝   ╚══════╝  ╚═╝  ╚═╝    ╚═══╝    ╚══════╝  ╚═╝  ╚═╝     
'

cd ~

echo -e "$ASCII_ART"

case "$OS_TYPE" in
Darwin) 
    OS=macos
    ;;
Linux)
    if [ ! -f /etc/os-release ]; then
        echo "Not able to detect Linux distro. Stopping"
        exit 1
    fi

    . /etc/os-release

    case "$ID" in
    ubuntu)
        OS=ubuntu
        ;;
    *)
        echo "Linux distro $NAME not supported. Stopping"
        exit 1
        ;;
    esac
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