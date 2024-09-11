PROVIDES += "virtual/egl virtual/libgl virtual/libgles1 virtual/libgles2 virtual/libgles3"

RPROVIDES:${PN} += "libegl libgl libgles1 libgles2"
RPROVIDES:${PN}-dev += "libegl-dev libgl-dev libgles1-dev libgles2-dev libgles3-dev"
RCONFLICTS:${PN} = "libegl libgl ligbles1 libgles2"
RCONFLICTS:${PN}-dev += "libegl-dev libgl-dev libgles1-dev libgles2-dev libgles3-dev"
RREPLACES:${PN} = "libegl libgl libgles1 ligbles2"
RREPLACESS_${PN}-dev += "libegl-dev libgl-dev libgles1-dev libgles2-dev libgles3-dev"

