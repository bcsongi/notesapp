package ro.bcim1296.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    // az elemekhez rendelheto prioritások
	public enum Priority {
		LOW, MEDIUM, HIGH
	}

	private Long _id;
	private String title;
	private Priority priority;
	private String dueDate;
	private String description;
    private double latitude;
    private double longitude;

	public Note(String title, Priority priority, String dueDate, String description, double latitude, double longitude) {
		this(-1l, title, priority, dueDate, description, latitude, longitude);
	}
	
	public Note(Long _id, String title, Priority priority, String dueDate, String description, double latitude, double longitude) {
		this._id = _id;
		this.title = title;
		this.priority = priority;
		this.dueDate = dueDate;
		this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
	}

	public Note(Parcel in) {
		this._id = in.readLong();
		this.title = in.readString();
		String priorityName = in.readString();
		if (priorityName != null) {
            this.priority = Priority.valueOf(priorityName);
        }
        this.dueDate = in.readString();
		this.description = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
	}

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

	public String getTitle() {
		return title;
	}

	public Priority getPriority() {
		return priority;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getDescription() {
		return description;
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

    // Parcelable

    public int describeContents() {
        return 0;
    }

    // az objektum parcelle alakitasa
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(title);
        dest.writeString(priority == null ? null : priority.name());
        dest.writeString(dueDate);
        dest.writeString(description);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
	}
    
    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
