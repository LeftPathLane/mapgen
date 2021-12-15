package com.github.leftpathlane.mapgen;

import com.github.leftpathlane.jnbt.types.*;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LevelData {
	private byte allowCommands = 1;
	private double borderCenterX;
	private double borderCenterZ;
	private double borderDamagePerBlock = 0.2;
	private double borderSafeZone = 5;
	private double borderSize = 60000000;
	private double borderSizeLerpTarget = 60000000;
	private long borderSizeLerpTime;
	private double borderWarningBlocks = 5;
	private double borderWarningTime = 15;
	private int clearWeatherTime;
	private long dayTime;
	private byte difficulty = 2;
	private byte difficultyLocked;
	private int gameType = 1;
	private String generatorName = "flat";
	private String generatorOptions = "3;minecraft:air;1;";
	private int generatorVersion;
	private byte hardcore;
	private byte initialized = 1;
	private long lastPlayed;
	private String levelName = "flat world";
	private byte mapFeatures;
	private byte raining;
	private int rainTime = Integer.MAX_VALUE;
	private long randomSeed = 1;
	private long sizeOnDisk;
	private int spawnX = 0;
	private int spawnY = 1;
	private int spawnZ = 1;
	private byte thundering;
	private int thunderTime = Integer.MAX_VALUE;
	private long time;
	private int version = 19133;

	private String commandBlockOutput = "true";
	private String doDaylightCycle = "true";
	private String doEntityDrops = "true";
	private String doFireTick = "true";
	private String doMobLoot = "true";
	private String doMobSpawning = "true";
	private String dotileDrop = "true";
	private String keepInventory = "false";
	private String logAdminCommands = "true";
	private String mobGriefing = "true";
	private String naturalRegenration = "true";
	private String randomtickSpeed = "3";
	private String reducedDebugInfo = "false";
	private String sendCommandFeedback = "true";
	private String showDeathMessages = "true";

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
