## Gradle Wait Plugin

A plugin to wait on a service to become available.

Usage:

```
task waitForMyService(type: WaitFor) {
  host: myhost
  port: 8080
}
```
