# UrlRewriteFilter

This is a fork of the [UrlRewriteFilter project](https://github.com/paultuckey/urlrewritefilter) from Paul Tuckey.
The original project is no longer maintained, and this fork is intended to allow customization for Jahia needs (e.g. adding new features, fixing bugs, security vulnerabilities, etc.).

The main differences with the original project:
- The `main` branch renamed to `5.x`
- A `main` branch created from the `3.2.0` tag, as Jahia uses this version in its projects
- project restructured to use Maven (instead of Ant)
- Use of JDK 11 to build the project
- Upgrade of dependencies (`junit`, `servlet-api`, `slf4j-api`)
- Replace the `commons-logging` dependency with `commons-httpclient`
