# NukeCap's Towny Cooldowns

## Introducing a towny addon (originally written for Epirate GeoPol) 
### This simple & light weight towny addon comes with a painless cooldown system that eliminates the worry of people town hopping. It's equipped with a niche config where you can edit the cooldown time (in seconds, although the messages are formatted for **"... hr:min:sec ..."** default set at 24 hours)*,  as well as the messages sent to the players when the events are triggered. 

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
 * If the player is not currently on cooldown, they will be allowed to leave the town.

## Command / Usage
* Admin usage:
 * Permission node : "towny.cooldowns.admin"
 * /cda [set] {Player Name} (duration (this can be 0 to reset a players cooldown)) -> /cda set {playername} (duration) will start a townhop cooldown for the player for (duration, please keep in mind this value is in seconds).
 * /cda [config] [reload] -> use this to update your changes from the config.yml
 * /cda [config] [save] -> use this to save the config.yml (keep in mind, when this plugin is shutdown it automatically saves the config. You may simply plugman reload this plugin after using the config reload command.)
* Player usage:
 * Permission node : "towny.coodlowns.check"
 * /cooldown -> returns their current cooldown time if any.

## Config.yml explanation:
```
cooldowns:
  duration: 86400  # input is seconds ie: 24 hours = 86400 seconds
  
# Having this value set to true will not allow the player to leave a town when their cooldown is active
  townLeaveEvent: false 

# Having this value set to true will not allow the player to join a town when their cooldown is acitve.
  townJoinEvent: true 

# Having this value set to true will allow the inviter to send an invitation to the target player even if their cooldown is active.
  townInviteEvent: true 

# Having this value set to true will send a message to the inviter if the person they invited is on cooldown.
  sendCooldownWarningToInviter: true

messages:
    onCooldown: "&cYou are on cooldown. Cannot join or leave another town."
    # Make sure to note, the placeholders available in the strings are only formatted for that string message, meaning you can only use the provided %% plaholders in their unique messages.
    remainingTime: "&eRemaining cooldown: %hours% hours."
    inviteCooldown: "&fCannot invite &c&l%player%&f as they are on cooldown. Remaining cooldown: &c&l%hours% &fhours."
  
```


https://github.com/NukeCaps/EpirateTownyAddon/assets/106290271/409935f0-64a3-45f1-aedc-802c118240fc


https://github.com/NukeCaps/EpirateTownyAddon/assets/106290271/b831425a-64d6-4619-8903-7106c8d5e2dc


https://github.com/NukeCaps/EpirateTownyAddon/assets/106290271/664b9c3a-fb1e-4bec-9e41-e0412ca63619


### Suggestions&Notes : 
### if you'd like to make any suggestions or add notes / bug reports please join my discord : https://discord.gg/X5k8MQRRAF
### This is a project I've enjoyed creating and hope some of you may be able to get some use of it.

(Spigot Download : https://www.spigotmc.org/resources/townhop-cooldown.114910/)
