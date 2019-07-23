package com.cts.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.cts.entity.ParentTask;
import com.cts.entity.Project;
import com.cts.entity.Task;

@Repository
public class TaskRepository {

	static SessionFactory sessionFactory;

	static {
		sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	@Override
	protected void finalize(){
		HibernateUtil.closeSessionFactory();
	}

	public List<Task> findTasks() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Task> tasks = null;
				
		Query queryResult = session.createNamedQuery("findTasks", Task.class);
		tasks = queryResult.list();
				
		tx.commit();
		session.close();
		return tasks;
	}
	
	public List<ParentTask> findParentTaskList() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<ParentTask> parentTasks = null;
				
		Query queryResult = session.createNamedQuery("findParentTasks", ParentTask.class);
		parentTasks = queryResult.list();
				
		tx.commit();
		session.close();
		return parentTasks;
	}
	
	
	public List<Task> findTask(int taskId) {
	
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Task> tasks = new ArrayList();
		
		Task task = session.get(Task.class, taskId);
		tasks.add(task);
		
		tx.commit();
		session.close();
		return tasks;
	}
	

	public List<Task> findById(int taskId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Task> tasks = new ArrayList();
				
		Query queryResult = session.createNamedQuery("findById", Task.class);
		queryResult.setParameter("taskId", taskId);
		tasks = queryResult.list();
		
		tx.commit();
		session.close();
		return tasks;
	}
	
	public List<ParentTask> findPTById(int pId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<ParentTask> parentTasks = new ArrayList();
				
		Query queryResult = session.createNamedQuery("findPTById", ParentTask.class);
		queryResult.setParameter("parentId", pId);
		parentTasks = queryResult.list();
		
		tx.commit();
		session.close();
		return parentTasks;
	}

	
	public void removeTask(int taskId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Task task = session.get(Task.class, taskId);

		session.remove(task);
		
		tx.commit();
		session.close();
	}
	

	public void addParentTask(ParentTask pt) {
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();
		session.save(pt);

		tx.commit();
		session.close();
		
	}
	
	public void addTask(Task t) {
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();
		session.save(t);

		tx.commit();
		session.close();
		
	}
	
	
	public void updateTask(Task task) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Task taskOld = session.get(Task.class, task.getTaskId());

		taskOld.setTask(task.getTask());
		taskOld.setStartDate(task.getStartDate());
		taskOld.setEndDate(task.getEndDate());
		taskOld.setPriority(task.getPriority());
		taskOld.setParentTask(task.getParentTask());
		taskOld.setUsers(task.getUsers());
		taskOld.setProject(task.getProject());
		
		tx.commit();
		session.close();
	}
	

	/**
	 * 
	 * @param taskId
	 */
	public void updateTaskStatus(int taskId) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Task task = session.get(Task.class, taskId);

		task.setStatus(0);

		tx.commit();
		session.close();
	}


}
