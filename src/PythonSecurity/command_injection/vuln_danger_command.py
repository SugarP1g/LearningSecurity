#!/usr/bin/env python
# encoding: utf-8

import re
import os


def checkInputParm(parm):
    if not parm:
        return True
    regStr = "^[a-zA-Z0-9\\s_.-]{0,255}$"
    patternStr = re.compile(regStr)
    if patternStr.match(parm) is None or ".." in parm:
        return False
    else:
        return True


def ip_netns_command(params):
    if checkInputParm(params):
        cmd = "ip netns exec " + params
        os.system(cmd)


if __name__ == "__main__":
    ip_netns_command("test ls -alt")
