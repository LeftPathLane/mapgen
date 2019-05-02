package com.github.leftpathlane.mapgen;

import com.github.leftpathlane.jnbt.types.*;

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

		allowCommands = data.getNbt("allowCommands").asByte().getValue();
		borderCenterX = data.getNbt("BorderCenterX").asDouble().getValue();
		borderCenterZ = data.getNbt("BorderCenterZ").asDouble().getValue();
		borderDamagePerBlock = data.getNbt("BorderDamagePerBlock").asDouble().getValue();
		borderSafeZone = data.getNbt("BorderSafeZone").asDouble().getValue();
		borderSize = data.getNbt("BorderSize").asDouble().getValue();
		borderSizeLerpTarget = data.getNbt("BorderSizeLerpTarget").asDouble().getValue();
		borderSizeLerpTime = data.getNbt("BorderSizeLerpTime").asLong().getValue();
		borderWarningBlocks = data.getNbt("BorderWarningBlocks").asDouble().getValue();
		borderWarningTime = data.getNbt("BorderWarningTime").asDouble().getValue();
		clearWeatherTime = data.getNbt("clearWeatherTime").asInt().getValue();
		dayTime = data.getNbt("DayTime").asLong().getValue();
		difficulty = data.getNbt("Difficulty").asByte().getValue();
		difficultyLocked = data.getNbt("DifficultyLocked").asByte().getValue();
		gameType = data.getNbt("GameType").asInt().getValue();
		generatorName = data.getNbt("generatorName").asString().getValue();
		generatorOptions = data.getNbt("generatorOptions").asString().getValue();
		generatorVersion = data.getNbt("generatorVersion").asInt().getValue();
		hardcore = data.getNbt("hardcore").asByte().getValue();
		initialized = data.getNbt("initialized").asByte().getValue();
		lastPlayed = data.getNbt("LastPlayed").asLong().getValue();
		levelName = data.getNbt("LevelName").asString().getValue();
		mapFeatures = data.getNbt("MapFeatures").asByte().getValue();
		raining = data.getNbt("raining").asByte().getValue();
		rainTime = data.getNbt("rainTime").asInt().getValue();
		randomSeed = data.getNbt("RandomSeed").asLong().getValue();
		sizeOnDisk = data.getNbt("SizeOnDisk").asLong().getValue();
		spawnX = data.getNbt("SpawnX").asInt().getValue();
		spawnY = data.getNbt("SpawnY").asInt().getValue();
		spawnZ = data.getNbt("SpawnZ").asInt().getValue();
		thundering = data.getNbt("thundering").asByte().getValue();
		thunderTime = data.getNbt("thunderTime").asInt().getValue();
		time = data.getNbt("Time").asLong().getValue();
		version = data.getNbt("version").asInt().getValue();

		NbtCompound gameRules = data.getNbt("GameRules").asCompound();

		commandBlockOutput = gameRules.getNbt("commandBlockOutput").asString().getValue();
		doDaylightCycle = gameRules.getNbt("doDaylightCycle").asString().getValue();
		doEntityDrops = gameRules.getNbt("doEntityDrops").asString().getValue();
		doFireTick = gameRules.getNbt("doFireTick").asString().getValue();
		doMobLoot = gameRules.getNbt("doMobLoot").asString().getValue();
		doMobSpawning = gameRules.getNbt("doMobSpawning").asString().getValue();
		dotileDrop = gameRules.getNbt("doTileDrops").asString().getValue();
		keepInventory = gameRules.getNbt("keepInventory").asString().getValue();
		logAdminCommands = gameRules.getNbt("logAdminCommands").asString().getValue();
		mobGriefing = gameRules.getNbt("mobGriefing").asString().getValue();
		naturalRegenration = gameRules.getNbt("naturalRegeneration").asString().getValue();
		randomtickSpeed = gameRules.getNbt("randomTickSpeed").asString().getValue();
		reducedDebugInfo = gameRules.getNbt("reducedDebugInfo").asString().getValue();
		sendCommandFeedback = gameRules.getNbt("sendCommandFeedback").asString().getValue();
		showDeathMessages = gameRules.getNbt("showDeathMessages").asString().getValue();
	}

	public NbtCompound toNbt() {
		NbtCompound root = new NbtCompound("");
		NbtCompound nbt = new NbtCompound("Data");
		root.addNbt(nbt);
		NbtCompound gameRules = new NbtCompound("GameRules");
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
