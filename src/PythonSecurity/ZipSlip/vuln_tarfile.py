#!/usr/bin/env python
# encoding: utf-8

"""
tarfile存在zip slip问题。
"""

import tarfile


def extract(tar_path, target_path):
    try:
        tar = tarfile.open(tar_path, "r:gz")
        file_names = tar.getnames()
        for file_name in file_names:
            print(file_name)
            tar.extract(file_name, target_path)
        tar.close()
    except Exception as e:
        raise e


if __name__ == "__main__":
    extract("./evil.tar.gz", "./")
