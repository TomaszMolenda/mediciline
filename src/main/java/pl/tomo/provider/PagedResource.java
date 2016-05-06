package pl.tomo.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pl.tomo.entity.Medicament;

public class PagedResource<T> {
	
	private List<T> content;
	private final Map metadata;
	
	public PagedResource(Page<T> page, List<T> content) {
		super();
		this.setContent(content);
		this.metadata = new HashMap();
		
		List<Link> links = new ArrayList<Link>();
		
		if(page.hasPreviousPage()) {
			String uriString = createBuilder().queryParam("page", page.getNumber() - 1).queryParam("size", page.getSize()).toUriString();
			Link previousLink = new Link(uriString, Link.REL_PREVIOUS);
			links.add(previousLink);
		}
		if(page.hasNextPage()) {
			String uriString = createBuilder().queryParam("page", page.getNumber() + 1).queryParam("size", page.getSize()).toUriString();
			Link nextLink = new Link(uriString, Link.REL_NEXT);
			links.add(nextLink);
		}
		if(!page.isFirstPage()) {
			String uriString = createBuilder().queryParam("page", 0).queryParam("size", page.getSize()).toUriString();
			Link firstLink = new Link(uriString, Link.REL_FIRST);
			links.add(firstLink);
		}
		if(!page.isLastPage()) {
			String uriString = createBuilder().queryParam("page", page.getTotalPages() - 1).queryParam("size", page.getSize()).toUriString();
			Link lastLink = new Link(uriString, Link.REL_LAST);
			links.add(lastLink);
		}

		metadata.put("links", links);
	}
	
	private ServletUriComponentsBuilder createBuilder() {
	    return ServletUriComponentsBuilder.fromCurrentRequestUri();
	  }
	
	private void setContent(List<T> content) {
	    this.content = content;
	  }

}
