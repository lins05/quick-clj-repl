#!/bin/bash

set -e

SCRIPT=$(dirname "${BASH_SOURCE[0]}")
SCRIPT_DIR=$(readlink -f $SCRIPT)

projdir=/opt/data/quick-clj

echo_run() {
    printf "\033[36m"
    echo -n "$ "
    echo -n "$@"
    printf "\033[m\n"

    "$@"
}

cmd_exists() {
    command -v $1 >/dev/null
}

init_projdir() {
    if ! [[ -d $projdir ]]; then
        mkdir -p $projdir
    fi
    cd $projdir
}

cp_or_rsync_dir() {
    if cmd_exists rsync; then
        echo_run \
            rsync \
            -a \
            -v \
            --debug=FILTER2 \
            --filter="dir-merge,- .gitignore" \
            --include=project.clj \
            --include=resources/ \
            --include=resources/* \
            --exclude=* \
            ${1}/ $2
    else
        echo_run cp -pra ${1}/* $2
    fi
}


install_project_clj() {
    if [[ $SCRIPT_DIR != "" ]] && [[ -e $SCRIPT_DIR/project.clj ]]; then
        cp_or_rsync_dir $SCRIPT_DIR $projdir
    else
        tmpdir=/tmp/quick-clj-repl
        rm -rf $tmpdir
        git clone --depth=1 https://github.com/lins05/quick-clj-repl.git $tmpdir
        cp_or_rsync_dir $tmpdir $projdir
    fi
}

install_lein() {
    if ! [[ -e lein ]]; then
        wget -q -O lein https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
        chmod +x lein
    fi
}

install_rlwrap() {
    if [[ $(uname) =~ "Linux" ]]; then
        platform=linux
    fi

    _sudo=
    if cmd_exists sudo; then
        _sudo=sudo
    fi
    if [[ $platform == "linux" ]]; then
        if ! cmd_exists rlwrap; then
            echo_run $_sudo apt-get install -y -q rlwrap
        fi
    fi
}


echo_run init_projdir
echo_run install_project_clj
echo_run install_lein
echo_run install_rlwrap

exit 0
echo_run rlwrap ./lein trampoline repl
