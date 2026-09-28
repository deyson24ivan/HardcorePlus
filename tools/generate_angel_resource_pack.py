import json
import os
import shutil
import struct
import zipfile
import zlib


ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
PACK_DIR = os.path.join(ROOT, "HardcorePlus-ResourcePack")
ZIP_PATH = os.path.join(ROOT, "HardcorePlus-Custom-Armor-v0.23.zip")
NS = "hardcoreplus"
VANILLA_TEXTURES = os.path.join(ROOT, ".vanilla-textures", "assets", "minecraft", "textures")
PIECES = ("helmet", "chestplate", "leggings", "boots")
GEAR_TYPES = ("sword", "axe", "bow", "pickaxe", "trident", "spear")
GEAR_TEXTURE_SIZE = 32
ARMOR_ICON_TEXTURE_SIZE = 64
MATERIAL_TEXTURE_SIZE = 16
SPECIAL_TEXTURE_SIZE = 32
GEAR_BASE_FILES = {
    "sword": "netherite_sword",
    "axe": "netherite_axe",
    "bow": "bow",
    "pickaxe": "netherite_pickaxe",
    "trident": "trident",
}


SETS = [
    {
        "id": "angel",
        "source": "diamond",
        "shadow": (126, 118, 108, 255),
        "mid": (214, 207, 190, 255),
        "light": (255, 252, 234, 255),
        "accent": (224, 159, 34, 255),
        "accent2": (82, 28, 128, 255),
        "glow": (255, 232, 130, 255),
        "dark": (9, 7, 18, 255),
        "pattern": "angel",
    },
    {
        "id": "titanium",
        "source": "diamond",
        "shadow": (70, 61, 88, 255),
        "mid": (176, 160, 176, 255),
        "light": (255, 239, 197, 255),
        "accent": (251, 237, 164, 255),
        "accent2": (66, 42, 91, 255),
        "glow": (255, 255, 204, 255),
        "dark": (31, 25, 51, 255),
        "pattern": "titanium",
    },
    {
        "id": "leviathan",
        "source": "netherite",
        "shadow": (5, 55, 58, 255),
        "mid": (16, 116, 112, 255),
        "light": (72, 189, 172, 255),
        "accent": (37, 205, 120, 255),
        "accent2": (12, 77, 93, 255),
        "glow": (136, 250, 225, 255),
        "dark": (2, 24, 29, 255),
        "pattern": "leviathan",
    },
    {
        "id": "phoenix",
        "source": "netherite",
        "shadow": (73, 36, 26, 255),
        "mid": (161, 76, 31, 255),
        "light": (255, 156, 46, 255),
        "accent": (255, 213, 78, 255),
        "accent2": (102, 24, 18, 255),
        "glow": (255, 93, 26, 255),
        "dark": (31, 20, 20, 255),
        "pattern": "phoenix",
    },
    {
        "id": "void",
        "source": "diamond",
        "shadow": (77, 102, 119, 255),
        "mid": (180, 204, 211, 255),
        "light": (239, 248, 247, 255),
        "accent": (87, 207, 225, 255),
        "accent2": (35, 67, 90, 255),
        "glow": (181, 247, 255, 255),
        "dark": (16, 32, 48, 255),
        "pattern": "void",
    },
    {
        "id": "dragon",
        "source": "netherite",
        "shadow": (54, 48, 58, 255),
        "mid": (91, 71, 94, 255),
        "light": (139, 105, 82, 255),
        "accent": (151, 72, 180, 255),
        "accent2": (112, 72, 42, 255),
        "glow": (202, 121, 227, 255),
        "dark": (28, 22, 32, 255),
        "pattern": "dragon",
    },
    {
        "id": "chaos",
        "source": "netherite",
        "shadow": (37, 35, 40, 255),
        "mid": (73, 48, 50, 255),
        "light": (125, 75, 67, 255),
        "accent": (210, 28, 38, 255),
        "accent2": (81, 14, 24, 255),
        "glow": (255, 62, 76, 255),
        "dark": (12, 10, 14, 255),
        "pattern": "chaos",
    },
    {
        "id": "celestial",
        "source": "netherite",
        "shadow": (44, 49, 55, 255),
        "mid": (79, 88, 90, 255),
        "light": (151, 160, 142, 255),
        "accent": (43, 221, 216, 255),
        "accent2": (246, 192, 62, 255),
        "glow": (112, 255, 247, 255),
        "dark": (18, 21, 27, 255),
        "pattern": "celestial",
    },
    {
        "id": "eclipse",
        "source": "netherite",
        "shadow": (61, 37, 43, 255),
        "mid": (111, 66, 75, 255),
        "light": (181, 119, 151, 255),
        "accent": (219, 67, 236, 255),
        "accent2": (245, 202, 47, 255),
        "glow": (252, 111, 255, 255),
        "dark": (19, 11, 21, 255),
        "pattern": "eclipse",
    },
    {
        "id": "time",
        "source": "netherite",
        "shadow": (28, 51, 58, 255),
        "mid": (65, 86, 91, 255),
        "light": (130, 129, 112, 255),
        "accent": (244, 189, 42, 255),
        "accent2": (50, 220, 224, 255),
        "glow": (133, 255, 255, 255),
        "dark": (15, 20, 26, 255),
        "pattern": "time",
    },
    {
        "id": "infinity",
        "source": "diamond",
        "shadow": (39, 37, 89, 255),
        "mid": (56, 74, 188, 255),
        "light": (82, 226, 241, 255),
        "accent": (187, 62, 240, 255),
        "accent2": (60, 242, 184, 255),
        "glow": (170, 240, 255, 255),
        "dark": (13, 12, 36, 255),
        "pattern": "infinity",
    },
    {
        "id": "colossus",
        "source": "netherite",
        "shadow": (61, 61, 58, 255),
        "mid": (105, 103, 96, 255),
        "light": (163, 156, 137, 255),
        "accent": (160, 111, 52, 255),
        "accent2": (93, 82, 72, 255),
        "glow": (218, 167, 83, 255),
        "dark": (31, 31, 30, 255),
        "pattern": "colossus",
    },
]


MATERIALS = [
    {
        "id": "angel_essence",
        "base": "echo_shard",
        "shadow": (118, 105, 129, 255),
        "mid": (225, 216, 204, 255),
        "light": (255, 250, 224, 255),
        "accent": (230, 177, 48, 255),
        "accent2": (86, 42, 132, 255),
        "glow": (255, 242, 143, 255),
        "dark": (43, 34, 62, 255),
        "pattern": "angel_essence",
    },
    {
        "id": "titanium_ingot",
        "base": "iron_ingot",
        "shadow": (67, 64, 78, 255),
        "mid": (175, 171, 173, 255),
        "light": (255, 238, 198, 255),
        "accent": (238, 219, 155, 255),
        "accent2": (69, 48, 89, 255),
        "glow": (255, 255, 214, 255),
        "dark": (30, 28, 43, 255),
        "pattern": "titanium_ingot",
    },
    {
        "id": "sea_essence",
        "base": "heart_of_the_sea",
        "shadow": (4, 56, 67, 255),
        "mid": (24, 139, 132, 255),
        "light": (101, 221, 194, 255),
        "accent": (29, 219, 126, 255),
        "accent2": (18, 93, 113, 255),
        "glow": (155, 255, 231, 255),
        "dark": (1, 24, 31, 255),
        "pattern": "sea_essence",
    },
    {
        "id": "phoenix_feather",
        "base": "feather",
        "shadow": (95, 38, 21, 255),
        "mid": (202, 80, 27, 255),
        "light": (255, 164, 48, 255),
        "accent": (255, 221, 78, 255),
        "accent2": (120, 24, 18, 255),
        "glow": (255, 91, 23, 255),
        "dark": (40, 20, 18, 255),
        "pattern": "phoenix_feather",
    },
    {
        "id": "dragon_scale",
        "base": "turtle_scute",
        "shadow": (33, 24, 45, 255),
        "mid": (72, 48, 88, 255),
        "light": (135, 95, 123, 255),
        "accent": (165, 82, 196, 255),
        "accent2": (99, 65, 42, 255),
        "glow": (213, 143, 226, 255),
        "dark": (12, 9, 18, 255),
        "pattern": "dragon_scale",
    },
    {
        "id": "void_fragment",
        "base": "amethyst_shard",
        "shadow": (81, 103, 120, 255),
        "mid": (183, 205, 211, 255),
        "light": (245, 251, 249, 255),
        "accent": (90, 210, 232, 255),
        "accent2": (31, 64, 91, 255),
        "glow": (184, 250, 255, 255),
        "dark": (12, 26, 45, 255),
        "pattern": "void_fragment",
    },
    {
        "id": "golem_heart",
        "base": "heart_of_the_sea",
        "shadow": (65, 63, 58, 255),
        "mid": (113, 109, 96, 255),
        "light": (180, 170, 139, 255),
        "accent": (161, 111, 51, 255),
        "accent2": (196, 35, 34, 255),
        "glow": (255, 88, 58, 255),
        "dark": (30, 29, 27, 255),
        "pattern": "golem_heart",
    },
    {
        "id": "solar_essence",
        "base": "glowstone_dust",
        "shadow": (68, 40, 41, 255),
        "mid": (178, 94, 97, 255),
        "light": (238, 145, 184, 255),
        "accent": (229, 67, 238, 255),
        "accent2": (246, 203, 47, 255),
        "glow": (255, 249, 123, 255),
        "dark": (30, 16, 26, 255),
        "pattern": "solar_essence",
    },
    {
        "id": "chaos_fragment",
        "base": "netherite_scrap",
        "shadow": (36, 34, 39, 255),
        "mid": (82, 48, 49, 255),
        "light": (144, 71, 69, 255),
        "accent": (216, 27, 39, 255),
        "accent2": (83, 10, 22, 255),
        "glow": (255, 66, 82, 255),
        "dark": (9, 8, 12, 255),
        "pattern": "chaos_fragment",
    },
    {
        "id": "temporal_crystal",
        "base": "amethyst_shard",
        "shadow": (27, 52, 61, 255),
        "mid": (62, 91, 99, 255),
        "light": (139, 142, 121, 255),
        "accent": (244, 190, 43, 255),
        "accent2": (48, 219, 224, 255),
        "glow": (137, 255, 255, 255),
        "dark": (13, 22, 29, 255),
        "pattern": "temporal_crystal",
    },
    {
        "id": "celestial_fragment",
        "base": "nether_star",
        "shadow": (43, 48, 57, 255),
        "mid": (81, 94, 97, 255),
        "light": (160, 169, 148, 255),
        "accent": (42, 225, 218, 255),
        "accent2": (247, 194, 61, 255),
        "glow": (139, 255, 247, 255),
        "dark": (16, 19, 25, 255),
        "pattern": "celestial_fragment",
    },
    {
        "id": "infinity_core",
        "base": "echo_shard",
        "shadow": (38, 35, 90, 255),
        "mid": (58, 72, 190, 255),
        "light": (83, 226, 241, 255),
        "accent": (189, 62, 241, 255),
        "accent2": (59, 241, 184, 255),
        "glow": (177, 242, 255, 255),
        "dark": (12, 10, 37, 255),
        "pattern": "infinity_core",
    },
]


SPECIAL_ITEMS = [
    {
        "id": "revive_totem",
        "base": "totem_of_undying",
        "shadow": (70, 12, 18, 255),
        "mid": (150, 24, 35, 255),
        "light": (242, 79, 70, 255),
        "accent": (255, 183, 54, 255),
        "accent2": (82, 7, 20, 255),
        "glow": (255, 58, 76, 255),
        "dark": (24, 5, 10, 255),
        "pattern": "revive_totem",
    },
    {
        "id": "waystone_token",
        "base": "recovery_compass_00",
        "shadow": (34, 30, 73, 255),
        "mid": (79, 74, 142, 255),
        "light": (122, 225, 238, 255),
        "accent": (193, 83, 241, 255),
        "accent2": (244, 190, 52, 255),
        "glow": (159, 255, 247, 255),
        "dark": (14, 12, 38, 255),
        "pattern": "waystone_token",
    },
    {
        "id": "phoenix_totem",
        "base": "totem_of_undying",
        "shadow": (92, 28, 10, 255),
        "mid": (202, 65, 18, 255),
        "light": (255, 154, 34, 255),
        "accent": (255, 221, 84, 255),
        "accent2": (169, 18, 20, 255),
        "glow": (255, 244, 142, 255),
        "dark": (45, 11, 8, 255),
        "pattern": "phoenix_totem",
    },
    {
        "id": "colossus_totem",
        "base": "totem_of_undying",
        "shadow": (45, 45, 50, 255),
        "mid": (106, 106, 112, 255),
        "light": (188, 190, 194, 255),
        "accent": (219, 158, 71, 255),
        "accent2": (133, 21, 24, 255),
        "glow": (255, 207, 108, 255),
        "dark": (20, 18, 21, 255),
        "pattern": "colossus_totem",
    },
    {
        "id": "time_totem",
        "base": "totem_of_undying",
        "shadow": (18, 41, 70, 255),
        "mid": (39, 91, 135, 255),
        "light": (91, 230, 240, 255),
        "accent": (245, 210, 73, 255),
        "accent2": (78, 70, 160, 255),
        "glow": (167, 255, 252, 255),
        "dark": (9, 20, 39, 255),
        "pattern": "time_totem",
    },
    {
        "id": "celestial_totem",
        "base": "totem_of_undying",
        "shadow": (76, 65, 117, 255),
        "mid": (151, 126, 210, 255),
        "light": (246, 246, 255, 255),
        "accent": (255, 221, 94, 255),
        "accent2": (91, 224, 241, 255),
        "glow": (255, 255, 184, 255),
        "dark": (28, 20, 60, 255),
        "pattern": "celestial_totem",
    },
    {
        "id": "totemcito",
        "base": "totem_of_undying",
        "shadow": (58, 8, 21, 255),
        "mid": (154, 22, 50, 255),
        "light": (252, 84, 96, 255),
        "accent": (108, 240, 194, 255),
        "accent2": (235, 72, 232, 255),
        "glow": (255, 225, 138, 255),
        "dark": (22, 6, 16, 255),
        "pattern": "totemcito",
    },
    {
        "id": "colossus_key",
        "base": "trial_key",
        "shadow": (45, 45, 50, 255),
        "mid": (103, 104, 109, 255),
        "light": (201, 203, 207, 255),
        "accent": (224, 159, 70, 255),
        "accent2": (118, 27, 23, 255),
        "glow": (255, 218, 116, 255),
        "dark": (18, 18, 22, 255),
        "pattern": "boss_key",
    },
    {
        "id": "eclipse_key",
        "base": "trial_key",
        "shadow": (57, 39, 50, 255),
        "mid": (119, 75, 83, 255),
        "light": (230, 176, 92, 255),
        "accent": (255, 218, 79, 255),
        "accent2": (186, 60, 199, 255),
        "glow": (255, 121, 236, 255),
        "dark": (19, 13, 20, 255),
        "pattern": "boss_key",
    },
    {
        "id": "void_key",
        "base": "ominous_trial_key",
        "shadow": (28, 21, 43, 255),
        "mid": (63, 43, 102, 255),
        "light": (121, 92, 179, 255),
        "accent": (183, 79, 243, 255),
        "accent2": (53, 228, 226, 255),
        "glow": (204, 145, 255, 255),
        "dark": (9, 7, 15, 255),
        "pattern": "boss_key",
    },
    {
        "id": "phoenix_key",
        "base": "ominous_trial_key",
        "shadow": (82, 24, 9, 255),
        "mid": (190, 55, 17, 255),
        "light": (255, 143, 36, 255),
        "accent": (255, 221, 82, 255),
        "accent2": (168, 17, 19, 255),
        "glow": (255, 245, 144, 255),
        "dark": (38, 10, 7, 255),
        "pattern": "boss_key",
    },
    {
        "id": "celestial_key",
        "base": "ominous_trial_key",
        "shadow": (74, 66, 118, 255),
        "mid": (155, 130, 215, 255),
        "light": (246, 246, 255, 255),
        "accent": (255, 223, 94, 255),
        "accent2": (83, 227, 242, 255),
        "glow": (255, 255, 184, 255),
        "dark": (27, 20, 59, 255),
        "pattern": "boss_key",
    },
    {
        "id": "infinity_key",
        "base": "ominous_trial_key",
        "shadow": (37, 32, 91, 255),
        "mid": (63, 74, 191, 255),
        "light": (89, 225, 242, 255),
        "accent": (190, 60, 240, 255),
        "accent2": (60, 241, 184, 255),
        "glow": (178, 243, 255, 255),
        "dark": (12, 10, 37, 255),
        "pattern": "boss_key",
    },
    {
        "id": "chaos_star",
        "base": "nether_star",
        "shadow": (56, 11, 19, 255),
        "mid": (129, 22, 31, 255),
        "light": (232, 74, 56, 255),
        "accent": (255, 190, 55, 255),
        "accent2": (67, 12, 21, 255),
        "glow": (255, 82, 62, 255),
        "dark": (17, 5, 9, 255),
        "pattern": "chaos_star",
    },
    {
        "id": "warden_heart",
        "base": "echo_shard",
        "shadow": (12, 45, 58, 255),
        "mid": (22, 99, 112, 255),
        "light": (76, 220, 219, 255),
        "accent": (25, 185, 171, 255),
        "accent2": (18, 31, 45, 255),
        "glow": (149, 255, 243, 255),
        "dark": (5, 18, 26, 255),
        "pattern": "warden_heart",
    },
    {
        "id": "dragon_heart",
        "base": "dragon_breath",
        "shadow": (42, 20, 59, 255),
        "mid": (91, 45, 112, 255),
        "light": (177, 112, 188, 255),
        "accent": (222, 90, 204, 255),
        "accent2": (123, 74, 42, 255),
        "glow": (245, 152, 232, 255),
        "dark": (13, 7, 21, 255),
        "pattern": "dragon_heart",
    },
    {
        "id": "colossus_altar",
        "base": "trial_key",
        "shadow": (45, 45, 50, 255),
        "mid": (103, 104, 109, 255),
        "light": (201, 203, 207, 255),
        "accent": (224, 159, 70, 255),
        "accent2": (118, 27, 23, 255),
        "glow": (255, 218, 116, 255),
        "dark": (18, 18, 22, 255),
        "pattern": "boss_altar",
    },
    {
        "id": "eclipse_altar",
        "base": "trial_key",
        "shadow": (57, 39, 50, 255),
        "mid": (119, 75, 83, 255),
        "light": (230, 176, 92, 255),
        "accent": (255, 218, 79, 255),
        "accent2": (186, 60, 199, 255),
        "glow": (255, 121, 236, 255),
        "dark": (19, 13, 20, 255),
        "pattern": "boss_altar",
    },
    {
        "id": "void_altar",
        "base": "trial_key",
        "shadow": (28, 21, 43, 255),
        "mid": (63, 43, 102, 255),
        "light": (121, 92, 179, 255),
        "accent": (183, 79, 243, 255),
        "accent2": (53, 228, 226, 255),
        "glow": (204, 145, 255, 255),
        "dark": (9, 7, 15, 255),
        "pattern": "boss_altar",
    },
    {
        "id": "phoenix_altar",
        "base": "trial_key",
        "shadow": (82, 24, 9, 255),
        "mid": (190, 55, 17, 255),
        "light": (255, 143, 36, 255),
        "accent": (255, 221, 82, 255),
        "accent2": (168, 17, 19, 255),
        "glow": (255, 245, 144, 255),
        "dark": (38, 10, 7, 255),
        "pattern": "boss_altar",
    },
    {
        "id": "celestial_altar",
        "base": "ominous_trial_key",
        "shadow": (74, 66, 118, 255),
        "mid": (155, 130, 215, 255),
        "light": (246, 246, 255, 255),
        "accent": (255, 223, 94, 255),
        "accent2": (83, 227, 242, 255),
        "glow": (255, 255, 184, 255),
        "dark": (27, 20, 59, 255),
        "pattern": "boss_altar",
    },
    {
        "id": "infinity_altar",
        "base": "ominous_trial_key",
        "shadow": (37, 32, 91, 255),
        "mid": (63, 74, 191, 255),
        "light": (89, 225, 242, 255),
        "accent": (190, 60, 240, 255),
        "accent2": (60, 241, 184, 255),
        "glow": (178, 243, 255, 255),
        "dark": (12, 10, 37, 255),
        "pattern": "boss_altar",
    },
]


