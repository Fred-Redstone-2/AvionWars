# AvionWars
A 1.12.2 recreation of the original gamemode as presented by FuzeIII in 2020! <br>
Go watch the [original video](https://youtu.be/BpKN7RDjZo4)!

All credits for the original idea to FuzeIII, Aywen and Gaspacho

## Description
A capture the flag integrated gamemode that implements all aspects of the game, from the flags themselves, the capture, the points systems and as much as I could! <br>
There are two teams, Green and Yellow, fighting to get points by briging the other team's flag back to their spawn or by killing them to get points and win the game!
I have to say that I'm _**not**_ an experienced mod developer, so any issue you might have may not be fixable by me specifically.

## Commands
- /join \<Team Name> : The current player joins the team, yellow or green
- /leave : The current player leaves the teams they were in
- /team status \<Team Name> : Displays information about the selected team (Members, Spawnpoint)
- /setSpawnpoint \<Team Name> : Sets the spawnpoint of the selected team; **MUST** be done **BEFORE** joining a team, otherwise the spawnpoint of the player will be the default one set in the config
- /start : Starts the game with the preparation phase first, then activates the capture phase!
- /reset : Resets the teams and the phases; basically sets the game back to zero
- /toggleBuilding : Toggles between being able to place and break blocks from the default state of not being able to
- /toggleExplosions : Toggles between the explosions destroying blocks or not
