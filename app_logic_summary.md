# Robot Battle Arena - App Logic Summary

## Overview
Robot Battle Arena is an Android app using Kotlin and MVVM architecture with Room persistence. Users build robots from part templates, validate configurations, save/load robots, and simulate battles.

**Key Features:**
- Custom robot building with parts (mobility, weapon, armor, sensor)
- Validation: max 6 parts, at least one mobility
- Persistence: Room DB serializing parts as JSON
- Simple battle simulation in custom GameView (random movement, proximity damage)

## Architecture
See [architecture.excalidraw](./architecture.excalidraw) for high-level components:
- **UI Layer**: MainActivity (launcher), BuildActivity (build/edit), BattleActivity (battle), SavedRobotsActivity (list)
- **ViewModel**: RobotViewModel (robot state, parts management)
- **Repository**: RobotRepository (Gson serialization, CRUD)
- **Data**: RobotDao, AppDatabase (Room)

## Main User Flows
See [user_flow.excalidraw](./user_flow.excalidraw):
- Launch → MainActivity → BuildActivity (add parts, validate, save) or BattleActivity
- SavedRobotsActivity to load and edit
- Validation branches in Build and Battle

## Data Flows
See [data_flow.excalidraw](./data_flow.excalidraw):
- PartTemplate → ViewModel → Robot → Repository → SavedRobot (JSON) → DAO → DB
- Reverse for load
- Battle uses Robot directly via JSON intent

## Detailed Logic
### Robot Model
- `Robot`: name, mutable parts list (RobotPart), validate(), totals
- `SavedRobot`: DB entity with partsJson
- Templates in Robot companion

### BuildActivity
- ViewModel observes robot LiveData
- TODO: part selection UI
- Import/export JSON
- Load from intent robot_id

### BattleActivity
- Robot from intent JSON or default
- Pass to GameView.startBattle(player, enemy)
- GameView: SurfaceView thread, random walk circles, damage if dist<100

### Persistence
- Gson for parts (since Room entities can't have lists easily)
- Flow for live updates in SavedRobots RecyclerView

## Verification Steps
1. `./gradlew build` - compiles successfully
2. Run on emulator/device:
   - MainActivity buttons launch Build/Battle
   - In Build: edit name, (add parts via TODO), validate passes/fails
   - Save, go to SavedRobots (assume accessible), list shows, load back
   - Battle: valid robot battles enemy, health decreases
3. `./gradlew test` - unit tests pass (RobotTest)
4. `./gradlew connectedAndroidTest` - UI tests