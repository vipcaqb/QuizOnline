package hl.quizonline.enumrable;

public enum Gender {
	MALE("Nam"),
	FEMALE("Nữ"),
	ORTHER("Khác");
	
	private final String displayValue;
    
    private Gender(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
}
