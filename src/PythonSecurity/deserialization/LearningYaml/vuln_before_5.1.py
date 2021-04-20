#!/usr/bin/env python
# -*- coding: utf-8 -*-

import yaml

poc = "!!python/object/apply:subprocess.check_output [[\"calc.exe\"]]"
# 等价于 LearningYaml.load(poc, Loader=LearningYaml.SafeLoader)
yaml.safe_load(poc)
