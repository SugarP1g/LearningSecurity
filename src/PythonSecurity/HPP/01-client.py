#!/usr/bin/env python
# -*- coding: utf-8 -*-

from urllib.parse import urlparse

o = urlparse('http://127.0.0.1:8000/test?param=123&param=456')
print(o.query)
print(type(o.query))