def chunk(kind, data):
    return (
        struct.pack(">I", len(data))
        + kind
        + data
        + struct.pack(">I", zlib.crc32(kind + data) & 0xFFFFFFFF)
    )


def save_png(path, width, height, pixels):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    raw = bytearray()
    for y in range(height):
        raw.append(0)
        for x in range(width):
            raw.extend(pixels[y][x])

    data = (
        b"\x89PNG\r\n\x1a\n"
        + chunk(b"IHDR", struct.pack(">IIBBBBB", width, height, 8, 6, 0, 0, 0))
        + chunk(b"IDAT", zlib.compress(bytes(raw), 9))
        + chunk(b"IEND", b"")
    )
    with open(path, "wb") as f:
        f.write(data)


def paeth(a, b, c):
    p = a + b - c
    pa = abs(p - a)
    pb = abs(p - b)
    pc = abs(p - c)
    if pa <= pb and pa <= pc:
        return a
    if pb <= pc:
        return b
    return c


def load_png(path):
    with open(path, "rb") as f:
        data = f.read()
    if data[:8] != b"\x89PNG\r\n\x1a\n":
        raise ValueError(f"No es PNG: {path}")

    pos = 8
    width = height = color_type = bit_depth = None
    palette = []
    transparency = []
    idat = bytearray()

    while pos < len(data):
        length = struct.unpack(">I", data[pos:pos + 4])[0]
        kind = data[pos + 4:pos + 8]
        payload = data[pos + 8:pos + 8 + length]
        pos += 12 + length

        if kind == b"IHDR":
            width, height, bit_depth, color_type, compression, filtering, interlace = struct.unpack(">IIBBBBB", payload)
            if compression != 0 or filtering != 0 or interlace != 0:
                raise ValueError(f"PNG no soportado: {path}")
        elif kind == b"PLTE":
            palette = [tuple(payload[i:i + 3]) for i in range(0, len(payload), 3)]
        elif kind == b"tRNS":
            transparency = list(payload)
        elif kind == b"IDAT":
            idat.extend(payload)
        elif kind == b"IEND":
            break

    if color_type == 0:
        if bit_depth != 8:
            raise ValueError(f"PNG gris no soportado {bit_depth} bits: {path}")
        bpp = 1
        stride = width
    elif color_type == 3:
        if bit_depth not in (4, 8):
            raise ValueError(f"PNG indexado no soportado {bit_depth} bits: {path}")
        bpp = 1
        stride = (width * bit_depth + 7) // 8
    elif color_type == 2:
        if bit_depth != 8:
            raise ValueError(f"PNG RGB no soportado {bit_depth} bits: {path}")
        bpp = 3
        stride = width * bpp
    elif color_type == 6:
        if bit_depth != 8:
            raise ValueError(f"PNG RGBA no soportado {bit_depth} bits: {path}")
        bpp = 4
        stride = width * bpp
    elif color_type == 4:
        if bit_depth != 8:
            raise ValueError(f"PNG gris alpha no soportado {bit_depth} bits: {path}")
        bpp = 2
        stride = width * bpp
    else:
        raise ValueError(f"Color type no soportado {color_type}: {path}")

    raw = zlib.decompress(bytes(idat))
    rows = []
    offset = 0
    prev = [0] * stride
    for _ in range(height):
        filter_type = raw[offset]
        offset += 1
        row = list(raw[offset:offset + stride])
        offset += stride
        out = [0] * stride
        for i, value in enumerate(row):
            left = out[i - bpp] if i >= bpp else 0
            up = prev[i]
            up_left = prev[i - bpp] if i >= bpp else 0
            if filter_type == 0:
                out[i] = value
            elif filter_type == 1:
                out[i] = (value + left) & 255
            elif filter_type == 2:
                out[i] = (value + up) & 255
            elif filter_type == 3:
                out[i] = (value + ((left + up) // 2)) & 255
            elif filter_type == 4:
                out[i] = (value + paeth(left, up, up_left)) & 255
            else:
                raise ValueError(f"Filtro PNG no soportado {filter_type}: {path}")
        rows.append(out)
        prev = out

    pixels = empty(width, height)
    for y, row in enumerate(rows):
        for x in range(width):
            if color_type == 0:
                v = row[x]
                r, g, b = v, v, v
                transparent = struct.unpack(">H", bytes(transparency[:2]))[0] if len(transparency) >= 2 else None
                a = 0 if transparent is not None and v == transparent else 255
            elif color_type == 3:
                if bit_depth == 8:
                    index = row[x]
                else:
                    packed = row[x // 2]
                    index = (packed >> 4) if x % 2 == 0 else (packed & 15)
                r, g, b = palette[index]
                a = transparency[index] if index < len(transparency) else 255
            elif color_type == 2:
                i = x * bpp
                r, g, b = row[i], row[i + 1], row[i + 2]
                if len(transparency) >= 6:
                    tr, tg, tb = struct.unpack(">HHH", bytes(transparency[:6]))
                    a = 0 if (r, g, b) == (tr, tg, tb) else 255
                else:
                    a = 255
            elif color_type == 4:
                i = x * bpp
                v = row[i]
                r, g, b = v, v, v
                a = row[i + 1]
            else:
                i = x * bpp
                r, g, b, a = row[i], row[i + 1], row[i + 2], row[i + 3]
            pixels[y][x] = (r, g, b, a)
    return width, height, pixels


def empty(width, height):
    return [[(0, 0, 0, 0) for _ in range(width)] for _ in range(height)]


def clamp(value, low=0.0, high=1.0):
    return max(low, min(high, value))


def mix(a, b, t):
    t = clamp(t)
    return tuple(int(a[i] + (b[i] - a[i]) * t) for i in range(4))


def recolor_pixel(pixel, visual):
    r, g, b, a = pixel
    if a == 0:
        return pixel
    lum = (r * 0.30 + g * 0.59 + b * 0.11) / 255.0
    if lum < 0.34:
        out = mix(visual["dark"], visual["shadow"], lum / 0.34)
    elif lum < 0.68:
        out = mix(visual["shadow"], visual["mid"], (lum - 0.34) / 0.34)
    else:
        out = mix(visual["mid"], visual["light"], (lum - 0.68) / 0.32)
    return (out[0], out[1], out[2], a)


def recolor_image(pixels, visual):
    return [[recolor_pixel(pixel, visual) for pixel in row] for row in pixels]


def upscale_nearest(pixels, factor):
    out = []
    for row in pixels:
        scaled_row = []
        for pixel in row:
            scaled_row.extend([pixel] * factor)
        for _ in range(factor):
            out.append(list(scaled_row))
    return out


def set_pixel(img, x, y, color):
    if 0 <= y < len(img) and 0 <= x < len(img[0]):
        img[y][x] = color


def overlay_if_solid(img, x, y, color):
    if 0 <= y < len(img) and 0 <= x < len(img[0]) and img[y][x][3] > 0:
        img[y][x] = color


def rect_solid(img, x0, y0, x1, y1, color):
    for y in range(y0, y1):
        for x in range(x0, x1):
            overlay_if_solid(img, x, y, color)


def rect_any(img, x0, y0, x1, y1, color):
    for y in range(y0, y1):
        for x in range(x0, x1):
            set_pixel(img, x, y, color)


def line_solid(img, x0, y0, x1, y1, color):
    line(img, x0, y0, x1, y1, color, solid_only=True)


def line_any(img, x0, y0, x1, y1, color):
    line(img, x0, y0, x1, y1, color, solid_only=False)


def line(img, x0, y0, x1, y1, color, solid_only):
    dx = abs(x1 - x0)
    dy = -abs(y1 - y0)
    sx = 1 if x0 < x1 else -1
    sy = 1 if y0 < y1 else -1
    err = dx + dy
    while True:
        if solid_only:
            overlay_if_solid(img, x0, y0, color)
        else:
            set_pixel(img, x0, y0, color)
        if x0 == x1 and y0 == y1:
            break
        e2 = 2 * err
        if e2 >= dy:
            err += dy
            x0 += sx
        if e2 <= dx:
            err += dx
            y0 += sy


def vanilla_texture(*parts):
    return os.path.join(VANILLA_TEXTURES, *parts)


def reinforce_armor_icon_outline(img, visual):
    source = [list(row) for row in img]
    outline = mix(visual["dark"], (0, 0, 0, 255), 0.35)
    height = len(source)
    width = len(source[0])

    def source_solid(x, y):
        return 0 <= y < height and 0 <= x < width and source[y][x][3] > 0

    for y, row in enumerate(source):
        for x, pixel in enumerate(row):
            if pixel[3] == 0:
                continue
            if any(not source_solid(x + dx, y + dy) for dx, dy in ((0, -1), (-1, 0), (1, 0), (0, 1))):
                img[y][x] = (outline[0], outline[1], outline[2], pixel[3])


def load_base_item(visual, piece):
    _, _, img = load_png(vanilla_texture("item", f"{visual['source']}_{piece}.png"))
    return recolor_image(img, visual)


def load_base_layer(visual, leggings):
    folder = "humanoid_leggings" if leggings else "humanoid"
    _, _, img = load_png(vanilla_texture("entity", "equipment", folder, f"{visual['source']}.png"))
    return recolor_image(img, visual)


def add_common_icon_marks(img, visual, piece):
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]

    if piece == "helmet":
        rect_solid(img, 4, 3, 12, 5, a)
        line_solid(img, 4, 3, 11, 3, g)
        rect_solid(img, 5, 6, 11, 8, d)
        set_pixel(img, 6, 6, g)
        set_pixel(img, 9, 6, g)
    elif piece == "chestplate":
        if visual["pattern"] != "chaos":
            line_solid(img, 3, 4, 7, 9, a)
            line_solid(img, 12, 4, 8, 9, a)
            line_solid(img, 4, 5, 7, 10, g)
            line_solid(img, 11, 5, 8, 10, g)
        rect_solid(img, 7, 6, 9, 10, b)
        set_pixel(img, 8, 7, g)
        rect_solid(img, 4, 12, 12, 14, d)
    elif piece == "leggings":
        rect_solid(img, 4, 3, 12, 5, a)
        line_solid(img, 5, 6, 6, 13, b)
        line_solid(img, 10, 6, 11, 13, b)
        set_pixel(img, 5, 11, g)
        set_pixel(img, 11, 11, g)
    elif piece == "boots":
        rect_solid(img, 3, 11, 8, 14, d)
        rect_solid(img, 8, 11, 13, 14, d)
        line_solid(img, 4, 7, 6, 11, a)
        line_solid(img, 10, 7, 12, 11, a)
        set_pixel(img, 5, 12, g)
        set_pixel(img, 11, 12, g)


def add_armor_icon_structure(img, visual, piece):
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]
    light = visual["light"]

    if piece == "helmet":
        line_solid(img, 4, 2, 11, 2, light)
        line_solid(img, 5, 4, 10, 4, a)
        line_solid(img, 5, 8, 10, 8, b)
        overlay_if_solid(img, 6, 7, g)
        overlay_if_solid(img, 9, 7, g)
    elif piece == "chestplate":
        if visual["pattern"] != "chaos":
            line_solid(img, 3, 4, 7, 4, light)
            line_solid(img, 12, 4, 8, 4, light)
        line_solid(img, 4, 10, 11, 10, a)
        line_solid(img, 4, 13, 12, 13, b)
        overlay_if_solid(img, 8, 8, g)
    elif piece == "leggings":
        line_solid(img, 4, 3, 12, 3, light)
        line_solid(img, 4, 6, 6, 12, a)
        line_solid(img, 11, 6, 13, 12, a)
        overlay_if_solid(img, 5, 10, g)
        overlay_if_solid(img, 11, 10, g)
        line_solid(img, 4, 14, 7, 14, d)
        line_solid(img, 10, 14, 13, 14, d)
    elif piece == "boots":
        line_solid(img, 3, 7, 6, 7, light)
        line_solid(img, 10, 7, 13, 7, light)
        line_solid(img, 3, 11, 7, 11, a)
        line_solid(img, 9, 11, 13, 11, a)
        overlay_if_solid(img, 5, 12, g)
        overlay_if_solid(img, 11, 12, g)
        line_solid(img, 3, 14, 7, 14, d)
        line_solid(img, 9, 14, 13, 14, d)


def add_special_icon_marks(img, visual, piece):
    p = visual["pattern"]
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]

    if p == "leviathan":
        if piece == "helmet":
            line_any(img, 3, 3, 1, 1, a)
            line_any(img, 12, 3, 14, 1, a)
        if piece == "chestplate":
            rect_solid(img, 7, 7, 10, 10, a)
            set_pixel(img, 8, 8, g)
    elif p == "phoenix":
        line_solid(img, 5, 12, 7, 8, g)
        line_solid(img, 10, 12, 8, 8, visual["glow"])
        if piece == "helmet":
            line_any(img, 7, 2, 8, 0, g)
    elif p == "void":
        rect_solid(img, 5, 5, 11, 8, d)
        line_solid(img, 4, 4, 12, 4, g)
        if piece == "chestplate":
            rect_solid(img, 7, 7, 9, 9, g)
    elif p == "dragon":
        if piece == "helmet":
            line_any(img, 4, 3, 2, 1, b)
            line_any(img, 11, 3, 13, 1, b)
        line_solid(img, 4, 12, 12, 12, b)
    elif p == "chaos":
        rect_solid(img, 5, 6, 11, 8, d)
        rect_solid(img, 4, 5, 7, 7, a)
        rect_solid(img, 9, 5, 12, 7, a)
        set_pixel(img, 6, 6, g)
        set_pixel(img, 10, 6, g)
    elif p == "celestial":
        rect_solid(img, 7, 6, 10, 10, a)
        set_pixel(img, 8, 7, g)
        line_solid(img, 3, 12, 12, 12, b)
    elif p == "eclipse":
        rect_solid(img, 7, 6, 10, 10, a)
        rect_solid(img, 7, 8, 10, 10, b)
        set_pixel(img, 8, 7, g)
    elif p == "time":
        rect_solid(img, 7, 6, 10, 10, b)
        set_pixel(img, 8, 6, a)
        set_pixel(img, 8, 9, a)
        set_pixel(img, 7, 8, a)
        set_pixel(img, 10, 8, a)
    elif p == "infinity":
        rect_solid(img, 6, 6, 10, 9, b)
        line_solid(img, 4, 6, 6, 8, a)
        line_solid(img, 12, 6, 10, 8, a)
        set_pixel(img, 8, 8, g)
    elif p == "colossus":
        rect_solid(img, 4, 4, 12, 5, b)
        for x, y in ((5, 6), (10, 6), (5, 12), (10, 12)):
            set_pixel(img, x, y, a)
            set_pixel(img, x + 1, y, g)
    elif p == "titanium":
        line_solid(img, 4, 11, 11, 4, a)
        set_pixel(img, 8, 7, g)


