# Changelog

## 1.6.0
- Stellaris Diesel Generator now accepts CDG diesel (1:1 → Stellaris diesel) and CDG biodiesel (1:0.5 → Stellaris diesel) as fuel, both via the bucket slot and via the Create Spout.
- Renamed the rocket upgrade to "Alternative Motor" (EN). Russian name unchanged.
- Redesigned the item texture to match the Stellaris motor family (same silhouette as the hydrogen motor, amber colour, fuel emblem).

## 1.5.2
- The Stellaris Jet Suit chestplate can now be filled with Create: Diesel Generators diesel using the Create Spout.
  1 mB of CDG diesel becomes 1 mB of Stellaris diesel inside the suit. Only diesel is accepted
  (gasoline, biodiesel and other fluids are rejected). Draining and flight behave as before.
- Potentials is now a declared required dependency (Stellaris already needs it).
- Startup self-test logs a clear error if a Stellaris update breaks one of the integrations.
- Added mod logo, license and publishing documents.

## 1.1.0
- Added the crafting recipe for the Alternative Engine
  (electron tube, 2 engine pistons, brass sheet, 3 steel ingots from the `c:ingots/steel` tag).

## 1.0.0
- Added the Alternative Engine rocket upgrade (`diesel_x_stellaris:gasoline_engine_upgrade`).
  Rockets with it burn buckets of gasoline and diesel from Create: Diesel Generators,
  with the same range and fuel use as regular Stellaris fuel.
