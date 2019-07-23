package com.cts.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.cts.entity.Project;
import com.cts.entity.Task;

@Repository
public class ProjectRepository {

	static SessionFactory sessionFactory;

	static {
		sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	@Override
	protected void finalize(){
		HibernateUtil.closeSessionFactory();
	}

	public List<Project> findProjects() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Project> projects = null;
				
		Query queryResult = session.createNamedQuery("findProjects", Project.class);
		projects = queryResult.list();
			
		tx.commit();
		session.close();
		return projects;
	}
	


	public List<Project> findProject(int projectId) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Project> projects = new ArrayList();
		
		Project project = session.get(Project.class, projectId);
		projects.add(project);
		
		tx.commit();
		session.close();
		return projects;
	}
	

	public List<Project> findById(int projectId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Project> projects = new ArrayList();
				
		Query queryResult = session.createNamedQuery("findPJById", Project.class);
		queryResult.setParameter("projectId", projectId);
		projects = queryResult.list();
		tx.commit();
		session.close();
		return projects;
	}


	public void removeProject(int projectId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Project project = session.get(Project.class, projectId);
		session.remove(project);
		
		tx.commit();
		session.close();
	}
	
	
	public void addProject(Project p) {
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();
		session.save(p);

		tx.commit();
		session.close();
		
	}
	
	
	public void updateProject(Project project) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Project projectOld = session.get(Project.class, project.getProjectId());

		projectOld.setProject(project.getProject());
		projectOld.setStartDate(project.getStartDate());
		projectOld.setEndDate(project.getEndDate());
		projectOld.setPriority(project.getPriority());
		projectOld.setUsers(project.getUsers());

		tx.commit();
		session.close();
	}
	


}
