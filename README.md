# Strike Force
By "Anything please"

For TDT4240

Trello: https://trello.com/invite/b/KnJ83SPl/4f0ac661316da1feacb1cea2057fe597/tdt4240group15

Run the server with java 17: https://adoptium.net/.

Run the client with the java SDK bundled with android studio (no need to make changes)


To run server:
1. Connect to the NTNU network either physically or with a vpn
2. Load project and make sure the project is recognised as gradle project
3. Navigate to root folder and use the command: 
```java
.\gradlew bootRun
```
4. Gradle integration in ide: server->Tasks->application->bootRun
5. Current endpoint is localhost:8080 or http://10.0.2.2:8080/ if running an android emulator

To run the client:
1. Use android studio to start either an android emulator or a desktop launcher

To play:
1. Login with a username
2. To create a game write a random number (preferably large to avoid crashes with old games)
3. To join a game write the code provided from creating a game (as above). It is not necessarily the same code as the one used to create the game.
4. When everybody is ready, click start game. Everyone needs to click the button.
5. Select your actions and click submit
6. When everyone has submitted, the game is updated
7. Repeat til someone wins.

Even if the server is not hosted anywhere, you can still play multiplayer if everyone runs the server locally. This is because the server just updates the MySQL database.
