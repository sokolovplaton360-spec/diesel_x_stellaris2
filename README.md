# Diesel x Stellaris

Adds the "Alternative Engine" rocket upgrade (item `diesel_x_stellaris:gasoline_engine_upgrade`): Stellaris rockets burn
buckets of gasoline and diesel from Create: Diesel Generators. Also lets the Create Spout fill the Stellaris Jet Suit
chestplate with CDG diesel (1 mB = 1 mB).

See `CHANGELOG.md`, `docs/CURSEFORGE_DESCRIPTION.md` and `docs/PUBLISHING.md`.

The Gradle setup follows the official NeoForge MDK for 1.21.1 (ModDevGradle 2.0.142, `src/main/templates`,
`generateModMetadata`, `localRuntime`).

## Easiest: build on GitHub (nothing to install)
1. Create a PRIVATE repository on github.com (Stellaris is non-commercial licensed, its jar in `libs/` must stay private).
2. "Add file" -> "Upload files": drag everything from INSIDE this folder
   (build.gradle, settings.gradle, gradle.properties, libs, src, gradle, docs ...). Commit.
3. The folder `.github` is hidden and is often skipped by the browser upload. Check that the repo shows it.
   If not: "Add file" -> "Create new file", type the name `.github/workflows/build.yml`
   (typing the slashes creates the folders), paste the contents of that file from this archive, commit.
4. Open the "Actions" tab -> "Build mod" -> wait for the green check (3-6 minutes).
5. Open the finished run -> "Artifacts" -> download `diesel_x_stellaris` -> unzip -> put the .jar into `mods/`.

## Alternative: IntelliJ IDEA
1. Install JDK 21 (for example Temurin 21) and IntelliJ IDEA Community.
2. File -> Open -> select this folder. Trust the project.
3. Settings -> Build, Execution, Deployment -> Build Tools -> Gradle:
   Gradle JVM = 21, Distribution = use Gradle from 'gradle-wrapper.properties' file.
4. Wait for the sync to finish (first time: 5-15 minutes). Gradle panel -> Tasks -> build -> build.
5. The jar is in `build/libs/`.

## Troubleshooting
- "Could not find net.neoforged:neoforge:21.1.248": change `neo_version` in gradle.properties
  (the official MDK uses 21.1.235).
- "Missing file libs/...": the three jars (Stellaris, Architectury, Potentials) must be in `libs/`.
- Game start from the IDE (runClient) additionally needs Create and Create: Diesel Generators jars in `libs/`.
