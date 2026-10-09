<!-- Paste into the CurseForge "Description" field. Summary (max 256 chars):
Rocket upgrade for Stellaris that burns Create: Diesel Generators gasoline and diesel, and a Create Spout fill for the Jet Suit chestplate. -->

# Diesel x Stellaris

A small bridge mod between **Stellaris**, **Create** and **Create: Diesel Generators**.

## Features

### Alternative Engine (rocket upgrade)
Put it into the engine slot of a Stellaris rocket and the rocket runs on buckets of
**gasoline** or **diesel** from Create: Diesel Generators. Range and fuel use are the same as with
regular Stellaris fuel. Rockets without it behave exactly as before, and the hydrogen engine still
runs on hydrogen only.

**Recipe**

| | | |
|---|---|---|
| | Electron Tube | |
| Engine Piston | Brass Sheet | Engine Piston |
| Steel Ingot | Steel Ingot | Steel Ingot |

Steel Ingot is the tag `c:ingots/steel`, so steel from other mods works too.

### Jet Suit fueling with the Create Spout
The Stellaris **Jet Suit chestplate** can be filled with **Create: Diesel Generators diesel** using a
**Spout**. 1 mB of CDG diesel = 1 mB of Stellaris diesel. Only diesel is accepted.
Stellaris diesel keeps working as before.

## Requirements
Minecraft 1.21.1, NeoForge 21.1.x, Java 21. Required on both client and server:
Create, Create: Diesel Generators, Stellaris, Architectury API, Potentials.

## FAQ
- **Does the Alternative Engine also accept Stellaris diesel buckets?** Yes, it uses Stellaris' diesel engine type.
- **Can I fill the Jet Suit with gasoline?** No, only diesel.
- **Where are the logs if something breaks?** Look for lines with `[diesel_x_stellaris]` in `latest.log`.

## Credits and license
Code: MIT. Requires Create, Create: Diesel Generators, Stellaris, Potentials and Architectury API,
which are separate projects by their own authors and are not included in this mod.

---

# Diesel x Stellaris (по-русски)

Небольшой мод-мост между **Stellaris**, **Create** и **Create: Diesel Generators**.

## Возможности

### Альтернативный двигатель (улучшение для ракеты)
Установите его в слот двигателя ракеты Stellaris, и она будет лететь на вёдрах **бензина** или **дизеля**
из Create: Diesel Generators. Дальность и расход такие же, как у обычного топлива Stellaris. Ракеты без него
работают как раньше, а водородный двигатель по-прежнему работает только на водороде.

**Рецепт:** электронная лампа сверху по центру; поршень двигателя, латунный лист, поршень двигателя посередине;
три стальных слитка (тег `c:ingots/steel`) снизу.

### Заправка реактивного скафандра дозатором
**Нагрудник реактивного скафандра** Stellaris можно заправить **дизелем из Create: Diesel Generators** с помощью
**дозатора** Create. 1 мБ дизеля CDG = 1 мБ дизеля Stellaris. Принимается только дизель.

## Требования
Minecraft 1.21.1, NeoForge 21.1.x, Java 21. Нужны и на клиенте, и на сервере:
Create, Create: Diesel Generators, Stellaris, Architectury API, Potentials.
