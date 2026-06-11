# AxTravel
>Minecraft plugin that allows players to teleport, set homes and set warps
---
## Requirements
- Minecraft Paper 26.1.2 Server
- MariaDB / MySQL Server
---
## Features
- Cross-server functionality
- Commands
  - Home
  - Homes
  - SetHome
  - SetSpawn
  - SetWarp
  - Spawn
  - Teleport
  - TeleportHere
  - Warp
  - Warps
  - Spawn Listener
---
## Installation
1. Download the latest release
2. Put the `.jar` into the `/plugins` folder of every server to be synchronized
3. Start the server
4. Stop the server
5. Edit the `config.yml`
6. Start the server
---
## Configuration
```
# config.yml
database:
  host: localhost
  port: 3306
  database: axtravel
  username: root
  password:
```
```
# server.yml
server: server
```
---
## Commands & Permissions
- `/delhome` - `axtravel.delhome`
- `/delwarp` - `axtravel.delwarp`
- `/home` - `axtravel.home`
- `/homes` - `axtravel.homes`
- `/sethome` - `axtravel.sethome`
- `/setspawn` - `axtravel.setspawn`
- `/setwarp` - `axtravel.setwarp`
- `/spawn` - `axtravel.spawn`
- `/tp` - `axtravel.teleport`
- `/tphere` - `axtravel.teleporthere`
- `/tpaccept` - `axtravel.tpaccept`
- `/tpa` - `axtravel.tpa`
- `/tpdeny` - `axtravel.tpdeny`
- `/warp` - `axtravel.warp`
- `/warps` - `axtravel.warps`
## Support
Discord: [AxForge](https://discord.gg/rYSxV4daS8)
---
## License
AxTravel is licensed under the permissive MIT License. Please see [LICENSE](https://github.com/JauniKapauni/AxTravel/blob/master/LICENSE) for more info.