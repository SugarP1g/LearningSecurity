#!/usr/bin/env python
# -*- coding: utf-8 -*-

import json
import os

from flask import Flask
from flask import request

app = Flask(__name__)


@app.route('/os-system', methods=["POST"])
def os_system_cmd():
    data = request.get_data()
    params = json.loads(data)
    cmd = params.get("cmd")
    os.system(cmd)
    return 'execute success.'


@app.route('/os-system-join', methods=["POST"])
def os_system_cmd_join():
    data = request.get_data()
    params = json.loads(data)
    path = params.get("path")
    cmd = "ls -alt %s" % path
    os.system(cmd)
    return 'execute success.'


@app.route('/upload', methods=["GET"])
def upload():
    filename = request.args.get("file")
    print(filename)
    file_path = os.path.join("/tmp", filename.split('/')[-1])
    f = None
    try:
        f = open(file_path, 'w')
        f.write("aaaa")
    except Exception as e:
        print(str(e))
    finally:
        if f:
            f.close()
    return 'success'


@app.route('/upload-cmd', methods=["GET"])
def upload_cmd_inj():
    filename = request.args.get("file")
    print(filename)
    file_path = os.path.join("/tmp", filename.split('/')[-1])
    print(file_path)
    if os.path.exists(file_path):
        cmd = "mv %s /tmp/aaa" % file_path
        print(cmd)
        os.system(cmd)
    return "success"


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
