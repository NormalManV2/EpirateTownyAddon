# NukeCap's Towny Cooldowns

## Introducing a towny addon (originally written for Epirate GeoPol) this simple & light weight towny addon comes with a painless cooldown system that eliminates the worry of people town hopping. It's equipped with a niche config where you can edit the cooldown time (in seconds, although the messages are formatted for **"... hr:min:sec ..."** default set at 24 hours)*,  as well as the messages sent to the players when the events are triggered. 

As you can see in the snippets below, the events that are triggered by this plugin are as follows: 

## TownPreAddResidentEvent
* Basic Functions:
 * If the player is currently on cooldown when joining a town, they will not be allowed to as well as sent the message(s). Either the default ugly ones (I am not very creative haha), or the ones you configure  (currently it's just basic & color code support / formatting)
 * If the player is not currently on cooldown, they will be allowed to join the town and then put on cooldown.


## TownPreInvitePlayerEvent
* Basic Functions:
 * If the player that's invited is currently on cooldown, the invite will be cancelled and the person who sent the invite will be informed of the invited player's cooldown.


## TownLeaveEvent
* Basic Functions:
 * If the player is on cooldown when leaving a town, they will not be allowed to do so and sent the message(s). Either the default ones or the ones you configure.
 * If the player is not currently on cooldown, they will be allowed to leave the town and then put on cooldown.

## Command / Usage
* Admin usage:
 * Permission node : "towny.cooldowns.admin"
 * /cda [start] | [remove] {Player Name} (duration in the case of starting a cooldown for the player) -> /cda start {playername} (duration) will start a townhop cooldown for the player for (duration, please keep in mind this value is in seconds) -> /cda remove {playername} will remove a cooldown from the player if they currently have a townhop cooldown.
* Player usage:
 * Permission node : "towny.coodlowns.check"
 * /cooldown -> returns their current cooldown time if any.


https://github.com/NukeCaps/EpirateTownyAddon/assets/106290271/409935f0-64a3-45f1-aedc-802c118240fc


https://github.com/NukeCaps/EpirateTownyAddon/assets/106290271/b831425a-64d6-4619-8903-7106c8d5e2dc


https://github.com/NukeCaps/EpirateTownyAddon/assets/106290271/664b9c3a-fb1e-4bec-9e41-e0412ca63619


### Suggestions&Notes : 
### if you'd like to make any suggestions or add notes / bug reports please join my discord : https://discord.gg/X5k8MQRRAF
### This is a project I've enjoyed creating and hope some of you may be able to get some use of it.
