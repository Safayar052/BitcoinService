package com.vayama.jsonobject;

import org.joda.money.Money;

public class Button {
	private String name;
	private String type;
	private boolean subscription;
	private String price_string;
	private String price_currency_iso;
	private String custom;
	private String callback_url;
	private String success_url;
	private String cancel_url;
	private String description;
	private String style;
	private boolean include_email;
	private Money _price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isSubscription() {
		return subscription;
	}
	public void setSubscription(boolean subscription) {
		this.subscription = subscription;
	}
	public String getPriceString() {
		return price_string;
	}
	public void setPriceString(String price_string) {
		this.price_string = price_string;
	}
	public String getPriceCurrencyIso() {
		return price_currency_iso;
	}
	public void setPriceCurrencyIso(String price_currency_iso) {
		this.price_currency_iso = price_currency_iso;
	}
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	public String getCallbackUrl() {
		return callback_url;
	}
	public void setCallbackUrl(String callback_url) {
		this.callback_url = callback_url;
	}
	public String getSuccessUrl() {
		return success_url;
	}
	public void setSuccessUrl(String success_url) {
		this.success_url = success_url;
	}
	public String getCancelUrl() {
		return cancel_url;
	}
	public void setCancelUrl(String cancel_url) {
		this.cancel_url = cancel_url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public boolean isIncludeEmail() {
		return include_email;
	}
	public void setIncludeEmail(boolean include_email) {
		this.include_email = include_email;
	}
	
	public Money getPrice() {
        return _price;
    }
    public void setPrice(Money price) {
        _price = price;
        if (price != null) {
            setPriceString(price.getAmount().toPlainString());
            setPriceCurrencyIso(price.getCurrencyUnit().getCurrencyCode());
        } else {
            setPriceString(null);
            setPriceCurrencyIso(null);
        }
    }
	
	@Override
	public String toString() {
		return "Button [name=" + name + ", type=" + type +
				", subscription=" + subscription + ", price_string=" + price_string +
				", price_currency_iso=" + price_currency_iso + ", custom=" + custom +
				", callback_url=" + callback_url + ", success_url=" + success_url + 
				", cancel_url=" + cancel_url +  ", description=" + description +
				", style=" + style + ", include_email=" + include_email + "]";
	}

}

