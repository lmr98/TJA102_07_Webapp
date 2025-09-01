package com.tibafit.sport.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tibafit.sport.model.SportService;
import com.tibafit.sport.model.SportVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SportServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");


		if ("getOne_For_Display".equals(action)) {

			// 接請求參數
			String inputStr = req.getParameter("sportId");

			// 錯誤訊息宣告
			List<String> errorMsgs = new LinkedList<String>();
			// 放入變數 (ref特性)
			req.setAttribute("idErrorMsgs", errorMsgs);

			// 請求參數值驗證 ===============================================
			if (inputStr == null || inputStr.trim().length() == 0) {
				errorMsgs.add("請輸入運動編號");
			}
			if (!errorMsgs.isEmpty()) {
				// 協forward
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/sport/sportMainPage.jsp");
				failureView.forward(req, res);
				// 程式中斷
				return;
			}

			Integer tempNum = null;
			try {
				tempNum = Integer.valueOf(inputStr);
			} catch (Exception e) {
				errorMsgs.add("運動編號格式不正確");

			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/sport/sportMainPage.jsp");
				failureView.forward(req, res);
				return;
			}

			// 查詢資料 ===============================================
			SportService spotSvc = new SportService();
			SportVO sportVO = spotSvc.getSportByPrimaryKey(tempNum);
			// 資料驗證
			if (sportVO == null) {
				errorMsgs.add("查無資料");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/sport/sportMainPage.jsp");
				failureView.forward(req, res);
				return;
			}

			// 查詢完成，進行轉送 ===============================================
			req.setAttribute("sportVO", sportVO);
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/sport/sportListOne.jsp");
			successView.forward(req, res);

		}


		if ("getPartial_For_Display".equals(action)) {

			String inputStr = req.getParameter("sportDataStatus");
			List<String> errorMsgs = new LinkedList<>();

			req.setAttribute("statusErrorMsgs", errorMsgs);

			Integer tempStatus = 0;

			if (inputStr == null || inputStr.isEmpty()) {
				errorMsgs.add("下拉選單值為空");
			} else {
				try {
					tempStatus = Integer.valueOf(inputStr);
				} catch (Exception e) {
					errorMsgs.add("下拉選單值格式有誤");
				}
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/sport/sportMainPage.jsp");
				failureView.forward(req, res);
				return;
			}

			SportService SportSvc = new SportService();
			List<SportVO> tempList = SportSvc.getSportByDataStatus(tempStatus);

			if (tempList.isEmpty()) {
				errorMsgs.add("查無資料");

				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/sport/sportMainPage.jsp");
				failureView.forward(req, res);
				return;
			}
			req.setAttribute("partialList", tempList);
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/sport/sportListPartial.jsp");
			successView.forward(req, res);

		}


		if ("getNameFuzzy_For_Display".equals(action)) {

			// 接請求參數
			String inputStr = req.getParameter("keyword");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("nameErrorMsgs", errorMsgs);

			if (inputStr == null || inputStr.trim().length() == 0) {
				errorMsgs.add("運動名稱關鍵字為空");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/sport/sportMainPage.jsp");
				failureView.forward(req, res);
				return;
			}

			SportService SportSvc = new SportService();
			List<SportVO> tempList = SportSvc.getSportByNameFuzzy(inputStr);

			if (tempList.isEmpty()) {
				errorMsgs.add("查無資料");

				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/sport/sportMainPage.jsp");
				failureView.forward(req, res);
				return;
			}
			req.setAttribute("partialList", tempList);
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/sport/sportListPartial.jsp");
			successView.forward(req, res);

		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			// 接請求參數 & 請求參數值驗證 =============================
			String name = req.getParameter("sportName");
			if (name == null || name.trim().length() == 0) {
				errorMsgs.add("運動名稱: 請勿空白");
			} else if (name.length() > 50) {
				errorMsgs.add("運動名稱: 最長50字");
			}

			String des = req.getParameter("sportDescription");
			String tempDes = des == null || des.trim().length() == 0 ? "" : des.trim();
			if (tempDes.length() > 255) {
				errorMsgs.add("運動描述: 最長255字");
			}

			String metStr = req.getParameter("sportMets").trim();
			req.setAttribute("rawSportMets", metStr);
			Double met = null;

			if (metStr == null || metStr.trim().length() == 0) {
				errorMsgs.add("運動強度: 請勿空白");
			} else {
				try {

					met = Double.valueOf(metStr);

					// 正數，最多小數點後兩位
					if (!metStr.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
						throw new NumberFormatException();
					}

					// 再確認是正數 (大於 0)
					if (met <= 0) {
						throw new NumberFormatException();
					}

				} catch (NumberFormatException e) {
					errorMsgs.add("運動強度: 請填正數，最多至小數點後兩位");
				}
			}

			String calorieStr = req.getParameter("sportEstimatedCalories").trim();
			req.setAttribute("rawSportCalorie", calorieStr);
			Integer calorie = null;

			if (calorieStr == null || calorieStr.trim().length() == 0) {
				errorMsgs.add("運動估算消耗熱量: 請勿空白");
			} else {
				try {

					calorie = Integer.valueOf(calorieStr);

					// 檢查：正整數 (不允許小數或負數)
					if (!calorieStr.matches("^[0-9]+$")) {
						throw new NumberFormatException();
					}

					// 再確認是正數 (大於 0)
					if (calorie <= 0) {
						throw new NumberFormatException();
					}

				} catch (NumberFormatException e) {
					errorMsgs.add("運動估算消耗熱量: 請填正整數");
				}
			}

			String tempName = name.trim();
			SportVO sportVO = new SportVO();
			sportVO.setSportName(tempName);
			sportVO.setSportDescription(tempDes);
			sportVO.setSportMets(met);
			sportVO.setSportEstimatedCalories(calorie);

			if (!errorMsgs.isEmpty()) {
				// 驗證錯誤的輸入值，也存入req sportVO物件
				req.setAttribute("sportVO", sportVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/sport/sportAdd.jsp");
				failureView.forward(req, res);
				return;
			}

			// 開始新增資料 =============================
			SportService sportSvc = new SportService();
			sportSvc.insertSport(tempName, tempDes, met, calorie, null, 1);

			// 新增成功後，轉送sportListAll.jsp
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/sport/sportListAll.jsp");
			successView.forward(req, res);
		}

		
		if ("getOne_For_Update".equals(action)) {
			// 接請求參數
			String inputStr = req.getParameter("sportId");

			// 請求參數值驗證 ===============================================
			if (inputStr == null || inputStr.trim().length() == 0) {
				System.out.println("請求參數sportId為空，Error: " + inputStr);
				return;
			}

			Integer tempNum = null;
			try {
				tempNum = Integer.valueOf(inputStr);
			} catch (Exception e) {
				System.out.println("請求參數sportId格式不正確，Error: " + tempNum);
				return;
			}

			// 查詢資料 ===============================================
			SportService spotSvc = new SportService();
			SportVO sportVO = spotSvc.getSportByPrimaryKey(tempNum);
			// 資料驗證
			if (sportVO == null) {
				System.out.println("查無資料，Error");
			}

			// 查詢完成，進行轉送 ===============================================
			req.setAttribute("sportVO", sportVO);
			req.setAttribute("rawSportMets", sportVO.getSportMets().toString());
			req.setAttribute("rawSportCalorie", sportVO.getSportEstimatedCalories().toString());
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/sport/sportUpdate.jsp");
			successView.forward(req, res);
		}

		
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			// 接請求參數 & 請求參數值驗證 =============================
			String idStr = req.getParameter("sportId");
			if (idStr == null || idStr.trim().length() == 0) {
				System.out.println("請求參數sportId為空，Error: " + idStr);
				return;
			}
			Integer tempId = null;
			try {
				tempId = Integer.valueOf(idStr);
			} catch (Exception e) {
				System.out.println("請求參數sportId格式不正確，Error: " + tempId);
				return;
			}
			
			String name = req.getParameter("sportName");
			if (name == null || name.trim().length() == 0) {
				errorMsgs.add("運動名稱: 請勿空白");
			} else if (name.length() > 50) {
				errorMsgs.add("運動名稱: 最長50字");
			}

			String des = req.getParameter("sportDescription");
			String tempDes = des == null || des.trim().length() == 0 ? "" : des.trim();
			if (tempDes.length() > 255) {
				errorMsgs.add("運動描述: 最長255字");
			}

			String metStr = req.getParameter("sportMets").trim();
			req.setAttribute("rawSportMets", metStr);
			Double met = null;

			if (metStr == null || metStr.trim().length() == 0) {
				errorMsgs.add("運動強度: 請勿空白");
			} else {
				try {

					met = Double.valueOf(metStr);

					// 正數，最多小數點後兩位
					if (!metStr.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
						throw new NumberFormatException();
					}

					// 再確認是正數 (大於 0)
					if (met <= 0) {
						throw new NumberFormatException();
					}

				} catch (NumberFormatException e) {
					errorMsgs.add("運動強度: 請填正數，最多至小數點後兩位");
				}
			}

			String calorieStr = req.getParameter("sportEstimatedCalories").trim();
			req.setAttribute("rawSportCalorie", calorieStr);
			Integer calorie = null;

			if (calorieStr == null || calorieStr.trim().length() == 0) {
				errorMsgs.add("運動估算消耗熱量: 請勿空白");
			} else {
				try {

					calorie = Integer.valueOf(calorieStr);

					// 檢查：正整數 (不允許小數或負數)
					if (!calorieStr.matches("^[0-9]+$")) {
						throw new NumberFormatException();
					}

					// 再確認是正數 (大於 0)
					if (calorie <= 0) {
						throw new NumberFormatException();
					}

				} catch (NumberFormatException e) {
					errorMsgs.add("運動估算消耗熱量: 請填正整數");
				}
			}
			
			String inputStr = req.getParameter("sportDataStatus");
			Integer tempStatus = 0;
			if (inputStr == null || inputStr.isEmpty()) {
				errorMsgs.add("下拉選單值為空");
			} else {
				try {
					tempStatus = Integer.valueOf(inputStr);
				} catch (Exception e) {
					errorMsgs.add("下拉選單值格式有誤");
				}
			}

			String tempName = name.trim();
			SportVO sportVO = new SportVO();
			sportVO.setSportId(tempId);
			sportVO.setSportName(tempName);
			sportVO.setSportDescription(tempDes);
			sportVO.setSportMets(met);
			sportVO.setSportEstimatedCalories(calorie);
			sportVO.setSportDataStatus(tempStatus);

			if (!errorMsgs.isEmpty()) {
				// 驗證錯誤的輸入值，也存入req sportVO物件
				req.setAttribute("sportVO", sportVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/sport/sportUpdate.jsp");
				failureView.forward(req, res);
				return;
			}

			// 開始更新資料 =============================
			SportService sportSvc = new SportService();
			sportSvc.updateSport(tempId, tempName, tempDes, met, calorie, null, tempStatus, null);

			// 新增成功後，轉送sportListAll.jsp
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/sport/sportListAll.jsp");
			successView.forward(req, res);
		}

		if ("change_status_online".equals(action) || "change_status_offline".equals(action) || "change_status_delete".equals(action)) {
			String tempAction = action;
			Integer tempStatus = 0;
			switch (tempAction) {
			    case "change_status_online":
			    	tempStatus = 1;
			        break;
			    case "change_status_offline":
			    	tempStatus = 0;
			        break;
			    case "change_status_delete":
			    	tempStatus = 2;
			        break;
			    default:
			        System.out.println("Wrong status number");
			}
			
			String idStr = req.getParameter("sportId");
			if (idStr == null || idStr.trim().length() == 0) {
				System.out.println("請求參數sportId為空，Error: " + idStr);
				return;
			}
			Integer tempId = null;
			try {
				tempId = Integer.valueOf(idStr);
			} catch (Exception e) {
				System.out.println("請求參數sportId格式不正確，Error: " + tempId);
				return;
			}
			
			// 開始更新資料 =============================
			SportService sportSvc = new SportService();
			List<Integer> tempIdList = new ArrayList();
			tempIdList.add(tempId);			
			sportSvc.updateDataStatus(tempStatus, tempIdList);

			// 更新成功後，轉送sportListAll.jsp
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/sport/sportListAll.jsp");
			successView.forward(req, res);
			
		}

//		if("".equals(action)) {
//		
//	}

	}
}
