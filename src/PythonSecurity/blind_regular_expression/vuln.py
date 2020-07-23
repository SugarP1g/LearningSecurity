#!/usr/bin/env python
# encoding: utf-8

import random
import re
import string

from timeout_decorator import timeout, TimeoutError

SECRET = ''.join([random.choice(string.ascii_letters) for i in range(30)])
print(SECRET)


@timeout(2)
def search(r, s):
    return re.match(r, s)


def handle(r):
    try:
        search(r, SECRET)
        return True
    except TimeoutError as e:
        return False
    except Exception as e:
        return True
