//class for Item
public class Item {
	private String itemID;
	private String itemName;
	private String itemBrand;
	private String itemType;
	private int itemQuantity;
	
	//constructors
	public Item() {}
	
	public Item(String ItemID, String ItemName, String ItemBrand, String ItemType, int ItemQuantity) {
		this.itemID = ItemID;
		this.itemName = ItemName;
		this.itemBrand = ItemBrand;
		this.itemType = ItemType;
		this.itemQuantity = ItemQuantity;
	}
	
	//methods
	public String getItemID() {
		return itemID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public String getItemBrand() {
		return itemBrand;
	}
	
	public String getItemType() {
		return itemType;
	}
	
	public int getItemQuantity() {
		return itemQuantity;
	}
	
	public void setItemID(String ItemID) {
		this.itemID = ItemID;
	}
	
	public void setItemName(String ItemName) {
		this.itemName = ItemName;
	}
	
	public void setItemBrand(String ItemBrand) {
		this.itemBrand = ItemBrand;
	}
	
	public void setItemType(String ItemType) {
		this.itemType = ItemType;
	}
	
	public void setItemQuantity(int ItemQuantity) {
		this.itemQuantity = ItemQuantity;
	}
}
