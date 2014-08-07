package com.lizard.eye.model;

public class Menus {

	private String menu_name, id, menu_url,nooficons;
	private boolean InAppPurchaseRequired;

	public Menus() {

	}

	public Menus(String id,String menu_name, String menu_url,String nooficons,boolean InAppPurchaseRequired) {
		this.id = id;
		this.menu_name = menu_name;
		this.menu_url = menu_url;
		this.nooficons = nooficons;
		this.InAppPurchaseRequired = InAppPurchaseRequired;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setMenuName(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getMenuName() {
		return this.menu_name;
	}

	public void setMenuUrl(String menu_url) {
		this.menu_url = menu_url;
	}

	public String getMenuUrl() {
		return this.menu_url;
	}
	
	public void setNoofIcons(String nooficons) {
		this.nooficons = nooficons;
	}

	public String getNoofIcons() {
		return this.nooficons;
	}
	
	public void setInAppPurchaseRequired(boolean InAppPurchaseRequired) {
		this.InAppPurchaseRequired = InAppPurchaseRequired;
	}

	public boolean getInAppPurchaseRequired() {
		return this.InAppPurchaseRequired;
	}
}
