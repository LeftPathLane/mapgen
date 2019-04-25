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
	public long borderSizeLerpTime;
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

	public LevelData() {}

	public LevelData(NbtCompound nbt) {
		NbtCompound data = nbt.getValue().get("Data").asCompound();

		allowCommands = data.getValue().get("allowCommands").asByte().getValue();
		borderCenterX = data.getValue().get("BorderCenterX").asDouble().getValue();
		borderCenterZ = data.getValue().get("BorderCenterZ").asDouble().getValue();
		borderDamagePerBlock = data.getValue().get("BorderDamagePerBlock").asDouble().getValue();
		borderSafeZone = data.getValue().get("BorderSafeZone").asDouble().getValue();
		borderSize = data.getValue().get("BorderSize").asDouble().getValue();
		borderSizeLerpTarget = data.getValue().get("BorderSizeLerpTarget").asDouble().getValue();
		borderSizeLerpTime = data.getValue().get("BorderSizeLerpTime").asLong().getValue();
		borderWarningBlocks = data.getValue().get("BorderWarningBlocks").asDouble().getValue();
		borderWarningTime = data.getValue().get("BorderWarningTime").asDouble().getValue();
		clearWeatherTime = data.getValue().get("clearWeatherTime").asInt().getValue();
		dayTime = data.getValue().get("DayTime").asLong().getValue();
		difficulty = data.getValue().get("Difficulty").asByte().getValue();
		difficultyLocked = data.getValue().get("DifficultyLocked").asByte().getValue();
		gameType = data.getValue().get("GameType").asInt().getValue();
		generatorName = data.getValue().get("generatorName").asString().getValue();
		generatorOptions = data.getValue().get("generatorOptions").asString().getValue();
		generatorVersion = data.getValue().get("generatorVersion").asInt().getValue();
		hardcore = data.getValue().get("hardcore").asByte().getValue();
		initialized = data.getValue().get("initialized").asByte().getValue();
		lastPlayed = data.getValue().get("LastPlayed").asLong().getValue();
		levelName = data.getValue().get("LevelName").asString().getValue();
		mapFeatures = data.getValue().get("MapFeatures").asByte().getValue();
		raining = data.getValue().get("raining").asByte().getValue();
		rainTime = data.getValue().get("rainTime").asInt().getValue();
		randomSeed = data.getValue().get("RandomSeed").asLong().getValue();
		sizeOnDisk = data.getValue().get("SizeOnDisk").asLong().getValue();
		spawnX = data.getValue().get("SpawnX").asInt().getValue();
		spawnY = data.getValue().get("SpawnY").asInt().getValue();
		spawnZ = data.getValue().get("SpawnZ").asInt().getValue();
		thundering = data.getValue().get("thundering").asByte().getValue();
		thunderTime = data.getValue().get("thunderTime").asInt().getValue();
		time = data.getValue().get("Time").asLong().getValue();
		version = data.getValue().get("version").asInt().getValue();

		NbtCompound gameRules = data.getValue().get("GameRules").asCompound();

		commandBlockOutput = gameRules.getValue().get("commandBlockOutput").asString().getValue();
		doDaylightCycle = gameRules.getValue().get("doDaylightCycle").asString().getValue();
		doEntityDrops = gameRules.getValue().get("doEntityDrops").asString().getValue();
		doFireTick = gameRules.getValue().get("doFireTick").asString().getValue();
		doMobLoot = gameRules.getValue().get("doMobLoot").asString().getValue();
		doMobSpawning = gameRules.getValue().get("doMobSpawning").asString().getValue();
		dotileDrop = gameRules.getValue().get("doTileDrops").asString().getValue();
		keepInventory = gameRules.getValue().get("keepInventory").asString().getValue();
		logAdminCommands = gameRules.getValue().get("logAdminCommands").asString().getValue();
		mobGriefing = gameRules.getValue().get("mobGriefing").asString().getValue();
		naturalRegenration = gameRules.getValue().get("naturalRegeneration").asString().getValue();
		randomtickSpeed = gameRules.getValue().get("randomTickSpeed").asString().getValue();
		reducedDebugInfo = gameRules.getValue().get("reducedDebugInfo").asString().getValue();
		sendCommandFeedback = gameRules.getValue().get("sendCommandFeedback").asString().getValue();
		showDeathMessages = gameRules.getValue().get("showDeathMessages").asString().getValue();
	}

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
		gameRules.addNbt("doMobLoot", doMobLoot);
		gameRules.addNbt("doMobSpawning", doMobSpawning);
		gameRules.addNbt("doTileDrops", dotileDrop);
		gameRules.addNbt("keepInventory", keepInventory);
		gameRules.addNbt("logAdminCommands", logAdminCommands);
		gameRules.addNbt("mobGriefing", mobGriefing);
		gameRules.addNbt("naturalRegeneration", naturalRegenration);
		gameRules.addNbt("randomTickSpeed", randomtickSpeed);
		gameRules.addNbt("reducedDebugInfo", reducedDebugInfo);
		gameRules.addNbt("sendCommandFeedback", sendCommandFeedback);
		gameRules.addNbt("showDeathMessages", showDeathMessages);

		nbt.addNbt("allowCommands", allowCommands);
		nbt.addNbt("BorderCenterX", borderCenterX);
		nbt.addNbt("BorderCenterZ", borderCenterZ);
		nbt.addNbt("BorderDamagePerBlock", borderDamagePerBlock);
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
