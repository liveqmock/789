package com.icebreak.p2p.integration.openapi.order;

import org.springframework.util.Assert;

import com.icebreak.p2p.ws.order.ValidateOrderBase;
import com.icebreak.util.lang.util.money.Money;
import com.icebreak.util.service.Order;

public class TradeTransferOrder extends ValidateOrderBase implements Order {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -4785083019448254123L;
	
	/** 订单号 */
	private String				orderNo;
	
	/** 付款人 */
	private String				payerUserId;
	
	/** 交易名称 */
	private final String		tradeName			= "";
	
	/** 交易额 */
	private final Money			tradeAmount			= Money.zero();
	/** 交易类型 */
	private final String		tradeType			= "";
	
	/** 来源系统 */
	protected String			systemId;
	
	/** 交易备注 */
	protected String			tradeMemo;
	
	/** 买家 */
	protected String			buyerUserId;
	
	/** 卖家 */
	private String				sellerUserId;
	
	/** 商品名称 */
	private String				name;
	
	/** 商品详情 */
	private String				memo;
	
	/** 商品单价 */
	private final Money			price				= Money.zero();
	
	/** 商品数量 */
	private final int			quantity			= 1;
	
	/** 商品单位 */
	private final String		unit				= "";
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		Assert.hasText(orderNo);
		Assert.hasText(payerUserId);
		Assert.hasText(tradeName);
		Assert.isTrue(tradeAmount.greaterThan(Money.zero()));
		Assert.hasText(buyerUserId);
		Assert.hasText(sellerUserId);
		Assert.hasText(name);
		
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getPayerUserId() {
		return payerUserId;
	}
	
	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}
	
	public String getSystemId() {
		return systemId;
	}
	
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getTradeMemo() {
		return tradeMemo;
	}
	
	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}
	
	public String getBuyerUserId() {
		return buyerUserId;
	}
	
	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}
	
	public String getSellerUserId() {
		return sellerUserId;
	}
	
	public void setSellerUserId(String sellerUserId) {
		this.sellerUserId = sellerUserId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getTradeName() {
		return tradeName;
	}
	
	public Money getTradeAmount() {
		return tradeAmount;
	}
	
	public String getTradeType() {
		return tradeType;
	}
	
	public Money getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public String getUnit() {
		return unit;
	}
	
}
