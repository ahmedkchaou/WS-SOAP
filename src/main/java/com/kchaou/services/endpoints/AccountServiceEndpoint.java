package com.kchaou.services.endpoints;

import javax.xml.transform.Source;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.support.MarshallingSource;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.kchaou.services.AccountService;
import com.kchaou.webservices.Account;
import com.kchaou.webservices.accountservice.AccountDetailsRequest;
import com.kchaou.webservices.accountservice.AccountDetailsResponse;



/**
 * The Class AccountService.
 */
@Endpoint
public class AccountServiceEndpoint
{
	private static final String TARGET_NAMESPACE = "http://com/kchaou/webservices/accountservice";

	//@Autowired
	//private Marshaller marshaller;
	
	@Autowired
	private AccountService accountService_i;

	protected transient final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Gets the account details.
	 *
	 * @param accountNumber the account number
	 * @return the account details
	 */
	
	@PayloadRoot(localPart = "AccountDetailsRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload AccountDetailsResponse getAccountDetails(@RequestPayload AccountDetailsRequest request)
	{
		AccountDetailsResponse response = new AccountDetailsResponse();
		
		try {

			logger.info("numberID is : " + request.getAccountNumber());


		/* call Spring injected service implementation to retrieve account data */
		
		if (request.getAccountNumber() != null) {
			Account account = accountService_i.getAccountDetails(request.getAccountNumber());
			
			logger.info("Account Details is : " + response.getAccountDetails());
			
			response.setAccountDetails(account);
		}
		else {
			response.setFaultmessage("Error");
			logger.info("Exception ");
		}
		
	
		} catch (Exception e) {
			logger.info("Exception : " + e.getMessage());
			//response.setFaultmessage("Error");
			
			//return new MarshallingSource(marshaller, response);
		}
		return response;
	}


	public void setAccountService(AccountService accountService_p)
	{
		this.accountService_i = accountService_p;
	}
}