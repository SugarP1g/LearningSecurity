## 0x00 简介

![img](Python%E9%9D%99%E6%80%81%E6%B1%A1%E7%82%B9%E5%88%86%E6%9E%90%E5%B7%A5%E5%85%B7Pyre.assets/logo.png)

**Pyre**是Facebook推出的一款Python语言的高性能类型检查器。Pyre可以快速分析数百万行代码的代码库，找出程序调用间变量的类型错误问题。

**Pyre**工具中还集成了一个用于分析安全漏洞的静态扫描工具：**Pysa**。**Pysa**是基于**Pyre**的类型检查功能，通过静态污点分析技术，构建程序的调用图，从而发现程序执行流中的安全问题。

本文档专注于介绍pysa模块的使用，pyre可以参照reference学习。

污点分析技术通过定义代码中的`Sources`、`Sinks`、`Sanitizers`，然后通过分析程序的执行流、调用关系等方法确认`source`是否可以污染到`sink`。

>  本文中统一将`source`翻译成污染源、`sink`翻译成锚点、`Sanitizers`翻译成消减措施。

## 0x01 类型提示

Python是一种解释型的动态语言。Java和C语言在变量定义时会明确定义变量的类型，而Python不用定义变量的类型。这往往带来一种问题，程序员在调用函数时，函数定义的参数明明是list类型，却错误地传了dict类型的变量。Python在3.5版本引入了类型提示（Typing hints）的新特性，支持在变量定义时明确指定变量类型。这种新特性可帮助我们避免刚才描述的那种编码错误。Pyre正是基于类型提示特性实现的静态类型检查工具，pysa也是利用了pyre的类型检查功能，实现了代码的调用关系图构建。

