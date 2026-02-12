package io.github.wcm.academicscheduler.course.internal;

import java.util.ArrayList;
import java.util.List;

public class CourseTimeslots {
	private List<Timeslot> lecture = new ArrayList<>();
	private List<Timeslot> tutorial = new ArrayList<>();
	private List<Timeslot> practical = new ArrayList<>();

	public CourseTimeslots() {
	}

	public CourseTimeslots(List<Timeslot> lecture, List<Timeslot> tutorial, List<Timeslot> practical) {
		this.lecture = lecture;
		this.tutorial = tutorial;
		this.practical = practical;
	}

	public List<Timeslot> getLecture() {
		return lecture;
	}

	public void setLecture(List<Timeslot> lecture) {
		this.lecture = lecture;
	}

	public List<Timeslot> getTutorial() {
		return tutorial;
	}

	public void setTutorial(List<Timeslot> tutorial) {
		this.tutorial = tutorial;
	}

	public List<Timeslot> getPractical() {
		return practical;
	}

	public void setPractical(List<Timeslot> practical) {
		this.practical = practical;
	}
}