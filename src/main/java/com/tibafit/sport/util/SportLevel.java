package com.tibafit.sport.util;

public enum SportLevel {
	JUNIOR("junior", "初階", 0), SENIOR("senior", "中階", 1), ADVANCED("advanced", "高階", 2);

	private final String codeName;
	private final String displayName;
	private final int codeNum;

	public String getCodeName() {
		return codeName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getCodeNum() {
		return codeNum;
	}

	private SportLevel(String codeName, String displayName, int codeNum) {
		this.codeName = codeName;
		this.displayName = displayName;
		this.codeNum = codeNum;
	}

	public static String getDisplayNameByCodeName(String codeName) {
		try {
			for (SportLevel level : SportLevel.values()) {
				if (level.getCodeName() == codeName) {
					return level.getDisplayName();
				}
			}
			throw new IllegalArgumentException("codeName can't match : " + codeName);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("SportLevel getDisplayNameByCodeName Error: " + e.getMessage());
		}
	}

	public static String judgeSportLevel(Double sportMets) {
		try {
			if (sportMets > 0 && sportMets < 3) {
				return JUNIOR.getCodeName();
			} else if (sportMets >= 3 && sportMets < 6) {
				return SENIOR.getCodeName();
			} else if (sportMets >= 6) {
				return ADVANCED.getCodeName();
			} else {
				throw new IllegalArgumentException(
						"sportMets can't be judge : " + sportMets);
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("SportLevel judgeSportLevel Error: " + e.getMessage());
		}
	}
}