def add_armor_icon_details_32(img, visual, piece):
    p = visual["pattern"]
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]
    light = visual["light"]

    # Shared 32x32 finishing pass: clean bands and mirrored panel seams.
    if piece == "helmet":
        line_solid(img, 8, 4, 23, 4, light)
        line_solid(img, 8, 8, 23, 8, a)
        line_solid(img, 10, 15, 21, 15, b)
        overlay_if_solid(img, 12, 13, g)
        overlay_if_solid(img, 19, 13, g)
    elif piece == "chestplate":
        line_solid(img, 6, 9, 13, 15, light)
        line_solid(img, 25, 9, 18, 15, light)
        line_solid(img, 8, 17, 13, 21, a)
        line_solid(img, 23, 17, 18, 21, a)
        line_solid(img, 9, 24, 22, 24, b)
        line_solid(img, 10, 28, 21, 28, d)
        rect_solid(img, 14, 15, 18, 21, d)
        overlay_if_solid(img, 16, 17, g)
    elif piece == "leggings":
        line_solid(img, 8, 5, 23, 5, light)
        line_solid(img, 8, 11, 11, 24, a)
        line_solid(img, 23, 11, 20, 24, a)
        line_solid(img, 8, 18, 11, 18, g)
        line_solid(img, 23, 18, 20, 18, g)
        line_solid(img, 8, 28, 13, 28, d)
        line_solid(img, 18, 28, 23, 28, d)
    elif piece == "boots":
        line_solid(img, 6, 14, 13, 14, light)
        line_solid(img, 25, 14, 18, 14, light)
        line_solid(img, 6, 22, 14, 22, a)
        line_solid(img, 25, 22, 17, 22, a)
        line_solid(img, 6, 28, 14, 28, d)
        line_solid(img, 17, 28, 25, 28, d)
        overlay_if_solid(img, 10, 23, g)
        overlay_if_solid(img, 21, 23, g)

    # Set motifs are built from centered panels, short chevrons and gems.
    if p == "chaos":
        if piece == "helmet":
            rect_solid(img, 10, 10, 14, 14, a)
            rect_solid(img, 18, 10, 22, 14, a)
            rect_solid(img, 12, 13, 20, 17, d)
            overlay_if_solid(img, 16, 14, g)
        elif piece == "chestplate":
            rect_solid(img, 8, 12, 13, 17, a)
            rect_solid(img, 19, 12, 24, 17, a)
            rect_solid(img, 11, 20, 14, 24, a)
            rect_solid(img, 18, 20, 21, 24, a)
            rect_solid(img, 14, 17, 18, 22, d)
            overlay_if_solid(img, 16, 18, g)
        elif piece == "leggings":
            line_solid(img, 9, 9, 12, 15, a)
            line_solid(img, 22, 9, 19, 15, a)
            line_solid(img, 9, 22, 12, 27, a)
            line_solid(img, 22, 22, 19, 27, a)
        elif piece == "boots":
            line_solid(img, 7, 20, 13, 24, a)
            line_solid(img, 24, 20, 18, 24, a)
            overlay_if_solid(img, 10, 24, g)
            overlay_if_solid(img, 21, 24, g)
    elif p == "infinity":
        if piece == "helmet":
            rect_solid(img, 10, 10, 22, 14, b)
            line_solid(img, 8, 9, 12, 13, a)
            line_solid(img, 24, 9, 20, 13, a)
            overlay_if_solid(img, 16, 11, g)
        elif piece == "chestplate":
            line_solid(img, 8, 14, 13, 19, a)
            line_solid(img, 23, 14, 18, 19, a)
            rect_solid(img, 13, 16, 19, 22, b)
            rect_solid(img, 15, 17, 17, 21, g)
            line_solid(img, 9, 25, 13, 25, a)
            line_solid(img, 23, 25, 19, 25, a)
        elif piece == "leggings":
            line_solid(img, 9, 9, 12, 19, b)
            line_solid(img, 22, 9, 19, 19, b)
            line_solid(img, 9, 23, 12, 28, a)
            line_solid(img, 22, 23, 19, 28, a)
        elif piece == "boots":
            line_solid(img, 7, 16, 13, 16, b)
            line_solid(img, 24, 16, 18, 16, b)
            overlay_if_solid(img, 10, 22, g)
            overlay_if_solid(img, 21, 22, g)
    elif p == "dragon" and piece == "chestplate":
        line_solid(img, 8, 13, 13, 18, b)
        line_solid(img, 23, 13, 18, 18, b)
        rect_solid(img, 14, 17, 18, 23, a)
        overlay_if_solid(img, 16, 18, g)
        line_solid(img, 10, 25, 22, 25, b)
    elif p == "phoenix" and piece == "chestplate":
        line_solid(img, 8, 13, 13, 19, a)
        line_solid(img, 23, 13, 18, 19, a)
        line_solid(img, 10, 22, 14, 26, g)
        line_solid(img, 21, 22, 17, 26, g)
        overlay_if_solid(img, 16, 18, light)
    elif p == "leviathan" and piece == "chestplate":
        line_solid(img, 8, 16, 13, 20, a)
        line_solid(img, 23, 16, 18, 20, a)
        rect_solid(img, 14, 17, 18, 22, b)
        overlay_if_solid(img, 16, 18, g)
    elif p == "void" and piece == "chestplate":
        line_solid(img, 8, 13, 13, 18, light)
        line_solid(img, 23, 13, 18, 18, light)
        rect_solid(img, 14, 16, 18, 22, b)
        overlay_if_solid(img, 16, 18, g)
    elif p in ("celestial", "eclipse", "time") and piece == "chestplate":
        rect_solid(img, 14, 16, 18, 22, a)
        overlay_if_solid(img, 16, 18, g)
        line_solid(img, 9, 24, 13, 24, b)
        line_solid(img, 23, 24, 19, 24, b)


def armor_plate_64(img, x0, y0, x1, y1, fill, edge):
    rect_any(img, x0, y0, x1, y1, edge)
    if x1 - x0 > 4 and y1 - y0 > 4:
        rect_any(img, x0 + 2, y0 + 2, x1 - 2, y1 - 2, fill)


def add_armor_icon_silhouette_64(img, visual, piece):
    p = visual["pattern"]
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]
    light = visual["light"]
    shadow = visual["shadow"]

    if piece == "helmet":
        if p == "angel":
            thick_line(img, 22, 14, 42, 14, d)
            line_any(img, 26, 12, 38, 12, a)
            line_any(img, 22, 18, 22, 24, a)
            line_any(img, 42, 18, 42, 24, a)
        elif p == "titanium":
            armor_plate_64(img, 8, 24, 17, 42, shadow, d)
            armor_plate_64(img, 47, 24, 56, 42, shadow, d)
        elif p == "leviathan":
            thick_line(img, 17, 30, 7, 20, d)
            line_any(img, 16, 29, 8, 21, a)
            thick_line(img, 47, 30, 57, 20, d)
            line_any(img, 48, 29, 56, 21, a)
        elif p == "phoenix":
            thick_line(img, 32, 24, 32, 6, d)
            line_any(img, 32, 21, 32, 8, a)
            line_any(img, 28, 14, 32, 6, g)
        elif p == "void":
            armor_plate_64(img, 20, 8, 44, 18, d, shadow)
            line_any(img, 24, 8, 40, 8, g)
        elif p == "dragon":
            thick_line(img, 21, 28, 10, 12, d)
            line_any(img, 20, 26, 11, 13, b)
            thick_line(img, 43, 28, 54, 12, d)
            line_any(img, 44, 26, 53, 13, b)
        elif p == "chaos":
            thick_line(img, 21, 28, 12, 14, d)
            line_any(img, 20, 26, 13, 15, a)
            thick_line(img, 43, 28, 52, 14, d)
            line_any(img, 44, 26, 51, 15, a)
        elif p == "celestial":
            thick_line(img, 20, 14, 44, 14, d)
            line_any(img, 24, 12, 40, 12, a)
            line_any(img, 28, 8, 36, 8, light)
        elif p == "eclipse":
            line_any(img, 24, 14, 32, 6, a)
            line_any(img, 40, 14, 32, 6, b)
            line_any(img, 28, 10, 32, 6, g)
        elif p == "time":
            line_any(img, 20, 22, 44, 22, d)
            line_any(img, 20, 22, 20, 38, b)
            line_any(img, 44, 22, 44, 38, b)
        elif p == "infinity":
            thick_line(img, 22, 24, 16, 8, d)
            line_any(img, 22, 22, 17, 9, a)
            thick_line(img, 42, 24, 48, 8, d)
            line_any(img, 42, 22, 47, 9, a)
        elif p == "colossus":
            armor_plate_64(img, 7, 24, 18, 44, shadow, d)
            armor_plate_64(img, 46, 24, 57, 44, shadow, d)

    elif piece == "chestplate":
        if p == "angel":
            thick_line(img, 18, 25, 4, 18, d)
            line_any(img, 18, 24, 5, 19, light)
            thick_line(img, 46, 25, 60, 18, d)
            line_any(img, 46, 24, 59, 19, light)
        elif p == "titanium":
            armor_plate_64(img, 5, 20, 18, 39, shadow, d)
            armor_plate_64(img, 46, 20, 59, 39, shadow, d)
            line_any(img, 10, 24, 14, 34, light)
            line_any(img, 54, 24, 50, 34, light)
        elif p == "leviathan":
            thick_line(img, 18, 28, 5, 22, d)
            line_any(img, 17, 27, 6, 23, a)
            thick_line(img, 46, 28, 59, 22, d)
            line_any(img, 47, 27, 58, 23, a)
        elif p == "phoenix":
            thick_line(img, 18, 26, 6, 16, d)
            line_any(img, 17, 25, 7, 17, a)
            thick_line(img, 46, 26, 58, 16, d)
            line_any(img, 47, 25, 57, 17, a)
        elif p == "void":
            armor_plate_64(img, 7, 24, 17, 46, shadow, d)
            armor_plate_64(img, 47, 24, 57, 46, shadow, d)
            line_any(img, 10, 28, 10, 40, g)
            line_any(img, 54, 28, 54, 40, g)
        elif p == "dragon":
            armor_plate_64(img, 6, 21, 19, 38, shadow, d)
            armor_plate_64(img, 45, 21, 58, 38, shadow, d)
            line_any(img, 10, 24, 15, 34, b)
            line_any(img, 54, 24, 49, 34, b)
        elif p == "chaos":
            armor_plate_64(img, 5, 22, 20, 38, d, a)
            armor_plate_64(img, 44, 22, 59, 38, d, a)
            line_any(img, 9, 26, 16, 26, g)
            line_any(img, 48, 26, 55, 26, g)
        elif p == "celestial":
            thick_line(img, 18, 24, 5, 18, d)
            line_any(img, 17, 23, 6, 19, light)
            thick_line(img, 46, 24, 59, 18, d)
            line_any(img, 47, 23, 58, 19, light)
        elif p == "eclipse":
            armor_plate_64(img, 7, 23, 19, 39, b, d)
            armor_plate_64(img, 45, 23, 57, 39, b, d)
            line_any(img, 11, 27, 15, 35, a)
            line_any(img, 53, 27, 49, 35, a)
        elif p == "time":
            armor_plate_64(img, 7, 23, 17, 39, shadow, d)
            armor_plate_64(img, 47, 23, 57, 39, shadow, d)
            line_any(img, 11, 27, 11, 35, b)
            line_any(img, 53, 27, 53, 35, b)
        elif p == "infinity":
            thick_line(img, 17, 28, 5, 18, d)
            line_any(img, 16, 27, 6, 19, a)
            thick_line(img, 47, 28, 59, 18, d)
            line_any(img, 48, 27, 58, 19, a)
        elif p == "colossus":
            armor_plate_64(img, 3, 18, 21, 42, shadow, d)
            armor_plate_64(img, 43, 18, 61, 42, shadow, d)
            line_any(img, 9, 22, 15, 34, light)
            line_any(img, 55, 22, 49, 34, light)

    elif piece == "leggings":
        if p in ("titanium", "colossus"):
            armor_plate_64(img, 10, 16, 20, 44, shadow, d)
            armor_plate_64(img, 44, 16, 54, 44, shadow, d)
        elif p in ("void", "infinity", "time"):
            line_any(img, 14, 16, 18, 46, a)
            line_any(img, 50, 16, 46, 46, a)
        elif p in ("phoenix", "eclipse"):
            line_any(img, 14, 18, 18, 46, a)
            line_any(img, 50, 18, 46, 46, a)
        elif p in ("dragon", "chaos"):
            line_any(img, 14, 16, 18, 44, b)
            line_any(img, 50, 16, 46, 44, b)

    elif piece == "boots":
        if p in ("angel", "celestial"):
            line_any(img, 14, 42, 4, 50, a)
            line_any(img, 50, 42, 60, 50, a)
        elif p in ("leviathan", "void", "infinity"):
            armor_plate_64(img, 8, 38, 20, 50, a, d)
            armor_plate_64(img, 44, 38, 56, 50, a, d)
        elif p in ("phoenix", "eclipse"):
            line_any(img, 14, 40, 6, 48, g)
            line_any(img, 50, 40, 58, 48, g)
        elif p in ("dragon", "chaos"):
            armor_plate_64(img, 8, 40, 22, 50, b, d)
            armor_plate_64(img, 42, 40, 56, 50, b, d)
        elif p in ("titanium", "colossus"):
            armor_plate_64(img, 6, 38, 23, 52, shadow, d)
            armor_plate_64(img, 41, 38, 58, 52, shadow, d)


def armor_gem_64(img, cx, cy, primary, center):
    rect_solid(img, cx - 8, cy - 8, cx + 8, cy + 8, primary)
    rect_solid(img, cx - 4, cy - 5, cx + 4, cy + 5, center)
    overlay_if_solid(img, cx, cy - 6, center)


