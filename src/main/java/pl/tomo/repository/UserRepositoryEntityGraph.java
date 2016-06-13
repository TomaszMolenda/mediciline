package pl.tomo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.tomo.entity.User;



@Repository
public class UserRepositoryEntityGraph {
	
	@Autowired
	private EntityManager entityManager;
	
	public List<User> getAll(String query, String...param) {
		EntityGraph<User> entityGraph = template(param);
		List<User> users = entityManager.createQuery(query, User.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getResultList();
		return users;
	}

	private EntityGraph<User> template(String... param) {
		EntityGraph<User> entityGraph = entityManager.createEntityGraph(User.class);
		entityGraph.addAttributeNodes(param);
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.fetchgraph", entityGraph);
		return entityGraph;
	}

	public User getOne(String query, String...param) {
		EntityGraph<User> entityGraph = template(param);
		User user = entityManager.createQuery(query, User.class)
			.setHint("javax.persistence.loadgraph", entityGraph)
			.getSingleResult();
		return user;
		
	}

}
