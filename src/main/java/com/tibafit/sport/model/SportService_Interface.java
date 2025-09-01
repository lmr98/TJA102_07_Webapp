package com.tibafit.sport.model;

import java.util.List;

import com.tibafit.sport.util.SportDataStatus;

public interface SportService_Interface {
	
	public void insertSport(String sportName, String sportDescription, Double sportMets, Integer sportEstimatedCalories, String sportPic, Integer adminId);
	
	public void updateSport(Integer sportId, String sportName, String sportDescription, Double sportMets, Integer sportEstimatedCalories, String sportPic, Integer sportDataStatus, Integer adminId);
	
	public void updateDataStatus(Integer targetStatus, List<Integer> sportIds);
	
	public SportVO getSportByPrimaryKey(Integer sportId);
	
	public List<SportVO> getSportAll();
	
	public List<SportVO> getSportByDataStatus(Integer sportDataStatus);
	
	public List<SportVO> getSportByNameFuzzy(String keyword);
	
	public List<SportDataStatus> getSportDataStatusOptions();
	
	public List<SportDataStatus> getSportDataStatusNeed(List<Integer> needStatusCodeNums);
}