def add_armor_icon_details_64(img, visual, piece):
    p = visual["pattern"]
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]
    light = visual["light"]
    shadow = visual["shadow"]

    # 64x64 finish: fine seams inside the vanilla silhouette.
    if piece == "helmet":
        line_solid(img, 16, 8, 47, 8, light)
        line_solid(img, 16, 12, 47, 12, a)
        rect_solid(img, 16, 28, 47, 36, d)
        line_solid(img, 20, 28, 29, 28, g)
        line_solid(img, 34, 28, 43, 28, g)
        line_solid(img, 20, 44, 43, 44, b)
    elif piece == "chestplate":
        line_solid(img, 12, 16, 27, 28, light)
        line_solid(img, 51, 16, 36, 28, light)
        line_solid(img, 16, 36, 27, 44, a)
        line_solid(img, 47, 36, 36, 44, a)
        rect_solid(img, 28, 28, 36, 48, d)
        line_solid(img, 16, 48, 48, 48, b)
        line_solid(img, 20, 56, 44, 56, shadow)
    elif piece == "leggings":
        line_solid(img, 16, 12, 47, 12, light)
        line_solid(img, 16, 24, 23, 48, a)
        line_solid(img, 47, 24, 40, 48, a)
        line_solid(img, 16, 36, 23, 36, g)
        line_solid(img, 47, 36, 40, 36, g)
        line_solid(img, 16, 56, 27, 56, d)
        line_solid(img, 36, 56, 47, 56, d)
    elif piece == "boots":
        line_solid(img, 12, 28, 27, 28, light)
        line_solid(img, 51, 28, 36, 28, light)
        line_solid(img, 12, 44, 28, 44, a)
        line_solid(img, 51, 44, 35, 44, a)
        line_solid(img, 12, 56, 28, 56, d)
        line_solid(img, 35, 56, 51, 56, d)

    # Cada conjunto recibe un emblema propio, con formas cortas y centradas.
    if p == "angel":
        if piece == "helmet":
            line_solid(img, 20, 20, 28, 28, a)
            line_solid(img, 44, 20, 36, 28, a)
            armor_gem_64(img, 32, 36, b, g)
        elif piece == "chestplate":
            line_solid(img, 16, 20, 28, 32, a)
            line_solid(img, 48, 20, 36, 32, a)
            armor_gem_64(img, 32, 35, b, g)
        elif piece == "leggings":
            line_solid(img, 20, 16, 24, 28, b)
            line_solid(img, 44, 16, 40, 28, b)
        elif piece == "boots":
            line_solid(img, 16, 36, 27, 36, a)
            line_solid(img, 48, 36, 37, 36, a)
    elif p == "titanium":
        if piece == "helmet":
            rect_solid(img, 20, 20, 28, 28, a)
            rect_solid(img, 36, 20, 44, 28, a)
            line_solid(img, 20, 40, 44, 40, b)
        elif piece == "chestplate":
            line_solid(img, 12, 20, 24, 32, light)
            line_solid(img, 52, 20, 40, 32, light)
            armor_gem_64(img, 32, 36, a, g)
            line_solid(img, 16, 50, 48, 50, b)
        elif piece == "leggings":
            line_solid(img, 20, 18, 24, 48, b)
            line_solid(img, 44, 18, 40, 48, b)
        elif piece == "boots":
            rect_solid(img, 16, 40, 27, 48, a)
            rect_solid(img, 37, 40, 48, 48, a)
    elif p == "leviathan":
        if piece == "helmet":
            line_solid(img, 12, 16, 24, 28, a)
            line_solid(img, 52, 16, 40, 28, a)
            armor_gem_64(img, 32, 35, b, g)
        elif piece == "chestplate":
            line_solid(img, 12, 24, 24, 36, a)
            line_solid(img, 52, 24, 40, 36, a)
            armor_gem_64(img, 32, 36, b, g)
            line_solid(img, 16, 52, 48, 52, a)
        elif piece == "leggings":
            line_solid(img, 20, 20, 24, 48, b)
            line_solid(img, 44, 20, 40, 48, b)
        elif piece == "boots":
            line_solid(img, 16, 36, 27, 44, a)
            line_solid(img, 48, 36, 37, 44, a)
    elif p == "phoenix":
        if piece == "helmet":
            line_solid(img, 20, 16, 28, 28, g)
            line_solid(img, 44, 16, 36, 28, g)
            line_solid(img, 28, 12, 32, 4, a)
        elif piece == "chestplate":
            line_solid(img, 12, 20, 24, 34, a)
            line_solid(img, 52, 20, 40, 34, a)
            armor_gem_64(img, 32, 36, a, g)
            line_solid(img, 16, 52, 48, 52, b)
        elif piece == "leggings":
            line_solid(img, 20, 20, 24, 44, g)
            line_solid(img, 44, 20, 40, 44, g)
        elif piece == "boots":
            line_solid(img, 16, 40, 27, 48, a)
            line_solid(img, 48, 40, 37, 48, a)
    elif p == "void":
        if piece == "helmet":
            rect_solid(img, 16, 28, 48, 36, d)
            line_solid(img, 20, 28, 29, 28, g)
            line_solid(img, 34, 28, 43, 28, g)
            armor_gem_64(img, 32, 40, a, g)
        elif piece == "chestplate":
            line_solid(img, 12, 16, 26, 30, light)
            line_solid(img, 52, 16, 38, 30, light)
            armor_gem_64(img, 32, 36, b, g)
            line_solid(img, 16, 52, 48, 52, a)
        elif piece == "leggings":
            line_solid(img, 20, 20, 24, 48, light)
            line_solid(img, 44, 20, 40, 48, light)
        elif piece == "boots":
            line_solid(img, 16, 36, 27, 44, a)
            line_solid(img, 48, 36, 37, 44, a)
    elif p == "dragon":
        if piece == "helmet":
            line_solid(img, 12, 20, 24, 28, b)
            line_solid(img, 52, 20, 40, 28, b)
            armor_gem_64(img, 32, 36, a, g)
        elif piece == "chestplate":
            line_solid(img, 12, 20, 24, 32, b)
            line_solid(img, 52, 20, 40, 32, b)
            armor_gem_64(img, 32, 36, a, g)
            line_solid(img, 16, 52, 48, 52, b)
        elif piece == "leggings":
            line_solid(img, 20, 20, 24, 48, b)
            line_solid(img, 44, 20, 40, 48, b)
        elif piece == "boots":
            line_solid(img, 16, 40, 27, 48, b)
            line_solid(img, 48, 40, 37, 48, b)
    elif p == "chaos":
        if piece == "helmet":
            rect_solid(img, 16, 24, 26, 34, a)
            rect_solid(img, 38, 24, 48, 34, a)
            rect_solid(img, 24, 32, 40, 40, d)
            overlay_if_solid(img, 32, 34, g)
        elif piece == "chestplate":
            rect_solid(img, 12, 20, 24, 36, a)
            rect_solid(img, 40, 20, 52, 36, a)
            rect_solid(img, 26, 28, 38, 46, d)
            armor_gem_64(img, 32, 36, a, g)
            line_solid(img, 16, 52, 48, 52, a)
        elif piece == "leggings":
            line_solid(img, 20, 16, 24, 44, a)
            line_solid(img, 44, 16, 40, 44, a)
            line_solid(img, 16, 52, 27, 52, a)
            line_solid(img, 48, 52, 37, 52, a)
        elif piece == "boots":
            rect_solid(img, 12, 40, 28, 48, a)
            rect_solid(img, 36, 40, 52, 48, a)


def clean_armor_gem_64(img, cx, cy, primary, center):
    rect_solid(img, cx - 8, cy - 10, cx + 8, cy + 10, primary)
    rect_solid(img, cx - 12, cy - 6, cx + 12, cy + 6, primary)
    rect_solid(img, cx - 4, cy - 6, cx + 4, cy + 6, center)
    overlay_if_solid(img, cx, cy - 7, center)


def add_clean_armor_icon_details_64(img, visual, piece):
    p = visual["pattern"]
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]
    light = visual["light"]
    shadow = visual["shadow"]

    # Base uniforme: pocas piezas grandes, bordes rectos y simetria clara.
    if piece == "helmet":
        line_solid(img, 16, 8, 47, 8, light)
        rect_solid(img, 16, 28, 48, 36, d)
        line_solid(img, 20, 44, 44, 44, b)
    elif piece == "chestplate":
        rect_solid(img, 28, 28, 36, 48, d)
        line_solid(img, 16, 48, 48, 48, b)
        line_solid(img, 20, 56, 44, 56, shadow)
    elif piece == "leggings":
        line_solid(img, 16, 8, 48, 8, light)
        line_solid(img, 16, 36, 24, 36, a)
        line_solid(img, 40, 36, 48, 36, a)
        line_solid(img, 16, 56, 28, 56, d)
        line_solid(img, 36, 56, 48, 56, d)
    elif piece == "boots":
        line_solid(img, 12, 28, 28, 28, light)
        line_solid(img, 36, 28, 52, 28, light)
        line_solid(img, 12, 56, 28, 56, d)
        line_solid(img, 36, 56, 52, 56, d)

    # Un solo emblema por pieza: el color identifica el set sin saturarlo.
    if p == "angel":
        trim, core = a, b
    elif p == "titanium":
        trim, core = a, light
    elif p == "leviathan":
        trim, core = a, b
    elif p == "phoenix":
        trim, core = a, g
    elif p == "void":
        trim, core = light, a
    elif p == "dragon":
        trim, core = b, a
    elif p == "chaos":
        trim, core = a, g
    elif p == "celestial":
        trim, core = b, a
    elif p == "eclipse":
        trim, core = a, b
    elif p == "time":
        trim, core = b, g
    elif p == "infinity":
        trim, core = a, g
    else:
        trim, core = a, g

    if piece == "helmet":
        clean_armor_gem_64(img, 32, 38, trim, core)
        line_solid(img, 20, 48, 44, 48, trim)
    elif piece == "chestplate":
        clean_armor_gem_64(img, 32, 36, trim, core)
        line_solid(img, 16, 52, 48, 52, trim)
    elif piece == "leggings":
        line_solid(img, 20, 16, 24, 48, trim)
        line_solid(img, 44, 16, 40, 48, trim)
    elif piece == "boots":
        rect_solid(img, 16, 40, 28, 48, trim)
        rect_solid(img, 36, 40, 48, 48, trim)

    # Motivos especiales, siempre en bloques simetricos.
    if p == "chaos":
        if piece == "helmet":
            rect_solid(img, 16, 24, 26, 34, a)
            rect_solid(img, 38, 24, 48, 34, a)
            rect_solid(img, 24, 32, 40, 40, d)
            overlay_if_solid(img, 32, 35, g)
        elif piece == "chestplate":
            rect_solid(img, 12, 20, 24, 36, a)
            rect_solid(img, 40, 20, 52, 36, a)
            rect_solid(img, 26, 28, 38, 46, d)
            rect_solid(img, 30, 32, 34, 42, g)
        elif piece == "leggings":
            rect_solid(img, 16, 20, 24, 32, a)
            rect_solid(img, 40, 20, 48, 32, a)
        elif piece == "boots":
            rect_solid(img, 12, 40, 28, 48, a)
            rect_solid(img, 36, 40, 52, 48, a)
    elif p == "infinity":
        if piece == "helmet":
            rect_solid(img, 16, 24, 48, 34, b)
            clean_armor_gem_64(img, 32, 38, b, g)
        elif piece == "chestplate":
            rect_solid(img, 12, 20, 24, 34, a)
            rect_solid(img, 40, 20, 52, 34, a)
            clean_armor_gem_64(img, 32, 36, b, g)
            line_solid(img, 16, 52, 48, 52, b)
        elif piece == "leggings":
            rect_solid(img, 16, 16, 24, 28, b)
            rect_solid(img, 40, 16, 48, 28, b)
        elif piece == "boots":
            rect_solid(img, 16, 40, 28, 52, a)
            rect_solid(img, 36, 40, 48, 52, a)
    elif p == "time" and piece == "chestplate":
        rect_solid(img, 28, 28, 36, 44, b)
        overlay_if_solid(img, 32, 32, g)
        line_solid(img, 32, 34, 32, 40, a)
        line_solid(img, 32, 36, 38, 36, a)
    elif p == "dragon" and piece == "chestplate":
        rect_solid(img, 28, 28, 36, 46, a)
        overlay_if_solid(img, 32, 34, g)
        line_solid(img, 16, 52, 48, 52, b)
    elif p == "phoenix" and piece == "chestplate":
        rect_solid(img, 28, 28, 36, 44, a)
        overlay_if_solid(img, 32, 33, g)
        line_solid(img, 16, 52, 48, 52, b)
    elif p == "leviathan" and piece == "chestplate":
        rect_solid(img, 28, 28, 36, 44, b)
        overlay_if_solid(img, 32, 33, g)
        line_solid(img, 16, 52, 48, 52, a)
    elif p == "celestial":
        if piece == "helmet":
            line_solid(img, 16, 16, 48, 16, b)
            armor_gem_64(img, 32, 36, a, g)
        elif piece == "chestplate":
            armor_gem_64(img, 32, 36, a, g)
            line_solid(img, 16, 52, 48, 52, b)
        elif piece == "leggings":
            line_solid(img, 20, 16, 24, 44, a)
            line_solid(img, 44, 16, 40, 44, a)
        elif piece == "boots":
            line_solid(img, 16, 40, 27, 48, b)
            line_solid(img, 48, 40, 37, 48, b)
    elif p == "eclipse":
        if piece == "helmet":
            rect_solid(img, 16, 28, 48, 36, a)
            line_solid(img, 20, 40, 44, 40, b)
        elif piece == "chestplate":
            armor_gem_64(img, 32, 36, a, g)
            line_solid(img, 16, 50, 48, 50, b)
        elif piece == "leggings":
            line_solid(img, 20, 20, 24, 44, a)
            line_solid(img, 44, 20, 40, 44, a)
        elif piece == "boots":
            line_solid(img, 16, 40, 27, 48, b)
            line_solid(img, 48, 40, 37, 48, b)
    elif p == "time":
        if piece == "helmet":
            armor_gem_64(img, 32, 36, b, g)
            line_solid(img, 32, 28, 32, 42, a)
        elif piece == "chestplate":
            armor_gem_64(img, 32, 36, b, g)
            line_solid(img, 32, 30, 32, 42, a)
            line_solid(img, 32, 36, 40, 36, a)
            line_solid(img, 16, 52, 48, 52, b)
        elif piece == "leggings":
            line_solid(img, 20, 20, 24, 44, b)
            line_solid(img, 44, 20, 40, 44, b)
        elif piece == "boots":
            line_solid(img, 16, 40, 27, 48, a)
            line_solid(img, 48, 40, 37, 48, a)
    elif p == "infinity":
        if piece == "helmet":
            rect_solid(img, 16, 24, 48, 34, b)
            line_solid(img, 20, 20, 28, 28, a)
            line_solid(img, 44, 20, 36, 28, a)
            armor_gem_64(img, 32, 38, a, g)
        elif piece == "chestplate":
            rect_solid(img, 12, 20, 24, 34, a)
            rect_solid(img, 40, 20, 52, 34, a)
            armor_gem_64(img, 32, 36, b, g)
            line_solid(img, 16, 52, 48, 52, b)
            line_solid(img, 20, 56, 44, 56, a)
        elif piece == "leggings":
            line_solid(img, 20, 16, 24, 44, b)
            line_solid(img, 44, 16, 40, 44, b)
            line_solid(img, 16, 52, 27, 52, a)
            line_solid(img, 48, 52, 37, 52, a)
        elif piece == "boots":
            line_solid(img, 16, 36, 27, 36, b)
            line_solid(img, 48, 36, 37, 36, b)
            rect_solid(img, 16, 44, 27, 52, a)
            rect_solid(img, 37, 44, 48, 52, a)
    elif p == "colossus":
        if piece == "helmet":
            line_solid(img, 16, 16, 48, 16, b)
            rect_solid(img, 16, 28, 24, 38, a)
            rect_solid(img, 40, 28, 48, 38, a)
        elif piece == "chestplate":
            line_solid(img, 12, 16, 24, 28, light)
            line_solid(img, 52, 16, 40, 28, light)
            rect_solid(img, 26, 28, 38, 44, b)
            armor_gem_64(img, 32, 36, a, g)
            line_solid(img, 16, 52, 48, 52, b)
        elif piece == "leggings":
            rect_solid(img, 16, 20, 24, 32, b)
            rect_solid(img, 40, 20, 48, 32, b)
        elif piece == "boots":
            rect_solid(img, 12, 40, 28, 48, a)
            rect_solid(img, 36, 40, 52, 48, a)


