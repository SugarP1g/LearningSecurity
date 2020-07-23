#!/usr/bin/env python
# encoding: utf-8

import re
import time

# regex = r"^(a+)*$"
regex = r"^(([A-Za-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~])+)*$"
pattern = re.compile(regex)

for i in range(16, 30):
    begin = time.time()
    text = 'a' * i + '.'
    result = pattern.match(text)
    if result:
        print(result.groups())
    cost = (time.time() - begin) * 1000
    print('%s takes %dms' % (text, cost))
