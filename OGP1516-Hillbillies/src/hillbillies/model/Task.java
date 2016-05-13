package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.statement.Statement;


/**
 * @invar  The ExecutingUnit of each Task must be a valid ExecutingUnit for any
 *         Task.
 *       | isValidExecutingUnit(getExecutingUnit())
 */
public class Task implements Comparable<Task>{

	private String name;
	private int priority;
	private Statement activity;
	private Set<Scheduler> schedulers;

	public Task(String name, int priority, Statement activity) {
		if (activity == null) {
			throw new NullPointerException("activity null");
		}
		if (name == null) {
			throw new NullPointerException("name must not be null");
		}
		this.name = name;
		this.priority = priority;
		this.activity = activity;
		this.schedulers = new HashSet<>();
	}

	@Override
	public String toString() {
		return "Name: " + this.name + "\nPriority: " + this.priority +
				"\nActivity:" + this.activity.toString();
	}
	
	public String getName(){
		return this.name;
	}
	
	public void execute(Unit unit) throws IllegalStateException, IllegalArgumentException{
		if (unit.getTask() != this)
			throw new IllegalStateException();
		this.setExecutingUnit(unit);
		this.activity.execute(unit); //TODO Double dispatch
	}

	/**
	 * Return the ExecutingUnit of this Task.
	 */
	@Basic @Raw
	public Unit getExecutingUnit() {
		return this.executingUnit;
	}
	
	/**
	 * Check whether the given ExecutingUnit is a valid ExecutingUnit for
	 * any Task.
	 *  
	 * @param  ExecutingUnit
	 *         The ExecutingUnit to check.
	 * @return 
	 *       | result == true
	*/
	public boolean isValidExecutingUnit(Unit executingUnit) {
		for (Scheduler scheduler: this.getSchedulers())
			if (scheduler.getFaction() == executingUnit.getFaction())
				return true;
		return false; //TODO
	}
	
	/**
	 * Set the ExecutingUnit of this Task to the given ExecutingUnit.
	 * 
	 * @param  executingUnit
	 *         The new ExecutingUnit for this Task.
	 * @post   The ExecutingUnit of this new Task is equal to
	 *         the given ExecutingUnit.
	 *       | new.getExecutingUnit() == executingUnit
	 * @throws IllegalArgumentException
	 *         The given ExecutingUnit is not a valid ExecutingUnit for any
	 *         Task.
	 *       | ! isValidExecutingUnit(getExecutingUnit())
	 */
	@Raw
	private void setExecutingUnit(Unit executingUnit) 
			throws IllegalArgumentException {
		if (! isValidExecutingUnit(executingUnit))
			throw new IllegalArgumentException();
		this.executingUnit = executingUnit;
	}
	
	/**
	 * Variable registering the ExecutingUnit of this Task.
	 */
	private Unit executingUnit = null;
	
	public void advanceTime(){
		this.activity.advanceTime();
	}
	
	public boolean isFinished(){
		return this.activity.isFinished();
	}
	
	public int getPriority(){
		return this.priority;
	}
	
	public boolean isBeingExecuted(){
		return (this.executingUnit != null);
	}

	@Override
	public int compareTo(Task other) {
		if (this.getPriority() > other.getPriority())
			return 1;
		else if (this.getPriority() == other.getPriority())
			return 0;
		else
			return -1;
	}
	
	public Set<Scheduler> getSchedulers(){
		return this.schedulers;
	}
	
	public void addScheduler(Scheduler sheduler){
		this.schedulers.add(sheduler);
	}
	
	public void removeScheduler(Scheduler scheduler){
		this.schedulers.remove(scheduler);
	}
	
}