def add_rich_armor_icon_details_64(img, visual, piece):
    p = visual["pattern"]
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]
    light = visual["light"]
    shadow = visual["shadow"]

    style = {
        "angel": (light, a, b),
        "titanium": (shadow, a, light),
        "leviathan": (b, a, g),
        "phoenix": (a, b, g),
        "void": (d, a, g),
        "dragon": (shadow, b, g),
        "chaos": (d, a, g),
        "celestial": (b, a, g),
        "eclipse": (b, a, g),
        "time": (shadow, b, g),
        "infinity": (b, a, g),
        "colossus": (shadow, b, light),
    }
    panel, trim, core = style[p]

    # Superficies grandes para que la pieza tenga volumen y no parezca un recolor.
    if piece == "helmet":
        rect_solid(img, 18, 16, 46, 24, panel)
        rect_solid(img, 18, 28, 46, 36, trim)
        rect_solid(img, 24, 34, 40, 42, core)
        overlay_if_solid(img, 32, 36, g)
        line_solid(img, 20, 46, 44, 46, trim)
    elif piece == "chestplate":
        rect_solid(img, 12, 20, 24, 34, panel)
        rect_solid(img, 40, 20, 52, 34, panel)
        rect_solid(img, 24, 26, 40, 46, trim)
        rect_solid(img, 28, 30, 36, 42, core)
        overlay_if_solid(img, 32, 34, g)
        rect_solid(img, 16, 50, 48, 54, trim)
    elif piece == "leggings":
        rect_solid(img, 16, 14, 24, 28, panel)
        rect_solid(img, 40, 14, 48, 28, panel)
        rect_solid(img, 16, 34, 24, 44, trim)
        rect_solid(img, 40, 34, 48, 44, trim)
        overlay_if_solid(img, 20, 38, g)
        overlay_if_solid(img, 44, 38, g)
    elif piece == "boots":
        rect_solid(img, 10, 34, 26, 48, panel)
        rect_solid(img, 38, 34, 54, 48, panel)
        rect_solid(img, 14, 48, 24, 54, trim)
        rect_solid(img, 40, 48, 50, 54, trim)

    # Motivos propios, hechos con bloques y capas de color.
    if p == "dragon":
        if piece == "helmet":
            rect_solid(img, 20, 28, 28, 34, b)
            rect_solid(img, 36, 28, 44, 34, b)
            overlay_if_solid(img, 24, 30, g)
            overlay_if_solid(img, 40, 30, g)
        elif piece == "chestplate":
            rect_solid(img, 24, 28, 40, 44, b)
            rect_solid(img, 28, 32, 36, 42, a)
            overlay_if_solid(img, 32, 34, g)
        elif piece == "leggings":
            rect_solid(img, 16, 18, 24, 30, b)
            rect_solid(img, 40, 18, 48, 30, b)
        elif piece == "boots":
            rect_solid(img, 12, 40, 22, 46, b)
            rect_solid(img, 42, 40, 52, 46, b)
    elif p == "phoenix":
        if piece == "helmet":
            rect_solid(img, 28, 4, 36, 12, g)
            rect_solid(img, 24, 10, 40, 18, a)
            rect_solid(img, 28, 16, 36, 24, b)
        elif piece == "chestplate":
            rect_solid(img, 18, 24, 26, 34, a)
            rect_solid(img, 38, 24, 46, 34, a)
            rect_solid(img, 22, 32, 28, 42, g)
            rect_solid(img, 36, 32, 42, 42, g)
        elif piece == "leggings":
            rect_solid(img, 16, 18, 24, 26, a)
            rect_solid(img, 40, 18, 48, 26, a)
            rect_solid(img, 16, 34, 24, 42, g)
            rect_solid(img, 40, 34, 48, 42, g)
        elif piece == "boots":
            rect_solid(img, 10, 40, 22, 48, a)
            rect_solid(img, 42, 40, 54, 48, a)
    elif p == "chaos":
        if piece == "helmet":
            rect_solid(img, 16, 22, 26, 34, a)
            rect_solid(img, 38, 22, 48, 34, a)
            rect_solid(img, 24, 30, 40, 42, d)
            rect_solid(img, 30, 34, 34, 40, g)
        elif piece == "chestplate":
            rect_solid(img, 8, 22, 20, 38, d)
            rect_solid(img, 44, 22, 56, 38, d)
            rect_solid(img, 12, 26, 20, 32, a)
            rect_solid(img, 44, 26, 52, 32, a)
            rect_solid(img, 28, 30, 36, 44, g)
        elif piece == "leggings":
            rect_solid(img, 12, 16, 24, 28, d)
            rect_solid(img, 40, 16, 52, 28, d)
            rect_solid(img, 16, 36, 24, 44, a)
            rect_solid(img, 40, 36, 48, 44, a)
        elif piece == "boots":
            rect_solid(img, 8, 38, 24, 50, d)
            rect_solid(img, 40, 38, 56, 50, d)
    elif p == "infinity":
        if piece == "helmet":
            rect_solid(img, 16, 18, 48, 26, b)
            rect_solid(img, 22, 28, 30, 40, a)
            rect_solid(img, 34, 28, 42, 40, a)
            overlay_if_solid(img, 32, 34, g)
        elif piece == "chestplate":
            rect_solid(img, 8, 20, 20, 36, a)
            rect_solid(img, 44, 20, 56, 36, a)
            rect_solid(img, 24, 26, 40, 46, b)
            rect_solid(img, 28, 30, 36, 42, g)
            rect_solid(img, 16, 50, 48, 54, b)
        elif piece == "leggings":
            rect_solid(img, 12, 16, 24, 32, b)
            rect_solid(img, 40, 16, 52, 32, b)
            rect_solid(img, 16, 38, 24, 46, a)
            rect_solid(img, 40, 38, 48, 46, a)
        elif piece == "boots":
            rect_solid(img, 8, 36, 24, 50, b)
            rect_solid(img, 40, 36, 56, 50, b)
            rect_solid(img, 12, 50, 24, 56, a)
            rect_solid(img, 40, 50, 52, 56, a)
    elif p == "time" and piece == "chestplate":
        rect_solid(img, 24, 28, 40, 44, b)
        overlay_if_solid(img, 32, 32, g)
        rect_solid(img, 30, 34, 34, 40, a)
        rect_solid(img, 32, 36, 38, 40, a)
    elif p == "eclipse" and piece == "chestplate":
        rect_solid(img, 24, 28, 32, 44, b)
        rect_solid(img, 32, 28, 40, 44, a)
        overlay_if_solid(img, 32, 34, g)
    elif p == "celestial" and piece == "chestplate":
        clean_armor_gem_64(img, 32, 36, a, g)
        rect_solid(img, 16, 50, 48, 54, b)


def detail_scale_64(img, x, y, edge, fill, highlight):
    rect_solid(img, x, y, x + 8, y + 6, edge)
    rect_solid(img, x + 2, y + 1, x + 7, y + 5, fill)
    rect_solid(img, x + 2, y + 1, x + 5, y + 2, highlight)


def detail_feather_64(img, x, y, edge, fill, highlight):
    rect_solid(img, x + 2, y, x + 6, y + 10, edge)
    rect_solid(img, x, y + 3, x + 8, y + 7, fill)
    rect_solid(img, x + 2, y + 2, x + 5, y + 4, highlight)


def detail_rivet_64(img, x, y, edge, highlight):
    rect_solid(img, x, y, x + 5, y + 5, edge)
    overlay_if_solid(img, x + 1, y + 1, highlight)


def add_signature_surface_details_64(img, visual, piece):
    p = visual["pattern"]
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]
    light = visual["light"]
    shadow = visual["shadow"]

    if p == "dragon":
        if piece == "helmet":
            rect_solid(img, 20, 30, 28, 35, a)
            rect_solid(img, 36, 30, 44, 35, a)
            overlay_if_solid(img, 22, 31, g)
            overlay_if_solid(img, 42, 31, g)
            rect_solid(img, 28, 40, 36, 46, b)
        elif piece == "chestplate":
            for x, y in ((16, 24), (24, 22), (32, 22), (40, 24), (20, 32), (28, 30), (36, 30), (44, 32), (24, 40), (32, 38), (40, 40)):
                detail_scale_64(img, x, y, d, b, light)
        elif piece == "leggings":
            for x, y in ((16, 18), (24, 26), (16, 36), (40, 18), (32, 26), (40, 36)):
                detail_scale_64(img, x, y, d, b, light)
        elif piece == "boots":
            rect_solid(img, 10, 40, 18, 47, b)
            rect_solid(img, 46, 40, 54, 47, b)
            overlay_if_solid(img, 12, 41, light)
            overlay_if_solid(img, 52, 41, light)

    elif p == "phoenix":
        if piece == "helmet":
            detail_feather_64(img, 28, 4, b, a, g)
            detail_feather_64(img, 22, 14, b, a, g)
            detail_feather_64(img, 38, 14, b, a, g)
        elif piece == "chestplate":
            detail_feather_64(img, 16, 22, b, a, g)
            detail_feather_64(img, 40, 22, b, a, g)
            detail_feather_64(img, 22, 28, b, a, g)
            detail_feather_64(img, 34, 28, b, a, g)
            detail_feather_64(img, 28, 34, b, a, g)
        elif piece == "leggings":
            detail_feather_64(img, 16, 18, b, a, g)
            detail_feather_64(img, 40, 18, b, a, g)
            detail_feather_64(img, 16, 34, b, a, g)
            detail_feather_64(img, 40, 34, b, a, g)
        elif piece == "boots":
            detail_feather_64(img, 10, 38, b, a, g)
            detail_feather_64(img, 46, 38, b, a, g)

    elif p == "chaos":
        if piece == "helmet":
            detail_rivet_64(img, 18, 24, a, g)
            detail_rivet_64(img, 41, 24, a, g)
            rect_solid(img, 26, 32, 38, 42, d)
            rect_solid(img, 30, 34, 34, 40, g)
        elif piece == "chestplate":
            detail_rivet_64(img, 12, 26, a, g)
            detail_rivet_64(img, 47, 26, a, g)
            rect_solid(img, 28, 30, 36, 44, a)
            rect_solid(img, 30, 34, 34, 40, g)
        elif piece == "leggings":
            detail_rivet_64(img, 16, 20, a, g)
            detail_rivet_64(img, 43, 20, a, g)
        elif piece == "boots":
            detail_rivet_64(img, 12, 40, a, g)
            detail_rivet_64(img, 47, 40, a, g)

    elif p == "infinity":
        if piece == "helmet":
            detail_scale_64(img, 18, 18, d, b, g)
            detail_scale_64(img, 38, 18, d, b, g)
            rect_solid(img, 28, 32, 36, 42, a)
            rect_solid(img, 30, 34, 34, 40, g)
        elif piece == "chestplate":
            detail_scale_64(img, 10, 22, d, a, g)
            detail_scale_64(img, 46, 22, d, a, g)
            detail_scale_64(img, 18, 28, d, b, g)
            detail_scale_64(img, 38, 28, d, b, g)
            rect_solid(img, 28, 30, 36, 42, g)
        elif piece == "leggings":
            detail_scale_64(img, 16, 18, d, b, g)
            detail_scale_64(img, 40, 18, d, b, g)
            detail_scale_64(img, 16, 36, d, a, g)
            detail_scale_64(img, 40, 36, d, a, g)
        elif piece == "boots":
            detail_scale_64(img, 10, 38, d, b, g)
            detail_scale_64(img, 46, 38, d, b, g)

    elif p == "leviathan" and piece in ("chestplate", "leggings"):
        scale_points = ((14, 24), (22, 26), (30, 24), (38, 26), (46, 24)) if piece == "chestplate" else ((16, 18), (40, 18), (16, 36), (40, 36))
        for x, y in scale_points:
            detail_scale_64(img, x, y, d, b, g)

    elif p == "angel":
        if piece == "chestplate":
            detail_feather_64(img, 12, 22, shadow, light, g)
            detail_feather_64(img, 44, 22, shadow, light, g)
            detail_feather_64(img, 18, 30, shadow, light, g)
            detail_feather_64(img, 38, 30, shadow, light, g)
        elif piece == "helmet":
            detail_feather_64(img, 20, 18, shadow, light, g)
            detail_feather_64(img, 40, 18, shadow, light, g)

    elif p in ("titanium", "colossus"):
        detail_rivet_64(img, 16, 24, b, light)
        detail_rivet_64(img, 43, 24, b, light)
        if piece == "chestplate":
            detail_rivet_64(img, 24, 48, b, light)
            detail_rivet_64(img, 36, 48, b, light)

    elif p == "void":
        if piece == "helmet":
            detail_rivet_64(img, 20, 30, a, g)
            detail_rivet_64(img, 39, 30, a, g)
        elif piece == "chestplate":
            detail_rivet_64(img, 10, 28, a, g)
            detail_rivet_64(img, 49, 28, a, g)

    elif p == "celestial" and piece == "chestplate":
        detail_rivet_64(img, 14, 26, b, g)
        detail_rivet_64(img, 45, 26, b, g)


def draw_icon(visual, piece, path):
    img = load_base_item(visual, piece)
    img = upscale_nearest(img, 4)
    add_armor_icon_silhouette_64(img, visual, piece)
    reinforce_armor_icon_outline(img, visual)
    add_rich_armor_icon_details_64(img, visual, piece)
    save_png(path, ARMOR_ICON_TEXTURE_SIZE, ARMOR_ICON_TEXTURE_SIZE, img)


def load_base_material(visual):
    _, _, img = load_png(vanilla_texture("item", f"{visual['base']}.png"))
    return recolor_image(img, visual)


def diamond_dot(img, cx, cy, color, glow):
    set_pixel(img, cx, cy - 1, color)
    set_pixel(img, cx - 1, cy, color)
    set_pixel(img, cx, cy, glow)
    set_pixel(img, cx + 1, cy, color)
    set_pixel(img, cx, cy + 1, color)


def diamond_dot_solid(img, cx, cy, color, glow):
    overlay_if_solid(img, cx, cy - 1, color)
    overlay_if_solid(img, cx - 1, cy, color)
    overlay_if_solid(img, cx, cy, glow)
    overlay_if_solid(img, cx + 1, cy, color)
    overlay_if_solid(img, cx, cy + 1, color)


def ring(img, cx, cy, radius, color):
    for dx, dy in ((0, -radius), (1, -radius), (-1, -radius), (radius, 0), (-radius, 0),
                   (0, radius), (1, radius), (-1, radius)):
        set_pixel(img, cx + dx, cy + dy, color)
    for dx, dy in ((radius - 1, -radius + 1), (-radius + 1, -radius + 1),
                   (radius - 1, radius - 1), (-radius + 1, radius - 1)):
        set_pixel(img, cx + dx, cy + dy, color)


def material_palette(visual):
    return {
        "outline": mix(visual["dark"], (0, 0, 0, 255), 0.45),
        "deep": mix(visual["dark"], visual["shadow"], 0.25),
        "shade": visual["shadow"],
        "body": visual["mid"],
        "light": visual["light"],
        "accent": visual["accent"],
        "accent2": visual["accent2"],
        "glow": visual["glow"],
        "spark": mix(visual["light"], visual["glow"], 0.35),
    }


def draw_diamond_shape(img, cx, cy, radius, outline, fill, light=None):
    for dy in range(-radius, radius + 1):
        span = radius - abs(dy)
        rect_any(img, cx - span, cy + dy, cx + span + 1, cy + dy + 1, fill)
    outline_current_shape(img, outline)
    if light:
        line_solid(img, cx - radius + 1, cy, cx, cy - radius + 1, light)
        line_solid(img, cx - radius + 2, cy + 1, cx + 1, cy - radius + 1, light)


def draw_ingot_32(img, p):
    rows = (
        (8, 10, 22), (9, 8, 25), (10, 6, 27), (11, 5, 28), (12, 4, 29),
        (13, 4, 29), (14, 5, 28), (15, 6, 27), (16, 7, 26), (17, 8, 25),
        (18, 10, 23), (19, 12, 21),
    )
    draw_row_shape(img, rows, p["body"])
    outline_current_shape(img, p["outline"])
    draw_row_shape(img, ((10, 9, 24), (11, 7, 26), (12, 6, 25)), p["light"])
    draw_row_shape(img, ((16, 8, 25), (17, 10, 23), (18, 12, 21)), p["shade"])
    line_any(img, 8, 14, 25, 10, p["spark"])
    line_any(img, 11, 18, 23, 15, p["accent2"])


def draw_feather_32(img, p):
    thick_line(img, 8, 29, 18, 7, p["outline"])
    line_any(img, 10, 28, 19, 8, p["light"])
    for x0, y0, x1, y1, color in (
        (12, 24, 5, 22, p["accent"]),
        (13, 21, 6, 18, p["body"]),
        (14, 18, 8, 14, p["glow"]),
        (16, 16, 24, 10, p["accent"]),
        (15, 20, 24, 16, p["body"]),
        (13, 24, 21, 23, p["shade"]),
    ):
        line_any(img, x0, y0, x1, y1, p["outline"])
        line_any(img, x0 + (1 if x1 >= x0 else -1), y0, x1, y1, color)
    set_pixel(img, 20, 6, p["glow"])
    set_pixel(img, 21, 7, p["accent"])


def draw_scale_32(img, p):
    rows = (
        (5, 15, 20), (6, 13, 22), (7, 11, 24), (8, 10, 25), (9, 9, 26),
        (10, 8, 27), (11, 8, 27), (12, 9, 26), (13, 10, 25), (14, 11, 24),
        (15, 12, 23), (16, 13, 22), (17, 14, 21), (18, 15, 20),
        (19, 16, 19),
    )
    draw_row_shape(img, rows, p["body"])
    outline_current_shape(img, p["outline"])
    draw_row_shape(img, ((7, 13, 21), (8, 12, 23), (9, 11, 21)), p["light"])
    line_solid(img, 10, 12, 23, 7, p["accent"])
    line_solid(img, 11, 16, 25, 11, p["accent2"])
    line_solid(img, 15, 19, 24, 14, p["shade"])
    set_pixel(img, 18, 9, p["glow"])


