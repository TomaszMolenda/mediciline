package pl.tomo.provider.pageable;

public class Link {
	
	public static final String REL_PREVIOUS = "prev";
	public static final String REL_NEXT = "next";
	public static final String REL_SELF = "self";
	public static final String REL_FIRST = "first";
	public static final String REL_LAST = "last";
	
	
	
	String link;
	String rel;
	
	public Link(String uriString, String relPrevious) {
		this.link = uriString;
		this.rel = relPrevious;
	}

	

}
