#!/usr/bin/env python
# encoding: utf-8

from lxml import etree

from poc import generate_xml

billion_laughs_xml = generate_xml()
print("Evil xml file: %s" % billion_laughs_xml)

# fromstring函数作为入口函数直接报错了
# Traceback (most recent call last):
#   File "lxml_vuln.py", line 11, in <module>
#     root = etree.fromstring(billion_laughs_xml)
#   File "src/lxml/etree.pyx", line 3237, in lxml.etree.fromstring
#   File "src/lxml/parser.pxi", line 1896, in lxml.etree._parseMemoryDocument
#   File "src/lxml/parser.pxi", line 1777, in lxml.etree._parseDoc
#   File "src/lxml/parser.pxi", line 1082, in lxml.etree._BaseParser._parseUnicodeDoc
#   File "src/lxml/parser.pxi", line 615, in lxml.etree._ParserContext._handleParseResultDoc
#   File "src/lxml/parser.pxi", line 725, in lxml.etree._handleParseResult
#   File "src/lxml/parser.pxi", line 654, in lxml.etree._raiseParseError
#   File "<string>", line 1
# lxml.etree.XMLSyntaxError: Detected an entity reference loop, line 1, column 7
root = etree.fromstring(billion_laughs_xml)
