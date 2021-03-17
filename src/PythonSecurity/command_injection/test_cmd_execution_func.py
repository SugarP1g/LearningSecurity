#!/usr/bin/env python
# encoding: utf-8

import pty

# 需要保证命令参数是列表类型
# 如果传入字符串，只能执行命令，无法带参数
pty.spawn("ls")

try:
    pty.spawn("ls -alt")
except Exception as e:
    print(str(e))

pty.spawn(["ls", "-alt"])
