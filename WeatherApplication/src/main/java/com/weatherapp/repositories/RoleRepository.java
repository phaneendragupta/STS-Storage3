package com.weatherapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weatherapp.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{

	Optional<Role> findByroleId(Integer id);

	 @Query("""
			    SELECT r FROM Role r WHERE r.role_name = :name """)

	Optional<Role> findByrole_name(String name);

}

//@Repository
//public class RoleRepository {
//
//	private final JdbcTemplate jdbcTemplate;
//
//	public RoleRepository(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}
//
//	public Optional<Role> findByRoleId(Integer id) {
//        String sql = "SELECT * FROM roles WHERE id = ?";
//        return jdbcTemplate.query(sql, new Object[]{id}, new RoleRowMapper())
//                .stream()
//                .findFirst();
//    }
//
//    public Optional<Role> findByRoleName(String name) {
//        String sql = "SELECT * FROM roles WHERE role_name = ?";
//        return jdbcTemplate.query(sql, new Object[]{name}, new RoleRowMapper())
//                .stream()
//                .findFirst();
//    }
//}
