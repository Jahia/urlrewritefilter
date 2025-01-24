# UrlRewriteFilter

This is a fork of the [UrlRewriteFilter project](https://github.com/paultuckey/urlrewritefilter) from Paul Tuckey.
The original project is no longer maintained, and this fork is intended to allow customization for Jahia needs (e.g. adding new features, fixing bugs, security vulnerabilities, etc.).

The main differences with the original project:
- `main` branch renamed to `5.x`
- `main` branch created from the `3.2.0` tag, as Jahia uses this version in its projects
- project restructured to use Maven (instead of Ant)
- JDK 11 used to build the project
- dependencies upgraded (`junit`, `servlet-api`, `slf4j-api`)
- `commons-logging` dependency replaced with `commons-httpclient`
- line endings changed to LF
