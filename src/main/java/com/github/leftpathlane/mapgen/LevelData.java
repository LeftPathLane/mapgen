package com.github.leftpathlane.mapgen;

import com.github.leftpathlane.jnbt.types.*;

import java.util.HashMap;

public class LevelData {
	public byte allowCommands = 1;
	public double borderCenterX;
	public double borderCenterZ;
	public double borderDamagePerBlock = 0.2;
	public double borderSafeZone = 5;
	public double borderSize = 60000000;
	public double borderSizeLerpTarget = 60000000;
	public double borderSizeLerpTime;
	public double borderWarningBlocks = 5;
	public double borderWarningTime = 15;
	public int clearWeatherTime;
	public long dayTime;
	public byte difficulty = 2;
	public byte difficultyLocked;
	public int gameType = 1;
	public String generatorName = "flat";
	public String generatorOptions = "3;minecraft:air;1;";
	public int generatorVersion;
	public byte hardcore;
	public byte initialized = 1;
	public long lastPlayed;
	public String levelName = "flat world";
	public byte mapFeatures;
	public byte raining;
	public int rainTime = Integer.MAX_VALUE;
	public long randomSeed = 1;
	public long sizeOnDisk;
	public int spawnX = 0;
	public int spawnY = 1;
	public int spawnZ = 1;
	public byte thundering;
	public int thunderTime = Integer.MAX_VALUE;
	public long time;
	public int version = 19133;

	public String commandBlockOutput = "true";
	public String doDaylightCycle = "true";
	public String doEntityDrops = "true";
	public String doFireTick = "true";
	public String doMobLoot = "true";
	public String doMobSpawning = "true";
	public String dotileDrop = "true";
	public String keepInventory = "false";
	public String logAdminCommands = "true";
	public String mobGriefing = "true";
	public String naturalRegenration = "true";
	public String randomtickSpeed = "3";
	public String reducedDebugInfo = "false";
	public String sendCommandFeedback = "true";
	public String showDeathMessages = "true";


	public NbtCompound toNbt() {
		NbtCompound root = new NbtCompound("", new HashMap<String, NbtType>());
		NbtCompound nbt = new NbtCompound("Data", new HashMap<String, NbtType>());
		root.addNbt(nbt);
		NbtCompound gameRules = new NbtCompound("GameRules", new HashMap<String, NbtType>());
		nbt.addNbt(gameRules);
		gameRules.addNbt("commandBlockOutput", commandBlockOutput);
		gameRules.addNbt("doDaylightCycle", doDaylightCycle);
		gameRules.addNbt("doEntityDrops", doEntityDrops);
		gameRules.addNbt("doFireTick", doFireTick);
		gameRules.addNbt("doMobLood", doMobLoot);
		gameRules.addNbt("doMobSpawning", doMobSpawning);
		gameRules.addNbt("dotileDrops", dotileDrop);
		gameRules.addNbt("keepInventory", keepInventory);
		gameRules.addNbt("logAdminCommands", logAdminCommands);
		gameRules.addNbt("mobGriefing", mobGriefing);
		gameRules.addNbt("naturalRegeneration", naturalRegenration);
		gameRules.addNbt("randomTickSpeed", randomtickSpeed);
		gameRules.addNbt("reducedDebugInfo", reducedDebugInfo);
		gameRules.addNbt("sendCommandfeedback", sendCommandFeedback);
		gameRules.addNbt("showDeathMessages", showDeathMessages);

		nbt.addNbt("allowCommands", allowCommands);
		nbt.addNbt("BorderCenterX", borderCenterX);
		nbt.addNbt("BorderCenterZ", borderCenterZ);
		nbt.addNbt("BorderDamagerPerBlock", borderDamagePerBlock);
		nbt.addNbt("BorderSafeZone", borderSafeZone);
		nbt.addNbt("BorderSize", borderSize);
		nbt.addNbt("BorderSizeLerpTarget", borderSizeLerpTarget);
		nbt.addNbt("BorderSizeLerpTime", borderSizeLerpTime);
		nbt.addNbt("BorderWarningBlocks", borderWarningBlocks);
		nbt.addNbt("BorderWarningTime", borderWarningTime);
		nbt.addNbt("clearWeatherTime", clearWeatherTime);
		nbt.addNbt("DayTime", dayTime);
		nbt.addNbt("Difficulty", difficulty);
		nbt.addNbt("DifficultyLocked", difficultyLocked);
		nbt.addNbt("GameType", gameType);
		nbt.addNbt("generatorName", generatorName);
		nbt.addNbt("generatorOptions", generatorOptions);
		nbt.addNbt("generatorVersion", generatorVersion);
		nbt.addNbt("hardcore", hardcore);
		nbt.addNbt("initialized", initialized);
		nbt.addNbt("LastPlayed", lastPlayed);
		nbt.addNbt("LevelName", levelName);
		nbt.addNbt("MapFeatures", mapFeatures);
		nbt.addNbt("raining", raining);
		nbt.addNbt("rainTime", rainTime);
		nbt.addNbt("RandomSeed", randomSeed);
		nbt.addNbt("SizeOnDisk", sizeOnDisk);
		nbt.addNbt("SpawnX", spawnX);
		nbt.addNbt("SpawnY", spawnY);
		nbt.addNbt("SpawnZ", spawnZ);
		nbt.addNbt("thundering", thundering);
		nbt.addNbt("thunderTime", thunderTime);
		nbt.addNbt("Time", time);
		nbt.addNbt("version", version);
		return root;
	}
}
