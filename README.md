# oe-custom
Custom layer for gumstix build

Includes files and recipes for the original xbox (work in progress)

# Setup

See https://docs.yoctoproject.org/3.2.4/ref-manual/ref-system-requirements.html for information on base package required for your system.

```
repo init -u https://github.com/uglyoldbob/yocto-manifest.git -m xbox.xml
```

# Workarounds

For ubuntu, `sudo apparmor_parser -R /etc/apparmor.d/unprivileged_userns` works around an issue with operation not permitted with `/proc/self/uid_map`
