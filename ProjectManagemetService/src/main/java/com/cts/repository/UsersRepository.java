package com.cts.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.cts.entity.Project;
import com.cts.entity.Users;

@Repository
public class UsersRepository {

	static SessionFactory sessionFactory;

	static {
		sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	@Override
	protected void finalize(){
		HibernateUtil.closeSessionFactory();
	}

	public List<Users> findUsers() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Users> users = null;
				
		Query queryResult = session.createNamedQuery("findUsers", Users.class);
		users = queryResult.list();
			
		tx.commit();
		session.close();
		return users;
	}


	public List<Users> findUser(int userId) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Users> users = new ArrayList();
		
		Users user = session.get(Users.class, userId);
		users.add(user);
		
		tx.commit();
		session.close();
		return users;
	}
	

	public List<Users> findById(int userId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Users> users = new ArrayList();
				
		Query queryResult = session.createNamedQuery("findUserById", Users.class);
		queryResult.setParameter("userId", userId);
		users = queryResult.list();
		tx.commit();
		session.close();
		return users;
	}


	public void removeUser(int userId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Users user = session.get(Users.class, userId);
		session.remove(user);
		
		tx.commit();
		session.close();
	}
	
	
	public void addUser(Users user) {
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();
		session.save(user);

		tx.commit();
		session.close();
		
	}
	
	
	public void updateUser(Users user) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Users userOld = session.get(Users.class, user.getUserId());

		userOld.setFirstName(user.getFirstName());
		userOld.setLastName(user.getLastName());
		userOld.setEmployeeId(user.getEmployeeId());

		tx.commit();
		session.close();
	}
	


}
