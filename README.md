# Computer Squad Bot
Discord bot for the computer squad server

Please read [Contributing](CONTRIBUTING.md)

# Build
Build with maven
```
mvn clean package
```

# Configuration
You should make a folder named "user" 
if it doesn't exist already in the same directory as the jar file.
Inside (make it if it doesn't exist already) a file named "configuration".
Replace the values in the lines with the information:
```
BOT Token
Owner ID
channel to send the alarm ping in
```

# Built with
Discord API wrapper for java JDA: https://github.com/DV8FromTheWorld/JDA 

JDA-utilities: https://github.com/JDA-Applications/JDA-Utilities

Wisp java scheduler: https://github.com/Coreoz/Wisp