def draw_crystal_32(img, p, long=False):
    rows = (
        (3, 15, 18), (4, 14, 19), (5, 13, 20), (6, 12, 21), (7, 11, 22),
        (8, 10, 23), (9, 10, 23), (10, 9, 24), (11, 9, 24), (12, 10, 23),
        (13, 10, 23), (14, 11, 22), (15, 12, 21), (16, 13, 20),
        (17, 14, 19), (18, 15, 18),
    )
    if long:
        rows = (
            (2, 16, 18), (3, 15, 19), (4, 14, 20), (5, 13, 21), (6, 12, 22),
            (7, 11, 23), (8, 10, 24), (9, 10, 24), (10, 11, 23), (11, 11, 23),
            (12, 12, 22), (13, 12, 22), (14, 13, 21), (15, 13, 21),
            (16, 14, 20), (17, 14, 20), (18, 15, 19), (19, 15, 19),
            (20, 16, 18), (21, 16, 18),
        )
    draw_row_shape(img, rows, p["body"])
    outline_current_shape(img, p["outline"])
    line_solid(img, 13, 6, 20, 3, p["light"])
    line_solid(img, 11, 10, 22, 5, p["spark"])
    line_solid(img, 16, 19, 23, 12, p["shade"])
    line_solid(img, 12, 15, 19, 22, p["accent2"])
    set_pixel(img, 18, 8, p["glow"])


def draw_orb_32(img, p):
    rows = (
        (6, 13, 19), (7, 10, 22), (8, 8, 24), (9, 7, 25), (10, 6, 26),
        (11, 5, 27), (12, 5, 27), (13, 4, 28), (14, 4, 28), (15, 4, 28),
        (16, 5, 27), (17, 5, 27), (18, 6, 26), (19, 7, 25), (20, 8, 24),
        (21, 10, 22), (22, 13, 19),
    )
    draw_row_shape(img, rows, p["body"])
    outline_current_shape(img, p["outline"])
    draw_row_shape(img, ((8, 11, 18), (9, 9, 20), (10, 8, 17)), p["light"])
    draw_row_shape(img, ((18, 15, 24), (19, 16, 23), (20, 17, 22)), p["shade"])
    diamond_dot(img, 16, 15, p["accent"], p["glow"])


def draw_material_icon(visual, path):
    img = load_base_material(visual)
    pattern = visual["pattern"]
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]
    light = visual["light"]

    # Materiales: base vanilla reconocible primero, detalles pequeños después.
    outline_current_shape(img, mix(d, (0, 0, 0, 255), 0.25))

    if pattern == "angel_essence":
        line_solid(img, 8, 3, 8, 13, g)
        line_solid(img, 4, 8, 12, 8, light)
        diamond_dot_solid(img, 8, 8, a, g)
    elif pattern == "titanium_ingot":
        line_solid(img, 3, 11, 12, 5, light)
        line_solid(img, 4, 12, 13, 6, a)
        rect_solid(img, 3, 9, 12, 11, b)
        overlay_if_solid(img, 10, 6, g)
    elif pattern == "sea_essence":
        diamond_dot_solid(img, 8, 8, a, g)
        line_solid(img, 4, 5, 11, 11, light)
        overlay_if_solid(img, 12, 5, g)
        overlay_if_solid(img, 4, 12, g)
    elif pattern == "phoenix_feather":
        line_solid(img, 5, 13, 9, 5, a)
        line_solid(img, 8, 13, 11, 5, g)
        line_solid(img, 4, 11, 7, 8, b)
        overlay_if_solid(img, 9, 4, light)
    elif pattern == "dragon_scale":
        line_solid(img, 4, 11, 11, 4, a)
        line_solid(img, 5, 12, 12, 7, b)
        line_solid(img, 5, 6, 10, 3, light)
        overlay_if_solid(img, 8, 7, g)
    elif pattern == "void_fragment":
        rect_solid(img, 5, 5, 11, 11, d)
        line_solid(img, 4, 4, 12, 4, g)
        line_solid(img, 4, 12, 12, 12, b)
        diamond_dot_solid(img, 8, 8, a, g)
    elif pattern == "golem_heart":
        rect_solid(img, 5, 5, 11, 11, visual["mid"])
        diamond_dot_solid(img, 8, 8, visual["accent2"], g)
        overlay_if_solid(img, 5, 6, a)
        overlay_if_solid(img, 11, 9, a)
    elif pattern == "solar_essence":
        diamond_dot_solid(img, 8, 8, b, g)
        line_solid(img, 8, 2, 8, 6, a)
        line_solid(img, 8, 10, 8, 14, a)
        line_solid(img, 2, 8, 6, 8, a)
        line_solid(img, 10, 8, 14, 8, a)
    elif pattern == "chaos_fragment":
        line_solid(img, 3, 4, 12, 13, a)
        line_solid(img, 12, 4, 3, 13, b)
        diamond_dot_solid(img, 8, 8, a, g)
    elif pattern == "temporal_crystal":
        line_solid(img, 8, 2, 8, 13, b)
        line_solid(img, 5, 8, 11, 8, a)
        diamond_dot_solid(img, 8, 8, b, g)
    elif pattern == "celestial_fragment":
        diamond_dot_solid(img, 8, 8, a, g)
        line_solid(img, 4, 12, 12, 4, b)
        overlay_if_solid(img, 5, 5, g)
        overlay_if_solid(img, 11, 11, g)
    elif pattern == "infinity_core":
        line_solid(img, 4, 5, 12, 11, a)
        line_solid(img, 12, 5, 4, 11, b)
        diamond_dot_solid(img, 8, 8, light, g)

    save_png(path, MATERIAL_TEXTURE_SIZE, MATERIAL_TEXTURE_SIZE, img)


def special_palette(visual):
    return {
        "outline": mix(visual["dark"], (0, 0, 0, 255), 0.45),
        "deep": mix(visual["dark"], visual["shadow"], 0.30),
        "shade": visual["shadow"],
        "body": visual["mid"],
        "light": visual["light"],
        "accent": visual["accent"],
        "accent2": visual["accent2"],
        "glow": visual["glow"],
        "gold": mix(visual["accent"], visual["glow"], 0.45),
    }


def rect_outline(img, x0, y0, x1, y1, outline, fill):
    rect_any(img, x0, y0, x1, y1, outline)
    if x1 - x0 > 2 and y1 - y0 > 2:
        rect_any(img, x0 + 1, y0 + 1, x1 - 1, y1 - 1, fill)


def draw_row_shape(img, rows, fill):
    for y, x0, x1 in rows:
        rect_any(img, x0, y, x1, y + 1, fill)


def outline_current_shape(img, outline):
    source = [list(row) for row in img]
    height = len(source)
    width = len(source[0])

    def source_solid(x, y):
        return 0 <= y < height and 0 <= x < width and source[y][x][3] > 0

    for y, row in enumerate(source):
        for x, pixel in enumerate(row):
            if pixel[3] == 0:
                continue
            if any(not source_solid(x + dx, y + dy) for dx, dy in ((0, -1), (-1, 0), (1, 0), (0, 1))):
                set_pixel(img, x, y, outline)


def paste_opaque(dst, src):
    for y, row in enumerate(src):
        for x, px in enumerate(row):
            if px[3] > 0:
                set_pixel(dst, x, y, px)


