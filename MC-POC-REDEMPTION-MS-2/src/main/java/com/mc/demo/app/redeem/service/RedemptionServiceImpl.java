/**
 * 
 */
package com.mc.demo.app.redeem.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mc.demo.app.redeem.Points;
import com.mc.demo.app.redeem.RedemptionTransaction;
import com.mc.demo.app.redeem.exception.ApplicationException;
import com.mc.demo.app.redeem.model.RedemptionRepository;
import com.mc.demo.app.redeem.model.Redemptiontransaction;

/**
 * @author Wipro
 *
 */
@Service("redeemService")
public class RedemptionServiceImpl implements RedemptionService {

	@Autowired
	RedemptionRepository redeemRepo;
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mc.demo.app.redeem.service.RedemptionService#save(com.mc.demo.app.
	 * redeem.RedemptionTransaction)
	 */
	@Override
	public boolean save(RedemptionTransaction transac) throws Exception {

		try {
			
			Points point =new Points();
			point.setCardNumber(transac.getCardNumber());
			point.setOpertaion("minus");
			point.setPointsvalue(transac.getRedeemedpoints());
			
			String url = "https://customer.apps.dev.pcf-aws.com/api/v1/creditcard/customer/adjustpoints";
			//String url = "https://localhost:8081/api/v1/creditcard/customer/adjustpoints";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.postForEntity(url,point,
	                String.class);
			if("unsuccessful".equalsIgnoreCase(response.getBody().toString())){
				return false;
			}
			Redemptiontransaction redeemObj = new Redemptiontransaction();
			redeemObj.setCustid(transac.getCustid());
			redeemObj.setAccountnumber(transac.getCardNumber());
			Random r = new Random();
			int inR = r.nextInt((99999999 - 2000) + 1) + 2000000;
			redeemObj.setRedeemid(String.valueOf(inR));
			redeemObj.setRedeemeditem(transac.getRedeemeditem());
			redeemObj.setQuantity(transac.getQuantity());
			redeemObj.setRedeemedpoints(transac.getRedeemedpoints());
			redeemObj.setRedemptointimestaamp(java.sql.Timestamp.valueOf(LocalDateTime.now()));
			redeemObj.setVendorid(transac.getVendorid());
			redeemObj.setCreated_at(java.sql.Timestamp.valueOf(LocalDateTime.now()));
			redeemObj.setUpdated_at(java.sql.Timestamp.valueOf(LocalDateTime.now()));
			redeemRepo.save(redeemObj);
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage());
		}
		
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mc.demo.app.redeem.service.RedemptionService#getHistoryByCard(java.
	 * lang.String)
	 */
	@Override
	public List<RedemptionTransaction> getHistoryByCard(String cardnumber) {

		List<RedemptionTransaction> RedeemhistoryList = new ArrayList<RedemptionTransaction>();
		List<Redemptiontransaction> historyList = redeemRepo.findRedeemHistoryByAccountnumber(cardnumber);
		if (historyList == null || historyList.isEmpty()) {
			return null;
		}
		historyList.forEach(transaction -> {
			System.out.println("      " + transaction);
			RedemptionTransaction redeemHistory = new RedemptionTransaction();
			redeemHistory.setCardNumber(transaction.getAccountnumber());
			redeemHistory.setCustid(transaction.getCustid());
			redeemHistory.setQuantity(transaction.getQuantity());
			redeemHistory.setRedeemeditem(transaction.getRedeemeditem());
			redeemHistory.setRedeemedpoints(transaction.getRedeemedpoints());
			redeemHistory.setRedemptiontimestaamp(transaction.getRedemptointimestaamp());
			redeemHistory.setVendorid(transaction.getVendorid());
			RedeemhistoryList.add(redeemHistory);
		});

		return RedeemhistoryList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mc.demo.app.redeem.service.RedemptionService#getHistoryByCustomerID(
	 * java.lang.String)
	 */
	@Override
	public List<RedemptionTransaction> getHistoryByCustomerID(String custId) {

		List<RedemptionTransaction> RedeemhistoryList = new ArrayList<RedemptionTransaction>();
		List<Redemptiontransaction> historyList = redeemRepo.findRedeemHistoryByCustid(custId);
		if (historyList == null || historyList.isEmpty()) {
			return null;
		}
		historyList.forEach(transaction -> {
			System.out.println("      " + transaction);
			RedemptionTransaction redeemHistory = new RedemptionTransaction();
			redeemHistory.setCardNumber(transaction.getAccountnumber());
			redeemHistory.setCustid(transaction.getCustid());
			redeemHistory.setQuantity(transaction.getQuantity());
			redeemHistory.setRedeemeditem(transaction.getRedeemeditem());
			redeemHistory.setRedeemedpoints(transaction.getRedeemedpoints());
			redeemHistory.setRedemptiontimestaamp(transaction.getRedemptointimestaamp());
			redeemHistory.setVendorid(transaction.getVendorid());
			RedeemhistoryList.add(redeemHistory);
		});

		return RedeemhistoryList;
	}

}
