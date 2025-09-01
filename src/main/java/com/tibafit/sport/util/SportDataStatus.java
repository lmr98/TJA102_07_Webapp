package com.tibafit.sport.util;

public enum SportDataStatus {
	ONLINE("online", "上架", 1), OFFLINE("offline", "下架", 0), DELETE("delete", "刪除", 2);

	private final String codeName;
	private final String displayName;
	private final Integer codeNum;

	public String getCodeName() {
		return codeName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getCodeNum() {
		return codeNum;
	}

	private SportDataStatus(String codeName, String displayName, Integer codeNum) {
		this.codeName = codeName;
		this.displayName = displayName;
		this.codeNum = codeNum;
	}
	
	public static String getDisplayNameByCodeNum(Integer codeNum) {
		try {
			for (SportDataStatus level : SportDataStatus.values()) {
				if (level.getCodeNum() == codeNum) {
					return level.getDisplayName();
				}
			}
			throw new IllegalArgumentException("codeNum can't match : " + codeNum);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("SportDataStatus getDisplayNameByCodeName Error: " + e.getMessage());
		}
	}

	public static String getDisplayNameByCodeName(String codeName) {
		try {
			for (SportDataStatus level : SportDataStatus.values()) {
				if (level.getCodeName() == codeName) {
					return level.getDisplayName();
				}
			}
			throw new IllegalArgumentException("codeName can't match : " + codeName);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("SportDataStatus getDisplayNameByCodeName Error: " + e.getMessage());
		}
	}
}