def load_totem_base_32(visual):
    _, _, img = load_png(vanilla_texture("item", "totem_of_undying.png"))
    return upscale_nearest(recolor_image(img, visual), SPECIAL_TEXTURE_SIZE // 16)


def load_special_base_32(visual):
    _, _, img = load_png(vanilla_texture("item", f"{visual['base']}.png"))
    return upscale_nearest(recolor_image(img, visual), SPECIAL_TEXTURE_SIZE // 16)


def draw_totem_wing(img, left, outline, fill, light):
    if left:
        rows = ((8, 6, 10), (9, 4, 11), (10, 3, 12), (11, 2, 12), (12, 1, 11),
                (13, 2, 10), (14, 3, 9), (15, 5, 8))
        inner = ((9, 6, 10), (10, 5, 10), (11, 4, 10), (12, 3, 9), (13, 4, 8))
        draw_row_shape(img, rows, outline)
        draw_row_shape(img, inner, fill)
        line_any(img, 3, 12, 8, 10, light)
        line_any(img, 4, 14, 8, 13, light)
        return
    rows = ((8, 22, 26), (9, 21, 28), (10, 20, 29), (11, 20, 30), (12, 21, 31),
            (13, 22, 30), (14, 23, 29), (15, 24, 27))
    inner = ((9, 22, 26), (10, 22, 27), (11, 22, 28), (12, 23, 29), (13, 24, 28))
    draw_row_shape(img, rows, outline)
    draw_row_shape(img, inner, fill)
    line_any(img, 23, 10, 28, 12, light)
    line_any(img, 23, 13, 27, 14, light)


def draw_totem_body_32(img, visual, bulky=False, wings=False, halo=False, horns=False):
    p = special_palette(visual)
    outline = p["outline"]
    light = p["light"]
    accent = p["accent"]
    accent2 = p["accent2"]
    glow = p["glow"]

    if halo:
        rect_any(img, 11, 0, 21, 2, outline)
        rect_any(img, 12, 1, 20, 3, accent)
        rect_any(img, 13, 1, 19, 2, glow)

    if horns:
        set_pixel(img, 10, 3, accent)
        set_pixel(img, 21, 3, accent)
        set_pixel(img, 8, 2, outline)
        set_pixel(img, 23, 2, outline)

    paste_opaque(img, load_totem_base_32(visual))
    outline_current_shape(img, outline)

    rect_solid(img, 10, 6, 22, 8, light)
    rect_solid(img, 11, 10, 15, 13, accent2)
    rect_solid(img, 17, 10, 21, 13, accent2)
    set_pixel(img, 12, 10, glow)
    set_pixel(img, 18, 10, glow)
    rect_solid(img, 14, 13, 18, 16, light)
    rect_solid(img, 10, 18, 22, 21, light)
    line_solid(img, 11, 23, 21, 23, accent)

    if bulky:
        rect_solid(img, 4, 17, 28, 20, mix(outline, accent, 0.30))
        line_solid(img, 5, 18, 27, 18, light)
    if wings:
        line_solid(img, 2, 17, 11, 14, light)
        line_solid(img, 20, 14, 29, 17, light)


def draw_compass_token_32(visual):
    img = load_special_base_32(visual)
    p = special_palette(visual)
    outline = p["outline"]
    light = p["light"]
    accent = p["accent"]
    accent2 = p["accent2"]
    glow = p["glow"]

    outline_current_shape(img, outline)
    ring(img, 16, 16, 12, mix(accent, glow, 0.40))
    ring(img, 16, 16, 10, accent2)
    line_solid(img, 7, 23, 14, 16, accent2)
    line_solid(img, 14, 16, 20, 17, glow)
    line_solid(img, 20, 17, 25, 9, accent)
    line_solid(img, 9, 8, 14, 13, light)
    diamond_dot_solid(img, 16, 16, accent, glow)
    set_pixel(img, 16, 4, glow)
    set_pixel(img, 27, 16, light)
    set_pixel(img, 16, 27, accent)
    set_pixel(img, 4, 16, accent2)
    return img


def draw_revive_totem_32(visual):
    img = empty(SPECIAL_TEXTURE_SIZE, SPECIAL_TEXTURE_SIZE)
    draw_totem_body_32(img, visual)
    p = special_palette(visual)
    line_solid(img, 16, 12, 16, 24, p["glow"])
    line_solid(img, 10, 18, 22, 18, p["glow"])
    line_solid(img, 11, 17, 21, 17, p["accent"])
    set_pixel(img, 4, 6, p["glow"])
    set_pixel(img, 27, 6, p["glow"])
    set_pixel(img, 5, 25, p["accent"])
    set_pixel(img, 26, 25, p["accent"])
    return img


def draw_phoenix_totem_32(visual):
    img = empty(SPECIAL_TEXTURE_SIZE, SPECIAL_TEXTURE_SIZE)
    p = special_palette(visual)
    draw_totem_body_32(img, visual)
    line_solid(img, 10, 22, 15, 15, p["glow"])
    line_solid(img, 22, 22, 17, 15, p["accent"])
    line_solid(img, 8, 17, 2, 16, p["accent"])
    line_solid(img, 24, 17, 30, 16, p["accent"])
    set_pixel(img, 15, 2, p["glow"])
    set_pixel(img, 16, 1, p["accent"])
    set_pixel(img, 17, 2, p["glow"])
    return img


def draw_colossus_totem_32(visual):
    img = empty(SPECIAL_TEXTURE_SIZE, SPECIAL_TEXTURE_SIZE)
    draw_totem_body_32(img, visual, bulky=True)
    p = special_palette(visual)
    line_solid(img, 5, 18, 27, 18, p["light"])
    line_solid(img, 7, 20, 25, 20, p["deep"])
    rect_solid(img, 12, 15, 20, 23, p["deep"])
    diamond_dot(img, 16, 19, p["accent2"], p["glow"])
    set_pixel(img, 4, 25, p["light"])
    set_pixel(img, 27, 25, p["light"])
    return img


def draw_time_totem_32(visual):
    img = empty(SPECIAL_TEXTURE_SIZE, SPECIAL_TEXTURE_SIZE)
    p = special_palette(visual)
    draw_totem_body_32(img, visual)
    ring(img, 16, 18, 6, p["accent"])
    ring(img, 16, 18, 5, p["accent2"])
    line_solid(img, 16, 11, 16, 18, p["light"])
    line_solid(img, 16, 18, 22, 18, p["accent"])
    diamond_dot(img, 16, 18, p["accent2"], p["glow"])
    set_pixel(img, 6, 8, p["glow"])
    set_pixel(img, 25, 8, p["glow"])
    return img


def draw_celestial_totem_32(visual):
    img = empty(SPECIAL_TEXTURE_SIZE, SPECIAL_TEXTURE_SIZE)
    p = special_palette(visual)
    draw_totem_body_32(img, visual, wings=True, halo=True)
    line_solid(img, 16, 6, 16, 26, p["accent"])
    line_solid(img, 6, 16, 26, 16, p["accent"])
    line_solid(img, 10, 11, 22, 23, p["accent2"])
    line_solid(img, 22, 11, 10, 23, p["accent2"])
    diamond_dot(img, 16, 18, p["light"], p["glow"])
    set_pixel(img, 12, 13, p["glow"])
    set_pixel(img, 20, 13, p["glow"])
    set_pixel(img, 12, 23, p["glow"])
    set_pixel(img, 20, 23, p["glow"])
    return img


def draw_totemcito_32(visual):
    img = empty(SPECIAL_TEXTURE_SIZE, SPECIAL_TEXTURE_SIZE)
    p = special_palette(visual)
    draw_totem_body_32(img, visual, halo=True)
    rect_solid(img, 11, 6, 21, 10, p["light"])
    line_solid(img, 12, 17, 20, 17, p["glow"])
    line_solid(img, 3, 16, 10, 12, p["accent2"])
    line_solid(img, 22, 12, 29, 16, p["accent2"])
    diamond_dot(img, 16, 18, p["accent"], p["glow"])
    set_pixel(img, 7, 7, p["glow"])
    set_pixel(img, 24, 7, p["accent"])
    set_pixel(img, 6, 25, p["accent2"])
    set_pixel(img, 25, 25, p["glow"])
    return img


def draw_boss_key_32(visual):
    img = empty(SPECIAL_TEXTURE_SIZE, SPECIAL_TEXTURE_SIZE)
    p = special_palette(visual)
    outline = p["outline"]
    body = mix(p["body"], p["accent"], 0.18)
    light = p["light"]
    accent = p["accent"]
    accent2 = p["accent2"]
    glow = p["glow"]

    # Trial-key style silhouette: chunky head, straight shaft and stepped teeth.
    head = (
        (3, 12, 20), (4, 10, 22), (5, 8, 24), (6, 7, 25), (7, 7, 25),
        (8, 8, 24), (9, 10, 22), (10, 12, 20),
    )
    draw_row_shape(img, head, body)
    draw_row_shape(img, ((5, 13, 19), (6, 12, 20), (7, 12, 20), (8, 13, 19)), (0, 0, 0, 0))
    rect_any(img, 14, 9, 19, 25, body)
    rect_any(img, 18, 18, 25, 22, body)
    rect_any(img, 18, 24, 23, 27, body)
    rect_any(img, 13, 1, 20, 4, outline)
    rect_any(img, 14, 2, 19, 5, body)
    outline_current_shape(img, outline)

    line_solid(img, 9, 5, 21, 5, light)
    line_solid(img, 15, 10, 15, 24, light)
    line_solid(img, 18, 10, 18, 24, accent)
    rect_solid(img, 12, 13, 20, 15, accent2)
    rect_solid(img, 14, 13, 18, 15, glow)
    line_solid(img, 18, 19, 24, 19, light)
    line_solid(img, 18, 25, 22, 25, accent2)
    set_pixel(img, 14, 2, glow)
    set_pixel(img, 18, 3, light)
    return img


def draw_chaos_star_32(visual):
    img = load_special_base_32(visual)
    p = special_palette(visual)
    outline_current_shape(img, p["outline"])
    line_solid(img, 10, 10, 17, 17, p["deep"])
    line_solid(img, 21, 9, 15, 16, p["accent2"])
    line_solid(img, 8, 19, 15, 16, p["accent"])
    line_solid(img, 16, 7, 16, 23, p["glow"])
    diamond_dot_solid(img, 16, 16, p["accent"], p["glow"])
    set_pixel(img, 11, 12, p["accent"])
    set_pixel(img, 21, 19, p["glow"])
    set_pixel(img, 15, 24, p["accent2"])
    return img


def draw_boss_heart_32(visual, dragon=False):
    img = empty(SPECIAL_TEXTURE_SIZE, SPECIAL_TEXTURE_SIZE)
    p = special_palette(visual)
    rows = (
        (4, 10, 15), (4, 18, 23),
        (5, 8, 16), (5, 17, 25),
        (6, 6, 17), (6, 16, 27),
        (7, 5, 17), (7, 16, 28),
        (8, 5, 28), (9, 5, 28), (10, 5, 28),
        (11, 6, 27), (12, 6, 27), (13, 7, 26), (14, 8, 25),
        (15, 9, 24), (16, 10, 23), (17, 11, 22), (18, 12, 21),
        (19, 13, 20), (20, 14, 19), (21, 15, 18), (22, 16, 17),
    )
    draw_row_shape(img, rows, p["body"])
    outline_current_shape(img, p["outline"])
    draw_row_shape(img, ((6, 8, 15), (7, 7, 15), (8, 7, 16), (6, 18, 25), (7, 18, 26)), p["light"])
    draw_row_shape(img, ((13, 8, 25), (14, 9, 24), (15, 10, 23), (16, 11, 22), (17, 12, 21),
                         (18, 13, 20), (19, 14, 19)), p["shade"])
    diamond_dot_solid(img, 16, 13, p["accent"], p["glow"])

    if dragon:
        line_solid(img, 9, 13, 23, 9, p["accent"])
        line_solid(img, 10, 17, 24, 13, p["accent2"])
        line_solid(img, 13, 21, 19, 17, p["glow"])
        set_pixel(img, 9, 8, p["glow"])
        set_pixel(img, 24, 8, p["accent"])
    else:
        line_solid(img, 9, 12, 23, 12, p["glow"])
        line_solid(img, 11, 16, 21, 16, p["accent"])
        line_solid(img, 13, 20, 18, 17, p["accent2"])
        set_pixel(img, 8, 9, p["glow"])
        set_pixel(img, 24, 9, p["glow"])
    return img


def draw_boss_altar_32(visual):
    img = empty(SPECIAL_TEXTURE_SIZE, SPECIAL_TEXTURE_SIZE)
    p = special_palette(visual)
    outline = p["outline"]
    deep = p["deep"]
    body = p["body"]
    light = p["light"]
    accent = p["accent"]
    accent2 = p["accent2"]
    glow = p["glow"]
    boss_id = visual["id"].split("_", 1)[0]

    base = (
        (23, 8, 24), (24, 6, 26), (25, 4, 28), (26, 3, 29),
        (27, 4, 28), (28, 7, 25), (29, 10, 22),
    )
    column = (
        (11, 11, 21), (12, 9, 23), (13, 8, 24), (14, 8, 24),
        (15, 8, 24), (16, 8, 24), (17, 8, 24), (18, 8, 24),
        (19, 8, 24), (20, 8, 24), (21, 9, 23), (22, 11, 21),
    )
    top = (
        (6, 11, 21), (7, 9, 23), (8, 7, 25), (9, 6, 26),
        (10, 7, 25), (11, 9, 23),
    )

    draw_row_shape(img, base, deep)
    draw_row_shape(img, column, body)
    draw_row_shape(img, top, light)
    outline_current_shape(img, outline)

    rect_any(img, 10, 14, 13, 22, deep)
    rect_any(img, 19, 14, 22, 22, deep)
    rect_any(img, 14, 14, 18, 22, mix(deep, body, 0.45))
    rect_any(img, 8, 24, 24, 26, accent2)
    rect_any(img, 9, 8, 23, 10, mix(light, glow, 0.20))
    rect_any(img, 7, 11, 10, 14, accent)
    rect_any(img, 22, 11, 25, 14, accent2)
    rect_any(img, 5, 25, 8, 27, mix(accent, outline, 0.35))
    rect_any(img, 24, 25, 27, 27, mix(accent2, outline, 0.35))

    if boss_id == "colossus":
        rect_any(img, 12, 15, 20, 21, deep)
        rect_any(img, 13, 16, 19, 20, body)
        diamond_dot(img, 16, 18, accent, glow)
        line_any(img, 10, 23, 22, 23, light)
    elif boss_id == "eclipse":
        ring(img, 16, 18, 5, accent)
        rect_any(img, 16, 13, 22, 23, body)
        line_any(img, 12, 15, 18, 21, glow)
        set_pixel(img, 13, 12, accent2)
    elif boss_id == "void":
        draw_diamond_shape(img, 16, 18, 5, outline, deep, accent2)
        diamond_dot(img, 16, 18, accent, glow)
        line_any(img, 11, 14, 21, 22, accent2)
    elif boss_id == "phoenix":
        line_any(img, 16, 13, 12, 21, accent)
        line_any(img, 16, 13, 20, 21, glow)
        line_any(img, 12, 21, 20, 21, accent2)
        set_pixel(img, 16, 12, glow)
    elif boss_id == "celestial":
        line_any(img, 16, 12, 16, 24, glow)
        line_any(img, 10, 18, 22, 18, light)
        diamond_dot(img, 16, 18, accent, glow)
    elif boss_id == "infinity":
        ring(img, 13, 18, 4, accent)
        ring(img, 19, 18, 4, accent2)
        line_any(img, 12, 18, 20, 18, glow)

    outline_current_shape(img, outline)
    line_solid(img, 9, 8, 22, 8, glow)
    line_solid(img, 9, 15, 23, 15, light)
    line_solid(img, 8, 27, 24, 27, outline)
    return img


def draw_special_item_icon(visual, path):
    pattern = visual["pattern"]
    if pattern == "waystone_token":
        img = draw_compass_token_32(visual)
    elif pattern == "revive_totem":
        img = draw_revive_totem_32(visual)
    elif pattern == "phoenix_totem":
        img = draw_phoenix_totem_32(visual)
    elif pattern == "colossus_totem":
        img = draw_colossus_totem_32(visual)
    elif pattern == "time_totem":
        img = draw_time_totem_32(visual)
    elif pattern == "celestial_totem":
        img = draw_celestial_totem_32(visual)
    elif pattern == "totemcito":
        img = draw_totemcito_32(visual)
    elif pattern == "boss_key":
        img = draw_boss_key_32(visual)
    elif pattern == "chaos_star":
        img = draw_chaos_star_32(visual)
    elif pattern == "warden_heart":
        img = draw_boss_heart_32(visual)
    elif pattern == "dragon_heart":
        img = draw_boss_heart_32(visual, dragon=True)
    elif pattern == "boss_altar":
        img = draw_boss_altar_32(visual)
    else:
        img = load_base_material(visual)
    save_png(path, SPECIAL_TEXTURE_SIZE, SPECIAL_TEXTURE_SIZE, img)


def thick_line(img, x0, y0, x1, y1, color):
    line_any(img, x0, y0, x1, y1, color)
    if abs(x1 - x0) >= abs(y1 - y0):
        line_any(img, x0, y0 + 1, x1, y1 + 1, color)
    else:
        line_any(img, x0 + 1, y0, x1 + 1, y1, color)


def solid_at(img, x, y):
    return 0 <= y < len(img) and 0 <= x < len(img[0]) and img[y][x][3] > 0


def near_solid(img, x, y, radius=1):
    if not (0 <= y < len(img) and 0 <= x < len(img[0])):
        return False
    for yy in range(y - radius, y + radius + 1):
        for xx in range(x - radius, x + radius + 1):
            if solid_at(img, xx, yy):
                return True
    return False


def set_if_near_solid(img, x, y, color, radius=1):
    if near_solid(img, x, y, radius):
        set_pixel(img, x, y, color)


def rect_near_solid(img, x0, y0, x1, y1, color, radius=1):
    for y in range(y0, y1):
        for x in range(x0, x1):
            set_if_near_solid(img, x, y, color, radius)


def line_near_solid(img, x0, y0, x1, y1, color, radius=1):
    dx = abs(x1 - x0)
    dy = -abs(y1 - y0)
    sx = 1 if x0 < x1 else -1
    sy = 1 if y0 < y1 else -1
    err = dx + dy
    while True:
        set_if_near_solid(img, x0, y0, color, radius)
        if x0 == x1 and y0 == y1:
            break
        e2 = 2 * err
        if e2 >= dy:
            err += dy
            x0 += sx
        if e2 <= dx:
            err += dx
            y0 += sy


def line2_near_solid(img, x0, y0, x1, y1, color, radius=1):
    line_near_solid(img, x0, y0, x1, y1, color, radius)
    if abs(x1 - x0) >= abs(y1 - y0):
        line_near_solid(img, x0, y0 + 1, x1, y1 + 1, color, radius)
    else:
        line_near_solid(img, x0 + 1, y0, x1 + 1, y1, color, radius)


def block_near_solid(img, x, y, color, radius=1):
    rect_near_solid(img, x, y, x + 2, y + 2, color, radius)


def gem_near_solid(img, visual, cx, cy, primary=None, center=None, radius=2):
    primary = primary or visual["accent"]
    center = center or visual["glow"]
    block_near_solid(img, cx - 1, cy - 1, center, radius)
    for x, y in ((cx, cy - 3), (cx - 3, cy), (cx + 2, cy), (cx, cy + 2)):
        block_near_solid(img, x, y, primary, radius)


def reinforce_gear_outline(img, visual):
    source = [list(row) for row in img]
    outline = mix(visual["dark"], (0, 0, 0, 255), 0.35)
    height = len(source)
    width = len(source[0])

    def source_solid(x, y):
        return 0 <= y < height and 0 <= x < width and source[y][x][3] > 0

    for y, row in enumerate(source):
        for x, pixel in enumerate(row):
            if pixel[3] == 0:
                continue
            if any(not source_solid(x + dx, y + dy) for dx, dy in ((0, -1), (-1, 0), (1, 0), (0, 1))):
                img[y][x] = (outline[0], outline[1], outline[2], pixel[3])


def gear_palette(visual):
    return {
        "outline": mix(visual["dark"], (0, 0, 0, 255), 0.35),
        "handle": mix(visual["dark"], visual["accent2"], 0.35),
        "handle_light": mix(visual["shadow"], visual["accent2"], 0.45),
        "metal": mix(visual["mid"], visual["light"], 0.35),
        "shine": mix(visual["light"], visual["glow"], 0.25),
        "accent": visual["accent"],
        "accent2": visual["accent2"],
        "glow": visual["glow"],
        "shadow": visual["shadow"],
        "dark": visual["dark"],
    }


def load_base_gear(visual, gear_type):
    base = GEAR_BASE_FILES.get(gear_type)
    if base is None:
        return None
    _, _, img = load_png(vanilla_texture("item", f"{base}.png"))
    return upscale_nearest(recolor_image(img, visual), GEAR_TEXTURE_SIZE // 16)


def add_vanilla_gear_retouches(img, visual, gear_type):
    p = gear_palette(visual)
    reinforce_gear_outline(img, visual)

    if gear_type == "sword":
        line2_near_solid(img, 11, 22, 27, 6, p["shine"], 1)
        line_near_solid(img, 13, 22, 27, 8, p["metal"], 1)
        line2_near_solid(img, 1, 28, 7, 22, p["handle"], 1)
        line_near_solid(img, 3, 29, 8, 24, p["handle_light"], 1)
        line2_near_solid(img, 4, 22, 11, 29, p["outline"], 1)
        line2_near_solid(img, 5, 21, 12, 28, p["accent"], 1)
        gem_near_solid(img, visual, 10, 23, p["accent2"], p["glow"], 2)
    elif gear_type == "axe":
        line2_near_solid(img, 6, 29, 20, 15, p["handle"], 1)
        line_near_solid(img, 8, 29, 21, 16, p["handle_light"], 1)
        line2_near_solid(img, 15, 7, 25, 4, p["shine"], 1)
        line2_near_solid(img, 16, 11, 28, 11, p["metal"], 1)
        line2_near_solid(img, 18, 16, 26, 16, p["shadow"], 1)
        rect_near_solid(img, 18, 12, 23, 16, p["accent"], 1)
        gem_near_solid(img, visual, 20, 13, p["accent2"], p["glow"], 2)
    elif gear_type == "bow":
        line_any(img, 25, 4, 9, 28, p["glow"])
        line_any(img, 24, 5, 10, 27, p["accent2"])
        line2_near_solid(img, 6, 5, 11, 1, p["shine"], 1)
        line2_near_solid(img, 4, 25, 9, 29, p["metal"], 1)
        rect_near_solid(img, 8, 14, 15, 18, p["handle"], 1)
        rect_near_solid(img, 9, 15, 14, 17, p["accent"], 1)
        gem_near_solid(img, visual, 12, 16, p["accent2"], p["glow"], 2)
    elif gear_type == "pickaxe":
        line2_near_solid(img, 6, 29, 20, 15, p["handle"], 1)
        line_near_solid(img, 8, 29, 21, 16, p["handle_light"], 1)
        line2_near_solid(img, 7, 5, 24, 5, p["shine"], 1)
        line2_near_solid(img, 5, 8, 15, 8, p["metal"], 1)
        line2_near_solid(img, 20, 8, 28, 13, p["metal"], 1)
        rect_near_solid(img, 15, 9, 22, 13, p["accent"], 1)
        gem_near_solid(img, visual, 18, 11, p["accent2"], p["glow"], 2)
    elif gear_type == "trident":
        line2_near_solid(img, 4, 28, 21, 11, p["handle"], 1)
        line_near_solid(img, 6, 28, 22, 12, p["handle_light"], 1)
        line2_near_solid(img, 14, 8, 23, 17, p["shine"], 1)
        line2_near_solid(img, 18, 5, 27, 14, p["metal"], 1)
        line2_near_solid(img, 10, 10, 18, 18, p["accent"], 1)
        gem_near_solid(img, visual, 13, 18, p["accent2"], p["glow"], 2)


def add_gear_family_marks_32(img, visual, gear_type):
    family = visual["pattern"]
    p = gear_palette(visual)
    anchors = {
        "sword": (10, 23),
        "axe": (20, 13),
        "bow": (12, 16),
        "pickaxe": (18, 11),
        "trident": (13, 18),
        "spear": (19, 18),
    }
    cx, cy = anchors.get(gear_type, (16, 16))

    if family in ("phoenix", "dragon", "chaos"):
        line2_near_solid(img, cx + 3, cy - 5, cx + 8, cy - 10, p["glow"], 1)
        line_near_solid(img, cx + 2, cy - 3, cx + 6, cy - 7, p["accent"], 1)
    elif family in ("leviathan", "time", "celestial", "infinity"):
        block_near_solid(img, cx - 4, cy + 2, p["glow"], 1)
        block_near_solid(img, cx + 3, cy - 4, p["accent"], 1)
    elif family in ("titanium", "colossus"):
        block_near_solid(img, cx - 4, cy + 1, p["accent2"], 1)
        block_near_solid(img, cx + 3, cy - 3, p["shine"], 1)
    elif family == "void":
        line_near_solid(img, cx - 4, cy + 4, cx + 4, cy - 4, p["dark"], 1)
        block_near_solid(img, cx + 2, cy - 2, p["glow"], 1)
    elif family == "eclipse":
        block_near_solid(img, cx - 2, cy - 2, p["dark"], 1)
        block_near_solid(img, cx + 2, cy + 1, p["glow"], 1)

    if gear_type == "bow":
        line_near_solid(img, 7, 8, 11, 12, p["accent"], 1)
        line_near_solid(img, 6, 23, 10, 20, p["accent"], 1)
    elif gear_type in ("axe", "pickaxe"):
        block_near_solid(img, cx - 1, cy - 1, p["glow"], 2)
    elif gear_type in ("sword", "trident", "spear"):
        block_near_solid(img, cx - 1, cy - 1, p["glow"], 2)


def draw_spear_icon(visual):
    img = empty(GEAR_TEXTURE_SIZE, GEAR_TEXTURE_SIZE)
    p = gear_palette(visual)

    thick_line(img, 4, 30, 23, 11, p["outline"])
    thick_line(img, 6, 30, 25, 11, p["handle"])
    line_any(img, 8, 29, 25, 12, p["handle_light"])

    for y, x0, x1 in (
        (3, 26, 28),
        (4, 24, 29),
        (5, 23, 30),
        (6, 22, 31),
        (7, 22, 31),
        (8, 23, 30),
        (9, 24, 29),
        (10, 24, 28),
    ):
        rect_any(img, x0, y, x1, y + 1, p["outline"])
    for y, x0, x1 in (
        (4, 26, 28),
        (5, 24, 29),
        (6, 24, 30),
        (7, 24, 30),
        (8, 25, 29),
        (9, 25, 28),
    ):
        rect_any(img, x0, y, x1, y + 1, p["metal"])
    line_any(img, 25, 4, 29, 6, p["shine"])
    line_any(img, 24, 9, 29, 6, p["shadow"])
    set_pixel(img, 27, 3, p["glow"])

    thick_line(img, 14, 20, 20, 26, p["outline"])
    thick_line(img, 17, 17, 23, 23, p["outline"])
    line_any(img, 15, 20, 20, 25, p["accent"])
    line_any(img, 18, 17, 23, 22, p["accent2"])
    gem_near_solid(img, visual, 19, 18, p["accent2"], p["glow"], 2)
    add_gear_family_marks_32(img, visual, "spear")
    return img


def draw_gear_icon(visual, gear_type, path):
    base_img = load_base_gear(visual, gear_type)
    if base_img is not None:
        add_vanilla_gear_retouches(base_img, visual, gear_type)
        add_gear_family_marks_32(base_img, visual, gear_type)
        save_png(path, GEAR_TEXTURE_SIZE, GEAR_TEXTURE_SIZE, base_img)
        return

    if gear_type == "spear":
        save_png(path, GEAR_TEXTURE_SIZE, GEAR_TEXTURE_SIZE, draw_spear_icon(visual))


def add_armor_layer_structure(img, visual, leggings):
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]
    light = visual["light"]

    if leggings:
        line_solid(img, 4, 16, 12, 16, a)
        line_solid(img, 4, 20, 7, 20, b)
        line_solid(img, 12, 20, 15, 20, b)
        line_solid(img, 5, 24, 7, 24, g)
        line_solid(img, 13, 24, 15, 24, g)
        line_solid(img, 4, 29, 7, 29, light)
        line_solid(img, 12, 29, 15, 29, light)
        return

    # Casco: banda superior, visor y laterales simetricos.
    line_solid(img, 8, 1, 15, 1, light)
    line_solid(img, 0, 8, 7, 8, a)
    line_solid(img, 16, 8, 23, 8, a)
    line_solid(img, 8, 12, 15, 12, b)

    # Pechera, brazos y botas: paneles discretos sobre las zonas vanilla.
    line_solid(img, 16, 17, 31, 17, a)
    line_solid(img, 20, 19, 27, 19, light)
    line_solid(img, 20, 23, 27, 23, b)
    line_solid(img, 20, 27, 27, 27, d)
    line_solid(img, 40, 19, 47, 19, a)
    line_solid(img, 48, 19, 55, 19, a)
    line_solid(img, 40, 27, 47, 27, b)
    line_solid(img, 48, 27, 55, 27, b)
    line_solid(img, 3, 24, 7, 24, a)
    line_solid(img, 10, 24, 14, 24, a)
    line_solid(img, 3, 29, 8, 29, g)
    line_solid(img, 11, 29, 16, 29, g)


def add_common_layer_marks(img, visual, leggings):
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]

    if leggings:
        rect_solid(img, 4, 16, 12, 20, a)
        rect_solid(img, 4, 26, 8, 28, g)
        rect_solid(img, 12, 26, 16, 28, g)
        line_solid(img, 5, 20, 7, 31, b)
        line_solid(img, 13, 20, 15, 31, b)
        rect_solid(img, 20, 29, 28, 31, d)
        return

    rect_solid(img, 9, 8, 15, 10, a)
    rect_solid(img, 9, 10, 15, 15, d)
    set_pixel(img, 10, 11, g)
    set_pixel(img, 13, 11, g)
    rect_solid(img, 21, 21, 27, 24, visual["light"])
    line_solid(img, 20, 22, 24, 28, a)
    line_solid(img, 27, 22, 24, 28, a)
    line_solid(img, 21, 23, 24, 28, g)
    line_solid(img, 26, 23, 24, 28, g)
    rect_solid(img, 23, 24, 25, 27, b)
    rect_solid(img, 20, 29, 28, 31, d)
    line_solid(img, 41, 24, 55, 24, a)
    line_solid(img, 41, 28, 55, 28, b)
    rect_solid(img, 4, 29, 8, 31, d)
    rect_solid(img, 12, 29, 16, 31, d)


def add_special_layer_marks(img, visual, leggings):
    p = visual["pattern"]
    a = visual["accent"]
    b = visual["accent2"]
    g = visual["glow"]
    d = visual["dark"]

    if leggings:
        if p in ("celestial", "time", "infinity"):
            set_pixel(img, 6, 23, g)
            set_pixel(img, 14, 23, g)
        if p in ("phoenix", "chaos", "dragon"):
            if p == "chaos":
                line_solid(img, 4, 25, 7, 31, a)
                line_solid(img, 12, 25, 15, 31, a)
            else:
                line_solid(img, 4, 31, 7, 25, a)
                line_solid(img, 12, 31, 15, 25, a)
        if p == "colossus":
            rect_solid(img, 4, 20, 8, 22, b)
            rect_solid(img, 12, 20, 16, 22, b)
        return

    if p == "leviathan":
        line_solid(img, 9, 8, 7, 5, a)
        line_solid(img, 14, 8, 16, 5, a)
        rect_solid(img, 23, 24, 26, 27, a)
        set_pixel(img, 24, 25, g)
    elif p == "phoenix":
        line_solid(img, 22, 30, 24, 24, g)
        line_solid(img, 26, 30, 24, 24, visual["glow"])
        line_solid(img, 11, 8, 12, 5, g)
    elif p == "void":
        rect_solid(img, 9, 11, 15, 15, d)
        line_solid(img, 8, 9, 15, 9, g)
        rect_solid(img, 23, 24, 26, 27, g)
    elif p == "dragon":
        line_solid(img, 9, 8, 6, 5, b)
        line_solid(img, 14, 8, 17, 5, b)
        line_solid(img, 20, 30, 28, 30, b)
    elif p == "chaos":
        rect_solid(img, 20, 21, 24, 24, a)
        rect_solid(img, 25, 21, 28, 24, a)
        line_solid(img, 21, 28, 27, 28, a)
        set_pixel(img, 10, 11, g)
        set_pixel(img, 13, 11, g)
    elif p == "celestial":
        rect_solid(img, 23, 23, 26, 27, a)
        set_pixel(img, 24, 24, g)
        line_solid(img, 20, 30, 28, 30, b)
    elif p == "eclipse":
        rect_solid(img, 23, 23, 26, 27, a)
        rect_solid(img, 23, 26, 26, 28, b)
        set_pixel(img, 24, 24, g)
    elif p == "time":
        rect_solid(img, 23, 23, 26, 27, b)
        for x, y in ((24, 23), (24, 27), (22, 25), (26, 25)):
            set_pixel(img, x, y, a)
    elif p == "infinity":
        rect_solid(img, 21, 22, 27, 26, b)
        line_solid(img, 20, 28, 28, 28, a)
        set_pixel(img, 24, 24, g)
    elif p == "colossus":
        rect_solid(img, 20, 21, 28, 23, b)
        rect_solid(img, 20, 29, 28, 31, b)
        for x, y in ((21, 24), (26, 24), (21, 28), (26, 28)):
            set_pixel(img, x, y, a)
    elif p == "titanium":
        line_solid(img, 21, 29, 27, 23, a)
        set_pixel(img, 24, 25, g)


def draw_layer(visual, leggings, path):
    img = load_base_layer(visual, leggings)
    add_armor_layer_structure(img, visual, leggings)
    add_common_layer_marks(img, visual, leggings)
    add_special_layer_marks(img, visual, leggings)
    save_png(path, 64, 32, img)


def write_text(path, content):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        f.write(content)


def write_json(path, payload):
    write_text(path, json.dumps(payload, indent=2, ensure_ascii=False) + "\n")


def write_model_files(visual):
    set_id = visual["id"]
    equipment_payload = {
        "layers": {
            "humanoid": [{"texture": f"{NS}:{set_id}"}],
            "humanoid_leggings": [{"texture": f"{NS}:{set_id}"}],
        }
    }
    write_json(os.path.join(PACK_DIR, "assets", NS, "equipment", f"{set_id}.json"), equipment_payload)
    write_json(os.path.join(PACK_DIR, "assets", NS, "models", "equipment", f"{set_id}.json"), equipment_payload)

    for piece in PIECES:
        item_id = f"{set_id}_{piece}"
        write_json(
            os.path.join(PACK_DIR, "assets", NS, "items", f"{item_id}.json"),
            {
                "model": {
                    "type": "minecraft:model",
                    "model": f"{NS}:item/{item_id}",
                }
            },
        )
        write_json(
            os.path.join(PACK_DIR, "assets", NS, "models", "item", f"{item_id}.json"),
            {
                "parent": "minecraft:item/generated",
                "textures": {"layer0": f"{NS}:item/{item_id}"},
            },
        )


def write_material_model_files(visual):
    item_id = visual["id"]
    write_json(
        os.path.join(PACK_DIR, "assets", NS, "items", f"{item_id}.json"),
        {
            "model": {
                "type": "minecraft:model",
                "model": f"{NS}:item/{item_id}",
            }
        },
    )
    write_json(
        os.path.join(PACK_DIR, "assets", NS, "models", "item", f"{item_id}.json"),
        {
            "parent": "minecraft:item/generated",
            "textures": {"layer0": f"{NS}:item/{item_id}"},
        },
    )


def write_gear_model_files(visual):
    for gear_type in GEAR_TYPES:
        write_material_model_files({"id": f"{visual['id']}_{gear_type}"})


def write_textures(visual):
    tex_root = os.path.join(PACK_DIR, "assets", NS, "textures")
    draw_layer(visual, False, os.path.join(tex_root, "entity", "equipment", "humanoid", f"{visual['id']}.png"))
    draw_layer(visual, True, os.path.join(tex_root, "entity", "equipment", "humanoid_leggings", f"{visual['id']}.png"))
    for piece in PIECES:
        draw_icon(visual, piece, os.path.join(tex_root, "item", f"{visual['id']}_{piece}.png"))


def write_material_texture(visual):
    tex_root = os.path.join(PACK_DIR, "assets", NS, "textures")
    draw_material_icon(visual, os.path.join(tex_root, "item", f"{visual['id']}.png"))


def write_special_item_texture(visual):
    tex_root = os.path.join(PACK_DIR, "assets", NS, "textures")
    draw_special_item_icon(visual, os.path.join(tex_root, "item", f"{visual['id']}.png"))


def write_gear_textures(visual):
    tex_root = os.path.join(PACK_DIR, "assets", NS, "textures")
    for gear_type in GEAR_TYPES:
        draw_gear_icon(visual, gear_type, os.path.join(tex_root, "item", f"{visual['id']}_{gear_type}.png"))


def make_preview():
    scale = 2
    cell = 70
    cols = 4
    rows = (len(SETS) + cols - 1) // cols
    width = cols * cell * 4 * scale
    height = rows * cell * scale
    bg = (42, 42, 42, 255)
    sep = (78, 78, 78, 255)
    out = [[bg for _ in range(width)] for _ in range(height)]

    for index, visual in enumerate(SETS):
        group_x = (index % cols) * cell * 4 * scale
        group_y = (index // cols) * cell * scale
        for x in range(group_x, min(width, group_x + cell * 4 * scale)):
            out[group_y][x] = sep
        for piece_index, piece in enumerate(PIECES):
            _, _, img = load_png(os.path.join(
                PACK_DIR, "assets", NS, "textures", "item", f"{visual['id']}_{piece}.png"
            ))
            ox = group_x + (piece_index * cell + 2) * scale
            oy = group_y + 2 * scale
            for y, row in enumerate(img):
                for x, px in enumerate(row):
                    if px[3] == 0:
                        continue
                    for yy in range(scale):
                        for xx in range(scale):
                            out[oy + y * scale + yy][ox + x * scale + xx] = px

    save_png(os.path.join(PACK_DIR, "preview_armor_icons_x2.png"), width, height, out)


def make_material_preview():
    scale = 4
    cell = MATERIAL_TEXTURE_SIZE + 6
    cols = 6
    rows = (len(MATERIALS) + cols - 1) // cols
    width = cols * cell * scale
    height = rows * cell * scale
    bg = (42, 42, 42, 255)
    sep = (78, 78, 78, 255)
    out = [[bg for _ in range(width)] for _ in range(height)]

    for index, visual in enumerate(MATERIALS):
        cell_x = (index % cols) * cell * scale
        cell_y = (index // cols) * cell * scale
        for x in range(cell_x, min(width, cell_x + cell * scale)):
            out[cell_y][x] = sep
        _, _, img = load_png(os.path.join(PACK_DIR, "assets", NS, "textures", "item", f"{visual['id']}.png"))
        ox = cell_x + 2 * scale
        oy = cell_y + 2 * scale
        for y, row in enumerate(img):
            for x, px in enumerate(row):
                if px[3] == 0:
                    continue
                for yy in range(scale):
                    for xx in range(scale):
                        out[oy + y * scale + yy][ox + x * scale + xx] = px

    save_png(os.path.join(PACK_DIR, "preview_material_icons_x4.png"), width, height, out)


def make_special_item_preview():
    scale = 4
    cell = SPECIAL_TEXTURE_SIZE + 6
    width = len(SPECIAL_ITEMS) * cell * scale
    height = cell * scale
    bg = (42, 42, 42, 255)
    sep = (78, 78, 78, 255)
    out = [[bg for _ in range(width)] for _ in range(height)]

    for index, visual in enumerate(SPECIAL_ITEMS):
        cell_x = index * cell * scale
        for x in range(cell_x, min(width, cell_x + cell * scale)):
            out[0][x] = sep
        _, _, img = load_png(os.path.join(PACK_DIR, "assets", NS, "textures", "item", f"{visual['id']}.png"))
        ox = cell_x + 2 * scale
        oy = 2 * scale
        for y, row in enumerate(img):
            for x, px in enumerate(row):
                if px[3] == 0:
                    continue
                for yy in range(scale):
                    for xx in range(scale):
                        out[oy + y * scale + yy][ox + x * scale + xx] = px

    save_png(os.path.join(PACK_DIR, "preview_special_items_x4.png"), width, height, out)


def make_gear_preview():
    scale = 3
    cell = 38
    width = len(GEAR_TYPES) * cell * scale
    height = len(SETS) * cell * scale
    bg = (42, 42, 42, 255)
    sep = (78, 78, 78, 255)
    out = [[bg for _ in range(width)] for _ in range(height)]

    for set_index, visual in enumerate(SETS):
        row_y = set_index * cell * scale
        for x in range(width):
            out[row_y][x] = sep
        for type_index, gear_type in enumerate(GEAR_TYPES):
            _, _, img = load_png(os.path.join(
                PACK_DIR, "assets", NS, "textures", "item", f"{visual['id']}_{gear_type}.png"
            ))
            ox = (type_index * cell + 3) * scale
            oy = row_y + 3 * scale
            for y, row in enumerate(img):
                for x, px in enumerate(row):
                    if px[3] == 0:
                        continue
                    for yy in range(scale):
                        for xx in range(scale):
                            out[oy + y * scale + yy][ox + x * scale + xx] = px

    save_png(os.path.join(PACK_DIR, "preview_gear_icons_x6.png"), width, height, out)


def zip_pack(path):
    if os.path.exists(path):
        os.remove(path)
    with zipfile.ZipFile(path, "w", zipfile.ZIP_DEFLATED) as zf:
        for root, _, files in os.walk(PACK_DIR):
            for name in files:
                file_path = os.path.join(root, name)
                zf.write(file_path, os.path.relpath(file_path, PACK_DIR).replace(os.sep, "/"))


def build_pack():
    if os.path.isdir(PACK_DIR):
        shutil.rmtree(PACK_DIR)
    os.makedirs(PACK_DIR, exist_ok=True)

    write_json(
        os.path.join(PACK_DIR, "pack.mcmeta"),
        {
            "pack": {
                "min_format": 88,
                "max_format": 88,
                "description": "HardcorePlus - Custom Armor v0.23",
            }
        },
    )

    for visual in SETS:
        write_model_files(visual)
        write_textures(visual)
        write_gear_model_files(visual)
        write_gear_textures(visual)

    for visual in MATERIALS:
        write_material_model_files(visual)
        write_material_texture(visual)

    for visual in SPECIAL_ITEMS:
        write_material_model_files(visual)
        write_special_item_texture(visual)

    make_preview()
    make_material_preview()
    make_special_item_preview()
    make_gear_preview()
    draw_icon(SETS[10], "chestplate", os.path.join(PACK_DIR, "pack.png"))
    zip_pack(ZIP_PATH)

    print(PACK_DIR)
    print(ZIP_PATH)


if __name__ == "__main__":
    build_pack()
