package com.tibafit.sport.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class SportJDBCDAO implements SportDAO_Interface {
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/tja102ggg?serverTimezone=Asia/Taipei";
	String userid = "root";
	String password = "123456";

	private static final String INSERT_STMT = "INSERT INTO sport ( sport_name, sport_description, sport_mets, sport_estimated_calories, sport_level, sport_pic, sport_data_status, admin_id ) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
//	private static final String UPDATE_STMT = "UPDATE sport SET sport_name = ?, sport_description = ?, sport_mets = ?, sport_estimated_calories = ?, sport_level = ?, sport_pic = ?, sport_data_status = ?, admin_id = ? WHERE sport_id = ?";
	private static final String UPDATE_DATA_STATUS_STMT = "UPDATE sport SET sport_data_status = ? WHERE sport_id = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM sport WHERE sport_id = ? AND sport_data_status != 2";
	private static final String GET_ALL_STMT = "SELECT * FROM sport WHERE sport_data_status != 2  ORDER BY sport_id ASC";
	private static final String GET_DATA_STATUS_STMT = "SELECT * FROM sport WHERE sport_data_status = ? AND sport_data_status != 2 ORDER BY sport_id ASC";
	private static final String GET_NAME_FUZZY_STMT = "SELECT * FROM sport WHERE sport_name LIKE ? AND sport_data_status != 2 ORDER BY sport_id ASC";

	@Override
	// 注意: 新增時，運動名稱不可重複
	public void insertSport(SportVO sportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, sportVO.getSportName());
			pstmt.setString(2, sportVO.getSportDescription());
			pstmt.setDouble(3, sportVO.getSportMets());
			pstmt.setInt(4, sportVO.getSportEstimatedCalories());
			pstmt.setString(5, sportVO.getSportLevel());
			pstmt.setString(6, sportVO.getSportPic());
			pstmt.setInt(7, sportVO.getSportDataStatus());
			pstmt.setInt(8, sportVO.getAdminId());

			int affectDataNum = pstmt.executeUpdate();
			
			System.out.println(affectDataNum);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Database Driver Error : " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("Database Error : "
					+ e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	
	@Override
	public void updateSport(SportVO sportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);

			StringBuilder sqlStr = new StringBuilder("UPDATE sport SET ");
			List<SportDynamicParam> params = new ArrayList<SportDynamicParam>();

			if (sportVO.getSportId() == null) {
				return;
			}

			if (sportVO.getSportName() != null) {
				sqlStr.append("sport_name = ?, ");
				params.add(new SportDynamicParam(sportVO.getSportName(), Types.VARCHAR));
			}
			if (sportVO.getSportDescription() != null) {
				sqlStr.append("sport_description = ?, ");
				params.add(new SportDynamicParam(sportVO.getSportDescription(), Types.VARCHAR));
			}

			if (sportVO.getSportMets() != null) {
				sqlStr.append("sport_mets = ?, ");
				params.add(new SportDynamicParam(sportVO.getSportMets(), Types.DECIMAL));
			}

			System.out.println(sportVO.getSportEstimatedCalories());
			if (sportVO.getSportEstimatedCalories() != null) {
				sqlStr.append("sport_estimated_calories = ?, ");
				params.add(new SportDynamicParam(sportVO.getSportEstimatedCalories(), Types.INTEGER));
			}
			
			if (sportVO.getSportLevel() != null) {
				sqlStr.append("sport_level = ?, ");
				params.add(new SportDynamicParam(sportVO.getSportLevel(), Types.VARCHAR));
			}
			if (sportVO.getSportPic() != null) {
				sqlStr.append("sport_pic = ?, ");
				params.add(new SportDynamicParam(sportVO.getSportPic(), Types.VARCHAR));
			}
			if (sportVO.getSportDataStatus() != null) {
				sqlStr.append("sport_data_status = ?, ");
				params.add(new SportDynamicParam(sportVO.getSportDataStatus(), Types.TINYINT));
			}
			if (sportVO.getAdminId() != null) {
				sqlStr.append("admin_id = ?, ");
				params.add(new SportDynamicParam(sportVO.getAdminId(), Types.INTEGER));
			}

			int lastCommaIndex = sqlStr.lastIndexOf(",");
			if (lastCommaIndex != -1) {
				sqlStr.setLength(lastCommaIndex);
			}

			sqlStr.append(" WHERE sport_id = ?");
			params.add(new SportDynamicParam(sportVO.getSportId(), Types.INTEGER));

			pstmt = con.prepareStatement(sqlStr.toString());

			for (int i = 0; i < params.size(); i++) {
				// i + 1 : pstmt索引從 1 開始
				// List.get(i)取索引param物件
				SportDynamicParam tempSportDynamicParam = params.get(i);
				if(tempSportDynamicParam != null && params.get(i).getInputValue() != null) {
					pstmt.setObject(i + 1, params.get(i).getInputValue(), params.get(i).getSqlType());
				}
			}

			Integer affectDataNum = pstmt.executeUpdate();
			
			System.out.println(affectDataNum);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Database Driver Error : " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("Database Error : " + e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	@Override
	public void updateDataStatus(Integer targetStatus, List<Integer> sportIds) {
	    if (sportIds == null || sportIds.isEmpty()) {
	        return;
	    }
	    
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE_DATA_STATUS_STMT);
			
			Integer sendStatus = 0;
			switch (targetStatus) {
			    case 0:
			    	sendStatus = 0;
			        break;
			    case 1:
			    	sendStatus = 1;
			        break;
			    case 2:
			    	sendStatus = 2;
			        break;
			    default:
			        System.out.println("Wrong status number");
			}
			
			for(Integer sportId: sportIds) {
				pstmt.setInt(1, sendStatus);
				pstmt.setInt(2, sportId);				
				pstmt.addBatch();
			}
			
			int[] affectDataNums = pstmt.executeBatch();
			
			StringBuilder numStr = new  StringBuilder("");
			for(int num: affectDataNums) {
				numStr.append(num + " ");
			}
			System.out.println(numStr);
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Database Driver Error : " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("Database Error : " + e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	
	@Override
	public SportVO getSportByPrimaryKey(Integer sportId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<SportVO> returnList = new ArrayList<>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, sportId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SportVO tempSport = new SportVO();
				
				tempSport.setSportId(rs.getInt("sport_id"));
				tempSport.setSportName(rs.getString("sport_name"));
				tempSport.setSportDescription(rs.getString("sport_description"));
				tempSport.setSportMets(rs.getDouble("sport_mets"));
				tempSport.setSportEstimatedCalories(rs.getInt("sport_estimated_calories"));
				tempSport.setSportLevel(rs.getString("sport_level"));
				tempSport.setSportPic(rs.getString("sport_pic"));
				tempSport.setSportDataStatus(rs.getInt("sport_data_status"));
				tempSport.setAdminId(rs.getInt("admin_id"));
				
				returnList.add(tempSport);
			}
			
			if(returnList.size() == 0) {
				throw new SportResultOneException("No data found");
			}
			if(returnList.size() > 1) {
				throw new SportResultOneException("Data number over one");
			}
			
			return returnList.get(0);
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Database Driver Error : " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("Database Error : " + e.getMessage());
		} catch (SportResultOneException e) {
			throw new RuntimeException("ResultOne Error : " + e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	@Override
	public List<SportVO> getSportAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SportVO> returnList = new ArrayList<>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SportVO tempSport = new SportVO();
				
				tempSport.setSportId(rs.getInt("sport_id"));
				tempSport.setSportName(rs.getString("sport_name"));
				tempSport.setSportDescription(rs.getString("sport_description"));
				tempSport.setSportMets(rs.getDouble("sport_mets"));
				tempSport.setSportEstimatedCalories(rs.getInt("sport_estimated_calories"));
				tempSport.setSportLevel(rs.getString("sport_level"));
				tempSport.setSportPic(rs.getString("sport_pic"));
				tempSport.setSportDataStatus(rs.getInt("sport_data_status"));
				tempSport.setAdminId(rs.getInt("admin_id"));
				
				returnList.add(tempSport);
			}
			
			return returnList;
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Database Driver Error : " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("Database Error : " + e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	@Override
	public List<SportVO> getSportByDataStatus(Integer sportDataStatus) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SportVO> returnList = new ArrayList<>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_DATA_STATUS_STMT);
			
			pstmt.setInt(1, sportDataStatus);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SportVO tempSport = new SportVO();
				
				tempSport.setSportId(rs.getInt("sport_id"));
				tempSport.setSportName(rs.getString("sport_name"));
				tempSport.setSportDescription(rs.getString("sport_description"));
				tempSport.setSportMets(rs.getDouble("sport_mets"));
				tempSport.setSportEstimatedCalories(rs.getInt("sport_estimated_calories"));
				tempSport.setSportLevel(rs.getString("sport_level"));
				tempSport.setSportPic(rs.getString("sport_pic"));
				tempSport.setSportDataStatus(rs.getInt("sport_data_status"));
				tempSport.setAdminId(rs.getInt("admin_id"));
				
				returnList.add(tempSport);				
			}
			
			return returnList;
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Database Driver Error : " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("Database Error : " + e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public List<SportVO> getSportByNameFuzzy(String keyword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SportVO> returnList = new ArrayList<>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_NAME_FUZZY_STMT);
			
			pstmt.setString(1, "%" + keyword + "%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SportVO tempSport = new SportVO();
				
				tempSport.setSportId(rs.getInt("sport_id"));
				tempSport.setSportName(rs.getString("sport_name"));
				tempSport.setSportDescription(rs.getString("sport_description"));
				tempSport.setSportMets(rs.getDouble("sport_mets"));
				tempSport.setSportEstimatedCalories(rs.getInt("sport_estimated_calories"));
				tempSport.setSportLevel(rs.getString("sport_level"));
				tempSport.setSportPic(rs.getString("sport_pic"));
				tempSport.setSportDataStatus(rs.getInt("sport_data_status"));
				tempSport.setAdminId(rs.getInt("admin_id"));
				
				returnList.add(tempSport);				
			}
			
			return returnList;
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Database Driver Error : " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("Database Error : " + e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	

}
