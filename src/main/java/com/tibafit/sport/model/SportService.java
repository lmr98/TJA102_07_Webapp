package com.tibafit.sport.model;

import java.util.ArrayList;
import java.util.List;

import com.tibafit.sport.util.SportDataStatus;
import com.tibafit.sport.util.SportLevel;


public class SportService implements SportService_Interface {

	private SportDAO_Interface dao;

	public SportService() {
		super();
		dao = new SportDAO();
	}

	@Override
	public void insertSport(String sportName, String sportDescription, Double sportMets, Integer sportEstimatedCalories, String sportPic, Integer adminId) {
		
			SportVO sportVO = new SportVO();
			String tempDes = sportDescription == null? "" : sportDescription;
			
			sportVO.setSportName(sportName);
			sportVO.setSportDescription(tempDes);
			sportVO.setSportMets(sportMets);
			sportVO.setSportEstimatedCalories(sportEstimatedCalories);
			sportVO.setSportLevel(SportLevel.judgeSportLevel(sportMets));
			sportVO.setSportPic(sportPic);
			sportVO.setSportDataStatus(1);
			sportVO.setAdminId(adminId);
			
			dao.insertSport(sportVO);
	}

	@Override
	public void updateSport(Integer sportId, String sportName, String sportDescription, Double sportMets, Integer sportEstimatedCalories, String sportPic, Integer sportDataStatus, Integer adminId) {
		
		SportVO sportVO = new SportVO();
		
		sportVO.setSportId(sportId);
		sportVO.setSportName(sportName);
		sportVO.setSportDescription(sportDescription);
		sportVO.setSportMets(sportMets);
		sportVO.setSportEstimatedCalories(sportEstimatedCalories);
		if(sportMets == null || sportMets > 0) {
			sportVO.setSportLevel(null);
		} else {
			sportVO.setSportLevel(SportLevel.judgeSportLevel(sportMets));
		}
		sportVO.setSportPic(sportPic);
		sportVO.setSportDataStatus(sportDataStatus);
		sportVO.setAdminId(adminId);
		
		dao.updateSport(sportVO);
	}

	@Override
	public void updateDataStatus(Integer targetStatus, List<Integer> sportIds) {
		dao.updateDataStatus(targetStatus, sportIds);
	}

	@Override
	public SportVO getSportByPrimaryKey(Integer sportId) {
		return dao.getSportByPrimaryKey(sportId);
	}

	@Override
	public List<SportVO> getSportAll() {
		return dao.getSportAll();
	}

	@Override
	public List<SportVO> getSportByDataStatus(Integer sportDataStatus) {
		return dao.getSportByDataStatus(sportDataStatus);
	}
	
	@Override
	public List<SportVO> getSportByNameFuzzy(String keyword) {
		return dao.getSportByNameFuzzy(keyword);
	}

	@Override
	public List<SportDataStatus> getSportDataStatusOptions() {
		List<SportDataStatus> tempList = new ArrayList<>();
		
		for (SportDataStatus level : SportDataStatus.values()) {

			if(level.getCodeNum() == 0 || level.getCodeNum() == 1) {
				tempList.add(level);
			}
		}
		return tempList;
	}

	@Override
	public List<SportDataStatus> getSportDataStatusNeed(List<Integer> needStatusCodeNums) {
		List<SportDataStatus> tempList = new ArrayList<>();
		
		for (SportDataStatus level : SportDataStatus.values()) {
			int index = needStatusCodeNums.indexOf(level.getCodeNum());
			if(index != -1) {
				tempList.add(level);
			}
		}
		return tempList;
	}
}
