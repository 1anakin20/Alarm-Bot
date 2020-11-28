[![Build Status](https://travis-ci.com/1anakin20/ComputerSquadBot.svg?branch=main)](https://travis-ci.com/1anakin20/ComputerSquadBot)
# Alarm Bot
Discord bot to send a weekly ping at specified times.
If you would like to get involved in the project please read [Contributing](CONTRIBUTING.md).

# Build
Build with maven
```
mvn clean package
```

# Configuration
You should make a folder named "user" if it doesn't exist already in the same directory as the jar file.
 Inside (make it if it doesn't exist already) a file named "config.properties".
Copy paste this in the file and fill the values:
```
# Token for the bot account
botToken:
# Channel ID to alarm send the ping in
channelID: 
# ID for the owner of the bot
ownerID:
```

# Built with
Discord API wrapper for java JDA: https://github.com/DV8FromTheWorld/JDA 

JDA-utilities: https://github.com/JDA-Applications/JDA-Utilities

Wisp java scheduler: https://github.com/Coreoz/Wisp
