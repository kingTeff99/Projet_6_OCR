package com.buddy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buddy.dto.FullTransactionDTO;
import com.buddy.dto.TransactionDTO;
import com.buddy.model.BankAccount;
import com.buddy.model.Transaction;
import com.buddy.model.Users;
import com.buddy.repository.BankRepository;
import com.buddy.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Service @Transactional @Slf4j
public class TransactionService {
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	/**
	 * Make a transaction
	 * @param transaction
	 * @return Transaction
	 */
	public TransactionDTO makeTransactionWithInputVerification(FullTransactionDTO transaction) {
		
		if(contactService.verifyRelationship(transaction.getUserSenderId()
				, transaction.getUserReceiverId()) == null) 
		{
            	return null;
        }
            
        BankAccount sender = bankService.getBankAccountByUserId(
        		transaction.getUserSenderId());
            
        BankAccount receiver = bankService.getBankAccountByUserId(
        		transaction.getUserReceiverId());

        Double fees = Math.round((transaction.getAmount() * 0.05) * 100.0)/100.0;
            
        sender.setBalance( Math.round(
        		(sender.getBalance() - transaction.getAmount() - fees) * 100.0) / 100.0);
            
        receiver.setBalance( Math.round(
        		(receiver.getBalance() + transaction.getAmount()) * 100.0) / 100.0);
            
        transaction.setFees(fees);
            
        Transaction transact = Transaction.builder()
        		.amount(transaction.getAmount())
            	.bankReceiverId(receiver)
            	.bankSenderId(sender)
            	.userReceiverId(receiver.getUserId())
            	.userSenderId(sender.getUserId())
            	.description(transaction.getDescription())
            	.fees(transaction.getFees())
            	.build();
            
        transactionRepository.save(transact);
        
        bankRepository.save(sender);
        
        bankRepository.save(receiver);
            
        log.info("Transaction {} made", transact.getId());
            
        return TransactionDTO.builder()
        			.id(transaction.getId())
            		.amount(transaction.getAmount())
            		.description(transaction.getDescription())
            		.build();
    }

	/**
	 * Get a Transaction
	 * @param userIdSender
	 * @param userIdReceiver
	 * @return List of transaction
	 */
    public List<Transaction> getTransactions(Users userSenderId, Users userReceiverId) {
    	
    	if((userSenderId == null) ||(userReceiverId == null)) {
    		return Collections.emptyList();
    	}
    	
    	return transactionRepository.findByUserReceiverIdAndUserSenderId(userSenderId
    			, userReceiverId);
    	
    }
    
    /**
     * Get All Transactions By User Id
     * @param id
     * @return List of transaction
     */
    public List<Transaction> getAllTransactionsForAnUser(Users userId) {
    	
    	if(userId == null) {
    		return Collections.emptyList();
    	}
    	
    	List<Transaction> allTransaction = new ArrayList<>();
    	
    	allTransaction.addAll(transactionRepository.findByUserReceiverId(userId));
    	
    	allTransaction.addAll(transactionRepository.findByUserSenderId(userId));
    	
    	return allTransaction;
    			
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public Transaction getOneTransaction(Long id) {
    	
    	if(id == null) {
    		return null;
    	}
    	
        return transactionRepository.getById(id);
    	
    }
    
}
