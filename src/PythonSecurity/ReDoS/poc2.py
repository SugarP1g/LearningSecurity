#!/usr/bin/env python
# -*- coding: utf-8 -*-

import re
import time

pattern = re.compile(r'Current ACLs for resource `'
                     r'(?P<acl_type>\S*).*:'
                     r'(?P<acl_name>\S*).*`:')

re.X

begin = time.time()
text = 'Current ACLs for resource `' + ':' * 3456
pattern.search(text)
cost = (time.time() - begin) * 1000
print('%s takes %dms' % (text, cost))
