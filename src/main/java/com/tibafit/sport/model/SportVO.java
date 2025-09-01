package com.tibafit.sport.model;

import java.io.Serializable;

import com.tibafit.sport.util.SportLevel;

public class SportVO implements Serializable {
	private Integer sportId;
	private String sportName;
	private String sportDescription;
	private Double sportMets;
	private Integer sportEstimatedCalories;
	private String sportLevel;
	private String sportPic;
	private Integer sportDataStatus;
	private Integer adminId;
	
	
	public SportVO() {
		super();
	}


	public SportVO(Integer sportId, String sportName, String sportDescription, Double sportMets, Integer sportEstimatedCalories,
			String sportLevel, String sportPic, Integer sportDataStatus, Integer adminId) {
		super();
		this.sportId = sportId;
		this.sportName = sportName;
		this.sportDescription = sportDescription;
		this.sportMets = sportMets;
		this.sportEstimatedCalories = sportEstimatedCalories;
		this.sportLevel = sportLevel;
		this.sportPic = sportPic;
		this.sportDataStatus = sportDataStatus;
		this.adminId = adminId;
		
		this.setSportMets(sportMets);
	}


	public Integer getSportId() {
		return sportId;
	}


	public void setSportId(Integer sportId) {
		this.sportId = sportId;
	}

	
	public String getSportName() {
		return sportName;
	}

	
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}


	public String getSportDescription() {
		return sportDescription;
	}


	public void setSportDescription(String sportDescription) {
		this.sportDescription = sportDescription;
	}


	public Double getSportMets() {
		return sportMets;
	}


	public void setSportMets(Double sportMets) {
		this.sportMets = sportMets;
	}


	public Integer getSportEstimatedCalories() {
		return sportEstimatedCalories;
	}


	public void setSportEstimatedCalories(Integer sportEstimatedCalories) {
		this.sportEstimatedCalories = sportEstimatedCalories;
	}


	public String getSportLevel() {
		return sportLevel;
	}

	public void setSportLevel(String sportLevel) {
		this.sportLevel = sportLevel;
	}
	

	public String getSportPic() {
		return sportPic;
	}


	public void setSportPic(String sportPic) {
		this.sportPic = sportPic;
	}


	public Integer getSportDataStatus() {
		return sportDataStatus;
	}


	public void setSportDataStatus(Integer sportDataStatus) {
		this.sportDataStatus = sportDataStatus;
	}


	public Integer getAdminId() {
		return adminId;
	}


	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	@Override
	public String toString() {
	    return "SportVO {" +
	            "sportId=" + sportId +
	            ", sportName='" + sportName + '\'' +
	            ", sportDescription='" + sportDescription + '\'' +
	            ", sportMets=" + sportMets +
	            ", sportEstimatedCalories=" + sportEstimatedCalories +
	            ", sportLevel='" + sportLevel + '\'' +
	            ", sportPic='" + sportPic + '\'' +
	            ", sportDataStatus=" + sportDataStatus +
	            ", adminId=" + adminId +
	            '}';
	}
	
	
}
