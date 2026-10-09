# Publishing on CurseForge

## 0. Before you start
- **Keep the GitHub repository PRIVATE.** `libs/` contains the Stellaris, Architectury and Potentials jars that are
  needed only to compile. Stellaris is licensed CC BY-NC-SA 4.0 (non-commercial), so its jar must not be published.
  The mod jar you upload to CurseForge does NOT contain any of them. Check with: open the jar as a zip, there must be no
  `com/st0x0ef`, `com/fej1fun` or `dev/architectury` folders.
- Put your own nickname into `mod_authors` and the MIT copyright line in `LICENSE`. Make sure you own the rights to
  the item texture.
- Check the Stellaris and Create project pages for any add-on or monetization rules.

## 1. Build
GitHub -> Actions -> Build mod -> Artifacts -> download `diesel_x_stellaris`. The file is `diesel_x_stellaris-1.5.2.jar`.

## 2. Test (all must pass)
1. Start the game with Create, Create: Diesel Generators, Stellaris, Architectury, Potentials and this mod.
   `latest.log` has `accepted by the Alternative Engine` (2 lines) and `The jet suit chestplate accepts CDG diesel`.
   No line with `[diesel_x_stellaris]` and `ERROR`.
2. Craft the Alternative Engine, put it into a rocket, fill with a CDG diesel bucket and a CDG gasoline bucket, fly.
3. Put a Jet Suit chestplate on a depot or belt under a Spout fed with CDG diesel: it fills. With gasoline or biodiesel it must not.
4. Wear the suit, check that flight uses the fuel.
5. Dedicated server with the same mods: starts, item is craftable, Spout filling works.

## 3. Create the project (CurseForge Author Console)
- Game: Minecraft, type: Mods. Name: `Diesel x Stellaris`. Slug: `diesel-x-stellaris`
  (if different, change `mod_url` and `mod_issues_url` in `gradle.properties` and rebuild).
- Summary: `Rocket upgrade for Stellaris that burns Create: Diesel Generators gasoline and diesel, plus Spout fueling for the Jet Suit.`
- Categories: Technology and Miscellaneous. License: MIT.
- Logo: `docs/curseforge_avatar_400.png`.
- Description: paste `docs/CURSEFORGE_DESCRIPTION.md`.
- Source/Issues links: optional. Do not link a private repository.

## 4. Upload the file
- File: the jar from step 1. Release type: Beta for the first upload, Release after it is confirmed to work.
- Game version: Minecraft 1.21.1. Mod loader: NeoForge. Java: 21. Environment: Client and Server.
- Changelog: paste the `1.5.2` section of `CHANGELOG.md`.
- Relations, Required dependency (search by name): Create, Create: Diesel Generators, Stellaris, Architectury API, Potentials.
- A new project goes through moderation, usually a few days.

## 5. Next releases
Raise `mod_version` in `gradle.properties`, add a section to `CHANGELOG.md`, push, download the new artifact, upload it as a new file.
