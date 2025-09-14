package com.java.taskmanager;

import java.time.LocalDate;

public class Task {
	private String title;
	private int priority; // 1 = High, 2 = Medium, 3 = Low
	private LocalDate deadline;

	public Task(String title, int priority, LocalDate deadline) {
		this.title = title;
		this.priority = priority;
		this.deadline = deadline;
	}

	public String getTitle() {
		return title;
	}

	public int getPriority() {
		return priority;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	@Override
	public String toString() {
		return String.format("[%s] Priority: %d, Due: %s", title, priority, deadline);
	}
}
