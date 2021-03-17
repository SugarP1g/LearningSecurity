#!/usr/bin/env python
# encoding: utf-8

"""
使用zipfile不会遭受zip slip攻击。虽然构造的恶意压缩包文件名包含"../"，但是使用zipfile解压时，仍然会将文件解压到目录文件，不会存在跨目录问题。
"""

import zipfile


def unzip_file(zip_src, dst_dir):
    r = zipfile.is_zipfile(zip_src)
    if r:
        fz = zipfile.ZipFile(zip_src, 'r')
        for file in fz.namelist():
            print(file)
            fz.extract(file, dst_dir)
    else:
        print('This is not zip')


if __name__ == "__main__":
    unzip_file("./evil.zip", "./")
