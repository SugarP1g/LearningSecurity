## 0x00 简介

**Pyre**是Facebook推出的一款Python语言的高性能类型检查器。Pyre可以快速分析数百万行代码的代码库，找出程序调用间变量的类型错误问题。

**Pyre**工具中还集成了一个用于分析安全漏洞的静态扫描工具：**Pysa**。**Pysa**是基于**Pyre**的类型检查功能，通过静态污点分析技术，构建程序的调用图，从而发现程序执行流中的安全问题。

本文档专注于介绍pysa模块的使用，pyre可以参照reference学习。

## 0x01 运行原理

## 0x02 配置文件

```shell
.pyre_configuration
taint.config
XX1.pysa
XX2.pysa
...
```

### taint.config

`taint.config`文件存储了用户自定义的Source类型、Sink类型、Sanitizers类型和规则信息。

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

`.pysa`文件中存储了用户定义的Source、Sink和Sanitizers。用户可以定义多个`.pysa`文件。

## 0x03 交互式终端：SAPP

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

## 0x04 局限性

### 漏报问题

### 适用场景



## 0x05 Reference

- [pyre-check Github仓库](https://github.com/facebook/pyre-check)

- [pyre-check官方文档](https://pyre-check.org/docs/pysa-basics)

- [pysa工具使用指导视频-DEF CON 28SM](https://www.youtube.com/watch?v=8I3zlvtpOww)