关于Python的类型提示特性可参照：[`typing` --- 类型提示支持](https://docs.python.org/zh-cn/3/library/typing.html)。

## 0x02 配置文件

```shell
.pyre_configuration
taint.config
XX1.pysa
XX2.pysa
...
```

### taint.config

`taint.config`文件存储了用户自定义的污染源类型、锚点类型、消减措施类型和规则信息。

#### 污染源

我们可以在`taint.config`文件中定义不同的污染数据源类型。声明方式如下：

```json
"sources": [
    {
        "name": "Cookies",
        "comment": "used to annotate cookie sources"
    }
]
```

对每一个污染数据源类型，我们需要指定`name`和`comment`，前者是类型名，全局唯一，后者是注释信息，详细地描述类型的含义。

#### 锚点

和数据污染源类型定义相似，我们也可以在`taint.config`文件中定义不同的锚点类型。声明方式如下：

```json
"sinks": [
  {
    "name": "SQL",
    "comment": "use to annotate places of SQL injection risk"
  }
]
```

对每一个锚点类型，我们也需要指定`name`和`comment`，前者是类型名，全局唯一，后者是注释信息，详细地描述类型的含义。

#### 规则

有了污染源和锚点，我们接下来就需要定义规则，让pysa知道执行流从哪个污染源到哪个锚点是一个安全漏洞。规则的定义方式也很简单，声明方式如下：

```json
"rules": [
  {
    "name": "SQL injection.",
    "code": 1,
    "sources": [ "UserControlled" ],
    "sinks": [ "SQL" ],
    "message_format": "Data from [{$sources}] source(s) may reach [{$sinks}] sink(s)"
  }
]
```

同样，`name`指定了该条规则的名称，`code`是全局唯一的，每一条规则对应一个`code`。`sources`定义污染数据来源，可以指定多个；`sinks`也可以指定多个。`message_format`是详细信息，会在命中某条规则时显示。

#### 字符串方式匹配的污染源和锚点

>  这块内容应该是放在`.pysa`文件那里一起讲的。因为是定义在`taint.config`文件里，怕看文章的人搞混淆了，所以就在这提一下，等看完了`.pysa`文件的介绍，可以回过头来再看一下。

有时候我们想要定义的污染源或者锚点在代码中不是一个函数（属性），那么我们还可以采用字符串匹配的方式去定义污染源和锚点。定义方式如下：

```json
{
  "sources": [
    {
      "name": "IPAddress"
    }
  ],
  "implicit_sources": {
     "literal_strings": [
       {
         "regexp": "\\d{1,3}(\\.\\d{1,3})+",
         "kind": "IPAddress",
         "description": "String that looks like an IP address."
       }
     ]
  },
  "sinks": [
    {
      "name": "MayBeRendered"
    }
  ],
  "implicit_sinks": {
     "literal_strings": [
       {
         "regexp": "^<.*>$",
         "kind": "MayBeRendered",
         "description": "Indicates a string whose contents may be rendered."
       }
     ]
  }
}
```

- `regexp`值就是我们需要用来匹配特定字符串的正则表达式

- `kind`值对应我们定义的某一种污染源/锚点类型。

### .pyre_configuration

`.pyre_configuration`文件存储pysa运行的配置文件。`.pyre_configuration`文件内容如下：

```json
{
  "source_directories": [
    "."
  ],
  "taint_models_path": [
    "."
  ],
  "search_path": [
    "../../../stubs/"
  ],
  "exclude": [
    ".*/integration_test/.*"
  ]
}
```

`source_directories`指定需要分析的Python代码的目录。

```
"source_directories": ["."]
```

`taint_models_path`告诉Pysa在当前目录中查找`.pysa`/`taint.config` 文件。

```
"taint_models_path": ["."]
```

`search_path`告诉Pyre查找存根（stub）文件的地方。

```
"search_path": ["../../stubs/"]
```

### XX.pysa

刚才在上文介绍`taint.config`文件时，介绍了污染源类型、锚点类型和规则的定义。接下来还需要配置`.pysa`文件让pysa知道，哪些函数的返回值是污染源，哪些函数的参数是锚点。

`.pysa`文件中存储了用户定义的污染源、锚点和消减措施。用户可以定义多个`.pysa`文件。

#### 污染源

污染源的定义方式和Python类型提示语法一致，不同的是就是用我们之前定义的污染源类型替换Python官方定义的类型。函数污染源标注的方式如下所示：

```python
# Function return source
def django.http.request.HttpRequest.get_signed_cookie(
    self,
    key,
    default=...,
    salt=...,
    max_age=...
) -> TaintSource[Cookies]: ...

# Class attribute source:
django.http.request.HttpRequest.COOKIES: TaintSource[Cookies]
    
# Class source:
class BaseException(TaintSource[Exception]): ...
```

函数返回类型，类属性，甚至整个类都可以通过`TaintSource[SOURCE_NAME]`在任何要添加python类型的地方添加来声明为源：

如果整个类被标注为污染源的话，那么类的属性和函数的返回值都会被当成污染源。

可索引的类型（例如`Dict`，`List`和`Tuple`）被标记为污染源时，可以使用`AppliesTo`语法将索引类型的一部分标记为污染：

```python
def applies_to_index.only_applies_to_nested() -> AppliesTo[0, AppliesTo[1, TaintSource[Test]]]: ...

def applies_to_index.only_applies_to_a_key() -> AppliesTo["a", TaintSource[Test]]: ...
```

only_applies_to_nested函数返回的变量类型是List[int, List[int, Any]]。假设返回值是`[[a, b]]`，那么这里b将被标记为污染源。

only_applies_to_a_key函数返回的变量类型是Dict[str, Any]。假设返回值是`{'a': x}`，那么x将被标记为污染源。

> 注：`...`是用来忽略函数/类主体代码的。

#### 锚点

通过将`TaintSink[SINK_NAME]`将添加python类型的位置，可以将函数参数，类属性甚至整个类声明为锚点。如下所示：

```python
# Function parameter sink
def sqlite3.dbapi2.Cursor.execute(self, sql: TaintSink[SQL], parameters): ...

# Attribute sink
file_name.ClassName.attribute_name: TaintSink[RemoteCodeExecution]
```

如果有污染源能够污染`sqlite3.dbapi2.Cursor.execute`函数的sql参数的话，那么即发现了问题。

如果有污染源污染了`file_name.ClassName.attribute_name`属性，那么即发现了问题。

#### TITO(Taint In Taint Out)

TITO标记了在函数内部污染源是如何传播的，即函数的参数是如何污染到返回值的。

例如用污染的值更新字典时，Pysa需要`TaintInTaintOut`注释，该注释指示 `Updates[self]`：

```python
def dict.update(self, __m: TaintInTaintOut[Updates[self]]): ...
```

当对受污染对象的函数调用返回污染时，例如当您从字典中检索值时，Pysa需要一个`TaintInTaintOut`注释，该注释指示`LocalReturn`：

```python
def dict.get(self: TaintInTaintOut[LocalReturn], key, default): ...
```

#### 消减措施

在某些调用路径中，虽然检测到污染源传播到了锚点，但是在这条路径中，可能已经有某个函数对污染源做了严格的检查（举个例子，escape函数过滤了html的特殊字符），那么实际上这条路径也是不可用的。我们可以定义这类的函数为消减措施，使得pysa在分析时就可以自动筛掉无效的污染传播路径。

通过添加特殊的装饰器`@Sanitize`，将函数声明为消减措施。

```python
# This will remove any taint passing through a function, regardless of whether
# it is a taint source returned by this function, taint reaching sinks within
# the function via 'argument', or taint propagateing through 'argument' to the
# return value.
@Sanitize
def django.utils.html.escape(text): ...

# 将某个属性定义为消减措施
django.http.request.HttpRequest.GET: Sanitize
```

除了将整个函数声明为消减措施，pysa还支持更加细粒度的配置

```python
# This will remove any taint sources returned by this function, but allow taint
# to reach sinks within the function via 'argument' as well as allow taint to
# propagate through 'argument' to the return value.
@Sanitize(TaintSource)
def module.sanitize_source(argument): ...

# This remove any taint which passes through 'argument' to reach a sink within
# the function, but allow taint sources to be returned from the function as well
# as allow taint to propagate through 'argument' to the return value.
@Sanitize(TaintSink)
def module.sanitize_sink(argument): ...

# This will remove any taint which propagates through 'argument' to the return
# value, but allow taint sources to be returned from the function as well as
# allow taint to reach sinks within the function via 'argument'.
@Sanitize(TaintInTaintOut)
def module.sanitize_tito(argument): ...

# Sanitizes only the `UserControlled` source kind.
@Sanitize(TaintSource[UserControlled])
def module.return_not_user_controlled(): ...

# Sanitizes both the `SQL` and `Logging` sinks.
@Sanitize(TaintSink[SQL, Logging])
def module.sanitizes_sql_and_logging_sinks(flows_to_sql, logged_parameter): ...

# With this annotation, whenever `escape(data)` is called, the UserControlled taint of `data`
# will be sanitized, whereas other taint that might be present on `data` will be preserved.
@Sanitize(TaintInTaintOut[TaintSource[UserControlled]])
def django.utils.html.escape(text: TaintInTaintOut): ...

@Sanitize(TaintInTaintOut[TaintSink[SQL, Logging]])
def module.sanitize_for_logging_and_sql(): ...
```

## 0x03 运行原理

pysa主要的工作是计算所有函数的摘要。摘要（**Summaries**）主要有：

- 哪些函数的参数命中了sink；
- 哪些函数返回了source；
- 哪些参数传播污染到了返回值

>  这里以污染源举例说明pysa工作过程，锚点、TITO的分析和污染源分析过程类似，不再赘述。

假设我们已经定义了source函数的返回值为`UserControlled`类型的污染源。

```python
def source() -> TaintSource[UserControlled]: ...
```

pysa分析到下面的代码时，推测`returns_source`函数同样返回`UserControlled`类型的污染源。

```python
def returns_source():
  return source()
```

pysa生成`returns_source`函数的摘要如下：

```python
def returns_source() -> TaintSource[UserControlled]: ...
```

在下一轮迭代中，分析到如下代码，pysa推测`wraps_source`函数同样返回`UserControlled`类型的污染源。

```python
def wraps_source():
  return returns_source()
```

这些摘要覆盖了整个函数的调用图。也就是说，如果有一个函数`foo`调用了函数`bar`，`foo`的摘要就会包括到达`bar`的所有source和sink。pysa摘要的推断过程是迭代的。摘要不停地重新计算，直到没有摘要信息再更新。

![Combining summaries to find an issue](Python%E9%9D%99%E6%80%81%E6%B1%A1%E7%82%B9%E5%88%86%E6%9E%90%E5%B7%A5%E5%85%B7Pyre.assets/issue_visualization.png)

## 0x04 运行Pysa

使用`pyre analyze`命令即可运行pysa，具体参数如下：

```shell
Usage: pyre analyze [OPTIONS] [ANALYSIS]

  Run Pysa, the inter-procedural static analysis tool.

Options:
  --taint-models-path READABLE_DIRECTORY
                                  Location of taint models.
  --no-verify                     Do not verify models for the taint analysis.
  --save-results-to WRITABLE_DIRECTORY
                                  Directory to write analysis results to.
  --repository-root ABSPATH
  --find-missing-flows [obscure|type]
                                  Perform a taint analysis to find flows
                                  through obscure models.

  --dump-model-query-results      Provide model query debugging output.
  --use-cache                     Store information in .pyre/pysa.cache for
                                  faster runs.

  -h, --help                      Show this message and exit.
```

找个实际的例子执行一下。这里选了官方文档中的[例子](https://github.com/facebook/pyre-check/tree/master/documentation/pysa_tutorial/exercise1)。目录结构如下：

```shell
views.py
.pyre_configuration
sources_sinks.pysa
taint.config
```

其中，`views.py`为待测试的python代码，内容如下：

```python
from django.http import HttpRequest, HttpResponse


def operate_on_twos(request: HttpRequest) -> HttpResponse:
    operator = request.GET["operator"]

    result = eval(f"2 {operator} 2")  # noqa: P204

    return result
```

直接运行命令，运行结果如下所示，可以看到pysa发现了一条issue。

```shell
$ pyre analyze
ƛ No cached overrides loaded, computing overrides...
ƛ `google.protobuf.message.Message.ClearField` has 57 overrides, this might slow down the analysis considerably.
ƛ `google.protobuf.message.Message.__init__` has 58 overrides, this might slow down the analysis considerably.
ƛ `object.__eq__` has 53 overrides, this might slow down the analysis considerably.
ƛ `object.__init__` has 674 overrides, this might slow down the analysis considerably.
ƛ `type.__call__` has 127 overrides, this might slow down the analysis considerably.
ƛ `type.__init__` has 425 overrides, this might slow down the analysis considerably.
ƛ `type.__new__` has 70 overrides, this might slow down the analysis considerably.
[
  {
    "line": 12,
    "column": 18,
    "stop_line": 12,
    "stop_column": 35,
    "path": "views.py",
    "code": 5001,
    "name": "Possible RCE:",
    "description":
      "Possible RCE: [5001]: User specified data may reach a code execution sink",
    "long_description":
      "Possible RCE: [5001]: User specified data may reach a code execution sink",
    "concise_description":
      "Possible RCE: [5001]: User specified data may reach a code execution sink",
    "inference": null,
    "define": "views.operate_on_twos"
  }
]
```

## 0x05 交互式终端：SAPP

`pyre analyze`命令运行静态分析并将结果输出为JSON。静态分析后处理器（SAPP）工具可以处理这些结果，并允许用户浏览结果。

### 安装SAPP

SAPP作为单独的PyPI软件包提供，可以使用如下命令进行安装：

```shell
$ pip install fb-sapp
```

### 使用SAPP

```shell
$ pyre analyze --save-results-to ./
```

使用`--save-results-to`参数，指定的目录下会生成两个json文件：`taint-metadata.json`和`taint-output.json`。分别保存了pysa运行的metadata信息和检测结果信息。

```shell
$ sapp --database-name sapp.db analyze ./taint-output.json
```

解析JSON文件并将结果保存到本地sqlite文件`sapp.db`中。

```shell
$ sapp --database-name sapp.db explore
```

将启动连接到sqlite文件的自定义IPython接口。在这种模式下，可以开启交互式console。

### SAPP命令

- 查询命令（Information commands）
  - `help`：显示所有支持的命令
  - `help COMMAND`：显示特定命令的详细信息
  - `state`：显示状态和调试信息

- 展示命令（Display commands）
  - `runs`：展示静态分析运行的列表。sapp的sqlite数据库不会自动删除，所以每一次运行`pyre analyze`命令分析代码都会对应一个`run ID`，`runs`命令展示所有的run信息。
  - `issues`：列出所有检查出的issue信息。
  - `show`：显示issue的概览信息，使用该命令前需要先使用`issue ID`命令指定issue。
  - `frames`: 显示issue的调用栈信息，使用该命令前需要先使用`issue ID`命令指定issue。

- 选择命令（Selection commands）
  - `analysis_output DIR`: sets the location of the analysis output
  - `run ID`: select a specific run for browsing issues
  - `latest_run KIND`: select the latest run of the given kind
  - `issue ID`: select a specific issue for browsing a trace
  - `frame ID`: select a trace frame

- 跟踪命令（Trace commands）
  - `trace`: show a trace of the selected issue
  - `prev`/`p`: move backward within the trace
  - `next`/`n`: move forward within the trace
  - `jump NUM`: jump within a trace
  - `branch`: show and select alternative trace branches
  - `list`：显示源码。

- 调试命令（Debugging commands）
  - `parents`: show the callers of the current trace frame
  - `details`: additional details about the current trace frame

## 0x06 局限性

pysa基于pyre类型检查存储的类型信息实现污染源和锚点的识别。这要求被检测的程序要尽量的提高类型提示信息，而目前大部分人在写代码时，往往不会为变量添加类型提示信息。这造成了pysa生成的调用图存在不完整的可能，造成一定的漏报。

### 漏报场景

#### 丢失污染源/锚点

```python
from django.http import HttpRequest

def this_is_missed(request):
    # This flow WILL NOT be found, because Pysa does not know the type of
    # 'request' at this point and thus does not know 'request.GET' is a source
    # (even though the type is known in 'run')
    eval(request.GET["command"])

def this_is_caught(request: HttpRequest):
    # This flow WILL be found.
    eval(request.GET["command"])

def run(request: HttpRequest):
    this_is_missed(request)
    this_is_caught(request)
```

上述代码中，`this_is_missed`函数中eval危险函数不会被识别到，因为参数request类型没有被显式标记，pysa无法识别到该参数为HttpRequest类型。

#### 不完整的调用图

```python
from django.http import HttpRequest

class Runner:
    def run(self, command: str) -> None:
        eval(command)

def this_is_missed(request: HttpRequest, runner):
    # This flow WILL NOT be found, because Pysa does not know the type of
    # 'runner', and thus does not know where the 'run' call dispatches to
    runner.run(request.GET["command"])

def this_is_caught(request: HttpRequest, runner: Runner):
    # This flow WILL be found.
    runner.run(request.GET["command"])
```

上述代码中，`this_is_missed`函数到eval函数的这条调用链不会被识别到，因为参数runner类型没有被显式标记，pysa无法识别到该参数为Runner类型。

#### 全局变量

```python
user_controlled_data = ""

def load_data(request: HttpRequest) -> None:
    user_controlled_data = request.GET["data"]

def run_command(request: HttpRequest) -> None:
    load_data(request)
    eval(user_controlled_data)
```

为了实现并行处理，pysa限制了经过全局变量的污染流的分析能力。这导致在上述代码中，load_data到eval的这条污染流无法被识别。

## 0x07 总结

pysa是一款很优秀的针对Python语言的静态污点分析工具。因为python语言被设计时就是一款动态语言，导致相较于Java、C的同类型工具，pysa在漏报率方面表现更差。随着Python类型提示特性的普及，Pysa会越来越适用于安全测试。基于现状，pysa更适用于去快速发现大型项目中的安全问题，或者作为门禁工具融入到开发流程中的CI/CD场景中。

## 0x08 Reference

- [`typing` --- 类型提示支持](https://docs.python.org/zh-cn/3/library/typing.html)

- [pyre-check Github仓库](https://github.com/facebook/pyre-check)

- [pyre-check官方文档](https://pyre-check.org/docs/pysa-basics)

- [pysa工具使用指导视频-DEF CON 28SM](https://www.youtube.com/watch?v=8I3zlvtpOww)

