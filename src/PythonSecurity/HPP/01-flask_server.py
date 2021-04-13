#!/usr/bin/env python
# -*- coding: utf-8 -*-

from flask import Flask
from flask import request

app = Flask(__name__)


@app.route('/')
def hello_world():
    passwd = request.args.get("passwd")
    print(type(passwd))
    print(passwd)
    return 'Hello, World!'


app.run(debug=True)
