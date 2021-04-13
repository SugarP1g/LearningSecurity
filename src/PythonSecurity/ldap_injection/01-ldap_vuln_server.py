#!/usr/bin/env python
# -*- coding: utf-8 -*-

from flask import request
import ldap


@app.route("/user")
def user():
    dn = request.args['dn']
    username = request.args['username']

    search_filter = "(&(objectClass=*)(uid=" + username + "))"
    ldap_connection = ldap.initialize("ldap://127.0.0.1:389")
    user = ldap_connection.search_s(dn, ldap.SCOPE_SUBTREE, search_filter)  # Noncompliant
    return user[0]
