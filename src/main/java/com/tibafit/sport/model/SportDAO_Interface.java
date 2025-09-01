package com.tibafit.sport.model;

import java.util.*;

public interface SportDAO_Interface {
	public void insertSport(SportVO sportVO);
	public void updateSport(SportVO sportVO);
	public void updateDataStatus(Integer targetStatus, List<Integer> sportId);
	
	public SportVO getSportByPrimaryKey(Integer sportId);
	public List<SportVO> getSportAll();
	public List<SportVO> getSportByDataStatus(Integer sportDataStatus);
	public List<SportVO> getSportByNameFuzzy(String keyword);
}
