package com.APOCRPG.API;

public class Effect {

	private String Name;
	private String Type;
	private int Level;
	private int Minutes;
	private int Seconds;

	public Effect(String Name, String Type, int Level, String Time) {
		this.Name = Name;
		this.Type = Type;
		this.Level = Level;
		this.Minutes = Integer.valueOf(Time.split(":")[0]);
		this.Seconds = Integer.valueOf(Time.split(":")[1]);
	}

	public String getType() {
		return Type;
	}

	public String getEffectName() {
		return Name;
	}

	public int getLevel() {
		return Level;
	}

	public int getTimeMinutes() {
		return Minutes;
	}

	public int getTimeSeconds() {
		return Seconds;
	}

	public int getTimeInTicks() {
		return (Seconds * 20) + (Minutes * (20 * 60));
	}

	public String getTime() {
		return getTimeMinutes() + ":" + getTimeSeconds();
	}

	public String toString() {
		return "Gem of " + getEffectName() + "\n" + Type + " gem\n------" + "\nLevel " + getLevel() + "\n" + getTime();
	}

}
