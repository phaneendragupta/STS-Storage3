package com.weatherapp.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weatherapp.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	// Here the fields in the query should be according to your entity.
	 @Query("""
			    SELECT u FROM User u WHERE u.userName = :username """)
			  Optional<User> findUserByuserName(String username);
	 @Query("""
			    SELECT u FROM User u WHERE u.userEmail = :useremail """)
	 		Optional<User> findUserByuserEmail(String useremail);
//	 @Query("""
//			    SELECT u FROM User u WHERE u.id = :userid """)
//	Optional<User> findUserByuserId(Integer userid);

}

//@Repository
//public class UserRepository{
//
//	@Autowired
//	private final JdbcTemplate jdbcTemplate;
//	@Autowired
//	private UserRowMapper userRowMapper;
//
//    public UserRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//	public void save(User user) {
//		String sql = "INSERT INTO users (user_name, user_email, password) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, user.getUserName(), user.getUserEmail(),user.getPassword());
//
//	}
//
//	public Optional<User> findUserByuserEmail(String userEmail) {
//		String sql = "SELECT * FROM users WHERE user_email = ?";
//		 return jdbcTemplate.query(sql,new UserRowMapper())
//		           .stream()
//		           .findFirst();
////        List<User> users = jdbcTemplate.query(sql, new Object[]{userEmail}, new UserRowMapper());
////        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
//	}
//
//	public Optional<User> findById(int userId) {
//		String sql = "SELECT * FROM users WHERE id = ?";
//		List<User> users = jdbcTemplate.query(sql, new Object[]{userId}, new UserRowMapper());
//        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
//	}
//
//	public List<User> findAll() {
//		String sql = "SELECT * FROM users";
//        return jdbcTemplate.query(sql, new UserRowMapper());
//	}
//
//	public Optional<User> findUserByuserName(String name) {
//		String sql = "SELECT * FROM users WHERE name = ?";
//        List<User> users = jdbcTemplate.query(sql, new Object[]{name}, new UserRowMapper());
//        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
//	}
//
//	public void delete(User user) {
//		String sql = "DELETE FROM users WHERE id = ?";
//	    jdbcTemplate.update(sql, user.getId());
//	}
//
//}





