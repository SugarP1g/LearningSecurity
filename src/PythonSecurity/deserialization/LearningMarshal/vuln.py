#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
https://stackoverflow.com/questions/26931919/marshal-unserialization-not-secure
"""

import base64
import marshal
import pickle
import py_compile


# pyc_path = py_compile.compile("./poc.py")
# print(pyc_path)
# f = open(pyc_path, "rb")
# content = f.read()
# print(content)
# result = marshal.dumps(content)
# print(result)
# load_result = marshal.loads(result)
# print(load_result)


def foo():
    import os
    return os.system('touch /tmp/marshal_deserialization')


code_serialized = marshal.dumps(foo.__code__)
print(code_serialized)
obj = marshal.loads(code_serialized)
print(type(obj))
